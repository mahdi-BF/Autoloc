package tn.esprit.autoloc.autolocapi.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Equipement { @Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long idEquipement;

    private String libelle;
    @JsonIgnore
    @ManyToMany(mappedBy = "equipements")
    private List<Vehicule> vehicules;

}
