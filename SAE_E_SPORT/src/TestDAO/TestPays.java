package TestDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.LinkedList;

import dao.ConnectionJDBC;
import dao.PaysJDBC;
import modele.Pays;

public class TestPays {

	public static void main(String[] args) throws Exception {
		PaysJDBC pjdbc = new PaysJDBC();
		
//		pjdbc.add(Pays.AD);
		
		for(Pays p : pjdbc.getAll()) {
			System.out.println(p.denomination());
		}
		
	}

}
