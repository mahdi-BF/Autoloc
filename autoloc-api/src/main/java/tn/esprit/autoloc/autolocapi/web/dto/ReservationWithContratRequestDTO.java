package tn.esprit.autoloc.autolocapi.web.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import tn.esprit.autoloc.autolocapi.domain.StatutReservation;

public record ReservationWithContratRequestDTO(
        @NotNull LocalDate dateDebut,
        @NotNull LocalDate dateFin,
        @NotNull StatutReservation statut,
        @NotNull Long vehiculeId,
        @NotNull Long clientId,
        @NotNull LocalDate dateSignature,
        @NotNull @DecimalMin(value = "0.0", inclusive = false) BigDecimal montantTotal,
        boolean valide
) {}
