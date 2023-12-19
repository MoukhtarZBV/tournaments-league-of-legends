package TestModele;

import java.sql.Date;
import java.time.LocalDate;

import modele.Partie;

public class TestTournoi {

	public static void main (String[] args) {
		// nombre matchs totals
		int equipesSize = 8;
		int nbMatchsTotals = nombreMatchs(equipesSize);
		// duree des matchs
		int joursMatchs = dureePoule(14);
		int matchsRestant = nbMatchsTotals % joursMatchs;
		int nbMatchsParJour = nbMatchsTotals/joursMatchs;
		int trouHeures = trouHeure(nbMatchsParJour);
		int nbMatchsParJourTmp = nbMatchsParJour +1;
		if (matchsRestant == 0) {
			matchsRestant = -1;
			nbMatchsParJourTmp--;
		}
		// nombre de matchs par jour
		// le trou entre des matchs
		// heure commence
		int heure = 10;
		// date commence 
		LocalDate date = LocalDate.of(2023, 12, 30);
		
		// compteur pour les matchs par jour
		int cmpMatchs = 0;
		
		System.out.println("Matchs totals : "+nbMatchsTotals+ ", jours Matchs : "+joursMatchs+ ", nbMatchs/Jour : " + nbMatchsParJour + ", trouHeure : "+trouHeures);
		System.out.println("Matchs restant : "+matchsRestant+"\n");
		for (int cmp = 0, initial = 1 ; cmp<nbMatchsTotals ; initial++) {
			for (int i = 0, j = initial ; i<equipesSize && cmp<nbMatchsTotals ; i++, j++, cmp++, heure+=trouHeures) {
				cmpMatchs ++;
				j %= equipesSize;
				
				System.out.println("cmp : "+cmp);
				System.out.println("cmpt matchs : "+cmpMatchs);
				System.out.println("initial : " + initial);
				System.out.println("date : "+date.toString()+", hh : "+heure);
				System.out.println("e1 : "+i+", e2 : "+j);
				System.out.println("NTmp : "+nbMatchsParJourTmp);
				System.out.println();
				
				if (cmpMatchs==nbMatchsParJourTmp) {
					heure = 10;
					date = date.plusDays(1);
					
					cmpMatchs = 0;
					matchsRestant --;
					if (matchsRestant == 0) {
						nbMatchsParJourTmp --;
					}
				}
			}
		}
	}
	
	private static int dureePoule(int dureeTournoi) {
		int duree = 4;
		if (dureeTournoi==7) {
			duree = 5;
		}else if (dureeTournoi>7) {
			duree = 7;
		}
		return duree;
	}
	
	private static int trouHeure(int nbMatchs) {
		int gap = 1;
		if (nbMatchs<=2) gap = 3;
		else if (nbMatchs<=4) gap = 2;
		return gap;
	}
	
	private static int nombreMatchs(int nbEquipes) {
		return nbEquipes*(nbEquipes-1)/2;
	}
	
	
}
