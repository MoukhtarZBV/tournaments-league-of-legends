package dao;

import java.util.List;
import java.util.Optional;

import modele.Administrateur;

public interface AdminDAO extends DAO<Administrateur, Integer> {
    
    public Optional<Administrateur> getByNomPrenom(String nom, String prenom);

}
