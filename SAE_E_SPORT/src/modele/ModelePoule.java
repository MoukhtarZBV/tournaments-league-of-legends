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
					this.participations.get(nomEquipeGagnante).setNbPointsGagnes(this.participations.get(nomEquipeGagnante).getNbPointsGagnes() - (int) ((25 - 15) * Niveau.multiplicateurNiveau(tournoi.getNiveau())));
					this.participations.get(nomEquipeGagnante).setNbMatchsGagnes(this.participations.get(nomEquipeGagnante).getNbMatchsGagnes() - 1);
					this.participations.get(nomNouvelleEquipe).setNbPointsGagnes(this.participations.get(nomNouvelleEquipe).getNbPointsGagnes() + (int) ((25 - 15) * Niveau.multiplicateurNiveau(tournoi.getNiveau())));
					this.participations.get(nomNouvelleEquipe).setNbMatchsGagnes(this.participations.get(nomNouvelleEquipe).getNbMatchsGagnes() + 1);
				}
			} else {
				String nomEquipePerdante = numeroEquipeGagnante == 1 ? partie.getEquipeDeux().getNom() : partie.getEquipeUne().getNom();
				this.participations.get(nomNouvelleEquipe).setNbPointsGagnes(this.participations.get(nomNouvelleEquipe).getNbPointsGagnes() + (int) (25 * Niveau.multiplicateurNiveau(tournoi.getNiveau())));
				this.participations.get(nomNouvelleEquipe).setNbMatchsGagnes(this.participations.get(nomNouvelleEquipe).getNbMatchsGagnes() + 1);
				this.participations.get(nomEquipePerdante).setNbPointsGagnes(this.participations.get(nomEquipePerdante).getNbPointsGagnes() + (int) (15 * Niveau.multiplicateurNiveau(tournoi.getNiveau())));
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
        Collections.sort(sortedEntries, Comparator.comparing(entry -> entry.getValue().getNbPointsGagnes(), Comparator.reverseOrder()));

        Object[][] datas = new Object[sortedEntries.size()][4];
		int i=0;
		for (Map.Entry<String, Participer> entry : sortedEntries) {
			datas[i][0] = i+1;
			datas[i][1] = entry.getKey();
			datas[i][2] = entry.getValue().getNbPointsGagnes();
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
		List<Participer> participationsOrdrePoints = participations.values().stream()
				.sorted((p1, p2) -> Integer.compare(p1.getNbPointsGagnes(), p2.getNbPointsGagnes()))
				.collect(Collectors.toList());
		Collections.reverse(participationsOrdrePoints);
		
		List<Equipe> equipesFinale = new ArrayList<>();
		// Choix par rapport au world ranking en cas d'égalité
		if (participationsOrdrePoints.get(1).getNbPointsGagnes() == participationsOrdrePoints.get(2).getNbPointsGagnes()) {
			equipesFinale = choixEquipesFinaleEgalite(participationsOrdrePoints);
		} else {
			equipesFinale.add(participationsOrdrePoints.get(0).getEquipe());
			equipesFinale.add(participationsOrdrePoints.get(1).getEquipe());
		}
		
		int heure = new Random().nextInt(8 + 1)  + 10;
		Partie finale = new Partie(tournoi.getDateFin(), String.format("%02d", heure)+":00", "Finale", tournoi);
		finale.setEquipeUne(equipesFinale.get(0));
		finale.setEquipeDeux(equipesFinale.get(1));
		new PartieJDBC().add(finale);
	}

	private List<Equipe> choixEquipesFinaleEgalite(List<Participer> participationsOrdrePoints) {
		List<Equipe> equipesEnEgalite = new ArrayList<>();

		int indiceEquipe = 1;
		while (indiceEquipe + 1 < participationsOrdrePoints.size() && participationsOrdrePoints.get(indiceEquipe).getNbPointsGagnes() == participationsOrdrePoints.get(indiceEquipe + 1).getNbPointsGagnes()) {
			equipesEnEgalite.add(participationsOrdrePoints.get(indiceEquipe).getEquipe());
			indiceEquipe++;
		}
		equipesEnEgalite.add(participationsOrdrePoints.get(indiceEquipe).getEquipe());
		
		// Si la première équipe est également en égalité avec le reste		
		boolean premiereEquipeEgalite = false;
		if (participationsOrdrePoints.get(0).getNbPointsGagnes() == participationsOrdrePoints.get(1).getNbPointsGagnes()) {
			equipesEnEgalite.add(participationsOrdrePoints.get(0).getEquipe());
			premiereEquipeEgalite = true;
		}
		
		List<Equipe> equipesEnEgaliteTriRang = equipesEnEgalite.stream()
				.sorted((e1, e2) -> Integer.compare(e1.getRang(), e2.getRang()))
				.collect(Collectors.collectingAndThen(
		        Collectors.toList(),
		        result -> {
		            Collections.reverse(result);
		            return result;
		        }));
		
		List<Equipe> equipesFinale = new ArrayList<>();
		if (premiereEquipeEgalite) {
			equipesFinale.add(equipesEnEgaliteTriRang.get(0));
			equipesFinale.add(equipesEnEgaliteTriRang.get(1));
		} else {
			equipesFinale.add(participationsOrdrePoints.get(0).getEquipe());
			equipesFinale.add(equipesEnEgaliteTriRang.get(0));
		}
		return equipesFinale;
	}
}
