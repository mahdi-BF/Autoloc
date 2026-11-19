package tn.esprit.autoloc.autolocapi.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.autolocapi.domain.Client;
import tn.esprit.autoloc.autolocapi.web.dto.ClientDto;

@Component
public class ClientMapper {
    public ClientDto toDto(Client e) {
        return new ClientDto(e.getIdClient(), e.getNom(), e.getPrenom(), e.getEmail(), e.getTelephone(), e.getNumPermis(), e.getDateInscription());
    }

    public Client toEntity(ClientDto d) {
        return new Client(d.idClient(), d.nom(), d.prenom(), d.email(), d.telephone(), d.numPermis(), d.dateInscription(), null);
    }
}
