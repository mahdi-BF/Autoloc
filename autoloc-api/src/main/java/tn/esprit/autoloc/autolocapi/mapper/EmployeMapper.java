package tn.esprit.autoloc.autolocapi.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.autolocapi.domain.Agence;
import tn.esprit.autoloc.autolocapi.domain.Employe;
import tn.esprit.autoloc.autolocapi.web.dto.EmployeDto;

@Component
public class EmployeMapper {
    public EmployeDto toDto(Employe e) {
        return new EmployeDto(e.getIdEmploye(), e.getNom(), e.getPrenom(), e.getRole(), e.getAgence() == null ? null : e.getAgence().getIdAgence());
    }

    public Employe toEntity(EmployeDto d) {
        Employe e = new Employe();
        e.setIdEmploye(d.idEmploye());
        e.setNom(d.nom());
        e.setPrenom(d.prenom());
        e.setRole(d.role());
        if (d.agenceId() != null) {
            Agence agence = new Agence();
            agence.setIdAgence(d.agenceId());
            e.setAgence(agence);
        }
        return e;
    }
}
