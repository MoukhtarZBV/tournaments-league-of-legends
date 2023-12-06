package modele;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import dao.TournoiJDBC;

public class Tournoi {

	private int idTournoi;
	private String nomTournoi;
	private Niveau niveau;
	private Date dateDebut;
	private Date dateFin;
	private Pays pays;
	private Compte compte;
	private Equipe vainqueur;
	
	private TournoiJDBC jdbc;
	
	public Tournoi(int id, String nomTournoi, Niveau niveau, Date dateDebut, Date dateFin, Pays pays) throws IllegalArgumentException {
		if (dateDebut.compareTo(dateFin) > 0) {
			throw new IllegalArgumentException("La date de début doit être inférieure ou égale à la date de fin");
		} 
		this.idTournoi = id;
		this.nomTournoi = nomTournoi;
		this.niveau = niveau;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.pays = pays;
		this.compte = null;
		this.vainqueur = null;
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
				+ ", dateDebut=" + this.dateDebut.toString() + ", dateFin=" + this.dateFin.toString() + ", pays=" + this.pays.denomination() +"]";
	}
	
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
	
	public List<Tournoi> getTournoisNiveauStatusNom(String nom, Niveau niveau, Status status){
		if (niveau == null && status == null && nom == "") {
			return jdbc.getAll();
		} else if (niveau == null && status == null) {
			return jdbc.getAll().stream().filter(tournoi -> tournoi.getNomTournoi().contains(nom)).collect(Collectors.toList());
		} else if (niveau != null && status == null) {
			return jdbc.getAll().stream().filter(tournoi -> tournoi.getNomTournoi().contains(nom)).filter(tournoi -> tournoi.getNiveau() == niveau).collect(Collectors.toList());
		} else if (niveau == null && status != null) {
			return jdbc.getAll().stream().filter(tournoi -> tournoi.getNomTournoi().contains(nom)).filter(tournoi -> Tournoi.etatTournoi(tournoi) == status).collect(Collectors.toList());
		}
		return jdbc.getAll().stream().filter(tournoi -> tournoi.getNomTournoi().contains(nom)).filter(tournoi -> Tournoi.etatTournoi(tournoi) == status && tournoi.getNiveau() == niveau).collect(Collectors.toList());
	}

	public Tournoi getByDateDebut(Date dateDebut) throws Exception{
		return jdbc.getByDateDebut(dateDebut).get();
	}
}
