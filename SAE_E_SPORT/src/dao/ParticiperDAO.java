package dao;

import java.util.List;
import java.util.Optional;

import modele.Equipe;
import modele.Participer;
import modele.Tournoi;

public interface ParticiperDAO extends DAO<Participer, Tournoi>{

	public Optional<Participer> getByTournoiEquipe(Tournoi tournoi, Equipe equipe);
	
}
