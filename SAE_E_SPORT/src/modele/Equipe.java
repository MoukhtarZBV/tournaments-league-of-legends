package modele;

import java.util.LinkedList;
import java.util.List;

public class Equipe {

	private int idEquipe;
	private String nom;
	private int rang;
	private Pays nationnalité;
	private List<Joueur> joueurs;
	
	public Equipe(int id, String nom, int rang, Pays nationnalité) {
		this.idEquipe = id;
		this.nom = nom;
		this.rang = rang;
		this.nationnalité = nationnalité;
		this.joueurs = new LinkedList<>();
	}

	public int getIdEquipe() {
		return this.idEquipe;
	}

	public String getNom() {
		return this.nom;
	}
	
	public void ajouterJoueur(Joueur j) {
		this.joueurs.add(j);
	}

	public List<Joueur> getJoueurs(){
		return this.joueurs;
	}
	
	public int getRang() {
		return this.rang;
	}

	public Pays getNationnalité() {
		return this.nationnalité;
	}
	
}
