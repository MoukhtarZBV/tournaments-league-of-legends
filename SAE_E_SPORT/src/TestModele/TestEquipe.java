package TestModele;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dao.CreateDB;
import dao.EquipeJDBC;
import dao.JoueurJDBC;
import modele.Equipe;
import modele.Joueur;
import modele.Pays;

public class TestEquipe {

	private Equipe e1;
	private Equipe e2;
	private Equipe e3;
	private Equipe e5;
	
	@Before
	public void setUp() throws Exception {
		this.e1 = new Equipe(EquipeJDBC.getNextValueSequence(), "My1", 1000, Pays.FR);
		this.e2 = new Equipe(EquipeJDBC.getNextValueSequence(), "My2", 1000, Pays.FR);
		this.e3 = new Equipe(EquipeJDBC.getNextValueSequence(), "My3", 1000, Pays.FR);
		this.e5 = new Equipe(EquipeJDBC.getNextValueSequence(), "My5", 100, Pays.TW);
	}

	@After
	public void tearDown() throws Exception {
		this.e1 = null;
		this.e2 = null;
		this.e3 = null;
		this.e5 = null;
	}

	@Test
	public void testAllEquipes() throws Exception {
		CreateDB.main(null);
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
		this.e3.ajouterJoueur(new Joueur(JoueurJDBC.getNextValueSequence(), "J31", this.e3), 
								new Joueur(JoueurJDBC.getNextValueSequence(), "J32", this.e3), 
								new Joueur(JoueurJDBC.getNextValueSequence(), "J33", this.e3), 
								new Joueur(JoueurJDBC.getNextValueSequence(), "J34", this.e3), 
								new Joueur(JoueurJDBC.getNextValueSequence(), "J35", this.e3));
		
		EquipeJDBC edb = new EquipeJDBC();
		edb.add(this.e1);
		edb.add(this.e2);
		edb.add(this.e3);
		
		List<Equipe> equipes = new LinkedList<>();
		equipes.add(e1);
		equipes.add(e2);
		equipes.add(e3);
		
		assertTrue(e1.equipeExistante(e1));
		assertFalse(e5.equipeExistante(e5));
//		assertTrue(Equipe.toutesLesEquipes().equals(equipes));
	}

}
