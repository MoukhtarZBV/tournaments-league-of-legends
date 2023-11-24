package dao;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import modele.TypeCompte;

public class TypeCompteJDBC implements TypeCompteDAO {
	
	@Override
	public List<TypeCompte> getAll() throws Exception {
		List<TypeCompte> tc = new ArrayList<>();
        try {
			Statement st = ConnectionJDBC.getConnection().createStatement();
	        ResultSet rs = st.executeQuery("select * from TypeCompte");
        
	        while (rs.next()) {
	        	tc.add(TypeCompte.getTypeCompte(rs.getString("type")));
	        }
        } catch (SQLException e) {
        	e.printStackTrace();
        }
        return tc;
	}

	@Override
	public Optional<TypeCompte> getById(Integer id) throws Exception {
		// Pays n'a pas d'id
		return Optional.empty();
	}

	@Override
	public boolean add(TypeCompte tc) throws Exception {
		boolean res = false;
		try {
			CallableStatement cs = ConnectionJDBC.getConnection().prepareCall("insert into TypeCompte values (?)");
			cs.setString(1, tc.denomination());
			cs.execute();
			res = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public boolean update(TypeCompte tc) throws Exception {
		return false;
	}

	@Override
	public boolean delete(TypeCompte tc) throws Exception {
		boolean res = false;
		try {
			CallableStatement cs = ConnectionJDBC.getConnection().prepareCall("delete from TypeCompte where type = ?");
			cs.setString(1, tc.denomination());
			cs.execute();
			res = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	
	
}
