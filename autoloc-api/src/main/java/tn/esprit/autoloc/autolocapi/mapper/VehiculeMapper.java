package tn.esprit.autoloc.autolocapi.mapper;

import java.util.List;
import org.springframework.stereotype.Component;
import tn.esprit.autoloc.autolocapi.domain.Agence;
import tn.esprit.autoloc.autolocapi.domain.Equipement;
import tn.esprit.autoloc.autolocapi.domain.Vehicule;
import tn.esprit.autoloc.autolocapi.web.dto.VehiculeDto;

@Component
public class VehiculeMapper {
    public VehiculeDto toDto(Vehicule e) {
        return new VehiculeDto(e.getIdVehicule(), e.getImmatriculation(), e.getMarque(), e.getModele(), e.getCategorie(), e.getTarifJournalier(), e.getStatut(),
                e.getAgence() == null ? null : e.getAgence().getIdAgence(),
                e.getEquipements() == null ? List.of() : e.getEquipements().stream().map(Equipement::getIdEquipement).toList());
    }

    public Vehicule toEntity(VehiculeDto d) {
        Vehicule e = new Vehicule();
        e.setIdVehicule(d.idVehicule());
        e.setImmatriculation(d.immatriculation());
        e.setMarque(d.marque());
        e.setModele(d.modele());
        e.setCategorie(d.categorie());
        e.setTarifJournalier(d.tarifJournalier());
        e.setStatut(d.statut());
        if (d.agenceId() != null) {
            Agence agence = new Agence();
            agence.setIdAgence(d.agenceId());
            e.setAgence(agence);
        }
        if (d.equipementIds() != null) {
            e.setEquipements(d.equipementIds().stream().map(id -> {
                Equipement equipement = new Equipement();
                equipement.setIdEquipement(id);
                return equipement;
            }).toList());
        }
        return e;
    }
}
