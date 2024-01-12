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
import dao.EquipeJDBC;
import dao.InsertionDB;
import dao.JoueurJDBC;
import modele.Arbitre;
import modele.Equipe;
import modele.Joueur;
import modele.ModeleImportation;
import modele.Niveau;
import modele.Partie;
import modele.Pays;
import modele.Statut;
import modele.Tournoi;
import modele.ModeleImportation.EtatEquipe;
import modele.ModelePoule;

public class TestsGestionDesTournois {
	
	private Tournoi modele;
	private Tournoi tournoi;
	
	Equipe equipeBDD = new Equipe();
	Tournoi tournoiBDD = new Tournoi();
	Arbitre arbitreBDD = new Arbitre();
	
	private Date dateJour;
	private Date dateJourPlus2;
	private Date dateJourPlus10;
	private Date dateJourPlus20;
	private Date dateJourMoins10;
	private Date dateJourAnneePlus1;
	private Date dateJourAnneePlus1Plus10Jours;
	
	private String cheminCSV;
	private ModeleImportation modeleImportation;
	private ModelePoule modelePoule;
	
	@Before
	public void setUp() {
		modele = new Tournoi();

		Calendar cDateJour = Calendar.getInstance();
		
		cDateJour.add(Calendar.DAY_OF_MONTH, +1);
		dateJour = new Date(cDateJour.getTimeInMillis());
		
		cDateJour.add(Calendar.DAY_OF_MONTH, +2);
        dateJourPlus2 = new Date(cDateJour.getTimeInMillis());
		
        cDateJour.add(Calendar.DAY_OF_MONTH, +8);
        dateJourPlus10 = new Date(cDateJour.getTimeInMillis());
        
        cDateJour.add(Calendar.DAY_OF_MONTH, +10);
        dateJourPlus20 = new Date(cDateJour.getTimeInMillis());
        
        cDateJour.add(Calendar.DAY_OF_MONTH, -30);
        dateJourMoins10 = new Date(cDateJour.getTimeInMillis());
        
        cDateJour.add(Calendar.DAY_OF_MONTH, +400);
        dateJourAnneePlus1 = new Date(cDateJour.getTimeInMillis());
        
        cDateJour.add(Calendar.DAY_OF_MONTH, +10);
        dateJourAnneePlus1Plus10Jours = new Date(cDateJour.getTimeInMillis());
        
        modele = new Tournoi();
		tournoi = Tournoi.createTournoi("Tournoi1", Niveau.LOCAL, dateJour, dateJourPlus10, 
				Pays.FR, Statut.ATTENTE_EQUIPES, Optional.empty(), Optional.empty());
		cheminCSV = System.getProperty("user.dir") + "\\src\\scenarios\\testCSV_Tournoi1.csv";
		modeleImportation = new ModeleImportation();
		modelePoule = new ModelePoule(tournoi);
	}

	@After
	public void tearDown() {
		modele = null;
		tournoi = null;
	}

	@Test
	public void testCloturationTournoiAvecVainqueur() throws Exception {
		CreateDB.main(null);
		this.modele.ajouterTournoi(tournoi);
		ajouterEquipes(tournoi);
		arbitreBDD.ajouterArbitre(new Arbitre(ArbitreJDBC.getNextValueSequence(), "Richard", "Rich"));
		assertTrue(InsertionDB.simulerParties(tournoi.generationPoule(), tournoi, arbitreBDD.getTousLesArbitres()));	    
		
		Partie finale = new Partie().getFinaleTournoi(tournoi);
		Equipe equipeGagnante = finale.getEquipeUne();
		tournoiBDD.cloturerTournoi(tournoi, equipeGagnante);
		
		// Verfication des changements
		assertEquals(equipeGagnante, tournoi.getVainqueur());
		assertEquals(Statut.TERMINE, tournoi.getStatut());
		
		// Verfication des changements dans la base de données
		Tournoi tournoiTemp = tournoiBDD.getTournoiParNom(tournoi.getNomTournoi());
		assertEquals(equipeGagnante, tournoiTemp.getVainqueur());
		assertEquals(Statut.TERMINE, tournoiTemp.getStatut());
	}
	
	@Test
	public void testOuvertureTournoiPasAssezArbitres() throws Exception {
		CreateDB.main(null);
		this.modele.ajouterTournoi(tournoi);
		assertFalse(tournoiBDD.associerArbitresTournoi(tournoi, arbitreBDD.getTousLesArbitres()));
		arbitreBDD.ajouterArbitre(new Arbitre(ArbitreJDBC.getNextValueSequence(), "Richard", "Rich"));
		assertTrue(tournoiBDD.associerArbitresTournoi(tournoi, arbitreBDD.getTousLesArbitres()));
	}
	
