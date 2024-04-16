// src/main/java/fr/uphf/projet/Controleurs/CompteController.java
package fr.uphf.projet.Controleurs;

import fr.uphf.projet.Services.Compte.*;
import fr.uphf.projet.Services.Compte.Request.CreatePaiementRequest;
import fr.uphf.projet.Services.Compte.Request.CreationCarteRequest;
import fr.uphf.projet.Services.Compte.Request.CreationCompteRequest;
import fr.uphf.projet.Services.Compte.Response.*;
import fr.uphf.projet.Services.Exception.BadRequestException;
import fr.uphf.projet.Services.Exception.HttpErrorResponse;
import fr.uphf.projet.Services.Exception.NoContentException;
import fr.uphf.projet.entities.CarteBancaire;
import fr.uphf.projet.entities.Compte;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CompteController {

    @Autowired
    private CompteService compteService;

    @GetMapping("comptes")
    public ResponseEntity<?> getComptes(@RequestParam(required = true) Long identifiantClient) {
        try {
            List<Compte> comptes = compteService.findComptesByClientId(identifiantClient);
            List<GetCompteResponse> compteResponses = comptes.stream()
                    .map(compte -> new GetCompteResponse(
                            compte.getIBAN(),
                            compte.getSolde(),
                            compte.getIntituleCompte(),
                            compte.getTypeCompte().toString(),
                            compte.getTitulairesCompte().stream()
                                .map(titulaireCompte -> new TitulaireCompteResponse(titulaireCompte.getClient().getId()))
                                .collect(Collectors.toList()),
                            compte.getTransactions().stream()
                                .map(transaction -> new TransactionResponse(
                                        transaction.getId(),
                                        transaction.getMontant(),
                                        transaction.getTypeTransaction().toString(),
                                        transaction.getTypeSource(),
                                        transaction.getIdSource()
                                ))
                                .collect(Collectors.toList())
                    ))
                    .collect(Collectors.toList());
            GetTitulaireCompteResponse response = new GetTitulaireCompteResponse(identifiantClient, compteResponses);
            return ResponseEntity.ok(response);
        } catch (NoContentException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }
    @PostMapping("/comptes")
    public ResponseEntity<CreationCompteResponse> createCompte(@RequestBody CreationCompteRequest creationCompteRequest) {
        CreationCompteResponse response = compteService.createCompte(creationCompteRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @GetMapping("/comptes/{iban}/cartes")
    public ResponseEntity<?> getCartesParIban(@PathVariable String iban) {
        try {
            List<CarteBancaire> cartes = compteService.findCartesByIban(iban);
            List<GetCarteResponse> carteResponses = cartes.stream()
                    .map(carte -> new GetCarteResponse(carte.getNumeroCarte(), carte.getDateExpiration(), carte.getTitulaire().getId()))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(carteResponses);
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HttpErrorResponse(e.getMessage(), "BAD_REQUEST"));
        } catch (NoContentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HttpErrorResponse(e.getMessage(), "NOT_FOUND"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new HttpErrorResponse("An error occurred: " + e.getMessage(), "INTERNAL_SERVER_ERROR"));
        }
    }

@PostMapping("/comptes/{iban}/cartes")
public ResponseEntity<?> createCarte(@PathVariable String iban, @RequestBody CreationCarteRequest creationCarteRequest) {
    try {
        CreationCarteResponse newCarte = compteService.createCarte(iban, creationCarteRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCarte);
    } catch (BadRequestException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HttpErrorResponse(e.getMessage(), "BAD_REQUEST"));
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new HttpErrorResponse("An error occurred: " + e.getMessage(), "INTERNAL_SERVER_ERROR"));
    }
}
@PostMapping("/comptes/{iban}/cartes/{numeroCarte}/paiement")
public ResponseEntity<?> createPaiementCarte(@PathVariable String iban, @PathVariable String numeroCarte, @RequestBody CreatePaiementRequest createPaiementRequest) {
    try {
        CreatePaiementResponse newPaiement = compteService.createPaiement(iban, numeroCarte, createPaiementRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(newPaiement);
    } catch (BadRequestException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HttpErrorResponse(e.getMessage(), "BAD_REQUEST"));
    } catch (NoContentException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HttpErrorResponse(e.getMessage(), "NOT_FOUND"));
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new HttpErrorResponse("An error occurred: " + e.getMessage(), "INTERNAL_SERVER_ERROR"));
    }
}
}