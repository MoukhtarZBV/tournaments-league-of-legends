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

import modele.Compte;
import modele.Equipe;
import modele.ModeleCreationTournoi;
import modele.Niveau;
import modele.Pays;
import modele.Tournoi;

public class TournoiJDBC implements TournoiDAO{
	
	@Override
	public List<Tournoi> getAll() throws Exception {
		List<Tournoi> tournois = new ArrayList<>();
		try {
			Statement st = ConnectionJDBC.getConnection().createStatement();
			ResultSet rs = st.executeQuery("select * from Tournoi");
			while(rs.next()) {
				tournois.add(new Tournoi(rs.getInt("idTournoi"), rs.getString("nomTournoi"), 
						Niveau.getNiveau(rs.getString("niveau")), rs.getDate("dateDebut"), 
						rs.getDate("dateFin"), Pays.getPays(rs.getString("nomPays"))));
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
			Statement st = ConnectionJDBC.getConnection().createStatement();
			ResultSet rs = st.executeQuery("select * from Tournoi where idTournoi = "+ id);
			if(rs.next()) {
				Tournoi t = new Tournoi(rs.getInt("idTournoi"), rs.getString("nomTournoi"), 
						Niveau.getNiveau(rs.getString("niveau")), rs.getDate("dateDebut"), 
						rs.getDate("dateFin"), Pays.getPays(rs.getString("nomPays")));

				EquipeJDBC ejdbc = new EquipeJDBC();
				Optional<Equipe> e = ejdbc.getById(rs.getInt("idEquipe"));
				t.setVainqueur(e.orElse(null));
				
				CompteJDBC cjdbc = new CompteJDBC();
				Optional<Compte> opt = cjdbc.getById(rs.getInt("idCompte"));
				t.setCompte(opt.orElse(null));
				
				tournois = Optional.ofNullable(t);
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
			CallableStatement cs = ConnectionJDBC.getConnection().prepareCall("insert into Tournoi (idTournoi, nomTournoi, niveau, dateDebut, dateFin, idCompte, idEquipe) values (NEXT VALUE FOR SEQ_Tournoi, ?, ?, ?, ?, NULL, NULL)");
			cs.setString(1, t.getNomTournoi());
			cs.setString(2, t.getNiveau().denomination());
			cs.setDate(3, t.getDateDebut());
			cs.setDate(4, t.getDateFin());
			cs.executeUpdate();
			res = true;
			
			System.out.println("Tournoi "+t.getNomTournoi()+" ajoute.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public boolean update(Tournoi t) throws Exception {
		boolean res = false;
		try {
			CallableStatement cs;
			if (t.getCompte()!=null) {
				if (t.getVainqueur()!=null) {
					cs = ConnectionJDBC.getConnection().prepareCall("update Tournoi set nomTournoi = ?, niveau = ?, "+
																	"dateDebut = ?, dateFin = ?, nomPays = ?, idEquipe =?, "+
																	"idCompte =? where idTournoi = ?");
					cs.setInt(6, t.getVainqueur().getIdEquipe());
					cs.setInt(7, t.getCompte().getId());
					cs.setInt(8, t.getIdTournoi());
				}else {
					cs = ConnectionJDBC.getConnection().prepareCall("update Tournoi set nomTournoi = ?, niveau = ?, "+ 
																	"dateDebut = ?, dateFin = ?, nomPays = ?, idCompte =? "+
																	"where idTournoi = ?");
					cs.setInt(6, t.getCompte().getId());
					cs.setInt(7, t.getIdTournoi());
				}
			} else {
				if (t.getVainqueur()!=null) {
					cs = ConnectionJDBC.getConnection().prepareCall("update Tournoi set nomTournoi = ?, niveau = ?, "+
																	"dateDebut = ?, dateFin = ?, nomPays = ?, idEquipe =? "+
																	"where idTournoi = ?");
					cs.setInt(6, t.getVainqueur().getIdEquipe());
					cs.setInt(7, t.getIdTournoi());
				}else {
					cs = ConnectionJDBC.getConnection().prepareCall("update Tournoi set nomTournoi = ?, niveau = ?, "+
																	"dateDebut = ?, dateFin = ?, nomPays = ? "+
																	"where idTournoi = ?");
					cs.setInt(6, t.getIdTournoi());
				}
			}
			cs.setString(1, t.getNomTournoi());
			cs.setString(2, t.getNiveau().denomination());
			cs.setDate(3, t.getDateDebut());
			cs.setDate(4, t.getDateFin());
			cs.setString(5, t.getPays().denomination());
			cs.executeUpdate();
			res = true;
			
			System.out.println("Tournoi "+t.getNomTournoi().toUpperCase()+" mis a jour.");

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public boolean delete(Tournoi t) throws Exception {
		boolean res = false;
		try {

			CallableStatement cs = ConnectionJDBC.getConnection().prepareCall("delete from Tournoi where idTournoi = ? ");
			cs.setInt(1, t.getIdTournoi());
			cs.execute();
			
			res = true;
			
			System.out.println("Tournoi "+t.getNomTournoi().toUpperCase()+" supprime.");
		} catch (SQLException e) {
			System.out.println("Suppression echec !");
			e.printStackTrace();
		}
		return res;
	}
	
	public boolean existeTournoiEntreDates(Date dateDebut, Date dateFin) throws SQLException {
		Tournoi tournoi = null;
		try {
			Statement st = ConnectionJDBC.getConnection().createStatement();
			ResultSet rs = st.executeQuery("select dateDebut, dateFin from Tournoi");
			while(rs.next()) {
				if (!ModeleCreationTournoi.estTournoiDisjoint(dateDebut, dateFin, rs.getDate("dateDebut"), rs.getDate("dateFin"))) {
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public List<Tournoi> getTournoisDeNiveau(Niveau niveau){
		List<Tournoi> tournois = new ArrayList<>();
		try {
			PreparedStatement st = ConnectionJDBC.getConnection().prepareStatement("select * from Tournoi where niveau = ?");
			st.setString(1, niveau.denomination());
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				tournois.add(new Tournoi(rs.getInt("idTournoi"), rs.getString("nomTournoi"), Niveau.getNiveau(rs.getString("niveau")), rs.getDate("dateDebut"), rs.getDate("dateFin"), Pays.getPays(rs.getString("nomPays"))));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tournois;
	}
	
	public List<Tournoi> getTournoisEtat(String etat) {
		List<Tournoi> tournois = new ArrayList<>();
		String req = null;
		switch (etat) {
		case "Terminé":
			req = "select * from Tournoi where dateFin < ?";
			break;
		case "En cours":
			req = "select * from Tournoi where ? between dateDebut and dateFin";
			break;
		case "À venir":
			req = "select * from Tournoi where dateDebut > ?";
			break;
		}
		try {
			PreparedStatement st = ConnectionJDBC.getConnection().prepareStatement(req);
			st.setDate(1, new Date(System.currentTimeMillis()));
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				tournois.add(new Tournoi(rs.getInt("idTournoi"), rs.getString("nomTournoi"), Niveau.getNiveau(rs.getString("niveau")), rs.getDate("dateDebut"), rs.getDate("dateFin"), Pays.getPays(rs.getString("nomPays"))));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tournois;
	}
	
	public static int nombreEquipesTournoi(Tournoi tournoi) {
		int nombreEquipes = 0;
		try {
			PreparedStatement st = ConnectionJDBC.getConnection().prepareStatement("select count(*) as nombreEquipes from Participer where idTournoi = ?");
			st.setInt(1, tournoi.getIdTournoi());
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				nombreEquipes = rs.getInt("nombreEquipes");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nombreEquipes;
	}
}
