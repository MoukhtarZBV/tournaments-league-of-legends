package modele;

import java.sql.Date;

public class Jouer {

	private Equipe equipe;
	private Date dateDebut;
	private String heureDebut;
	
	public Jouer(Equipe e, Date dateDebut, String heureDebut) {
		this.equipe = e;
		this.dateDebut = dateDebut;
		this.heureDebut = heureDebut;
	}
	
	public Equipe getEquipe() {
		return equipe;
	}

	public void setEquipe(Equipe e) {
		this.equipe = e;
	}

	public Date getDate() {
		return this.dateDebut;
	}
	
	public String getHeure() {
		return this.heureDebut;
	}
	
	@Override 
	public String toString() {
		return "Jouer = [equipe=" + this.equipe + ", date = " + this.dateDebut + ", heure = " + this.heureDebut + "]";
	}
	
}
