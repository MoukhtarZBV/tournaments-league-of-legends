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

import entites.Joueur;

public class JoueurJDBC implements JoueurDAO{
	
	public static Connection con;
    
	public JoueurJDBC(Connection connect) {
	    con = connect;
	}

	@Override
	public List<Joueur> getAll() throws Exception {
		Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("select * from joueur");
        List<Joueur> listeJoueurs = new ArrayList<Joueur>();
        
        while (rs.next()) {
        	listeJoueurs.add(new Joueur(rs.getInt("idJoueur"),
            		                 rs.getString("pseudo")));  	        
        }
        return listeJoueurs;
	}

	@Override
	public Optional<Joueur> getById(Integer id) throws Exception {
		try {
			String req = "SELECT * FROM Joueur WHERE idJoueur = ?;";
			
			PreparedStatement st = con.prepareStatement(req);
			st.setInt(1, id);
			
			ResultSet rs = st.executeQuery(req);
			
			return Optional.ofNullable(new Joueur(rs.getInt("idJoueur"), rs.getString("pseudo")));
			
		} catch (SQLException e) {
			return Optional.empty();
		}
	}
	
	@Override
	public Optional<Joueur> getByPseudo(String value) throws Exception {
		try {
			String req = "SELECT * FROM Joueur WHERE pseudo = ?;";
			
			PreparedStatement st = con.prepareStatement(req);
			st.setString(1, value);
			
			ResultSet rs = st.executeQuery(req);
			
			return Optional.ofNullable(new Joueur(rs.getInt("idJoueur"), rs.getString("pseudo")));
			
		} catch (SQLException e) {
			return Optional.empty();
		}
	}

	@Override
	public boolean add(Joueur value) throws Exception {
		try {
			String addJoueur = "INSERT INTO Joueur VALUES (NEXT VALUE FOR SEQ_Joueur, ?)";
			
			PreparedStatement st  = con.prepareStatement(addJoueur);
			
			st.setString(1, value.getPseudo());
			
			st.executeUpdate(addJoueur);
			
			System.out.println("Le joueur "+ value.getPseudo().toUpperCase() +" a été ajouté.");
			return true;
			
		} catch (SQLException e) {
			return false;
		}
	}

	@Override
	public boolean update(Joueur value) throws Exception {
		try {
			String updateJoueur = "UPDATE Joueur "
					   		   + "SET pseudo = ?"
					   		   + "WHERE idJoueur = ?;";
			
			PreparedStatement st  = con.prepareStatement(updateJoueur);
			st.setString(1, value.getPseudo());
			st.setInt(2, value.getId());

			st.executeUpdate(updateJoueur);
			
			System.out.println("Le joueur " + value.getPseudo().toUpperCase() + " a été modifié.");
			return true;
			
		} catch (SQLException e) {
			return false;
		}
	}

	@Override
	public boolean delete(Joueur value) throws Exception {
		try {
			String updateJoueur = "DELETE FROM Joueur WHERE idJoueur = ?;";
			
			PreparedStatement st  = con.prepareStatement(updateJoueur);
			st.setInt(1, value.getId());
			
			st.executeUpdate();
			
			System.out.println("Le joueur " + value.getPseudo().toUpperCase() + " a été supprimé.");
			return true;
			
		} catch (SQLException e) {
			return false;
		}
	}

}
