package tn.esprit.autoloc.autolocapi.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.autolocapi.domain.Paiement;
import tn.esprit.autoloc.autolocapi.web.dto.PaiementDto;

@Component
public class PaiementMapper {
    public PaiementDto toDto(Paiement e) {
        return new PaiementDto(e.getIdPaiement(), e.getMontant(), e.getDatePaiement(), e.getModePaiement(), e.getContrat() == null ? null : e.getContrat().getIdContrat());
    }

    public Paiement toEntity(PaiementDto d) {
        Paiement e = new Paiement();
        e.setIdPaiement(d.idPaiement());
        e.setMontant(d.montant());
        e.setDatePaiement(d.datePaiement());
        e.setModePaiement(d.modePaiement());
        return e;
    }
}
