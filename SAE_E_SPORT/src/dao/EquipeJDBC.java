package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import modele.Equipe;
import modele.Pays;

public class EquipeJDBC implements EquipeDAO{

	public static Connection cn;
    
	public EquipeJDBC() {
	    cn = ConnectionJDBC.getConnection();
	}
	
	@Override
	public List<Equipe> getAll() throws Exception {
		List<Equipe> equipes = new ArrayList<>();
		try {
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery("select * from Equipe");
			while(rs.next()) {
				equipes.add(new Equipe(rs.getInt("idEquipe"), rs.getString("nomEquipe"), rs.getInt("rang"), Pays.valueOf(rs.getString("nationnalite"))));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return equipes;
	}

	@Override
	public Optional<Equipe> getById(Integer id) throws Exception {
		Optional<Equipe> equipes = Optional.empty();
		try {
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery("select * from Equipe where idEquipe = "+id);
			if(rs.next()) {
				equipes = Optional.ofNullable(new Equipe(rs.getInt("idEquipe"), rs.getString("nomEquipe"), rs.getInt("rang"), Pays.valueOf(rs.getString("nationnalite"))));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return equipes;
	}

	@Override
	public boolean add(Equipe e) throws Exception {
		boolean res = false;
		try {
			CallableStatement cs = cn.prepareCall("insert into Equipe (idEquipe, nomEquipe, rang, nationnalite) values (NEXT VALUE FOR idEquipe,?,?,?)");
			cs.setString(1, e.getNom());
			cs.setInt(2, e.getRang());
			cs.setString(3, e.getNationnalité().denomination());
			cs.executeUpdate();
			res = true;
		}catch (SQLException exp) {
			exp.printStackTrace();
		}
		return res;
	}

	@Override
	public boolean update(Equipe e) throws Exception {
		boolean res = false;
		try {
			CallableStatement cs = cn.prepareCall("update Equipe set nomEquipe = ?, rang = ?, pays = ? where idEquipe = ?");
			cs.setString(1, e.getNom());
			cs.setInt(2, e.getRang());
			cs.setString(3, e.getNationnalité().denomination());
			cs.setInt(4, e.getIdEquipe());
			cs.executeUpdate();
			res = true;
		}catch (SQLException exp) {
			exp.printStackTrace();
		}
		return res;
	}

	@Override
	public boolean delete(Equipe value) throws Exception {
		boolean res = false;
		try {
			Statement st = cn.createStatement();
			st.executeUpdate("delete from Equipe where idEquipe = "+value.getIdEquipe());
			res = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public Optional<Equipe> getByNom(String nom) throws Exception {
		Optional<Equipe> equipe = Optional.empty();
		try {
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery("select * from Equipe where nomEquipe = "+nom);	
			if (rs.next()) {
				equipe = Optional.ofNullable(new Equipe(rs.getInt("idEquipe"), rs.getString("nomEquipe"), rs.getInt("rang"), Pays.valueOf(rs.getString("nationnalite"))));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return equipe;
	}

}
