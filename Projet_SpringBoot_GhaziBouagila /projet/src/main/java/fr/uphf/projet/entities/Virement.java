
package fr.uphf.projet.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name="virement")
public class Virement {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long idVirement;

    @ManyToOne
    @JoinColumn(name="IBAN_emetteur")
    private Compte compteEmetteur;

    @ManyToOne
    @JoinColumn(name="IBAN_beneficiaire")
    private Compte compteBeneficiaire;

    private double montant;
    private String libelleVirement;
    private LocalDateTime dateCreation;
}