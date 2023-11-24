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
import modele.Partie;
import modele.Tournoi;

public class PartieJDBC implements PartieDAO{

	private Connection cn;
	private static PartieJDBC partieDB;
	
	private PartieJDBC (Connection c) {
		this.cn = c;
	}
	
	public static synchronized PartieJDBC getInstance() {
		if(partieDB == null) {
			partieDB = new PartieJDBC(ConnectionJDBC.getConnection());
		}
		return partieDB;
	}
	
	@Override
	public List<Partie> getAll() throws Exception {
		List<Partie> parties = new ArrayList<>();
		try {
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery("select * from Partie");
			while(rs.next()) {
				TournoiJDBC tournoiBDD = TournoiJDBC.getInstance();
				Tournoi tournoi = tournoiBDD.getById(rs.getInt("idTournoi")).get();
				EquipeJDBC equipeBDD = EquipeJDBC.getInstance();
				Equipe equipe = equipeBDD.getById(rs.getInt("equipe")).get();
				
				parties.add(new Partie(rs.getDate("dateDebut"), rs.getTime("heureDebut"), rs.getString("deroulement"), equipe, tournoi));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return parties;
	}

	@Override
	public Optional<Partie> getById(Integer id) throws Exception {
		Optional<Partie> partie = Optional.empty();
		try {

			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery("select * from Partie where idPartie = "+id);
			if (rs.next()) {
				TournoiJDBC tournoiBDD = TournoiJDBC.getInstance();
				Tournoi tournoi = tournoiBDD.getById(rs.getInt("idTournoi")).get();
				EquipeJDBC equipeBDD = EquipeJDBC.getInstance();
				Equipe equipe = equipeBDD.getById(rs.getInt("equipe")).get();
				partie = Optional.ofNullable(new Partie(rs.getDate("dateDebut"), rs.getTime("heureDebut"), rs.getString("deroulement"), equipe, tournoi));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return partie;
	}

	@Override
	public boolean add(Partie p) throws Exception {
		boolean res = false;
		try {
			CallableStatement cs = cn.prepareCall("insert into Partie (date, heure, deroulement, idEquipe, idTournoi) values (?,?,?,?,?)");
			cs.setDate(1, p.getDate());
			cs.setTime(2, p.getHeure());
			cs.setString(3, p.getDeroulement());
			cs.setInt(4, p.getTournoi().getIdTournoi());
			cs.setInt(5, p.getEquipeGagnant().getIdEquipe());
			cs.executeUpdate();
			res = true;
			
			System.out.println("Partie ajoute");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public boolean update(Partie p) throws Exception {
		boolean res = false;
		try {

			CallableStatement cs = cn.prepareCall("update Partie (date, heure, deroulement, idEquipe, idTournoi) values (?,?,?,?,?)");
			cs.setDate(1, p.getDate());
			cs.setTime(2, p.getHeure());
			cs.setString(3, p.getDeroulement());
			cs.setInt(4, p.getTournoi().getIdTournoi());
			cs.setInt(5, p.getEquipeGagnant().getIdEquipe());
			cs.executeUpdate();
			res = true;
			
			System.out.println("Partie mis a jour");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public boolean delete(Partie p) throws Exception {
		boolean res = false;
		try {
			CallableStatement cs = cn.prepareCall("delete from Partie where datePartie = ?, heureDebut = ?)");
			cs.setDate(1, p.getDate());
			cs.setTime(2, p.getHeure());
			cs.executeUpdate();
			res = true;
			
			System.out.println("Partie supprime.");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	
	
}
