package dao;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import modele.Equipe;
import modele.Partie;
import modele.Tournoi;

public interface PartieDAO extends DAO<Partie, Integer> {
	
	@Override
	public boolean add(Partie p) throws IllegalArgumentException;

	Optional<Partie> getByDateHeure(Date date, String heure);
	
	public List<Partie> getPartiesTournoi(Tournoi tournoi);
	
	public Optional<Partie> getFinaleTournoi(Tournoi tournoi);
	
	public List<Equipe> getEquipes(Partie p);
}
