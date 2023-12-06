package TestDAO;

import java.sql.Date;
import java.time.LocalDate;

import dao.CreateDB;
import dao.EquipeJDBC;
import dao.JoueurJDBC;
import dao.ParticiperDAO;
import dao.ParticiperJDBC;
import dao.TournoiJDBC;
import modele.Equipe;
import modele.Joueur;
import modele.Niveau;
import modele.Participer;
import modele.Pays;
import modele.Tournoi;

public class TestParticiper {

	public static void main(String[] args) throws Exception {
		CreateDB.main(args);
		int idT = TournoiJDBC.getNextSequenceValue();
		Tournoi t1 = new Tournoi(idT, "Happy League", Niveau.LOCAL, Date.valueOf(LocalDate.of(2023, 12, 02)), 
				Date.valueOf(LocalDate.of(2023, 12, 31)), Pays.FR);
		System.out.println(t1);
		TournoiJDBC tbd = new TournoiJDBC();
		tbd.add(t1);
		System.out.println(tbd.getAll());
		
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
		
		Participer part = new Participer(eJDBC.getByNom("T1").orElse(null), tbd.getTournoiByName("Happy League").orElse(null));
		part.setNbMatchsJoues(3);
		part.setNbMatchsGagnes(2);
		part.setNbPointsGagnes(10);
		
		ParticiperJDBC pbd = new ParticiperJDBC();
		
		pbd.add(part);
		
		for (Participer p : pbd.getAll()) {
			System.out.println(p);
		}
		
	}

}
