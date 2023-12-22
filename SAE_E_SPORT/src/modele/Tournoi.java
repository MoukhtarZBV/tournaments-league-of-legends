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
import dao.CompteJDBC;
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
		} 
		if (niveau == null) {
			throw new IllegalArgumentException("Le niveau ne doit pas être vide");
		} 
		if (pays == null) {
			throw new IllegalArgumentException("Le pays ne doit pas être vide");
		} 
		if (nomTournoi.length() >= 100) {
			throw new IllegalArgumentException("Le nom du tournoi ne peut dépasser les 100 caractères");
		} 
		if (getTournoiDeNom(nomTournoi) != null) {
			throw new IllegalArgumentException("Un tournoi portant ce nom existe déjà");
		} 
		if (dateDebut.compareTo(new Date(System.currentTimeMillis())) <= 0) {
			throw new IllegalArgumentException("La date de debut doit être supérieure à la date du jour");
		} 
		if (dateDebut.after(dateFin)) {
			throw new IllegalArgumentException("La date de début doit être inférieure à la date de fin");
		} 
		if (!anneePourSaisonEnCours(dateDebut)) {
			throw new IllegalArgumentException("L'année de la date doit être la même que celle en cours");
		} 
		if (!moinsDeDeuxSemainesEntreDates(dateDebut, dateFin)) {
			throw new IllegalArgumentException("Le tournoi ne peut durer plus de deux semaines");
		} if (!minimum5JoursEntreDates(dateDebut, dateFin)) {
			throw new IllegalArgumentException("Le tournoi doit durer minimum cinq jours");
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
		this.statut = Statut.ATTENTE_EQUIPES;
	}
	
	public static Tournoi createTournoi(String nomTournoi, Niveau niveau, Date dateDebut, Date dateFin, Pays pays, Statut statut, Optional<Equipe> vainqueur, Optional<Compte> compte) {
		Tournoi tournoi = new Tournoi();
		tournoi.nomTournoi = nomTournoi;
        tournoi.niveau = niveau;
        tournoi.dateDebut = dateDebut;
        tournoi.dateFin = dateFin;
        tournoi.pays = pays;
        tournoi.vainqueur = vainqueur.orElse(null);
        tournoi.compte = compte.orElse(null);
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
	
	public void setStatut(Statut statut) {
		this.statut = statut;
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
				+ ", dateDebut=" + this.dateDebut.toString() + ", dateFin=" + this.dateFin.toString() + ", pays=" + this.pays.denomination() 
				+", status=" + this.statut.denomination() + ", equipe vainqueur = " + this.vainqueur + "]";
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
	
	// générer une poule lorsque un tournoi est ouvert
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
		
		int equipeSize = equipes.size();
		
		// nombre matchs totals
		int nbMatchsTotals = nombreMatchs(equipeSize);
		// duree total des matchs
		int joursMatchs = this.dureePoule(this.getDureeTournoi());
		// nombre de matchs restant
		int matchsRestant = nbMatchsTotals % joursMatchs;
		// nombre de matchs par jour
		int nbMatchsParJour = nbMatchsTotals/joursMatchs;
		// le trou entre des matchs
		int trouHeures = trouHeure(nbMatchsParJour);
		// heure commence
		int heure = 10;
		// date commence 
		LocalDate date = this.dateDebut.toLocalDate();
		// compteur pour les matchs par jour
		int cmpMatchs = 0;
		nbMatchsParJour = verifierExtraMatchs(matchsRestant, nbMatchsParJour);
		for (int cmp = 0, initial = 1; cmp<nbMatchsTotals ;initial++) {
			for (int i = 0, j = initial ; i<equipeSize && cmp<nbMatchsTotals ; i++, j++, cmp++, heure+=trouHeures) {
				cmpMatchs ++;
				j %= equipeSize;
				createMatch(equipes, ppdb, heure, date, i, j);
				if (cmpMatchs==nbMatchsParJour) {
					heure = 10-trouHeures;
					date = date.plusDays(1);
					matchsRestant--;
					if (matchsRestant==0) {
						nbMatchsParJour--;
					}
					cmpMatchs = 0;
				}
			}
			initial %= equipeSize;
		}
		
	}

	// creer un match et ajouter dans la base de données 
	private void createMatch(List<Equipe> equipes, PartieJDBC ppdb, int heure, LocalDate date, int i, int j) {
		Partie partie = new Partie(Date.valueOf(date), String.format("%02d", heure)+":00", "Poule", equipes.get(i), this);
		partie.setEquipeGagnant(-1);
		partie.setEquipe2(equipes.get(j));
		try {
			ppdb.add(partie);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	// verifier s'il existe des extra matchs
	private int verifierExtraMatchs(int matchsRestant, int nbMatchsParJour) {
		if (matchsRestant>0) {
			nbMatchsParJour++;
		}
		return nbMatchsParJour;
	}
	
	// la durée totale pour faire des matchs
	private int dureePoule(int dureeTournoi) {
		int duree = 4;
		if (dureeTournoi==7) {
			duree = 5;
		}else if (dureeTournoi>7) {
			duree = 7;
		}
		return duree;
	}
	
	// le trou entre deux matchs
	// dépendre de matchs par jour
	// plus de match, moins de temps et vice versa
	private int trouHeure(int nbMatchs) {
		int gap = 1;
		if (nbMatchs<=2) gap = 3;
		else if (nbMatchs<=4) gap = 2;
		return gap;
	}
	
	// nombre total des matchs pour un nombre d'équipe donné
	private int nombreMatchs(int nbEquipes) {
		return nbEquipes*(nbEquipes-1)/2;
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
		
		// Ajout d'un compte à la base de donnée pour le tournoi
		Compte c = new Compte();
		int idCompte = -1;
		try {
			idCompte = CompteJDBC.getNextValueSequence();
			c.ajouterCompte(new Compte(idCompte,tournoi.getNomTournoi().replace(" ", ""),Compte.genererPassword(15),TypeCompte.ARBITRE));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		for (int i = 0; i<nbArbitres; i++) {
			int numArbitre = random.nextInt(arbitres.size());
			arbitresTirees.add(arbitres.get(numArbitre));
			
			// Association du compte aux arbitres
			try {
				Arbitre arb = arbitres.get(numArbitre);
				arb.setIdCompte(idCompte);
				ajdbc.update(arb);
			} catch (Exception e2) {
				e2.printStackTrace();
			}

			// Ajout dans la base de données la liason arbitre / tournoi
			try {
				Associer ass = new Associer(arbitres.get(numArbitre), tournoi);
				assjdbc.add(new Associer(arbitres.get(numArbitre), tournoi));
			} catch (Exception e) {
				e.printStackTrace();
			}
			arbitres.remove(numArbitre);
		}
	}
	
	public void changerStatusTournoi(Tournoi tournoi, Statut status) {
		this.jdbc.changerStatusTournoi(tournoi, status);
	}
	
	public void setVainqueurTournoi(Tournoi tournoi, Equipe equipe) {
		this.jdbc.setVainqueurTournoi(tournoi, equipe);
	}
	
	public List<Equipe> getEquipesFinale(Tournoi tournoi){
		return this.jdbc.getEquipesFinale(tournoi);
	}
}
