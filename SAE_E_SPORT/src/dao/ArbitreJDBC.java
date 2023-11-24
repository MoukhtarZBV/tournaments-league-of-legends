package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import modele.Arbitre;

public class ArbitreJDBC implements ArbitreDAO{
	
	private Connection con;
	private static ArbitreJDBC arbitreDB;

	private ArbitreJDBC (Connection c) {
		con = c;
	}
	
	public static synchronized ArbitreJDBC getInstance() {
		if(arbitreDB == null) {
			arbitreDB = new ArbitreJDBC(ConnectionJDBC.getConnection());
		}
		return arbitreDB;
	}
	
	@Override
	public List<Arbitre> getAll() throws Exception {
        List<Arbitre> listeArbitres = new ArrayList<>();
		try {
			Statement st = con.createStatement();
	        ResultSet rs = st.executeQuery("select * from Arbitre");
	        
	        while (rs.next()) {
	        	listeArbitres.add(new Arbitre(rs.getInt("idArbitre"),
	            		                 rs.getString("nom"),
	            		                 rs.getString("prenom")));  	        
	        }
	        
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return listeArbitres;
	}

	@Override
	public Optional<Arbitre> getById(Integer id) throws Exception {
		Optional<Arbitre> opt = Optional.empty();
		try {
			String req = "SELECT * FROM Arbitre WHERE idArbitre = ?;";
			
			PreparedStatement st = con.prepareStatement(req);
			st.setInt(1, id);
			
			ResultSet rs = st.executeQuery(req);
			
			opt = Optional.ofNullable(new Arbitre(rs.getInt("idArbitre"), rs.getString("nom"),rs.getString("prenom")));
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return opt;
	}
	
	@Override
	public Optional<Arbitre> getByNomPrenom(String nom, String prenom) throws Exception {
		Optional<Arbitre> opt = Optional.empty();
		try {
			String req = "SELECT * FROM Arbitre WHERE nom = ? AND prenom = ?;";
			
			PreparedStatement st = con.prepareStatement(req);
			st.setString(1, nom);
			st.setString(2, prenom);
			
			ResultSet rs = st.executeQuery(req);
			
			opt = Optional.ofNullable(new Arbitre(rs.getInt("idArbitre"), rs.getString("nom"),rs.getString("prenom")));
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return opt;
	}

	@Override
	public boolean add(Arbitre value) throws Exception {
		boolean res = false;
		try {
			String addArbitre = "INSERT INTO Arbitre VALUES (NEXT VALUE FOR SEQ_Arbitre, ?, ?)";
			
			PreparedStatement st  = con.prepareStatement(addArbitre);
			
			st.setString(1, value.getNom());
			st.setString(2, value.getPrenom());
			
			st.executeUpdate(addArbitre);
			
			System.out.println("L'arbitre "+ value.getNom().toUpperCase() + " " + value.getPrenom()  +" a été ajouté.");
			res = true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public boolean update(Arbitre value) throws Exception {
		boolean res = false;
		try {
			String updateArbitre = "UPDATE Arbitre "
					   		   + "SET nom = ?, prenom = ?"
					   		   + "WHERE idArbitre = ?;";
			
			PreparedStatement st  = con.prepareStatement(updateArbitre);
			st.setString(1, value.getNom());
			st.setString(2, value.getPrenom());
			st.setInt(3, value.getId());

			st.executeUpdate(updateArbitre);
			
			System.out.println("L'arbitre "+ value.getNom().toUpperCase() + " " + value.getPrenom() + " a été modifié.");
			res = true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public boolean delete(Arbitre value) throws Exception {
		boolean res = false;
		try {
			String updateArbitre = "DELETE FROM Arbitre WHERE idArbitre = ?;";
			
			PreparedStatement st  = con.prepareStatement(updateArbitre);
			st.setInt(1, value.getId());
			
			st.executeUpdate();
			
			System.out.println("L'arbitre "+ value.getNom().toUpperCase() + " " + value.getPrenom() + " a été supprimé.");
			res = true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

}
