package dao;

import java.awt.Window.Type;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import modele.Compte;
import modele.TypeCompte;

public class CompteJDBC implements CompteDAO{
	
	private static Connection con;

	@Override
	public List<Compte> getAll() throws Exception {
        List<Compte> listeComptes = new ArrayList<>();
		try {
		    con = ConnectionJDBC.getConnection();

			Statement st = con.createStatement();
	        ResultSet rs = st.executeQuery("select * from Compte");
	        
	        while (rs.next()) {
	        	listeComptes.add(new Compte(rs.getInt("idCompte"),
	            		                 rs.getString("login"),
	            		                 rs.getString("motDePasse"),
	            		                 TypeCompte.valueOf(rs.getString("type"))));  	        
	        }
	        
	        con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return listeComptes;
	}

	@Override
	public Optional<Compte> getById(Integer id) throws Exception {
		Optional<Compte> compte = Optional.empty();
		try {
		    con = ConnectionJDBC.getConnection();

			String req = "SELECT * FROM Compte WHERE idCompte = ?;";
			
			PreparedStatement st = con.prepareStatement(req);
			st.setInt(1, id);
			
			ResultSet rs = st.executeQuery(req);
			
			compte = Optional.ofNullable(new Compte(rs.getInt("idCompte"),
	                 rs.getString("login"),
	                 rs.getString("motDePasse"),
	                 TypeCompte.valueOf(rs.getString("type"))));
			
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return compte;
	}

	@Override
	public boolean add(Compte value) throws Exception {
		boolean res = false;
		try {
		    con = ConnectionJDBC.getConnection();

			String addCompte = "INSERT INTO Compte VALUES (NEXT VALUE FOR SEQ_Compte, ?, ?, ?)";
			
			PreparedStatement st  = con.prepareStatement(addCompte);
			
			st.setString(1, value.getLogin());
			st.setString(2, value.getMotDePasse());
			st.setString(3,value.getType().denomination());
			
			st.executeUpdate(addCompte);
			
			res = true;
			System.out.println("Le compte "+ value.getLogin().toUpperCase() + " " + value.getMotDePasse() + value.getType() +" a été ajouté.");
			
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public boolean update(Compte value) throws Exception {
		boolean res = false;
		try {
		    con = ConnectionJDBC.getConnection();

			String updateCompte = "UPDATE Compte "
					   		   + "SET login = ?, motDePasse = ?, type = ?"
					   		   + "WHERE idCompte = ?;";
			
			PreparedStatement st  = con.prepareStatement(updateCompte);
			st.setString(1, value.getLogin());
			st.setString(2, value.getMotDePasse());
			st.setString(3,value.getType().toString());
			st.setInt(4, value.getId());

			st.executeUpdate(updateCompte);
			
			System.out.println("Le compte "+ value.getLogin().toUpperCase() + " " + value.getMotDePasse() + value.getType() + " a été modifié.");
			res = true;
			
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public boolean delete(Compte value) throws Exception {
		boolean res = false;
		try {
		    con = ConnectionJDBC.getConnection();

			String updateCompte = "DELETE FROM Compte WHERE idCompte = ?;";
			
			PreparedStatement st  = con.prepareStatement(updateCompte);
			st.setInt(1, value.getId());
			
			st.executeUpdate();
			
			System.out.println("Le compte "+ value.getLogin().toUpperCase() + " " + value.getMotDePasse() + value.getType() + " a été supprimé.");
			res = true;
			
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

}
