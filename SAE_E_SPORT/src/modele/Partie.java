package modele;

import java.sql.Date;
import java.sql.Time;
import java.util.Objects;

public class Partie {

	private Date date;
	private String heure;
	private String deroulement;
	private Equipe equipe1;
	private Equipe equipe2;
	private Tournoi tournoi;
	private int gagnant;
	
	public Partie(Date date, String heure, String deroulement, Equipe equipe1, Tournoi tournoi) {
		this.date = date;
		this.heure = heure;
		this.deroulement = deroulement;
		this.tournoi = tournoi;
		this.equipe1 = equipe1;
		this.gagnant = -1;
		this.equipe2 = null;
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

	public Equipe getEquipe1 () {
		return this.equipe1;
	}
	
	public int getEquipeGagnant() {
		return this.gagnant;
	}
	
	public void setEquipeGagnant(int gagnant) {
		this.gagnant = gagnant;
	}
	
	public Tournoi getTournoi() {
		return this.tournoi;
	}
	
	public Equipe getEquipe2 () {
		return this.equipe2;
	}
	
	public void setEquipe2(Equipe equipe2) {
		this.equipe2 = equipe2;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o==null) return false;
		if (o==this) return true;
		if (o instanceof Partie) {
			Partie p = (Partie) o;
			return p.date==this.date && p.heure==this.heure && p.deroulement == this.deroulement 
					&& p.gagnant==this.gagnant && p.tournoi.equals(this.tournoi);
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
				this.deroulement + ", equipe1=" + this.equipe1 + "equipe2=" + this.equipe2 +
				", tournoi=" + this.tournoi + ", gagnant=" + this.gagnant + ']';
	}
	
}
