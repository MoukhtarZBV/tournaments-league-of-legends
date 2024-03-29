package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Date;

import modele.Statut;

public class UpdateDB {
	
	private LocalDate lastUpdate;
	
	private static UpdateDB instance;
	
	private UpdateDB () {}
	
	public static synchronized UpdateDB getInstance() {
		if (instance == null) {
			instance = new UpdateDB();
		}
		return instance;
	}
	
	public boolean canUpdate() {
		if (lastUpdate == null) {
			this.lastUpdate = LocalDate.now();
			return true;
		} else if (lastUpdate.getDayOfMonth() < LocalDate.now().getDayOfMonth()) {
			this.lastUpdate = LocalDate.now();
			return true;
		}
		return false;
	}

	public void updateStatusTournois(Connection connection) {
		if (canUpdate()) {
			Statement stmt = null;
			
			
			/*
			try {
				stmt.executeUpdate("UPDATE Tournoi"
								+ " SET status = " + Status.ATTENTE_RESULTATS.denomination()
								+ " WHERE status = " + Status.EN_COURS.denomination()
								+ " AND dateFin <= CURDATE()");
			} catch (SQLException e) {
				e.printStackTrace();
			}*/
		}
		System.out.println("Update pas effectué");
	}
}
