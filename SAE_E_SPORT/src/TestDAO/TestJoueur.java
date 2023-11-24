package TestDAO;

import java.sql.Connection;

import org.apache.derby.jdbc.EmbeddedConnectionPoolDataSource40;

import dao.ConnectionJDBC;
import dao.EquipeJDBC;
import dao.JoueurJDBC;
import modele.Equipe;
import modele.Joueur;
import modele.Pays;

public class TestJoueur {

	public static void main (String[] args) throws Exception {
		
		EquipeJDBC eJDBC = new EquipeJDBC();
		JoueurJDBC jJDBC = new JoueurJDBC();

		ConnectionJDBC.getConnection().createStatement().execute("insert into Pays values('France')");
		ConnectionJDBC.getConnection().createStatement().execute("insert into Pays values('Taiwan')");
		
		Equipe e1 = new Equipe(1, "T1", 1000, Pays.FR);
		
		Joueur j1 = new Joueur(1, "Zeus", e1);
		Joueur j2 = new Joueur(2, "Oner", e1);
		Joueur j3 = new Joueur(3, "Faker", e1);
		Joueur j4 = new Joueur(4, "Gumayusi", e1);
		Joueur j5 = new Joueur(5, "Keria", e1);
		
		e1.ajouterJoueur(j1, j2, j3, j4, j5);
		
		Equipe e2 = new Equipe(2, "GenG", 1000, Pays.FR);
		Joueur j11 = new Joueur(1, "Doran", e2);
		Joueur j21 = new Joueur(2, "Peanut", e2);
		Joueur j31 = new Joueur(3, "Chovy", e2);
		Joueur j41 = new Joueur(4, "Adc", e2);
		Joueur j51 = new Joueur(5, "Sp", e2);
		e2.ajouterJoueur(j11,j21,j31,j41,j51);
		
		eJDBC.add(e1);
		eJDBC.add(e2);
		
		jJDBC.add(j1);
		jJDBC.add(j2);
		jJDBC.add(j3);
		jJDBC.add(j4);
		jJDBC.add(j5);
		
		jJDBC.add(j11);
		jJDBC.add(j21);
		jJDBC.add(j31);
		jJDBC.add(j41);
		jJDBC.add(j51);
		
		for(Joueur j : jJDBC.getAll()) {
			System.out.println(j.toString());
		}
		
		for(Equipe e : eJDBC.getAll()) {
			System.out.println(e.toString());
		}
		
		for (Joueur j : jJDBC.getByEquipe(e2)) {
			System.out.println(j.toString());
		};
				
		System.out.println(jJDBC.getById(5).toString());
		
		System.out.println(jJDBC.getByPseudo("Faker"));

		System.out.println();
		
		for (Equipe e : eJDBC.getAll()) {
			System.out.println(e.toString());
		}
		int id1 = eJDBC.getIdByNom("T1");
		System.out.println("Team 1 : " + id1);
		
		int id2 = eJDBC.getIdByNom("GenG");
		System.out.println("Team 2 : " + id2);
		
		ConnectionJDBC.getConnection().close();
	}
	
}
