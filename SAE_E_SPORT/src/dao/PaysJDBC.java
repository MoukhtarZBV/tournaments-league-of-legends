package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import modele.Pays;

public class PaysJDBC implements PaysDAO {
	
	@Override
	public List<Pays> getAll() {
		List<Pays> pays = new ArrayList<>();
        try {
			Statement st = ConnectionJDBC.getConnection().createStatement();
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
	public Optional<Pays> getById(Integer id) {
		// Inutile dans notre cas
		return Optional.empty();
	}

	@Override
	public boolean add(Pays p) {
		boolean res = false;
		try {
			CallableStatement cs = ConnectionJDBC.getConnection().prepareCall("insert into Pays values (?)");
			cs.setString(1, p.denomination());
			cs.execute();
			res = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public boolean update(Pays p) {
		// Inutile dans notre cas
		return false;
	}

	@Override
	public boolean delete(Pays p) {
		boolean res = false;
		try {
			CallableStatement cs = ConnectionJDBC.getConnection().prepareCall("delete from Pays where nomPays = ?");
			cs.setString(1, p.denomination());
			cs.execute();
			res = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	
	
}
