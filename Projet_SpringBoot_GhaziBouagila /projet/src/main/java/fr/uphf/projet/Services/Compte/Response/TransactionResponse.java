
package fr.uphf.projet.Services.Compte.Response;

import fr.uphf.projet.entities.Transaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TransactionResponse {
    private Long id;
    private double montant;
    private String typeTransaction;
    private String typeSource;
    private String idSource;
    public TransactionResponse(Transaction transaction) {
        this.id = transaction.getId();
        this.montant = transaction.getMontant();
        this.typeTransaction = transaction.getTypeTransaction().toString();
        this.typeSource = transaction.getTypeSource();
        this.idSource = transaction.getIdSource();

    }
}