package modele;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import dao.EquipeJDBC;
import dao.TournoiJDBC;

public class Equipe {

	private int idEquipe;
	private String nom;
	private int rang;
	private Pays nationalite;
	private List<Joueur> joueurs;
	
	private EquipeJDBC jdbc;
	
	public Equipe(int id, String nom, int rang, Pays nationalite) {
		this.idEquipe = id;
		this.nom = nom;
		this.rang = rang;
		this.nationalite = nationalite;
		this.joueurs = new LinkedList<>();
	}
	
	public Equipe() {
		this.jdbc = new EquipeJDBC();
	}

	public int getIdEquipe() {
		return this.idEquipe;
	}

	public String getNom() {
		return this.nom;
	}
	
	public void ajouterJoueur(Joueur... j) {
		for (Joueur joueur : j) {
			this.joueurs.add(joueur);
		}
	}

	public List<Joueur> getJoueurs(){
		return this.joueurs;
	}
	
	public int getRang() {
		return this.rang;
	}

	public Pays getNationalite() {
		return this.nationalite;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o==this) return true;
		if (o==null) return false;
		if(o instanceof Equipe) {
			Equipe e = (Equipe) o;
			return this.nom.equals(e.nom);
		} else {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.nom);
	}
	
	@Override
	public String toString() {
		return "Equipe [ID : "+ this.idEquipe + ", nom=" + this.nom + ", rang=" + this.rang + ", nationalite=" + this.nationalite +
				", joueurs=" + this.joueurs + "\n";
	}

	// ==================== //
	// ==== Partie DAO ==== //
	// ==================== //

	public List<Equipe> getToutesLesEquipes(){
		return jdbc.getAll();
	}

	public Equipe getEquipeParID(Integer id) {
		return jdbc.getById(id).orElse(null);
	}

	public void ajouterEquipe(Equipe equipe) {
		jdbc.add(equipe);
	}

	public void mettreAJourEquipe(Equipe equipe) {
		jdbc.update(equipe);
	}

	public void supprimerEquipe(Equipe equipe) {
		jdbc.delete(equipe);
	}
	
	public Equipe getEquipeParNom(String nom) {
		return jdbc.getByNom(nom).orElse(null);
	}
	
	public int getIdByNom(String nom) {
		return jdbc.getIdByNom(nom);
	}
	
	public List<Joueur> listeJoueurs(Equipe equipe) {
		return jdbc.listeJoueurs(equipe);
	}
	
	public boolean equipeExistante(Equipe equipe) {
		return getToutesLesEquipes().contains(equipe);
	}
}
