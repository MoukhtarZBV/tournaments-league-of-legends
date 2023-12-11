package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ApplicationTest {

	public static void main(String[] args) throws SQLException {
		Connection cn = ConnectionJDBC.getConnection();
		Statement stmt = cn.createStatement();
		stmt.execute("select equipe.* from Equipe, Tournoi where equipe.idTournoi = ");
	}

}
