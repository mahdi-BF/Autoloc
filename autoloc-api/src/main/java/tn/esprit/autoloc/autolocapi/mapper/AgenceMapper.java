package tn.esprit.autoloc.autolocapi.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.autolocapi.domain.Agence;
import tn.esprit.autoloc.autolocapi.web.dto.AgenceDto;

@Component
public class AgenceMapper {
    public AgenceDto toDto(Agence e) {
        return new AgenceDto(e.getIdAgence(), e.getNom(), e.getVille(), e.getAdresse(), e.getTelephone(), e.getTarifJournalier());
    }

    public Agence toEntity(AgenceDto d) {
        return new Agence(d.idAgence(), d.nom(), d.ville(), d.adresse(), d.telephone(), d.tarifJournalier(), null, null);
    }
}
