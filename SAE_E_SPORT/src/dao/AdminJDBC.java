package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import modele.Administrateur;

public class AdminJDBC implements AdminDAO {
	
	private static Connection con;
	
	public AdminJDBC() {
		con = ConnectionJDBC.getConnection();
	}

	@Override
	public List<Administrateur> getAll() throws Exception {
		List<Administrateur> list = new ArrayList<>();
		try {			
		    con = ConnectionJDBC.getConnection();

			Statement st = con.createStatement();
			
			String req   = "SELECT * FROM Administrateur";
			ResultSet rs = st.executeQuery(req);
			
			while(rs.next()) {
				list.add(new Administrateur(rs.getInt("idAdministrateur"), rs.getString("nomAdmin"), rs.getString("prenomAdmin")));
			}
			
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Optional<Administrateur> getById(Integer id) throws Exception {
		Optional<Administrateur> opt = Optional.empty();
		try {
		    con = ConnectionJDBC.getConnection();

			String req = "SELECT * FROM Administrateur WHERE idAdministrateur = ?;";
			
			PreparedStatement st = con.prepareStatement(req);
			st.setInt(1, id);
			
			ResultSet rs = st.executeQuery(req);
			
			opt = Optional.ofNullable(new Administrateur(rs.getInt("idAdministrateur"), rs.getString("nomAdmin"), rs.getString("prenomAdmin")));
			
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return opt;
	}

	@Override
	public boolean add(Administrateur value) throws Exception {
		boolean res = false;
		try {
		    con = ConnectionJDBC.getConnection();

			String addAdmin = "INSERT INTO Administrateur VALUES (NEXT VALUE FOR SEQ_Administrateur, ?, ?)";
			
			PreparedStatement st  = con.prepareStatement(addAdmin);
			
			st.setString(1, value.getNom());
			st.setString(2, value.getPrenom());
			
			st.executeUpdate(addAdmin);
			
			System.out.println("L'administrateur "+ value.getNom().toUpperCase() +" a été ajouté.");
			res = true;
			
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public boolean update(Administrateur value) throws Exception {
		boolean res = false;
		try {
		    con = ConnectionJDBC.getConnection();

			String updateAdmin = "UPDATE Administrateur "
					   		   + "SET nom = ?, prenom = ? "
					   		   + "WHERE idAdministrateur = ?;";
			
			PreparedStatement st  = con.prepareStatement(updateAdmin);
			st.setString(1, value.getNom());
			st.setString(2, value.getPrenom());

			st.executeUpdate(updateAdmin);
			
			System.out.println("L'Administrateur " + value.getNom().toUpperCase() + " a été modifié.");
			res = true;
			
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public boolean delete(Administrateur value) throws Exception {
		boolean res = false;
		try {
		    con = ConnectionJDBC.getConnection();

			String updateAdmin = "DELETE FROM Administrateur WHERE idAdministrateur = ?;";
			
			PreparedStatement st  = con.prepareStatement(updateAdmin);
			st.setInt(1, value.getId());
			
			st.executeUpdate();
			
			System.out.println("L'Administrateur " + value.getNom().toUpperCase() + " a été supprimé.");
			res = true;
			
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public Optional<Administrateur> getByNom(String nom) throws Exception {
		Optional<Administrateur> opt = Optional.empty();
		try {
		    con = ConnectionJDBC.getConnection();

			String req = "SELECT * FROM Administrateur WHERE nomAdmin = ?;";
			
			PreparedStatement st = con.prepareStatement(req);
			st.setString(1,  nom);
			
			ResultSet rs = st.executeQuery();
			
			opt = Optional.ofNullable(new Administrateur(rs.getInt("idAdministrateur"), rs.getString("nomAdmin"), rs.getString("prenomAdmin")));
			
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return opt;
	}

	@Override
	public Optional<Administrateur> getByNomPrenom(String nom, String prenom) throws Exception {
		Optional<Administrateur> opt = Optional.empty();
		try {
		    con = ConnectionJDBC.getConnection();

			String req = "SELECT * FROM Administrateur WHERE nomAdmin = ? AND prenomAdmin = ?;";
			
			PreparedStatement st = con.prepareStatement(req);
			st.setString(1, nom);
			st.setString(2, prenom);
		
			ResultSet rs = st.executeQuery(req);
			
			opt = Optional.ofNullable(new Administrateur(rs.getInt("idAdministrateur"), rs.getString("nomAdmin"), rs.getString("prenomAdmin")));
			
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();;
		}
		return opt;
	}

}
