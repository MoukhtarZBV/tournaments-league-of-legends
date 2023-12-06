package TestDAO;

import java.sql.Date;
import java.time.LocalDate;

import dao.CreateDB;
import dao.EquipeJDBC;
import dao.JouerJDBC;
import dao.JoueurJDBC;
import dao.PartieJDBC;
import dao.TournoiJDBC;
import modele.Equipe;
import modele.Jouer;
import modele.Joueur;
import modele.Niveau;
import modele.Partie;
import modele.Pays;
import modele.Tournoi;

public class TestJouer {

	public static void main(String[] args) throws Exception {
		CreateDB.main(args);
		
		Tournoi t1 = new Tournoi(TournoiJDBC.getNextSequenceValue(), "Happy League", Niveau.LOCAL, Date.valueOf(LocalDate.of(2023, 12, 02)), 
				Date.valueOf(LocalDate.of(2023, 12, 31)), Pays.FR);
		TournoiJDBC tbd = new TournoiJDBC();
		tbd.add(t1);
		
		Equipe e1 = new Equipe(EquipeJDBC.getNextValueSequence(), "T1", 1000, Pays.FR);
		Joueur j1 = new Joueur(1, "Zeus", e1);
		Joueur j2 = new Joueur(2, "Oner", e1);
		Joueur j3 = new Joueur(3, "Faker", e1);
		Joueur j4 = new Joueur(4, "Gumayusi", e1);
		Joueur j5 = new Joueur(5, "Keria", e1);
		
		e1.ajouterJoueur(j1, j2, j3, j4, j5);
		
		EquipeJDBC eJDBC = new EquipeJDBC();
		eJDBC.add(e1);
		
		JoueurJDBC jdb = new JoueurJDBC();
		jdb.add(j1);
		jdb.add(j2);
		jdb.add(j3);
		jdb.add(j4);
		jdb.add(j5);
		
		PartieJDBC pbd = new PartieJDBC();
		Partie partie = new Partie(Date.valueOf(LocalDate.of(2022,12,25)), "13:00", "Local", e1 ,t1);
		
		pbd.add(partie);
		
		
		
		Equipe e2 = new Equipe(2, "GenG", 1000, Pays.FR);
		Joueur j11 = new Joueur(1, "Doran", e2);
		Joueur j21 = new Joueur(2, "Peanut", e2);
		Joueur j31 = new Joueur(3, "Chovy", e2);
		Joueur j41 = new Joueur(4, "Adc", e2);
		Joueur j51 = new Joueur(5, "Sp", e2);
		e2.ajouterJoueur(j11,j21,j31,j41,j51);
//		
//		JouerJDBC j = new JouerJDBC();
//		Jouer jouer = new Jouer(e1, partie);
//		j.add(jouer);
//		jouer = new Jouer(e2, partie);
//		j.add(jouer);
//		
//		for (Jouer jr : j.getAll()) {
//			System.out.println(jr);
//		}
	}

}
