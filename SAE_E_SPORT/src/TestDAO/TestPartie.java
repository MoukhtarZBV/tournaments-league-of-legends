package TestDAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import dao.ConnectionJDBC;
import dao.CreateDB;
import dao.PartieJDBC;
import dao.TournoiJDBC;
import modele.Niveau;
import modele.Partie;
import modele.Pays;
import modele.Tournoi;

public class TestPartie {

	public static void main(String[] args) throws Exception {
		PartieJDBC pjdbc = new PartieJDBC();
		TournoiJDBC tjdbc = new TournoiJDBC();
		
		CreateDB.main(args);
		System.out.println("Table tournoi, partie, niveauTournoi vide\n");
		
		ConnectionJDBC.getConnection().createStatement().executeUpdate("insert into niveauTournoi values('Local')");
		ConnectionJDBC.getConnection().createStatement().executeUpdate("insert into niveauTournoi values('International')");
		
		Tournoi t1 = new Tournoi(1, "Happy League", Niveau.LOCAL, Date.valueOf(LocalDate.of(2023, 11, 23)), 
				Date.valueOf(LocalDate.of(2023, 12, 31)), Pays.FR);
		Tournoi t2 = new Tournoi(2, "Happy Legends", Niveau.INTERNATIONAL, Date.valueOf(LocalDate.of(2023, 5, 10)), 
				Date.valueOf(LocalDate.of(2023, 10, 31)), Pays.FR);
		
		tjdbc.add(t1);
		tjdbc.add(t2);
		
		for (Tournoi t : tjdbc.getAll()) {
			System.out.println(t);
		}
		
		Partie p1 = new Partie(Date.valueOf(LocalDate.of(2023, 11, 23)), "12:00", "Final", t1);
		Partie p2 = new Partie(Date.valueOf(LocalDate.of(2023, 11, 23)), "13:00", "Final", t2);
		
		pjdbc.add(p1);
		pjdbc.add(p2);
		System.out.println("Add partie OK\n");
		
		for(Partie partie : pjdbc.getAll()) {
			System.out.println(partie);
		}
		System.out.println("\ngetAll Partie OK\n");
		
		p2 = new Partie(Date.valueOf(LocalDate.of(2023, 11, 24)), "15:00", "Final", t2);
		pjdbc.update(p2);
		for(Partie partie : pjdbc.getAll()) {
			System.out.println(partie);
		}
		System.out.println("\nupdate Partie OK\n");
		
		Optional<Partie> opt = pjdbc.getByDateHeure(Date.valueOf(LocalDate.of(2023, 11, 24)), "15:00");
		System.out.println(opt.orElseGet(null));
		System.out.println("\ngetByDateHeure OK\n");
	
		pjdbc.delete(p2);
		for(Partie partie : pjdbc.getAll()) {
			System.out.println(partie);
		}
		System.out.println("\ndelete Partie OK\n");
		
		ConnectionJDBC.getConnection().createStatement().execute("delete from Partie");
		ConnectionJDBC.getConnection().createStatement().execute("delete from Tournoi");
		ConnectionJDBC.getConnection().createStatement().execute("delete from niveauTournoi");
		System.out.println("\nTable tournoi, partie, niveauTournoi vide\n");
		
		Connection c = ConnectionJDBC.getConnection();
		c.close();
	}

}
