package TestDAO;

import java.sql.Date;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import dao.CompteJDBC;
import dao.ConnectionJDBC;
import dao.CreateDB;
import dao.PaysJDBC;
import dao.TournoiJDBC;
import modele.Compte;
import modele.Equipe;
import modele.Joueur;
import modele.Niveau;
import modele.Pays;
import modele.Tournoi;
import modele.TypeCompte;

public class TestTournoi {

	public static void main(String[] args) throws Exception {
		CreateDB.main(args);

		TournoiJDBC tjdbc = new TournoiJDBC();
		
		Tournoi t1 = new Tournoi("Happy League", Niveau.LOCAL, Date.valueOf(LocalDate.of(2023, 11, 23)), 
				Date.valueOf(LocalDate.of(2023, 12, 31)), Pays.FR);
		Tournoi t2 = new Tournoi("Happy Legends", Niveau.INTERNATIONAL, Date.valueOf(LocalDate.of(2023, 5, 10)), 
				Date.valueOf(LocalDate.of(2023, 10, 31)), Pays.FR);
		
		// ajouter bon 
		tjdbc.add(t1);
		tjdbc.add(t2);

		for(Tournoi tournoi : tjdbc.getAll()) {
			System.out.println(tournoi);
		}
		
		System.out.println("\n###getAll Tournoi OK###\n");
		System.out.println("###add Tournoi OK###\n");
		
		Optional<Tournoi> opt = tjdbc.getById("Happy League");
		System.out.println(opt.orElse(null));
		System.out.println("\n###getById Tournoi OK###\n");
		
		tjdbc.delete(t1);
		for(Tournoi tournoi : tjdbc.getAll()) {
			System.out.println(tournoi);
		}
		
		t2 = new Tournoi("Happy Legends", Niveau.INTERNATIONAL, Date.valueOf(LocalDate.of(2023, 5, 10)), 
				Date.valueOf(LocalDate.of(2023, 10, 31)), Pays.US);
		
		Equipe e1 = new Equipe(1, "T1", 1000, Pays.FR);
		
		Joueur j1 = new Joueur(1, "Zeus", e1);
		Joueur j2 = new Joueur(2, "Oner", e1);
		Joueur j3 = new Joueur(3, "Faker", e1);
		Joueur j4 = new Joueur(4, "Gumayusi", e1);
		Joueur j5 = new Joueur(5, "Keria", e1);
		
		e1.ajouterJoueur(j1, j2, j3, j4, j5);
				
		Compte compte = new Compte("kyc88IUT", "$iutinfo", TypeCompte.ADMINISTRATEUR);
		
		CompteJDBC cjdbc = new CompteJDBC();
		cjdbc.add(compte);
		
		for(Compte c : cjdbc.getAll()) {
			System.out.println(c);
		}
		
		System.out.println();
		
		t2.setVainqueur(e1);
		t2.setCompte(compte);
		
		tjdbc.update(t2);
		
		System.out.println();
		for(Tournoi tournoi : tjdbc.getAll()) {
			System.out.println(tournoi);
		}
	}

}
