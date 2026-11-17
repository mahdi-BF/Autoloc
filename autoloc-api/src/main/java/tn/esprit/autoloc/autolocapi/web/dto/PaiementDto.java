package tn.esprit.autoloc.autolocapi.web.dto;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import tn.esprit.autoloc.autolocapi.domain.ModePaiement;
public record PaiementDto(Long idPaiement, @NotNull @DecimalMin("0.0") BigDecimal montant, @NotNull LocalDate datePaiement, @NotNull ModePaiement modePaiement, Long contratId) { }
