package dao;

import entites.Administrateur;
import java.util.Optional;

public interface AdminDAO extends DAO<Administrateur, Integer> {
	
    Optional<Administrateur> getByNom(String nom) throws Exception;
    Optional<Administrateur> getByNomPrenom(String nom, String prenom) throws Exception;

}
