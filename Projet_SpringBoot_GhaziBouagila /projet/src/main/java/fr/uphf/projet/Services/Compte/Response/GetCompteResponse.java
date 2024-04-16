
package fr.uphf.projet.Services.Compte.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetCompteResponse {
    private String iban;
    private double solde;
    private String intituleCompte;
    private String typeCompte;
    private List<TitulaireCompteResponse> titulairesCompte;
    private List<TransactionResponse> transactions;
}