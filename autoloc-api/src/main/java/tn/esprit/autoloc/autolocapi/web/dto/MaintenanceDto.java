package tn.esprit.autoloc.autolocapi.web.dto;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
public record MaintenanceDto(Long idMaintenance, @NotNull LocalDate dateDebut, @NotNull LocalDate dateFin, String description, @NotNull Long vehiculeId) { }
