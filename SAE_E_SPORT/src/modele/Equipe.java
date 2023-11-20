package modele;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Equipe {

	private int idEquipe;
	private String nom;
	private int rang;
	private Pays nationalite;
	private List<Joueur> joueurs;
	
	public Equipe(int id, String nom, int rang, Pays nationalite) {
		this.idEquipe = id;
		this.nom = nom;
		this.rang = rang;
		this.nationalite = nationalite;
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

	public Pays getNationalite() {
		return this.nationalite;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o==this) return true;
		if (o==null) return false;
		if(o instanceof Equipe) {
			Equipe e = (Equipe) o;
			return this.idEquipe == e.getIdEquipe() && this.nom==e.nom && this.rang == e.rang
					&& this.nationalite == e.nationalite && this.joueurs.equals(e.joueurs);
		} else {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.idEquipe);
	}
	
	@Override
	public String toString() {
		return "Equipe [ID : "+ this.idEquipe + ", nom=" + this.nom + ", rang=" + this.rang + ", nationalite=" + this.nationalite +
				", joueurs=" + this.joueurs;
	}
	
}
