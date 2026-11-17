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
import tn.esprit.autoloc.autolocapi.domain.Equipement;
import tn.esprit.autoloc.autolocapi.mapper.ApiMapper;
import tn.esprit.autoloc.autolocapi.web.dto.EquipementDto;
import tn.esprit.autoloc.autolocapi.service.IEquipementService;

@Tag(name = "Équipements", description = "Gestion des équipements des véhicules")
@RestController
@RequestMapping("/equipements")
@RequiredArgsConstructor
public class EquipementController {

    private final IEquipementService equipementService;
    private final ApiMapper apiMapper;

    @Operation(summary = "Lister les équipements")
    @ApiResponse(responseCode = "200", description = "Liste des équipements")
    @GetMapping
    public List<EquipementDto> findAll() {
        return equipementService.findAll().stream().map(apiMapper::toDto).toList();
    }

    @Operation(summary = "Consulter un équipement")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Équipement trouvé"),
            @ApiResponse(responseCode = "404", description = "Équipement introuvable")
    })
    @GetMapping("/{id}")
    public EquipementDto findById(
            @Parameter(description = "Identifiant de l'équipement") @PathVariable Long id) {
        return apiMapper.toDto(equipementService.findById(id));
    }

    @Operation(summary = "Créer un équipement")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Équipement créé"),
            @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EquipementDto create(@jakarta.validation.Valid @RequestBody EquipementDto dto) {
        return apiMapper.toDto(equipementService.create(apiMapper.toEntity(dto)));
    }

    @Operation(summary = "Modifier un équipement")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Équipement modifié"),
            @ApiResponse(responseCode = "404", description = "Équipement introuvable"),
            @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    @PutMapping("/{id}")
    public EquipementDto update(
            @Parameter(description = "Identifiant de l'équipement") @PathVariable Long id,
            @jakarta.validation.Valid @RequestBody EquipementDto dto) {
        return apiMapper.toDto(equipementService.update(id, apiMapper.toEntity(dto)));
    }

    @Operation(summary = "Supprimer un équipement")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Équipement supprimé"),
            @ApiResponse(responseCode = "404", description = "Équipement introuvable")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(
            @Parameter(description = "Identifiant de l'équipement") @PathVariable Long id) {
        equipementService.deleteById(id);
    }
}