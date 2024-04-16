package fr.uphf.projet.Services.Compte.Response;

import fr.uphf.projet.Services.Compte.Response.GetCompteResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetTitulaireCompteResponse {
    private Long identifiantClient;
    private List<GetCompteResponse> comptes;
}
