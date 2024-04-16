package fr.uphf.projet.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name="compte")
public class Compte {
    @Id
    private String IBAN ;
    @Column(name="solde", nullable=false, length=50)
    private double solde ;
    @Column(name="intitule_compte", nullable=false, length=50)
    private String intituleCompte ;
    @Column(name="type_compte", nullable=false, length=50)
    @Enumerated(EnumType.STRING)
    private TypeCompte typeCompte ;
    @OneToMany(mappedBy="compte")
    private List<CarteBancaire> carteBancaires ;
    @Column(name="date_creation", nullable=false, length=50)
    private LocalDateTime dateCreation ;
    @OneToMany(mappedBy="compte")
    private List<Transaction> transactions ;
    @OneToMany(mappedBy="compte")
    private List<TitulaireCompte> titulairesCompte ;
}
