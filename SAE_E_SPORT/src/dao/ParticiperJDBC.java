package dao;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import modele.Equipe;
import modele.Joueur;
import modele.Participer;
import modele.Pays;
import modele.Tournoi;

public class ParticiperJDBC implements ParticiperDAO{

	@Override
	public List<Participer> getAll() throws Exception {
		List<Participer> participations = new ArrayList<>();
		ResultSet rs = ConnectionJDBC.getConnection().createStatement().executeQuery("select * from participer");
		while (rs.next()) {
			Equipe e = (new EquipeJDBC()).getById(rs.getInt("idEquipe")).orElse(null);
			Tournoi t = (new TournoiJDBC()).getById(rs.getInt("idTournoi")).orElse(null);
			Participer participation = new Participer(e, t);
			participation.setNbMatchsGagnes(rs.getInt("nbMatchsGagnes"));
			participation.setNbMatchsJoues(rs.getInt("nbMatchsJoues"));
			participation.setNbPointsGagnes(rs.getInt("nbPointsGagnes"));
			participations.add(participation);
		}
		return participations;
	}

	@Override
	public Optional<Participer> getById(Integer id) throws Exception {
		return Optional.empty();
	}

	@Override
	public boolean add(Participer p) throws Exception {
		boolean res = false;
		try {
			CallableStatement cs = ConnectionJDBC.getConnection().
					prepareCall("insert into Participer(idTournoi, idEquipe, nbPointsGagnes, nbMatchsJoues, nbMatchsGagnes)"
							  + "values (?, ?, ?, ?, ?)");
			cs.setInt(1, p.getTournoi().getIdTournoi());
			cs.setInt(2, p.getEquipe().getIdEquipe());
			cs.setInt(3, p.getNbPointsGagnes());
			cs.setInt(4, p.getNbMatchsJoues());
			cs.setInt(5, p.getNbMatchsGagnes());
			cs.executeUpdate();
			res = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public boolean update(Participer value) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Participer value) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}	
}
