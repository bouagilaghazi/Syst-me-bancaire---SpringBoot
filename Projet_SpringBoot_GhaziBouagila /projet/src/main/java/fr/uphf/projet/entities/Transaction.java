// src/main/java/fr/uphf/projet/entities/Transaction.java
package fr.uphf.projet.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name="transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id ;
    private double montant ;
    @Enumerated(EnumType.STRING)
    private TypeTransaction typeTransaction ;
    private String typeSource ;
    private String idSource ;
    @ManyToOne
    @JoinColumn(name="IBAN")
    private Compte compte ;
}