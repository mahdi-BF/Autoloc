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
import tn.esprit.autoloc.autolocapi.domain.Agence;
import tn.esprit.autoloc.autolocapi.mapper.ApiMapper;
import tn.esprit.autoloc.autolocapi.web.dto.AgenceDto;
import tn.esprit.autoloc.autolocapi.service.IAgenceService;

@Tag(name = "Agences", description = "Gestion des agences de location")
@RestController
@RequestMapping("/agences")
@RequiredArgsConstructor
public class AgenceController {

    private final IAgenceService agenceService;
    private final ApiMapper apiMapper;

    @Operation(summary = "Lister les agences")
    @ApiResponse(responseCode = "200", description = "Liste des agences")

    @GetMapping
    public List<AgenceDto> findAll() {
        return agenceService.findAll().stream().map(apiMapper::toDto).toList();
    }

    @Operation(summary = "Consulter une agence")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Agence trouvée"),
            @ApiResponse(responseCode = "404", description = "Agence introuvable")
    })
    @GetMapping("/{id}")
    public AgenceDto findById(
            @Parameter(description = "Identifiant de l'agence") @PathVariable Long id) {
        return apiMapper.toDto(agenceService.findById(id));
    }

    @Operation(summary = "Créer une agence")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Agence créée"),
            @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AgenceDto create(@jakarta.validation.Valid @RequestBody AgenceDto dto) {
        return apiMapper.toDto(agenceService.create(apiMapper.toEntity(dto)));
    }

    @Operation(summary = "Modifier une agence")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Agence modifiée"),
            @ApiResponse(responseCode = "404", description = "Agence introuvable"),
            @ApiResponse(responseCode = "400", description = "Données invalides")

    })
    @PutMapping("/{id}")
    public AgenceDto update(
            @Parameter(description = "Identifiant de l'agence") @PathVariable Long id,
            @jakarta.validation.Valid @RequestBody AgenceDto dto) {
        return apiMapper.toDto(agenceService.update(id, apiMapper.toEntity(dto)));
    }

    @Operation(summary = "Supprimer une agence")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Agence supprimée"),
            @ApiResponse(responseCode = "404", description = "Agence introuvable")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(
            @Parameter(description = "Identifiant de l'agence") @PathVariable Long id) {
        agenceService.deleteById(id);
    }
}