package modele;

public class Participer implements Comparable<Participer>{

	private Equipe equipe;
	private Tournoi tournoi;
	private int nbPointsGagnes;
	private int nbMatchsJoues;
	private int nbMatchsGagnes;
	
	public Participer(Equipe equipe, Tournoi tournoi) throws IllegalArgumentException {
		this.equipe = equipe;
		this.tournoi = tournoi;
		this.nbMatchsGagnes = 0;
		this.nbMatchsJoues = 0;
		this.nbPointsGagnes = 0;
	}

	public int getNbPointsGagnes() throws IllegalArgumentException {
		return this.nbPointsGagnes;
	}

	public void setNbPointsGagnes(int nbPointsGagnes) {
		this.nbPointsGagnes = nbPointsGagnes;
	}

	public int getNbMatchsJoues() throws IllegalArgumentException{
		return this.nbMatchsJoues;
	}

	public void setNbMatchsJoues(int nbMatchsJoues) throws IllegalArgumentException {
		this.nbMatchsJoues = nbMatchsJoues;
	}

	public int getNbMatchsGagnes() {
		return this.nbMatchsGagnes;
	}

	public void setNbMatchsGagnes(int nbMatchsGagnes) {
		this.nbMatchsGagnes = nbMatchsGagnes;
	}

	public Equipe getEquipe() {
		return this.equipe;
	}

	public Tournoi getTournoi() {
		return tournoi;
	}
	
	@Override 
	public String toString() {
		return "Participer = [equipe=" + this.equipe + ", tournoi=" + this.tournoi + ", nbMatchsJoues=" + this.nbMatchsJoues +
				", nbMatchsGagnes=" + this.nbMatchsGagnes + ", nbPointsGagnes=" + this.nbPointsGagnes;
	}

	@Override
	public int compareTo(Participer p) {
		int res = this.nbPointsGagnes - p.nbPointsGagnes;
		if (res<0) return -1;
		else if (res==0) return 0;
		else return 1;
	}
	
}
