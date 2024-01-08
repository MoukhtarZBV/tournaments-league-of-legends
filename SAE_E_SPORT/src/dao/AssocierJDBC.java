package dao;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import modele.Arbitre;
import modele.Associer;
import modele.Equipe;
import modele.Participer;
import modele.Tournoi;

public class AssocierJDBC implements AssocierDAO{

	@Override
	public List<Associer> getAll() {
		List<Associer> associations = new ArrayList<>();
		ResultSet rs;
		try {
			rs = ConnectionJDBC.getConnection().createStatement().executeQuery("select * from associer");
			while (rs.next()) {
				Arbitre a = (new ArbitreJDBC()).getById(rs.getInt("idArbitre")).orElse(null);
				Tournoi t = (new TournoiJDBC()).getById(rs.getString("nomTournoi")).orElse(null);
				Associer associer = new Associer(a, t);
				associations.add(associer);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return associations;
	}

	@Override
	public Optional<Associer> getById(Integer id) {
		// Inutile dans notre cas
		return Optional.empty();
	}

	@Override
	public boolean add(Associer value) {
		boolean res = false;
		try {
			CallableStatement cs = ConnectionJDBC.getConnection().
					prepareCall("insert into Associer(idArbitre, nomTournoi)"
							  + "values (?, ?)");
			cs.setInt(1, value.getArbitre().getId());
			cs.setString(2, value.getTournoi().getNomTournoi());
			cs.executeUpdate();
			res = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public boolean update(Associer value){
		// Inutile dans notre cas
		return false;
	}

	@Override
	public boolean delete(Associer value) {
		// Inutile dans notre cas
		return false;
	}

}
