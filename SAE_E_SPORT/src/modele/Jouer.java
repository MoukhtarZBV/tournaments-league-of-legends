package modele;

import java.sql.Date;

public class Jouer {

	private Equipe equipe;
	private Partie partie;
	
	public Jouer(Equipe e, Partie p) {
		this.equipe = e;
		this.partie = p;
	}
	
	public Equipe getEquipe() {
		return equipe;
	}

	public void setEquipe(Equipe e) {
		this.equipe = e;
	}

	public Partie getPartie() {
		return this.partie;
	}
	
	@Override 
	public String toString() {
		return "Jouer = [equipe=" + this.equipe + ", partie=" + this.partie + "]";
	}
	
}
