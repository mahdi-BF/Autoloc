package tn.esprit.autoloc.autolocapi.service;

import java.util.List;
import tn.esprit.autoloc.autolocapi.domain.Vehicule;
import tn.esprit.autoloc.autolocapi.web.dto.VehiculeDto;

public interface IVehiculeService {

    Vehicule create(Vehicule vehicule);

    Vehicule findById(Long id);

    List<Vehicule> findAll();

    Vehicule update(Long id, Vehicule vehicule);

    void deleteById(Long id);

    VehiculeDto affecterVehiculeAEquipement(
            Long idVehicule, Long idEquipement);

    VehiculeDto affecterMaintenanceAvehicule(
            String description, String immatriculation);
}