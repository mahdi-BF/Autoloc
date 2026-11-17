package tn.esprit.autoloc.autolocapi.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.autoloc.autolocapi.domain.Maintenance;
import tn.esprit.autoloc.autolocapi.mapper.ApiMapper;
import tn.esprit.autoloc.autolocapi.web.dto.MaintenanceDto;
import tn.esprit.autoloc.autolocapi.service.IMaintenanceService;

@Tag(name = "Maintenances", description = "Gestion des maintenances des véhicules")
@RestController
@RequestMapping("/maintenances")
@RequiredArgsConstructor
public class MaintenanceController {

    private final IMaintenanceService maintenanceService;
    private final ApiMapper apiMapper;

    @Operation(summary = "Lister les maintenances")
    @ApiResponse(responseCode = "200", description = "Liste des maintenances")

    @GetMapping
    public List<MaintenanceDto> findAll() {
        return maintenanceService.findAll().stream().map(apiMapper::toDto).toList();
    }

    @Operation(summary = "Consulter une maintenance")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Maintenance trouvée"),
            @ApiResponse(responseCode = "404", description = "Maintenance introuvable")
    })
    @GetMapping("/{id}")
    public MaintenanceDto findById(
            @Parameter(description = "Identifiant de la maintenance") @PathVariable Long id) {
        return apiMapper.toDto(maintenanceService.findById(id));
    }

    @Operation(summary = "Créer une maintenance")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Maintenance créée"),
            @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MaintenanceDto create(@jakarta.validation.Valid @RequestBody MaintenanceDto dto) {
        return apiMapper.toDto(maintenanceService.create(apiMapper.toEntity(dto)));
    }

    @Operation(summary = "Modifier une maintenance")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Maintenance modifiée"),
            @ApiResponse(responseCode = "404", description = "Maintenance introuvable"),
            @ApiResponse(responseCode = "400", description = "Données invalides")

    })
    @PutMapping("/{id}")
    public MaintenanceDto update(
            @Parameter(description = "Identifiant de la maintenance") @PathVariable Long id,
            @jakarta.validation.Valid @RequestBody MaintenanceDto dto) {
        return apiMapper.toDto(maintenanceService.update(id, apiMapper.toEntity(dto)));
    }

    @Operation(summary = "Supprimer une maintenance")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Maintenance supprimée"),
            @ApiResponse(responseCode = "404", description = "Maintenance introuvable")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(
            @Parameter(description = "Identifiant de la maintenance") @PathVariable Long id) {
        maintenanceService.deleteById(id);
    }
}