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
	public List<Jouer> getAll() {
		List<Jouer> jouers = new ArrayList<>();
		try {
			ResultSet rs = ConnectionJDBC.getConnection().createStatement().executeQuery("select * from Jouer");
			while (rs.next()) {
				Equipe e = (new EquipeJDBC()).getById(rs.getInt("idEquipe")).orElse(null);
				jouers.add(new Jouer(e, rs.getDate("datePartie"), rs.getString("heureDebut")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return jouers;
	}

	@Override
	public Optional<Jouer> getById(Integer id) {
		// Pas d'id unique
		return Optional.empty();
	}

	@Override
	public boolean add(Jouer j) {
		boolean res = false;
		try {
			CallableStatement cs = ConnectionJDBC.getConnection().prepareCall
					("insert into Jouer(idEquipe, datePartie, heureDebut) "
					+ "values (?, ?, ?)");
			cs.setInt(1, j.getEquipe().getIdEquipe());
			cs.setDate(2, j.getDate());
			cs.setString(3, j.getHeure());
			cs.executeUpdate();
			res = true;
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public boolean update(Jouer jouer) {
		// Inutile dans notre cas
		return false;
	}

	@Override
	public boolean delete(Jouer jouer) {
		boolean res = false;
		try {
			CallableStatement cs = ConnectionJDBC.getConnection().prepareCall("delete from Jouer where datePartie = ? and heureDebut = ? and idEquipe = ?");
			cs.setDate(1, jouer.getDate());
			cs.setString(2, jouer.getHeure());
			cs.setInt(3, jouer.getEquipe().getIdEquipe());
			cs.executeUpdate();
			res = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	
	
}
