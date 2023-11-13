package entites;

import java.time.LocalDate;
import java.time.LocalTime;

public class Partie {

	private LocalDate date;
	private LocalTime heure;
	private String deroulement;
	private Equipe equipe1;
	private Equipe equipe2;
	private Tournoi tournoi;
	
	public Partie(LocalDate date, LocalTime heure, String deroulement, Equipe equipe1, Equipe equipe2, Tournoi tournoi) {
		this.date = date;
		this.heure = heure;
		this.deroulement = deroulement;
		this.equipe1 = equipe1;
		this.equipe2 = equipe2;
		this.tournoi = tournoi;
	}

	public LocalDate getDate() {
		return date;
	}

	public LocalTime getHeure() {
		return heure;
	}

	public String getDeroulement() {
		return deroulement;
	}

	public Equipe getEquipe1() {
		return equipe1;
	}

	public Equipe getEquipe2() {
		return equipe2;
	}
	
	public Tournoi getTournoi() {
		return this.tournoi;
	}
	
}
