package tn.esprit.autoloc.autolocapi.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.autolocapi.domain.Equipement;
import tn.esprit.autoloc.autolocapi.web.dto.EquipementDto;

@Component
public class EquipementMapper {
    public EquipementDto toDto(Equipement e) {
        return new EquipementDto(e.getIdEquipement(), e.getLibelle());
    }

    public Equipement toEntity(EquipementDto d) {
        return new Equipement(d.idEquipement(), d.libelle(), null);
    }
}
