package fr.uphf.projet.Services.Compte.Request;

import fr.uphf.projet.entities.Client;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreationCarteRequest {
    private Long titulaireId;
    private String codeSecret;
}
