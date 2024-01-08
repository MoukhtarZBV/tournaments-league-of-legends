package modele;

import java.sql.Date;
import java.util.List;

import dao.JouerJDBC;

public class Jouer {

	private Equipe equipe;
	private Date dateDebut;
	private String heureDebut;
	
	private JouerJDBC jdbc;
	
	public Jouer(Equipe e, Date dateDebut, String heureDebut) {
		this.equipe = e;
		this.dateDebut = dateDebut;
		this.heureDebut = heureDebut;
	}
	
	public Jouer() {
		this.jdbc = new JouerJDBC();
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

	// ==================== //
	// ==== Partie DAO ==== //
	// ==================== //

	public List<Jouer> getTousLesJouers(){
		return jdbc.getAll();
	}
	
	public void ajouterJouer(Jouer jouer) {
		jdbc.add(jouer);
	}

	public void supprimerJouer(Jouer jouer) {
		jdbc.delete(jouer);
	}
	
}
