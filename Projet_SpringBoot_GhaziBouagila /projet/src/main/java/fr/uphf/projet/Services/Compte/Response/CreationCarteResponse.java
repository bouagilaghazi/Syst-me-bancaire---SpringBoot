package fr.uphf.projet.Services.Compte.Response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class CreationCarteResponse {
    private Long TitulaireId;
    private String numeroCarte;
    private String codeSecret;
    private String dateExpiration;

    public CreationCarteResponse(Long Titulaire , String numeroCarte, String codeSecret, String dateExpiration) {
        this.TitulaireId = Titulaire;
        this.numeroCarte = numeroCarte;
        this.codeSecret = codeSecret;
        this.dateExpiration = dateExpiration;
    }

}
