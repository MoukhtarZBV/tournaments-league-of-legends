package TestDAO;

import dao.ArbitreJDBC;
import dao.CreateDB;
import modele.Arbitre;

public class TestArbitre {

	public static void main(String[] args) throws Exception {
		CreateDB.main(args);
		ArbitreJDBC abdd = new ArbitreJDBC();
		
		Arbitre a1 = new Arbitre(1, "koh", "youchen");
		Arbitre a2 = new Arbitre(2, "marquet", "david");
		Arbitre a3 = new Arbitre(3, "chevalier", "max");
		// à retirer plus tard c'est pour le test
		Arbitre a4 = new Arbitre(4, "tito", "pablo");
		Arbitre a5 = new Arbitre(5, "tic", "tac");
		Arbitre a6 = new Arbitre(6, "bam", "bim");
		Arbitre a7 = new Arbitre(7, "lebron", "james");
		abdd.add(a1);
		abdd.add(a2);
		abdd.add(a3);
		abdd.add(a4);
		abdd.add(a5);
		abdd.add(a6);
		abdd.add(a7);
		
		for (Arbitre a : abdd.getAll()) {
			System.out.println(a);
		}
		System.out.println("\n###getAll Arbitre OK###\n");
		System.out.println("###add Arbitre OK###\n");
		
		System.out.println(abdd.getById(1).orElse(null));
		System.out.println("\n###getById Arbitre OK");
		
		a3 = new Arbitre(3, "julien", "christine");
		abdd.update(a3);
		for (Arbitre a : abdd.getAll()) {
			System.out.println(a);
		}
		System.out.println("\n###update Arbitre OK###\n");
		
		abdd.delete(a3);
		for (Arbitre a : abdd.getAll()) {
			System.out.println(a);
		}
		System.out.println("\n###delete Arbitre OK###\n");
	}

}
