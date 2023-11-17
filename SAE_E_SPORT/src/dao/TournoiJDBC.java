package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import modele.Niveau;
import modele.Pays;
import modele.Tournoi;

public class TournoiJDBC implements TournoiDAO{

	public static Connection cn;
    
	public TournoiJDBC() {
	    cn = ConnectionJDBC.getConnection();
	}
	
	@Override
	public List<Tournoi> getAll() throws Exception {
		List<Tournoi> tournois = new ArrayList<>();
		try {
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery("select * from Tournoi");
			while(rs.next()) {
				tournois.add(new Tournoi(rs.getInt("idTournoi"), rs.getString("nomTournoi"), Niveau.valueOf(rs.getString("niveau")), rs.getDate("dateDebut"), rs.getDate("dateFin"), Pays.valueOf(rs.getString("nationnalite"))));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tournois;
	}

	@Override
	public Optional<Tournoi> getById(Integer id) throws Exception {
		Optional<Tournoi> tournois = Optional.empty();
		try {
		Statement st = cn.createStatement();
		ResultSet rs = st.executeQuery("select * from Tournoi where idTournoi = "+ id);
		if(rs.next()) {
			tournois = Optional.ofNullable(new Tournoi(rs.getInt("idTournoi"), rs.getString("nomTournoi"), Niveau.valueOf(rs.getString("niveau")), rs.getDate("dateDebut"), rs.getDate("dateFin"), Pays.valueOf(rs.getString("nationnalite"))));
		}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tournois;
	}

	@Override
	public boolean add(Tournoi t) throws Exception {
		boolean res = false;
		try {
			CallableStatement cs = cn.prepareCall("insert into Tournoi (idTournoi, nomTournoi, niveau, dateDebut, dateFin, vainqueur) values (NEXT VALUE FOR idEquipe,?,?,?,?,?)");
			cs.setString(1, t.getNomTournoi());
			cs.setString(2, t.getNiveau().denomination());
			cs.setDate(3, t.getDateDebut());
			cs.setDate(4, t.getDateFin());
			cs.setInt(5, t.getVainqueur().getIdEquipe());
			cs.executeUpdate();
			res = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public boolean update(Tournoi t) throws Exception {
		boolean res = false;
		try {
			CallableStatement cs = cn.prepareCall("update Tournoi set nomTournoi = ?, niveau = ?, dateDebut = ?, dateFin = ?, vainqueur =? where idEquipe = ?");
			cs.setString(1, t.getNomTournoi());
			cs.setString(2, t.getNiveau().denomination());
			cs.setDate(3, t.getDateDebut());
			cs.setDate(4, t.getDateFin());
			cs.setInt(5, t.getVainqueur().getIdEquipe());
			cs.setInt(6, t.getIdTournoi());
			cs.executeUpdate();
			res = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public boolean delete(Tournoi t) throws Exception {
		boolean res = false;
		try {
			CallableStatement cs = cn.prepareCall("delete from Tournoi where idTournoi = ?");
			cs.setInt(1, t.getIdTournoi());
			res = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

}
