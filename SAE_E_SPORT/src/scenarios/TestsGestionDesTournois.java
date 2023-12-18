package scenarios;

import static org.junit.Assert.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import modele.Niveau;
import modele.Pays;
import modele.Tournoi;

public class TestsGestionDesTournois {
	
	private Tournoi modele;
	private Date janvier1;
	private Date janvier5;
	private Date janvier10;
	private Date fevrier5;
	private Date fevrier10;
	private Date dateJour;
	private Date dateJourPlus1;

	@BeforeClass
	public void setUp() throws Exception {
		modele = new Tournoi();
		janvier1 = Date.valueOf(LocalDate.of(2024, 01, 01));
		janvier5 = Date.valueOf(LocalDate.of(2024, 01, 5));
		janvier10 = Date.valueOf(LocalDate.of(2024, 01, 10));
		fevrier5 = Date.valueOf(LocalDate.of(2024, 02, 5));
		fevrier10 = Date.valueOf(LocalDate.of(2024, 02, 10));
		dateJour = Date.valueOf(LocalDate.now());
		Calendar cDateJour = Calendar.getInstance();
        cDateJour.add(Calendar.DAY_OF_MONTH, +1);
        dateJourPlus1 = new Date(cDateJour.getTimeInMillis());
	}

	@AfterClass
	public void tearDown() throws Exception {
		modele = null;
	}

	@Test
	public void testDateDeDebutSuperieurDateDuJour() {
		Tournoi tournoi = new Tournoi("TournoiTest", Niveau.LOCAL, dateJourPlus1, dateJour, Pays.FR);
		//assert
	}

}
