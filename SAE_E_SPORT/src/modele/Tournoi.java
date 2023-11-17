package modele;

import java.sql.Date;

public class Tournoi {

	private int idTournoi;
	private String nomTournoi;
	private Niveau niveau;
	private Date dateDebut;
	private Date dateFin;
	private Equipe vainqueur;
	
	public Tournoi(int idTournoi, String nomTournoi, Niveau niveau, Date dateDebut, Date dateFin) {
		this.idTournoi = idTournoi;
		this.nomTournoi = nomTournoi;
		this.niveau = niveau;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
	}

	public Equipe getVainqueur() {
		return vainqueur;
	}

	public void setVainqueur(Equipe vainqueur) {
		this.vainqueur = vainqueur;
	}

	public int getIdTournoi() {
		return idTournoi;
	}

	public String getNomTournoi() {
		return nomTournoi;
	}

	public Niveau getNiveau() {
		return niveau;
	}

	public Date getDateDebut() {
		return dateDebut;
	}

	public Date getDateFin() {
		return dateFin;
	}
	
}
