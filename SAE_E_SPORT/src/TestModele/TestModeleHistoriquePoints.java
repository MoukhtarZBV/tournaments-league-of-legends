package TestModele;

import static org.junit.Assert.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dao.ArbitreJDBC;
import dao.CompteJDBC;
import dao.CreateDB;
import dao.EquipeJDBC;
import dao.JoueurJDBC;
import modele.Arbitre;
import modele.Compte;
import modele.Equipe;
import modele.Joueur;
import modele.ModeleHistoriquePoints;
import modele.ModeleImportation;
import modele.ModeleListeEquipes;
import modele.Niveau;
import modele.Participer;
import modele.Pays;
import modele.Statut;
import modele.Tournoi;
import modele.TypeCompte;

public class TestModeleHistoriquePoints {

	private Equipe e1;
	private Equipe e2;
	private Equipe e3;
	private Equipe e5;
	private List<Equipe> equipes;
	private Equipe modeleEquipe;
	private ModeleHistoriquePoints modeleHistorique;
	
	@Before
	public void setUp() throws Exception {
		this.modeleEquipe = new Equipe();
		this.e1 = new Equipe(EquipeJDBC.getNextValueSequence(), "My1", 1, Pays.FR);
		this.e2 = new Equipe(EquipeJDBC.getNextValueSequence(), "My2", 2, Pays.FR);
		this.e3 = new Equipe(EquipeJDBC.getNextValueSequence(), "My3", 3, Pays.FR);
		this.e5 = new Equipe(EquipeJDBC.getNextValueSequence(), "My5", 4, Pays.TW);
		this.equipes = new LinkedList<>();
		this.equipes.add(e1);
		this.equipes.add(e2);
		this.equipes.add(e3);
		this.equipes.add(e5);
		prepareDatas();
		this.modeleHistorique = new ModeleHistoriquePoints();
	}

	@After
	public void tearDown() throws Exception {
		this.e1 = null;
		this.e2 = null;
		this.e3 = null;
		this.e5 = null;
		this.equipes = null;
		this.modeleEquipe = null;
		this.modeleHistorique = null;
	}

	@Test
	public void testGetEquipesEtFilteredEquipes() {
		assertEquals(this.modeleEquipe.getToutesLesEquipes(), this.modeleHistorique.getEquipes());
		assertEquals(this.modeleEquipe.getToutesLesEquipes(), this.modeleHistorique.getFilteredEquipes());
	}
	
	@Test
	public void testFilterEquipes() {
		// test sans texte
		this.modeleHistorique.filterEquipes("");
		assertEquals(this.modeleEquipe.getToutesLesEquipes(), this.modeleHistorique.getFilteredEquipes());
		// test sans resultat
		this.modeleHistorique.filterEquipes("[]21323asdqwl[");
		assertEquals(this.modeleHistorique.getFilteredEquipes().size(),0);
		// test texte commun
		this.modeleHistorique.filterEquipes("My");
		assertEquals(this.modeleHistorique.getFilteredEquipes().size(),4);
		// test texte specific
		this.modeleHistorique.filterEquipes("My2");
		assertEquals(this.modeleHistorique.getFilteredEquipes().size(),1);
	}
	
	@Test
	public void testResetEquipes() {
		this.modeleHistorique.filterEquipes("My2");
		assertEquals(this.modeleHistorique.getFilteredEquipes().size(), 1);
		this.modeleHistorique.resetEquipes();
		assertEquals(this.modeleHistorique.getFilteredEquipes().size(), 4);
	}
	
	@Test
	public void testPointsTournoiParEquipe() {
		// Tournoi 1
		Tournoi tournoi1 = Tournoi.createTournoi("Happy League1", Niveau.LOCAL, Date.valueOf(LocalDate.of(2024, 1, 13)), 
				Date.valueOf(LocalDate.of(2024, 1, 18)), Pays.FR, Statut.EN_COURS, Optional.empty(), Optional.empty());
		Tournoi modeleTournoi = new Tournoi();
		Arbitre modeleArbitre = new Arbitre();
		List<Arbitre> arbs = new LinkedList<>();
		Arbitre arb1 = new Arbitre(ArbitreJDBC.getNextValueSequence() , "Koh1", "You Chen1");
		Arbitre arb2 = new Arbitre(ArbitreJDBC.getNextValueSequence() , "Koh2", "You Chen2");
		Arbitre arb3 = new Arbitre(ArbitreJDBC.getNextValueSequence() , "Koh3", "You Chen3");
		arbs.add(arb1);
		arbs.add(arb2);
		arbs.add(arb3);
		for (Arbitre arb : arbs) {
			modeleArbitre.ajouterArbitre(arb);
		}
		List<Arbitre> arbitres = new Arbitre().getTousLesArbitres();
        Collections.shuffle(arbitres);
        Compte c = new Compte("arbitre", "youinfo", TypeCompte.ARBITRE);
        Compte modeleCompte = new Compte();
        modeleCompte.ajouterCompte(c);
        tournoi1.setCompte(c);
		modeleTournoi.ajouterTournoi(tournoi1);
        List<Arbitre> arbitresAttribuer = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Arbitre arb = arbitres.get(i);
            arb.setCompte(c);
            new Arbitre().mettreAJourArbitre(arb);
            arbitresAttribuer.add(arb);
        }
        modeleTournoi.associerArbitresTournoi(tournoi1, arbitresAttribuer);

