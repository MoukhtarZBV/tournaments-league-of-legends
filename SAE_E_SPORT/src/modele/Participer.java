package modele;

import java.util.List;
import java.util.Optional;

import dao.ParticiperJDBC;

public class Participer implements Comparable<Participer>{

	private Equipe equipe;
	private Tournoi tournoi;
	private int nbPointsPouleGagnes;
	private int nbPointsTournoiGagnes;
	private int nbMatchsJoues;
	private int nbMatchsGagnes;
	private int classement;
	
	private ParticiperJDBC jdbc;
	
	public Participer(Equipe equipe, Tournoi tournoi) {
		this.equipe = equipe;
		this.tournoi = tournoi;
		this.nbMatchsGagnes = 0;
		this.nbMatchsJoues = 0;
		this.nbPointsPouleGagnes = 0;
		this.nbPointsTournoiGagnes = 0;
		this.classement = 0;
	}
	
	public Participer() {
		this.jdbc = new ParticiperJDBC();
	}

	public int getNbPointsPouleGagnes() {
		return this.nbPointsPouleGagnes;
	}

	public void setNbPointsPouleGagnes(int nbPointsGagnes) {
		this.nbPointsPouleGagnes = nbPointsGagnes;
	}
	
	public int getNbPointsTournoiGagnes() {
		return this.nbPointsTournoiGagnes;
	}

	public void setNbPointsTournoiGagnes(int nbPointsGagnes) {
		this.nbPointsTournoiGagnes = nbPointsGagnes;
	}

	public int getNbMatchsJoues() {
		return this.nbMatchsJoues;
	}

	public void setNbMatchsJoues(int nbMatchsJoues) {
		this.nbMatchsJoues = nbMatchsJoues;
	}

	public int getNbMatchsGagnes() {
		return this.nbMatchsGagnes;
	}

	public void setNbMatchsGagnes(int nbMatchsGagnes) {
		this.nbMatchsGagnes = nbMatchsGagnes;
	}
	
	public int getClassement() {
		return this.classement;
	}
	
	public void setClassement(int classement) {
		this.classement = classement;
	}

	public Equipe getEquipe() {
		return this.equipe;
	}

	public Tournoi getTournoi() {
		return tournoi;
	}
	
	@Override 
	public String toString() {
		return "Participer = [equipe=" + this.equipe + ", tournoi=" + this.tournoi + ", nbMatchsJoues=" + this.nbMatchsJoues +
				", nbMatchsGagnes=" + this.nbMatchsGagnes + ", nbPointsGagnes=" + this.nbPointsPouleGagnes + "\n";
	}

	@Override
	public int compareTo(Participer p) {
		int res = this.nbPointsPouleGagnes - p.nbPointsPouleGagnes;
		if (res<0) return -1;
		else if (res==0) return 0;
		else return 1;
	}

	// ==================== //
	// ==== Partie DAO ==== //
	// ==================== //

	public List<Participer> getToutesLesParticipations(){
		return jdbc.getAll();
	}
	
	public void ajouterParticipation(Participer participation) {
		jdbc.add(participation);
	}

	public void mettreAJourParticipation(Participer participation) {
		jdbc.update(participation);
	}
	
	public Participer getParTournoiEquipe(Tournoi tournoi, Equipe equipe) {
		return jdbc.getByTournoiEquipe(tournoi, equipe).get();
	}
}
