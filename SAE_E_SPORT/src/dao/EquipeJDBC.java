package dao;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import modele.Equipe;
import modele.Joueur;
import modele.Pays;

public class EquipeJDBC implements EquipeDAO{

	@Override
	public List<Equipe> getAll() throws Exception {
		List<Equipe> equipes = new ArrayList<>();
		try {
			Statement st = ConnectionJDBC.getConnection().createStatement();
			ResultSet rs = st.executeQuery("select * from Equipe");
			while(rs.next()) {
				Equipe e = new Equipe(rs.getInt("idEquipe"), rs.getString("nomEquipe"), 
										rs.getInt("rang"), Pays.getPays(rs.getString("nationalite")));
				JoueurJDBC j = new JoueurJDBC();
				for(Joueur joueur : j.getByEquipe(e)) {
					e.ajouterJoueur(joueur);
				};
				equipes.add(e);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return equipes;
	}

	@Override
	public Optional<Equipe> getById(Integer id) throws Exception {
		Optional<Equipe> equipes = Optional.empty();
		try {
			Statement st = ConnectionJDBC.getConnection().createStatement();
			ResultSet rs = st.executeQuery("select * from Equipe where idEquipe = "+id);
			if(rs.next()) {
				Equipe e = new Equipe(rs.getInt("idEquipe"), rs.getString("nomEquipe"), 
										rs.getInt("rang"), Pays.getPays(rs.getString("nationalite")));
				JoueurJDBC j = new JoueurJDBC();
				for(Joueur jou : j.getByEquipe(e)) {
					e.ajouterJoueur(jou);
				}
				equipes = Optional.ofNullable(e);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return equipes;
	}

	//Test ok
	@Override
	public boolean add(Equipe e) throws Exception {
		if (e.getJoueurs().size()!=5) {
			throw new IllegalArgumentException("Nombre de joueurs d'une equipe doit etre 5 !");
		}
		boolean res = false;
		try {
			CallableStatement cs = ConnectionJDBC.getConnection().prepareCall("insert into Equipe (idEquipe, nomEquipe, rang, nationalite) values (NEXT VALUE FOR SEQ_EQUIPE,?,?,?)");
			cs.setString(1, e.getNom());
			cs.setInt(2, e.getRang());
			cs.setString(3, e.getNationalite().denomination());
			cs.executeUpdate();
			
			res = true;
			System.out.println("L'équipe "+ e.getNom().toUpperCase() +" a été ajouté.");
		}catch (SQLException exp) {
			exp.printStackTrace();
		}
		return res;
	}

	@Override
	public boolean update(Equipe e) throws Exception {
		boolean res = false;
		try {
			CallableStatement cs = ConnectionJDBC.getConnection().prepareCall("update Equipe set nomEquipe = ?, rang = ?, nationalite = ? where idEquipe = ?");
			cs.setString(1, e.getNom());
			cs.setInt(2, e.getRang());
			cs.setString(3, e.getNationalite().denomination());
			cs.setInt(4, e.getIdEquipe());
			cs.executeUpdate();
			res = true;
		}catch (SQLException exp) {
			exp.printStackTrace();
		}
		return res;
	}

	@Override
	public boolean delete(Equipe e) throws Exception {
		boolean res = false;
		try {
			JoueurJDBC jjdbc = new JoueurJDBC();
			for (Joueur joueur : e.getJoueurs()) {
				joueur.setEquipe(null);
				jjdbc.update(joueur);
			}
			Statement st = ConnectionJDBC.getConnection().createStatement();
			st.executeUpdate("delete from Equipe where idEquipe = "+e.getIdEquipe());
			res = true;
		} catch (SQLException exp) {
			exp.printStackTrace();
		}
		return res;
	}

	@Override
	public Optional<Equipe> getByNom(String nom) throws Exception {
		Optional<Equipe> equipe = Optional.empty();
		try {
			CallableStatement st = ConnectionJDBC.getConnection().prepareCall("select * from Equipe where nomEquipe = ?");
			st.setString(1, nom);
			ResultSet rs = st.executeQuery();	
			if (rs.next()) {
				Equipe e = new Equipe(rs.getInt("idEquipe"), rs.getString("nomEquipe"), rs.getInt("rang"), Pays.getPays(rs.getString("nationalite")));
				JoueurJDBC j = new JoueurJDBC();
				for(Joueur jou : j.getByEquipe(e)) {
					e.ajouterJoueur(jou);
				};
				equipe = Optional.ofNullable(e);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return equipe;
	}
	
	public int getIdByNom(String nom) throws Exception{
		int id = -1;
		try {
			CallableStatement st = ConnectionJDBC.getConnection().prepareCall("select idEquipe from Equipe where nomEquipe = ?");
			st.setString(1, nom);
			ResultSet rs = st.executeQuery();    
			if (rs.next()) {
				id = rs.getInt("idEquipe");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}
	
	@Override
    public int getNextValueSequence() throws Exception {
        int res = -1;
        Statement st = ConnectionJDBC.getConnection().createStatement();
        ResultSet rs = st.executeQuery("VALUES NEXT VALUE FOR SEQ_Equipe");
        if (rs.next()) {
            res = rs.getInt(1);
        }
        return res;
    }
	
	

}
