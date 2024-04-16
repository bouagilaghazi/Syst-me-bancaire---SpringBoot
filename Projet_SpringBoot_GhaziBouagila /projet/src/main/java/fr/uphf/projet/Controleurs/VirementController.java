package fr.uphf.projet.Controleurs;

import fr.uphf.projet.Services.Virement.VirementRequest;
import fr.uphf.projet.Services.Virement.VirementResponse;
import fr.uphf.projet.Services.Virement.VirementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VirementController {

    @Autowired
    private VirementService virementService;

    @PostMapping("/virements")
    public ResponseEntity<VirementResponse> createVirement(@RequestBody VirementRequest virementRequest) {
        VirementResponse virementResponse = virementService.creerVirement(virementRequest);
        return ResponseEntity.ok(virementResponse);
    }
}