package tn.esprit.autoloc.autolocapi.service;

import java.util.List;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.autolocapi.domain.Vehicule;
import tn.esprit.autoloc.autolocapi.exception.ResourceNotFoundException;
import tn.esprit.autoloc.autolocapi.repository.IVehiculeRepository;
import tn.esprit.autoloc.autolocapi.domain.Equipement;
import tn.esprit.autoloc.autolocapi.domain.Maintenance;
import tn.esprit.autoloc.autolocapi.repository.IEquipementRepository;
import tn.esprit.autoloc.autolocapi.repository.IMaintenanceRepository;
import tn.esprit.autoloc.autolocapi.mapper.VehiculeMapper;
import tn.esprit.autoloc.autolocapi.web.dto.VehiculeDto;

@Service
@RequiredArgsConstructor
public class VehiculeServiceImpl implements IVehiculeService {
    private final IEquipementRepository equipementRepository;
    private final IMaintenanceRepository maintenanceRepository;
    private final VehiculeMapper vehiculeMapper;


    private final IVehiculeRepository vehiculeRepository;
    @Override
    @Transactional
    public VehiculeDto affecterVehiculeAEquipement(
            Long idVehicule, Long idEquipement) {

        Vehicule vehicule = findById(idVehicule);

        Equipement equipement = equipementRepository.findById(idEquipement)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Equipement", idEquipement));

        if (vehicule.getEquipements() == null) {
            vehicule.setEquipements(new java.util.ArrayList<>());
        }
        if (vehicule.getEquipements().stream().noneMatch(existing ->
                existing.getIdEquipement().equals(equipement.getIdEquipement()))) {
            vehicule.getEquipements().add(equipement);
        }

        Vehicule saved = vehiculeRepository.save(vehicule);

        return vehiculeMapper.toDto(saved);
    }
    @Override
    @Transactional
    public VehiculeDto affecterMaintenanceAvehicule(
            String description, String immatriculation) {

        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("La description de la maintenance est obligatoire");
        }
        if (immatriculation == null || immatriculation.isBlank()) {
            throw new IllegalArgumentException("L'immatriculation est obligatoire");
        }

        Vehicule vehicule = vehiculeRepository
                .findByImmatriculation(immatriculation)
                .orElseThrow(() ->
                        new IllegalArgumentException("Véhicule introuvable"));

        Maintenance maintenance = new Maintenance();
        maintenance.setDescription(description);
        maintenance.setDateDebut(java.time.LocalDate.now());
        maintenance.setVehicules(vehicule);

        maintenanceRepository.save(maintenance);

        return vehiculeMapper.toDto(vehicule);
    }

    @Override
    public Vehicule create(Vehicule vehicule) {
        if (vehicule.getIdVehicule() != null) {
            throw new IllegalArgumentException(
                    "Un nouveau véhicule ne doit pas avoir d'identifiant");
        }
        verifier(vehicule);
        return vehiculeRepository.save(vehicule);
    }

    @Override
    public Vehicule findById(Long id) {
        return vehiculeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", id));
    }

    @Override

    public List<Vehicule> findAll() {
        return vehiculeRepository.findAll();
    }

    @Override
    public Vehicule update(Long id, Vehicule vehicule) {
        Vehicule existant = findById(id);
        verifier(vehicule);
        existant.setImmatriculation(vehicule.getImmatriculation());
        existant.setMarque(vehicule.getMarque());
        existant.setModele(vehicule.getModele());
        existant.setCategorie(vehicule.getCategorie());
        existant.setTarifJournalier(vehicule.getTarifJournalier());
        existant.setStatut(vehicule.getStatut());
        existant.setAgence(vehicule.getAgence());
        // reservations et equipements ne sont pas modifiés ici
        return vehiculeRepository.save(existant);
    }

    @Override
    public void deleteById(Long id) {
        if (!vehiculeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Vehicule", id);
        }
        vehiculeRepository.deleteById(id);
    }

    private void verifier(Vehicule vehicule) {
        if (estVide(vehicule.getImmatriculation()) || estVide(vehicule.getMarque())
                || estVide(vehicule.getModele())) {
            throw new IllegalArgumentException(
                    "L'immatriculation, la marque et le modèle sont obligatoires");

        }
        if (vehicule.getCategorie() == null || vehicule.getStatut() == null) {
            throw new IllegalArgumentException("La catégorie et le statut sont obligatoires");
        }
        if (vehicule.getTarifJournalier() == null
                || vehicule.getTarifJournalier().signum() < 0) {
            throw new IllegalArgumentException(
                    "Le tarif journalier est obligatoire et ne peut pas être négatif");
        }
    }

    private boolean estVide(String valeur) {
        return valeur == null || valeur.isBlank();
    }
}
