package tn.esprit.autoloc.autolocapi.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.autolocapi.domain.Client;
import tn.esprit.autoloc.autolocapi.domain.Contrat;
import tn.esprit.autoloc.autolocapi.domain.Reservation;
import tn.esprit.autoloc.autolocapi.domain.Vehicule;
import tn.esprit.autoloc.autolocapi.web.dto.ReservationRequestDTO;
import tn.esprit.autoloc.autolocapi.web.dto.ReservationResponseDTO;

@Component
public class ReservationMapper {
    public ReservationResponseDTO toResponse(Reservation reservation) {
        return new ReservationResponseDTO(
                reservation.getIdReservation(),
                reservation.getDateDebut(),
                reservation.getDateFin(),
                reservation.getStatut(),
                reservation.getVehicule() == null ? null : reservation.getVehicule().getIdVehicule(),
                reservation.getClient() == null ? null : reservation.getClient().getIdClient(),
                reservation.getClient() == null ? null : reservation.getClient().getNom(),
                reservation.getClient() == null ? null : reservation.getClient().getPrenom(),
                reservation.getContrat() == null ? null : reservation.getContrat().getIdContrat());
    }

    public Reservation toEntity(ReservationRequestDTO request) {
        Reservation reservation = new Reservation();
        reservation.setDateDebut(request.dateDebut());
        reservation.setDateFin(request.dateFin());
        reservation.setStatut(request.statut());

        Vehicule vehicule = new Vehicule();
        vehicule.setIdVehicule(request.vehiculeId());
        reservation.setVehicule(vehicule);

        Client client = new Client();
        client.setIdClient(request.clientId());
        reservation.setClient(client);
        if (request.contratId() != null) {
            Contrat contrat = new Contrat();
            contrat.setIdContrat(request.contratId());
            reservation.setContrat(contrat);
        }
        return reservation;
    }
}
