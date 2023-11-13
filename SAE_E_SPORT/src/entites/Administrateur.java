package entites;

public class Administrateur {
	
	private    int idAdministrateur;
	private String nomAdmin;
	private String prenomAdmin;
	
	
	// Constructeur
	public Administrateur(int id, String nom, String prenom) {
		this.nomAdmin         = nom;
		this.prenomAdmin      = prenom;
		this.idAdministrateur = id;
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
	
	// Set
	public void setNom(String nom) {
		this.nomAdmin = nom;
	}
	
	public void setPrenom(String prenom) {
		this.prenomAdmin = prenom;
	}
	
	
	// Overrides
	@Override
    public boolean equals(Object o) {
 
        if (o == this) {
            return true;
        }
 
        if (!(o instanceof Administrateur)) {
            return false;
        }
         
        Administrateur a = (Administrateur) o;
        return a.getId() == this.getId();
    }
	
	@Override
	public String toString() {
		return String.format("Administrateur ID[%d], %s %s", this.getId(), this.getPrenom(), this.getNom().toUpperCase());
	}

}
