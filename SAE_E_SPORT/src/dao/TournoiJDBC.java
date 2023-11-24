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
				Tournoi t = new Tournoi(rs.getInt("idTournoi"), rs.getString("nomTournoi"), 
						Niveau.getNiveau(rs.getString("niveau")), rs.getDate("dateDebut"), 
						rs.getDate("dateFin"), Pays.getPays(rs.getString("nomPays")));
				EquipeJDBC ejdbc = new EquipeJDBC();
				Optional<Equipe> e = ejdbc.getById(rs.getInt("idEquipe"));
				if (e.isPresent()) {
					t.setVainqueur(e.get());
				}
				CompteJDBC cjdbc = new CompteJDBC();
				Optional<Compte> opt = cjdbc.getById(rs.getInt("idCompte"));
				if (opt.isPresent()) {
					t.setCompte(opt.get());
				}
				tournois.add(t);
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
				if (e.isPresent()) {
					t.setVainqueur(e.get());
				}
				
				CompteJDBC cjdbc = new CompteJDBC();
				Optional<Compte> opt = cjdbc.getById(rs.getInt("idCompte"));
				if (opt.isPresent()) {
					t.setCompte(opt.get());
				}
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
			CallableStatement cs;
			if (t.getCompte()!=null) {
				if (t.getVainqueur()!=null) {
					cs = ConnectionJDBC.getConnection().prepareCall("insert into Tournoi (idTournoi, nomTournoi, "+
																	"niveau, dateDebut, dateFin, nomPays, idEquipe, idCompte) "+
																	"values (NEXT VALUE FOR SEQ_Tournoi,?,?,?,?,?,?,?)");
					cs.setInt(6, t.getVainqueur().getIdEquipe());
					cs.setInt(7, t.getCompte().getId());
				}else {
					cs = ConnectionJDBC.getConnection().prepareCall("insert into Tournoi (idTournoi, nomTournoi, "+
																	"niveau, dateDebut, dateFin, nomPays, idEquipe, idCompte) "+
																	"values (NEXT VALUE FOR SEQ_Tournoi,?,?,?,?,?,null,?)");
					cs.setInt(6, t.getCompte().getId());
				}
			} else {
				if (t.getVainqueur()!=null) {
					cs = ConnectionJDBC.getConnection().prepareCall("insert into Tournoi (idTournoi, nomTournoi, niveau, "+
																	"dateDebut, dateFin, nomPays, idEquipe, idCompte) "+
																	"values (NEXT VALUE FOR SEQ_Tournoi,?,?,?,?,?,?,null)");
					cs.setInt(6, t.getVainqueur().getIdEquipe());
				}else {
					cs = ConnectionJDBC.getConnection().prepareCall("insert into Tournoi (idTournoi, nomTournoi, niveau, "+
																	"dateDebut, dateFin, nomPays, idEquipe, idCompte) "+
																	"values (NEXT VALUE FOR SEQ_Tournoi,?,?,?,?,?,null,null)");
				}
			}
			cs.setString(1, t.getNomTournoi());
			cs.setString(2, t.getNiveau().denomination());
			cs.setDate(3, t.getDateDebut());
			cs.setDate(4, t.getDateFin());
			cs.setString(5, t.getPays().denomination());
			
			cs.executeUpdate();
			res = true;
			
			System.out.println("Tournoi "+t.getNomTournoi().toUpperCase()+" ajoute.");
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
		PreparedStatement st = ConnectionJDBC.getConnection().prepareStatement("SELECT COUNT(*) FROM Tournoi WHERE dateDebut BETWEEN ? AND ?");
		st.setDate(1, dateDebut);
		st.setDate(2, dateFin);
		ResultSet res = st.executeQuery();
		res.next();
		int nb = res.getInt(1);
		return nb > 0;
	}

}
