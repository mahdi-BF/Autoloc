package tn.esprit.autoloc.autolocapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloc.autolocapi.domain.Paiement;
import tn.esprit.autoloc.autolocapi.domain.Vehicule;

public interface IPaiementRepository extends JpaRepository<Paiement, Long> {
}
