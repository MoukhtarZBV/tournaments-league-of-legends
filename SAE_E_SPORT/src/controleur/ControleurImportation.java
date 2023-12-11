package controleur;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;

import javax.swing.table.DefaultTableModel;

import ihm.VueImportation;
import modele.ModeleImportation;
import modele.ModeleImportation.EtatEquipe;

public class ControleurImportation implements ActionListener{
	
	private VueImportation vue;
	private ModeleImportation modele;
		
	public ControleurImportation(VueImportation vue) {
		this.vue = vue;
		this.modele = new ModeleImportation();
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
	            	 
	            	this.modele.importerEquipesJoueurs(chemin);
	            	Object[][] datas = this.modele.getEquipesJoueurs();
	            	
		            // Récupération modèle de la JTable
		            DefaultTableModel model = this.vue.getModel();
		            
		            // Efface les lignes et colonnes
		            model.setRowCount(0);
		            model.setColumnCount(0);
		            
		            for (Object data : datas[0]) {
		            	model.addColumn(data);
		            }
		            
		            // Ajout des colonnes au modèle
		            for (Object[] data : datas) {
		            	model.addRow(data);
		            }
		            
		            this.vue.changerBtnValider(true);
		            vue.setVisible(true);
	             }
	        	
	        } catch (Exception e1) {
	            e1.printStackTrace();
	        }
	    }
	    if (bouton.getText().equals("Valider")) {
	    	try {
	    		EtatEquipe etat = this.modele.verifierEquipe();
				if(etat==EtatEquipe.MAL_COMPOSITION || etat==EtatEquipe.JOUEUR_EXISTE) {
					this.vue.setColorMessage(new Color(255, 204, 204));
					this.vue.setMsgErreur("Un ou plusieurs joueurs d'équipe appartiennent à plus d'une équipe");
				} else {
					if (etat == EtatEquipe.OK) {
						try {
							this.modele.enregistrerImportation(this.vue.getTournoi());
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
					this.vue.setColorMessage(new Color(204, 255, 204));
					this.vue.setMsgErreur("Les équipes ont bien été importées");
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
	    }
	    
	    if (bouton.getText().equals("Retour")) {
	    	this.vue.dispose();
	    }
	    
	}
}
