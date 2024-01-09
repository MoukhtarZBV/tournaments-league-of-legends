package TestDAO;

import dao.CompteJDBC;
import dao.CreateDB;
import modele.Compte;
import modele.TypeCompte;

public class TestCompte {

	public static void main(String[] args) throws Exception {
		CreateDB.main(args);
		CompteJDBC cbdd = new CompteJDBC();
		
		Compte c1 = new Compte("KHC4298A", "iutinfo", TypeCompte.ADMINISTRATEUR);
		Compte c2 = new Compte("MOU5221A", "iutinfo", TypeCompte.ARBITRE);
		
		cbdd.add(c1);
		cbdd.add(c2);
		
		System.out.println();
		for(Compte c : cbdd.getAll()) {
			System.out.println(c);
		}
		System.out.println("\n###add Compte OK###\n");
		System.out.println("###getAll Compte OK###\n");
		
		System.out.println(cbdd.getById("MOU5221A").orElse(null));
		System.out.println("\n###getById Compte OK###\n");
		
		c2 = new Compte("MOU5252A", "iutinfo", TypeCompte.ARBITRE);
		cbdd.update(c2);
		
		for(Compte c : cbdd.getAll()) {
			System.out.println(c);
		}
		System.out.println("\n###update Compte OK###\n");
		
		cbdd.delete(c2);
		System.out.println();
		for(Compte c : cbdd.getAll()) {
			System.out.println(c);
		}
		System.out.println("\n###delete Compte OK###\n");

		
		
	}

}
