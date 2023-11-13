package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import entites.Administrateur;

public class AdminJDBC implements AdminDAO {
	
	private static Connection con;
	
	public AdminJDBC(Connection connection) {
		con = connection;
	}

	@Override
	public List<Administrateur> getAll() throws Exception {
		try {			
			Statement st = con.createStatement();
			
			String req   = "SELECT * FROM Administrateur";
			ResultSet rs = st.executeQuery(req);
			
			List<Administrateur> list = new ArrayList<Administrateur>();
			while(rs.next()) {
				list.add(new Administrateur(rs.getInt("idAdministrateur"), rs.getString("nomAdmin"), rs.getString("prenomAdmin")));
			}
			return list;
			
		} catch (SQLException e) {
			return null;
		}
	}

	@Override
	public Optional<Administrateur> getById(Integer id) throws Exception {
		try {
			String req = "SELECT * FROM Administrateur WHERE idAdministrateur = ?;";
			
			PreparedStatement st = con.prepareStatement(req);
			st.setInt(1, id);
			
			ResultSet rs = st.executeQuery(req);
			
			return Optional.ofNullable(new Administrateur(rs.getInt("idAdministrateur"), rs.getString("nomAdmin"), rs.getString("prenomAdmin")));
			
		} catch (SQLException e) {
			return Optional.empty();
		}
	}

	@Override
	public boolean add(Administrateur value) throws Exception {
		try {
			String addAdmin = "INSERT INTO Administrateur VALUES (NEXT VALUE FOR SEQ_Administrateur, ?, ?)";
			
			PreparedStatement st  = con.prepareStatement(addAdmin);
			
			st.setString(1, value.getNom());
			st.setString(2, value.getPrenom());
			
			st.executeUpdate(addAdmin);
			
			System.out.println("L'administrateur "+ value.getNom().toUpperCase() +" a été ajouté.");
			return true;
			
		} catch (SQLException e) {
			return false;
		}
	}

	@Override
	public boolean update(Administrateur value) throws Exception {
		try {
			String updateAdmin = "UPDATE Administrateur "
					   		   + "SET nom = ?, prenom = ? "
					   		   + "WHERE idAdministrateur = ?;";
			
			PreparedStatement st  = con.prepareStatement(updateAdmin);
			st.setString(1, value.getNom());
			st.setString(2, value.getPrenom());

			st.executeUpdate(updateAdmin);
			
			System.out.println("L'Administrateur " + value.getNom().toUpperCase() + " a été modifié.");
			return true;
			
		} catch (SQLException e) {
			return false;
		}
	}

	@Override
	public boolean delete(Administrateur value) throws Exception {
		try {
			String updateAdmin = "DELETE FROM Administrateur WHERE idAdministrateur = ?;";
			
			PreparedStatement st  = con.prepareStatement(updateAdmin);
			st.setInt(1, value.getId());
			
			st.executeUpdate();
			
			System.out.println("L'Administrateur " + value.getNom().toUpperCase() + " a été supprimé.");
			return true;
			
		} catch (SQLException e) {
			return false;
		}
	}

	@Override
	public Optional<Administrateur> getByNom(String nom) throws Exception {
		try {
			String req = "SELECT * FROM Administrateur WHERE nomAdmin = ?;";
			
			PreparedStatement st = con.prepareStatement(req);
			st.setString(1,  nom);
			
			ResultSet rs = st.executeQuery();
			
			return Optional.ofNullable(new Administrateur(rs.getInt("idAdministrateur"), rs.getString("nomAdmin"), rs.getString("prenomAdmin")));
			
		} catch (SQLException e) {
			return Optional.empty();
		}
	}

	@Override
	public Optional<Administrateur> getByNomPrenom(String nom, String prenom) throws Exception {
		try {
			String req = "SELECT * FROM Administrateur WHERE nomAdmin = ? AND prenomAdmin = ?;";
			
			PreparedStatement st = con.prepareStatement(req);
			st.setString(1, nom);
			st.setString(2, prenom);
		
			ResultSet rs = st.executeQuery(req);
			
			return Optional.ofNullable(new Administrateur(rs.getInt("idAdministrateur"), rs.getString("nomAdmin"), rs.getString("prenomAdmin")));
			
		} catch (SQLException e) {
			return Optional.empty();
		}
	}

}