		Participer modelePart = new Participer();
		for (Equipe equipe : equipes) {
			Participer participer = new Participer(equipe, tournoi1);
			modelePart.ajouterParticipation(participer);
		}
		List<Participer> participations = modelePart.getToutesLesParticipations().stream().filter(p->p.getTournoi().getNomTournoi().equals(tournoi1.getNomTournoi())).collect(Collectors.toList());
		for (int i=0, point=10;i<participations.size();i++, point+=10) {
			participations.get(i).setNbPointsTournoiGagnes(point);
			modelePart.mettreAJourParticipation(participations.get(i));
		}
		
		// Tournoi 2
		Tournoi tournoi2 = Tournoi.createTournoi("Happy League2", Niveau.LOCAL, Date.valueOf(LocalDate.of(2024, 1, 20)), 
				Date.valueOf(LocalDate.of(2024, 1, 26)), Pays.FR, Statut.EN_COURS, Optional.empty(), Optional.empty());
        tournoi2.setCompte(c);
		modeleTournoi.ajouterTournoi(tournoi2);
        modeleTournoi.associerArbitresTournoi(tournoi2, arbitresAttribuer);
		for (Equipe equipe : equipes) {
			Participer participer = new Participer(equipe, tournoi2);
			modelePart.ajouterParticipation(participer);
		}
		participations = modelePart.getToutesLesParticipations().stream().filter(p->p.getTournoi().getNomTournoi().equals(tournoi2.getNomTournoi())).collect(Collectors.toList());
		for (int i=0, point=10;i<participations.size();i++, point+=10) {
			participations.get(i).setNbPointsTournoiGagnes(point);
			modelePart.mettreAJourParticipation(participations.get(i));
		}
		
		// Tournoi 3
		Tournoi tournoi3 = Tournoi.createTournoi("Happy League3", Niveau.LOCAL, Date.valueOf(LocalDate.of(2024, 2, 1)), 
				Date.valueOf(LocalDate.of(2024, 2, 8)), Pays.FR, Statut.EN_COURS, Optional.empty(), Optional.empty());
        tournoi3.setCompte(c);		
        modeleTournoi.ajouterTournoi(tournoi3);
        modeleTournoi.associerArbitresTournoi(tournoi3, arbitresAttribuer);
		for (Equipe equipe : equipes) {
			Participer participer = new Participer(equipe, tournoi3);
			modelePart.ajouterParticipation(participer);
		}
		participations = modelePart.getToutesLesParticipations().stream().filter(p->p.getTournoi().getNomTournoi().equals(tournoi3.getNomTournoi())).collect(Collectors.toList());
		for (int i=0, point=10;i<participations.size();i++, point+=10) {
			participations.get(i).setNbPointsTournoiGagnes(point);
			modelePart.mettreAJourParticipation(participations.get(i));
		}
		
		modeleHistorique = new ModeleHistoriquePoints();
		Map<Tournoi, Integer> point1 = modeleHistorique.pointsTournoiParEquipe(e1);
		int point = 10;
		for (Entry<Tournoi, Integer> p1 : point1.entrySet()) {
			assertTrue(p1.getValue() == point);
		}
		
		Map<Tournoi, Integer> point2 = modeleHistorique.pointsTournoiParEquipe(e2);
		point = 20;
		for (Entry<Tournoi, Integer> p2 : point2.entrySet()) {
			assertTrue(p2.getValue() == point);
		}
		
		Map<Tournoi, Integer> point3 = modeleHistorique.pointsTournoiParEquipe(e3);
		point = 30;
		for (Entry<Tournoi, Integer> p3 : point3.entrySet()) {
			assertTrue(p3.getValue() == point);
		}		
	}
	
	private void prepareDatas() throws Exception {
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
		this.e5.ajouterJoueur(new Joueur(JoueurJDBC.getNextValueSequence(), "J51", this.e5), 
								new Joueur(JoueurJDBC.getNextValueSequence(), "J52", this.e5), 
								new Joueur(JoueurJDBC.getNextValueSequence(), "J53", this.e5), 
								new Joueur(JoueurJDBC.getNextValueSequence(), "J54", this.e5), 
								new Joueur(JoueurJDBC.getNextValueSequence(), "J55", this.e5));
		for (Equipe eq : this.equipes) {
			modeleEquipe.ajouterEquipe(eq);
		}
	}

}
