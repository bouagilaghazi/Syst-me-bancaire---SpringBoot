package fr.uphf.projet.Services.Virement;

import fr.uphf.projet.Services.Compte.Response.TransactionResponse;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class VirementResponse {
    private     Long idVirement;
    private LocalDateTime dateCreation;
    private List<TransactionResponse> transactions;
    public VirementResponse(Long idVirement, LocalDateTime dateCreation, List<TransactionResponse> transactions) {
        this.idVirement = idVirement;
        this.dateCreation = dateCreation;
        this.transactions = transactions;
    }

}