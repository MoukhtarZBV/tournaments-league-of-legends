package modele;

import java.sql.Date;
import java.sql.Time;
import java.util.Objects;

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
	
	@Override
	public boolean equals(Object o) {
		if (o==null) return false;
		if (o==this) return true;
		if (o instanceof Partie) {
			Partie p = (Partie) o;
			return p.date==this.date && p.heure==this.heure && p.deroulement == this.deroulement 
					&& p.idEquipe == this.idEquipe && p.tournoi.equals(this.tournoi);
		} else {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.date, this.heure);
	}
	
}
