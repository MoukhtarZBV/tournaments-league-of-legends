package dao;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import modele.Equipe;
import modele.Jouer;
import modele.Joueur;
import modele.Niveau;
import modele.Participer;
import modele.Partie;
import modele.Pays;
import modele.Statut;
import modele.Tournoi;
import modele.TypeCompte;
import modele.Arbitre;
import modele.Compte;

public class InsertionDB {

	public static void main(String args[]) throws Exception {
		CreateDB.main(args);
		
		EquipeJDBC eJDBC = new EquipeJDBC();
		JoueurJDBC jJDBC = new JoueurJDBC();
		CompteJDBC cJDBC = new CompteJDBC();
		TournoiJDBC tJDBC = new TournoiJDBC();
		ParticiperJDBC pJDBC = new ParticiperJDBC();
		PartieJDBC mJDBC = new PartieJDBC();
		ArbitreJDBC aJDBC = new ArbitreJDBC();
		
		// ============================== //
		// ==== Création des équipes ==== //
		// ============================== //
		
		// Équipe 1 - G2 Esports
	    Equipe e1 = new Equipe(EquipeJDBC.getNextValueSequence(), "G2 Esports", 450, Pays.ES);
	    Joueur j1 = new Joueur(JoueurJDBC.getNextValueSequence(), "Wunder", e1);
	    Joueur j2 = new Joueur(JoueurJDBC.getNextValueSequence(), "Jankos", e1);
	    Joueur j3 = new Joueur(JoueurJDBC.getNextValueSequence(), "Caps", e1);
	    Joueur j4 = new Joueur(JoueurJDBC.getNextValueSequence(), "Rekkles", e1);
	    Joueur j5 = new Joueur(JoueurJDBC.getNextValueSequence(), "Mikyx", e1);
	    e1.ajouterJoueur(j1, j2, j3, j4, j5);
	    eJDBC.add(e1);

	    // Équipe 2 - T1
	    Equipe e2 = new Equipe(EquipeJDBC.getNextValueSequence(), "T1", 500, Pays.KR);
	    Joueur j6 = new Joueur(JoueurJDBC.getNextValueSequence(), "Canna", e2);
	    Joueur j7 = new Joueur(JoueurJDBC.getNextValueSequence(), "Cuzz", e2);
	    Joueur j8 = new Joueur(JoueurJDBC.getNextValueSequence(), "Faker", e2);
	    Joueur j9 = new Joueur(JoueurJDBC.getNextValueSequence(), "Teddy", e2);
	    Joueur j10 = new Joueur(JoueurJDBC.getNextValueSequence(), "Keria", e2);
	    e2.ajouterJoueur(j6, j7, j8, j9, j10);
	    eJDBC.add(e2);

	    // Équipe 3 - Cloud9
	    Equipe e3 = new Equipe(EquipeJDBC.getNextValueSequence(), "Cloud9", 480, Pays.US);
	    Joueur j11 = new Joueur(JoueurJDBC.getNextValueSequence(), "Fudge", e3);
	    Joueur j12 = new Joueur(JoueurJDBC.getNextValueSequence(), "Blaber", e3);
	    Joueur j13 = new Joueur(JoueurJDBC.getNextValueSequence(), "Perkz", e3);
	    Joueur j14 = new Joueur(JoueurJDBC.getNextValueSequence(), "Zven", e3);
	    Joueur j15 = new Joueur(JoueurJDBC.getNextValueSequence(), "Vulcan", e3);
	    e3.ajouterJoueur(j11, j12, j13, j14, j15);
	    eJDBC.add(e3);
	    
	    // Équipe 4 - Fnatic
	    Equipe e4 = new Equipe(EquipeJDBC.getNextValueSequence(), "Fnatic", 420, Pays.GB);
	    Joueur j16 = new Joueur(JoueurJDBC.getNextValueSequence(), "Bwipo", e4);
	    Joueur j17 = new Joueur(JoueurJDBC.getNextValueSequence(), "Selfmade", e4);
	    Joueur j18 = new Joueur(JoueurJDBC.getNextValueSequence(), "Nisqy", e4);
	    Joueur j19 = new Joueur(JoueurJDBC.getNextValueSequence(), "Upset", e4);
	    Joueur j20 = new Joueur(JoueurJDBC.getNextValueSequence(), "Hylissang", e4);
	    e4.ajouterJoueur(j16, j17, j18, j19, j20);
	    eJDBC.add(e4);
	    
	    // Équipe 5 - Team Liquid
	    Equipe e5 = new Equipe(EquipeJDBC.getNextValueSequence(), "Team Liquid", 470, Pays.US);
	    Joueur j21 = new Joueur(JoueurJDBC.getNextValueSequence(), "Alphari", e5);
	    Joueur j22 = new Joueur(JoueurJDBC.getNextValueSequence(), "Santorin", e5);
	    Joueur j23 = new Joueur(JoueurJDBC.getNextValueSequence(), "Jensen", e5);
	    Joueur j24 = new Joueur(JoueurJDBC.getNextValueSequence(), "Tactical", e5);
	    Joueur j25 = new Joueur(JoueurJDBC.getNextValueSequence(), "CoreJJ", e5);
	    e5.ajouterJoueur(j21, j22, j23, j24, j25);
	    eJDBC.add(e5);

	    // Équipe 6 - DAMWON KIA
	    Equipe e6 = new Equipe(EquipeJDBC.getNextValueSequence(), "DAMWON KIA", 510, Pays.KR);
	    Joueur j26 = new Joueur(JoueurJDBC.getNextValueSequence(), "Khan", e6);
	    Joueur j27 = new Joueur(JoueurJDBC.getNextValueSequence(), "Canyon", e6);
	    Joueur j28 = new Joueur(JoueurJDBC.getNextValueSequence(), "ShowMaker", e6);
	    Joueur j29 = new Joueur(JoueurJDBC.getNextValueSequence(), "Ghost", e6);
	    Joueur j30 = new Joueur(JoueurJDBC.getNextValueSequence(), "BeryL", e6);
	    e6.ajouterJoueur(j26, j27, j28, j29, j30);
	    eJDBC.add(e6);

	    // Équipe 7 - Rogue
	    Equipe e7 = new Equipe(EquipeJDBC.getNextValueSequence(), "Rogue", 490, Pays.GB);
	    Joueur j31 = new Joueur(JoueurJDBC.getNextValueSequence(), "Odoamne", e7);
	    Joueur j32 = new Joueur(JoueurJDBC.getNextValueSequence(), "Inspired", e7);
	    Joueur j33 = new Joueur(JoueurJDBC.getNextValueSequence(), "Larssen", e7);
	    Joueur j34 = new Joueur(JoueurJDBC.getNextValueSequence(), "Hans sama", e7);
	    Joueur j35 = new Joueur(JoueurJDBC.getNextValueSequence(), "Trymbi", e7);
	    e7.ajouterJoueur(j31, j32, j33, j34, j35);
	    eJDBC.add(e7);

	    // Équipe 8 - EDward Gaming
	    Equipe e8 = new Equipe(EquipeJDBC.getNextValueSequence(), "EDward Gaming", 480, Pays.CN);
	    Joueur j36 = new Joueur(JoueurJDBC.getNextValueSequence(), "Flandre", e8);
	    Joueur j37 = new Joueur(JoueurJDBC.getNextValueSequence(), "Jiejie", e8);
	    Joueur j38 = new Joueur(JoueurJDBC.getNextValueSequence(), "Scout", e8);
	    Joueur j39 = new Joueur(JoueurJDBC.getNextValueSequence(), "Viper", e8);
	    Joueur j40 = new Joueur(JoueurJDBC.getNextValueSequence(), "Meiko", e8);
	    e8.ajouterJoueur(j36, j37, j38, j39, j40);
	    eJDBC.add(e8);
	    
	    // Équipe 9 - MAD Lions
	    Equipe e9 = new Equipe(EquipeJDBC.getNextValueSequence(), "MAD Lions", 460, Pays.ES);
	    Joueur j41 = new Joueur(JoueurJDBC.getNextValueSequence(), "Armut", e9);
	    Joueur j42 = new Joueur(JoueurJDBC.getNextValueSequence(), "Elyoya", e9);
	    Joueur j43 = new Joueur(JoueurJDBC.getNextValueSequence(), "Humanoid", e9);
	    Joueur j44 = new Joueur(JoueurJDBC.getNextValueSequence(), "Carzzy", e9);
	    Joueur j45 = new Joueur(JoueurJDBC.getNextValueSequence(), "Kaiser", e9);
	    e9.ajouterJoueur(j41, j42, j43, j44, j45);
	    eJDBC.add(e9);

	    // Équipe 10 - Gen.G
	    Equipe e10 = new Equipe(EquipeJDBC.getNextValueSequence(), "Gen.G", 490, Pays.KR);
	    Joueur j46 = new Joueur(JoueurJDBC.getNextValueSequence(), "Rascal", e10);
	    Joueur j47 = new Joueur(JoueurJDBC.getNextValueSequence(), "Clid", e10);
	    Joueur j48 = new Joueur(JoueurJDBC.getNextValueSequence(), "Bdd", e10);
	    Joueur j49 = new Joueur(JoueurJDBC.getNextValueSequence(), "Ruler", e10);
	    Joueur j50 = new Joueur(JoueurJDBC.getNextValueSequence(), "Life", e10);
	    e10.ajouterJoueur(j46, j47, j48, j49, j50);
	    eJDBC.add(e10);
	    
	    // Équipe 11 - Hanwha Life Esports
	    Equipe e11 = new Equipe(EquipeJDBC.getNextValueSequence(), "Hanwha Life Esports", 470, Pays.KR);
	    Joueur j51 = new Joueur(JoueurJDBC.getNextValueSequence(), "Morgan", e11);
	    Joueur j52 = new Joueur(JoueurJDBC.getNextValueSequence(), "Arthur", e11);
	    Joueur j53 = new Joueur(JoueurJDBC.getNextValueSequence(), "Chovy", e11);
	    Joueur j54 = new Joueur(JoueurJDBC.getNextValueSequence(), "Deft", e11);
	    Joueur j55 = new Joueur(JoueurJDBC.getNextValueSequence(), "Vsta", e11);
	    e11.ajouterJoueur(j51, j52, j53, j54, j55);
	    eJDBC.add(e11);

	    // Équipe 12 - 100 Thieves
	    Equipe e12 = new Equipe(EquipeJDBC.getNextValueSequence(), "100 Thieves", 460, Pays.US);
	    Joueur j56 = new Joueur(JoueurJDBC.getNextValueSequence(), "Ssumday", e12);
	    Joueur j57 = new Joueur(JoueurJDBC.getNextValueSequence(), "Closer", e12);
	    Joueur j58 = new Joueur(JoueurJDBC.getNextValueSequence(), "Abbedagge", e12);
	    Joueur j59 = new Joueur(JoueurJDBC.getNextValueSequence(), "FBI", e12);
	    Joueur j60 = new Joueur(JoueurJDBC.getNextValueSequence(), "Huhi", e12);
	    e12.ajouterJoueur(j56, j57, j58, j59, j60);
	    eJDBC.add(e12);
	    
	    // Équipe 13 - Team Vitality
	    Equipe e13 = new Equipe(EquipeJDBC.getNextValueSequence(), "Team Vitality", 450, Pays.FR);
	    Joueur j61 = new Joueur(JoueurJDBC.getNextValueSequence(), "SLT", e13);
	    Joueur j62 = new Joueur(JoueurJDBC.getNextValueSequence(), "Skeanz", e13);
	    Joueur j63 = new Joueur(JoueurJDBC.getNextValueSequence(), "Milica", e13);
	    Joueur j64 = new Joueur(JoueurJDBC.getNextValueSequence(), "Comp", e13);
	    Joueur j65 = new Joueur(JoueurJDBC.getNextValueSequence(), "Labrov", e13);
	    e13.ajouterJoueur(j61, j62, j63, j64, j65);
	    eJDBC.add(e13);

	    // Équipe 14 - Invictus Gaming
	    Equipe e14 = new Equipe(EquipeJDBC.getNextValueSequence(), "Invictus Gaming", 500, Pays.CN);
	    Joueur j66 = new Joueur(JoueurJDBC.getNextValueSequence(), "TheShy", e14);
	    Joueur j67 = new Joueur(JoueurJDBC.getNextValueSequence(), "Ning", e14);
	    Joueur j68 = new Joueur(JoueurJDBC.getNextValueSequence(), "Rookie", e14);
	    Joueur j69 = new Joueur(JoueurJDBC.getNextValueSequence(), "JackeyLove", e14);
	    Joueur j70 = new Joueur(JoueurJDBC.getNextValueSequence(), "Baolan", e14);
	    e14.ajouterJoueur(j66, j67, j68, j69, j70);
	    eJDBC.add(e14);

	    // Équipe 15 - FlyQuest
	    Equipe e15 = new Equipe(EquipeJDBC.getNextValueSequence(), "FlyQuest", 440, Pays.US);
	    Joueur j71 = new Joueur(JoueurJDBC.getNextValueSequence(), "Licorice", e15);
	    Joueur j72 = new Joueur(JoueurJDBC.getNextValueSequence(), "Josedeodo", e15);
	    Joueur j73 = new Joueur(JoueurJDBC.getNextValueSequence(), "Palafox", e15);
	    Joueur j74 = new Joueur(JoueurJDBC.getNextValueSequence(), "Johnsun", e15);
	    Joueur j75 = new Joueur(JoueurJDBC.getNextValueSequence(), "Dreams", e15);
	    e15.ajouterJoueur(j71, j72, j73, j74, j75);
	    eJDBC.add(e15);
	    
	    // Équipe 16 - Immortals
	    Equipe e16 = new Equipe(EquipeJDBC.getNextValueSequence(), "Immortals", 430, Pays.US);
	    Joueur j76 = new Joueur(JoueurJDBC.getNextValueSequence(), "Revenge", e16);
	    Joueur j77 = new Joueur(JoueurJDBC.getNextValueSequence(), "Xerxe", e16);
	    Joueur j78 = new Joueur(JoueurJDBC.getNextValueSequence(), "Insanity", e16);
	    Joueur j79 = new Joueur(JoueurJDBC.getNextValueSequence(), "Raes", e16);
	    Joueur j80 = new Joueur(JoueurJDBC.getNextValueSequence(), "Destiny", e16);
	    e16.ajouterJoueur(j76, j77, j78, j79, j80);
	    eJDBC.add(e16);

	    // Équipe 17 - Team SoloMid (TSM)
	    Equipe e17 = new Equipe(EquipeJDBC.getNextValueSequence(), "TSM", 460, Pays.US);
	    Joueur j81 = new Joueur(JoueurJDBC.getNextValueSequence(), "Huni", e17);
	    Joueur j82 = new Joueur(JoueurJDBC.getNextValueSequence(), "Spica", e17);
	    Joueur j83 = new Joueur(JoueurJDBC.getNextValueSequence(), "PowerOfEvil", e17);
	    Joueur j84 = new Joueur(JoueurJDBC.getNextValueSequence(), "Lost", e17);
	    Joueur j85 = new Joueur(JoueurJDBC.getNextValueSequence(), "SwordArt", e17);
	    e17.ajouterJoueur(j81, j82, j83, j84, j85);
	    eJDBC.add(e17);
	    
	    // Équipe 18 - Evil Geniuses
	    Equipe e18 = new Equipe(EquipeJDBC.getNextValueSequence(), "Evil Geniuses", 440, Pays.US);
	    Joueur j86 = new Joueur(JoueurJDBC.getNextValueSequence(), "Impact", e18);
	    Joueur j87 = new Joueur(JoueurJDBC.getNextValueSequence(), "Svenskeren", e18);
	    Joueur j88 = new Joueur(JoueurJDBC.getNextValueSequence(), "Jiizuke", e18);
	    Joueur j89 = new Joueur(JoueurJDBC.getNextValueSequence(), "Danny", e18);
	    Joueur j90 = new Joueur(JoueurJDBC.getNextValueSequence(), "IgNar", e18);
	    e18.ajouterJoueur(j86, j87, j88, j89, j90);
	    eJDBC.add(e18);

	    // Équipe 19 - Fnatic Rising (Équipe de développement de Fnatic)
	    Equipe e19 = new Equipe(EquipeJDBC.getNextValueSequence(), "Fnatic Rising", 410, Pays.GB);
	    Joueur j91 = new Joueur(JoueurJDBC.getNextValueSequence(), "Adam", e19);
	    Joueur j92 = new Joueur(JoueurJDBC.getNextValueSequence(), "Nxy", e19);
	    Joueur j93 = new Joueur(JoueurJDBC.getNextValueSequence(), "MagiFelix", e19);
	    Joueur j94 = new Joueur(JoueurJDBC.getNextValueSequence(), "Botkap", e19);
	    Joueur j95 = new Joueur(JoueurJDBC.getNextValueSequence(), "Prosfair", e19);
	    e19.ajouterJoueur(j91, j92, j93, j94, j95);
	    eJDBC.add(e19);
	    
	    aJDBC.add(new Arbitre(ArbitreJDBC.getNextValueSequence(), "Jules", "Antoine"));
	    aJDBC.add(new Arbitre(ArbitreJDBC.getNextValueSequence(), "Paul", "Antoine"));
	    aJDBC.add(new Arbitre(ArbitreJDBC.getNextValueSequence(), "Eric", "Antoine"));
	    
	    // =============================== //
	 	// ==== Création des tournois ==== //
	 	// =============================== //
	    
	    // Tournoi 1 
		Compte c1 = new Compte(CompteJDBC.getNextValueSequence(), "happyleague", "vHd14sR?!de@Az", TypeCompte.ARBITRE);
		cJDBC.add(c1);
		Tournoi t1 = Tournoi.createTournoi("Happy League", Niveau.LOCAL, Date.valueOf(LocalDate.of(2023, 01, 13)), 
							Date.valueOf(LocalDate.of(2023, 01, 20)), Pays.FR, Statut.A_VENIR, Optional.empty(), Optional.of(c1));
		tJDBC.add(t1);
		ajouterEquipesTournoi(pJDBC, t1, e1, e4, e5, e11, e16, e17);/*
		t1.generationPoule();
		List<Partie> parties = mJDBC.getAll().stream().filter(p -> p.getTournoi().getNomTournoi().equals(t1.getNomTournoi())).collect(Collectors.toList());
		List<Equipe> equipesPartie;
		for (Partie partie : parties) {
			equipesPartie = mJDBC.getEquipes(partie);
			partie.setEquipeGagnante((Math.random() <= 0.5) ? equipesPartie.get(0) : equipesPartie.get(1));
			mJDBC.update(partie);
		}
	    
	    /*
	    Compte c1 = new Compte(CompteJDBC.getNextValueSequence(), "happyleague", "vHd14sR?!de@Az", TypeCompte.ARBITRE);
		cJDBC.add(c1);
	    Tournoi t1 = Tournoi.createTournoi("Happy League", Niveau.LOCAL, Date.valueOf(LocalDate.of(2023, 01, 13)), 
				Date.valueOf(LocalDate.of(2023, 01, 20)), Pays.FR, Statut.TERMINE, Optional.empty(), Optional.of(c1));
	    tJDBC.add(t1);
		
		Partie partie = new Partie(t1.getDateDebut(), "10:00", "Poule", t1);
		
		JouerJDBC jouerJDBC = new JouerJDBC();
		PartieJDBC partieJDBC = new PartieJDBC();
		partieJDBC.add(partie);
		jouerJDBC.add(jouerUn);
		jouerJDBC.add(jouerDeux);
		
		System.out.println(new JouerJDBC().getAll());
		System.out.println(mJDBC.getEquipes(partie));*/
	    
	}

	private static void ajouterEquipesTournoi(ParticiperJDBC pJDBC, Tournoi tournoi, Equipe... equipes) throws Exception {
		for (Equipe equipe : equipes) {
			pJDBC.add(new Participer(equipe, tournoi));
		}
	}
}
