package modele;

import java.sql.Date;
import java.sql.Time;

public class Partie {

	private Date date;
	private Time heure;
	private String deroulement;
	private Equipe idEquipe;
	private Tournoi tournoi;
	
	public Partie(Date date, Time heure, String deroulement, Equipe idEquipe, Tournoi tournoi) {
		this.date = date;
		this.heure = heure;
		this.deroulement = deroulement;
		this.idEquipe = idEquipe;
		this.tournoi = tournoi;
	}

	public Date getDate() {
		return date;
	}

	public Time getHeure() {
		return heure;
	}

	public String getDeroulement() {
		return deroulement;
	}

	public Equipe getEquipeGagnant() {
		return idEquipe;
	}
	
	public Tournoi getTournoi() {
		return this.tournoi;
	}
	
}
