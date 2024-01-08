package scenarios;

import static org.junit.Assert.*;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Fonctions.LireCSV;
import Images.ImagesIcons;
import dao.CreateDB;
import dao.EquipeJDBC;
import dao.JoueurJDBC;
import modele.Equipe;
import modele.Joueur;
import modele.ModeleImportation;
import modele.Niveau;
import modele.Pays;
import modele.Statut;
import modele.Tournoi;
import modele.ModeleImportation.EtatEquipe;

public class TestsImportationEquipes {
	
	private Tournoi tournoi;
	private Tournoi autreTournoi;
	private String cheminCSV;
	private ModeleImportation modele;

	@Before
	public void setUp() throws Exception {
		CreateDB.main(null);
		tournoi = Tournoi.createTournoi("Tournoi1", Niveau.LOCAL, Date.valueOf(LocalDate.of(2024, 01, 01)), Date.valueOf(LocalDate.of(2024, 01, 10)), 
				Pays.FR, Statut.ATTENTE_EQUIPES, Optional.empty(), Optional.empty());
		autreTournoi = Tournoi.createTournoi("AutreTournoi", Niveau.LOCAL, Date.valueOf(LocalDate.of(2024, 02, 01)), Date.valueOf(LocalDate.of(2024, 02, 10)), 
				Pays.FR, Statut.ATTENTE_EQUIPES, Optional.empty(), Optional.empty());
		new Tournoi().ajouterTournoi(tournoi);
		cheminCSV = System.getProperty("user.dir") + "\\src\\scenarios\\testCSV_Tournoi1.csv";
		modele = new ModeleImportation();
	}

	@After
	public void tearDown() throws Exception {
		tournoi = null;
		autreTournoi = null;
	}

	@Test
	public void testAdministrateurImporteEtConfirme() throws IOException {
		modele.importerEquipesJoueurs(cheminCSV);
		EtatEquipe etat = this.modele.verifierEquipe();
		if (etat == EtatEquipe.OK) {
			this.modele.enregistrerImportation(tournoi);
			this.modele.changerStatusAVenir(tournoi);
		}
		Equipe equipeBDD = new Equipe();
		Tournoi tournoiBDD = new Tournoi();
		assertTrue(tournoi.getStatut() == Statut.A_VENIR);
		
		int nbEquipesTotal = equipeBDD.getToutesLesEquipes().size();
		int nbEquipesTournoi = tournoiBDD.getEquipesTournoi(tournoi).size();
		assertEquals(nbEquipesTotal, 4);
		assertEquals(nbEquipesTotal, nbEquipesTournoi);
		
		for (int numEquipe = 0; numEquipe < nbEquipesTournoi; numEquipe++) {
			Equipe equipe = equipeBDD.getEquipeParNom("Equipe" + (numEquipe + 1));
			List<Joueur> joueursEquipe = equipe.getJoueurs();
			assertNotNull(equipe);
			assertNotNull(joueursEquipe);
			assertEquals(joueursEquipe.size(), 5);
			for (int numJoueur = 0; numJoueur < joueursEquipe.size(); numJoueur++) {
				assertEquals(joueursEquipe.get(numJoueur).toString(), "Joueur ID[" + ((numJoueur + 1) + (numEquipe * 5)) + "] : Joueur" + ((numJoueur + 1) + (numEquipe * 5)));
			}
		}
	}
	
	@Test
	public void testFichierCSVConcerneTournoi() throws IOException {
		assertTrue(modele.fichierCSVconcerneTournoi(cheminCSV, tournoi));
		assertFalse(modele.fichierCSVconcerneTournoi(cheminCSV, autreTournoi));
	}
	
	@Test
	public void testEquipePasPresenteApplication() throws IOException {
		Equipe equipeBDD = new Equipe();
		Tournoi tournoiBDD = new Tournoi();
		
		Equipe equipeUne = new Equipe(EquipeJDBC.getNextValueSequence(), "Equipe1", 23, Pays.TW);
	    Joueur j1 = new Joueur(JoueurJDBC.getNextValueSequence(), "Joueur1", equipeUne);
	    Joueur j2 = new Joueur(JoueurJDBC.getNextValueSequence(), "Joueur2", equipeUne);
	    Joueur j3 = new Joueur(JoueurJDBC.getNextValueSequence(), "Joueur3", equipeUne);
	    Joueur j4 = new Joueur(JoueurJDBC.getNextValueSequence(), "Joueur4", equipeUne);
	    Joueur j5 = new Joueur(JoueurJDBC.getNextValueSequence(), "Joueur5", equipeUne);
	    equipeUne.ajouterJoueur(j1, j2, j3, j4, j5);
	    equipeBDD.ajouterEquipe(equipeUne);
		
		modele.importerEquipesJoueurs(cheminCSV);
		EtatEquipe etat = this.modele.verifierEquipe();
		if (etat == EtatEquipe.OK) {
			this.modele.enregistrerImportation(tournoi);
			this.modele.changerStatusAVenir(tournoi);
		}
		
		assertTrue(tournoi.getStatut() == Statut.A_VENIR);
		
		int nbEquipesTotal = equipeBDD.getToutesLesEquipes().size();
		int nbEquipesTournoi = tournoiBDD.getEquipesTournoi(tournoi).size();
		assertEquals(nbEquipesTotal, 4);
		assertEquals(nbEquipesTotal, nbEquipesTournoi);
		
		for (int numEquipe = 0; numEquipe < nbEquipesTournoi; numEquipe++) {
			Equipe equipe = equipeBDD.getEquipeParNom("Equipe" + (numEquipe + 1));
			List<Joueur> joueursEquipe = equipe.getJoueurs();
			assertNotNull(equipe);
			assertNotNull(joueursEquipe);
			assertEquals(joueursEquipe.size(), 5);
			for (int numJoueur = 0; numJoueur < joueursEquipe.size(); numJoueur++) {
				assertEquals(joueursEquipe.get(numJoueur).toString(), "Joueur ID[" + ((numJoueur + 1) + (numEquipe * 5)) + "] : Joueur" + ((numJoueur + 1) + (numEquipe * 5)));
			}
		}
	}
}
