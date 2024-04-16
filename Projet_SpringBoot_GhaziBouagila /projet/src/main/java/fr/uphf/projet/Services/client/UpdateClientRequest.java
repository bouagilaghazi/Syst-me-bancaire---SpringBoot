package fr.uphf.projet.Services.client;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class UpdateClientRequest {
    private String nom;
    private String prenom;
    private Date date_naissance;
    private String adresse;
    private String telephone;

}
