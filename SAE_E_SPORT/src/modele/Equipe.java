package modele;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

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
	
	public boolean equipeExistante() throws Exception {
		EquipeJDBC edb = new EquipeJDBC();
		return edb.getAll().contains(this);
	}
	
	public List<Equipe> toutesLesEquipes(){
		List<Equipe> eq = null;
		try {
			eq =  jdbc.getAll();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return eq;
		
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
	
}
