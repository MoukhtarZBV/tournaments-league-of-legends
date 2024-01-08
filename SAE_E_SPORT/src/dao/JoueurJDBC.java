package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import modele.Equipe;
import modele.Joueur;

public class JoueurJDBC implements JoueurDAO{
	
	@Override
	public List<Joueur> getAll() {
        List<Joueur> listeJoueurs = new ArrayList<Joueur>();
        try {
			Statement st = ConnectionJDBC.getConnection().createStatement();
	        ResultSet rs = st.executeQuery("select * from joueur");
        
	        while (rs.next()) {
	        	EquipeJDBC e = new EquipeJDBC();
				Equipe equipe = e.getById(rs.getInt("idEquipe")).get();
				
	        	listeJoueurs.add(new Joueur(rs.getInt("idJoueur"),
	            		                 rs.getString("pseudo"), 
	            		                 equipe));  	        
	        }
        } catch (SQLException e) {
        	e.printStackTrace();
        }
        return listeJoueurs;
	}

	@Override
	public Optional<Joueur> getById(Integer id) {
		Optional<Joueur> opt = Optional.empty();
		try {
			String req = "SELECT * FROM Joueur WHERE idJoueur = ?";
			
			PreparedStatement st = ConnectionJDBC.getConnection().prepareStatement(req);
			st.setInt(1, id);
			
			ResultSet rs = st.executeQuery();
			
			if (rs.next()) {
				EquipeJDBC e = new EquipeJDBC();
				Equipe equipe = e.getById(rs.getInt("idEquipe")).get();
				
				opt = Optional.ofNullable(new Joueur(rs.getInt("idJoueur"), rs.getString("pseudo"), equipe));
			};
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return opt;
	}
	
	@Override
	public Optional<Joueur> getByPseudo(String j) {
		Optional<Joueur> opt = Optional.empty();
		try {
			String req = "SELECT * FROM Joueur WHERE pseudo = ?";
			
			PreparedStatement st = ConnectionJDBC.getConnection().prepareStatement(req);
			st.setString(1, j);
			
			ResultSet rs = st.executeQuery();
			
			if (rs.next()) {
				EquipeJDBC e = new EquipeJDBC();
				Equipe equipe = e.getById(rs.getInt("idEquipe")).orElse(null);
				
				opt = Optional.ofNullable(new Joueur(rs.getInt("idJoueur"), rs.getString("pseudo"), equipe));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return opt;
	}
	
	@Override
	public List<Joueur> getByEquipe (Equipe equipe) {
		List<Joueur> joueurs = new ArrayList<>();
		try {			
			String req = "select * from Joueur where idEquipe = ?";
			
			PreparedStatement st  = ConnectionJDBC.getConnection().prepareStatement(req);
			st.setInt(1, equipe.getIdEquipe());
			
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				joueurs.add(new Joueur(rs.getInt("idJoueur"), rs.getString("pseudo"), equipe));
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return joueurs;
	}
	
    public static int getNextValueSequence() {
        int res = -1;
		try { 
			Statement st = ConnectionJDBC.getConnection().createStatement();
			ResultSet rs = st.executeQuery("VALUES NEXT VALUE FOR SEQ_Joueur");
	        if (rs.next()) {
	            res = rs.getInt(1);
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return res;
    }

	@Override
	public boolean add(Joueur j) {
		boolean res = false;
		try {
			String addJoueur = "INSERT INTO Joueur VALUES (?, ?, ?)";
			
			PreparedStatement st  = ConnectionJDBC.getConnection().prepareStatement(addJoueur);
			
			EquipeJDBC ejdbc = new EquipeJDBC();
			Equipe e = ejdbc.getByNom(j.getEquipe().getNom()).orElse(null);
			
			st.setInt(1, j.getId());
			st.setString(2, j.getPseudo());
			st.setInt(3, e.getIdEquipe());
			
			st.executeUpdate();
			
			System.out.println("Le joueur "+ j.getPseudo().toUpperCase() +" a été ajouté.");
			res = true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public boolean update(Joueur j) {
		boolean res = false;
		try {
			String updateJoueur = "UPDATE Joueur "
					   		   + "SET pseudo = ?"
					   		   + "WHERE idJoueur = ?";
			
			PreparedStatement st  = ConnectionJDBC.getConnection().prepareStatement(updateJoueur);
			st.setString(1, j.getPseudo());
			st.setInt(2, j.getId());

			st.executeUpdate();
			
			System.out.println("Le joueur " + j.getPseudo().toUpperCase() + " a été modifié.");
			res = true;
		} catch (SQLException e) {
			e.printStackTrace();;
		}
		return res;
	}

	@Override
	public boolean delete(Joueur j) {
		boolean res = false;
		try {
			String updateJoueur = "DELETE FROM Joueur WHERE idJoueur = ?";
			
			PreparedStatement st  = ConnectionJDBC.getConnection().prepareStatement(updateJoueur);
			st.setInt(1, j.getId());
			
			st.executeUpdate();
			
			System.out.println("Le joueur " + j.getPseudo().toUpperCase() + " a été supprimé.");
			res = true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

}
