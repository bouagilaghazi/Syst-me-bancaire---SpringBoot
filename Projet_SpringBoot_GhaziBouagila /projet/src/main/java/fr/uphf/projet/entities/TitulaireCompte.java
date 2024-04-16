package fr.uphf.projet.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="titulaire_compte")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TitulaireCompte {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id ;
    @ManyToOne
    @JoinColumn(name="id_client")
    private Client client ;
    @ManyToOne
    @JoinColumn(name="IBAN")
    private Compte compte ;

}
