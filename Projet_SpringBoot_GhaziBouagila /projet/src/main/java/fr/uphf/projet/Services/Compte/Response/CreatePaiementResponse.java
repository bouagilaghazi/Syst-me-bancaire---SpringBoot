package fr.uphf.projet.Services.Compte.Response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CreatePaiementResponse {
    private Long idTransaction;
    private double montant;
    private String typeTransaction;
    private LocalDateTime dateCreation;

    public CreatePaiementResponse(Long idTransaction, double montant, String typeTransaction, LocalDateTime dateCreation) {
        this.idTransaction = idTransaction;
        this.montant = montant;
        this.typeTransaction = typeTransaction;
        this.dateCreation = dateCreation;
    }
}