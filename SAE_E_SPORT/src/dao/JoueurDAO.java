package dao;

import java.util.List;
import java.util.Optional;

import modele.Equipe;
import modele.Joueur;

public interface JoueurDAO extends DAO<Joueur, Integer>{
	Optional <Joueur> getByPseudo(String value) throws Exception;
	List <Joueur> getByEquipe(Equipe equipe) throws Exception;
}
