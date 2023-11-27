package modele;

import java.sql.Date;

public class ModeleListeTournois {

	public String etatTournoi(Tournoi tournoi) {
		if (tournoi.getDateFin().compareTo(new Date(System.currentTimeMillis())) < 0) {
			return "Terminé";
		} else if (tournoi.getDateDebut().compareTo(new Date(System.currentTimeMillis())) < 0) {
			return "En cours";
		}
		return "À venir";
	}
	
	
}
