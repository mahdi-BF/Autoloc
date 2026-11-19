package tn.esprit.autoloc.autolocapi.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.autolocapi.domain.Reservation;
import tn.esprit.autoloc.autolocapi.exception.ResourceNotFoundException;
import tn.esprit.autoloc.autolocapi.repository.IReservationRepository;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.autolocapi.domain.Client;
import tn.esprit.autoloc.autolocapi.domain.Contrat;
import tn.esprit.autoloc.autolocapi.domain.Vehicule;
import tn.esprit.autoloc.autolocapi.repository.IClientRepository;
import tn.esprit.autoloc.autolocapi.repository.IVehiculeRepository;
import tn.esprit.autoloc.autolocapi.repository.IContratRepository;
import tn.esprit.autoloc.autolocapi.mapper.ReservationMapper;
import tn.esprit.autoloc.autolocapi.web.dto.ReservationResponseDTO;
import tn.esprit.autoloc.autolocapi.web.dto.ReservationWithContratRequestDTO;


@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements IReservationService {

    private final IReservationRepository reservationRepository;
    private final IClientRepository clientRepository;
    private final IVehiculeRepository vehiculeRepository;
    private final IContratRepository contratRepository;
    private final ReservationMapper reservationMapper;
    @Override
    @Transactional
    public ReservationResponseDTO affecterReservationAClient(
            Long idReservation, String nom, String prenom) {

        Reservation reservation = findById(idReservation);

        Client client = clientRepository.findByNomAndPrenom(nom, prenom)
                .orElseThrow(() ->
                        new IllegalArgumentException("Client introuvable"));

        reservation.setClient(client);

        Reservation saved = reservationRepository.save(reservation);

        return reservationMapper.toResponse(saved);
    }
    @Override
    @Transactional
    public ReservationResponseDTO creerReservationEtContratAssocie(
            ReservationWithContratRequestDTO requestDTO) {

        Vehicule vehicule = vehiculeRepository.findById(requestDTO.vehiculeId())
                .orElseThrow(() -> new IllegalArgumentException("Véhicule introuvable"));

        Client client = clientRepository.findById(requestDTO.clientId())
                .orElseThrow(() -> new IllegalArgumentException("Client introuvable"));

        Reservation reservation = new Reservation();

        reservation.setDateDebut(requestDTO.dateDebut());
        reservation.setDateFin(requestDTO.dateFin());
        reservation.setStatut(requestDTO.statut());
        reservation.setVehicule(vehicule);
        reservation.setClient(client);

        verifier(reservation);

        Reservation savedReservation = reservationRepository.save(reservation);

        Contrat contrat = new Contrat();
        contrat.setDateSignature(requestDTO.dateSignature());
        contrat.setMontantTotal(requestDTO.montantTotal());
        contrat.setValide(requestDTO.valide());
        contrat.setReservation(savedReservation);

        Contrat savedContrat = contratRepository.save(contrat);

        savedReservation.setContrat(savedContrat);

        savedReservation = reservationRepository.save(savedReservation);

        return reservationMapper.toResponse(savedReservation);
    }

    @Override
    public Reservation create(Reservation reservation) {
        if (reservation.getIdReservation() != null) {
            throw new IllegalArgumentException(
                    "Une nouvelle réservation ne doit pas avoir d'identifiant");
        }
        verifier(reservation);
        return reservationRepository.save(reservation);
    }

    @Override
    public Reservation findById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", id));
    }

    @Override

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation update(Long id, Reservation reservation) {
        Reservation existante = findById(id);
        verifier(reservation);
        existante.setDateDebut(reservation.getDateDebut());
        existante.setDateFin(reservation.getDateFin());
        existante.setStatut(reservation.getStatut());
        existante.setVehicule(reservation.getVehicule());
        existante.setClient(reservation.getClient());
        existante.setContrat(reservation.getContrat());
        return reservationRepository.save(existante);
    }

    @Override
    public void deleteById(Long id) {
        if (!reservationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Reservation", id);
        }
        reservationRepository.deleteById(id);
    }

    private void verifier(Reservation reservation) {
        if (reservation.getDateDebut() != null && reservation.getDateFin() != null
                && reservation.getDateFin().isBefore(reservation.getDateDebut())) {
            throw new IllegalArgumentException(
                    "La date de fin ne peut pas être antérieure à la date de début");
        }
    }
}
