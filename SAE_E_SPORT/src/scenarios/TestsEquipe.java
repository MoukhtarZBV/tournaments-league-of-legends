package scenarios;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dao.CreateDB;
import dao.EquipeJDBC;
import dao.JoueurJDBC;
import ihm.VueEquipe;
import ihm.VueListeEquipe;
import ihm.VueTournoi;
import modele.Equipe;
import modele.Joueur;
import modele.Pays;

public class TestsEquipe {
	
	private Equipe equipeBDD;
	private Equipe e1;
	private Equipe e2;

	@Before
	public void setUp() throws Exception {
		CreateDB.main(null);
		this.equipeBDD = new Equipe();
		this.e1 = new Equipe(EquipeJDBC.getNextValueSequence(), "G2 Esports", 18, Pays.ES);
	    Joueur j1 = new Joueur(JoueurJDBC.getNextValueSequence(), "Wunder", e1);
	    Joueur j2 = new Joueur(JoueurJDBC.getNextValueSequence(), "Jankos", e1);
	    Joueur j3 = new Joueur(JoueurJDBC.getNextValueSequence(), "Caps", e1);
	    Joueur j4 = new Joueur(JoueurJDBC.getNextValueSequence(), "Rekkles", e1);
	    Joueur j5 = new Joueur(JoueurJDBC.getNextValueSequence(), "Mikyx", e1);
	    this.e1.ajouterJoueur(j1, j2, j3, j4, j5);
	    this.equipeBDD.ajouterEquipe(this.e1);
	    
	    this.e2 = new Equipe(EquipeJDBC.getNextValueSequence(), "Pipou FC", 20, Pays.ES);
	    j1 = new Joueur(JoueurJDBC.getNextValueSequence(), "Titatitutu", e2);
	    j2 = new Joueur(JoueurJDBC.getNextValueSequence(), "Etoiles", e2);
	    j3 = new Joueur(JoueurJDBC.getNextValueSequence(), "Fraise", e2);
	    j4 = new Joueur(JoueurJDBC.getNextValueSequence(), "Guill", e2);
	    j5 = new Joueur(JoueurJDBC.getNextValueSequence(), "Kenny", e2);
	    this.e2.ajouterJoueur(j1, j2, j3, j4, j5);
	    this.equipeBDD.ajouterEquipe(this.e2);
	}

	@After
	public void tearDown() throws Exception {
		this.e1 = null;
		this.equipeBDD = null;
	}

	@Test
	public void testModifNomValide() {
		this.equipeBDD.mettreAJourEquipe(new Equipe(this.e1.getIdEquipe(), "Gcorp", 18, Pays.ES));
		assertEquals(this.equipeBDD.getEquipeParID(this.e1.getIdEquipe()).getNom(),"Gcorp");
		assertFalse(this.equipeBDD.nomEquipeIsVide(this.e1.getNom()));
		assertFalse(this.equipeBDD.nomEquipeIsTropLong(this.e1.getNom()));
		assertFalse(this.equipeBDD.nomEquipeIsDejaExistant(this.e1.getNom()));
	}
	
	@Test
	public void testModifNomEquipeVide() {
		// Vide
		this.equipeBDD.mettreAJourEquipe(new Equipe(this.e1.getIdEquipe(), "", 18, Pays.ES));
		String newName = this.equipeBDD.getEquipeParID(this.e1.getIdEquipe()).getNom();
		assertTrue(this.equipeBDD.nomEquipeIsVide(newName));
		
		// Avec espace
		this.equipeBDD.mettreAJourEquipe(new Equipe(this.e1.getIdEquipe(), "        ", 18, Pays.ES));
		newName = this.equipeBDD.getEquipeParID(this.e1.getIdEquipe()).getNom();
		assertTrue(this.equipeBDD.nomEquipeIsVide(newName));
	}
	
	@Test
	public void testModifNomEquipeTropLong() {
		// 40 caractères ou plus
		this.equipeBDD.mettreAJourEquipe(new Equipe(this.e1.getIdEquipe(), "azertyuiopqsdfghjklmwxcvbnazertyuiopqsdf", 18, Pays.ES));
		String newName = this.equipeBDD.getEquipeParID(this.e1.getIdEquipe()).getNom();
		assertTrue(this.equipeBDD.nomEquipeIsTropLong(newName));
	}
	
	@Test
	public void testModifNomEquipeDejaExistant() {
		this.equipeBDD.mettreAJourEquipe(new Equipe(this.e1.getIdEquipe(), "Pipou FC", 18, Pays.ES));
		String newName = this.equipeBDD.getEquipeParID(this.e1.getIdEquipe()).getNom();
		assertTrue(this.equipeBDD.nomEquipeIsDejaExistant(newName));
	}

}
