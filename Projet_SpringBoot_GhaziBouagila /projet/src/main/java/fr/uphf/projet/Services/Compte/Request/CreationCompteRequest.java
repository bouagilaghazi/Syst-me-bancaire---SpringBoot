
package fr.uphf.projet.Services.Compte.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CreationCompteRequest {
    private String intituleCompte;
    private String typeCompte;
    private List<Long> idClients; // Les identifiants des clients à rattacher au compte
}