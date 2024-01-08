package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import modele.Equipe;
import modele.Jouer;
import modele.Partie;
import modele.Tournoi;

public class PartieJDBC implements PartieDAO{
	
	@Override
	public List<Partie> getAll() {
		List<Partie> parties = new ArrayList<>();
		try {
			Statement st = ConnectionJDBC.getConnection().createStatement();
			ResultSet rs = st.executeQuery("select * from Partie");
			while(rs.next()) {
				TournoiJDBC tournoiBDD = new TournoiJDBC();
				Optional<Tournoi> opt = tournoiBDD.getById(rs.getString("nomTournoi"));
				Tournoi tournoi = null;
				tournoi = opt.orElse(null);
				
				EquipeJDBC equipeBDD = new EquipeJDBC();
				Optional<Equipe> opte = equipeBDD.getById(rs.getInt("idEquipe"));
				Equipe equipe1 = null;
				equipe1 = opte.orElse(null);
                Partie p = new Partie(rs.getDate("datePartie"), rs.getString("heureDebut"), rs.getString("deroulement"), tournoi);
                List<Equipe> equipesPartie = getEquipes(p);
                p.setEquipeUne(equipesPartie.get(0));
                p.setEquipeDeux(equipesPartie.get(1));
                p.setEquipeGagnante(equipe1);
                parties.add(p);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return parties;
	}

	@Override
	public Optional<Partie> getById(Integer id) {
		// Partie pas de id
		return Optional.empty();
	}
	
	@Override
	public boolean add(Partie p) throws IllegalArgumentException {
		if (p.getEquipeUne() == null || p.getEquipeDeux() == null) {
			throw new IllegalArgumentException("Les équipes participant à la partie ne doivent pas être nulles !");
		} 
		boolean res = false;
		try {
			CallableStatement cs;
			cs = ConnectionJDBC.getConnection().prepareCall("insert into Partie (datePartie, heureDebut, deroulement, nomTournoi, idEquipe) values (?,?,?,?,?)");
			cs.setDate(1, p.getDate());
			cs.setString(2, p.getHeure());
			cs.setString(3, p.getDeroulement());
			cs.setString(4, p.getTournoi().getNomTournoi());
			if (p.getEquipeGagnante() == null) {
				cs.setNull(5, java.sql.Types.INTEGER);
			} else {
				cs.setInt(5, p.getEquipeGagnante().getIdEquipe());
			}
			cs.executeUpdate();
			Jouer jouerUn = new Jouer(p.getEquipeUne(), p.getDate(), p.getHeure());
			Jouer jouerDeux = new Jouer(p.getEquipeDeux(), p.getDate(), p.getHeure());
			
			JouerJDBC jouerJDBC = new JouerJDBC();
			jouerJDBC.add(jouerUn);
			jouerJDBC.add(jouerDeux);
			res = true;
			
			System.out.println("Partie ajoute");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	@Override
	public Optional<Partie> getByDateHeure (Date date, String heure) {
		Optional<Partie> opt = Optional.empty();
		try {
			PreparedStatement preparedStatement = ConnectionJDBC.getConnection().prepareStatement("select * from partie where datePartie = ? and heureDebut = ?");
            preparedStatement.setDate(1, new java.sql.Date(date.getTime())); 
            preparedStatement.setString(2, heure);
            
            ResultSet rs = preparedStatement.executeQuery();
            
            if (rs.next()) {
                TournoiJDBC tjdbc = new TournoiJDBC();
                Optional<Tournoi> ot = tjdbc.getById(rs.getString("nomTournoi"));
                Tournoi t = ot.orElse(null);

                EquipeJDBC equipeBDD = new EquipeJDBC();
                Optional<Equipe> opte = equipeBDD.getById(rs.getInt("idEquipe"));
                Equipe equipe1 = opte.orElse(null);
                
                Partie p = new Partie(rs.getDate("datePartie"), rs.getString("heureDebut"), rs.getString("deroulement"), t);
                p.setEquipeUne(getEquipes(p).get(0));
                p.setEquipeDeux(getEquipes(p).get(1));
                p.setEquipeGagnante(equipe1);
                opt = Optional.ofNullable(p);
            }
        } catch (SQLException e) {
        	e.printStackTrace();
        }
		
		return opt;
	}

	@Override
	public boolean update(Partie p) {
	    boolean res = false;

	    try {
	        Connection connection = ConnectionJDBC.getConnection();
        	CallableStatement cs = connection.prepareCall("UPDATE Partie SET deroulement = ?, idEquipe = ?, nomTournoi = ? WHERE datePartie = ? and heureDebut = ?");
            cs.setString(1, p.getDeroulement());
            if (p.getEquipeGagnante() != null) {
                cs.setInt(2, p.getEquipeGagnante().getIdEquipe());
            } else {
            	cs.setNull(2, java.sql.Types.INTEGER);
            }
            cs.setString(3, p.getTournoi().getNomTournoi());
            cs.setDate(4, p.getDate());
            cs.setString(5, p.getHeure());
            cs.executeUpdate();
            
            res = true;

            System.out.println("Partie mise à jour");
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return res;
	}
	
	@Override
	public boolean delete(Partie p) {
		boolean res = false;
		try {
			CallableStatement cs = ConnectionJDBC.getConnection().prepareCall("delete from Partie where datePartie = ? and heureDebut = ?");
			cs.setDate(1, p.getDate());
			cs.setString(2, p.getHeure());
			cs.executeUpdate();
			res = true;
			
			System.out.println("Partie supprime.");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	@Override
	public List<Partie> getPartiesTournoi(Tournoi tournoi) {
		List<Partie> parties = new ArrayList<>();
		try {
			Statement st = ConnectionJDBC.getConnection().createStatement();
			ResultSet rs = st.executeQuery("select * from Partie where id");
			while(rs.next()) {
				TournoiJDBC tournoiBDD = new TournoiJDBC();
				Optional<Tournoi> opt = tournoiBDD.getById(rs.getString("nomTournoi"));
				Tournoi tournoiPartie = null;
				tournoi = opt.orElse(null);
				
				EquipeJDBC equipeBDD = new EquipeJDBC();
				Optional<Equipe> opte = equipeBDD.getById(rs.getInt("idEquipe"));
				Equipe equipe1 = null;
				equipe1 = opte.orElse(null);
				
                Partie p = new Partie(rs.getDate("datePartie"), rs.getString("heureDebut"), rs.getString("deroulement"), tournoi);
                p.setEquipeUne(getEquipes(p).get(0));
                p.setEquipeDeux(getEquipes(p).get(1));
                p.setEquipeGagnante(equipe1);
                parties.add(p);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return parties;
	}
	
	@Override
	public Optional<Partie> getFinaleTournoi(Tournoi tournoi) {
		Optional<Partie> partie = Optional.empty();
		try {
			PreparedStatement st = ConnectionJDBC.getConnection().prepareStatement("select datePartie, heureDebut from partie where partie.nomTournoi = ? and partie.deroulement = 'Finale'");
			st.setString(1, tournoi.getNomTournoi());
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				partie = Optional.ofNullable(getByDateHeure(rs.getDate("datePartie"), rs.getString("heureDebut")).orElse(null));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return partie;
	}
	
	@Override
	public List<Equipe> getEquipes(Partie p) {
        List<Equipe> equipes = new ArrayList<>();
		try {
			PreparedStatement st = ConnectionJDBC.getConnection().prepareStatement("select jouer.idEquipe from jouer, partie "
					+ "where jouer.datePartie = partie.datePartie "
					+ "and jouer.heureDebut = partie.heureDebut "
					+ "and partie.datePartie = ?"
					+ "and partie.heureDebut = ?"
					+ "and partie.nomTournoi = ?");
			st.setDate(1, p.getDate());
			st.setString(2, p.getHeure());
			st.setString(3, p.getTournoi().getNomTournoi());
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				Equipe equipe = new EquipeJDBC().getById(rs.getInt("idEquipe")).orElse(null);
				equipes.add(equipe);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return equipes;
	}

}
