package dao;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import modele.Equipe;
import modele.Jouer;
import modele.Joueur;
import modele.ModelePoule;
import modele.Niveau;
import modele.Participer;
import modele.Partie;
import modele.Pays;
import modele.Statut;
import modele.Tournoi;
import modele.TypeCompte;
import modele.Administrateur;
import modele.Arbitre;
import modele.Compte;

public class InsertionDB {
	
	private static Equipe equipeBDD = new Equipe();
	private static Tournoi tournoiBDD = new Tournoi();
	private static Participer participerBDD = new Participer();
	private static Partie partieBDD = new Partie();
	private static Arbitre arbitreBDD = new Arbitre();

	public static void main(String args[]) throws Exception {
		CreateDB.main(args);
		
		// ============================== //
		// ==== Création des équipes ==== //
		// ============================== //
		
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
	    
	    // Équipe 5 - Team Liquid
	    Equipe e5 = new Equipe(EquipeJDBC.getNextValueSequence(), "Team Liquid", 289, Pays.US);
	    Joueur j21 = new Joueur(JoueurJDBC.getNextValueSequence(), "Alphari", e5);
	    Joueur j22 = new Joueur(JoueurJDBC.getNextValueSequence(), "Santorin", e5);
	    Joueur j23 = new Joueur(JoueurJDBC.getNextValueSequence(), "Jensen", e5);
	    Joueur j24 = new Joueur(JoueurJDBC.getNextValueSequence(), "Tactical", e5);
	    Joueur j25 = new Joueur(JoueurJDBC.getNextValueSequence(), "CoreJJ", e5);
	    e5.ajouterJoueur(j21, j22, j23, j24, j25);
	    equipeBDD.ajouterEquipe(e5);

	    // Équipe 6 - DAMWON KIA
	    Equipe e6 = new Equipe(EquipeJDBC.getNextValueSequence(), "DAMWON KIA", 478, Pays.KR);
	    Joueur j26 = new Joueur(JoueurJDBC.getNextValueSequence(), "Khan", e6);
	    Joueur j27 = new Joueur(JoueurJDBC.getNextValueSequence(), "Canyon", e6);
	    Joueur j28 = new Joueur(JoueurJDBC.getNextValueSequence(), "ShowMaker", e6);
	    Joueur j29 = new Joueur(JoueurJDBC.getNextValueSequence(), "Ghost", e6);
	    Joueur j30 = new Joueur(JoueurJDBC.getNextValueSequence(), "BeryL", e6);
	    e6.ajouterJoueur(j26, j27, j28, j29, j30);
	    equipeBDD.ajouterEquipe(e6);

	    // Équipe 7 - Rogue
	    Equipe e7 = new Equipe(EquipeJDBC.getNextValueSequence(), "Rogue", 178, Pays.GB);
	    Joueur j31 = new Joueur(JoueurJDBC.getNextValueSequence(), "Odoamne", e7);
	    Joueur j32 = new Joueur(JoueurJDBC.getNextValueSequence(), "Inspired", e7);
	    Joueur j33 = new Joueur(JoueurJDBC.getNextValueSequence(), "Larssen", e7);
	    Joueur j34 = new Joueur(JoueurJDBC.getNextValueSequence(), "Hans sama", e7);
	    Joueur j35 = new Joueur(JoueurJDBC.getNextValueSequence(), "Trymbi", e7);
	    e7.ajouterJoueur(j31, j32, j33, j34, j35);
	    equipeBDD.ajouterEquipe(e7);

	    // Équipe 8 - EDward Gaming
	    Equipe e8 = new Equipe(EquipeJDBC.getNextValueSequence(), "EDward Gaming", 296, Pays.CN);
	    Joueur j36 = new Joueur(JoueurJDBC.getNextValueSequence(), "Flandre", e8);
	    Joueur j37 = new Joueur(JoueurJDBC.getNextValueSequence(), "Jiejie", e8);
	    Joueur j38 = new Joueur(JoueurJDBC.getNextValueSequence(), "Scout", e8);
	    Joueur j39 = new Joueur(JoueurJDBC.getNextValueSequence(), "Viper", e8);
	    Joueur j40 = new Joueur(JoueurJDBC.getNextValueSequence(), "Meiko", e8);
	    e8.ajouterJoueur(j36, j37, j38, j39, j40);
	    equipeBDD.ajouterEquipe(e8);
	    
	    // Équipe 9 - MAD Lions
	    Equipe e9 = new Equipe(EquipeJDBC.getNextValueSequence(), "MAD Lions", 126, Pays.ES);
	    Joueur j41 = new Joueur(JoueurJDBC.getNextValueSequence(), "Armut", e9);
	    Joueur j42 = new Joueur(JoueurJDBC.getNextValueSequence(), "Elyoya", e9);
	    Joueur j43 = new Joueur(JoueurJDBC.getNextValueSequence(), "Humanoid", e9);
	    Joueur j44 = new Joueur(JoueurJDBC.getNextValueSequence(), "Carzzy", e9);
	    Joueur j45 = new Joueur(JoueurJDBC.getNextValueSequence(), "Kaiser", e9);
	    e9.ajouterJoueur(j41, j42, j43, j44, j45);
	    equipeBDD.ajouterEquipe(e9);

	    // Équipe 10 - Gen.G
	    Equipe e10 = new Equipe(EquipeJDBC.getNextValueSequence(), "Gen.G", 236, Pays.KR);
	    Joueur j46 = new Joueur(JoueurJDBC.getNextValueSequence(), "Rascal", e10);
	    Joueur j47 = new Joueur(JoueurJDBC.getNextValueSequence(), "Clid", e10);
	    Joueur j48 = new Joueur(JoueurJDBC.getNextValueSequence(), "Bdd", e10);
	    Joueur j49 = new Joueur(JoueurJDBC.getNextValueSequence(), "Ruler", e10);
	    Joueur j50 = new Joueur(JoueurJDBC.getNextValueSequence(), "Life", e10);
	    e10.ajouterJoueur(j46, j47, j48, j49, j50);
	    equipeBDD.ajouterEquipe(e10);
	    
	    // Équipe 11 - Hanwha Life Esports
	    Equipe e11 = new Equipe(EquipeJDBC.getNextValueSequence(), "Hanwha Life Esports", 182, Pays.KR);
	    Joueur j51 = new Joueur(JoueurJDBC.getNextValueSequence(), "Morgan", e11);
	    Joueur j52 = new Joueur(JoueurJDBC.getNextValueSequence(), "Arthur", e11);
	    Joueur j53 = new Joueur(JoueurJDBC.getNextValueSequence(), "Chovy", e11);
	    Joueur j54 = new Joueur(JoueurJDBC.getNextValueSequence(), "Deft", e11);
	    Joueur j55 = new Joueur(JoueurJDBC.getNextValueSequence(), "Vsta", e11);
	    e11.ajouterJoueur(j51, j52, j53, j54, j55);
	    equipeBDD.ajouterEquipe(e11);

	    // Équipe 12 - 100 Thieves
	    Equipe e12 = new Equipe(EquipeJDBC.getNextValueSequence(), "100 Thieves", 100, Pays.US);
	    Joueur j56 = new Joueur(JoueurJDBC.getNextValueSequence(), "Ssumday", e12);
	    Joueur j57 = new Joueur(JoueurJDBC.getNextValueSequence(), "Closer", e12);
	    Joueur j58 = new Joueur(JoueurJDBC.getNextValueSequence(), "Abbedagge", e12);
	    Joueur j59 = new Joueur(JoueurJDBC.getNextValueSequence(), "FBI", e12);
	    Joueur j60 = new Joueur(JoueurJDBC.getNextValueSequence(), "Huhi", e12);
	    e12.ajouterJoueur(j56, j57, j58, j59, j60);
	    equipeBDD.ajouterEquipe(e12);
	    
	    // Équipe 13 - Team Vitality
	    Equipe e13 = new Equipe(EquipeJDBC.getNextValueSequence(), "Team Vitality", 11, Pays.FR);
	    Joueur j61 = new Joueur(JoueurJDBC.getNextValueSequence(), "SLT", e13);
	    Joueur j62 = new Joueur(JoueurJDBC.getNextValueSequence(), "Skeanz", e13);
	    Joueur j63 = new Joueur(JoueurJDBC.getNextValueSequence(), "Milica", e13);
	    Joueur j64 = new Joueur(JoueurJDBC.getNextValueSequence(), "Comp", e13);
	    Joueur j65 = new Joueur(JoueurJDBC.getNextValueSequence(), "Labrov", e13);
	    e13.ajouterJoueur(j61, j62, j63, j64, j65);
	    equipeBDD.ajouterEquipe(e13);

	    // Équipe 14 - Invictus Gaming
	    Equipe e14 = new Equipe(EquipeJDBC.getNextValueSequence(), "Invictus Gaming", 48, Pays.CN);
	    Joueur j66 = new Joueur(JoueurJDBC.getNextValueSequence(), "TheShy", e14);
	    Joueur j67 = new Joueur(JoueurJDBC.getNextValueSequence(), "Ning", e14);
	    Joueur j68 = new Joueur(JoueurJDBC.getNextValueSequence(), "Rookie", e14);
	    Joueur j69 = new Joueur(JoueurJDBC.getNextValueSequence(), "JackeyLove", e14);
	    Joueur j70 = new Joueur(JoueurJDBC.getNextValueSequence(), "Baolan", e14);
	    e14.ajouterJoueur(j66, j67, j68, j69, j70);
	    equipeBDD.ajouterEquipe(e14);

	    // Équipe 15 - FlyQuest
	    Equipe e15 = new Equipe(EquipeJDBC.getNextValueSequence(), "FlyQuest", 65, Pays.US);
	    Joueur j71 = new Joueur(JoueurJDBC.getNextValueSequence(), "Licorice", e15);
	    Joueur j72 = new Joueur(JoueurJDBC.getNextValueSequence(), "Josedeodo", e15);
	    Joueur j73 = new Joueur(JoueurJDBC.getNextValueSequence(), "Palafox", e15);
	    Joueur j74 = new Joueur(JoueurJDBC.getNextValueSequence(), "Johnsun", e15);
	    Joueur j75 = new Joueur(JoueurJDBC.getNextValueSequence(), "Dreams", e15);
	    e15.ajouterJoueur(j71, j72, j73, j74, j75);
	    equipeBDD.ajouterEquipe(e15);
	    
	    // Équipe 16 - Immortals
	    Equipe e16 = new Equipe(EquipeJDBC.getNextValueSequence(), "Immortals", 325, Pays.US);
	    Joueur j76 = new Joueur(JoueurJDBC.getNextValueSequence(), "Revenge", e16);
	    Joueur j77 = new Joueur(JoueurJDBC.getNextValueSequence(), "Xerxe", e16);
	    Joueur j78 = new Joueur(JoueurJDBC.getNextValueSequence(), "Insanity", e16);
	    Joueur j79 = new Joueur(JoueurJDBC.getNextValueSequence(), "Raes", e16);
	    Joueur j80 = new Joueur(JoueurJDBC.getNextValueSequence(), "Destiny", e16);
	    e16.ajouterJoueur(j76, j77, j78, j79, j80);
	    equipeBDD.ajouterEquipe(e16);

	    // Équipe 17 - Team SoloMid (TSM)
	    Equipe e17 = new Equipe(EquipeJDBC.getNextValueSequence(), "TSM", 74, Pays.US);
	    Joueur j81 = new Joueur(JoueurJDBC.getNextValueSequence(), "Huni", e17);
	    Joueur j82 = new Joueur(JoueurJDBC.getNextValueSequence(), "Spica", e17);
	    Joueur j83 = new Joueur(JoueurJDBC.getNextValueSequence(), "PowerOfEvil", e17);
	    Joueur j84 = new Joueur(JoueurJDBC.getNextValueSequence(), "Lost", e17);
	    Joueur j85 = new Joueur(JoueurJDBC.getNextValueSequence(), "SwordArt", e17);
	    e17.ajouterJoueur(j81, j82, j83, j84, j85);
	    equipeBDD.ajouterEquipe(e17);
	    
	    // Équipe 18 - Evil Geniuses
	    Equipe e18 = new Equipe(EquipeJDBC.getNextValueSequence(), "Evil Geniuses", 231, Pays.US);
	    Joueur j86 = new Joueur(JoueurJDBC.getNextValueSequence(), "Impact", e18);
	    Joueur j87 = new Joueur(JoueurJDBC.getNextValueSequence(), "Svenskeren", e18);
	    Joueur j88 = new Joueur(JoueurJDBC.getNextValueSequence(), "Jiizuke", e18);
	    Joueur j89 = new Joueur(JoueurJDBC.getNextValueSequence(), "Danny", e18);
	    Joueur j90 = new Joueur(JoueurJDBC.getNextValueSequence(), "IgNar", e18);
	    e18.ajouterJoueur(j86, j87, j88, j89, j90);
	    equipeBDD.ajouterEquipe(e18);

	    // Équipe 19 - Fnatic Rising
	    Equipe e19 = new Equipe(EquipeJDBC.getNextValueSequence(), "Fnatic Rising", 845, Pays.GB);
	    Joueur j91 = new Joueur(JoueurJDBC.getNextValueSequence(), "Adam", e19);
	    Joueur j92 = new Joueur(JoueurJDBC.getNextValueSequence(), "Nxy", e19);
	    Joueur j93 = new Joueur(JoueurJDBC.getNextValueSequence(), "MagiFelix", e19);
	    Joueur j94 = new Joueur(JoueurJDBC.getNextValueSequence(), "Botkap", e19);
	    Joueur j95 = new Joueur(JoueurJDBC.getNextValueSequence(), "Prosfair", e19);
	    e19.ajouterJoueur(j91, j92, j93, j94, j95);
	    equipeBDD.ajouterEquipe(e19);

	    // =============================== //
	    // ==== Création des arbitres ==== //
	    // =============================== //

	    arbitreBDD.ajouterArbitre(new Arbitre(ArbitreJDBC.getNextValueSequence(), "Richard", "Rich"));
	    arbitreBDD.ajouterArbitre(new Arbitre(ArbitreJDBC.getNextValueSequence(), "Saul", "Badman"));
	    arbitreBDD.ajouterArbitre(new Arbitre(ArbitreJDBC.getNextValueSequence(), "Gus", "Pollos"));
	    arbitreBDD.ajouterArbitre(new Arbitre(ArbitreJDBC.getNextValueSequence(), "Lester", "Yankton"));
	    arbitreBDD.ajouterArbitre(new Arbitre(ArbitreJDBC.getNextValueSequence(), "Water", "Write"));
	    arbitreBDD.ajouterArbitre(new Arbitre(ArbitreJDBC.getNextValueSequence(), "Gojaud", "Chateau-Roux"));
	    arbitreBDD.ajouterArbitre(new Arbitre(ArbitreJDBC.getNextValueSequence(), "Sebastien", "Lague"));
	    arbitreBDD.ajouterArbitre(new Arbitre(ArbitreJDBC.getNextValueSequence(), "Thomas", "Mustang"));
	    arbitreBDD.ajouterArbitre(new Arbitre(ArbitreJDBC.getNextValueSequence(), "Aisen", "Berg"));
	    arbitreBDD.ajouterArbitre(new Arbitre(ArbitreJDBC.getNextValueSequence(), "Bellman", "Ford"));
	    arbitreBDD.ajouterArbitre(new Arbitre(ArbitreJDBC.getNextValueSequence(), "Bezout", "Unplus"));
	    List<Arbitre> arbitres = arbitreBDD.getTousLesArbitres();

	    // =============================== //
	    // ==== Création des tournois ==== //
	    // =============================== //

	    // Tournoi 1
	    Tournoi t1 = Tournoi.createTournoi("Hitpoint Masters Winter 2023", Niveau.INTERNATIONAL, Date.valueOf(LocalDate.of(2023, 01, 13)), 
	    		Date.valueOf(LocalDate.of(2023, 01, 20)), Pays.GB, Statut.TERMINE, Optional.empty(), Optional.empty());
	    tournoiBDD.ajouterTournoi(t1);
	    ajouterEquipesTournoi(t1, e1, e4, e5, e11, e16, e17);
	    simulerParties(t1.generationPoule(), t1, arbitres);
	    simulerFinale(t1);

	    // Tournoi 2
	    Tournoi t2 = Tournoi.createTournoi("League of Legends Champions Korea", Niveau.INTERNATIONAL_CLASSE, Date.valueOf(LocalDate.of(2023, 01, 28)), 
	    		Date.valueOf(LocalDate.of(2023, 02, 8)), Pays.KR, Statut.TERMINE, Optional.empty(), Optional.empty());
	    tournoiBDD.ajouterTournoi(t2);
	    ajouterEquipesTournoi(t2, e4, e6, e1, e11, e14, e5, e2, e3);
	    simulerParties(t2.generationPoule(), t2, arbitres);
	    simulerFinale(t2);
	    
	    // Tournoi 3
	    Tournoi t3 = Tournoi.createTournoi("Superdome 2023 - Egypt", Niveau.NATIONAL, Date.valueOf(LocalDate.of(2023, 02, 10)), 
	    		Date.valueOf(LocalDate.of(2023, 02, 17)), Pays.EG, Statut.TERMINE, Optional.empty(), Optional.empty());
	    tournoiBDD.ajouterTournoi(t3);
	    ajouterEquipesTournoi(t3, e5, e11, e16, e17);
	    simulerParties(t3.generationPoule(), t3, arbitres);
	    simulerFinale(t3);
	    
	    // Tournoi 4
	    Tournoi t4 = Tournoi.createTournoi("Ignis Cup Split", Niveau.LOCAL, Date.valueOf(LocalDate.of(2023, 02, 21)), 
	    		Date.valueOf(LocalDate.of(2023, 02, 27)), Pays.KP, Statut.TERMINE, Optional.empty(), Optional.empty());
	    tournoiBDD.ajouterTournoi(t4);
	    ajouterEquipesTournoi(t4, e7, e15, e17, e3);
	    simulerParties(t4.generationPoule(), t4, arbitres);
	    simulerFinale(t4);
	    
	    // Tournoi 5
	    Tournoi t5 = Tournoi.createTournoi("Asia Esports Championship 2023", Niveau.INTERNATIONAL_CLASSE, Date.valueOf(LocalDate.of(2023, 03, 19)), 
	    		Date.valueOf(LocalDate.of(2023, 04, 02)), Pays.JP, Statut.TERMINE, Optional.empty(), Optional.empty());
	    tournoiBDD.ajouterTournoi(t5);
	    ajouterEquipesTournoi(t5, e1, e2, e8, e9, e10, e12, e13, e14);
	    simulerParties(t5.generationPoule(), t5, arbitres);
	    simulerFinale(t5);


	    // ====================================== //
	    // ==== Création des Administrateurs ==== //
	    // ====================================== //
	    
		AdminJDBC abdd = new AdminJDBC();
		CompteJDBC cbdd = new CompteJDBC();
		
		Compte c1 = new Compte("KHC4298A", "youinfo", TypeCompte.ADMINISTRATEUR);
		Compte c2 = new Compte("MAR5221A", "davinfo", TypeCompte.ADMINISTRATEUR); 
		Compte c3 = new Compte("CHE1111A", "maxinfo", TypeCompte.ADMINISTRATEUR);
		cbdd.add(c1);
		cbdd.add(c2);
		cbdd.add(c3);
		
		Administrateur a1 = new Administrateur(1, "koh", "youchen", c1);
		Administrateur a2 = new Administrateur(2, "marquet", "david", c2);
		Administrateur a3 = new Administrateur(3, "chevalier", "max", c3);
		abdd.add(a1);
		abdd.add(a2);
		abdd.add(a3);
	    
	}

