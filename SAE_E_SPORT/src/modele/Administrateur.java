package modele;

import java.util.Objects;

public class Administrateur {
	
	private    int idAdministrateur;
	private String nomAdmin;
	private String prenomAdmin;
	private Compte compte;
	
	// Constructeur
	public Administrateur(int id, String nom, String prenom, Compte c) {
		this.idAdministrateur = id;
		this.nomAdmin         = nom;
		this.prenomAdmin      = prenom;
		this.compte 		  = c;
	}
	
	// Get
	public int getId() {
		return this.idAdministrateur;
	}
	
	public String getNom() {
		return this.nomAdmin;
	}
	
	public String getPrenom() {
		return this.prenomAdmin;
	}
	
	public Compte getCompte() {
		return this.compte;
	}
	
	// Set
	public void setNom(String nom) {
		this.nomAdmin = nom;
	}
	
	public void setPrenom(String prenom) {
		this.prenomAdmin = prenom;
	}
	
	public void setCompte(Compte c) {
		this.compte = c;
	}
		
	// Overrides
	@Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o == null) {
        	return false;
        }
        if (o instanceof Administrateur) {
        	Administrateur a = (Administrateur) o;
            return a.idAdministrateur == this.idAdministrateur && a.nomAdmin == this.nomAdmin && a.prenomAdmin == this.prenomAdmin;
        } else {
        	return false;
        }
    }
	
	@Override 
	public int hashCode() {
		return Objects.hash(this.idAdministrateur, this.nomAdmin, this.prenomAdmin);
	}
	
	@Override
	public String toString() {
		return String.format("Administrateur ID[%d], %s %s, %s", this.getId(), this.getPrenom(), this.getNom().toUpperCase(), this.compte);
	}

}
