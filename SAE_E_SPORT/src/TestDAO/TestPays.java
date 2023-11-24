package TestDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.LinkedList;

import dao.ConnectionJDBC;
import dao.PaysJDBC;
import modele.Pays;

public class TestPays {

	public static void main(String[] args) throws Exception {
		Connection c = ConnectionJDBC.getConnection();
		PaysJDBC pjdbc = PaysJDBC.getInstance();
		
		pjdbc.add(Pays.AD);
		
		ResultSet rs = c.createStatement().executeQuery("select * from pays");
		while (rs.next()) {
			System.out.println(rs.getString(1));
		}
		
		for(Pays p : pjdbc.getAll()) {
			if (p != null) {
				System.out.println(p.getNom());
			}
		}
		
		c.close();
	}

}
