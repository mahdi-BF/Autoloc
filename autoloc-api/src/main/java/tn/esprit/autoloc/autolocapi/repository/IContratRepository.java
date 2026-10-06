package tn.esprit.autoloc.autolocapi.repository;

import org.springframework.data.repository.CrudRepository;
import tn.esprit.autoloc.autolocapi.domain.Contrat;
public interface IContratRepository extends CrudRepository<Contrat, Long> {
}
