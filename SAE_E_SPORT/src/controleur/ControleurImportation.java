package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;

import dao.EquipeJDBC;
import dao.JoueurJDBC;
import ihm.VueImportation;
import modele.Equipe;
import modele.Joueur;
import modele.LireCSV;
import modele.Pays;

public class ControleurImportation implements ActionListener{
	
	private VueImportation vue;
	private List<String[]> data = null;

	public ControleurImportation(VueImportation vue) {
		this.vue = vue;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
	    JButton bouton = (JButton) e.getSource();
	    if (bouton.getText().equals("Importer")) {
	        try {
	            JFileChooser fc = new JFileChooser();
	            // Affichage de la boite de séléction de fichier
	            if (fc.showOpenDialog(this.vue) == JFileChooser.APPROVE_OPTION) {
	            	// Récupération du chemin absolu vers le fichier
	            	String chemin = fc.getSelectedFile().getAbsolutePath();
	            	 
	 	            LireCSV read = new LireCSV(chemin);
		            this.data = read.getData();

		            // Récupération modèle de la JTable
		            DefaultTableModel model = (DefaultTableModel) this.vue.getModel();

		            // Ajout des colonnes au modèle
		            for (int i = 1; i < data.size(); i = i + 5) {
		                if (i%5 == 1) {
		                    model.addColumn(data.get(i)[4]);
		                }
		            }
		            
		            // Récupération des noms des équipes
		            Object[] titreColonne = new Object[10];
		            int indice = 0;
		            for (int i = 1; i < data.size();i++) {
		            	if (i%5 == 1) {
		            		titreColonne[indice] = (data.get(i)[4]);
		            		indice++;
		            	}
		            } 
		            // Ajout du nom des équipes
		            model.addRow(titreColonne);
		            
		            // Récupération des joueurs
		            List<String> joueurs = new ArrayList<>();
		            for (int i = 1; i < data.size();i++) {
		            	joueurs.add(data.get(i)[7]);
		            } 
		            
		            // Récupération des joueurs par lignes 
		            Object [][] joueursRow = new Object[6][6];
		            for (int i = 0; i<5;i++) {
		            	joueursRow[i][0] = joueurs.get(i);
		            	joueursRow[i][1] = joueurs.get(i+5);
		            	joueursRow[i][2] = joueurs.get(i+10);
		            	joueursRow[i][3] = joueurs.get(i+15);
		            }
		            
		            // Ajout des joueurs
		            for (int i = 0; i < 5; i++) {
		                model.addRow(joueursRow[i]);
		            }

		            // Mise à jour de l'affichage de la vue
		            vue.setVisible(true);
	             }
	        	
	        } catch (Exception e1) {
	            e1.printStackTrace();
	        }
	    }
	    if(bouton.getText().equals("Valider") && this.data != null) {
	    	EquipeJDBC equipeDB = new EquipeJDBC();
	    	JoueurJDBC joueurDB = new JoueurJDBC();
	    	for(int i = 1; i<this.data.size();i=i+5) {
	    		try {
	    			Equipe equipe = new Equipe(equipeDB.getNextValueSequence(), this.data.get(i)[4], Integer.parseInt(this.data.get(i)[5]), Pays.getPays(data.get(i)[6]));
	    			for (int j = i; j<i+5; j++) {
	    				Joueur joueur = new Joueur(joueurDB.getNextValueSequence(), this.data.get(j)[7], equipe);
	    				equipe.ajouterJoueur(joueur);
	    			}
					equipeDB.add(equipe);
					for (Joueur j : equipe.getJoueurs()) {
						joueurDB.add(j);
					}
					equipeDB.getAll().toString();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
	    	}
	    }
	    if(bouton.getText().equals("Retour")) {
	    	this.vue.dispose();
	    }
	}

}

