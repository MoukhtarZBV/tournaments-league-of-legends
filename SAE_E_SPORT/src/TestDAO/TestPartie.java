package TestDAO;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

import dao.ConnectionJDBC;
import dao.CreateDB;
import dao.EquipeJDBC;
import dao.JoueurJDBC;
import dao.PartieJDBC;
import dao.TournoiJDBC;
import modele.Equipe;
import modele.Joueur;
import modele.Niveau;
import modele.Partie;
import modele.Pays;
import modele.Tournoi;

public class TestPartie {

	public static void main(String[] args) throws Exception {
		CreateDB.main(args);
		
		PartieJDBC pjdbc = new PartieJDBC();
		TournoiJDBC tjdbc = new TournoiJDBC();
		
		CreateDB.main(args);
		System.out.println("Table tournoi, partie, niveauTournoi vide\n");
		
		Tournoi t1 = new Tournoi(1, "Happy League", Niveau.LOCAL, Date.valueOf(LocalDate.of(2023, 12, 28)), 
				Date.valueOf(LocalDate.of(2023, 12, 29)), Pays.FR);
		Tournoi t2 = new Tournoi(2, "Happy Legends", Niveau.INTERNATIONAL, Date.valueOf(LocalDate.of(2023, 12, 30)), 
				Date.valueOf(LocalDate.of(2023, 12, 31)), Pays.FR);
		
		tjdbc.add(t1);
		tjdbc.add(t2);
		
		for (Tournoi t : tjdbc.getAll()) {
			System.out.println(t);
		}

		JoueurJDBC JoueurJDBC = new JoueurJDBC();
		
		Equipe e1 = new Equipe(EquipeJDBC.getNextValueSequence(), "T1", 1000, Pays.FR);
		Joueur j1 = new Joueur(dao.JoueurJDBC.getNextValueSequence(), "Zeus", e1);
		Joueur j2 = new Joueur(dao.JoueurJDBC.getNextValueSequence(), "Oner", e1);
		Joueur j3 = new Joueur(dao.JoueurJDBC.getNextValueSequence(), "Faker", e1);
		Joueur j4 = new Joueur(dao.JoueurJDBC.getNextValueSequence(), "Gumayusi", e1);
		Joueur j5 = new Joueur(dao.JoueurJDBC.getNextValueSequence(), "Keria", e1);
		
		e1.ajouterJoueur(j1, j2, j3, j4, j5);
		
		EquipeJDBC eJDBC = new EquipeJDBC();
		eJDBC.add(e1);
		
		Equipe e2 = new Equipe(2, "GenG", 1000, Pays.FR);
		Joueur j11 = new Joueur(dao.JoueurJDBC.getNextValueSequence(), "Doran", e2);
		Joueur j21 = new Joueur(dao.JoueurJDBC.getNextValueSequence(), "Peanut", e2);
		Joueur j31 = new Joueur(dao.JoueurJDBC.getNextValueSequence(), "Chovy", e2);
		Joueur j41 = new Joueur(dao.JoueurJDBC.getNextValueSequence(), "Adc", e2);
		Joueur j51 = new Joueur(dao.JoueurJDBC.getNextValueSequence(), "Sp", e2);
		e2.ajouterJoueur(j11,j21,j31,j41,j51);
		
		eJDBC.add(e2);
		
		for (Equipe e : eJDBC.getAll()) {
			System.out.println(e);
		}
		
		for (Joueur joueur : JoueurJDBC.getAll()) {
			System.out.println(joueur);
		}
		
		Partie p1 = new Partie(Date.valueOf(LocalDate.of(2023, 12, 28)), "12:00", "Final", e1, t1);
		p1.setEquipeGagnant(1);
		p1.setEquipe2(e2);
		Partie p2 = new Partie(Date.valueOf(LocalDate.of(2023, 12, 30)), "13:00", "Final", e1, t2);
		p2.setEquipe2(e2);
		pjdbc.add(p1);
		pjdbc.add(p2);
		System.out.println("Add partie OK\n");
		
		for(Partie partie : pjdbc.getAll()) {
			System.out.println(partie);
		}
		System.out.println("\ngetAll Partie OK\n");
		
		Optional<Partie> opt = pjdbc.getByDateHeure(Date.valueOf(LocalDate.of(2023, 12, 30)), "13:00");
		System.out.println(opt.orElse(null));
		System.out.println("\ngetByDateHeure OK\n");
	
		pjdbc.delete(p2);
		for(Partie partie : pjdbc.getAll()) {
			System.out.println(partie);
		}
		System.out.println("\ndelete Partie OK\n");
		
		p1.setEquipeGagnant(2);
		pjdbc.update(p1);
		for(Partie partie : pjdbc.getAll()) {
			System.out.println(partie);
		}
		System.out.println("\nUpdate Partie OK\n");
		ConnectionJDBC.closeConnection();
	}

}
