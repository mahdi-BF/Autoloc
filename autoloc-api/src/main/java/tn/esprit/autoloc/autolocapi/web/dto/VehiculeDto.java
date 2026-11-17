package tn.esprit.autoloc.autolocapi.web.dto;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import tn.esprit.autoloc.autolocapi.domain.CategorieVehicule;
import tn.esprit.autoloc.autolocapi.domain.StatutVehicule;
public record VehiculeDto(Long idVehicule, @NotBlank String immatriculation, @NotBlank String marque, @NotBlank String modele, @NotNull CategorieVehicule categorie, @NotNull @DecimalMin("0.0") BigDecimal tarifJournalier, @NotNull StatutVehicule statut, Long agenceId, List<Long> equipementIds) { }
