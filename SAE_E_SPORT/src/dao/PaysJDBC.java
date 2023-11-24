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
import modele.Joueur;
import modele.Pays;

public class PaysJDBC implements PaysDAO {

	private Connection cn;
	private static PaysJDBC paysDB;
	
	private PaysJDBC (Connection cn) {
		this.cn = cn;
	}
	
	public static synchronized PaysJDBC getInstance() {
		if(paysDB == null) {
			paysDB = new PaysJDBC(ConnectionJDBC.getConnection());
		}
		return paysDB;
	}
	
	@Override
	public List<Pays> getAll() throws Exception {
		List<Pays> pays = new ArrayList<>();
        try {
			Statement st = cn.createStatement();
	        ResultSet rs = st.executeQuery("select * from Pays");
        
	        while (rs.next()) {
	        	pays.add(Pays.getPays(rs.getString("nomPays")));
	        }
        } catch (SQLException e) {
        	e.printStackTrace();
        }
        return pays;
	}

	@Override
	public Optional<Pays> getById(Integer id) throws Exception {
		// Pays n'a pas d'id
		return Optional.empty();
	}

	@Override
	public boolean add(Pays p) throws Exception {
		boolean res = false;
		try {
			CallableStatement cs = this.cn.prepareCall("insert into Pays values (?)");
			cs.setString(1, p.getNom());
			cs.execute();
			res = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public boolean update(Pays p) throws Exception {
		return false;
	}

	@Override
	public boolean delete(Pays p) throws Exception {
		boolean res = false;
		try {
			CallableStatement cs = this.cn.prepareCall("delete from Pays where nomPays = ?");
			cs.setString(1, p.getNom());
			cs.execute();
			res = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	
	
}
