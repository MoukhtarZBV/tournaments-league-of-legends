package dao;

import java.util.Optional;

import modele.Joueur;

public interface JoueurDAO extends DAO<Joueur, Integer>{
	Optional <Joueur> getByPseudo(String value) throws Exception;
	
}
