package dao;

import java.sql.Date;
import java.util.List;

import modele.Arbitre;
import modele.Equipe;
import modele.Niveau;
import modele.Statut;
import modele.Tournoi;

public interface TournoiDAO extends DAO<Tournoi, String>{
	
	public boolean existeTournoiEntreDates(Date dateDebut, Date dateFin);
	
	public List<Equipe> getEquipesTournoi(Tournoi tournoi);
	
	public List<Arbitre> getArbitresTournoi(Tournoi tournoi);
	
	public List<Tournoi> getTournoisNiveauStatutNom(String nom, Niveau niveau, Statut status);
	
	public void changerStatutTournoi(Tournoi tournoi, Statut status);
	
	public void changerVainqueurTournoi(Tournoi tournoi, Equipe equipe);
	
}
