package dao;

import java.util.Optional;

import entites.Arbitre;

public interface ArbitreDAO extends DAO<Arbitre,Integer>{
	
	Optional <Arbitre> getByNomPrenom(String nom, String prenom) throws Exception;

}
