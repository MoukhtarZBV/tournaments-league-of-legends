package modele;

import java.util.Objects;

public class Joueur {
	
	private int idJoueur;
	private String pseudo;
	
	// Constructeur
	public Joueur(int idJoueur, String pseudo) {
		this.idJoueur = idJoueur;
		this.pseudo = pseudo;
	}
	
	// Get
	public int getId() {
		return this.idJoueur;
	}
	
	public String getPseudo() {
		return this.pseudo;
	}
	
	// Set	
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	
	// Overrides
	@Override
	public boolean equals(Object o) {
		if (o==this) return true;
		if (o==null) return false;
		if(o instanceof Joueur) {
			Joueur j = (Joueur) o;
			return this.idJoueur == j.getId();
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
