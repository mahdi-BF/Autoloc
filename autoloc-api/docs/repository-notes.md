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
# Atelier 4 : Notes sur la couche Service

## A. Modes d'injection comparés

| Critère | Constructeur | Attribut |
|---|---|---|
| Champ `final` possible ? | Oui | Non |
| Dépendance visible dans la signature de la classe ? | Oui | Non (cachée) |
| Utilisable avec `new` hors conteneur sans dépendance `null` ? | Oui : le repository est obligatoire à la construction | Non : le champ reste `null` |
| Signalé par SonarQube for IDE ? | Non | Oui (règle S6813) |

Convention retenue : injection par constructeur (`@RequiredArgsConstructor` + champ `final`), car l'objet ne peut jamais exister dans un état incomplet.
## B. Services

Tous les services sont des beans `@Service`, singletons sans état. Ils reçoivent leur repository par **injection par constructeur** (`@RequiredArgsConstructor` + champ `final`), typé par l'interface du repository.

| Service | Dépendances injectées | Mode | Justification |
|---|---|---|---|
| ContratServiceImpl | IContratRepository | Constructeur | CRUD complet. Refuse un montant total négatif. La suppression d'un contrat supprime ses paiements par cascade. |
| AgenceServiceImpl | IAgenceRepository | Constructeur | CRUD complet. Refuse un nom, une ville, une adresse ou un téléphone vide, et un tarif journalier négatif. |
| EmployeServiceImpl | IEmployeRepository | Constructeur | CRUD complet. Refuse un nom ou un prénom vide, et un rôle absent. |
| VehiculeServiceImpl | IVehiculeRepository | Constructeur | CRUD complet. Refuse les champs obligatoires vides et un tarif journalier négatif. |
| EquipementServiceImpl | IEquipementRepository | Constructeur | CRUD complet. Refuse un équipement sans libellé ou avec une quantité négative. |
| ClientServiceImpl | IClientRepository | Constructeur | CRUD complet. Refuse un client dont le nom ou l'e-mail est vide. |
| ReservationServiceImpl | IReservationRepository | Constructeur | CRUD complet. Refuse une date de fin antérieure à la date de début. |
| PaiementServiceImpl | IPaiementRepository | Constructeur | Lecture seule (`findById`, `findAll`). La création, la modification et la suppression passent par le Contrat (composition). |
| MaintenanceServiceImpl | IMaintenanceRepository | Constructeur | CRUD complet. Refuse une date de fin antérieure à la date de début. |


## C. Messages d'erreur du conteneur

### Message 1
- **Exception** : `NoSuchBeanDefinitionException` (aucun bean du type demandé).
- **Bean concerné** : `ContratController` demande un bean de type `IContratService`.
- **Causes probables** : (1) l'annotation `@Service` est absente de `ContratServiceImpl` ; (2) la classe est placée en dehors du package de scan (`tn.esprit.autoloc.autolocapi` et ses sous-packages).
- **Correction** : ajouter `@Service` sur `ContratServiceImpl` et vérifier son package.

### Message 2
- **Exception** : `NoUniqueBeanDefinitionException` (deux beans du même type).
- **Bean concerné** : `NotificationController` ; candidats : `emailNotificateur` et `smsNotificateur`.
- **Correction 1** : `@Primary` sur la **classe** du bean choisi par défaut (ex. `EmailNotificateur`).
- **Correction 2** : `@Qualifier("emailNotificateur")` sur le **paramètre du constructeur** du contrôleur.

### Message 3
- **Exception** : dépendance circulaire (`BeanCurrentlyInCreationException`) entre `clientServiceImpl` et `reservationServiceImpl`.
- **Pourquoi c'est impossible à contourner avec le constructeur** : chaque service a besoin de l'autre pour être construit, donc aucun des deux ne peut être créé en premier.
- **Piste de conception** : extraire la responsabilité commune dans un troisième service, ou faire dépendre `ReservationServiceImpl` de `IClientRepository` plutôt que de `ClientServiceImpl`.

## D. Anomalies SonarQube for IDE

L'analyse initiale des packages `service` et `exception` n'a signalé aucune anomalie.
Pour illustrer le rôle de l'outil, trois anomalies ont été provoquées volontairement,
observées dans le panneau Findings, puis corrigées.

| Anomalie | Règle / explication | Correction apportée |
|---|---|---|
| Injection par attribut (`@Autowired` sur le champ) dans `ClientServiceImpl` | S6813 : le champ ne peut pas être `final`, la dépendance est cachée et reste `null` si la classe est créée hors du conteneur. | Remplacée par l'injection par constructeur (`@RequiredArgsConstructor` + champ `final`). |
| `RuntimeException` levée dans `ContratServiceImpl.findById` | S112 : une exception générique ne permet pas de distinguer l'erreur et d'y répondre précisément. | Remplacée par l'exception métier `ResourceNotFoundException`. |
| `System.out.println` dans `AgenceServiceImpl.findAll` | S106 : la sortie standard doit être remplacée par un logger. | Ligne supprimée. |

## E. Questions de compréhension

**1. Où est le `new` de `ContratServiceImpl` et qui l'exécute ?**
Il n'est écrit nulle part dans notre code. Au démarrage, le conteneur Spring détecte la classe grâce à `@Service` (scan des composants), appelle lui-même son constructeur et lui fournit le proxy de `IContratRepository`.

**2. Pourquoi un contrôleur dépendra-t-il de `IContratService` et non de `ContratServiceImpl` ?**
Pour dépendre d'une abstraction : on peut changer ou remplacer l'implémentation (ou fournir une fausse pour un test) sans modifier le contrôleur. Le couplage est faible.

**3. Pourquoi un service singleton doit-il rester sans état ? Exemple de bug.**
Une seule instance est partagée par toutes les requêtes et tous les threads. Exemple : si le service a un attribut `Contrat contratCourant` modifié à chaque appel, deux requêtes simultanées se l'écrasent : le client A peut recevoir ou modifier le contrat du client B.

**4. Pourquoi `update` charge-t-il l'entité existante avant d'appeler `save` ?**
Passer directement l'objet reçu à `save` déclenche un merge qui écrase toutes les colonnes (les champs non envoyés deviennent `null`) et, avec l'orphanRemoval, peut vider la collection de paiements. En chargeant l'existant puis en recopiant seulement les champs modifiables, on garde l'identifiant et les relations intacts. Cela permet aussi de lever `ResourceNotFoundException` si l'id n'existe pas.

**5. Différences demandées.**
- **`@Component` / `@Bean`** : `@Component` se pose sur une classe qu'on écrit, détectée par le scan. `@Bean` se pose sur une méthode d'une classe `@Configuration` et sert à déclarer un bean pour une classe qu'on ne peut pas annoter (bibliothèque, JDK) ou à construction particulière.
- **`@Primary` / `@Qualifier`** : `@Primary` se pose sur la classe et désigne le candidat par défaut quand plusieurs beans ont le même type. `@Qualifier("nom")` se pose à l'injection (paramètre) et choisit explicitement le bean voulu.
- **`IPaiementService` en lecture seule** : `Paiement` est un enfant en composition de `Contrat`. Il ne doit être créé, modifié ou supprimé qu'à travers son parent (cascade et orphanRemoval), ce qui garde le contrat et ses paiements cohérents.