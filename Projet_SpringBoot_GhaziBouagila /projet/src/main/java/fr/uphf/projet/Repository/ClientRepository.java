package fr.uphf.projet.Repository;
import fr.uphf.projet.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
 Client findByNomAndPrenom(String nom,String prenom);
}
