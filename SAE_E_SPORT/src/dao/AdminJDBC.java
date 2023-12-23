package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import modele.Administrateur;
import modele.Compte;

public class AdminJDBC implements AdminDAO {
	
	@Override
	public List<Administrateur> getAll() throws Exception {
		List<Administrateur> list = new ArrayList<>();
		try {			
			Statement st = ConnectionJDBC.getConnection().createStatement();
			
			String req   = "SELECT * FROM Administrateur";
			ResultSet rs = st.executeQuery(req);
			
			while(rs.next()) {
				CompteJDBC cbdd = new CompteJDBC();
				Compte c = cbdd.getById(rs.getInt("idCompte")).orElse(null);
				list.add(new Administrateur(rs.getInt("idAdministrateur"), rs.getString("nomAdmin"), rs.getString("prenomAdmin"), c));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Optional<Administrateur> getById(Integer id) throws Exception {
		Optional<Administrateur> opt = Optional.empty();
		try {

			String req = "SELECT * FROM Administrateur WHERE idAdministrateur = ?";
			
			PreparedStatement st = ConnectionJDBC.getConnection().prepareStatement(req);
			st.setInt(1, id);
			
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				CompteJDBC cbdd = new CompteJDBC();
				Compte c = cbdd.getById(rs.getInt("idCompte")).orElse(null);
				opt = Optional.ofNullable(new Administrateur(rs.getInt("idAdministrateur"), rs.getString("nomAdmin"), rs.getString("prenomAdmin"), c));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return opt;
	}

	@Override
	public boolean add(Administrateur admin) throws Exception {
		boolean res = false;
		try {
			PreparedStatement st = null;
			if (admin.getCompte()!= null) {
				st  = ConnectionJDBC.getConnection()
						.prepareStatement("INSERT INTO Administrateur(idAdministrateur, nomAdmin, prenomAdmin, idCompte) "
										+ "VALUES (?, ?, ?, ?)");
				st.setInt(4, admin.getCompte().getIdCompte());
			} else {
				st  = ConnectionJDBC.getConnection()
						.prepareStatement("INSERT INTO Administrateur (idAdministrateur, nomAdmin, prenomAdmin, idCompte) "
										+ "VALUES (NEXT VALUE FOR SEQ_Administrateur, ?, ?, null)");
			}
			st.setInt(1, admin.getId());
			st.setString(2, admin.getNom());
			st.setString(3, admin.getPrenom());
			st.executeUpdate();
			
			System.out.println("L'administrateur "+ admin.getNom().toUpperCase() +" a été ajouté.");
			res = true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public boolean update(Administrateur admin) throws Exception {
		boolean res = false;
		try {
			PreparedStatement st = null;
			if (admin.getCompte()!=null) {
				st  = ConnectionJDBC.getConnection().prepareStatement("UPDATE Administrateur "
														   		   + "SET nomAdmin = ?, prenomAdmin = ?, idCompte = ? "
														   		   + "WHERE idAdministrateur = ?");
				st.setInt(3, admin.getCompte().getIdCompte());
				st.setInt(4, admin.getId());
			} else {
				st  = ConnectionJDBC.getConnection().prepareStatement("UPDATE Administrateur "
													   		   + "SET nomAdmin = ?, prenomAdmin = ?, idCompte = null "
													   		   + "WHERE idAdministrateur = ?");
				st.setInt(3, admin.getId());
			}
			st.setString(1, admin.getNom());
			st.setString(2, admin.getPrenom());

			st.executeUpdate();
			
			System.out.println("L'Administrateur " + admin.getNom().toUpperCase() + " a été modifié.");
			res = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public boolean delete(Administrateur admin) throws Exception {
		boolean res = false;
		try {
			String updateAdmin = "DELETE FROM Administrateur WHERE idAdministrateur = ?";
			
			PreparedStatement st  = ConnectionJDBC.getConnection().prepareStatement(updateAdmin);
			st.setInt(1, admin.getId());
			
			st.executeUpdate();
			
			System.out.println("L'Administrateur " + admin.getNom().toUpperCase() + " a été supprimé.");
			res = true;	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public List<Administrateur> getByNom(String nom) throws Exception {
		List<Administrateur> admins = this.getAll().stream()
											.filter(e->e.getNom()==nom)
											.collect(Collectors.toList());
		return admins;	
	}

	@Override
	public List<Administrateur> getByNomPrenom(String nom, String prenom) throws Exception {
		List<Administrateur> admins = this.getAll().stream()
										.filter(e->e.getNom()==nom && e.getPrenom()==prenom)
										.collect(Collectors.toList());
		return admins;
	}

}
