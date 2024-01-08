package modele;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import dao.ArbitreJDBC;
import dao.EquipeJDBC;

public class Arbitre {
	
	private int idArbitre;
	private String nomArbitre;
	private String prenomArbitre;
	private Compte compte;
	
	private ArbitreJDBC jdbc;
	
	// Constructeur
	public Arbitre(int id, String nomArbitre, String prenomArbitre) {
		this.idArbitre = id;
		this.nomArbitre = nomArbitre;
		this.prenomArbitre = prenomArbitre;
		this.compte = null;
	}
	
	public Arbitre() {
		this.jdbc = new ArbitreJDBC();
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
	
	public Compte getCompte() {
		return this.compte;
	}
	
	// Set
	public void setNom(String nomArbitre) {
		this.nomArbitre = nomArbitre;
	}
	
	public void setPrenom(String prenomArbitre) {
		this.prenomArbitre = prenomArbitre;
	}
	
	public void setCompte(Compte compte) {
		this.compte = compte;
	}
	
	// Overrides
	@Override
	public boolean equals(Object o) {
		if (o==null) return false;
		if (o==this) return true;
		if(o instanceof Arbitre) {
			Arbitre a = (Arbitre) o;
			return this.idArbitre == a.idArbitre && this.nomArbitre == a.nomArbitre && this.prenomArbitre == a.prenomArbitre; 
		} else {
			return false;
		}
	}
	
	@Override 
	public int hashCode() {
		return Objects.hash(this.idArbitre, this.nomArbitre, this.prenomArbitre);
	}
	
	@Override
	public String toString() {
		return String.format("Arbitre ID[%d], %s %s", this.getId(), this.getPrenom(), this.getNom().toUpperCase());
	}	

	// ==================== //
	// ==== Partie DAO ==== //
	// ==================== //

	public List<Arbitre> getTousLesArbitres(){
		return jdbc.getAll();
	}

	public Arbitre getArbitreParID(Integer id) {
		return jdbc.getById(id).orElse(null);
	}

	public void ajouterArbitre(Arbitre arbitre) {
		jdbc.add(arbitre);
	}

	public void mettreAJourArbitre(Arbitre arbitre) {
		jdbc.update(arbitre);
	}

	public void supprimerArbitre(Arbitre arbitre) {
		jdbc.delete(arbitre);
	}
	
	public Arbitre getByNomPrenom(String nom, String prenom){
		return jdbc.getByNomPrenom(nom, prenom).orElse(null);
	}
}
