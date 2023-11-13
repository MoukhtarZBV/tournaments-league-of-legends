package entites;

public class Equipe {

	private int idEquipe;
	private String nom;
	private int rang;
	private String nomPays;
	
	public Equipe(int id, String nom, int rang, String pays) {
		this.idEquipe = id;
		this.nom = nom;
		this.rang = rang;
	}

	public int getIdEquipe() {
		return this.idEquipe;
	}

	public String getNom() {
		return this.nom;
	}

	public int getRang() {
		return this.rang;
	}

	public String getPays() {
		return this.nomPays;
	}
	
}
