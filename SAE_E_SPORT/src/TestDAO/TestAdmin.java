package TestDAO;

import dao.AdminJDBC;
import dao.CompteJDBC;
import dao.CreateDB;
import modele.Administrateur;
import modele.Compte;
import modele.TypeCompte;

public class TestAdmin {

	public static void main(String[] args) throws Exception {
		CreateDB.main(args);
		AdminJDBC abdd = new AdminJDBC();
		CompteJDBC cbdd = new CompteJDBC();
		
		Compte c1 = new Compte(1, "KHC4298A", "youinfo", TypeCompte.ADMINISTRATEUR);
		Compte c2 = new Compte(2, "MAR5221A", "davinfo", TypeCompte.ADMINISTRATEUR); 
		Compte c3 = new Compte(3, "CHE1111A", "maxinfo", TypeCompte.ADMINISTRATEUR);
		cbdd.add(c1);
		cbdd.add(c2);
		cbdd.add(c3);
		
		Administrateur a1 = new Administrateur (1, "koh", "youchen", c1);
		System.out.println(a1);
		Administrateur a2 = new Administrateur(2, "marquet", "david", c2);
		Administrateur a3 = new Administrateur(3, "chevalier", "max", c3);
		abdd.add(a1);
		abdd.add(a2);
		abdd.add(a3);
		
		System.out.println();
		
		for (Administrateur a : abdd.getAll()) {
			System.out.println(a);
		}
		System.out.println("\n###getAll Admin OK###\n");
		System.out.println("###add Admin OK###\n");
		
		System.out.println(abdd.getById(1).orElse(null));
		System.out.println("\n###getById Admin OK\n");
		
		c3 = new Compte(3, "JUL1111A", "chsinfo", TypeCompte.ADMINISTRATEUR);
		cbdd.update(c3);
		a3 = new Administrateur(3, "julien", "christine", c3);
		abdd.update(a3);
		for (Administrateur a : abdd.getAll()) {
			System.out.println(a);
		}
		System.out.println("\n###update Admin OK###\n");
		
		abdd.delete(a3);
		for (Administrateur a : abdd.getAll()) {
			System.out.println(a);
		}
		System.out.println("\n###delete Admin OK###\n");

	}

}
