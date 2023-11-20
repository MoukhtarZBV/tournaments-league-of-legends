package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import modele.Joueur;

public class JoueurJDBC implements JoueurDAO{
	
	private static Connection con;

	@Override
	public List<Joueur> getAll() throws Exception {
        List<Joueur> listeJoueurs = new ArrayList<Joueur>();
        try {
    		con = ConnectionJDBC.getConnection();

			Statement st = con.createStatement();
	        ResultSet rs = st.executeQuery("select * from joueur");
        
	        while (rs.next()) {
	        	listeJoueurs.add(new Joueur(rs.getInt("idJoueur"),
	            		                 rs.getString("pseudo")));  	        
	        }
	        con.close();
        } catch (SQLException e) {
        	e.printStackTrace();
        }
        return listeJoueurs;
	}

	@Override
	public Optional<Joueur> getById(Integer id) throws Exception {
		Optional<Joueur> opt = Optional.empty();
		try {
    		con = ConnectionJDBC.getConnection();

			String req = "SELECT * FROM Joueur WHERE idJoueur = ?;";
			
			PreparedStatement st = con.prepareStatement(req);
			st.setInt(1, id);
			
			ResultSet rs = st.executeQuery(req);
			
			opt = Optional.ofNullable(new Joueur(rs.getInt("idJoueur"), rs.getString("pseudo")));
			
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return opt;
	}
	
	@Override
	public Optional<Joueur> getByPseudo(String value) throws Exception {
		Optional<Joueur> opt = Optional.empty();
		try {
    		con = ConnectionJDBC.getConnection();

			String req = "SELECT * FROM Joueur WHERE pseudo = ?;";
			
			PreparedStatement st = con.prepareStatement(req);
			st.setString(1, value);
			
			ResultSet rs = st.executeQuery(req);
			
			opt = Optional.ofNullable(new Joueur(rs.getInt("idJoueur"), rs.getString("pseudo")));
			
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return opt;
	}

	@Override
	public boolean add(Joueur value) throws Exception {
		boolean res = false;
		try {
    		con = ConnectionJDBC.getConnection();

			String addJoueur = "INSERT INTO Joueur VALUES (NEXT VALUE FOR SEQ_Joueur, ?)";
			
			PreparedStatement st  = con.prepareStatement(addJoueur);
			
			st.setString(1, value.getPseudo());
			
			st.executeUpdate(addJoueur);
			
			System.out.println("Le joueur "+ value.getPseudo().toUpperCase() +" a été ajouté.");
			res = true;
			
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public boolean update(Joueur value) throws Exception {
		boolean res = false;
		try {
    		con = ConnectionJDBC.getConnection();

			String updateJoueur = "UPDATE Joueur "
					   		   + "SET pseudo = ?"
					   		   + "WHERE idJoueur = ?;";
			
			PreparedStatement st  = con.prepareStatement(updateJoueur);
			st.setString(1, value.getPseudo());
			st.setInt(2, value.getId());

			st.executeUpdate(updateJoueur);
			
			System.out.println("Le joueur " + value.getPseudo().toUpperCase() + " a été modifié.");
			res = true;
			
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();;
		}
		return res;
	}

	@Override
	public boolean delete(Joueur value) throws Exception {
		boolean res = false;
		try {
    		con = ConnectionJDBC.getConnection();

			String updateJoueur = "DELETE FROM Joueur WHERE idJoueur = ?;";
			
			PreparedStatement st  = con.prepareStatement(updateJoueur);
			st.setInt(1, value.getId());
			
			st.executeUpdate();
			
			System.out.println("Le joueur " + value.getPseudo().toUpperCase() + " a été supprimé.");
			res = true;
			
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

}
