package controleur;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;

import javax.swing.table.DefaultTableModel;

import components.DropListener;
import ihm.VueImportation;
import ihm.VueTournoi;
import modele.ModeleImportation;
import modele.ModeleImportation.EtatEquipe;
import modele.Statut;

public class ControleurImportation implements ActionListener, DropListener {
	
	private VueImportation vue;
	private ModeleImportation modele;
		
	public ControleurImportation(VueImportation vue) {
		this.vue = vue;
		this.modele = new ModeleImportation();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
	    JButton bouton = (JButton) e.getSource();
	    if (bouton.getText().equals("Importez depuis l'explorateur")) {
	        try {
	            JFileChooser fc = new JFileChooser();
	            if (fc.showOpenDialog(this.vue) == JFileChooser.APPROVE_OPTION) {
	            	String chemin = fc.getSelectedFile().getAbsolutePath();
	            	afficherTableEquipes(chemin);
	             }
	        } catch (Exception e1) {
	            e1.printStackTrace();
	        }
	    } 
	    if (bouton.getText().equals("Valider")) {
	    	try {
	    		EtatEquipe etat = this.modele.verifierEquipe();
				if(etat==EtatEquipe.MAL_COMPOSITION || etat==EtatEquipe.JOUEUR_EXISTE) {
					this.vue.getPopup().setErreur("Un ou plusieurs joueurs d'équipe appartiennent à plus d'une équipe");
				} else {
					if (etat == EtatEquipe.OK) {
						try {
							this.modele.enregistrerImportation(this.vue.getTournoi());
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
					this.vue.getPopup().setSucces("Les équipes ont bien été importées");
					this.vue.getTournoi().setStatut(Statut.A_VENIR);
					this.modele.changerStatusAVenir(this.vue.getTournoi());
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
	    }
	    
	    if (bouton.getText().equals("Retour")) {
	    	VueTournoi vueTournoi = new VueTournoi(vue.getTournoi());
			vueTournoi.setVisible(true);
	    	this.vue.dispose();
	    }    
	}
	
	@Override
	public void fileDropped(String filePath) {
		try {
			afficherTableEquipes(filePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void afficherTableEquipes(String chemin) throws IOException, Exception {
		if (this.modele.estBonFichierCSV(chemin, vue.getTournoi())) {
			this.modele.importerEquipesJoueurs(chemin);
			this.vue.afficherTableEquipes();
			this.vue.ajouterEquipesTable(this.modele.getEquipesJoueurs());
		    this.vue.changerBtnValider(true);
		    this.vue.setVisible(true);
		    this.vue.getPopup().setNormal("Voici les équipes qui seront importées");
		} else {
			this.vue.getPopup().setErreur("Le fichier importé ne concerne pas ce tournoi");
		}
	}
}
