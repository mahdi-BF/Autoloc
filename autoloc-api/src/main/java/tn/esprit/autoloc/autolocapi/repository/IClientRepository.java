package tn.esprit.autoloc.autolocapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloc.autolocapi.domain.Client;
import tn.esprit.autoloc.autolocapi.domain.Vehicule;

import java.util.List;

public interface IClientRepository extends JpaRepository<Client, Long> {


}
