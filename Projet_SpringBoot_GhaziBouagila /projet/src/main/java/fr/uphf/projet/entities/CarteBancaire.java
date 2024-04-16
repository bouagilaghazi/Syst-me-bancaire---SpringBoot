package fr.uphf.projet.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name="carte_bancaire")
public class CarteBancaire {
    @Id
    private String numeroCarte;
    private String CodeSecret;
    private LocalDateTime dateExpiration;

    @ManyToOne
    @JoinColumn(name="titulaireCompte")
    private Client titulaire;

    @ManyToOne
    @JoinColumn(name="IBAN")
    private Compte compte;

}
