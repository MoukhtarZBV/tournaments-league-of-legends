package entites;

import java.time.LocalDate;

public class Tournoi {

	private int idTournoi;
	private String nomTournoi;
	private String niveau;
	private LocalDate dateDebut;
	private LocalDate dateFin;
	private Equipe vainqueur;
	
	public Tournoi(int idTournoi, String nomTournoi, String niveau, LocalDate dateDebut, LocalDate dateFin) {
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

	public String getNiveau() {
		return niveau;
	}

	public LocalDate getDateDebut() {
		return dateDebut;
	}

	public LocalDate getDateFin() {
		return dateFin;
	}
	
}
