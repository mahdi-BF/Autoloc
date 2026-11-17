package tn.esprit.autoloc.autolocapi.web.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import tn.esprit.autoloc.autolocapi.domain.RoleEmploye;
public record EmployeDto(Long idEmploye, @NotBlank String nom, @NotBlank String prenom, @NotNull RoleEmploye role, Long agenceId) { }
