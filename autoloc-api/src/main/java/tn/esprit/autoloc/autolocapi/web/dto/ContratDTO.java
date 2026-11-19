package tn.esprit.autoloc.autolocapi.web.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public record ContratDTO(
        Long idContrat,
        @NotNull LocalDate dateSignature,
        @NotNull @DecimalMin(value = "0.0", inclusive = false) BigDecimal montantTotal,
        boolean valide,
        @NotNull Long reservationId,
        List<PaiementDto> paiements) {}
