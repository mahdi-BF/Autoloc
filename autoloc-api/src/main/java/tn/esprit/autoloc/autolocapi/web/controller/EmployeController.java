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
import tn.esprit.autoloc.autolocapi.domain.Employe;
import tn.esprit.autoloc.autolocapi.mapper.ApiMapper;
import tn.esprit.autoloc.autolocapi.web.dto.EmployeDto;
import tn.esprit.autoloc.autolocapi.service.IEmployeService;

@Tag(name = "Employés", description = "Gestion des employés des agences")
@RestController
@RequestMapping("/employes")
@RequiredArgsConstructor
public class EmployeController {

    private final IEmployeService employeService;
    private final ApiMapper apiMapper;

    @Operation(summary = "Lister les employés")
    @ApiResponse(responseCode = "200", description = "Liste des employés")
    @GetMapping
    public List<EmployeDto> findAll() {
        return employeService.findAll().stream().map(apiMapper::toDto).toList();
    }

    @Operation(summary = "Consulter un employé")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Employé trouvé"),
            @ApiResponse(responseCode = "404", description = "Employé introuvable")
    })
    @GetMapping("/{id}")
    public EmployeDto findById(
            @Parameter(description = "Identifiant de l'employé") @PathVariable Long id) {
        return apiMapper.toDto(employeService.findById(id));
    }

    @Operation(summary = "Créer un employé")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Employé créé"),
            @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeDto create(@jakarta.validation.Valid @RequestBody EmployeDto dto) {
        return apiMapper.toDto(employeService.create(apiMapper.toEntity(dto)));
    }

    @Operation(summary = "Modifier un employé")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Employé modifié"),
            @ApiResponse(responseCode = "404", description = "Employé introuvable"),
            @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    @PutMapping("/{id}")
    public EmployeDto update(
            @Parameter(description = "Identifiant de l'employé") @PathVariable Long id,
            @jakarta.validation.Valid @RequestBody EmployeDto dto) {
        return apiMapper.toDto(employeService.update(id, apiMapper.toEntity(dto)));
    }

    @Operation(summary = "Supprimer un employé")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Employé supprimé"),
            @ApiResponse(responseCode = "404", description = "Employé introuvable")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(
            @Parameter(description = "Identifiant de l'employé") @PathVariable Long id) {
        employeService.deleteById(id);
    }
}