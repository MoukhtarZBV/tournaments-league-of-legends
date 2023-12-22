package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import modele.Arbitre;

public class ArbitreJDBC implements ArbitreDAO{
	
	@Override
	public List<Arbitre> getAll() throws Exception {
        List<Arbitre> listeArbitres = new ArrayList<>();
		try {
			Statement st = ConnectionJDBC.getConnection().createStatement();
	        ResultSet rs = st.executeQuery("select * from Arbitre");
	        
	        while (rs.next()) {
	        	Arbitre arbitre = new Arbitre(rs.getInt("idArbitre"),
		                 rs.getString("nomArbitre"),
		                 rs.getString("prenomArbitre"));
	        	arbitre.setIdCompte(rs.getInt("idCompte"));
	        	listeArbitres.add(arbitre);
	        }
	        
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return listeArbitres;
	}

	@Override
	public Optional<Arbitre> getById(Integer id) {
		Optional<Arbitre> opt = Optional.empty();
		try {
			String req = "SELECT * FROM Arbitre WHERE idArbitre = ?";
			
			PreparedStatement st = ConnectionJDBC.getConnection().prepareStatement(req);
			st.setInt(1, id);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				Arbitre arbitre = new Arbitre(rs.getInt("idArbitre"), rs.getString("nomArbitre"),rs.getString("prenomArbitre"));
				arbitre.setIdCompte(rs.getInt("idCompte"));
				opt = Optional.ofNullable(arbitre);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return opt;
	}
	
	@Override
	public Optional<Arbitre> getByNomPrenom(String nom, String prenom) throws Exception {
		Optional<Arbitre> opt = Optional.empty();
		try {
			String req = "SELECT * FROM Arbitre WHERE nomArbitre = ? AND prenomArbitre = ?";
			
			PreparedStatement st = ConnectionJDBC.getConnection().prepareStatement(req);
			st.setString(1, nom);
			st.setString(2, prenom);
			
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				Arbitre arbitre = new Arbitre(rs.getInt("idArbitre"), rs.getString("nomArbitre"),rs.getString("prenomArbitre"));
				arbitre.setIdCompte(rs.getInt("idCompte"));
				opt = Optional.ofNullable(arbitre);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return opt;
	}
	
	public static int getNextValueSequence() throws Exception {
        int res = -1;
        Statement st = ConnectionJDBC.getConnection().createStatement();
        ResultSet rs = st.executeQuery("VALUES NEXT VALUE FOR SEQ_Arbitre");
        if (rs.next()) {
            res = rs.getInt(1);
        }
        return res;
    }

	@Override
	public boolean add(Arbitre a) throws Exception {
		boolean res = false;
		try {
			String addArbitre = "INSERT INTO Arbitre(idArbitre, nomArbitre, prenomArbitre) values (?, ?, ?)";
			
			PreparedStatement st  = ConnectionJDBC.getConnection().prepareStatement(addArbitre);
			
			st.setInt(1, a.getId());
			st.setString(2, a.getNom());
			st.setString(3, a.getPrenom());
			
			st.executeUpdate();
			
			System.out.println("L'arbitre "+ a.getNom().toUpperCase() + " " + a.getPrenom()  +" a été ajouté.");
			res = true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public boolean update(Arbitre a) throws Exception {
		boolean res = false;
		try {
			String updateArbitre = "UPDATE Arbitre "
					   		   + "SET nomArbitre = ?, prenomArbitre = ?, idCompte = ?"
					   		   + "WHERE idArbitre = ?";
			
			PreparedStatement st  = ConnectionJDBC.getConnection().prepareStatement(updateArbitre);
			st.setString(1, a.getNom());
			st.setString(2, a.getPrenom());
			if (a.getIdCompte() == null) {
				st.setNull(3, java.sql.Types.INTEGER);
			} else {
				st.setInt(3, a.getIdCompte());
			}
			st.setInt(4, a.getId());

			st.executeUpdate();
			
			System.out.println("L'arbitre "+ a.getNom().toUpperCase() + " " + a.getPrenom() + " a été modifié.");
			res = true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public boolean delete(Arbitre a) throws Exception {
		boolean res = false;
		try {
			String updateArbitre = "DELETE FROM Arbitre WHERE idArbitre = ?";
			
			PreparedStatement st  = ConnectionJDBC.getConnection().prepareStatement(updateArbitre);
			st.setInt(1, a.getId());
			
			st.executeUpdate();
			
			System.out.println("L'arbitre "+ a.getNom().toUpperCase() + " " + a.getPrenom() + " a été supprimé.");
			res = true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

}
