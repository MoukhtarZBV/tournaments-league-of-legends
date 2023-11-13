package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import entites.Administrateur;
import entites.Arbitre;

public class ArbitreJDBC implements ArbitreDAO{
	
	public static Connection con;
    
	public ArbitreJDBC(Connection connect) {
	    con = connect;
	}

	@Override
	public List<Arbitre> getAll() throws Exception {
		Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("select * from Arbitre");
        List<Arbitre> listeArbitres = new ArrayList<Arbitre>();
        
        while (rs.next()) {
        	listeArbitres.add(new Arbitre(rs.getInt("idArbitre"),
            		                 rs.getString("nom"),
            		                 rs.getString("prenom")));  	        
        }
        return listeArbitres;
	}

	@Override
	public Optional<Arbitre> getById(Integer id) throws Exception {
		try {
			String req = "SELECT * FROM Arbitre WHERE idArbitre = ?;";
			
			PreparedStatement st = con.prepareStatement(req);
			st.setInt(1, id);
			
			ResultSet rs = st.executeQuery(req);
			
			return Optional.ofNullable(new Arbitre(rs.getInt("idArbitre"), rs.getString("nom"),rs.getString("prenom")));
			
		} catch (SQLException e) {
			return Optional.empty();
		}
	}
	
	@Override
	public Optional<Arbitre> getByNomPrenom(String nom, String prenom) throws Exception {
		try {
			String req = "SELECT * FROM Arbitre WHERE nom = ? AND prenom = ?;";
			
			PreparedStatement st = con.prepareStatement(req);
			st.setString(1, nom);
			st.setString(2, prenom);
			
			ResultSet rs = st.executeQuery(req);
			
			return Optional.ofNullable(new Arbitre(rs.getInt("idArbitre"), rs.getString("nom"),rs.getString("prenom")));
			
		} catch (SQLException e) {
			return Optional.empty();
		}
	}

	@Override
	public boolean add(Arbitre value) throws Exception {
		try {
			String addAdmin = "INSERT INTO Arbitre VALUES (NEXT VALUE FOR SEQ_Arbitre, ?, ?)";
			
			PreparedStatement st  = con.prepareStatement(addAdmin);
			
			st.setString(1, value.getNom());
			st.setString(2, value.getPrenom());
			
			st.executeUpdate(addAdmin);
			
			System.out.println("L'arbitre "+ value.getNom().toUpperCase() + " " + value.getPrenom()  +" a été ajouté.");
			return true;
			
		} catch (SQLException e) {
			return false;
		}
	}

	@Override
	public boolean update(Arbitre value) throws Exception {
		try {
			String updateAdmin = "UPDATE Arbitre "
					   		   + "SET nom = ?, prenom = ?"
					   		   + "WHERE idArbitre = ?;";
			
			PreparedStatement st  = con.prepareStatement(updateAdmin);
			st.setString(1, value.getNom());
			st.setString(2, value.getPrenom());
			st.setInt(3, value.getId());

			st.executeUpdate(updateAdmin);
			
			System.out.println("L'arbitre "+ value.getNom().toUpperCase() + " " + value.getPrenom() + " a été modifié.");
			return true;
			
		} catch (SQLException e) {
			return false;
		}
	}

	@Override
	public boolean delete(Arbitre value) throws Exception {
		try {
			String updateAdmin = "DELETE FROM Arbitre WHERE idArbitre = ?;";
			
			PreparedStatement st  = con.prepareStatement(updateAdmin);
			st.setInt(1, value.getId());
			
			st.executeUpdate();
			
			System.out.println("L'arbitre "+ value.getNom().toUpperCase() + " " + value.getPrenom() + " a été supprimé.");
			return true;
			
		} catch (SQLException e) {
			return false;
		}
	}

}
