package dao;

import java.sql.Connection;
import java.sql.DriverManager;
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
	    this.con = connect;
	}

	@Override
	public List<Joueur> getAll() throws Exception {
		Statement st = this.con.createStatement();
        ResultSet rs = st.executeQuery("select * from joueur");
        List<Joueur> listeJoueurs = new ArrayList<Joueur>();
        
        while (rs.next()) {
        	listeJoueurs.add(new Joueur(rs.getInt("idJoueur"),
            		                 rs.getString("pseudo")));  	        
        }
        return listeJoueurs;
	}

	@Override
	public Optional<Joueur> getById(Integer... id) throws Exception {
		try {
			Statement st = this.con.createStatement();
	        ResultSet rs = st.executeQuery("select * from joueur where idJoueur = "+id[0]);
	        Optional<Joueur> retour = Optional.empty();
	        if (rs.next()) {        	
	        	retour = Optional.ofNullable(new Joueur(rs.getInt("idJoueur"), rs.getString("pseudo")));
        }
        return retour;
        
		} catch (SQLException e) {
			System.out.println("Erreur dans la séléction");
			e.printStackTrace();
			return Optional.empty();
		}
	}
	
	@Override
	public Optional<Joueur> getByPseudo(String... pseudo) throws Exception {
		try {
			Statement st = this.con.createStatement();
	        ResultSet rs = st.executeQuery("select * from joueur where pseudo = "+pseudo[0]);
	        Optional<Joueur> retour = Optional.empty();
	        if (rs.next()) {        	
	        	retour = Optional.ofNullable(new Joueur(rs.getInt("idJoueur"), rs.getString("pseudo")));
        }
        return retour;
        
		} catch (SQLException e) {
			System.out.println("Erreur dans la séléction");
			e.printStackTrace();
			return Optional.empty();
		}
	}

	@Override
	public boolean add(Joueur value) throws Exception {
		try {
			Statement st = this.con.createStatement();
	        String req = "INSERT INTO joueur (pseudo) VALUES ('"+ value.getPseudo() + "')";
	        st.executeUpdate(req);
	        return true;
		}catch (SQLException e) {
			System.out.println("Erreur à l'insertion d'un sujet");
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean update(Joueur value) throws Exception {
		try {
			Statement st = this.con.createStatement();
	        String req = "UPDATE joueur SET contenuSujet = '"+value.getContenu()+"' , utilisateur = '"+value.getUtilisateur()+"' WHERE idSujet = " + value.getIdSujet();
	        st.executeUpdate(req);
	        return true;
		}catch (SQLException e) {
			System.out.println("Erreur à la mise à jour d'un sujet");
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delete(Joueur value) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

}
