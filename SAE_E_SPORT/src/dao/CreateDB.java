package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import modele.Niveau;
import modele.Pays;
import modele.TypeCompte;

public class CreateDB {
	
	public static void main(String[] args) throws Exception {
		CreateDB db = new CreateDB();
	}

	public CreateDB() throws Exception {
		Connection connection = ConnectionJDBC.getConnection();
		createTables(connection);
		ConnectionJDBC.closeConnection();
	}
	
	private static void createTables(Connection connection) throws Exception {
		
		Statement stmt = null;
		
		try {
			stmt = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		try {
			stmt.executeUpdate("DROP TABLE Associer");
		} catch (SQLException e) {
			System.out.println("Drop Associer échoué");
		}
		
		try {
			stmt.executeUpdate("DROP TABLE Arbitrer");
		} catch (SQLException e) {
			System.out.println("Drop Arbitrer échoué");
		}
		
		try {
			stmt.executeUpdate("DROP TABLE Jouer");
		} catch (SQLException e) {
			System.out.println("Drop Jouer échoué");
		}
		
		try {
			stmt.executeUpdate("DROP TABLE Participer");
		} catch (SQLException e) {
			System.out.println("Drop Participer échoué");
		}
		
		try {
			stmt.executeUpdate("DROP TABLE Administrateur");
		} catch (SQLException e) {
			System.out.println("Drop Administrateur échoué");
		}
		
		try {
			stmt.executeUpdate("DROP TABLE Arbitre");
		} catch (SQLException e) {
			System.out.println("Drop Arbitre échoué");
		}
		
		try {
			stmt.executeUpdate("DROP TABLE Partie");
		} catch (SQLException e) {
			System.out.println("Drop Partie échoué");
		}
		
		try {
			stmt.executeUpdate("DROP TABLE Tournoi");
		} catch (SQLException e) {
			System.out.println("Drop Tournoi échoué");
		}
		
		try {
			stmt.executeUpdate("DROP TABLE Joueur");
		} catch (SQLException e) {
			System.out.println("Drop Joueur échoué");
		}
		
		try {
			stmt.executeUpdate("DROP TABLE Equipe");
		} catch (SQLException e) {
			System.out.println("Drop Equipe échoué");
		}
		
		try {
			stmt.executeUpdate("DROP TABLE Compte");
		} catch (SQLException e) {
			System.out.println("Drop Compte échoué");
		}
		
		try {
			stmt.executeUpdate("DROP TABLE Pays");
		} catch (SQLException e) {
			System.out.println("Drop Pays échoué");
		}
		
		// ====================================
		// ========== Drop Sequences ==========
		// ====================================
		
		try {
			stmt.executeUpdate("DROP SEQUENCE SEQ_Equipe RESTRICT");
		} catch (SQLException e) {
			System.out.println("Drop SEQ_Equipe échoué");
		}
		
		try {
			stmt.executeUpdate("DROP SEQUENCE SEQ_Joueur RESTRICT");
		} catch (SQLException e) {
			System.out.println("Drop SEQ_Joueur échoué");
		}
		
		try {
			stmt.executeUpdate("DROP SEQUENCE SEQ_Arbitre RESTRICT");
		} catch (SQLException e) {
			System.out.println("Drop SEQ_Arbitre échoué");
		}
		
		try {
			stmt.executeUpdate("DROP SEQUENCE SEQ_Administrateur RESTRICT");
		} catch (SQLException e) {
			System.out.println("Drop SEQ_Administrateur échoué");
		}
		
		// =====================================
		// ========== Créer Sequences ==========
		// =====================================

		try {
			stmt.executeUpdate("CREATE SEQUENCE SEQ_Equipe START WITH 1 INCREMENT BY 1");
			System.out.println("-- Séquence SEQ_Equipe créée");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			stmt.executeUpdate("CREATE SEQUENCE SEQ_Joueur START WITH 1 INCREMENT BY 1");
			System.out.println("-- Séquence SEQ_Joueur créée");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			stmt.executeUpdate("CREATE SEQUENCE SEQ_Arbitre START WITH 1 INCREMENT BY 1");
			System.out.println("-- Séquence SEQ_Arbitre créée");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			stmt.executeUpdate("CREATE SEQUENCE SEQ_Administrateur START WITH 1 INCREMENT BY 1");
			System.out.println("-- Séquence SEQ_Administrateur créée");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// ====================================
		// ========== Créer Tables ============
		// ====================================
		// Table Pays
		try {
			stmt.executeUpdate("CREATE TABLE Pays ("
					+ "nomPays VARCHAR(50),"
					+ "CONSTRAINT PK_Pays_nomPays PRIMARY KEY (nomPays))");
			System.out.println("-- Table Pays créée");
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		// Table Compte
		try { 
			stmt.executeUpdate("CREATE TABLE Compte ("
					+ "login VARCHAR(30),"
					+ "motDePasse VARCHAR(30),"
					+ "type VARCHAR(30),"
					+ "CONSTRAINT PK_Compte_login PRIMARY KEY (login))");
			System.out.println("-- Table Compte créée");
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		// Table Equipe
		try {
			stmt.executeUpdate("CREATE TABLE Equipe ("
					+ "idEquipe INTEGER,"
					+ "nomEquipe VARCHAR(40),"
					+ "rang INTEGER,"
					+ "nationalite VARCHAR(50),"
					+ "CONSTRAINT PK_Equipe_idEquipe PRIMARY KEY(idEquipe),"
					+ "CONSTRAINT FK_Equipe_nationalite FOREIGN KEY (nationalite) REFERENCES Pays(nomPays),"
					+ "CONSTRAINT UN_Equipe_nom UNIQUE (nomEquipe),"
					+ "CONSTRAINT UN_Equipe_rang UNIQUE (rang),"
					+ "CONSTRAINT CK_Equipe_rang CHECK (0 < rang))");
			System.out.println("-- Table Equipe créée");
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		// Table Joueur
		try {
			stmt.executeUpdate("CREATE TABLE Joueur ("
					+ "idJoueur INTEGER,"
					+ "pseudo VARCHAR(50),"
					+ "idEquipe INTEGER,"
					+ "CONSTRAINT PK_Joueur_idJoueur PRIMARY KEY (idJoueur),"
					+ "CONSTRAINT FK_Joueur_idEquipe FOREIGN KEY (idEquipe) REFERENCES Equipe(idEquipe),"
					+ "CONSTRAINT FK_Joueur_pseudo UNIQUE (pseudo))");
			System.out.println("-- Table Joueur créée");
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		// Table Tournoi
		try {
			stmt.executeUpdate("CREATE TABLE Tournoi ("
					+ "nomTournoi VARCHAR(100),"
					+ "niveau VARCHAR(30),"
					+ "dateDebut DATE,"
					+ "dateFin DATE,"
					+ "login VARCHAR(30),"
					+ "nomPays VARCHAR(50),"
					+ "idEquipe INTEGER,"
					+ "statut VARCHAR(50),"
					+ "CONSTRAINT PK_Tournoi_nomTournoi PRIMARY KEY (nomTournoi)," 
					+ "CONSTRAINT FK_Tournoi_idEquipe FOREIGN KEY (idEquipe) REFERENCES Equipe(idEquipe) ON DELETE CASCADE,"
					+ "CONSTRAINT FK_Tournoi_login FOREIGN KEY (login) REFERENCES Compte(login) ON DELETE CASCADE,"
					+ "CONSTRAINT FK_Tournoi_nomPays FOREIGN KEY (nomPays) REFERENCES Pays(nomPays) ON DELETE CASCADE)");
			System.out.println("-- Table Tournoi créée");
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		// Table Partie
		try {
			stmt.executeUpdate("CREATE TABLE Partie ("
					+ "datePartie DATE,"
					+ "heureDebut CHAR(5),"
					+ "deroulement VARCHAR(15),"
					+ "idEquipe INTEGER,"
					+ "nomTournoi VARCHAR(100),"
					+ "CONSTRAINT PK_Partie_date_heureDebut PRIMARY KEY (datePartie, heureDebut),"
					+ "CONSTRAINT FK_Partie_idEquipe FOREIGN KEY (idEquipe) REFERENCES Equipe(idEquipe),"
					+ "CONSTRAINT FK_Partie_nomTournoi FOREIGN KEY (nomTournoi) REFERENCES Tournoi(nomTournoi))");
			System.out.println("-- Table Partie créée");
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		// Table Arbitre
		try {
			stmt.executeUpdate("CREATE TABLE Arbitre ("
					+ "idArbitre INTEGER,"
					+ "nomArbitre VARCHAR(50),"
					+ "prenomArbitre VARCHAR(50),"
					+ "login VARCHAR(30),"
					+ "CONSTRAINT PK_Arbitre_idArbitre PRIMARY KEY (idArbitre),"
					+ "CONSTRAINT FK_Arbitre_login FOREIGN KEY (login) REFERENCES Compte(login),"
					+ "CONSTRAINT UN_Arbitre_nomPrenom UNIQUE (nomArbitre, prenomArbitre))");
			System.out.println("-- Table Arbitre créée");
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		// Table Administrateur 
		try {
			stmt.executeUpdate("CREATE TABLE Administrateur ("
					+ "idAdministrateur INTEGER,"
					+ "nomAdmin VARCHAR(50),"
					+ "prenomAdmin VARCHAR(50),"
					+ "login VARCHAR(30),"
					+ "CONSTRAINT PK_Administrateur_idAdmin PRIMARY KEY (idAdministrateur),"
					+ "CONSTRAINT FK_Administrateur_login FOREIGN KEY (login) REFERENCES Compte(login),"
					+ "CONSTRAINT UN_Administrateur_nomPrenom UNIQUE (nomAdmin, prenomAdmin))");
			System.out.println("-- Table Administrateur créée");
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		// Table Participer 
		try {
			stmt.executeUpdate("CREATE TABLE Participer ("
					+ "idEquipe INTEGER,"
					+ "nomTournoi VARCHAR(100),"
					+ "nbPointsPouleGagnes INTEGER,"
					+ "nbPointsTournoiGagnes INTEGER,"
					+ "nbMatchsJoues INTEGER,"
					+ "nbMatchsGagnes INTEGER,"
					+ "classement INTEGER,"
					+ "CONSTRAINT PK_Participer_idEquipeTournoi PRIMARY KEY (idEquipe, nomTournoi),"
					+ "CONSTRAINT FK_Participer_idEquipe FOREIGN KEY (idEquipe) REFERENCES Equipe(idEquipe) ON DELETE CASCADE,"
					+ "CONSTRAINT FK_Participer_nomTournoi FOREIGN KEY (nomTournoi) REFERENCES Tournoi(nomTournoi) ON DELETE CASCADE,"
					+ "CONSTRAINT CK_Participer_classement CHECK (0 <= classement AND classement <= 8))");
			System.out.println("-- Table Participer créée");
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		// Table Jouer 
		try {
			stmt.executeUpdate("CREATE TABLE Jouer ("
					+ "idEquipe INTEGER,"
					+ "datePartie DATE,"
					+ "heureDebut CHAR(5),"
					+ "CONSTRAINT PK_Jouer_idEquipe_date_heure PRIMARY KEY (idEquipe, datePartie, heureDebut),"
					+ "CONSTRAINT FK_Jouer_datePartie FOREIGN KEY (datePartie, heureDebut) REFERENCES Partie(datePartie, heureDebut),"
					+ "CONSTRAINT FK_Jouer_idEquipe FOREIGN KEY (idEquipe) REFERENCES Equipe(idEquipe))");
			System.out.println("-- Table Jouer créée");
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		// Table Arbitrer 
		try {
			stmt.executeUpdate("CREATE TABLE Arbitrer ("
					+ "idArbitre INTEGER,"
					+ "datePartie DATE,"
					+ "heureDebut CHAR(5),"
					+ "CONSTRAINT PK_Arbitrer_Arbitre_date_heure PRIMARY KEY (idArbitre, datePartie, heureDebut),"
					+ "CONSTRAINT FK_Arbitrer_datePartie FOREIGN KEY (datePartie, heureDebut) REFERENCES Partie(datePartie, heureDebut),"
					+ "CONSTRAINT FK_Arbitrer_idArbitre FOREIGN KEY (idArbitre) REFERENCES Arbitre(idArbitre))");
			System.out.println("-- Table Arbitrer créée");
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		// Table Associer
		try {
			stmt.executeUpdate("CREATE TABLE Associer ("
					+ "idArbitre INTEGER,"
					+ "nomTournoi VARCHAR(100),"
					+ "CONSTRAINT PK_Associer_idArbitreTournoi PRIMARY KEY (idArbitre, nomTournoi),"
					+ "CONSTRAINT FK_Associer_idArbitre FOREIGN KEY (idArbitre) REFERENCES Arbitre(idArbitre),"
					+ "CONSTRAINT FK_Associer_nomTournoi FOREIGN KEY (nomTournoi) REFERENCES Tournoi(nomTournoi))");
			System.out.println("-- Table Associer créée");
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		// ==========================================
		// ========== Initialiser Tables ============
		// ==========================================
		// Table Pays
		PaysJDBC paysJDBC = new PaysJDBC();
		for (Pays pays : Pays.values()) {
			try {
				paysJDBC.add(pays);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
