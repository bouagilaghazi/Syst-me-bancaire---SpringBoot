package fr.uphf.projet.Services.client;

import fr.uphf.projet.Services.Exception.BadRequestException;
import fr.uphf.projet.Services.Exception.NoContentException;
import fr.uphf.projet.entities.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import fr.uphf.projet.Repository.ClientRepository;

import java.util.Date;
import java.util.List;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    public Client getClient(String nom, String prenom){
        if(nom == null || prenom == null) {
            throw new BadRequestException("Nom /Prénom  non renseignées ou incorrectes",400) ;
        }
        Client clients = clientRepository.findByNomAndPrenom(nom, prenom);
        if(clients == null) {
            throw new NoContentException("Aucun client trouvé avec le nom : " + nom + " et le prénom : " + prenom + "",204) ;
        }
        return clients ;
    }

    public Client createClient(CreationClientRequest creationClientRequest) throws BadRequestException {
        // Validate the request
        if (creationClientRequest.getNom() == null || creationClientRequest.getPrenom() == null || creationClientRequest.getTelephone() == null || creationClientRequest.getAdresse() == null) {
            throw new BadRequestException("Les donnnées en entrée du service sont non renseignes ou incorrectes", 400);
        }

        // Create the new client in the banking system
        Client newClient = Client.builder()
                .nom(creationClientRequest.getNom())
                .prenom(creationClientRequest.getPrenom())
                .date_naissance(creationClientRequest.getDate_naissance())
                .date_inscription(creationClientRequest.getDate_inscription())
                .adresse(creationClientRequest.getAdresse())
                .telephone(creationClientRequest.getTelephone())
                .build();

        // Save the new client to the database...
        Client savedClient = this.clientRepository.save(newClient);

        return newClient;
    }
    public UpdateClientResponse updateClient(UpdateClientRequest updateClientRequest) throws BadRequestException {
        // Validate the request
        if (updateClientRequest.getNom() == null || updateClientRequest.getPrenom() == null || updateClientRequest.getTelephone() == null || updateClientRequest.getAdresse() == null) {
            throw new BadRequestException("Les donnnées en entrée du service sont non renseignes ou incorrectes", 400);
        }

        // Find the existing client in the banking system
        Client existingClient = clientRepository.findByNomAndPrenom(updateClientRequest.getNom(), updateClientRequest.getPrenom());
        if (existingClient == null) {
            throw new BadRequestException("Le client à mettre à jour n'existe pas", 400);
        }

        // Update the client's details
        existingClient.setNom(updateClientRequest.getNom());
        existingClient.setPrenom(updateClientRequest.getPrenom());
        existingClient.setDate_naissance(updateClientRequest.getDate_naissance());
        existingClient.setAdresse(updateClientRequest.getAdresse());
        existingClient.setTelephone(updateClientRequest.getTelephone());

        // Save the updated client to the database...
        Client updatedClient = this.clientRepository.save(existingClient);

        // Create a new UpdateClientResponse object and set its fields using the updated Client object
        UpdateClientResponse updateClientResponse = new UpdateClientResponse();
        updateClientResponse.setNom(updatedClient.getNom());
        updateClientResponse.setPrenom(updatedClient.getPrenom());
        updateClientResponse.setDate_naissance(updatedClient.getDate_naissance());
        updateClientResponse.setAdresse(updatedClient.getAdresse());
        updateClientResponse.setTelephone(updatedClient.getTelephone());
        updateClientResponse.setDateInscription(updatedClient.getDate_inscription());
        updateClientResponse.setDateModification(new Date()); // Set the modification date to the current date

        return updateClientResponse;
    }
}