package modele;

import java.util.Objects;

public class Arbitre {
	
	private int idArbitre;
	private String nomArbitre;
	private String prenomArbitre;
	
	// Constructeur
	public Arbitre(int idArbitre, String nomArbitre, String prenomArbitre) {
		this.idArbitre = idArbitre;
		this.nomArbitre = nomArbitre;
		this.prenomArbitre = nomArbitre;
	}
	
	// Get
	public int getId() {
		return this.idArbitre;
	}
	
	public String getNom() {
		return this.nomArbitre;
	}
	
	public String getPrenom() {
		return this.prenomArbitre;
	}
	
	// Set
	public void setNom(String nomArbitre) {
		this.nomArbitre = nomArbitre;
	}
	
	public void setPrenom(String prenomArbitre) {
		this.prenomArbitre = prenomArbitre;
	}
	
	// Overrides
	@Override
	public boolean equals(Object o) {
		if (o==null) return false;
		if (o==this) return true;
		if(o instanceof Arbitre) {
			Arbitre a = (Arbitre) o;
			return this.idArbitre == a.getId(); 
		} else {
			return false;
		}
	}
	
	@Override 
	public int hashCode() {
		return Objects.hash(this.idArbitre);
	}
	
	@Override
	public String toString() {
		return String.format("Arbitre ID[%d], %s %s", this.getId(), this.getPrenom(), this.getNom().toUpperCase());
	}	
}
