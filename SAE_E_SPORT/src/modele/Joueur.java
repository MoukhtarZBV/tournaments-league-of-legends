package modele;

import java.util.List;
import java.util.Objects;

import dao.EquipeJDBC;
import dao.JoueurJDBC;

public class Joueur {
	
	private int idJoueur;
	private String pseudo;
	private Equipe equipe;
	
	private JoueurJDBC jdbc;
	
	// Constructeur
	public Joueur(int id, String pseudo, Equipe e) {
		this.idJoueur = id;
		this.pseudo = pseudo;
		this.equipe = e;
	}
	
	public Joueur() {
		this.jdbc = new JoueurJDBC();
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
			return this.pseudo.equals(j.pseudo);
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
		return String.format("Joueur ID[%d] : %s", this.getId(), this.getPseudo());
	}

	// ==================== //
	// ==== Partie DAO ==== //
	// ==================== //

	public List<Joueur> getTousLesJoueurs(){
		return jdbc.getAll();
	}

	public Joueur getJoueurParID(Integer id) {
		return jdbc.getById(id).orElse(null);
	}

	public void ajouterJoueur(Joueur joueur) {
		jdbc.add(joueur);
	}

	public void mettreAJourJoueur(Joueur joueur) {
		jdbc.update(joueur);
	}

	public void supprimerJoueur(Joueur joueur) {
		jdbc.delete(joueur);
	}
	
	public Joueur getJoueurParPseudo(String pseudo) {
		return jdbc.getByPseudo(pseudo).orElse(null);
	}
	
	public List<Joueur> getJoueursEquipe(Equipe equipe){
		return jdbc.getByEquipe(equipe);
	}
	
	public boolean presentDansAutreEquipe() {
		Joueur j = jdbc.getByPseudo(this.pseudo).orElse(null);
		return !j.getEquipe().equals(this.getEquipe());
	}
}
