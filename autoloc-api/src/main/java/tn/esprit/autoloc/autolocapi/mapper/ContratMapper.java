package tn.esprit.autoloc.autolocapi.mapper;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tn.esprit.autoloc.autolocapi.domain.Contrat;
import tn.esprit.autoloc.autolocapi.domain.Paiement;
import tn.esprit.autoloc.autolocapi.domain.Reservation;
import tn.esprit.autoloc.autolocapi.web.dto.ContratDTO;
import tn.esprit.autoloc.autolocapi.web.dto.PaiementDto;

@Component
@RequiredArgsConstructor
public class ContratMapper {
    private final PaiementMapper paiementMapper;

    public ContratDTO toDto(Contrat e) {
        List<PaiementDto> paiements = e.getPaiements() == null ? List.of() : e.getPaiements().stream().map(paiementMapper::toDto).toList();
        return new ContratDTO(e.getIdContrat(), e.getDateSignature(), e.getMontantTotal(), e.isValide(), e.getReservation() == null ? null : e.getReservation().getIdReservation(), paiements);
    }

    public Contrat toEntity(ContratDTO d) {
        Contrat e = new Contrat();
        e.setIdContrat(d.idContrat());
        e.setDateSignature(d.dateSignature());
        e.setMontantTotal(d.montantTotal());
        e.setValide(d.valide());
        if (d.reservationId() != null) {
            Reservation reservation = new Reservation();
            reservation.setIdReservation(d.reservationId());
            e.setReservation(reservation);
        }
        e.setPaiements(d.paiements() == null ? new ArrayList<>() : d.paiements().stream().map(p -> {
            Paiement paiement = paiementMapper.toEntity(p);
            paiement.setContrat(e);
            return paiement;
        }).toList());
        return e;
    }
}
