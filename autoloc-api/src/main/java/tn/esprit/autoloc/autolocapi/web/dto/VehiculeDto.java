package tn.esprit.autoloc.autolocapi.web.dto;

import java.math.BigDecimal;
import java.util.List;

import tn.esprit.autoloc.autolocapi.domain.CategorieVehicule;
import tn.esprit.autoloc.autolocapi.domain.StatutVehicule;

public record VehiculeDto(
        Long idVehicule,
        String immatriculation,
        String marque,
        String modele,
        CategorieVehicule categorie,
        BigDecimal tarifJournalier,
        StatutVehicule statut,
        Long agenceId,
        List<Long> equipementIds
) {}