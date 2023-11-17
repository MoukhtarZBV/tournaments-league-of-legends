package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import modele.Niveau;
import modele.Pays;
import modele.Tournoi;

public class TournoiJDBC implements TournoiDAO{

	public static Connection cn;
    
	public TournoiJDBC(Connection connect) {
	    cn = connect;
	}
	
	@Override
	public List<Tournoi> getAll() throws Exception {
		List<Tournoi> tournois = new ArrayList<>();
		Statement st = cn.createStatement();
		ResultSet rs = st.executeQuery("select * from Tournoi");
		while(rs.next()) {
			tournois.add(new Tournoi(rs.getInt("idTournoi"), rs.getString("nomTournoi"), Niveau.valueOf(rs.getString("niveau")), rs.getDate("dateDebut"), rs.getDate("dateFin"), Pays.valueOf(rs.getString("pays"))));
		}
		return tournois;
	}

	@Override
	public Optional<Tournoi> getById(Integer id) throws Exception {
		List<Tournoi> tournois = new ArrayList<>();
		Statement st = cn.createStatement();
		ResultSet rs = st.executeQuery("select * from Tournoi where idTournoi = "+ id);
		if(rs.next()) {
			tournois.add(new Tournoi(rs.getInt("idTournoi"), rs.getString("nomTournoi"), Niveau.valueOf(rs.getString("niveau")), rs.getDate("dateDebut"), rs.getDate("dateFin"), Pays.valueOf(rs.getString("pays"))));
		}
		return Optional.empty();
	}

	@Override
	public boolean add(Tournoi t) throws Exception {
		CallableStatement cs = cn.prepareCall("insert into Tournoi ('idTournoi','nomTournoi','niveau','dateDebut','dateFin','vainqueur') values (NEXT VALUE FOR idEquipe,?,?,?,?,?)");
		cs.setString(1, t.getNomTournoi());
		cs.setString(2, t.getNiveau().toString());
		cs.setDate(3, t.getDateDebut());
		cs.setDate(4, t.getDateFin());
		cs.setInt(5, t.getVainqueur().getIdEquipe());
		return cs.executeUpdate()>0;
	}

	@Override
	public boolean update(Tournoi t) throws Exception {
		CallableStatement cs = cn.prepareCall("update Tournoi set nomTournoi = ?, niveau = ?, dateDebut = ?, dateFin = ?, vainqueur =? where idEquipe = ?");
		cs.setString(1, t.getNomTournoi());
		cs.setString(2, t.getNiveau().toString());
		cs.setDate(3, t.getDateDebut());
		cs.setDate(4, t.getDateFin());
		cs.setInt(5, t.getVainqueur().getIdEquipe());
		cs.setInt(6, t.getIdTournoi());
		return cs.executeUpdate()>0;
	}

	@Override
	public boolean delete(Tournoi t) throws Exception {
		Statement cs = cn.createStatement();
		return cs.executeUpdate("delete from Tournoi where idTournoi = "+t.getIdTournoi()) > 0;
	}

}
