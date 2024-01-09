package dao;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import modele.Equipe;
import modele.Participer;
import modele.Tournoi;

public class ParticiperJDBC implements ParticiperDAO{

	@Override
	public List<Participer> getAll() {
		List<Participer> participations = new LinkedList<>();
		ResultSet rs;
		try {
			rs = ConnectionJDBC.getConnection().createStatement().executeQuery("select * from participer");
			while (rs.next()) {
				Equipe e = (new EquipeJDBC()).getById(rs.getInt("idEquipe")).orElse(null);
				Tournoi t = (new TournoiJDBC()).getById(rs.getString("nomTournoi")).orElse(null);
				Participer participation = new Participer(e, t);
				participation.setNbMatchsGagnes(rs.getInt("nbMatchsGagnes"));
				participation.setNbMatchsJoues(rs.getInt("nbMatchsJoues"));
				participation.setNbPointsPouleGagnes(rs.getInt("nbPointsPouleGagnes"));
				participation.setNbPointsTournoiGagnes(rs.getInt("nbPointsTournoiGagnes"));
				participation.setClassement(rs.getInt("classement"));
				participations.add(participation);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return participations;
	}
	
	@Override
	public boolean add(Participer p) {
		boolean res = false;
		try {
			CallableStatement cs = ConnectionJDBC.getConnection().
					prepareCall("insert into Participer(nomTournoi, idEquipe, nbPointsPouleGagnes, nbPointsTournoiGagnes, nbMatchsJoues, nbMatchsGagnes, classement)"
							  + "values (?, ?, ?, ?, ?, ?, ?)");
			cs.setString(1, p.getTournoi().getNomTournoi());
			cs.setInt(2, p.getEquipe().getIdEquipe());
			cs.setInt(3, p.getNbPointsPouleGagnes());
			cs.setInt(4, p.getNbPointsTournoiGagnes());
			cs.setInt(5, p.getNbMatchsJoues());
			cs.setInt(6, p.getNbMatchsGagnes());
			cs.setInt(7, p.getClassement());
			cs.executeUpdate();
			res = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public boolean update(Participer p) {
		boolean res = false;
		try {
			CallableStatement cs = ConnectionJDBC.getConnection().
					prepareCall("update Participer set nbPointsPouleGagnes = ?, nbPointsTournoiGagnes = ?, nbMatchsJoues = ?, nbMatchsGagnes = ?, classement = ? where nomTournoi = ? and idEquipe = ?");
			cs.setInt(1, p.getNbPointsPouleGagnes());
			cs.setInt(2, p.getNbPointsTournoiGagnes());
			cs.setInt(3, p.getNbMatchsJoues());
			cs.setInt(4, p.getNbMatchsGagnes());
			cs.setInt(5, p.getClassement());
			cs.setString(6, p.getTournoi().getNomTournoi());
			cs.setInt(7, p.getEquipe().getIdEquipe());
			cs.executeUpdate();
			res = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	@Override
	public Optional<Participer> getByTournoiEquipe(Tournoi tournoi, Equipe equipe) {
		Optional<Participer> optParticipation = Optional.empty();
 		try {
			CallableStatement cs = ConnectionJDBC.getConnection().
					prepareCall("select * from Participer where nomTournoi = ? and idEquipe = ?");
			cs.setString(1, tournoi.getNomTournoi());
			cs.setInt(2, equipe.getIdEquipe());
			ResultSet rs = cs.executeQuery();
			if (rs.next()) {
				Equipe e = (new EquipeJDBC()).getById(rs.getInt("idEquipe")).orElse(null);
				Tournoi t = (new TournoiJDBC()).getById(rs.getString("nomTournoi")).orElse(null);
				Participer participation = new Participer(e, t);
				participation.setNbMatchsGagnes(rs.getInt("nbMatchsGagnes"));
				participation.setNbMatchsJoues(rs.getInt("nbMatchsJoues"));
				participation.setNbPointsPouleGagnes(rs.getInt("nbPointsPouleGagnes"));
				participation.setNbPointsTournoiGagnes(rs.getInt("nbPointsTournoiGagnes"));
				participation.setClassement(rs.getInt("classement"));
				optParticipation = Optional.of(participation);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return optParticipation;
	}	

	@Override
	public boolean delete(Participer p) {
		// Inutile dans notre cas
		return false;
	}

	@Override
	public Optional<Participer> getById(Tournoi id) {
		// Pas d'id unique
		return Optional.empty();
	}	
}
