package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import entites.Equipe;

public class EquipeJDBC implements EquipeDAO{

	public static Connection cn;
    
	public EquipeJDBC(Connection connect) {
	    cn = connect;
	}
	
	@Override
	public List<Equipe> getAll() throws Exception {
		List<Equipe> equipes = new ArrayList<>();
		Statement st = cn.createStatement();
		ResultSet rs = st.executeQuery("select * from Equipe");
		while(rs.next()) {
			equipes.add(new Equipe(rs.getInt("idEquipe"), rs.getString("nomEquipe"), rs.getInt("rang"), rs.getString("pays")));
		}
		return equipes;
	}

	@Override
	public Optional<Equipe> getById(Integer... id) throws Exception {
		Optional<Equipe> equipes = Optional.empty();
		Statement st = cn.createStatement();
		ResultSet rs = st.executeQuery("select * from Equipe where idEquipe = "+id[0]);
		if(rs.next()) {
			equipes = Optional.ofNullable(new Equipe(rs.getInt("idEquipe"), rs.getString("nomEquipe"), rs.getInt("rang"), rs.getString("pays")));
		}
		return equipes;
	}

	@Override
	public boolean add(Equipe e) throws Exception {
		CallableStatement cs = cn.prepareCall("insert into Equipe ('idEquipe','nomEquipe','rang','pays') values (NEXT VALUE FOR idEquipe,?,?,?)");
		cs.setInt(1, e.getIdEquipe());
		cs.setString(2, e.getNom());
		cs.setInt(3, e.getRang());
		cs.setString(4, e.getPays());
		return cs.executeUpdate()>0;
	}

	@Override
	public boolean update(Equipe e) throws Exception {
		CallableStatement cs = cn.prepareCall("update Equipe set nomEquipe = ?, rang = ?, pays = ? where idEquipe = "+e.getIdEquipe());
		cs.setString(1, e.getNom());
		cs.setInt(2, e.getRang());
		cs.setString(3, e.getPays());
		return cs.executeUpdate()>0;
	}

	@Override
	public boolean delete(Equipe value) throws Exception {
		Statement st = cn.createStatement();
		return st.executeUpdate("delete Equipe where idEquipe = "+value.getIdEquipe()) > 0;
	}

	@Override
	public Optional<Equipe> getByNom(String nom) throws Exception {
		Optional<Equipe> equipe = Optional.empty();
		Statement st = cn.createStatement();
		ResultSet rs = st.executeQuery("select * from Equipe where nomEquipe = "+nom);	
		if (rs.next()) {
			equipe = Optional.of(new Equipe(rs.getInt("idEquipe"), rs.getString("nomEquipe"), rs.getInt("rang"), rs.getString("pays")));
		}
		return equipe;
	}
	
}
