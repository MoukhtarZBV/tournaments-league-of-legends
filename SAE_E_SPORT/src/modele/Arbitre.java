package modele;

import java.util.List;
import java.util.Objects;

import dao.ArbitreJDBC;
import dao.EquipeJDBC;

public class Arbitre {
	
	private int idArbitre;
	private String nomArbitre;
	private String prenomArbitre;
	private ArbitreJDBC jdbc;
	
	// Constructeur
	public Arbitre(int id, String nomArbitre, String prenomArbitre) {
		this.idArbitre = id;
		this.nomArbitre = nomArbitre;
		this.prenomArbitre = prenomArbitre;
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
	
	// Set
	public void setNom(String nomArbitre) {
		this.nomArbitre = nomArbitre;
	}
	
	public void setPrenom(String prenomArbitre) {
		this.prenomArbitre = prenomArbitre;
	}
	
	public void ajouterArbitre(Arbitre a) {
		try {
			this.jdbc.add(a);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<Arbitre> tousLesArbitres(){
		List<Arbitre> arbitres = null;
		try {
			arbitres =  jdbc.getAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return arbitres;
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
}
