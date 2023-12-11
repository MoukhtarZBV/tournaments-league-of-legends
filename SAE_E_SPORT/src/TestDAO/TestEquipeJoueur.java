package TestDAO;

import dao.ConnectionJDBC;
import dao.CreateDB;
import dao.EquipeJDBC;
import dao.JoueurJDBC;
import modele.Equipe;
import modele.Joueur;
import modele.Pays;

public class TestEquipeJoueur {

	public static void main (String[] args) throws Exception {
		
		CreateDB.main(args);
		
		EquipeJDBC eJDBC = new EquipeJDBC();
		JoueurJDBC jJDBC = new JoueurJDBC();
		
		Equipe e1 = new Equipe(EquipeJDBC.getNextValueSequence(), "T1", 1000, Pays.FR);
		
		Joueur j1 = new Joueur(JoueurJDBC.getNextValueSequence(), "Zeus", e1);
		Joueur j2 = new Joueur(JoueurJDBC.getNextValueSequence(), "Oner", e1);
		Joueur j3 = new Joueur(JoueurJDBC.getNextValueSequence(), "Faker", e1);
		Joueur j4 = new Joueur(JoueurJDBC.getNextValueSequence(), "Gumayusi", e1);
		Joueur j5 = new Joueur(JoueurJDBC.getNextValueSequence(), "Keria", e1);
		
		e1.ajouterJoueur(j1, j2, j3, j4, j5);
		
		Equipe e2 = new Equipe(EquipeJDBC.getNextValueSequence(), "GenG", 1000, Pays.FR);
		Joueur j11 = new Joueur(JoueurJDBC.getNextValueSequence(), "Doran", e2);
		Joueur j21 = new Joueur(JoueurJDBC.getNextValueSequence(), "Peanut", e2);
		Joueur j31 = new Joueur(JoueurJDBC.getNextValueSequence(), "Chovy", e2);
		Joueur j41 = new Joueur(JoueurJDBC.getNextValueSequence(), "Adc", e2);
		Joueur j51 = new Joueur(JoueurJDBC.getNextValueSequence(), "Sp", e2);
		e2.ajouterJoueur(j11,j21,j31,j41,j51);
		
		eJDBC.add(e1);
		eJDBC.add(e2);
		
		System.out.println("\n###add Equipe OK###\n");
		
		System.out.println("\n###add Joueur OK###\n");
		
		for(Joueur j : jJDBC.getAll()) {
			System.out.println(j.toString());
		}
		System.out.println("\n###getAll Joueur OK###\n");
		
		for(Equipe e : eJDBC.getAll()) {
			System.out.println(e.toString());
		}
		System.out.println("\n###getAll Equipe OK###\n");
		
		for (Joueur j : jJDBC.getByEquipe(e2)) {
			System.out.println(j.toString());
		}
		System.out.println("\n###getByEquipe Joueur OK###\n");
				
		System.out.println(jJDBC.getById(5).orElse(null));
		System.out.println("\n###getById Joueur OK###\n");
		
		System.out.println(jJDBC.getByPseudo("Faker").orElse(null));
		System.out.println("\n###getByPseudo Joueur OK###\n");
				
		for (Equipe e : eJDBC.getAll()) {
			System.out.println(e.toString());
		}
		
		int id1 = eJDBC.getIdByNom("T1");
		System.out.println("Team 1 : " + id1);
		
		int id2 = eJDBC.getIdByNom("GenG");
		System.out.println("Team 2 : " + id2);
		
		System.out.println("\n###getIdByNom Equipe OK###\n");
		
		e2 = new Equipe(2, "GenG", 500, Pays.FR);
		eJDBC.update(e2);
		for(Equipe e : eJDBC.getAll()) {
			System.out.println(e);
		}
		System.out.println("\n###update Equipe OK###\n");
		
		System.out.println(eJDBC.getNextValueSequence());
		
		ConnectionJDBC.closeConnection();
	}
	
}
