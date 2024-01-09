package modele;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import dao.EquipeJDBC;
import dao.ParticiperJDBC;
import dao.PartieJDBC;

import Images.ImagesIcons;

public class ModelePoule {

	private List<Partie> parties;
	private Map<String, Participer> participations;
	private Tournoi tournoi;
	
	public ModelePoule(Tournoi tournoi) throws Exception {
		this.parties = this.partiesParTournoi(tournoi);
		this.participations = this.participerParTournoi(tournoi);
		this.tournoi = tournoi;
	}
	
	private List<Partie> partiesParTournoi(Tournoi t) throws Exception{
		return new PartieJDBC().getAll().stream()
								.filter(e -> e.getTournoi().getNomTournoi().equals(t.getNomTournoi()) && e.getDeroulement().equals("Poule"))
								.sorted((p1,p2) -> p1.getDate().compareTo(p2.getDate()))
								.collect(Collectors.toList());
	}
	
	private Map<String,Participer> participerParTournoi(Tournoi t) throws Exception {
		return new ParticiperJDBC().getAll().stream()
								.filter(e->e.getTournoi().getNomTournoi().equals(t.getNomTournoi()))
								.collect(Collectors.toMap(e->e.getEquipe().getNom(), e->e));
	}
	
	public void updateGagnant(int indicePartie, int numeroEquipeGagnante) {
			Partie partie = this.parties.get(indicePartie);
			Equipe equipeGagnante = partie.getEquipeGagnante();
			Equipe nouvelleEquipe = numeroEquipeGagnante == 1 ? partie.getEquipeUne() : partie.getEquipeDeux();
			String nomNouvelleEquipe = nouvelleEquipe.getNom();
			if (equipeGagnante != null) {
				if (equipeGagnante != nouvelleEquipe) {
					String nomEquipeGagnante = equipeGagnante.getNom();
					Participer participationEquipeGagnante = this.participations.get(nomEquipeGagnante);
					participationEquipeGagnante.setNbPointsPouleGagnes(participationEquipeGagnante.getNbPointsPouleGagnes() - 2);
					participationEquipeGagnante.setNbMatchsGagnes(participationEquipeGagnante.getNbMatchsGagnes() - 1);
					Participer participationNouvelleEquipeGagnante = this.participations.get(nomNouvelleEquipe);
					participationNouvelleEquipeGagnante.setNbPointsPouleGagnes(participationNouvelleEquipeGagnante.getNbPointsPouleGagnes() + 2);
					participationNouvelleEquipeGagnante.setNbMatchsGagnes(participationNouvelleEquipeGagnante.getNbMatchsGagnes() + 1);
				}
			} else {
				String nomEquipePerdante = numeroEquipeGagnante == 1 ? partie.getEquipeDeux().getNom() : partie.getEquipeUne().getNom();
				Participer participationNouvelleEquipeGagnante = this.participations.get(nomNouvelleEquipe);
				participationNouvelleEquipeGagnante.setNbPointsPouleGagnes(participationNouvelleEquipeGagnante.getNbPointsPouleGagnes() + 3);
				participationNouvelleEquipeGagnante.setNbMatchsGagnes(participationNouvelleEquipeGagnante.getNbMatchsGagnes() + 1);
				participationNouvelleEquipeGagnante.setNbMatchsJoues(participationNouvelleEquipeGagnante.getNbMatchsJoues() + 1);
				Participer participationEquipePerdante = this.participations.get(nomEquipePerdante);
				participationEquipePerdante.setNbPointsPouleGagnes(participationEquipePerdante.getNbPointsPouleGagnes() + 1);
				participationEquipePerdante.setNbMatchsJoues(participationEquipePerdante.getNbMatchsJoues() + 1);
			}
			partie.setEquipeGagnante(nouvelleEquipe);
	}
	
