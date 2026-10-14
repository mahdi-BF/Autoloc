

package tn.esprit.autoloc.autolocapi.service;

import tn.esprit.autoloc.autolocapi.domain.Paiement;
import java.util.List;




public interface IPaiementService {

    Paiement findById(Long id);

    List<Paiement> findAll();
}