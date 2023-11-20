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

	private static Connection cn;
	
	@Override
	public List<Equipe> getAll() throws Exception {
	    cn = ConnectionJDBC.getConnection();
		List<Equipe> equipes = new ArrayList<>();
		try {
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery("select * from Equipe");
			while(rs.next()) {
				equipes.add(new Equipe(rs.getInt("idEquipe"), rs.getString("nomEquipe"), rs.getInt("rang"), Pays.valueOf(rs.getString("nationalite").toUpperCase())));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		cn.close();
		return equipes;
	}

	@Override
	public Optional<Equipe> getById(Integer id) throws Exception {
		Optional<Equipe> equipes = Optional.empty();
		try {
			cn = ConnectionJDBC.getConnection();
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery("select * from Equipe where idEquipe = "+id);
			if(rs.next()) {
				equipes = Optional.ofNullable(new Equipe(rs.getInt("idEquipe"), rs.getString("nomEquipe"), rs.getInt("rang"), Pays.valueOf(rs.getString("nationalite"))));
			}
			cn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return equipes;
	}

	@Override
	public boolean add(Equipe e) throws Exception {
		boolean res = false;
		try {
			CallableStatement cs = cn.prepareCall("insert into Equipe (idEquipe, nomEquipe, rang, nationalite) values (?,?,?,?)");
			cs.setInt(1, e.getIdEquipe());
			cs.setString(2, e.getNom());
			cs.setInt(3, e.getRang());
			cs.setString(4, e.getNationalite().denomination());
			cs.executeUpdate();
			res = true;
			cn.close();
		}catch (SQLException exp) {
			exp.printStackTrace();
		}
		return res;
	}

	@Override
	public boolean update(Equipe e) throws Exception {
		boolean res = false;
		try {
			cn = ConnectionJDBC.getConnection();
			CallableStatement cs = cn.prepareCall("update Equipe set nomEquipe = ?, rang = ?, pays = ? where idEquipe = ?");
			cs.setString(1, e.getNom());
			cs.setInt(2, e.getRang());
			cs.setString(3, e.getNationalite().denomination());
			cs.setInt(4, e.getIdEquipe());
			cs.executeUpdate();
			res = true;
			cn.close();
		}catch (SQLException exp) {
			exp.printStackTrace();
		}
		return res;
	}

	@Override
	public boolean delete(Equipe value) throws Exception {
		boolean res = false;
		try {
			cn = ConnectionJDBC.getConnection();
			Statement st = cn.createStatement();
			st.executeUpdate("delete from Equipe where idEquipe = "+value.getIdEquipe());
			res = true;
			cn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public Optional<Equipe> getByNom(String nom) throws Exception {
		Optional<Equipe> equipe = Optional.empty();
		try {
			cn = ConnectionJDBC.getConnection();
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery("select * from Equipe where nomEquipe = '"+nom+"'");	
			if (rs.next()) {
				equipe = Optional.ofNullable(new Equipe(rs.getInt("idEquipe"), rs.getString("nomEquipe"), rs.getInt("rang"), Pays.valueOf(rs.getString("nationalite"))));
			}
			cn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return equipe;
	}

}
