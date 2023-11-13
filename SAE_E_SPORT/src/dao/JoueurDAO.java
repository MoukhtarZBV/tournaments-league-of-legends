package dao;

import java.util.Optional;

import entites.Joueur;

public interface JoueurDAO extends DAO<Joueur, Integer>{
	Optional <Joueur> getByPseudo(String value) throws Exception;
	
}
