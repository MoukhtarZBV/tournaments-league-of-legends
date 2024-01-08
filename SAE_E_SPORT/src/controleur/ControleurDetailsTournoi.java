package controleur;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JTable;

import ihm.VueEquipe;
import ihm.VueFinale;
import ihm.VueGestionDeLaPoule;
import ihm.VueImportation;
import ihm.VueListeTournois;
import ihm.VueTournoi;
import modele.Compte;
import modele.Equipe;
import modele.ModelePoule;
import modele.Statut;
import modele.Tournoi;
import modele.TypeCompte;

public class ControleurDetailsTournoi implements ActionListener, MouseListener {
	
	private VueTournoi vue;
	private Tournoi modele;

	public ControleurDetailsTournoi(VueTournoi vue) {
		this.modele = new Tournoi();
		this.vue = vue;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton bouton = (JButton) e.getSource();
		if (bouton.getName().equals("Importer")) {
			VueImportation vueImportation = new VueImportation(vue.getTournoi());
			vueImportation.setVisible(true);
			vue.dispose();
		} else if (bouton.getName().equals("Retour")) {
			this.vue.dispose();
			VueListeTournois vue = new VueListeTournois(new Tournoi().getTousLesTournois());
			vue.setVisible(true);
		} else if (bouton.getName().equals("Poule")) {
	        VueGestionDeLaPoule frame = new VueGestionDeLaPoule(this.vue.getTournoi());
	        frame.setVisible(true);
	        this.vue.dispose();
		} else if (bouton.getName().equals("Ouvrir")) {
			if (this.modele.selectionArbitre(vue.getTournoi())) {
				this.modele.changerStatutTournoi(vue.getTournoi(), Statut.EN_COURS);
				this.vue.getTournoi().setStatut(Statut.EN_COURS);
				this.vue.getTournoi().generationPoule();
				this.vue.setVisibleBoutonOuvrir(false);
				this.vue.afficherMessageArbitres(false);
				this.vue.afficherArbitresTournoi();
				this.vue.afficherBoutonGererPoule("Gérer la poule");
			} else {
				this.vue.afficherMessageErreurArbitres();
			}
		} else if (bouton.getName().equals("Finale")) {
			VueFinale vueFinale = new VueFinale(this.vue.getTournoi());
			vueFinale.setVisible(true);
			this.vue.dispose(); 
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() instanceof JTable) {
			if (e.getClickCount() == 2) {
				try {
					Equipe equipeBDD = new Equipe();
		            JTable table = (JTable) e.getSource();
		            int row = table.getSelectedRow();
		            List<Equipe> equipes = (equipeBDD.getToutesLesEquipes());
					
					VueEquipe vue = new VueEquipe(equipes, equipeBDD.getEquipeParNom(table.getValueAt(row, 0).toString()), this.vue.getTournoi());
					vue.setVisible(true);
					this.vue.dispose();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
	         }
		}
	}
	
	
	// NOT IMPLEMENTED \\

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}
}
