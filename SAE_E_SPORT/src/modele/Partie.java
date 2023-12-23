package modele;

import java.sql.Date;
import java.sql.Time;
import java.util.Objects;

public class Partie {

	private Date date;
	private String heure;
	private String deroulement;
	private Equipe equipeUne;
	private Equipe equipeDeux;
	private Equipe equipeGagnante;
	private Tournoi tournoi;
	
	public Partie(Date date, String heure, String deroulement, Tournoi tournoi) {
		this.date = date;
		this.heure = heure;
		this.deroulement = deroulement;
		this.tournoi = tournoi;
		this.equipeUne = null;
		this.equipeDeux = null;
		this.equipeGagnante = null;
	}

	public Date getDate() {
		return date;
	}

	public String getHeure() {
		return heure;
	}

	public String getDeroulement() {
		return deroulement;
	}
	
	public Equipe getEquipeUne() {
		return this.equipeUne;
	}
	
	public Equipe getEquipeDeux() {
		return this.equipeDeux;
	}
	
	public void setEquipeUne(Equipe equipe) {
		this.equipeUne = equipe;
	}
	
	public void setEquipeDeux(Equipe equipe) {
		this.equipeDeux = equipe;
	}
	
	public Equipe getEquipeGagnante() {
		return this.equipeGagnante;
	}
	
	public void setEquipeGagnante(Equipe gagnant) {
		this.equipeGagnante = gagnant;
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
			return p.date==this.date && p.heure==this.heure;
		} else {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.date, this.heure);
	}

	@Override
	public String toString() {
		return "Partie [date=" + this.date + ", heure=" + this.heure + ", deroulement=" + 
				this.deroulement + ", tournoi=" + this.tournoi + ", premiere equipe = " + this.equipeUne + ", deuxieme equipe = " + this.equipeDeux + ", gagnant=" + this.equipeGagnante + ']';
	}
	
}
