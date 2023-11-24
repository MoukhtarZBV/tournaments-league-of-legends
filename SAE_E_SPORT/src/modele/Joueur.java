package modele;

import java.util.Objects;

public class Joueur {
	
	private int idJoueur;
	private String pseudo;
	private Equipe equipe;
	
	// Constructeur
	public Joueur(int id, String pseudo, Equipe e) {
		this.idJoueur = id;
		this.pseudo = pseudo;
		this.equipe = e;
	}
	
	// Get
	public int getId() {
		return this.idJoueur;
	}
	
	public String getPseudo() {
		return this.pseudo;
	}
	
	public Equipe getEquipe() {
		return this.equipe;
	}
	
	// Set	
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	
	public void setEquipe(Equipe e) {
		this.equipe = e;
	}
	
	// Overrides
	@Override
	public boolean equals(Object o) {
		if (o==this) return true;
		if (o==null) return false;
		if(o instanceof Joueur) {
			Joueur j = (Joueur) o;
			return this.idJoueur == j.idJoueur && this.pseudo == j.pseudo;
		} else {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.idJoueur);
	}
	
	@Override
	public String toString() {
		return String.format("Joueur ID[%d], %s", this.getId(), this.getPseudo());
	}
}
