package scenarios;

import static org.junit.Assert.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Images.ImagesIcons;
import dao.ArbitreJDBC;
import dao.CompteJDBC;
import dao.CreateDB;
import dao.EquipeJDBC;
import dao.JoueurJDBC;
import modele.Arbitre;
import modele.Compte;
import modele.Equipe;
import modele.Joueur;
import modele.ModelePoule;
import modele.Niveau;
import modele.Participer;
import modele.Pays;
import modele.Statut;
import modele.Tournoi;
import modele.TypeCompte;

public class TestsGestionDesMatchs {

	private CloturerSimulationBouton bouton;
	private ModelePoule modelePoule;
	private Tournoi t;
	
	@Before
	public void setUp() throws Exception {
		CreateDB.main(null);
		creerDonnees();
	}

	@After
	public void tearDown() throws Exception {
		this.bouton = null;
		this.modelePoule = null;
		this.t = null;
	}

	@Test
	public void testCloturationPouleLosqueTousLesMatchsOntUnVainqueur() throws Exception {
		assertFalse(this.bouton.getEnabled());
		for(int i=0; i<this.modelePoule.matches().length; i++) {
			this.modelePoule.updateGagnant(i, 1);
		}
		cloturerPoule();
		assertTrue(this.bouton.getEnabled());
	}
	
	@Test
	public void testTentativeSelectionDeuxEquipesVainqueurs() throws Exception {
		this.modelePoule.updateGagnant(0, 1);
		Object[][] matchs = this.modelePoule.matches();
		assertEquals((String)matchs[0][4], ImagesIcons.TROPHY_WIN);
		this.modelePoule.updateGagnant(0, 2);
		matchs = this.modelePoule.matches();
		assertEquals((String)matchs[0][4], ImagesIcons.TROPHY);
		assertEquals((String)matchs[0][5], ImagesIcons.TROPHY_WIN);
	}

	private void cloturerPoule() {
		if (this.modelePoule.tousLesMatchsJouees()) {
			this.bouton.setEnabled(true);
		}
	}
	
	private class CloturerSimulationBouton{
		private boolean enabled;
		
		public CloturerSimulationBouton(){
			this.enabled = false;
		}
		
		public void setEnabled (boolean enabled) {
			this.enabled = enabled;
		}
		
		public boolean getEnabled() {
			return this.enabled;
		}
	}
	
	private void creerDonnees() {
		this.t = Tournoi.createTournoi("Asia Esports Championsh3", Niveau.INTERNATIONAL_CLASSE, Date.valueOf(LocalDate.of(2024, 01, 10)), 
                Date.valueOf(LocalDate.of(2024, 01, 22)), Pays.JP, Statut.A_VENIR, Optional.empty(), Optional.empty());
		Tournoi tournoiBDD = new Tournoi();
		tournoiBDD.ajouterTournoi(t);
		
		Equipe equipeBDD = new Equipe();
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
		List<Equipe> equipes = new LinkedList<>();
		equipes.add(e1);
		equipes.add(e2);
		equipes.add(e3);
		equipes.add(e4);
		for (Equipe equipe : equipes) {
			Participer participer = new Participer(equipe, t);
			new Participer().ajouterParticipation(participer);
		}
		Arbitre arb1 = new Arbitre(ArbitreJDBC.getNextValueSequence(), "Yoush", "OPOP");
		Arbitre arb2 = new Arbitre(ArbitreJDBC.getNextValueSequence(), "Youshi", "OP");
		Arbitre arb3 = new Arbitre(ArbitreJDBC.getNextValueSequence(), "Max", "Chef");
		Arbitre arb4 = new Arbitre(ArbitreJDBC.getNextValueSequence(), "Poppy", "Cheve");
		Arbitre modeleArbitre = new Arbitre();
		modeleArbitre.ajouterArbitre(arb1);
		modeleArbitre.ajouterArbitre(arb2);
		modeleArbitre.ajouterArbitre(arb3);
		modeleArbitre.ajouterArbitre(arb4);
        List<Arbitre> arbitres = new Arbitre().getTousLesArbitres();
        Collections.shuffle(arbitres);
        Compte c = new Compte("arbitre", "youinfo", TypeCompte.ARBITRE);
        CompteJDBC cbdd = new CompteJDBC();
        cbdd.add(c);
        List<Arbitre> arbitresAttribuer = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Arbitre arb = arbitres.get(i);
            arb.setCompte(c);
            new Arbitre().mettreAJourArbitre(arb);
            arbitresAttribuer.add(arb);
        }
        tournoiBDD.associerArbitresTournoi(t, arbitresAttribuer);        
        t.generationPoule();
		this.bouton = new CloturerSimulationBouton();
		this.modelePoule = new ModelePoule(t);
		tournoiBDD.changerStatutTournoi(this.t, Statut.EN_COURS);
	}
	
	
}
