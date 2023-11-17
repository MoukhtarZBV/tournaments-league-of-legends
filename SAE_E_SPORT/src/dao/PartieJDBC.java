package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import modele.Equipe;
import modele.Niveau;
import modele.Partie;
import modele.Tournoi;

public class PartieJDBC implements PartieDAO{

	public static Connection cn;
    
	public PartieJDBC(Connection connect) {
	    cn = connect;
	}
	
	@Override
	public List<Partie> getAll() throws Exception {
		List<Partie> parties = new ArrayList<>();
		Statement st = cn.createStatement();
		ResultSet rs = st.executeQuery("select * from Partie");
		while(rs.next()) {
			TournoiJDBC tournoiBDD = new TournoiJDBC(cn);
			Tournoi tournoi = tournoiBDD.getById(rs.getInt("idTournoi")).get();
			EquipeJDBC equipeBDD = new EquipeJDBC(cn);
			Equipe equipe = equipeBDD.getById(rs.getInt("equipe1")).get();
			
			parties.add(new Partie(rs.getDate("dateDebut"), rs.getTime("heureDebut"), rs.getString("deroulement"), rs.getString("equipe1"), rs.getString("equipe2"), tournoi));
		}
		return tournois;
	}

	@Override
	public Optional<Partie> getById(Integer id) throws Exception {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public boolean add(Partie value) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Partie value) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Partie value) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	
	
}
