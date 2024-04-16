
package fr.uphf.projet.Services.Compte.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CreationCompteResponse {
    private String iban;
    private double solde;
    private String intituleCompte;
    private String typeCompte;
    private LocalDateTime dateCreation;
    private List<TitulaireCompteResponse> titulairesCompte;
}