package modele;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import dao.CompteJDBC;

public class Compte {
	private int idCompte;
	private String login;
	private String motDePasse;
	private TypeCompte type;
	private CompteJDBC jdbc;
	
	// Constructeur
	public Compte(int id, String login, String motDePasse, TypeCompte type) {
		this.idCompte = id;
		this.login = login;
		this.motDePasse = motDePasse;
		this.type = type;
	}
	
	public Compte() {
		this.jdbc = new CompteJDBC();
	}
	
	// Get
	public int getIdCompte() {
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
	
	public List<Compte> tousLesComptes(){
		List<Compte> c = new ArrayList<>();
		try {
			c =  this.jdbc.getAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return c;
	}
	
	public void ajouterCompte(Compte c) {
		try {
			this.jdbc.add(c);
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
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
		return String.format("Compte ID[%d], %s, %s", this.getIdCompte(), this.getLogin(), this.getMotDePasse());
	}
}
