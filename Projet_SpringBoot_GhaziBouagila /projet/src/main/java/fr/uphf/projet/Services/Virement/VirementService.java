package fr.uphf.projet.Services.Virement;
import fr.uphf.projet.Repository.VirementRepository;
import fr.uphf.projet.Repository.CompteRepository;
import fr.uphf.projet.Repository.TransactionRepository;
import fr.uphf.projet.Services.Compte.Response.TransactionResponse;
import fr.uphf.projet.Services.Exception.BadRequestException;
import fr.uphf.projet.entities.Compte;
import fr.uphf.projet.entities.Transaction;
import fr.uphf.projet.entities.TypeTransaction;
import fr.uphf.projet.entities.Virement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VirementService {
    @Autowired
    private CompteRepository compteRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private VirementRepository VirementRepository;
   public VirementResponse creerVirement(VirementRequest virementRequest) {
    // Fetch the sender and receiver accounts
    Compte compteEmetteur = compteRepository.findById(virementRequest.getIbanCompteEmetteur())
            .orElseThrow(() -> new BadRequestException("Compte with IBAN " + virementRequest.getIbanCompteEmetteur() + " not found", 400));
    Compte compteBeneficiaire = compteRepository.findById(virementRequest.getIbanCompteBeneficiaire())
            .orElseThrow(() -> new BadRequestException("Compte with IBAN " + virementRequest.getIbanCompteBeneficiaire() + " not found", 400));

    // Create a new Transaction object for the debit transaction
    Transaction transactionDebit = new Transaction();
    transactionDebit.setMontant(virementRequest.getMontant());
    transactionDebit.setTypeTransaction(TypeTransaction.DEBIT);
    transactionDebit.setTypeSource("Virement");
    transactionDebit.setIdSource(virementRequest.getIbanCompteEmetteur());
    transactionDebit.setCompte(compteEmetteur);

    // Create a new Transaction object for the credit transaction
    Transaction transactionCredit = new Transaction();
    transactionCredit.setMontant(virementRequest.getMontant());
    transactionCredit.setTypeTransaction(TypeTransaction.CREDIT);
    transactionCredit.setTypeSource("Virement");
    transactionCredit.setIdSource(virementRequest.getIbanCompteBeneficiaire());
    transactionCredit.setCompte(compteBeneficiaire);

    // Save the transactions to the database
    transactionDebit = transactionRepository.save(transactionDebit);
    transactionCredit = transactionRepository.save(transactionCredit);

    // Create a new Virement object and save it to the database
    Virement virement = new Virement();
    virement.setCompteEmetteur(compteEmetteur);
    virement.setCompteBeneficiaire(compteBeneficiaire);
    virement.setMontant(virementRequest.getMontant());
    virement.setLibelleVirement(virementRequest.getLibelleVirement());
    virement.setDateCreation(LocalDateTime.now());

    virement = VirementRepository.save(virement);

    // Create a new VirementResponse object and set its fields using the new Virement object
    VirementResponse virementResponse = new VirementResponse(
        virement.getIdVirement(),
        virement.getDateCreation(),
        List.of(new TransactionResponse(transactionDebit), new TransactionResponse(transactionCredit))
    );

    return virementResponse;
}
}