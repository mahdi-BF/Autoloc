package tn.esprit.autoloc.autolocapi.web.dto;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
public record ContratDto(Long idContrat, @NotNull LocalDate dateSignature, @NotNull @DecimalMin(value="0.0", inclusive=false) BigDecimal montantTotal, boolean valide, @NotNull Long reservationId, List<PaiementDto> paiements) { }