	@Test
	public void testModificationVainqueurAvantFinTournoi() throws Exception {
		CreateDB.main(null);
		this.modele.ajouterTournoi(tournoi);
		ajouterEquipes(tournoi);
		arbitreBDD.ajouterArbitre(new Arbitre(ArbitreJDBC.getNextValueSequence(), "Richard", "Rich"));
		assertTrue(InsertionDB.simulerParties(tournoi.generationPoule(), tournoi, arbitreBDD.getTousLesArbitres()));
		
		Partie finale = new Partie().getFinaleTournoi(tournoi);
		Equipe equipeUne = finale.getEquipeUne();
		Equipe equipeDeux = finale.getEquipeDeux();
		tournoi.setVainqueur(equipeUne);
		tournoiBDD.mettreAJourTournoi(tournoi);
		assertTrue(tournoiBDD.getTournoiParNom(tournoi.getNomTournoi()).getVainqueur().equals(equipeUne));
		tournoi.setVainqueur(equipeDeux);
		tournoiBDD.mettreAJourTournoi(tournoi);
		assertTrue(tournoiBDD.getTournoiParNom(tournoi.getNomTournoi()).getVainqueur().equals(equipeDeux));
	}
	
	@Test
	public void testCréationTournoiSurCreneauAutreTournoi() throws Exception {
		CreateDB.main(null);
		this.modele.ajouterTournoi(tournoi);
		// Ajout d'un tournoi date de fin chevauche
		Calendar cDateJour = Calendar.getInstance();
		cDateJour.setTime(dateJour);
		cDateJour.add(Calendar.DAY_OF_MONTH, -2);
		Date dateJourMoins2 = new Date(cDateJour.getTimeInMillis());
	    try {
	        Tournoi tournoi = new Tournoi("TournoiTest", Niveau.LOCAL, dateJour, dateJourPlus10, Pays.FR);
	        fail("L'exception attendue n'a pas été levée");
	    } catch (IllegalArgumentException e) {
	        assertEquals("Il existe déjà un tournoi sur ce créneau", e.getMessage());
	        return;
	    }
	    
	    // Ajout d'un tournoi date de debut chevauche
	    cDateJour.setTime(dateJour);
		cDateJour.add(Calendar.DAY_OF_MONTH, +8);
		Date dateJourPlus8 = new Date(cDateJour.getTimeInMillis());
	    try {
	        Tournoi tournoi = new Tournoi("TournoiTest", Niveau.LOCAL, dateJourPlus8, dateJourPlus20, Pays.FR);
	        fail("L'exception attendue n'a pas été levée");
	    } catch (IllegalArgumentException e) {
	        assertEquals("Il existe déjà un tournoi sur ce créneau", e.getMessage());
	        return;
	    }
	    
	    // Ajout d'un tournoi englobé par les dates d'un autre
	    try {
	        Tournoi tournoi = new Tournoi("TournoiTest", Niveau.LOCAL, dateJourPlus2, dateJourPlus8, Pays.FR);
	        fail("L'exception attendue n'a pas été levée");
	    } catch (IllegalArgumentException e) {
	        assertEquals("Il existe déjà un tournoi sur ce créneau", e.getMessage());
	        return;
	    }   
	}
	
	@Test
	public void testDateDeDebutSuperieurDateDeFin() {
		try {
			Tournoi tournoi = new Tournoi("TournoiTest", Niveau.LOCAL, dateJourPlus10, dateJour, Pays.FR);
	    } catch (IllegalArgumentException e) {
	        assertEquals("La date de début doit être inférieure à la date de fin", e.getMessage());
	        return;
	    }
	    fail("L'exception attendue n'a pas été levée");
	}
	
	@Test
	public void testDateDeDebutInferieureDateDuJour() {
	    try {
	        Tournoi tournoi = new Tournoi("TournoiTest", Niveau.LOCAL, dateJourMoins10, dateJour, Pays.FR);
	    } catch (IllegalArgumentException e) {
	        assertEquals("La date de début doit être supérieure à la date du jour", e.getMessage());
	        return;
	    }
	    fail("L'exception attendue n'a pas été levée");
	}
	
	@Test
	public void testDateDebutMemeAneeEnCours() {
	    try {
	    	System.out.println(dateJourAnneePlus1);
	        Tournoi tournoi = new Tournoi("TournoiTest", Niveau.LOCAL, dateJourAnneePlus1, dateJourAnneePlus1Plus10Jours, Pays.FR);
	    } catch (IllegalArgumentException e) {
	        assertEquals("L'année de la date doit être la même que celle en cours", e.getMessage());
	        return;
	    }
	    fail("L'exception attendue n'a pas été levée");
	}
	
