package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;

import ihm.VueImportation;
import modele.LireCSV;

public class ControleurImportation implements ActionListener{
	
	private VueImportation vue;

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
		            List<String[]> data = read.getData();

		            // Récupération modèle de la JTable
		            DefaultTableModel model = (DefaultTableModel) this.vue.getModel();

		            // Ajout des colonnes au modèle
		            for (int i = 1; i < data.size(); i = i + 5) {
		                if (i == 1 || i == 6 || i == 11 || i == 16 || i == 21) {
		                    model.addColumn(data.get(i)[4]);
		                }
		            }
		            
		            // Récupération des noms des équipes
		            Object[] titreColonne = new Object[10];
		            int indice = 0;
		            for (int i = 1; i < data.size();i++) {
		            	if (i == 1 || i == 6 || i == 11 || i == 16 || i == 21) {
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
	}

}

