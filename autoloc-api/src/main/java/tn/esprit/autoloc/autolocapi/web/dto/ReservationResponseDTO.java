package tn.esprit.autoloc.autolocapi.web.dto;

import java.time.LocalDate;
import tn.esprit.autoloc.autolocapi.domain.StatutReservation;

public record ReservationResponseDTO(
        Long idReservation,
        LocalDate dateDebut,
        LocalDate dateFin,
        StatutReservation statut,
        Long vehiculeId,
        Long clientId,
        String nomClient,
        String prenomClient,
        Long contratId
) {}