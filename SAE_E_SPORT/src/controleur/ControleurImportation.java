package controleur;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;

import components.DropListener;
import ihm.VueImportation;
import ihm.VueTournoi;
import modele.ModeleImportation;
import modele.ModeleImportation.EtatEquipe;
import modele.Statut;
import modele.Tournoi;

public class ControleurImportation implements ActionListener, DropListener, MouseListener {
	
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
	    } else if (bouton.getText().equals("Valider")) {
	    	EtatEquipe etat = this.modele.verifierEquipe();
	    	if(etat==EtatEquipe.MAL_COMPOSITION || etat==EtatEquipe.JOUEUR_EXISTE) {
	    		this.vue.getPopup().setErreur("Un ou plusieurs joueurs d'équipe appartiennent à plus d'une équipe");
	    	} else if (etat == EtatEquipe.OK) {
	    		this.modele.enregistrerImportation(this.vue.getTournoi());
	    		this.vue.getPopup().setSucces("Les équipes ont bien été importées");
	    		this.vue.getTournoi().setStatut(Statut.ATTENTE_ARBITRES);
	    		new Tournoi().changerStatutTournoi(this.vue.getTournoi(), Statut.ATTENTE_ARBITRES);
	    	}
	    } else if (bouton.getText().equals("Retour")) {
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
		if (this.modele.fichierCSVconcerneTournoi(chemin, vue.getTournoi())) {
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
	
	@Override
	public void mouseEntered(MouseEvent e) {
		if(e.getSource() instanceof JButton) {
			JButton b = (JButton)e.getSource();
			
			b.setBackground(b.getBackground().brighter());
			b.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}	
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if(e.getSource() instanceof JButton) {
			JButton b = (JButton)e.getSource();
			
			b.setBackground(b.getBackground().darker());
			b.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}	
	}
	
	
	// NOT IMPLEMENTED \\

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseClicked(MouseEvent e) {}
}
