package ihm;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;

import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

public class Utilitaires {

	public static final EmptyBorder EMPTY_BORDER_BOUTONS = new EmptyBorder(5, 20, 5, 20);
	public static final CompoundBorder BORDER_BOUTONS = new CompoundBorder(new MatteBorder(1, 1, 1, 1, Palette.WHITE), Utilitaires.EMPTY_BORDER_BOUTONS);
	public static final CompoundBorder BORDER_BOUTONS_DANGEREUX = new CompoundBorder(new MatteBorder(1, 1, 1, 1, Palette.ERREUR), Utilitaires.EMPTY_BORDER_BOUTONS);
	
	public static String formaterDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH)) + "/" + String.format("%02d", calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR);
	}
	
	public static LocalDate convertirTexteEnDate(String date) {
		int indexSeparateur = date.indexOf('/');
		int jour = Integer.valueOf(date.substring(0, indexSeparateur));
		
		date = date.substring(indexSeparateur + 1);
		indexSeparateur = date.indexOf('/');
		int mois = Integer.valueOf(date.substring(0, indexSeparateur));
		
		date = date.substring(indexSeparateur);
		int annee = Integer.valueOf(date.substring(1));
		
		LocalDate localDate = LocalDate.of(annee, mois, jour);
		return localDate;
	}
}
