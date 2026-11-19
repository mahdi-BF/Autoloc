package tn.esprit.autoloc.autolocapi.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.autolocapi.domain.Reservation;
import tn.esprit.autoloc.autolocapi.mapper.ReservationMapper;
import tn.esprit.autoloc.autolocapi.web.dto.ReservationRequestDTO;
import tn.esprit.autoloc.autolocapi.service.IReservationService;
import tn.esprit.autoloc.autolocapi.web.dto.ReservationResponseDTO;
import tn.esprit.autoloc.autolocapi.web.dto.ReservationWithContratRequestDTO;

@Tag(name = "Réservations", description = "Gestion des réservations de véhicules")
@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final IReservationService reservationService;
    private final ReservationMapper reservationMapper;

    @Operation(summary = "Lister les réservations")
    @ApiResponse(responseCode = "200", description = "Liste des réservations")


    @GetMapping
    public List<ReservationResponseDTO> findAll() {
        return reservationService.findAll().stream().map(reservationMapper::toResponse).toList();
    }

    @Operation(summary = "Consulter une réservation")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Réservation trouvée"),
            @ApiResponse(responseCode = "404", description = "Réservation introuvable")
    })
    @GetMapping("/{id}")
    public ReservationResponseDTO findById(
            @Parameter(description = "Identifiant de la réservation") @PathVariable Long id) {
        return reservationMapper.toResponse(reservationService.findById(id));
    }

    @Operation(summary = "Créer une réservation")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Réservation créée"),
            @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationResponseDTO create(@jakarta.validation.Valid @RequestBody ReservationRequestDTO dto) {
        return reservationMapper.toResponse(reservationService.create(reservationMapper.toEntity(dto)));
    }

    @Operation(summary = "Modifier une réservation")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Réservation modifiée"),
            @ApiResponse(responseCode = "404", description = "Réservation introuvable"),
            @ApiResponse(responseCode = "400", description = "Données invalides")

    })
    @PutMapping("/{id}")
    public ReservationResponseDTO update(
            @Parameter(description = "Identifiant de la réservation") @PathVariable Long id,
            @jakarta.validation.Valid @RequestBody ReservationRequestDTO dto) {
        return reservationMapper.toResponse(reservationService.update(id, reservationMapper.toEntity(dto)));
    }

    @Operation(summary = "Supprimer une réservation")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Réservation supprimée"),
            @ApiResponse(responseCode = "404", description = "Réservation introuvable")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(
            @Parameter(description = "Identifiant de la réservation") @PathVariable Long id) {
        reservationService.deleteById(id);
    }
    @PostMapping("/{idReservation}/affecter-client")
    public ReservationResponseDTO affecterReservationAClient(
            @PathVariable Long idReservation,
            @RequestParam String nom,
            @RequestParam String prenom) {

        return reservationService.affecterReservationAClient(
                idReservation, nom, prenom);
    }
    @PostMapping("/avec-contrat")
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationResponseDTO creerReservationEtContratAssocie(
            @jakarta.validation.Valid @RequestBody ReservationWithContratRequestDTO requestDTO) {

        return reservationService.creerReservationEtContratAssocie(requestDTO);
    }
}
