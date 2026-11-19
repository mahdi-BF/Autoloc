package tn.esprit.autoloc.autolocapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloc.autolocapi.domain.Reservation;
import tn.esprit.autoloc.autolocapi.domain.Vehicule;

import java.time.LocalDate;
import java.util.Optional;

public interface IReservationRepository extends JpaRepository<Reservation, Long> {
    Optional<Reservation> findByDateDebutAndDateFin(
            LocalDate dateDebut,
            LocalDate dateFin
    );
}
