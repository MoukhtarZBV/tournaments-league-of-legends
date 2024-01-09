package scenarios;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dao.CreateDB;
import dao.EquipeJDBC;
import dao.JoueurJDBC;
import modele.Equipe;
import modele.Joueur;
import modele.ModeleHistoriquePoints;
import modele.Pays;

public class TestsHistorique {
	
	private ModeleHistoriquePoints modele;
	private Equipe equipeBDD;
	private List<Equipe> equipes;

	@Before
	public void setUp() throws Exception {
		CreateDB.main(null);
		this.equipeBDD = new Equipe();
		this.equipes = new ArrayList<>();
		
		// Équipe 1 - T2 Esports
		Equipe e1 = new Equipe(EquipeJDBC.getNextValueSequence(), "T2 Esports", 18, Pays.ES);
	    Joueur j1 = new Joueur(JoueurJDBC.getNextValueSequence(), "Wunder", e1);
	    Joueur j2 = new Joueur(JoueurJDBC.getNextValueSequence(), "Jankos", e1);
	    Joueur j3 = new Joueur(JoueurJDBC.getNextValueSequence(), "Caps", e1);
	    Joueur j4 = new Joueur(JoueurJDBC.getNextValueSequence(), "Rekkles", e1);
	    Joueur j5 = new Joueur(JoueurJDBC.getNextValueSequence(), "Mikyx", e1);
	    e1.ajouterJoueur(j1, j2, j3, j4, j5);
	    equipeBDD.ajouterEquipe(e1);
	    this.equipes.add(e1);

	    // Équipe 2 - T1
	    Equipe e2 = new Equipe(EquipeJDBC.getNextValueSequence(), "T1", 32, Pays.KR);
	    Joueur j6 = new Joueur(JoueurJDBC.getNextValueSequence(), "Canna", e2);
	    Joueur j7 = new Joueur(JoueurJDBC.getNextValueSequence(), "Cuzz", e2);
	    Joueur j8 = new Joueur(JoueurJDBC.getNextValueSequence(), "Faker", e2);
	    Joueur j9 = new Joueur(JoueurJDBC.getNextValueSequence(), "Teddy", e2);
	    Joueur j10 = new Joueur(JoueurJDBC.getNextValueSequence(), "Keria", e2);
	    e2.ajouterJoueur(j6, j7, j8, j9, j10);
	    equipeBDD.ajouterEquipe(e2);
	    this.equipes.add(e2);

	    // Équipe 3 - Cloud9
	    Equipe e3 = new Equipe(EquipeJDBC.getNextValueSequence(), "Cloud9", 59, Pays.US);
	    Joueur j11 = new Joueur(JoueurJDBC.getNextValueSequence(), "Fudge", e3);
	    Joueur j12 = new Joueur(JoueurJDBC.getNextValueSequence(), "Blaber", e3);
	    Joueur j13 = new Joueur(JoueurJDBC.getNextValueSequence(), "Perkz", e3);
	    Joueur j14 = new Joueur(JoueurJDBC.getNextValueSequence(), "Zven", e3);
	    Joueur j15 = new Joueur(JoueurJDBC.getNextValueSequence(), "Vulcan", e3);
	    e3.ajouterJoueur(j11, j12, j13, j14, j15);
	    equipeBDD.ajouterEquipe(e3);
	    this.equipes.add(e3);
	    this.modele = new ModeleHistoriquePoints();
	}

	@After
	public void tearDown() throws Exception {
		this.modele = null;
		this.equipeBDD = null;
		this.equipes = null;
	}

	@Test
	public void testSansFiltre() {
		assertEquals(this.modele.getEquipes(),this.equipes.stream().sorted((e1,e2)->e1.getNom().compareTo(e2.getNom())).collect(Collectors.toList()));
	}
	
	@Test
	public void testAvecFiltre() {
		this.modele.filterEquipes("T");
		this.equipes.remove(new Equipe(EquipeJDBC.getNextValueSequence(), "Cloud9", 59, Pays.US));
		assertEquals(this.modele.getFilteredEquipes(),this.equipes.stream().sorted((e1,e2)->e1.getNom().compareTo(e2.getNom())).collect(Collectors.toList()));
	}
	
	@Test
	public void testResetFiltre() {
		this.modele.filterEquipes("T");
		this.equipes.remove(new Equipe(EquipeJDBC.getNextValueSequence(), "Cloud9", 59, Pays.US));
		this.modele.resetEquipes();
		this.equipes.add(new Equipe(EquipeJDBC.getNextValueSequence(), "Cloud9", 59, Pays.US));
		assertEquals(this.modele.getEquipes(),this.equipes.stream().sorted((e1,e2)->e1.getNom().compareTo(e2.getNom())).collect(Collectors.toList()));
	}

}
