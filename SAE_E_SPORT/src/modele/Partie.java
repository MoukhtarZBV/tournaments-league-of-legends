package modele;

import java.sql.Date;
import java.sql.Time;
import java.util.Objects;

public class Partie {

	private Date date;
	private String heure;
	private String deroulement;
	private Equipe gagnant;
	private Tournoi tournoi;
	
	public Partie(Date date, String heure, String deroulement, Tournoi tournoi) {
		this.date = date;
		this.heure = heure;
		this.deroulement = deroulement;
		this.tournoi = tournoi;
		this.gagnant = null;
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

	public Equipe getEquipeGagnant() {
		return this.gagnant;
	}
	
	public void setEquipeGagnant(Equipe e) {
		this.gagnant = e;
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
					&& p.gagnant.equals(this.gagnant) && p.tournoi.equals(this.tournoi);
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
				this.deroulement + ", tournoi=" + this.tournoi + ", gagnant=" + this.gagnant + ']';
	}
	
}
