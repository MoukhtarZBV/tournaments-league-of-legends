package dao;

import java.util.List;
import java.util.Optional;

import modele.Equipe;
import modele.Joueur;

public interface EquipeDAO extends DAO<Equipe, Integer>{
	
	Optional<Equipe> getByNom (String nom);
	
	int getIdByNom(String nom);
	
	public List<Joueur> listeJoueurs(Equipe equipe);
}
