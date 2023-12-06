package modele;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import dao.EquipeJDBC;
import dao.JouerJDBC;
import dao.ParticiperJDBC;
import dao.PartieJDBC;
import dao.TournoiJDBC;

public class ModelePoule {

	public Object[][] matchesParTournoi(Tournoi t) throws Exception {
		PartieJDBC pdb = new PartieJDBC();
		List<Partie> parties = pdb.getAll().stream()
								.filter(e -> e.getTournoi().getIdTournoi() == t.getIdTournoi())
								.sorted((p1,p2) -> p1.getDate().compareTo(p2.getDate()))
								.collect(Collectors.toList());
		
		Object[][] datas = new Object[parties.size()][4];
		int i=0;
		for (Partie p : parties) {
			datas[i][0] = i+1;
			datas[i][1] = p.getEquipe1().getNom();
			datas[i][2] = p.getEquipe2().getNom();
			datas[i][3] = p.getDate();
			i++;
		}
		return datas;
	}
	
	public Object[][] classement (Tournoi t) throws Exception {
		List<Participer> participers = (new ParticiperJDBC()).getAll().stream()
										.filter(e->e.getTournoi().getIdTournoi() == t.getIdTournoi())
										.collect(Collectors.toList());
		Object[][] datas = new Object[participers.size()][4];
		int i=0;
		for (Participer p : participers) {
			datas[i][0] = i+1;
			datas[i][1] = p.getEquipe().getNom();
			datas[i][2] = p.getNbPointsGagnes();
			datas[i][3] = p.getNbMatchsJoues();
			i++;
		}
		return datas;
	}
	
}
