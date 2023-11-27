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
				Optional<Tournoi> opt = tournoiBDD.getById(rs.getInt("idTournoi"));
				if (opt.isPresent()) {
					tournoi = opt.get();
				}
				EquipeJDBC equipeBDD = new EquipeJDBC();
				Optional<Equipe> opte = equipeBDD.getById(rs.getInt("idEquipe"));
				Equipe equipe = null;
				if(opte.isPresent()) {
					equipe = opte.get();
				}
				Partie p = new Partie(rs.getDate("datePartie"), rs.getString("heureDebut"), rs.getString("deroulement"), tournoi);
				p.setEquipeGagnant(equipe);
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
                Optional<Tournoi> ot = tjdbc.getById(rs.getInt("idTournoi"));
                Tournoi t = ot.orElse(null);

                EquipeJDBC equipeBDD = new EquipeJDBC();
                Optional<Equipe> opte = equipeBDD.getById(rs.getInt("idEquipe"));
                Equipe equipe = opte.orElse(null);

                Partie p = new Partie(rs.getDate("datePartie"), rs.getString("heureDebut"), rs.getString("deroulement"), t);
                p.setEquipeGagnant(equipe);
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
			if (p.getEquipeGagnant() != null) {
				cs = ConnectionJDBC.getConnection().prepareCall("insert into Partie (datePartie, heureDebut, deroulement, idTournoi, idEquipe) values (?,?,?,?,?)");
				cs.setInt(5, p.getEquipeGagnant().getIdEquipe());
			} else {
				cs = ConnectionJDBC.getConnection().prepareCall("insert into Partie (datePartie, heureDebut, deroulement, idTournoi) values (?,?,?,?)");
			}
			cs.setDate(1, p.getDate());
			cs.setString(2, p.getHeure());
			cs.setString(3, p.getDeroulement());
			cs.setInt(4, p.getTournoi().getIdTournoi());
			
			cs.execute();
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

	        if (p.getEquipeGagnant() != null) {
	            query = "UPDATE Partie SET datePartie = ?, heureDebut = ?, deroulement = ?, idEquipe = ? WHERE idTournoi = ?";
	        } else {
	            query = "UPDATE Partie SET datePartie = ?, heureDebut = ?, deroulement = ? WHERE idTournoi = ?";
	        }

        	CallableStatement cs = connection.prepareCall(query);
            cs.setDate(1, p.getDate());
            cs.setString(2, p.getHeure());
            cs.setString(3, p.getDeroulement());

            if (p.getEquipeGagnant() != null) {
                cs.setInt(4, p.getEquipeGagnant().getIdEquipe());
                cs.setInt(5, p.getTournoi().getIdTournoi());
            } else {
                cs.setInt(4, p.getTournoi().getIdTournoi());
            }

            cs.execute();
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
			CallableStatement cs = ConnectionJDBC.getConnection().prepareCall("delete from Partie where datePartie = ? and heureDebut = ?");
			cs.setDate(1, p.getDate());
			cs.setString(2, p.getHeure());
			cs.execute();
			res = true;
			
			System.out.println("Partie supprime.");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

}
