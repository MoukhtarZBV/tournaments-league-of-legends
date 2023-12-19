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
import java.util.TreeMap;
import java.util.stream.Collectors;

import dao.ParticiperJDBC;
import dao.PartieJDBC;

import Images.Images;

public class ModelePoule {

	private List<Partie> parties;
	private Map<String, Participer> participations;
	
	public ModelePoule(Tournoi t) throws Exception {
		this.parties = this.partiesParTournoi(t);
		this.participations = this.participerParTournoi(t);
		System.out.println(this.parties);
		System.out.println(this.participations);
	}
	
	private List<Partie> partiesParTournoi(Tournoi t) throws Exception{
		PartieJDBC pdb = new PartieJDBC();
		List<Partie> parties = pdb.getAll().stream()
								.filter(e -> e.getTournoi().getNomTournoi().equals(t.getNomTournoi()))
								.sorted((p1,p2) -> p1.getDate().compareTo(p2.getDate()))
								.collect(Collectors.toList());
		return parties;
	}
	
	private Map<String,Participer> participerParTournoi(Tournoi t) throws Exception {
		ParticiperJDBC pdb = new ParticiperJDBC(); 
		Map<String, Participer> map =  pdb.getAll().stream()
										.filter(e->e.getTournoi().getNomTournoi().equals(t.getNomTournoi()))
										.collect(Collectors.toMap(e->e.getEquipe().getNom(), e->e));
		return map;
	}
	
	public void updateGagnant(int row, int gagnant) {
		if (gagnant == 1 || gagnant == 2) {
			Partie p = this.parties.get(row);
			
			int eGagne = p.getEquipeGagnant();
			if (eGagne != -1) {		
				String eq;
				if (eGagne == 1) eq = p.getEquipe1().getNom();
				else eq = p.getEquipe2().getNom();
				
				// Recalculer les points et les matches gagnés 
				// pas sur +- combien
				this.participations.get(eq).setNbPointsGagnes(this.participations.get(eq).getNbPointsGagnes()-10);
				this.participations.get(eq).setNbMatchsGagnes(this.participations.get(eq).getNbMatchsGagnes()-1);
				if(eGagne == gagnant) {
					p.setEquipeGagnant(-1);
				}
			}
			if (eGagne != gagnant || eGagne == -1) {
				p.setEquipeGagnant(gagnant);
				Participer participer = this.participations.get(gagnant == 1 ? p.getEquipe1().getNom() : p.getEquipe2().getNom());
				participer.setNbPointsGagnes(participer.getNbPointsGagnes()+10);
				participer.setNbMatchsGagnes(participer.getNbMatchsGagnes()+1);
			} 
		}
	}
	
	public Object[][] matches() throws Exception {
		Object[][] datas = new Object[this.parties.size()][6];
		int i=0;
		String trophyWin = Images.TROPHY_WIN;
		String trophy = Images.TROPHY;
		for (Partie p : this.parties) {
			datas[i][0] = i+1;
			datas[i][1] = p.getEquipe1().getNom();
			datas[i][2] = p.getEquipe2().getNom();
			datas[i][3] = (new SimpleDateFormat("dd/MM/yyyy").format(p.getDate()))+"  "+p.getHeure();
			datas[i][4] = p.getEquipeGagnant()==1 ? trophyWin : trophy;
			datas[i][5] = p.getEquipeGagnant()==2 ? trophyWin : trophy;
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
			datas[i][3] = entry.getValue().getNbMatchsJoues();
			i++;
		}
		return datas;
	}
	
	public boolean enregistrerResultat () throws Exception {
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
	
}
