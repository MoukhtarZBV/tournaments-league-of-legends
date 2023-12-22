package modele;

import java.util.ArrayList;
import java.util.List;

import dao.AssocierJDBC;

public class Associer {
	
	private Arbitre arbitre;
	private Tournoi tournoi;
	private AssocierJDBC jdbc;
	
	public Associer(Arbitre arbitre, Tournoi tournoi) {
		this.arbitre = arbitre;
		this.tournoi = tournoi;
	}
	
	public Associer() {
		this.jdbc = new AssocierJDBC();
	}
	
	public Arbitre getArbitre() {
		return this.arbitre;
	}
	public Tournoi getTournoi() {
		return this.tournoi;
	}
	
	public List<Associer> toutesLesAssociations(){
		List<Associer> associations = new ArrayList<>();
		associations = this.jdbc.getAll();
		return associations;
	}
	
	@Override 
	public String toString() {
		return "Associer = [arbitre=" + this.arbitre.getId() + ", tournoi=" + this.tournoi.getNomTournoi() +"]\n";
	}
}
