//package TestVue;
//
//import java.awt.EventQueue;
//
//import java.sql.Date;
//import java.time.LocalDate;
//
//import dao.CreateDB;
//import dao.EquipeJDBC;
//import dao.JoueurJDBC;
//import dao.ParticiperJDBC;
//import dao.PartieJDBC;
//import dao.TournoiJDBC;
//import ihm.VueGestionDeLaPoule;
//import modele.Equipe;
//import modele.Joueur;
//import modele.ModelePoule;
//import modele.Niveau;
//import modele.Participer;
//import modele.Partie;
//import modele.Pays;
//import modele.Tournoi;
//
//public class TestVueGestionPoule {
//
//	public static void main(String[] args) {
//        EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                try {
//                	CreateDB.main(args);
//                    Tournoi t = new Tournoi("My Tournament", Niveau.INTERNATIONAL, 
//                    		Date.valueOf(LocalDate.of(2023, 12, 23)), Date.valueOf(LocalDate.of(2023, 12, 30)),
//                    		Pays.FR);
//                    createValues(args, t);
//
//                    VueGestionDeLaPoule frame = new VueGestionDeLaPoule(t);
//                    
//                    frame.setVisible(true);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//	}
//	
//	public static void createValues(String[] args, Tournoi t) throws Exception {
//		//CreateDB.main(args);
//		
//		PartieJDBC pjdbc = new PartieJDBC();
//		TournoiJDBC tjdbc = new TournoiJDBC();
//		EquipeJDBC eJDBC = new EquipeJDBC();
//		ParticiperJDBC pbd = new ParticiperJDBC();
//				
//		tjdbc.add(t);
//		
//		Equipe e1 = new Equipe(EquipeJDBC.getNextValueSequence(), "T1", 1000, Pays.FR);
//		Joueur j1 = new Joueur(JoueurJDBC.getNextValueSequence(), "Zeus", e1);
//		Joueur j2 = new Joueur(JoueurJDBC.getNextValueSequence(), "Oner", e1);
//		Joueur j3 = new Joueur(JoueurJDBC.getNextValueSequence(), "Faker", e1);
//		Joueur j4 = new Joueur(JoueurJDBC.getNextValueSequence(), "Gumayusi", e1);
//		Joueur j5 = new Joueur(JoueurJDBC.getNextValueSequence(), "Keria", e1);
//		
//		e1.ajouterJoueur(j1, j2, j3, j4, j5);
//		
//		eJDBC.add(e1);
//		
//		Equipe e2 = new Equipe(EquipeJDBC.getNextValueSequence(), "GenG", 1000, Pays.FR);
//		Joueur j11 = new Joueur(JoueurJDBC.getNextValueSequence(), "Doran", e2);
//		Joueur j21 = new Joueur(JoueurJDBC.getNextValueSequence(), "Peanut", e2);
//		Joueur j31 = new Joueur(JoueurJDBC.getNextValueSequence(), "Chovy", e2);
//		Joueur j41 = new Joueur(JoueurJDBC.getNextValueSequence(), "Adc", e2);
//		Joueur j51 = new Joueur(JoueurJDBC.getNextValueSequence(), "Sp", e2);
//		e2.ajouterJoueur(j11,j21,j31,j41,j51);
//		
//		eJDBC.add(e2);
//		
//		Equipe e3 = new Equipe(EquipeJDBC.getNextValueSequence(), "T11", 1000, Pays.FR);
//		Joueur jj1 = new Joueur(JoueurJDBC.getNextValueSequence(), "Zeuse", e3);
//		Joueur jj2 = new Joueur(JoueurJDBC.getNextValueSequence(), "Onere", e3);
//		Joueur jj3 = new Joueur(JoueurJDBC.getNextValueSequence(), "Fakere", e3);
//		Joueur jj4 = new Joueur(JoueurJDBC.getNextValueSequence(), "Gumayusie", e3);
//		Joueur jj5 = new Joueur(JoueurJDBC.getNextValueSequence(), "Keriae", e3);
//		
//		e3.ajouterJoueur(jj1, jj2, jj3, jj4, jj5);
//		
//		eJDBC.add(e3);
//		
//		Equipe e4 = new Equipe(EquipeJDBC.getNextValueSequence(), "GenG1", 1000, Pays.FR);
//		Joueur j111 = new Joueur(dao.JoueurJDBC.getNextValueSequence(), "Dorane", e4);
//		Joueur j211 = new Joueur(dao.JoueurJDBC.getNextValueSequence(), "Peanute", e4);
//		Joueur j311 = new Joueur(dao.JoueurJDBC.getNextValueSequence(), "Chovye", e4);
//		Joueur j411 = new Joueur(dao.JoueurJDBC.getNextValueSequence(), "Adce", e4);
//		Joueur j511 = new Joueur(dao.JoueurJDBC.getNextValueSequence(), "Spe", e4);
//		e4.ajouterJoueur(j111, j211, j311, j411, j511);
//		
//		eJDBC.add(e4);
//		
//		// Création des matchs
//		Partie p1 = new Partie(Date.valueOf(LocalDate.of(2023, 12, 28)), "12:00", "Final", e1, t);
//		p1.setEquipeGagnante(-1);
//		p1.setEquipe2(e2);
//		pjdbc.add(p1);
//		
//		Partie p2 = new Partie(Date.valueOf(LocalDate.of(2023, 12, 30)), "13:00", "Final", e2, t);
//		p2.setEquipe2(e1);
//		p2.setEquipeGagnante(2);
//		pjdbc.add(p2);
//		
//		Partie p3 = new Partie(Date.valueOf(LocalDate.of(2023, 12, 29)), "12:00", "Final", e3, t);
//		p3.setEquipeGagnante(1);
//		p3.setEquipe2(e4);
//		pjdbc.add(p3);
//		
//		Partie p4 = new Partie(Date.valueOf(LocalDate.of(2023, 12, 31)), "13:00", "Final", e4, t);
//		p4.setEquipe2(e3);
//		p4.setEquipeGagnante(2);
//		pjdbc.add(p4);
//		
//		System.out.println("Add partie OK\n");
//		
//		// Nb matchs joués et points gagnés par chacune des équipes
//		Participer part1 = new Participer(e1, t);
//		Participer part2 = new Participer(e2, t);
//		Participer part3 = new Participer(e3, t);
//		Participer part4 = new Participer(e4, t);
//		
//		part1.setNbMatchsJoues(2);
//		part1.setNbPointsGagnes(6);
//		
//		part2.setNbMatchsJoues(5);
//		part2.setNbPointsGagnes(3);
//		
//		part3.setNbMatchsJoues(10);
//		part3.setNbPointsGagnes(100);
//		
//		part4.setNbMatchsJoues(5);
//		part4.setNbPointsGagnes(10);
//		
//		pbd.add(part1);
//		pbd.add(part2);
//		pbd.add(part3);
//		pbd.add(part4);
//		
//	};
//
//}
