package dao;

import java.sql.CallableStatement;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import modele.Arbitre;
import modele.Compte;
import modele.Equipe;
import modele.Niveau;
import modele.Partie;
import modele.Pays;
import modele.Statut;
import modele.Tournoi;

public class TournoiJDBC implements TournoiDAO {
	
	@Override
	public List<Tournoi> getAll() {
		List<Tournoi> tournois = new ArrayList<>();
		try {
			Statement st = ConnectionJDBC.getConnection().createStatement();
			ResultSet rs = st.executeQuery("select * from Tournoi order by dateDebut desc");
			while(rs.next()) {
				tournois.add(Tournoi.createTournoi(rs.getString("nomTournoi"), 
										Niveau.getNiveau(rs.getString("niveau")), 
										rs.getDate("dateDebut"), 
										rs.getDate("dateFin"), 
										Pays.getPays(rs.getString("nomPays")),
										Statut.getStatut(rs.getString("statut")),
										new EquipeJDBC().getById(rs.getInt("idEquipe")),
										new CompteJDBC().getById(rs.getString("login"))));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tournois;
	}

	@Override
	public Optional<Tournoi> getById(String nomTournoi) {
		Optional<Tournoi> tournoi = Optional.empty();
		try {
			PreparedStatement st = ConnectionJDBC.getConnection().prepareStatement("select * from Tournoi where nomTournoi = ?");
			st.setString(1, nomTournoi);
			ResultSet rs = st.executeQuery();
			if(rs.next()) {
				Tournoi t = Tournoi.createTournoi(rs.getString("nomTournoi"), 
										Niveau.getNiveau(rs.getString("niveau")), 
										rs.getDate("dateDebut"), 
										rs.getDate("dateFin"), 
										Pays.getPays(rs.getString("nomPays")),
										Statut.getStatut(rs.getString("statut")),
										new EquipeJDBC().getById(rs.getInt("idEquipe")),
										new CompteJDBC().getById(rs.getString("login")));
				tournoi = Optional.ofNullable(t);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tournoi;
	}

	@Override
	public boolean add(Tournoi t) {
		boolean res = false;
		try {
			CallableStatement cs = ConnectionJDBC.getConnection().prepareCall("insert into Tournoi (nomTournoi, niveau, dateDebut, dateFin, nomPays, login, idEquipe, statut) values (?, ?, ?, ?, ?, ?, ?, ?)");
			cs.setString(1, t.getNomTournoi());
			cs.setString(2, t.getNiveau().denomination());
			cs.setDate(3, t.getDateDebut());
			cs.setDate(4, t.getDateFin());
			cs.setString(5, t.getPays().denomination());
			if (t.getCompte() != null) {
				cs.setString(6, t.getCompte().getLogin());
			} else {
				cs.setNull(6, java.sql.Types.VARCHAR);
			}
			if (t.getVainqueur() != null) {
				cs.setInt(7, t.getVainqueur().getIdEquipe());
			} else {
				cs.setNull(7, java.sql.Types.INTEGER);
			}
			cs.setString(8, t.getStatut().denomination());
			cs.executeUpdate();
			res = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public boolean update(Tournoi t) {
		boolean res = false;
		try {
			CallableStatement cs = ConnectionJDBC.getConnection().prepareCall("update Tournoi "
					+ "set niveau = ?, dateDebut = ?, dateFin = ?, nomPays = ?, login = ?, idEquipe = ?, statut = ?"
					+ "where nomTournoi = ?");
			
			cs.setString(1, t.getNiveau().denomination());
			cs.setDate(2, t.getDateDebut());
			cs.setDate(3, t.getDateFin());
			cs.setString(4, t.getPays().denomination());
			if (t.getCompte() != null) {
				cs.setString(5, t.getCompte().getLogin());
			} else {
				cs.setNull(5, java.sql.Types.VARCHAR);
			}
			if (t.getVainqueur() != null) {
				cs.setInt(6, t.getVainqueur().getIdEquipe());
			} else {
				cs.setNull(6, java.sql.Types.INTEGER);
			}
			cs.setString(7, t.getStatut().denomination());
			cs.setString(8, t.getNomTournoi());
			cs.executeUpdate();
			res = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public boolean delete(Tournoi t) {
		boolean res = false;
		try {
			CallableStatement cs = ConnectionJDBC.getConnection().prepareCall("delete from Tournoi where nomTournoi = ? ");
			cs.setString(1, t.getNomTournoi());
			cs.execute();
			res = true;
		} catch (SQLException e) {
			System.out.println("Suppression echec !");
			e.printStackTrace();
		}
		return res;
	}
	
	@Override
	public boolean existeTournoiEntreDates(Date dateDebut, Date dateFin) {
		try {
			Statement st = ConnectionJDBC.getConnection().createStatement();
			ResultSet rs = st.executeQuery("select dateDebut, dateFin from Tournoi");
			while(rs.next()) {
				if (!Tournoi.estTournoiDisjoint(dateDebut, dateFin, rs.getDate("dateDebut"), rs.getDate("dateFin"))) {
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public List<Equipe> getEquipesTournoi(Tournoi tournoi){
		List<Equipe> equipes = new ArrayList<>();
		try {
			PreparedStatement st = ConnectionJDBC.getConnection().prepareStatement("select Participer.idEquipe as idEquipeParticipante from Participer where nomTournoi = ?");
			st.setString(1, tournoi.getNomTournoi());
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				equipes.add(new Equipe().getEquipeParID(rs.getInt("idEquipeParticipante")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return equipes;
	}
	
	@Override
	public List<Arbitre> getArbitresTournoi(Tournoi tournoi){
		List<Arbitre> arbitres = new ArrayList<>();
		try {
			PreparedStatement st = ConnectionJDBC.getConnection().prepareStatement("select idArbitre from Associer where associer.nomTournoi = ?");
			st.setString(1, tournoi.getNomTournoi());
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				arbitres.add(new Arbitre().getArbitreParID(rs.getInt("idArbitre")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return arbitres;
	}
	
	@Override
	public List<Tournoi> getTournoisNiveauStatutNom(String nom, Niveau niveau, Statut statut){
		if (niveau == null && statut == null && nom == "") {
			return getAll();
		} else if (niveau == null && statut == null) {
			return getAll().stream().filter(tournoi -> tournoi.getNomTournoi().toLowerCase().contains(nom.toLowerCase())).collect(Collectors.toList());
		} else if (niveau != null && statut == null) {
			return getAll().stream().filter(tournoi -> tournoi.getNomTournoi().toLowerCase().contains(nom.toLowerCase()) && tournoi.getNiveau() == niveau).collect(Collectors.toList());
		} else if (niveau == null && statut != null) {
			return getAll().stream().filter(tournoi -> tournoi.getNomTournoi().toLowerCase().contains(nom.toLowerCase()) && tournoi.getStatut() == statut).collect(Collectors.toList());
		}
		return getAll().stream().filter(tournoi -> tournoi.getNomTournoi().toLowerCase().contains(nom.toLowerCase()) && tournoi.getStatut() == statut && tournoi.getNiveau() == niveau).collect(Collectors.toList());
	}
	
	@Override
	public void changerStatutTournoi(Tournoi tournoi, Statut statut) {
		try {
			PreparedStatement st = ConnectionJDBC.getConnection().prepareStatement("update tournoi set statut = ? where nomTournoi = ?");
			st.setString(1, statut.denomination());
			st.setString(2, tournoi.getNomTournoi());
			st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void changerVainqueurTournoi(Tournoi tournoi, Equipe equipe) {
		try {
			PreparedStatement st = ConnectionJDBC.getConnection().prepareStatement("update tournoi set idEquipe = ? where nomTournoi = ?");
			st.setInt(1, equipe.getIdEquipe());
			st.setString(2, tournoi.getNomTournoi());
			st.executeUpdate();
			st = ConnectionJDBC.getConnection().prepareStatement("update partie set idEquipe = ? where nomTournoi = ? and deroulement = 'Finale'");
			st.setInt(1, equipe.getIdEquipe());
			st.setString(2, tournoi.getNomTournoi());
			st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
