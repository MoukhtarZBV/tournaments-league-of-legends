package modele;

import java.util.Objects;

public class Compte {
	private int idCompte;
	private String login;
	private String motDePasse;
	private TypeCompte type;
	
	// Constructeur
	public Compte(int id, String login, String motDePasse, TypeCompte type) {
		this.idCompte = id;
		this.login = login;
		this.motDePasse = motDePasse;
		this.type = type;
	}
	
	// Get
	public int getId() {
		return this.idCompte;
	}
	
	public String getLogin() {
		return this.login;
	}
	
	public String getMotDePasse() {
		return this.motDePasse;
	}
	
	public TypeCompte getType() {
		return this.type;
	}
	
	// Set	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}
	
	public void setType(TypeCompte type) {
		this.type = type;
	}
	
	// Overrides
	@Override
	public boolean equals(Object o) {
		if (o==this) return true;
		if (o==null) return false;
		if(o instanceof Compte) {
			Compte c = (Compte) o;
			return this.idCompte == c.idCompte && this.login == c.login && this.motDePasse == c.login;
		} else {
			return false;
		}
	}
	
	@Override 
	public int hashCode() {
		return Objects.hash(this.idCompte);
	}
	
	@Override
	public String toString() {
		return String.format("Compte ID[%d], %s, %s", this.getId(), this.getLogin(), this.getMotDePasse());
	}
}
