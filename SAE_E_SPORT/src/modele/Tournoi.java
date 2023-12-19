package modele;

import java.sql.Date;

import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import dao.ArbitreJDBC;
import dao.AssocierJDBC;
import dao.ParticiperJDBC;
import dao.PartieJDBC;
import dao.TournoiJDBC;

public class Tournoi {

	private String nomTournoi;
	private Niveau niveau;
	private Date dateDebut;
	private Date dateFin;
	private Pays pays;
	private Statut statut;
	private Compte compte;
	private Equipe vainqueur;
	
	private TournoiJDBC jdbc;
	
	public Tournoi(String nomTournoi, Niveau niveau, Date dateDebut, Date dateFin, Pays pays) throws IllegalArgumentException {
		this.jdbc = new TournoiJDBC();
		if (nomTournoi.trim().length() == 0) {
			throw new IllegalArgumentException("Le nom du tournoi ne doit pas être vide");
		} if (niveau == null) {
			throw new IllegalArgumentException("Le niveau ne doit pas être vide");
		} if (pays == null) {
			throw new IllegalArgumentException("Le pays ne doit pas être vide");
		} if (nomTournoi.length() >= 100) {
			throw new IllegalArgumentException("Le nom du tournoi ne peut dépasser les 100 caractères");
		} if (getTournoiDeNom(nomTournoi) != null) {
			throw new IllegalArgumentException("Un tournoi portant ce nom existe déjà");
		} if (dateDebut.compareTo(new Date(System.currentTimeMillis())) <= 0) {
			throw new IllegalArgumentException("La date de debut doit être supérieure à la date du jour");
		} if (dateDebut.after(dateFin)) {
			throw new IllegalArgumentException("La date de début doit être inférieure ou égale à la date de fin");
		} if (!anneePourSaisonEnCours(dateDebut)) {
			throw new IllegalArgumentException("L'année de la date doit être la même que celle en cours");
		} if (!moinsDeDeuxSemainesEntreDates(dateDebut, dateFin)) {
			throw new IllegalArgumentException("Le tournoi ne peut durer plus de deux semaines");
		} if (!minimum5JoursEntreDates(dateDebut, dateFin)) {
			throw new IllegalArgumentException("Le tournoi doit durer minimum quatre jours");
		} if (existeTournoiEntreDates(dateDebut, dateFin)) {
			throw new IllegalArgumentException("Il existe déjà un tournoi sur ce créneau");
		}
		this.nomTournoi = nomTournoi;
		this.niveau = niveau;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.pays = pays;
		this.compte = null;
		this.vainqueur = null;
		this.statut = Statut.A_VENIR;
	}
	
	public static Tournoi createTournoi(String nomTournoi, Niveau niveau, Date dateDebut, Date dateFin, Pays pays, Statut statut) {
		Tournoi tournoi = new Tournoi();
		tournoi.nomTournoi = nomTournoi;
        tournoi.niveau = niveau;
        tournoi.dateDebut = dateDebut;
        tournoi.dateFin = dateFin;
        tournoi.pays = pays;
        tournoi.compte = null;
        tournoi.vainqueur = null;
        tournoi.statut = statut;
        return tournoi;
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
	
	public Statut getStatut() {
		return this.statut;
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
					&& this.statut == other.statut;
		} 
		return false;
	}
	
	@Override
	public String toString() {
		return "Tournoi [name=" +this.nomTournoi +", niveau=" + this.niveau.denomination() 
				+ ", dateDebut=" + this.dateDebut.toString() + ", dateFin=" + this.dateFin.toString() + ", pays=" + this.pays.denomination() +", status=" + this.statut.denomination() + "]";
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
	
	public boolean minimum5JoursEntreDates(Date dateDebut, Date dateFin) {
		return TimeUnit.DAYS.convert(dateFin.getTime() - dateDebut.getTime(), TimeUnit.MILLISECONDS) + 1 >= 5;
	}
	
	public static boolean estTournoiDisjoint(Date dateDebutT1, Date dateFinT1, Date dateDebutT2, Date dateFinT2) {
		if ((dateDebutT1.compareTo(dateDebutT2) >= 0 && dateDebutT1.compareTo(dateFinT2) < 0) ||
			(dateFinT1.compareTo(dateDebutT2) > 0 && dateFinT1.compareTo(dateFinT2) <= 0) ||
			(dateDebutT2.compareTo(dateDebutT1) >= 0 && dateDebutT2.compareTo(dateFinT1) < 0)) {
			return false;
		}
		return true;
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
	
	public boolean existeTournoiEntreDates(Date dateDebut, Date dateFin) {
		return jdbc.existeTournoiEntreDates(dateDebut, dateFin);
	}
	
	public List<Tournoi> tousLesTournois(){
		return jdbc.getAll();
	}
	
	public void ajouterTournoi(Tournoi tournoi) {
		jdbc.add(tournoi);
	}
	
	public List<Tournoi> getTournoisNiveauStatusNom(String nom, Niveau niveau, Statut status){
		return jdbc.getTournoisNiveauStatusNom(nom, niveau, status);
	}
	
	public void generationPoule(){
		List<Equipe> equipes = new ArrayList<>(); 
		// Récupération de la liste des équipes qui participent au tournoi
		ParticiperJDBC pdb = new ParticiperJDBC();
		equipes = pdb.getAll().stream()
					.filter(e->e.getTournoi().getNomTournoi().equals(this.getNomTournoi()))
					.map(p-> p.getEquipe())
					.collect(Collectors.toList());
		
		// Création des matchs
		PartieJDBC ppdb = new PartieJDBC();
		for(int i = 0; i < equipes.size(); i++) {
			for (int j = i+1; j < equipes.size(); j++) {
				Partie partie = new Partie(Date.valueOf(LocalDate.of(2023, 12, 23)), "12:00", "Poule", equipes.get(i), this);
				partie.setEquipeGagnant(-1);
				partie.setEquipe2(equipes.get(j));
				try {
					ppdb.add(partie);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	
	public void selectionArbitre(Tournoi tournoi) {
		// nombre d'équipes dans le tournoi
		int nbEquipes = (int) new ParticiperJDBC().getAll().stream()
				.filter(participer -> participer.getTournoi().getNomTournoi().equals(tournoi.getNomTournoi()))
				.count();
		
		// selection du nombre d'arbitres en fonction du nombre d'équipes
		int nbArbitres = 0;
		switch(nbEquipes) {
			case 4:
			case 5:
				nbArbitres = 1;
				break;
			case 6:
			case 7:
				nbArbitres = 2;
				break;
			case 8:
				nbArbitres = 3;
				break;
		}
		
		// Selection de tous les arbitres de la base
		ArbitreJDBC ajdbc = new ArbitreJDBC();
		List<Arbitre> arbitres = new ArrayList<>();
		try {
			arbitres = ajdbc.getAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Selection d'arbitres au hasard
		AssocierJDBC assjdbc = new AssocierJDBC();
		List<Arbitre> arbitresTirees = new ArrayList<>();
		Random random = new Random();
		for (int i = 0; i<nbArbitres; i++) {
			int numArbitre = random.nextInt(arbitres.size());
			arbitresTirees.add(arbitres.get(numArbitre));
			arbitres.remove(numArbitre);
			// Ajout dans la base de données la liason arbitre / tournoi
			try {
				Associer ass = new Associer(arbitres.get(numArbitre), tournoi);
				assjdbc.add(new Associer(arbitres.get(numArbitre), tournoi));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void changerStatusTournoi(Tournoi tournoi, Statut status) {
		this.jdbc.changerStatusTournoi(tournoi, status);
	}
}
