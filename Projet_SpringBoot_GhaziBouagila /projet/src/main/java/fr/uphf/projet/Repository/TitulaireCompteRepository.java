package fr.uphf.projet.Repository ;

import fr.uphf.projet.entities.TitulaireCompte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TitulaireCompteRepository extends JpaRepository<TitulaireCompte, Long> {
    List<TitulaireCompte> findByClientId(Long clientId);
}