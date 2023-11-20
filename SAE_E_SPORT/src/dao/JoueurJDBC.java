package dao;

import java.sql.Connection;
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
	
	private Connection con;

	public JoueurJDBC (Connection c) {
		this.con = c;
	}
	
	@Override
	public List<Joueur> getAll() throws Exception {
        List<Joueur> listeJoueurs = new ArrayList<Joueur>();
        try {
			Statement st = con.createStatement();
	        ResultSet rs = st.executeQuery("select * from joueur");
        
	        while (rs.next()) {
	        	EquipeJDBC e = new EquipeJDBC(this.con);
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
	public Optional<Joueur> getById(Integer id) throws Exception {
		Optional<Joueur> opt = Optional.empty();
		try {
			String req = "SELECT * FROM Joueur WHERE idJoueur = ?";
			
			PreparedStatement st = con.prepareStatement(req);
			st.setInt(1, id);
			
			ResultSet rs = st.executeQuery(req);
			
			if (rs.next()) {
				EquipeJDBC e = new EquipeJDBC(this.con);
				Equipe equipe = e.getById(rs.getInt("idEquipe")).get();
				
				opt = Optional.ofNullable(new Joueur(rs.getInt("idJoueur"), rs.getString("pseudo"), equipe));
			};
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return opt;
	}
	
	@Override
	public Optional<Joueur> getByPseudo(String j) throws Exception {
		Optional<Joueur> opt = Optional.empty();
		try {
			String req = "SELECT * FROM Joueur WHERE pseudo = ?";
			
			PreparedStatement st = con.prepareStatement(req);
			st.setString(1, j);
			
			ResultSet rs = st.executeQuery(req);
			
			if (rs.next()) {
				EquipeJDBC e = new EquipeJDBC(this.con);
				Equipe equipe = e.getById(rs.getInt("idEquipe")).get();
				
				opt = Optional.ofNullable(new Joueur(rs.getInt("idJoueur"), rs.getString("pseudo"), equipe));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return opt;
	}
	
	@Override
	public List<Joueur> getByEquipe (Equipe equipe) throws Exception {
		List<Joueur> joueurs = new ArrayList<>();
		try {			
			String req = "select * from Joueur where idEquipe = ?";
			
			PreparedStatement st  = con.prepareStatement(req);
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

	@Override
	public boolean add(Joueur j) throws Exception {
		boolean res = false;
		try {
			String addJoueur = "INSERT INTO Joueur VALUES (NEXT VALUE FOR SEQ_Joueur, ?, ?)";
			
			PreparedStatement st  = con.prepareStatement(addJoueur);
			
			st.setString(1, j.getPseudo());
			st.setInt(2, j.getEquipe().getIdEquipe());
			
			st.execute();
			
			System.out.println("Le joueur "+ j.getPseudo().toUpperCase() +" a été ajouté.");
			res = true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public boolean update(Joueur j) throws Exception {
		boolean res = false;
		try {
			String updateJoueur = "UPDATE Joueur "
					   		   + "SET pseudo = ?"
					   		   + "WHERE idJoueur = ?";
			
			PreparedStatement st  = con.prepareStatement(updateJoueur);
			st.setString(1, j.getPseudo());
			st.setInt(2, j.getId());

			st.execute();
			
			System.out.println("Le joueur " + j.getPseudo().toUpperCase() + " a été modifié.");
			res = true;
		} catch (SQLException e) {
			e.printStackTrace();;
		}
		return res;
	}

	@Override
	public boolean delete(Joueur j) throws Exception {
		boolean res = false;
		try {
			String updateJoueur = "DELETE FROM Joueur WHERE idJoueur = ?";
			
			PreparedStatement st  = con.prepareStatement(updateJoueur);
			st.setInt(1, j.getId());
			
			st.execute();
			
			System.out.println("Le joueur " + j.getPseudo().toUpperCase() + " a été supprimé.");
			res = true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

}
