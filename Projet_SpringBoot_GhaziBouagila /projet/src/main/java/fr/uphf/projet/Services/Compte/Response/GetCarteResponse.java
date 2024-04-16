package fr.uphf.projet.Services.Compte.Response;

import fr.uphf.projet.entities.Client;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter

public class GetCarteResponse {
    private String numeroCarte;
    private LocalDateTime dateExpiration;
    private Long titulaire;
    public GetCarteResponse(String numeroCarte, LocalDateTime dateExpiration, Long titulaireId) {
        this.numeroCarte = numeroCarte;
        this.dateExpiration = dateExpiration;
        this.titulaire = titulaireId;
    }
}