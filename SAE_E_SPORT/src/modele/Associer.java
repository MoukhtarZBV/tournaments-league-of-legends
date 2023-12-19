package modele;

public class Associer {
	
	private Arbitre arbitre;
	private Tournoi tournoi;
	
	public Associer(Arbitre arbitre, Tournoi tournoi) {
		this.arbitre = arbitre;
		this.tournoi = tournoi;
	}
	
	public Arbitre getArbitre() {
		return this.arbitre;
	}
	public Tournoi getTournoi() {
		return this.tournoi;
	}
	
	@Override 
	public String toString() {
		return "Associer = [arbitre=" + this.arbitre.getId() + ", tournoi=" + this.tournoi.getNomTournoi() +"]\n";
	}
}
