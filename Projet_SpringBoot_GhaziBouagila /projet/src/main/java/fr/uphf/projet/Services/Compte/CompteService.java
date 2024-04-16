package fr.uphf.projet.Services.Compte;
import fr.uphf.projet.Repository.*;
import fr.uphf.projet.Services.Compte.Request.CreatePaiementRequest;
import fr.uphf.projet.Services.Compte.Request.CreationCarteRequest;
import fr.uphf.projet.Services.Compte.Request.CreationCompteRequest;
import fr.uphf.projet.Services.Compte.Response.CreatePaiementResponse;
import fr.uphf.projet.Services.Compte.Response.CreationCarteResponse;
import fr.uphf.projet.Services.Compte.Response.CreationCompteResponse;
import fr.uphf.projet.Services.Compte.Response.TitulaireCompteResponse;
import fr.uphf.projet.Services.Exception.BadRequestException;
import fr.uphf.projet.Services.Exception.NoContentException;
import fr.uphf.projet.entities.* ;
import fr.uphf.projet.entities.TitulaireCompte;
import fr.uphf.projet.entities.TypeCompte;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import fr.uphf.projet.entities.CarteBancaire;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class CompteService {
    @Autowired
    private CompteRepository compteRepository;
    @Autowired
    private TitulaireCompteRepository titulaireCompteRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private CarteBancaireRepository carteBancaireRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    public List<Compte> findComptesByClientId(Long clientId) throws NoContentException {
        List<TitulaireCompte> titulaireComptes = titulaireCompteRepository.findByClientId(clientId);
        List<Compte> comptes = titulaireComptes.stream()
                .map(TitulaireCompte::getCompte)
                .collect(Collectors.toList());
        if (comptes.isEmpty()) {
            throw new NoContentException("Aucun compte trouvé pour l'identifiant client " + clientId, 204);
        }
        return comptes;
    }

    public CreationCompteResponse createCompte(CreationCompteRequest creationCompteRequest) {
        Compte compte = new Compte();
        compte.setSolde(0); // Initialiser le solde à 0
        compte.setIntituleCompte(creationCompteRequest.getIntituleCompte());
        compte.setTypeCompte(TypeCompte.valueOf(creationCompteRequest.getTypeCompte()));
        compte.setDateCreation(LocalDateTime.now());

        // Calculer l'IBAN
        String codeBanque = "30003"; // Remplacer par le code banque approprié
        String codeGuichet = "02054"; // Remplacer par le code guichet approprié
        String numeroCompte = "10313400399"; // Remplacer par le numéro de compte approprié
        Long cleRIB = 97 - ((89 * Integer.parseInt(codeBanque) + 15 * Integer.parseInt(codeGuichet) + 3 * Long.parseLong(numeroCompte)) % 97);
        String iban = "FR" + cleRIB + codeBanque + codeGuichet + numeroCompte;
        compte.setIBAN(iban);

        compte = compteRepository.save(compte);
        List<TitulaireCompteResponse> titulairesCompte = new ArrayList<>();
        for (Long clientId : creationCompteRequest.getIdClients()) {
            TitulaireCompte titulaireCompte = new TitulaireCompte();
            Client client = clientRepository.findById(clientId)
                    .orElseThrow(() -> new BadRequestException("Client with id " + clientId + " not found", 400)); // Fetch the Client object
            titulaireCompte.setClient(client); // Set the Client object
            titulaireCompte.setCompte(compte);
            titulaireCompteRepository.save(titulaireCompte);

            // Ajoutez le titulaire du compte à la liste des titulaires du compte
            titulairesCompte.add(new TitulaireCompteResponse(client.getId()));
        }

        CreationCompteResponse response = new CreationCompteResponse(
                compte.getIBAN(),
                compte.getSolde(),
                compte.getIntituleCompte(),
                compte.getTypeCompte().toString(),
                compte.getDateCreation(),
                titulairesCompte // Ajoutez cette ligne
        );
        return response;
    }
    public List<CarteBancaire> findCartesByIban(String iban) {
        Compte compte = compteRepository.findById(iban)
                .orElseThrow(() -> new BadRequestException("Compte with IBAN " + iban + " not found", 400)); // Fetch the Compte object
        List<CarteBancaire> cartes = compte.getCarteBancaires();
        if (cartes.isEmpty()) {
            throw new NoContentException("Aucune carte n'est rattachée au compte fournit en paramètre", 404);
        }
        return cartes;
    }

    public CreationCarteResponse createCarte(String iban, CreationCarteRequest creationCarteRequest) {
        Compte compte = compteRepository.findById(iban)
                .orElseThrow(() -> new BadRequestException("Compte with IBAN " + iban + " not found", 400));
        if (compte.getCarteBancaires().size() >= 2) {
            throw new BadRequestException("A compte can have a maximum of two cards", 400);
        }
        Client titulaire = clientRepository.findById(creationCarteRequest.getTitulaireId())
                .orElseThrow(() -> new BadRequestException("Client with id " + creationCarteRequest.getTitulaireId() + " not found", 400)); // Fetch the Client object
        CarteBancaire newCarte = new CarteBancaire();
        newCarte.setNumeroCarte(generateCardNumber()); // Generate a random 16-digit card number
        newCarte.setDateExpiration(LocalDateTime.now().plusYears(5)); // Set the expiration date to 5 years from now
        newCarte.setTitulaire(titulaire); // Set the card holder
        newCarte.setCompte(compte); // Set the associated account
        newCarte.setCodeSecret(creationCarteRequest.getCodeSecret()); // Set the secret code
        carteBancaireRepository.save(newCarte); // Save the new card to the database
        return new CreationCarteResponse(newCarte.getTitulaire().getId(), newCarte.getNumeroCarte(), newCarte.getCodeSecret(), newCarte.getDateExpiration().format(DateTimeFormatter.ofPattern("MM/yy")));
    }

    private String generateCardNumber() {
        Random random = new Random();
        StringBuilder cardNumber = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            cardNumber.append(random.nextInt(10));
        }
        return cardNumber.toString();
    }
    public CreatePaiementResponse createPaiement(String iban, String numeroCarte, CreatePaiementRequest createPaiementRequest) {
        Compte compte = compteRepository.findById(iban)
                .orElseThrow(() -> new BadRequestException("Compte with IBAN " + iban + " not found", 400)); // Fetch the Compte object
        if (compte.getSolde() < createPaiementRequest.getMontant()) {
            throw new BadRequestException("Insufficient balance in the account", 400);
        }
        CarteBancaire carte = carteBancaireRepository.findById(numeroCarte)
                .orElseThrow(() -> new BadRequestException("Carte with numeroCarte " + numeroCarte + " not found", 400)); // Fetch the CarteBancaire object

        // Create a new Transaction object
        Transaction transaction = new Transaction();
        transaction.setMontant(createPaiementRequest.getMontant());
        transaction.setTypeTransaction(TypeTransaction.DEBIT); // Set the transaction type to DEBIT
        transaction.setTypeSource("CarteBancaire"); // Set the source type to CarteBancaire
        transaction.setIdSource(carte.getNumeroCarte()); // Set the source ID to the card number
        transaction.setCompte(compte); // Set the associated account

        transaction = transactionRepository.save(transaction); // Save the new Transaction object to the database
        //debiter le soldes du compte
        compte.setSolde(compte.getSolde() - createPaiementRequest.getMontant());
        compteRepository.save(compte);
        // Create a new CreatePaiementResponse object and set its fields using the new Transaction object
        CreatePaiementResponse createPaiementResponse = new CreatePaiementResponse(
                transaction.getId(),
                transaction.getMontant(),
                transaction.getTypeTransaction().toString(),
                LocalDateTime.now() // Set the creation date to the current date and time
        );

        return createPaiementResponse;
    }

}

