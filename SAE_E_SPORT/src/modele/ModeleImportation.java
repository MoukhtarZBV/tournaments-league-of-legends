package modele;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.LinkedList;
import java.util.List;

import Fonctions.LireCSV;
import dao.ConnectionJDBC;
import dao.EquipeJDBC;
import dao.JoueurJDBC;
import dao.ParticiperJDBC;

public class ModeleImportation {

	private List<Equipe> equipes;
	
	public enum EtatEquipe{
		OK, MEME_EQUIPE, MAL_COMPOSITION, JOUEUR_EXISTE;
	}
	
	public ModeleImportation() {
		this.equipes = new LinkedList<>();
	}
	
	public boolean estBonFichierCSV(String chemin, Tournoi tournoi) throws IOException {
		String line;
    	BufferedReader br;
		br = new BufferedReader(new FileReader(chemin));
		br.readLine();
		if ((line = br.readLine()) != null) {
		    String[] values = line.split(",");
		    System.out.println(values[0] + " " + tournoi.getNomTournoi());
			if (!values[0].equals(tournoi.getNomTournoi())) {
				br.close();
				return false;
		    }
		}
		br.close();
		return true;
	}
	
	public void importerEquipesJoueurs(String chemin) throws Exception {
		this.equipes.clear();
        String line;
        List<String[]> data = new LinkedList<>();
    	
        LireCSV reader = new LireCSV(chemin);
        data = reader.getData();
        
    	for (int ligne = 1; ligne<data.size(); ligne += 5) {		
			// Creer l'equipe et lui attribuer les joueurs
			Equipe equipe = new Equipe(EquipeJDBC.getNextValueSequence(), data.get(ligne)[4], Integer.parseInt(data.get(ligne)[5]), Pays.getPays(data.get(ligne)[6]));
			this.equipes.add(equipe);
			for (int j = ligne; j<ligne+5; j++) {
				Joueur joueur = new Joueur(JoueurJDBC.getNextValueSequence(), data.get(j)[7], equipe);
				equipe.ajouterJoueur(joueur);
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
		JoueurJDBC jdb = new JoueurJDBC();
		EquipeJDBC edb = new EquipeJDBC();
		EtatEquipe etat = EtatEquipe.OK;
		try {
			List<Joueur> joueurs = jdb.getAll();	
			List<Equipe> allEquipes = edb.getAll();
			int cmp = 0;
			for (int i=0;i<this.equipes.size();i++) {
				Equipe e = this.equipes.get(i);
				if (allEquipes.contains(e)) {
					for (Joueur j : e.getJoueurs()) {
						if (!joueurs.contains(j)){
							// joueur inexistant
							return EtatEquipe.MAL_COMPOSITION;
						}
					}
					cmp++;
				} else {
					for (Joueur j : e.getJoueurs()) {
						if (joueurs.contains(j)){
							// pas equipe mais joueur existe
							return EtatEquipe.JOUEUR_EXISTE;
						}
					}
				}
			}
			if (cmp == this.equipes.size()) {
				return EtatEquipe.MEME_EQUIPE;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return etat;
	}
	
	public boolean enregistrerImportation (Tournoi t) throws Exception{
		EquipeJDBC edb = new EquipeJDBC();
		ParticiperJDBC pdb = new ParticiperJDBC();
		try {
			for(Equipe e : this.equipes) {
				edb.add(e);
				pdb.add(new Participer(e, t));
			}
		} catch (Exception exp) {
			exp.printStackTrace();
		}
		return true;
	}
	
	public void changerStatusAVenir(Tournoi tournoi) {
		new Tournoi().changerStatusTournoi(tournoi, Statut.A_VENIR);
	}
}