	public static boolean simulerParties(int nbParties, Tournoi tournoi, List<Arbitre> arbitres) {
		ModelePoule poule = new ModelePoule(tournoi);
		int nbArbitres = new Random().nextInt(4) + 1;
		Collections.shuffle(arbitres);
		List<Arbitre> arbitresAttribuer = new ArrayList<>();
		for (int i = 0; i < nbArbitres; i++) {
			arbitresAttribuer.add(arbitres.get(i));
		}
		if (!tournoiBDD.associerArbitresTournoi(tournoi, arbitresAttribuer)) {
			return false;
		}
		int gagnant;
		for (int i = 0; i < nbParties; i++) {
			gagnant = (Math.random() <= 0.5) ? 1 : 2;
			poule.updateGagnant(i, gagnant);
		}
		poule.enregistrerResultat();
		poule.creerFinale(tournoi);
		return true;
	}

	private static void simulerFinale(Tournoi tournoi) {
		Partie finale = partieBDD.getFinaleTournoi(tournoi);
		tournoiBDD.cloturerTournoi(tournoi, (Math.random() <= 0.5) ? finale.getEquipeUne() : finale.getEquipeDeux());
	}

	public static void ajouterEquipesTournoi(Tournoi tournoi, Equipe... equipes) {
		for (Equipe equipe : equipes) {
			Participer participer = new Participer(equipe, tournoi);
			participerBDD.ajouterParticipation(participer);
		}
	}
}
