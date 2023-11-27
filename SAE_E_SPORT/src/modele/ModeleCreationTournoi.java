package modele;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;

public class ModeleCreationTournoi {

	public ModeleCreationTournoi() {}
	
	public boolean moinsDeDeuxSemainesEntreDates(Date dateDebut, Date dateFin) {
		return Math.abs(dateFin.getTime() - dateDebut.getTime()) < 1.21e+9; 
	}
	
	public boolean anneePourSaisonEnCours(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR) == LocalDate.now().getYear();
	}
	
	public static boolean estTournoiDisjoint(Date dateDebutT1, Date dateFinT1, Date dateDebutT2, Date dateFinT2) {
		if ((dateDebutT1.compareTo(dateDebutT2) >= 0 && dateDebutT1.compareTo(dateFinT2) < 0) ||
			(dateFinT1.compareTo(dateDebutT2) > 0 && dateFinT1.compareTo(dateFinT2) <= 0) ||
			(dateDebutT2.compareTo(dateDebutT1) >= 0 && dateDebutT2.compareTo(dateFinT1) < 0)) {
			return false;
		}
		return true;
	}
}
