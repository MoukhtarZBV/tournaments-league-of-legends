package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionJDBC {

	private static Connection cn;
	
	private ConnectionJDBC(){}
	
	public synchronized static Connection getConnection () {
			String dirProjetJava = System.getProperty("user.dir");
			System.setProperty("derby.system.home", dirProjetJava + "/BDD");
			try {
				DriverManager.registerDriver(new org.apache.derby.jdbc.EmbeddedDriver());
				String urlConnexion = "jdbc:derby:BDD;create=true";
				
				cn = DriverManager.getConnection(urlConnexion);
				System.out.println("Connexion OK");
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return cn;
	}
	
}
