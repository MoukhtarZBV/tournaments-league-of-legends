package modele;

import java.sql.Date;
import java.util.Objects;

public class Tournoi {

	private int idTournoi;
	private String nomTournoi;
	private Niveau niveau;
	private Date dateDebut;
	private Date dateFin;
	private Pays pays;
	private Compte compte;
	private Equipe vainqueur;
	
	public Tournoi(int id, String nomTournoi, Niveau niveau, Date dateDebut, Date dateFin, Pays pays) {
		this.idTournoi = id;
		this.nomTournoi = nomTournoi;
		this.niveau = niveau;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.pays = pays;
		this.compte = null;
		this.vainqueur = null;
	}

	public Compte getCompte() {
		return this.compte;
	}
	
	public void setCompte(Compte compte) {
		this.compte = compte;
	}
	
	public Pays getPays() {
		return this.pays;
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

	@Override
	public int hashCode() {
		return Objects.hash(idTournoi);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (obj instanceof Tournoi) {
			Tournoi other = (Tournoi) obj;
			return idTournoi == other.idTournoi && this.compte.equals(other.compte) && this.dateDebut == other.dateDebut
					&& this.dateFin == other.dateFin && this.niveau.equals(other.niveau) && this.nomTournoi.equals(other.nomTournoi)
					&& this.pays.equals(pays) && this.vainqueur.equals(other.vainqueur);
		} 
		return false;
	}
	
	@Override
	public String toString() {
		return "Tournoi [id="+ this.idTournoi + ", name=" +this.nomTournoi +", niveau=" + this.niveau.denomination() 
				+ ", dateDebut=" + this.dateDebut.toString() + ", dateFin=" + this.dateFin.toString() + ", pays=" + this.pays.getNom() +"]";
				
	}
	
}
