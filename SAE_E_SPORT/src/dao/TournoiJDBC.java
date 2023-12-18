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
import java.util.stream.Collectors;

import modele.Compte;
import modele.Equipe;
import modele.Niveau;
import modele.Pays;
import modele.Status;
import modele.Tournoi;

public class TournoiJDBC implements TournoiDAO {
	
	@Override
	public List<Tournoi> getAll() {
		List<Tournoi> tournois = new ArrayList<>();
		try {
			Statement st = ConnectionJDBC.getConnection().createStatement();
			ResultSet rs = st.executeQuery("select * from Tournoi");
			while(rs.next()) {
				tournois.add(new Tournoi(rs.getString("nomTournoi"), 
										Niveau.getNiveau(rs.getString("niveau")), 
										rs.getDate("dateDebut"), 
										rs.getDate("dateFin"), 
										Pays.getPays(rs.getString("nomPays"))));
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
			System.out.println(nomTournoi);
			ResultSet rs = st.executeQuery();
			if(rs.next()) {
				Tournoi t = new Tournoi(rs.getString("nomTournoi"), 
										Niveau.getNiveau(rs.getString("niveau")), 
										rs.getDate("dateDebut"), 
										rs.getDate("dateFin"), 
										Pays.getPays(rs.getString("nomPays")));
				System.out.println(t);
				EquipeJDBC ejdbc = new EquipeJDBC();
				Optional<Equipe> e = ejdbc.getById(rs.getInt("idEquipe"));
				t.setVainqueur(e.orElse(null));
				
				CompteJDBC cjdbc = new CompteJDBC();
				Optional<Compte> opt = cjdbc.getById(rs.getInt("idCompte"));
				t.setCompte(opt.orElse(null));
				
				tournoi = Optional.ofNullable(t);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tournoi;
	}

	@Override
	public boolean add(Tournoi t) throws Exception {
		boolean res = false;
		try {
			CallableStatement cs = ConnectionJDBC.getConnection().prepareCall("insert into Tournoi (nomTournoi, niveau, dateDebut, dateFin, nomPays, idCompte, idEquipe) values (?, ?, ?, ?, ?, NULL, NULL)");
			cs.setString(1, t.getNomTournoi());
			cs.setString(2, t.getNiveau().denomination());
			cs.setDate(3, t.getDateDebut());
			cs.setDate(4, t.getDateFin());
			cs.setString(5, t.getPays().denomination());
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
																	"dateDebut = ?, dateFin = ?, nomPays = ?, idEquipe =?, idCompte =?");
					cs.setInt(6, t.getVainqueur().getIdEquipe());
					cs.setInt(7, t.getCompte().getId());
				}else {
					cs = ConnectionJDBC.getConnection().prepareCall("update Tournoi set nomTournoi = ?, niveau = ?, "+ 
																	"dateDebut = ?, dateFin = ?, nomPays = ?, idCompte = ?");
					cs.setInt(6, t.getCompte().getId());
				}
			} else {
				if (t.getVainqueur()!=null) {
					cs = ConnectionJDBC.getConnection().prepareCall("update Tournoi set nomTournoi = ?, niveau = ?, "+
																	"dateDebut = ?, dateFin = ?, nomPays = ?, idEquipe =? ");
					cs.setInt(6, t.getVainqueur().getIdEquipe());
				}else {
					cs = ConnectionJDBC.getConnection().prepareCall("update Tournoi set nomTournoi = ?, niveau = ?, "+
																	"dateDebut = ?, dateFin = ?, nomPays = ? ");
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

			CallableStatement cs = ConnectionJDBC.getConnection().prepareCall("delete from Tournoi where nomTournoi = ? ");
			cs.setString(1, t.getNomTournoi());
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
				if (!Tournoi.estTournoiDisjoint(dateDebut, dateFin, rs.getDate("dateDebut"), rs.getDate("dateFin"))) {
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static int nombreEquipesTournoi(Tournoi tournoi) {
		int nombreEquipes = 0;
		try {
			PreparedStatement st = ConnectionJDBC.getConnection().prepareStatement("select count(*) as nombreEquipes from Participer where nomTournoi = ?");
			st.setString(1, tournoi.getNomTournoi());
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				nombreEquipes = rs.getInt("nombreEquipes");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nombreEquipes;
	}
	
	public List<Tournoi> getTournoisNiveauStatusNom(String nom, Niveau niveau, Status status){
		if (niveau == null && status == null && nom == "") {
			return getAll();
		} else if (niveau == null && status == null) {
			return getAll().stream().filter(tournoi -> tournoi.getNomTournoi().contains(nom)).collect(Collectors.toList());
		} else if (niveau != null && status == null) {
			return getAll().stream().filter(tournoi -> tournoi.getNomTournoi().contains(nom)).filter(tournoi -> tournoi.getNiveau() == niveau).collect(Collectors.toList());
		} else if (niveau == null && status != null) {
			return getAll().stream().filter(tournoi -> tournoi.getNomTournoi().contains(nom)).filter(tournoi -> Tournoi.etatTournoi(tournoi) == status).collect(Collectors.toList());
		}
		return getAll().stream().filter(tournoi -> tournoi.getNomTournoi().contains(nom)).filter(tournoi -> Tournoi.etatTournoi(tournoi) == status && tournoi.getNiveau() == niveau).collect(Collectors.toList());
	}
}
