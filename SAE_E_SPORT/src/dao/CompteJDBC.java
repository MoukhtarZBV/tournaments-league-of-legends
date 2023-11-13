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
	
	public static Connection con;
    
	public CompteJDBC(Connection connect) {
	    con = connect;
	}

	@Override
	public List<Compte> getAll() throws Exception {
		Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("select * from Compte");
        List<Compte> listeComptes = new ArrayList<Compte>();
        
        while (rs.next()) {
        	listeComptes.add(new Compte(rs.getInt("idCompte"),
            		                 rs.getString("login"),
            		                 rs.getString("motDePasse"),
            		                 TypeCompte.valueOf(rs.getString("type"))));  	        
        }
        return listeComptes;
	}

	@Override
	public Optional<Compte> getById(Integer id) throws Exception {
		try {
			String req = "SELECT * FROM Compte WHERE idCompte = ?;";
			
			PreparedStatement st = con.prepareStatement(req);
			st.setInt(1, id);
			
			ResultSet rs = st.executeQuery(req);
			
			return Optional.ofNullable(new Compte(rs.getInt("idCompte"),
	                 rs.getString("login"),
	                 rs.getString("motDePasse"),
	                 TypeCompte.valueOf(rs.getString("type"))));
			
		} catch (SQLException e) {
			return Optional.empty();
		}
	}

	@Override
	public boolean add(Compte value) throws Exception {
		try {
			String addCompte = "INSERT INTO Compte VALUES (NEXT VALUE FOR SEQ_Compte, ?, ?, ?)";
			
			PreparedStatement st  = con.prepareStatement(addCompte);
			
			st.setString(1, value.getLogin());
			st.setString(2, value.getMotDePasse());
			st.setString(3,value.getType().toString());
			
			st.executeUpdate(addCompte);
			
			System.out.println("Le compte "+ value.getLogin().toUpperCase() + " " + value.getMotDePasse() + value.getType() +" a été ajouté.");
			return true;
			
		} catch (SQLException e) {
			return false;
		}
	}

	@Override
	public boolean update(Compte value) throws Exception {
		try {
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
			return true;
			
		} catch (SQLException e) {
			return false;
		}
	}

	@Override
	public boolean delete(Compte value) throws Exception {
		try {
			String updateCompte = "DELETE FROM Compte WHERE idCompte = ?;";
			
			PreparedStatement st  = con.prepareStatement(updateCompte);
			st.setInt(1, value.getId());
			
			st.executeUpdate();
			
			System.out.println("Le compte "+ value.getLogin().toUpperCase() + " " + value.getMotDePasse() + value.getType() + " a été supprimé.");
			return true;
			
		} catch (SQLException e) {
			return false;
		}
	}

}
