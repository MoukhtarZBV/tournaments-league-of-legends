package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionJDBC {
	
	private static Connection connection;
	
	private ConnectionJDBC () {}
	
	public static synchronized Connection getConnection() {
		if (connection == null) {
			String dirProjetJava = System.getProperty("user.dir");
			System.setProperty("derby.system.home", dirProjetJava + "/BDD");
			try {
				DriverManager.registerDriver(new org.apache.derby.jdbc.EmbeddedDriver());
				String urlConnexion = "jdbc:derby:BDD;create=true";
				
				connection = DriverManager.getConnection(urlConnexion);
				System.out.println("Connexion OK");
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return connection;
	}
	
	public static void closeConnection() {
		if (connection != null) {
			connection = null;
		}
	}
	
}
