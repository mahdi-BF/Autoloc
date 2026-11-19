package tn.esprit.autoloc.autolocapi.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.autolocapi.domain.Contrat;
import tn.esprit.autoloc.autolocapi.exception.ResourceNotFoundException;
import tn.esprit.autoloc.autolocapi.repository.IContratRepository;
import java.time.LocalDate;
import tn.esprit.autoloc.autolocapi.domain.Reservation;
import tn.esprit.autoloc.autolocapi.repository.IReservationRepository;
import tn.esprit.autoloc.autolocapi.web.dto.ContratDTO;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContratServiceImpl implements IContratService {
    private final IReservationRepository reservationRepository;



    private final IContratRepository contratRepository;
    @Override
    @Transactional
    public ContratDTO affecterContratAReservation(
            LocalDate dateSignature,
            LocalDate dateDebut,
            LocalDate dateFin) {

        Reservation reservation = reservationRepository
                .findByDateDebutAndDateFin(dateDebut, dateFin)
                .orElseThrow(() ->
                        new IllegalArgumentException("Réservation introuvable"));

        if (reservation.getContrat() != null) {
            throw new IllegalArgumentException(
                    "Cette réservation possède déjà un contrat");
        }

        Contrat contrat = new Contrat();
        contrat.setDateSignature(dateSignature);
        contrat.setReservation(reservation);

        Contrat saved = contratRepository.save(contrat);

        reservation.setContrat(saved);
        reservationRepository.save(reservation);

        return new ContratDTO(
                saved.getIdContrat(),
                saved.getDateSignature(),
                saved.getMontantTotal(),
                saved.isValide(),
                reservation.getIdReservation(),
                List.of()
        );
    }

    @Override
    public Contrat create(Contrat contrat) {
        if (contrat.getIdContrat() != null) {
            throw new IllegalArgumentException(
                    "Un nouveau contrat ne doit pas avoir d'identifiant");
        }
        return contratRepository.save(contrat);
    }

    @Override
    public Contrat findById(Long id) {
        return contratRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contrat", id));
    }

    @Override
    public List<Contrat> findAll() {

        return contratRepository.findAll();
    }

    @Override
    public Contrat update(Long id, Contrat contrat) {
        Contrat existant = findById(id);
        existant.setMontantTotal(contrat.getMontantTotal());

        return contratRepository.save(existant);
    }

    @Override
    public void deleteById(Long id) {
        if (!contratRepository.existsById(id)) {
            throw new ResourceNotFoundException("Contrat", id);
        }
        contratRepository.deleteById(id);
    }
}