	public Object[][] matches() throws Exception {
		Object[][] datas = new Object[this.parties.size()][6];
		int i=0;
		String trophyWin = ImagesIcons.TROPHY_WIN;
		String trophy = ImagesIcons.TROPHY;
		for (Partie p : this.parties) {
			datas[i][0] = i+1;
			datas[i][1] = p.getEquipeUne().getNom();
			datas[i][2] = p.getEquipeDeux().getNom();
			datas[i][3] = (new SimpleDateFormat("dd/MM/yyyy").format(p.getDate()))+"  "+p.getHeure();
			if (p.getEquipeGagnante() != null) {
				datas[i][4] = p.getEquipeGagnante().getIdEquipe() == p.getEquipeUne().getIdEquipe() ? trophyWin : trophy;
				datas[i][5] = p.getEquipeGagnante().getIdEquipe() == p.getEquipeDeux().getIdEquipe() ? trophyWin : trophy;
			} else {
				datas[i][4] = trophy;
				datas[i][5] = trophy;
			}
			i++;
		}
		return datas;
	} 
	
	public Object[][] classement () {
		List<Map.Entry<String, Participer>> sortedEntries = new ArrayList<>(this.participations.entrySet());

        // Trie les participations par getNbPointsGagnes()
        Collections.sort(sortedEntries, Comparator.comparing(entry -> entry.getValue().getNbPointsPouleGagnes(), Comparator.reverseOrder()));

        Object[][] datas = new Object[sortedEntries.size()][4];
		int i=0;
		for (Map.Entry<String, Participer> entry : sortedEntries) {
			datas[i][0] = i+1;
			datas[i][1] = entry.getKey();
			datas[i][2] = entry.getValue().getNbPointsPouleGagnes();
			datas[i][3] = entry.getValue().getNbMatchsGagnes();
			i++;
		}
		return datas;
	}
	
	public boolean tousLesMatchsJouees() {
		boolean res = true;
		for (Partie p : this.parties) {
			if (p.getEquipeGagnante() == null) {
				res = false;
			}
		}
		return res;
	}
	
	public boolean enregistrerResultat() {
		boolean res = false;
		PartieJDBC pdb = new PartieJDBC();
		ParticiperJDBC partdb = new ParticiperJDBC();
		for (Partie p : this.parties) {
			res = pdb.update(p);
		}
		for (Participer p : this.participations.values()) {
			res &= partdb.update(p);
		}
		return res;
	}
	
	public void changerStatusEnFinale(Tournoi tournoi) {
		this.tournoi.changerStatutTournoi(tournoi, Statut.FINALE);
	}
	
	public void creerFinale(Tournoi tournoi) {
		// Liste des participations triés par points gagnés puis par world ranking
		List<Participer> participationsOrdrePoints = participations.values().stream()
				.sorted(Comparator.comparingInt(Participer::getNbPointsPouleGagnes).reversed()
		                .thenComparingInt(p -> p.getEquipe().getRang()))
				.collect(Collectors.toList());
		
		// Mise à jour du classement et des points gagnés par chaque équipe du tournoi
		Participer participerBDD = new Participer();
		int nbPointsTournoi;
		float basePoints = Niveau.multiplicateurNiveau(tournoi.getNiveau());
		for (int classement = 0; classement < participationsOrdrePoints.size(); classement++) {
			Participer participation = participationsOrdrePoints.get(classement);
			participation.setClassement(classement + 1);
			nbPointsTournoi = ((participation.getNbMatchsJoues() * 25) + ((participation.getNbMatchsJoues() - participation.getNbMatchsJoues()) * 15)) * 10;
			nbPointsTournoi = (int) (nbPointsTournoi * basePoints);
			participation.setNbPointsTournoiGagnes(nbPointsTournoi);;
			participerBDD.mettreAJourParticipation(participation);
		}
		
		// Mise à jour des points finaux du 3ème et 4ème du tournoi
		participationsOrdrePoints.get(2).setNbPointsTournoiGagnes(participationsOrdrePoints.get(2).getNbPointsTournoiGagnes() + 50);
		participationsOrdrePoints.get(3).setNbPointsTournoiGagnes(participationsOrdrePoints.get(3).getNbPointsTournoiGagnes() + 15);
		
		// Choix d'une heure pour la finale 
		int heure = new Random().nextInt(8 + 1)  + 10;
		Partie finale = new Partie(tournoi.getDateFin(), String.format("%02d", heure)+":00", "Finale", tournoi);
		finale.setEquipeUne(participationsOrdrePoints.get(0).getEquipe());
		finale.setEquipeDeux(participationsOrdrePoints.get(1).getEquipe());
		new PartieJDBC().add(finale);
	}
}
