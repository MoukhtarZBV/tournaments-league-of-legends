package dao;

import java.util.List;
import java.util.Optional;

import modele.Arbitre;
import modele.Tournoi;

public interface ArbitreDAO extends DAO<Arbitre,Integer>{
	
	Optional<Arbitre> getByNomPrenom(String nom, String prenom);

}
