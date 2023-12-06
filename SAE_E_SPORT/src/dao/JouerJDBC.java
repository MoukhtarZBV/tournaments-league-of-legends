package dao;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import modele.Equipe;
import modele.Jouer;
import modele.Partie;

public class JouerJDBC implements JouerDAO{

	@Override
	public List<Jouer> getAll() throws Exception {
		List<Jouer> jouers = new ArrayList<>();
		try {
			ResultSet rs = ConnectionJDBC.getConnection().createStatement().executeQuery("select * from Jouer");
			while (rs.next()) {
				Equipe e = (new EquipeJDBC()).getById(rs.getInt("idEquipe")).orElse(null);
				Partie p = (new PartieJDBC()).getByDateHeure(rs.getDate("datePartie"), rs.getString("heureDebut")).orElse(null);
				jouers.add(new Jouer(e, p));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return jouers;
	}

	@Override
	public Optional<Jouer> getById(Integer id) throws Exception {
		// TODO Auto-generated method stub
		return Optional.empty();
	}
	
	@Override
	public Optional<Jouer> getByDateHeure(Date date, String heure) throws Exception {
		Optional<Jouer> opt = Optional.empty();
		try {
			CallableStatement cs = ConnectionJDBC.getConnection().prepareCall("select * from Jouer where datePartie = ? and heureDebut = ?");
			cs.setDate(1, date);
			cs.setString(2, heure);
			ResultSet rs = cs.executeQuery();
			if (rs.next()) {
				Equipe e = (new EquipeJDBC()).getById(rs.getInt("idEquipe")).orElse(null);
				Partie p = (new PartieJDBC()).getByDateHeure(rs.getDate("datePartie"), rs.getString("heureDebut")).orElse(null);
				Jouer j = new Jouer(e, p);
				opt = Optional.ofNullable(j);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return opt;
	}
	
	@Override 
	public Optional<Jouer> getByEquipe(Equipe eq) throws Exception{
		Optional<Jouer> opt = Optional.empty();
		try {
			CallableStatement cs = ConnectionJDBC.getConnection().prepareCall("select * from Jouer where idEquipe = ?");
			cs.setInt(1, eq.getIdEquipe());
			ResultSet rs = cs.executeQuery();
			if (rs.next()) {
				Equipe equipe = (new EquipeJDBC()).getById(rs.getInt("idEquipe")).orElse(null);
				Partie partie = (new PartieJDBC()).getByDateHeure(rs.getDate("datePartie"), rs.getString("heureDebut")).orElse(null);
				Jouer j = new Jouer(equipe, partie);
				opt = Optional.ofNullable(j);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return opt;
	}

	@Override
	public boolean add(Jouer j) throws Exception {
		boolean res = false;
		try {
			CallableStatement cs = ConnectionJDBC.getConnection().prepareCall
					("insert into Jouer(idEquipe, datePartie, heureDebut) "
					+ "values (?, ?, ?)");
			cs.setInt(1, j.getEquipe().getIdEquipe());
			cs.setDate(2, j.getPartie().getDate());
			cs.setString(3, j.getPartie().getHeure());
			cs.executeUpdate();
			res = true;
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public boolean update(Jouer jouer) throws Exception {
//		CallableStatement cs = ConnectionJDBC.getConnection().prepareCall("UPDATE jouer set idEquipe = ?, partie = ?");
		
		return false;
	}

	@Override
	public boolean delete(Jouer jouer) throws Exception {
		boolean res = false;
		try {
			CallableStatement cs = ConnectionJDBC.getConnection().prepareCall("delete from Jouer where datePartie = ? and heureDebut = ? and idEquipe = ?");
			cs.setDate(1, jouer.getPartie().getDate());
			cs.setString(2, jouer.getPartie().getHeure());
			cs.setInt(3, jouer.getEquipe().getIdEquipe());
			cs.executeUpdate();
			res = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	
	
}
