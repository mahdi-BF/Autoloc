package tn.esprit.autoloc.autolocapi.web.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
public record ClientDto(Long idClient, @NotBlank String nom, String prenom, @NotBlank @Email String email, String telephone, String numPermis, LocalDate dateInscription) { }
