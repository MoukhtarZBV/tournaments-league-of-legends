package scenarios;

import static org.junit.Assert.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Optional;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import dao.ArbitreJDBC;
import dao.CreateDB;
import modele.Arbitre;
import modele.Equipe;
import modele.ModeleImportation;
import modele.Niveau;
import modele.Pays;
import modele.Statut;
import modele.Tournoi;
import modele.ModeleImportation.EtatEquipe;
import modele.ModelePoule;

public class TestsGestionDesTournois {
	
	private Tournoi modele;
	private Tournoi tournoi;
	private Date janvier1;
	private Date janvier5;
	private Date janvier10;
	private Date fevrier5;
	private Date fevrier10;
	private Date dateJour;
	private Date dateJourPlus1;
	private String cheminCSV;
	private ModeleImportation modeleImportation;
	private ModelePoule modelePoule;

	@Before
	public void setUp() throws Exception {
		/*
		modele = new Tournoi();
		janvier1 = Date.valueOf(LocalDate.of(2024, 01, 01));
		janvier5 = Date.valueOf(LocalDate.of(2024, 01, 5));
		janvier10 = Date.valueOf(LocalDate.of(2024, 01, 10));
		fevrier5 = Date.valueOf(LocalDate.of(2024, 02, 5));
		fevrier10 = Date.valueOf(LocalDate.of(2024, 02, 10));
		*/
		
		dateJour = Date.valueOf(LocalDate.now());
		Calendar cDateJour = Calendar.getInstance();
        cDateJour.add(Calendar.DAY_OF_MONTH, +1);
        dateJourPlus1 = new Date(cDateJour.getTimeInMillis());

        modele = new Tournoi();
		CreateDB.main(null);
		tournoi = Tournoi.createTournoi("Tournoi1", Niveau.LOCAL, dateJour, dateJourPlus1, 
				Pays.FR, Statut.ATTENTE_EQUIPES, Optional.empty(), Optional.empty());
		this.modele.ajouterTournoi(tournoi);
		cheminCSV = System.getProperty("user.dir") + "\\src\\scenarios\\testCSV_Tournoi1.csv";
		modeleImportation = new ModeleImportation();
		modelePoule = new ModelePoule(tournoi);
	}

	@After
	public void tearDown() throws Exception {
		modele = null;
		tournoi = null;
	}

	@Test
	public void testDateDeDebutSuperieurDateDuJour() {
		//Tournoi tournoi = new Tournoi("TournoiTest", Niveau.LOCAL, dateJourPlus1, dateJour, Pays.FR);
		//assert
	}
	
	/* NE FONCTIONNE PAS
	@Test
	public void testCloturerSiTousLesVainqueurs() {
		// Importation de 3 arbitres
		Arbitre arbitreBDD = new Arbitre();
		arbitreBDD.ajouterArbitre(new Arbitre(ArbitreJDBC.getNextValueSequence(), "Richard", "Rich"));
	    arbitreBDD.ajouterArbitre(new Arbitre(ArbitreJDBC.getNextValueSequence(), "Saul", "Badman"));
	    arbitreBDD.ajouterArbitre(new Arbitre(ArbitreJDBC.getNextValueSequence(), "Gus", "Pollos"));
	    
	    // Importation de 4 équipes
	    modeleImportation.importerEquipesJoueurs(cheminCSV);
		EtatEquipe etat = this.modeleImportation.verifierEquipe();
		if (etat == EtatEquipe.OK) {
			this.modeleImportation.enregistrerImportation(tournoi);
			this.modeleImportation.changerStatusAVenir(tournoi);
		}
		Equipe equipeBDD = new Equipe();
		Tournoi tournoiBDD = new Tournoi();
		assertTrue(tournoi.getStatut() == Statut.A_VENIR);
		
		int nbEquipesTotal = equipeBDD.getToutesLesEquipes().size();
		int nbEquipesTournoi = tournoiBDD.getEquipesTournoi(tournoi).size();
		assertEquals(nbEquipesTotal, 4);
		assertEquals(nbEquipesTotal, nbEquipesTournoi);
		
		// generation de Poule
		this.tournoi.generationPoule();

		assertFalse(this.modelePoule.tousLesMatchsJouees());

		for (int i = 0; i<6; i++) {
			this.modelePoule.updateGagnant(i, 1);	
		}
		assertTrue (this.modelePoule.tousLesMatchsJouees());
	}
	*/

}
