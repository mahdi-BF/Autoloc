package tn.esprit.autoloc.autolocapi.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.autolocapi.domain.Maintenance;
import tn.esprit.autoloc.autolocapi.domain.Vehicule;
import tn.esprit.autoloc.autolocapi.web.dto.MaintenanceDto;

@Component
public class MaintenanceMapper {
    public MaintenanceDto toDto(Maintenance e) {
        return new MaintenanceDto(e.getIdMaintenance(), e.getDateDebut(), e.getDateFin(), e.getDescription(), e.getVehicules() == null ? null : e.getVehicules().getIdVehicule());
    }

    public Maintenance toEntity(MaintenanceDto d) {
        Maintenance e = new Maintenance();
        e.setIdMaintenance(d.idMaintenance());
        e.setDateDebut(d.dateDebut());
        e.setDateFin(d.dateFin());
        e.setDescription(d.description());
        if (d.vehiculeId() != null) {
            Vehicule vehicule = new Vehicule();
            vehicule.setIdVehicule(d.vehiculeId());
            e.setVehicules(vehicule);
        }
        return e;
    }
}
