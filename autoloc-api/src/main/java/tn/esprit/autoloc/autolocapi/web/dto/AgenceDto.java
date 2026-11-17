package tn.esprit.autoloc.autolocapi.web.dto;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
public record AgenceDto
        (Long idAgence,
         @NotBlank @Size(max=20) String nom,
         @NotBlank @Size(max=50) String ville,
         @NotBlank @Size(max=50) String adresse,
         @NotBlank @Size(max=30) String telephone,
         @NotNull @DecimalMin("0.0") BigDecimal tarifJournalier) { }
