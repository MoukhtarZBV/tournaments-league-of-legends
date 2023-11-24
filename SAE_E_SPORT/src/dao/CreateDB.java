package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import modele.Niveau;
import modele.Pays;

public class CreateDB {
	
	public static void main(String[] args) {
		CreateDB db = new CreateDB();
	}

	public CreateDB() {
		try {
			Connection connection = ConnectionJDBC.getConnection();
			createTables(connection);
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static void createTables(Connection connection) {
		
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
			stmt.executeUpdate("DROP TABLE NiveauTournoi");
		} catch (SQLException e) {
			System.out.println("Drop NiveauTournoi échoué");
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
			stmt.executeUpdate("DROP TABLE TypeCompte");
		} catch (SQLException e) {
			System.out.println("Drop TypeCompte échoué");
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
			stmt.executeUpdate("DROP SEQUENCE SEQ_Compte RESTRICT");
		} catch (SQLException e) {
			System.out.println("Drop SEQ_Compte échoué");
		}
		
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
			stmt.executeUpdate("DROP SEQUENCE SEQ_Tournoi RESTRICT");
		} catch (SQLException e) {
			System.out.println("Drop SEQ_Tournoi échoué");
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
			stmt.executeUpdate("CREATE SEQUENCE SEQ_Compte START WITH 1 INCREMENT BY 1");
			System.out.println("-- Séquence SEQ_Compte créée");
		} catch (SQLException e) {
			e.printStackTrace();
		}

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
			stmt.executeUpdate("CREATE SEQUENCE SEQ_Tournoi START WITH 1 INCREMENT BY 1");
			System.out.println("-- Séquence SEQ_Tournoi créée");
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
		
		// Table TypeCompte
		try {
			stmt.executeUpdate("CREATE TABLE TypeCompte ("
					+ "type VARCHAR(20),"
					+ "CONSTRAINT PK_TypeCompte_type PRIMARY KEY (type))");
			System.out.println("-- Table TypeCompte créée");
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		// Table Compte
		try { 
			stmt.executeUpdate("CREATE TABLE Compte ("
					+ "idCompte INTEGER,"
					+ "login VARCHAR(30),"
					+ "motDePasse VARCHAR(30),"
					+ "type VARCHAR(20),"
					+ "CONSTRAINT PK_Compte_idCompte PRIMARY KEY (idCompte),"
					+ "CONSTRAINT FK_Compte_type FOREIGN KEY (type) REFERENCES TypeCompte(type))");
			System.out.println("-- Table Compte créée");
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		// Table Equipe
		try {
			stmt.executeUpdate("CREATE TABLE Equipe ("
					+ "idEquipe INTEGER,"
					+ "nomEquipe VARCHAR(100),"
					+ "rang INTEGER,"
					+ "nationalite VARCHAR(50),"
					+ "CONSTRAINT PK_Equipe_idEquipe PRIMARY KEY(idEquipe),"
					+ "CONSTRAINT FK_Equipe_nationalite FOREIGN KEY (nationalite) REFERENCES Pays(nomPays))");
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
					+ "CONSTRAINT FK_Joueur_idEquipe FOREIGN KEY (idEquipe) REFERENCES Equipe(idEquipe))");
			System.out.println("-- Table Joueur créée");
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		// Table NiveauTournoi
		try {
			stmt.executeUpdate("CREATE TABLE NiveauTournoi ("
					+ "niveau VARCHAR(30),"
					+ "CONSTRAINT PK_NiveauTournoi_niveau PRIMARY KEY (niveau))");
			System.out.println("-- Table NiveauTournoi créée");
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		// Table Tournoi
		try {
			stmt.executeUpdate("CREATE TABLE Tournoi ("
					+ "idTournoi INTEGER,"
					+ "nomTournoi VARCHAR(100),"
					+ "niveau VARCHAR(30),"
					+ "dateDebut DATE,"
					+ "dateFin DATE,"
					+ "idCompte INTEGER,"
					+ "nomPays VARCHAR(50),"
					+ "idEquipe INTEGER,"
					+ "CONSTRAINT PK_Tournoi_idTournoi PRIMARY KEY (idTournoi)," 
					+ "CONSTRAINT FK_Tournoi_idEquipe FOREIGN KEY (idEquipe) REFERENCES Equipe(idEquipe),"
					+ "CONSTRAINT FK_Tournoi_idCompte FOREIGN KEY (idCompte) REFERENCES Compte(idCompte),"
					+ "CONSTRAINT FK_Tournoi_nomPays FOREIGN KEY (nomPays) REFERENCES Pays(nomPays),"
					+ "CONSTRAINT FK_Tournoi_niveau FOREIGN KEY (niveau) REFERENCES NiveauTournoi(niveau))");
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
					+ "idTournoi INTEGER,"
					+ "CONSTRAINT PK_Partie_date_heureDebut PRIMARY KEY (datePartie, heureDebut),"
					+ "CONSTRAINT FK_Partie_idEquipe FOREIGN KEY (idEquipe) REFERENCES Equipe(idEquipe),"
					+ "CONSTRAINT FK_Partie_idTournoi FOREIGN KEY (idTournoi) REFERENCES Tournoi(idTournoi))");
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
					+ "CONSTRAINT PK_Arbitre_idArbitre PRIMARY KEY (idArbitre))");
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
					+ "idCompte INTEGER,"
					+ "CONSTRAINT PK_Administrateur_idAdmin PRIMARY KEY (idAdministrateur),"
					+ "CONSTRAINT FK_Administrateur_idCompte FOREIGN KEY (idCompte) REFERENCES Compte(idCompte))");
			System.out.println("-- Table Administrateur créée");
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		// Table Participer 
		try {
			stmt.executeUpdate("CREATE TABLE Participer ("
					+ "idEquipe INTEGER,"
					+ "idTournoi INTEGER,"
					+ "nbPointsGagnes INTEGER,"
					+ "nbMatchsJoues INTEGER,"
					+ "nbMatchsGagnes INTEGER,"
					+ "CONSTRAINT PK_Participer_idEquipeTournoi PRIMARY KEY (idEquipe, idTournoi),"
					+ "CONSTRAINT FK_Participer_idEquipe FOREIGN KEY (idEquipe) REFERENCES Equipe(idEquipe),"
					+ "CONSTRAINT FK_Participer_idTournoi FOREIGN KEY (idTournoi) REFERENCES Tournoi(idTournoi))");
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
					+ "idTournoi INTEGER,"
					+ "CONSTRAINT PK_Associer_idArbitreTournoi PRIMARY KEY (idArbitre, idTournoi),"
					+ "CONSTRAINT FK_Associer_idArbitre FOREIGN KEY (idArbitre) REFERENCES Arbitre(idArbitre),"
					+ "CONSTRAINT FK_Associer_idTournoi FOREIGN KEY (idTournoi) REFERENCES Tournoi(idTournoi))");
			System.out.println("-- Table Associer créée");
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		// ==========================================
		// ========== Initialiser Tables ============
		// ==========================================
		// Table Pays
		PaysJDBC paysJDBC = PaysJDBC.getInstance();
		for (Pays pays : Pays.values()) {
			try {
				paysJDBC.add(pays);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		// Table Niveau
		NiveauJDBC niveauJDBC = new NiveauJDBC();
		for (Niveau niveau : Niveau.values()) {
			try {
				niveauJDBC.add(niveau);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
