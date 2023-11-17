package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateDB {
	
	public static void main(String[] args) {
		
		System.out.println("CREATE TABLE Pays ("
				+ "nomPays VARCHAR(50),"
				+ "CONSTRAINT PK_Pays_nomPays PRIMARY KEY (nomPays))");
		
		System.out.println("CREATE TABLE TypeCompte ("
				+ "type VARCHAR(20),"
				+ "CONSTRAINT PK_TypeCompte_type PRIMARY KEY (type))");
		
		System.out.println("CREATE TABLE Compte ("
				+ "idCompte INTEGER,"
				+ "login VARCHAR(30),"
				+ "motDePasse VARCHAR(30),"
				+ "type VARCHAR(20),"
				+ "CONSTRAINT PK_Compte_idCompte PRIMARY KEY (idCompte))");
		
		System.out.println("CREATE TABLE Equipe ("
				+ "idEquipe INTEGER,"
				+ "nomEquipe VARCHAR(100),"
				+ "rang INTEGER,"
				+ "nationalite VARCHAR(50),"
				+ "CONSTRAINT PK_Equipe_idEquipe PRIMARY KEY(idEquipe),"
				+ "CONSTRAINT FK_Equipe_nationalite FOREIGN KEY (nationalite) REFERENCES Pays(nomPays))");
		
		System.out.println("CREATE TABLE Joueur ("
				+ "idJoueur INTEGER,"
				+ "pseudo VARCHAR(50),"
				+ "idEquipe INTEGER,"
				+ "CONSTRAINT PK_Joueur_idJoueur PRIMARY KEY (idJoueur),"
				+ "CONSTRAINT FK_Joueur_idEquipe FOREIGN KEY (idEquipe) REFERENCES Equipe(idEquipe))");
		
		System.out.println("CREATE TABLE Partie ("
				+ "datePartie DATE,"
				+ "heureDebut CHAR(5),"
				+ "deroulement VARCHAR(15),"
				+ "idEquipe INTEGER,"
				+ "idTournoi INTEGER,"
				+ "CONSTRAINT PK_Partie_date_heureDebut PRIMARY KEY (datePartie, heureDebut),"
				+ "CONSTRAINT FK_Partie_idEquipe FOREIGN KEY (idEquipe) REFERENCES Equipe(idEquipe),"
				+ "CONSTRAINT FK_Partie_idTournoi FOREIGN KEY (idTournoi) REFERENCES Tournoi(idTournoi))");
		
		System.out.println("CREATE TABLE Arbitre ("
				+ "idArbitre INTEGER,"
				+ "nomArbitre VARCHAR(50),"
				+ "prenomArbitre VARCHAR(50),"
				+ "CONSTRAINT PK_Arbitre_idArbitre PRIMARY KEY (idArbitre))");
		
		System.out.println("CREATE TABLE NiveauTournoi ("
				+ "niveau VARCHAR(30),"
				+ "CONSTRAINT PK_NiveauTournoi_niveau PRIMARY KEY (niveau))");
		
		System.out.println("CREATE TABLE Tournoi ("
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
				+ "CONSTRAINT FK_Tournoi_nomPays FOREIGN KEY (nomPays) REFERENCES Pays(nomPays))");
		
		System.out.println("CREATE TABLE Administrateur ("
				+ "idAdministrateur INTEGER,"
				+ "nomAdmin VARCHAR(50),"
				+ "prenomAdmin VARCHAR(50),"
				+ "idCompte INTEGER,"
				+ "CONSTRAINT PK_Administrateur_idAdmin PRIMARY KEY (idAdministrateur),"
				+ "CONSTRAINT FK_Administrateur_idCompte FOREIGN KEY (idCompte) REFERENCES Compte(idCompte))");
		
		System.out.println("CREATE TABLE Participer ("
				+ "idEquipe INTEGER,"
				+ "idTournoi INTEGER,"
				+ "nbPointsGagnes INTEGER,"
				+ "nbMatchsJoues INTEGER,"
				+ "CONSTRAINT PK_Participer_idEquipeTournoi PRIMARY KEY (idEquipe, idTournoi))");
		
		System.out.println("CREATE TABLE Jouer ("
				+ "idEquipe INTEGER,"
				+ "datePartie DATE,"
				+ "heureDebut CHAR(5),"
				+ "CONSTRAINT PK_Jouer_idEquipe_date_heure PRIMARY KEY (idEquipe, datePartie, heureDebut),"
				+ "CONSTRAINT FK_Jouer_datePartie FOREIGN KEY (datePartie, heureDebut) REFERENCES Partie(datePartie, heureDebut),"
				+ "CONSTRAINT FK_Jouer_idEquipe FOREIGN KEY (idEquipe) REFERENCES Equipe(idEquipe))");
		
		System.out.println("CREATE TABLE Arbitrer ("
				+ "idArbitre INTEGER,"
				+ "datePartie DATE,"
				+ "heureDebut CHAR(5),"
				+ "CONSTRAINT PK_Arbitrer_Arbitre_date_heure PRIMARY KEY (idArbitre, datePartie, heureDebut),"
				+ "CONSTRAINT FK_Arbitrer_datePartie FOREIGN KEY (datePartie, heureDebut) REFERENCES Partie(datePartie, heureDebut),"
				+ "CONSTRAINT FK_Arbitrer_idArbitre FOREIGN KEY (idArbitre) REFERENCES Arbitre(idArbitre))");
		
		System.out.println("CREATE TABLE Associer ("
				+ "idArbitre INTEGER,"
				+ "idTournoi INTEGER,"
				+ "CONSTRAINT PK_Associer_idArbitreTournoi PRIMARY KEY (idArbitre, idTournoi),"
				+ "CONSTRAINT FK_Associer_idArbitre FOREIGN KEY (idArbitre) REFERENCES Arbitre(idArbitre),"
				+ "CONSTRAINT FK_Associer_idTournoi FOREIGN KEY (idTournoi) REFERENCES Tournoi(idTournoi))");
		CreateDB db = new CreateDB();
	}

	public CreateDB() {
		String dirProjetJava = System.getProperty("user.dir");
		System.setProperty("derby.system.home", dirProjetJava + "/BDD");		
		String urlConnexion = "jdbc:derby:BDD;create=true";
		
		try {
			DriverManager.registerDriver(new org.apache.derby.jdbc.EmbeddedDriver());
			Connection connection = DriverManager.getConnection(urlConnexion);
			createTables(connection);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
			stmt.executeUpdate("DROP TABLE Pays");
		} catch (SQLException e) {
			System.out.println("Drop pays échoué");
		}
		
		try {
			stmt.executeUpdate("DROP TABLE TypeCompte");
		} catch (SQLException e) {
			System.out.println("Drop échoué");
		}
		
		try {
			stmt.executeUpdate("DROP TABLE Compte");
		} catch (SQLException e) {
			System.out.println("Drop échoué");
		}
		
		try {
			stmt.executeUpdate("DROP TABLE Equipe");
		} catch (SQLException e) {
			System.out.println("Drop échoué");
		}
		
		try {
			stmt.executeUpdate("DROP TABLE Joueur");
		} catch (SQLException e) {
			System.out.println("Drop échoué");
		}
		
		try {
			stmt.executeUpdate("DROP TABLE Partie");
		} catch (SQLException e) {
			System.out.println("Drop échoué");
		}
		
		try {
			stmt.executeUpdate("DROP TABLE Arbitre");
		} catch (SQLException e) {
			System.out.println("Drop échoué");
		}
		
		try {
			stmt.executeUpdate("DROP TABLE NiveauTournoi");
		} catch (SQLException e) {
			System.out.println("Drop échoué");
		}
		
		try {
			stmt.executeUpdate("DROP TABLE Tournoi");
		} catch (SQLException e) {
			System.out.println("Drop échoué");
		}
		
		try {
			stmt.executeUpdate("DROP TABLE Administrateur");
		} catch (SQLException e) {
			System.out.println("Drop échoué");
		}
		
		try {
			stmt.executeUpdate("DROP TABLE Participer");
		} catch (SQLException e) {
			System.out.println("Drop échoué");
		}
		
		try {
			stmt.executeUpdate("DROP TABLE Jouer");
		} catch (SQLException e) {
			System.out.println("Drop échoué");
		}
		
		try {
			stmt.executeUpdate("DROP TABLE Arbitrer");
		} catch (SQLException e) {
			System.out.println("Drop échoué");
		}
		
		try {
			stmt.executeUpdate("DROP TABLE Associer");
		} catch (SQLException e) {
			System.out.println("Drop échoué");
		}
		
		// ====================================
		// ========== Drop Sequences ==========
		// ====================================
		try {
			stmt.executeUpdate("DROP SEQUENCE SEQ_Compte RESTRICT");
		} catch (SQLException e) {
			System.out.println("Drop échoué");
		}
		
		try {
			stmt.executeUpdate("DROP SEQUENCE SEQ_Equipe RESTRICT");
		} catch (SQLException e) {
			System.out.println("Drop échoué");
		}
		
		try {
			stmt.executeUpdate("DROP SEQUENCE SEQ_Joueur RESTRICT");
		} catch (SQLException e) {
			System.out.println("Drop échoué");
		}
		
		try {
			stmt.executeUpdate("DROP SEQUENCE SEQ_Arbitre RESTRICT");
		} catch (SQLException e) {
			System.out.println("Drop échoué");
		}
		
		try {
			stmt.executeUpdate("DROP SEQUENCE SEQ_Tournoi RESTRICT");
		} catch (SQLException e) {
			System.out.println("Drop échoué");
		}
		
		try {
			stmt.executeUpdate("DROP SEQUENCE SEQ_Administrateur RESTRICT");
		} catch (SQLException e) {
			System.out.println("Drop échoué");
		}
		
		// =====================================
		// ========== Créer Sequences ==========
		// =====================================
		try {
			stmt.executeUpdate("CREATE SEQUENCE SEQ_Compte START WITH 1 INCREMENT BY 1");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			stmt.executeUpdate("CREATE SEQUENCE SEQ_Equipe START WITH 1 INCREMENT BY 1");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			stmt.executeUpdate("CREATE SEQUENCE SEQ_Joueur START WITH 1 INCREMENT BY 1");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			stmt.executeUpdate("CREATE SEQUENCE SEQ_Arbitre START WITH 1 INCREMENT BY 1");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			stmt.executeUpdate("CREATE SEQUENCE SEQ_Tournoi START WITH 1 INCREMENT BY 1");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			stmt.executeUpdate("CREATE SEQUENCE SEQ_Administrateur START WITH 1 INCREMENT BY 1");
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
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		// Table TypeCompte
		try {
			stmt.executeUpdate("CREATE TABLE TypeCompte ("
					+ "type VARCHAR(20),"
					+ "CONSTRAINT PK_TypeCompte_type PRIMARY KEY (type))");
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
					+ "CONSTRAINT PK_Compte_idCompte PRIMARY KEY (idCompte))");
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
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		// Table NiveauTournoi
		try {
			stmt.executeUpdate("CREATE TABLE NiveauTournoi ("
					+ "niveau VARCHAR(30),"
					+ "CONSTRAINT PK_NiveauTournoi_niveau PRIMARY KEY (niveau))");
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
					+ "CONSTRAINT FK_Tournoi_nomPays FOREIGN KEY (nomPays) REFERENCES Pays(nomPays))");
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
					+ "CONSTRAINT PK_Participer_idEquipeTournoi PRIMARY KEY (idEquipe, idTournoi))");
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
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
}
