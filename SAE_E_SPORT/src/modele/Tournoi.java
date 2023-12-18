package modele;

import java.sql.Date;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import dao.TournoiJDBC;

public class Tournoi {

	private String nomTournoi;
	private Niveau niveau;
	private Date dateDebut;
	private Date dateFin;
	private Pays pays;
	private Status status;
	private Compte compte;
	private Equipe vainqueur;
	
	private TournoiJDBC jdbc;
	
	public Tournoi(String nomTournoi, Niveau niveau, Date dateDebut, Date dateFin, Pays pays) throws IllegalArgumentException {
		if (dateDebut.compareTo(dateFin) > 0) {
			throw new IllegalArgumentException("La date de début doit être inférieure ou égale à la date de fin");
		} 
		this.nomTournoi = nomTournoi;
		this.niveau = niveau;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.pays = pays;
		this.compte = null;
		this.vainqueur = null;
		this.status = Status.A_VENIR;
		this.jdbc = new TournoiJDBC();
	}
	
	public Tournoi() {
		this.jdbc = new TournoiJDBC();
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
	
	public Status getStatus() {
		return this.status;
	}
	
	public Equipe getVainqueur() {
		return vainqueur;
	}

	public void setVainqueur(Equipe vainqueur) {
		this.vainqueur = vainqueur;
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
		return Objects.hash(nomTournoi);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (obj instanceof Tournoi) {
			Tournoi other = (Tournoi) obj;
			return this.compte.equals(other.compte) 
					&& this.dateDebut == other.dateDebut
					&& this.dateFin == other.dateFin 
					&& this.niveau.equals(other.niveau) 
					&& this.nomTournoi.equals(other.nomTournoi)
					&& this.pays.equals(pays) 
					&& this.vainqueur.equals(other.vainqueur)
					&& this.status == other.status;
		} 
		return false;
	}
	
	@Override
	public String toString() {
		return "Tournoi [name=" +this.nomTournoi +", niveau=" + this.niveau.denomination() 
				+ ", dateDebut=" + this.dateDebut.toString() + ", dateFin=" + this.dateFin.toString() + ", pays=" + this.pays.denomination() +", status=" + this.status.denomination() + "]";
	}
	
	// ======================= //
	// ==== Partie modele ==== //
	// ======================= //
	
	public boolean moinsDeDeuxSemainesEntreDates(Date dateDebut, Date dateFin) {
		return Math.abs(dateFin.getTime() - dateDebut.getTime()) < 1.21e+9; 
	}
	
	public boolean anneePourSaisonEnCours(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR) == LocalDate.now().getYear();
	}
	
	public static boolean estTournoiDisjoint(Date dateDebutT1, Date dateFinT1, Date dateDebutT2, Date dateFinT2) {
		if ((dateDebutT1.compareTo(dateDebutT2) >= 0 && dateDebutT1.compareTo(dateFinT2) < 0) ||
			(dateFinT1.compareTo(dateDebutT2) > 0 && dateFinT1.compareTo(dateFinT2) <= 0) ||
			(dateDebutT2.compareTo(dateDebutT1) >= 0 && dateDebutT2.compareTo(dateFinT1) < 0)) {
			return false;
		}
		return true;
	}
	
	public static Status etatTournoi(Tournoi tournoi) {
		if (tournoi.getDateFin().compareTo(new Date(System.currentTimeMillis())) < 0) {
			return Status.TERMINE;
		} else if (tournoi.getDateDebut().compareTo(new Date(System.currentTimeMillis())) < 0) {
			return Status.EN_COURS;
		}
		return Status.A_VENIR;
	}
	
	public int getDureeTournoi() {
		int duree = 0;
		long differenceInMilliseconds = Math.abs(this.dateFin.getTime() - this.dateDebut.getTime());
	    duree =  (int) TimeUnit.DAYS.convert(differenceInMilliseconds, TimeUnit.MILLISECONDS);
	    return duree;
	}
	
	// ===================== //
	// ==== Partie JDBC ==== //
	// ===================== //
	
	public Tournoi getTournoiDeNom(String nomTournoi) {
		return jdbc.getById(nomTournoi).orElse(null);
	}
	
	public boolean existeTournoiEntreDates(Date dateDebut, Date dateFin) throws SQLException {
		return jdbc.existeTournoiEntreDates(dateDebut, dateFin);
	}
	
	public List<Tournoi> tousLesTournois(){
		return jdbc.getAll();
	}
	
	public void ajouterTournoi(Tournoi tournoi) throws Exception {
		jdbc.add(tournoi);
	}
	
	public List<Tournoi> getTournoisNiveauStatusNom(String nom, Niveau niveau, Status status){
		return jdbc.getTournoisNiveauStatusNom(nom, niveau, status);
	}
	
}
