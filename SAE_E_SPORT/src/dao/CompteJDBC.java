package dao;

import java.sql.CallableStatement;
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
	
	@Override
	public List<Compte> getAll() throws Exception {
        List<Compte> listeComptes = new ArrayList<>();
		try {
			Statement st = ConnectionJDBC.getConnection().createStatement();
	        ResultSet rs = st.executeQuery("select * from Compte");
	        
	        while (rs.next()) {
	        	listeComptes.add(new Compte(rs.getInt("idCompte"),
	            		                 rs.getString("login"),
	            		                 rs.getString("motDePasse"),
	            		                 TypeCompte.getTypeCompte(rs.getString("type"))));
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return listeComptes;
	}

	@Override
	public Optional<Compte> getById(Integer id) {
		Optional<Compte> compte = Optional.empty();
		try {
			String req = "SELECT * FROM Compte WHERE idCompte = ?";
			
			 CallableStatement cs = ConnectionJDBC.getConnection().prepareCall(req);
			 cs.setInt(1, id);
			 
			 ResultSet rs = cs.executeQuery();
			if(rs.next()) {
				compte = Optional.ofNullable(new Compte(rs.getInt("idCompte"),
										                 rs.getString("login"),
										                 rs.getString("motDePasse"),
										                 TypeCompte.getTypeCompte(rs.getString("type"))));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return compte;
	}

	@Override
	public boolean add(Compte c) throws Exception {
		boolean res = false;
		try {
			String addCompte = "INSERT INTO Compte (idCompte, login, motDePasse, type) "
							 + "VALUES (?, ?, ?, ?)";
			
			PreparedStatement st  = ConnectionJDBC.getConnection().prepareStatement(addCompte);
			
			st.setInt(1, c.getId());
			st.setString(2, c.getLogin());
			st.setString(3, c.getMotDePasse());
			st.setString(4,c.getType().denomination());
			
			st.executeUpdate();
			
			res = true;
			System.out.println("Le compte "+ c.getLogin().toUpperCase() + " " + c.getMotDePasse() + " " + c.getType() +" a été ajouté.");
			
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
					   		   + "WHERE idCompte = ?";
			
			PreparedStatement st  = ConnectionJDBC.getConnection().prepareStatement(updateCompte);
			st.setString(1, c.getLogin());
			st.setString(2, c.getMotDePasse());
			st.setString(3,c.getType().denomination());
			st.setInt(4, c.getId());

			st.executeUpdate();
			
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
			String updateCompte = "DELETE FROM Compte WHERE idCompte = ?";
			
			PreparedStatement st  = ConnectionJDBC.getConnection().prepareStatement(updateCompte);
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
