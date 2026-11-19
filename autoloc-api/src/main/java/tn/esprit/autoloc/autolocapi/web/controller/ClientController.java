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
import tn.esprit.autoloc.autolocapi.domain.Client;
import tn.esprit.autoloc.autolocapi.mapper.ClientMapper;
import tn.esprit.autoloc.autolocapi.web.dto.ClientDto;
import tn.esprit.autoloc.autolocapi.service.IClientService;

@Tag(name = "Clients", description = "Gestion des clients")
@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {

    private final IClientService clientService;
    private final ClientMapper apiMapper;

    @Operation(summary = "Lister les clients")
    @ApiResponse(responseCode = "200", description = "Liste des clients")

    @GetMapping
    public List<ClientDto> findAll() {
        return clientService.findAll().stream().map(apiMapper::toDto).toList();
    }

    @Operation(summary = "Consulter un client")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Client trouvé"),
            @ApiResponse(responseCode = "404", description = "Client introuvable")
    })
    @GetMapping("/{id}")
    public ClientDto findById(
            @Parameter(description = "Identifiant du client") @PathVariable Long id) {
        return apiMapper.toDto(clientService.findById(id));
    }

    @Operation(summary = "Créer un client")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Client créé"),
            @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClientDto create(@jakarta.validation.Valid @RequestBody ClientDto dto) {
        return apiMapper.toDto(clientService.create(apiMapper.toEntity(dto)));
    }

    @Operation(summary = "Modifier un client")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Client modifié"),
            @ApiResponse(responseCode = "404", description = "Client introuvable"),
            @ApiResponse(responseCode = "400", description = "Données invalides")

    })
    @PutMapping("/{id}")
    public ClientDto update(
            @Parameter(description = "Identifiant du client") @PathVariable Long id,
            @jakarta.validation.Valid @RequestBody ClientDto dto) {
        return apiMapper.toDto(clientService.update(id, apiMapper.toEntity(dto)));
    }

    @Operation(summary = "Supprimer un client")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Client supprimé"),
            @ApiResponse(responseCode = "404", description = "Client introuvable")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(
            @Parameter(description = "Identifiant du client") @PathVariable Long id) {
        clientService.deleteById(id);
    }
}