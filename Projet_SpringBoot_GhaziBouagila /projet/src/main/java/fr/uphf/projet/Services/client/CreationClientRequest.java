package fr.uphf.projet.Services.client;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
@Getter
@Setter
public class CreationClientRequest {
    private String nom;
    private String prenom;
    private Date date_naissance;
    private LocalDate date_inscription = LocalDate.now();
    private String adresse;
    private String telephone;

}
