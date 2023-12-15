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

import modele.Equipe;
import modele.Jouer;
import modele.Partie;
import modele.Tournoi;

public class PartieJDBC implements PartieDAO{
	
	@Override
	public List<Partie> getAll() throws Exception {
		List<Partie> parties = new ArrayList<>();
		try {
			Statement st = ConnectionJDBC.getConnection().createStatement();
			ResultSet rs = st.executeQuery("select * from Partie");
			while(rs.next()) {
				TournoiJDBC tournoiBDD = new TournoiJDBC();
				Tournoi tournoi = null;
				Optional<Tournoi> opt = tournoiBDD.getById(rs.getString("nomTournoi"));
				tournoi = opt.orElse(null);
				
				EquipeJDBC equipeBDD = new EquipeJDBC();
				Optional<Equipe> opte = equipeBDD.getById(rs.getInt("idEquipe"));
				Equipe equipe1 = null;
				equipe1 = opte.orElse(null);
				
				JouerJDBC jdb = new JouerJDBC();
				Jouer j = jdb.getByDateHeure(rs.getDate("datePartie"), rs.getString("heureDebut")).orElse(null);
				Equipe equipe2 = null;
				if (j != null) {
					equipe2 = j.getEquipe();	
				}
                
                Partie p = new Partie(rs.getDate("datePartie"), rs.getString("heureDebut"), rs.getString("deroulement"), equipe1, tournoi);
                p.setEquipe2(equipe2);
                p.setEquipeGagnant(rs.getInt("gagnant"));
                parties.add(p);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return parties;
	}

	@Override
	public Optional<Partie> getById(Integer id) throws Exception {
		// Partie pas de id
		return Optional.empty();
	}
	
	@Override
	public Optional<Partie> getByDateHeure (Date date, String heure) throws Exception {
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

                opte = equipeBDD.getById(rs.getInt("idEquipe"));
                Equipe equipe2 = opte.orElse(null);
                
                Partie p = new Partie(rs.getDate("datePartie"), rs.getString("heureDebut"), rs.getString("deroulement"), equipe1, t);
                p.setEquipe2(equipe2);
                p.setEquipeGagnant(rs.getInt("gagnant"));
                opt = Optional.ofNullable(p);
            }
        } catch (SQLException e) {
        	e.printStackTrace();
        }
		
		return opt;
	}
	
	@Override
	public boolean add(Partie p) throws Exception {
		boolean res = false;
		try {
			CallableStatement cs;
			cs = ConnectionJDBC.getConnection().prepareCall("insert into Partie (datePartie, heureDebut, deroulement, nomTournoi, idEquipe, gagnant) values (?,?,?,?,?,?)");
			cs.setDate(1, p.getDate());
			cs.setString(2, p.getHeure());
			cs.setString(3, p.getDeroulement());
			cs.setString(4, p.getTournoi().getNomTournoi());
			cs.setInt(5, p.getEquipe1().getIdEquipe());
			cs.setInt(6, p.getEquipeGagnant());
			cs.executeUpdate();
			
			if (p.getEquipe2()!=null) {
				JouerJDBC jdb = new JouerJDBC();
				jdb.add(new Jouer(p.getEquipe2(), p));
			}
			
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
	        Connection connection = ConnectionJDBC.getConnection();
	        String query;
  
	        query = "UPDATE Partie SET deroulement = ?, idEquipe = ?, gagnant = ?, nomTournoi = ? WHERE datePartie = ? and heureDebut = ?";

        	CallableStatement cs = connection.prepareCall(query);
            cs.setString(1, p.getDeroulement());
            cs.setInt(2, p.getEquipe1().getIdEquipe());
            cs.setInt(3, p.getEquipeGagnant());
            cs.setString(4, p.getTournoi().getNomTournoi());
            cs.setDate(5, p.getDate());
            cs.setString(6, p.getHeure());

            cs.executeUpdate();
            
            if (p.getEquipe2()!=null) {
				JouerJDBC jdb = new JouerJDBC();
				jdb.update(new Jouer(p.getEquipe2(), p));
			}
            
            res = true;

            System.out.println("Partie mise à jour");
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return res;
	}
	@Override
	public boolean delete(Partie p) throws Exception {
		boolean res = false;
		try {
			if (p.getEquipe2()!=null) {
				JouerJDBC jdb = new JouerJDBC();
				jdb.delete(new Jouer(p.getEquipe2(), p));
			}
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

}
