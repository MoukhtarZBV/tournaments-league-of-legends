package TestModele;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dao.CreateDB;
import dao.EquipeJDBC;
import dao.JoueurJDBC;
import modele.Equipe;
import modele.Joueur;
import modele.Pays;

public class TestJoueur {

	private Equipe e;
	private Joueur j1;
	private Joueur j2;
	
	@Before
	public void setUp() throws Exception {
		CreateDB.main(null);
		this.e = new Equipe(EquipeJDBC.getNextValueSequence(), "My1", 1000, Pays.FR);
		this.e.ajouterJoueur(new Joueur(JoueurJDBC.getNextValueSequence(), "Zeus", this.e), 
				new Joueur(JoueurJDBC.getNextValueSequence(), "Oner", this.e), 
				new Joueur(JoueurJDBC.getNextValueSequence(), "Faker", this.e), 
				new Joueur(JoueurJDBC.getNextValueSequence(), "Guma", this.e), 
				new Joueur(JoueurJDBC.getNextValueSequence(), "Keria", this.e));
		new EquipeJDBC().add(this.e);
		this.j1 = new Joueur(JoueurJDBC.getNextValueSequence(), "Oner", new Equipe(EquipeJDBC.getNextValueSequence(), "My2", 500, Pays.TW));
		this.j2 = new Joueur(JoueurJDBC.getNextValueSequence(), "Faker", e);
	}

	@After
	public void tearDown() throws Exception {
		this.e = null;
		this.j1 = null;
		this.j2 = null;
	}

	@Test
	public void test() throws Exception {
		assertTrue(this.j1.presentDansAutreEquipe());
		assertFalse(this.j2.presentDansAutreEquipe());
	}

}
