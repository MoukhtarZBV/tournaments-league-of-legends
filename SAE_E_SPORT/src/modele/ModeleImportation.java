package modele;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import Fonctions.LireCSV;
import dao.ConnectionJDBC;
import dao.EquipeJDBC;
import dao.JoueurJDBC;
import dao.ParticiperJDBC;
import ihm.Utilitaires;

public class ModeleImportation {

	private List<Equipe> equipes;
	
	public enum EtatEquipe{
		OK, MEME_EQUIPE, MAL_COMPOSITION, JOUEUR_EXISTE;
	}
	
	public ModeleImportation() {
		this.equipes = new LinkedList<>();
	}
	
	public boolean fichierCSVconcerneTournoi(String chemin, Tournoi tournoi) throws IOException {
		String line;
    	BufferedReader br;
		br = new BufferedReader(new FileReader(chemin));
		br.readLine();
		if ((line = br.readLine()) != null) {
		    String[] values = line.split(",");
			if (!(values[0].equals(tournoi.getNomTournoi()) || values[1].equals(Utilitaires.formaterDate(tournoi.getDateDebut())) || values[2].equals(Utilitaires.formaterDate(tournoi.getDateFin())))) {
				br.close();
				return false;
		    }
		}
		br.close();
		return true;
	}
	
	public boolean fichierCSVNombreEquipes(String chemin, Tournoi tournoi) throws IOException {
		String line;
    	BufferedReader br;
		br = new BufferedReader(new FileReader(chemin));
		br.readLine();
		List<String> lines = new LinkedList<>();
		while ((line = br.readLine()) != null) {
			lines.add(line);
		}
		if (!(lines.size()>=20 && lines.size()<=32)) {
			System.out.println(lines.size());
			br.close();
			return false;
		}
		br.close();
		return true;
	}
	
	public void importerEquipesJoueurs(String chemin) {
		this.equipes.clear();
        List<String[]> data = new LinkedList<>();
    	
        LireCSV reader = new LireCSV(chemin);
        data = reader.getData();
        
        Equipe equipeBDD = new Equipe();
    	for (int ligne = 1; ligne<data.size(); ligne += 5) {		
			// Creer l'equipe et lui attribuer les joueurs
    		Equipe equipe = equipeBDD.getEquipeParNom(data.get(ligne)[4]);
    		if (equipe != null) {
    			this.equipes.add(equipe);
    		} else {
    			equipe = new Equipe(EquipeJDBC.getNextValueSequence(), data.get(ligne)[4], Integer.parseInt(data.get(ligne)[5]), Pays.getPays(data.get(ligne)[6]));
    			this.equipes.add(equipe);
				for (int j = ligne; j<ligne+5; j++) {
					Joueur joueur = new Joueur(JoueurJDBC.getNextValueSequence(), data.get(j)[7], equipe);
					equipe.ajouterJoueur(joueur);
				}
    		}
    	}
	}
	
	public Object[][] getEquipesJoueurs(){
		Object[][] datas = new Object[6][this.equipes.size()];
		int i = 0;
		for (Equipe e : this.equipes) {
			datas[0][i] = e.getNom();
			int j = 1;
			for (Joueur joueur : e.getJoueurs()) {
				datas[j][i] = joueur.getPseudo();
				j++;
			}
			i++;
		}
		return datas;
	}
	
	public EtatEquipe verifierEquipe() {
		Joueur joueurBDD = new Joueur();
		Equipe equipeBDD = new Equipe();
		try {
			List<Joueur> joueurs = joueurBDD.getTousLesJoueurs();	
			List<Equipe> allEquipes = equipeBDD.getToutesLesEquipes();
			int compteurEquipesPresentes = 0;
			for (int i=0;i<this.equipes.size();i++) {
				Equipe e = this.equipes.get(i);
				if (allEquipes.contains(e)) {
					for (Joueur j : e.getJoueurs()) {
						if (!joueurs.contains(j)){
							// Un  joueur de l'équipe présente dans l'application n'est pas enregistré dans la BDD
							return EtatEquipe.MAL_COMPOSITION;
						}
					}
					compteurEquipesPresentes++;
				} else {
					for (Joueur j : e.getJoueurs()) {
						if (joueurs.contains(j)){
							// Un joueur d'un équipe à importé est déjà dans une autre équipe
							return EtatEquipe.JOUEUR_EXISTE;
						}
					}
				}
			}
			if (compteurEquipesPresentes == this.equipes.size()) {
				return EtatEquipe.MEME_EQUIPE;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EtatEquipe.OK;
	}
	
	public boolean enregistrerImportation (Tournoi t) {
		Equipe equipeBDD = new Equipe();
		Participer participerBDD = new Participer();
		List<Equipe> allEquipes = equipeBDD.getToutesLesEquipes();
		for (int numEquipe = 0; numEquipe < this.equipes.size(); numEquipe++) {
			Equipe equipeCourante = this.equipes.get(numEquipe);
			if (!allEquipes.contains(equipeCourante)) {
				equipeBDD.ajouterEquipe(equipeCourante);
				participerBDD.ajouterParticipation(new Participer(equipeCourante, t));
			} else {
				participerBDD.ajouterParticipation(new Participer(equipeCourante, t));
			}
		}
		return true;
	}
}