	@Test
	public void testTournoiMoins5Jours() {
		try {
			Tournoi tournoi = new Tournoi("TournoiTest", Niveau.LOCAL, dateJour, dateJourPlus2, Pays.FR);
	    } catch (IllegalArgumentException e) {
	        assertEquals("Le tournoi doit durer minimum cinq jours", e.getMessage());
	        return;
	    }
	    fail("L'exception attendue n'a pas été levée");
	}
	
	@Test
	public void testTournoiPlus2Semaines() {
		try {
			Tournoi tournoi = new Tournoi("TournoiTest", Niveau.LOCAL, dateJour, dateJourPlus20, Pays.FR);
	    } catch (IllegalArgumentException e) {
	        assertEquals("Le tournoi doit durer maximum deux semaines", e.getMessage());
	        return;
	    }
	    fail("L'exception attendue n'a pas été levée");	
	}
	
	private void ajouterEquipes(Tournoi tournoi) {
		// Équipe 1 - G2 Esports
	    Equipe e1 = new Equipe(EquipeJDBC.getNextValueSequence(), "G2 Esports", 18, Pays.ES);
	    Joueur j1 = new Joueur(JoueurJDBC.getNextValueSequence(), "Wunder", e1);
	    Joueur j2 = new Joueur(JoueurJDBC.getNextValueSequence(), "Jankos", e1);
	    Joueur j3 = new Joueur(JoueurJDBC.getNextValueSequence(), "Caps", e1);
	    Joueur j4 = new Joueur(JoueurJDBC.getNextValueSequence(), "Rekkles", e1);
	    Joueur j5 = new Joueur(JoueurJDBC.getNextValueSequence(), "Mikyx", e1);
	    e1.ajouterJoueur(j1, j2, j3, j4, j5);
	    equipeBDD.ajouterEquipe(e1);

	    // Équipe 2 - T1
	    Equipe e2 = new Equipe(EquipeJDBC.getNextValueSequence(), "T1", 32, Pays.KR);
	    Joueur j6 = new Joueur(JoueurJDBC.getNextValueSequence(), "Canna", e2);
	    Joueur j7 = new Joueur(JoueurJDBC.getNextValueSequence(), "Cuzz", e2);
	    Joueur j8 = new Joueur(JoueurJDBC.getNextValueSequence(), "Faker", e2);
	    Joueur j9 = new Joueur(JoueurJDBC.getNextValueSequence(), "Teddy", e2);
	    Joueur j10 = new Joueur(JoueurJDBC.getNextValueSequence(), "Keria", e2);
	    e2.ajouterJoueur(j6, j7, j8, j9, j10);
	    equipeBDD.ajouterEquipe(e2);

	    // Équipe 3 - Cloud9
	    Equipe e3 = new Equipe(EquipeJDBC.getNextValueSequence(), "Cloud9", 59, Pays.US);
	    Joueur j11 = new Joueur(JoueurJDBC.getNextValueSequence(), "Fudge", e3);
	    Joueur j12 = new Joueur(JoueurJDBC.getNextValueSequence(), "Blaber", e3);
	    Joueur j13 = new Joueur(JoueurJDBC.getNextValueSequence(), "Perkz", e3);
	    Joueur j14 = new Joueur(JoueurJDBC.getNextValueSequence(), "Zven", e3);
	    Joueur j15 = new Joueur(JoueurJDBC.getNextValueSequence(), "Vulcan", e3);
	    e3.ajouterJoueur(j11, j12, j13, j14, j15);
	    equipeBDD.ajouterEquipe(e3);
	    
	    // Équipe 4 - Fnatic
	    Equipe e4 = new Equipe(EquipeJDBC.getNextValueSequence(), "Fnatic", 101, Pays.GB);
	    Joueur j16 = new Joueur(JoueurJDBC.getNextValueSequence(), "Bwipo", e4);
	    Joueur j17 = new Joueur(JoueurJDBC.getNextValueSequence(), "Selfmade", e4);
	    Joueur j18 = new Joueur(JoueurJDBC.getNextValueSequence(), "Nisqy", e4);
	    Joueur j19 = new Joueur(JoueurJDBC.getNextValueSequence(), "Upset", e4);
	    Joueur j20 = new Joueur(JoueurJDBC.getNextValueSequence(), "Hylissang", e4);
	    e4.ajouterJoueur(j16, j17, j18, j19, j20);
	    equipeBDD.ajouterEquipe(e4);
	    
	    InsertionDB.ajouterEquipesTournoi(tournoi, e1, e2, e3, e4);
	}

}
