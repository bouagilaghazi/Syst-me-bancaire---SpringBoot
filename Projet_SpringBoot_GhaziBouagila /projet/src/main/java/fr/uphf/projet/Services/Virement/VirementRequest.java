package fr.uphf.projet.Services.Virement;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VirementRequest {
    private String ibanCompteEmetteur;
    private String ibanCompteBeneficiaire;
    private double montant;
    private String libelleVirement;
}