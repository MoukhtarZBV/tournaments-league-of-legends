package TestModele;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dao.CreateDB;
import dao.EquipeJDBC;
import dao.JoueurJDBC;
import modele.Compte;
import modele.Equipe;
import modele.Joueur;
import modele.Pays;

public class TestEquipe {

	private Equipe e1;
	private Equipe e2;
	private Equipe modeleEquipe;
	
	@Before
	public void setUp() throws Exception {
		CreateDB.main(null);
		this.modeleEquipe = new Equipe();
		this.e1 = new Equipe(EquipeJDBC.getNextValueSequence(), "My1", 1, Pays.FR);
		this.e2 = new Equipe(EquipeJDBC.getNextValueSequence(), "My2", 2, Pays.FR);
		this.e1.ajouterJoueur(new Joueur(JoueurJDBC.getNextValueSequence(), "Zeus", this.e1), 
						new Joueur(JoueurJDBC.getNextValueSequence(), "Oner", this.e1), 
						new Joueur(JoueurJDBC.getNextValueSequence(), "Faker", this.e1), 
						new Joueur(JoueurJDBC.getNextValueSequence(), "Guma", this.e1), 
						new Joueur(JoueurJDBC.getNextValueSequence(), "Keria", this.e1));
		this.e2.ajouterJoueur(new Joueur(JoueurJDBC.getNextValueSequence(), "J21", this.e2), 
						new Joueur(JoueurJDBC.getNextValueSequence(), "J22", this.e2), 
						new Joueur(JoueurJDBC.getNextValueSequence(), "J23", this.e2), 
						new Joueur(JoueurJDBC.getNextValueSequence(), "J24", this.e2), 
						new Joueur(JoueurJDBC.getNextValueSequence(), "J25", this.e2));
		this.modeleEquipe.ajouterEquipe(this.e1);
		this.modeleEquipe.ajouterEquipe(this.e2);
	}

	@After
	public void tearDown() throws Exception {
		this.modeleEquipe = new Equipe();
		this.e1 = new Equipe(EquipeJDBC.getNextValueSequence(), "My1", 1, Pays.FR);
		this.e2 = new Equipe(EquipeJDBC.getNextValueSequence(), "My2", 2, Pays.FR);
	}

	@Test
	public void testNomVide() {
		assertTrue(this.modeleEquipe.nomEquipeIsVide("   "));
		assertTrue(this.modeleEquipe.nomEquipeIsDejaExistant("My1"));
		assertFalse(this.modeleEquipe.nomEquipeIsDejaExistant("My12345"));
	}

	@Test
	public void testNomTropLong() {
		String str = Compte.genererPassword(50);
		assertTrue(this.modeleEquipe.nomEquipeIsTropLong(str));
		str = Compte.genererPassword(10);
		assertFalse(this.modeleEquipe.nomEquipeIsTropLong(str));
	}
	
	@Test
	public void testNomExistant() {
		assertTrue(this.modeleEquipe.nomEquipeIsDejaExistant("My1"));
		assertFalse(this.modeleEquipe.nomEquipeIsDejaExistant("My12345"));
	}
	
}
