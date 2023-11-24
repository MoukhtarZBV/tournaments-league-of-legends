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
	
	private Connection con;
	private static CompteJDBC compteDB;

	private CompteJDBC (Connection c) {
		this.con = c;
	}
	
	public static synchronized CompteJDBC getInstance() {
		if(compteDB == null) {
			compteDB = new CompteJDBC(ConnectionJDBC.getConnection());
		}
		return compteDB;
	}
	
	@Override
	public List<Compte> getAll() throws Exception {
        List<Compte> listeComptes = new ArrayList<>();
		try {
			Statement st = con.createStatement();
	        ResultSet rs = st.executeQuery("select * from Compte");
	        
	        while (rs.next()) {
	        	listeComptes.add(new Compte(rs.getInt("idCompte"),
	            		                 rs.getString("login"),
	            		                 rs.getString("motDePasse"),
	            		                 TypeCompte.valueOf(rs.getString("type").toUpperCase())));  	        
	        }
	        
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return listeComptes;
	}

	@Override
	public Optional<Compte> getById(Integer id) throws Exception {
		Optional<Compte> compte = Optional.empty();
		try {
			String req = "SELECT * FROM Compte WHERE idCompte = ?;";
			
			PreparedStatement st = con.prepareStatement(req);
			st.setInt(1, id);
			
			ResultSet rs = st.executeQuery(req);
			
			compte = Optional.ofNullable(new Compte(rs.getInt("idCompte"),
	                 rs.getString("login"),
	                 rs.getString("motDePasse"),
	                 TypeCompte.valueOf(rs.getString("type").toUpperCase())));
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return compte;
	}

	@Override
	public boolean add(Compte c) throws Exception {
		boolean res = false;
		try {
			String addCompte = "INSERT INTO Compte VALUES (NEXT VALUE FOR SEQ_Compte, ?, ?, ?)";
			
			PreparedStatement st  = con.prepareStatement(addCompte);
			
			st.setString(1, c.getLogin());
			st.setString(2, c.getMotDePasse());
			st.setString(3,c.getType().denomination());
			
			st.executeUpdate(addCompte);
			
			res = true;
			System.out.println("Le compte "+ c.getLogin().toUpperCase() + " " + c.getMotDePasse() + c.getType() +" a été ajouté.");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public boolean update(Compte c) throws Exception {
		boolean res = false;
		try {
			String updateCompte = "UPDATE Compte "
					   		   + "SET login = ?, motDePasse = ?, type = ?"
					   		   + "WHERE idCompte = ?;";
			
			PreparedStatement st  = con.prepareStatement(updateCompte);
			st.setString(1, c.getLogin());
			st.setString(2, c.getMotDePasse());
			st.setString(3,c.getType().toString());
			st.setInt(4, c.getId());

			st.executeUpdate(updateCompte);
			
			System.out.println("Le compte "+ c.getLogin().toUpperCase() + " " + c.getMotDePasse() + c.getType() + " a été modifié.");
			res = true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public boolean delete(Compte c) throws Exception {
		boolean res = false;
		try {
			String updateCompte = "DELETE FROM Compte WHERE idCompte = ?;";
			
			PreparedStatement st  = con.prepareStatement(updateCompte);
			st.setInt(1, c.getId());
			
			st.executeUpdate();
			
			System.out.println("Le compte "+ c.getLogin().toUpperCase() + " " + c.getMotDePasse() + c.getType() + " a été supprimé.");
			res = true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

}
