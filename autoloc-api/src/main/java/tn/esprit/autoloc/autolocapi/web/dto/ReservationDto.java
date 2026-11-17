package tn.esprit.autoloc.autolocapi.web.dto;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import tn.esprit.autoloc.autolocapi.domain.StatutReservation;
public record ReservationDto(Long idReservation, @NotNull LocalDate dateDebut, @NotNull LocalDate dateFin, @NotNull StatutReservation statut, @NotNull Long vehiculeId, @NotNull Long clientId, Long contratId) { }
