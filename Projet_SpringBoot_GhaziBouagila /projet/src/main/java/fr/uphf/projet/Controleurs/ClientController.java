package fr.uphf.projet.Controleurs;

import fr.uphf.projet.Services.Exception.NoContentException;
import fr.uphf.projet.Services.client.ClientService;
import fr.uphf.projet.Services.client.CreationClientRequest;
import fr.uphf.projet.Services.client.UpdateClientRequest;
import fr.uphf.projet.Services.client.UpdateClientResponse;
import fr.uphf.projet.entities.Client;
import fr.uphf.projet.Services.Exception.BadRequestException;
import fr.uphf.projet.Services.Exception.HttpErrorResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/clients")
public class ClientController {
  private final ClientService clientService;

  @Autowired
  public ClientController(ClientService clientService) {
    this.clientService = clientService;
  }

  @GetMapping
  public ResponseEntity<?> getClients(@RequestParam(required = true) String nom,
                                      @RequestParam(required = true) String prenom) {
    try {
      Client clients = clientService.getClient(nom, prenom);
      if (clients == null) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
      }
      return ResponseEntity.ok(clients);
    } catch (BadRequestException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HttpErrorResponse(e.getMessage(), Integer.toString(e.getErrorCode())));
    } catch (NoContentException e) {
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body(new HttpErrorResponse("Une erreur de traitement a été rencontrée", "INTERNAL_SERVER_ERROR"));
    }
  }
  @PostMapping
  public ResponseEntity<?> createClient(@RequestBody CreationClientRequest creationClientRequest) {
    try {
      Client newClient = clientService.createClient(creationClientRequest);
      return ResponseEntity.status(HttpStatus.CREATED).body(newClient);
    } catch (BadRequestException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HttpErrorResponse(e.getMessage(), Integer.toString(e.getErrorCode())));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body(new HttpErrorResponse("Une erreur de traitement a été rencontrée", "INTERNAL_SERVER_ERROR"));
    }
  }
  @PutMapping
  public ResponseEntity<?> updateClient(@RequestBody UpdateClientRequest updateClientRequest) {
    try {
      UpdateClientResponse updatedClient = clientService.updateClient(updateClientRequest);
      return ResponseEntity.ok(updatedClient);
    } catch (BadRequestException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HttpErrorResponse(e.getMessage(), Integer.toString(e.getErrorCode())));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body(new HttpErrorResponse("Une erreur de traitement a été rencontrée", "INTERNAL_SERVER_ERROR"));
    }
  }
}
