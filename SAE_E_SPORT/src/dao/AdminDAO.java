package dao;

import java.util.Optional;

import modele.Administrateur;

public interface AdminDAO extends DAO<Administrateur, Integer> {
	
    Optional<Administrateur> getByNom(String nom) throws Exception;
    Optional<Administrateur> getByNomPrenom(String nom, String prenom) throws Exception;

}
