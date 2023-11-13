package modele;

import java.util.List;
import java.util.Map;

public class Participation {

	private List<Equipe> equipes;
	private Tournoi tournoi;
	private Map<Equipe, Integer> nbPointsGagnes;
	private Map<Equipe, Integer> nbMatchsJoues;
	private Map<Equipe, Integer> nbMatchsGagnes;
	
	public Participation(List<Equipe> equipes, Tournoi tournoi) throws IllegalArgumentException {
		if (equipes.size()<4 || equipes.size()>8) {
			throw new IllegalArgumentException ("Il faut donner entre 4 et 8 equipes !");
		}
		this.equipes = equipes;
		this.tournoi = tournoi;
		for(Equipe e : equipes) {
			this.nbMatchsGagnes.put(e, 0);
			this.nbMatchsJoues.put(e, 0);
			this.nbPointsGagnes.put(e, 0);
		}
	}

	public int getNbPointsGagnes(Equipe e) throws IllegalArgumentException {
		if (e == null) {
			throw new IllegalArgumentException("Equipe n'existe pas !");
		}
		return this.nbPointsGagnes.get(e);
	}

	public void setNbPointsGagnes(Equipe e, int nbPointsGagnes) {
		this.nbPointsGagnes.put(e,nbPointsGagnes);
	}

	public int getNbMatchsJoues(Equipe e) throws IllegalArgumentException{
		if (e == null) {
			throw new IllegalArgumentException("Equipe n'existe pas !");
		}
		return this.nbMatchsJoues.get(e);
	}

	public void setNbMatchsJoues(Equipe e, int nbMatchsJoues) throws IllegalArgumentException {
		if (e == null) {
			throw new IllegalArgumentException("Equipe n'existe pas !");
		}
		this.nbMatchsJoues.put(e, nbMatchsJoues);
	}

	public int getNbMatchsGagnes(Equipe e) {
		if (e == null) {
			throw new IllegalArgumentException("Equipe n'existe pas !");
		}
		return this.nbMatchsGagnes.get(e);
	}

	public void setNbMatchsGagnes(Equipe e, int nbMatchsGagnes) {
		this.nbMatchsGagnes.put(e, nbMatchsGagnes);
	}

	public List<Equipe> getEquipes() {
		return equipes;
	}

	public Tournoi getTournoi() {
		return tournoi;
	}
	
	
	
}
