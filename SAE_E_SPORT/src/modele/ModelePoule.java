package modele;

import java.util.List;

import dao.TournoiJDBC;

public class ModelePoule {

	public ModelePoule () {
		
	}
	
	public List<Tournoi> listeTournois() throws Exception {
		return new TournoiJDBC().getAll();
	}
	
}
