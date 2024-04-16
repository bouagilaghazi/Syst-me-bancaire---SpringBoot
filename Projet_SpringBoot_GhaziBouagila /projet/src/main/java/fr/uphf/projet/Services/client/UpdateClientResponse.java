package fr.uphf.projet.Services.client;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
@Getter
@Setter
public class UpdateClientResponse {
    private String nom;
    private String prenom;
    private Date date_naissance;
    private String adresse;
    private String telephone;
    private LocalDate dateInscription;
    private Date dateModification;
}
