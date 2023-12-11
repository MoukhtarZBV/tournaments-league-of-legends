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
	public boolean update(Participer p) throws Exception {
		boolean res = false;
		try {
			CallableStatement cs = ConnectionJDBC.getConnection().
					prepareCall("update Participer set nbPointsGagnes = ?, nbMatchsJoues = ?, nbMatchsGagnes = ? where idTournoi = ? and idEquipe = ?");
			cs.setInt(1, p.getNbPointsGagnes());
			cs.setInt(2, p.getNbMatchsJoues());
			cs.setInt(3, p.getNbMatchsGagnes());
			cs.setInt(4, p.getTournoi().getIdTournoi());
			cs.setInt(5, p.getEquipe().getIdEquipe());
			cs.executeUpdate();
			res = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public boolean delete(Participer p) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	public List<Equipe> listeEquipes(Tournoi tournoi){
		List<Equipe> listeEquipes = new ArrayList<>();
		try {
            PreparedStatement st = ConnectionJDBC.getConnection().prepareStatement("select equipe.* from Equipe, Tournoi, Participer where participer.idTournoi = ? and equipe.idEquipe = participer.idEquipe");
            st.setInt(1, tournoi.getIdTournoi());
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Equipe equipe = new Equipe(rs.getInt("idEquipe"), rs.getString("nomEquipe"), rs.getInt("rang"), Pays.getPays(rs.getString("nationalite")));
                for (Joueur j : new EquipeJDBC().listeJoueurs(equipe)) {
                	equipe.ajouterJoueur(j);
                }
                listeEquipes.add(equipe);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listeEquipes;
	}
	
}
