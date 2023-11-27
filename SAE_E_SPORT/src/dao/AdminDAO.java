package dao;

import java.util.List;

import modele.Administrateur;

public interface AdminDAO extends DAO<Administrateur, Integer> {
	
    List<Administrateur> getByNom(String nom) throws Exception;
    List<Administrateur> getByNomPrenom(String nom, String prenom) throws Exception;

}
