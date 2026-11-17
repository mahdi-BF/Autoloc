package tn.esprit.autoloc.autolocapi.mapper;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import tn.esprit.autoloc.autolocapi.domain.*;
import tn.esprit.autoloc.autolocapi.web.dto.*;

/** Converts API DTOs to entities and back; relations cross the API as identifiers. */
@Component
public class ApiMapper {
    public AgenceDto toDto(Agence e) { return new AgenceDto(e.getIdAgence(),e.getNom(),e.getVille(),e.getAdresse(),e.getTelephone(),e.getTarifJournalier()); }
    public Agence toEntity(AgenceDto d) { return new Agence(d.idAgence(),d.nom(),d.ville(),d.adresse(),d.telephone(),d.tarifJournalier(),null,null); }
    public ClientDto toDto(Client e) { return new ClientDto(e.getIdClient(),e.getNom(),e.getPrenom(),e.getEmail(),e.getTelephone(),e.getNumPermis(),e.getDateInscription()); }
    public Client toEntity(ClientDto d) { return new Client(d.idClient(),d.nom(),d.prenom(),d.email(),d.telephone(),d.numPermis(),d.dateInscription(),null); }
    public EmployeDto toDto(Employe e) { return new EmployeDto(e.getIdEmploye(),e.getNom(),e.getPrenom(),e.getRole(),e.getAgence()==null?null:e.getAgence().getIdAgence()); }
    public Employe toEntity(EmployeDto d) { Employe e=new Employe(); e.setIdEmploye(d.idEmploye()); e.setNom(d.nom()); e.setPrenom(d.prenom()); e.setRole(d.role()); if(d.agenceId()!=null){Agence a=new Agence();a.setIdAgence(d.agenceId());e.setAgence(a);} return e; }
    public EquipementDto toDto(Equipement e) { return new EquipementDto(e.getIdEquipement(),e.getLibelle()); }
    public Equipement toEntity(EquipementDto d) { return new Equipement(d.idEquipement(),d.libelle(),null); }
    public VehiculeDto toDto(Vehicule e) { return new VehiculeDto(e.getIdVehicule(),e.getImmatriculation(),e.getMarque(),e.getModele(),e.getCategorie(),e.getTarifJournalier(),e.getStatut(),e.getAgence()==null?null:e.getAgence().getIdAgence(),e.getEquipements()==null?List.of():e.getEquipements().stream().map(Equipement::getIdEquipement).toList()); }
    public Vehicule toEntity(VehiculeDto d) { Vehicule e=new Vehicule();e.setIdVehicule(d.idVehicule());e.setImmatriculation(d.immatriculation());e.setMarque(d.marque());e.setModele(d.modele());e.setCategorie(d.categorie());e.setTarifJournalier(d.tarifJournalier());e.setStatut(d.statut());if(d.agenceId()!=null){Agence a=new Agence();a.setIdAgence(d.agenceId());e.setAgence(a);}if(d.equipementIds()!=null)e.setEquipements(d.equipementIds().stream().map(id->{Equipement x=new Equipement();x.setIdEquipement(id);return x;}).toList());return e; }
    public ReservationDto toDto(Reservation e) { return new ReservationDto(e.getIdReservation(),e.getDateDebut(),e.getDateFin(),e.getStatut(),e.getVehicule()==null?null:e.getVehicule().getIdVehicule(),e.getClient()==null?null:e.getClient().getIdClient(),e.getContrat()==null?null:e.getContrat().getIdContrat()); }
    public Reservation toEntity(ReservationDto d) { Reservation e=new Reservation();e.setIdReservation(d.idReservation());e.setDateDebut(d.dateDebut());e.setDateFin(d.dateFin());e.setStatut(d.statut());Vehicule v=new Vehicule();v.setIdVehicule(d.vehiculeId());e.setVehicule(v);Client c=new Client();c.setIdClient(d.clientId());e.setClient(c);if(d.contratId()!=null){Contrat x=new Contrat();x.setIdContrat(d.contratId());e.setContrat(x);}return e; }
    public MaintenanceDto toDto(Maintenance e) { return new MaintenanceDto(e.getIdMaintenance(),e.getDateDebut(),e.getDateFin(),e.getDescription(),e.getVehicules()==null?null:e.getVehicules().getIdVehicule()); }
    public Maintenance toEntity(MaintenanceDto d) { Maintenance e=new Maintenance();e.setIdMaintenance(d.idMaintenance());e.setDateDebut(d.dateDebut());e.setDateFin(d.dateFin());e.setDescription(d.description());Vehicule v=new Vehicule();v.setIdVehicule(d.vehiculeId());e.setVehicules(v);return e; }
    public PaiementDto toDto(Paiement e) { return new PaiementDto(e.getIdPaiement(),e.getMontant(),e.getDatePaiement(),e.getModePaiement(),e.getContrat()==null?null:e.getContrat().getIdContrat()); }
    public Paiement toEntity(PaiementDto d) { Paiement e=new Paiement();e.setIdPaiement(d.idPaiement());e.setMontant(d.montant());e.setDatePaiement(d.datePaiement());e.setModePaiement(d.modePaiement());return e; }
    public ContratDto toDto(Contrat e) { List<PaiementDto> ps=e.getPaiements()==null?List.of():e.getPaiements().stream().map(this::toDto).toList();return new ContratDto(e.getIdContrat(),e.getDateSignature(),e.getMontantTotal(),e.isValide(),e.getReservation()==null?null:e.getReservation().getIdReservation(),ps); }
    public Contrat toEntity(ContratDto d) { Contrat e=new Contrat();e.setIdContrat(d.idContrat());e.setDateSignature(d.dateSignature());e.setMontantTotal(d.montantTotal());e.setValide(d.valide());Reservation r=new Reservation();r.setIdReservation(d.reservationId());e.setReservation(r);e.setPaiements(d.paiements()==null?new ArrayList<>():d.paiements().stream().map(p->{Paiement x=toEntity(p);x.setContrat(e);return x;}).toList());return e; }
}
