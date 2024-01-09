package modele;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import dao.CompteJDBC;
import ihm.VueAccueilAdmin;
import ihm.VueTournoi;

public class Compte {
	private String login;
	private String motDePasse;
	private TypeCompte type;
	
	private CompteJDBC jdbc;
	
	// Constructeur
	public Compte(String login, String motDePasse, TypeCompte type) {
		this.login = login;
		this.motDePasse = motDePasse;
		this.type = type;
	}
	
	public Compte() {
		this.jdbc = new CompteJDBC();
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
	
	public static String genererPassword(int longueur) {
		String caractères = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()-_=+[{]};:<>./?";
		String mdp = "";
		Random random = new Random();
		for (int i = 0 ; i < longueur; i++) {
			int indice = random.nextInt(caractères.length());
			mdp += caractères.charAt(indice);
		}
		return mdp;	
	}
	
	public boolean compteValide(String login, String mdp) {
		Compte compte = this.getCompteParLogin(login);
		if (compte != null) {
			if(compte.getMotDePasse().equals(mdp)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean compteIsAdmin(String login, String mdp) {
		Compte compte = this.getCompteParLogin(login);
		return compte.getType().denomination() == "Administrateur";
	}
	
	// Overrides
	@Override
	public boolean equals(Object o) {
		if (o==this) return true;
		if (o==null) return false;
		if(o instanceof Compte) {
			Compte c = (Compte) o;
			return this.login == c.login && this.motDePasse == c.login;
		} else {
			return false;
		}
	}
	
	@Override 
	public int hashCode() {
		return Objects.hash(this.login);
	}
	
	@Override
	public String toString() {
		return String.format("Compte %s | Mot de passe : %s", this.getLogin(), this.getMotDePasse());
	}

	// ==================== //
	// ==== Partie DAO ==== //
	// ==================== //

	public List<Compte> getTousLesComptes(){
		return jdbc.getAll();
	}

	public Compte getCompteParLogin(String login) {
		return jdbc.getById(login).orElse(null);
	}

	public void ajouterCompte(Compte compte) {
		jdbc.add(compte);
	}

	public void mettreAJourCompte(Compte compte) {
		jdbc.update(compte);
	}

	public void supprimerCompte(Compte compte) {
		jdbc.delete(compte);
	}
}
