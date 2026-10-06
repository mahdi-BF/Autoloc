Atelier 3 : Notes sur la couche Repository
Projet : `autoloc-api` | Package : `tn.esprit.autoloc.autolocapi.repository`
1. Choix des interfaces
   Les neuf repositories étendent `JpaRepository<Entité, Long>`. Cette interface regroupe le CRUD (`CrudRepository`), les retours en `List` (`ListCrudRepository`), le tri et la pagination (`PagingAndSortingRepository`) ainsi que les méthodes propres à JPA (`flush`, `saveAndFlush`, `getReferenceById`, suppression en lot). Spring Data génère le proxy au démarrage : l'annotation `@Repository` n'est pas nécessaire. Les interfaces sont volontairement vides, sans méthode personnalisée.
   Interface	Étend	Justification
   IAgenceRepository	JpaRepository<Agence, Long>	CRUD complet, `findAll` renvoie une `List`, tri et pagination disponibles.
   IEmployeRepository	JpaRepository<Employe, Long>	CRUD complet, `findAll` renvoie une `List`, tri et pagination disponibles.
   IVehiculeRepository	JpaRepository<Vehicule, Long>	CRUD complet, pagination et tri utiles pour une liste de véhicules.
   IEquipementRepository	JpaRepository<Equipement, Long>	CRUD complet, `findAll` renvoie une `List`.
   IClientRepository	JpaRepository<Client, Long>	CRUD complet, `findAll` renvoie une `List`, pagination disponible.
   IReservationRepository	JpaRepository<Reservation, Long>	CRUD complet, tri et pagination disponibles.
   IContratRepository	JpaRepository<Contrat, Long>	CRUD complet, `saveAndFlush` disponible. Les suppressions classiques passent par le contexte de persistance : la cascade et l'orphanRemoval s'appliquent aux paiements.
   IPaiementRepository	JpaRepository<Paiement, Long>	Permet de lire les paiements. La création et la suppression d'un paiement passent par le Contrat (composition).
   IMaintenanceRepository	JpaRepository<Maintenance, Long>	CRUD complet, `findAll` renvoie une `List`.
   Remarque : les suppressions en lot (`deleteAllInBatch`, `deleteAllByIdInBatch`) contournent le contexte de persistance. Elles ne sont pas utilisées sur `Contrat`, car la cascade et l'orphanRemoval ne s'appliqueraient pas (risque de paiements orphelins ou d'erreur de clé étrangère).
2. Anomalies relevées et corrigées
   Anomalie	Règle / explication	Correction apportée
   Méthode `retrieveAllClients()` déclarée dans `IClientRepository` : l'application ne démarrait pas (`No property 'retrieveAllClients' found for type 'Client'`).	Spring Data déduit la requête du nom de la méthode et n'accepte que des préfixes connus (`findBy`, `countBy`, `existsBy`...). Une méthode de type service n'a pas sa place dans la couche repository.	Méthode supprimée : `findAll()` fourni par `JpaRepository` fait le même travail. Les méthodes métier seront écrites dans la couche service (Atelier 4).
   Imports inutilisés dans les interfaces et entités.	Code mort : un import non utilisé alourdit le fichier et nuit à la lisibilité (règle Clean Code).	Imports inutilisés supprimés (Optimize Imports).
   Usage de `@Data` de Lombok sur une entité (le cas échéant).	`@Data` génère `equals`, `hashCode` et `toString` sur tous les attributs, y compris les associations : risque de boucles infinies et de chargements inutiles.	Remplacé par `@Getter` et `@Setter` ciblés.
3. Vérification
   Les neuf interfaces `I…Repository` sont dans le package `repository` et étendent `JpaRepository<Entité, Long>`.
   Au démarrage, les logs indiquent `Found 9 JPA repository interfaces`.