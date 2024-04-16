package fr.uphf.projet.Repository;

import fr.uphf.projet.entities.CarteBancaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarteBancaireRepository extends JpaRepository<CarteBancaire, String> {
}