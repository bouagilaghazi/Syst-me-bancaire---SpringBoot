package fr.uphf.projet.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name="client")
public class Client {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id_client", nullable=false, length=50)
    private Long id ;
    @Column(name="nom", nullable=false, length=50)
    private String nom ;
    @Column(name="prenom", nullable=false, length=50)
    private String prenom ;
    @Column(name="email", nullable=false, length=50)
    private String adresse ;
    @Column(name="date_naissance", nullable=false, length=50)
    private Date date_naissance ;
    @Column(name="date_inscription", nullable=false, length=50)
    private LocalDate date_inscription ;
    private String telephone ;


}
