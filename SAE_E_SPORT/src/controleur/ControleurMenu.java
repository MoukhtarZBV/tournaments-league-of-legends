package controleur;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ihm.Ecran;
import ihm.Palette;
import ihm.VueHistoriquePoints;
import ihm.VueIdentification;
import ihm.VueListeArbitre;
import ihm.VueListeEquipe;
import ihm.VueListeTournois;
import modele.Arbitre;
import modele.Equipe;
import modele.Tournoi;

public class ControleurMenu implements MouseListener {
	
	private JFrame parent;
	
	public ControleurMenu(JFrame parent) {
		this.parent = parent;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		JPanel panel = (JPanel)e.getSource();		
		switch(panel.getName()) {
			case "Equipes":
				if(!(parent instanceof VueListeEquipe)) {
					Ecran.update(this.parent);
					Equipe equipeBDD = new Equipe();
					VueListeEquipe vueEquipes = new VueListeEquipe(equipeBDD.getToutesLesEquipes());
					vueEquipes.setVisible(true);
				}
				break;
				
			case "Tournois":
				if(!(parent instanceof VueListeTournois)) {
					Ecran.update(this.parent);
					Tournoi tournoiBDD = new Tournoi();
					VueListeTournois vueTournois = new VueListeTournois(tournoiBDD.getTousLesTournois());
					vueTournois.setVisible(true);
				}
				break;
				
			case "Historique" :
				if(!(parent instanceof VueHistoriquePoints)) {
					Ecran.update(this.parent);
					VueHistoriquePoints vueHistorique = new VueHistoriquePoints();
					vueHistorique.setVisible(true);
				}
				break;
				
			case "Arbitres":
				if(!(parent instanceof VueListeArbitre)) {
					Ecran.update(this.parent);
					Arbitre arbitreBDD = new Arbitre();
					VueListeArbitre vueArbitres = new VueListeArbitre(arbitreBDD.getTousLesArbitres(), false, null);
					vueArbitres.setVisible(true);
				}
				break;
				
			case "Deconnexion":
				String[] options = { "Oui", "Non" }; 
				int choix = JOptionPane.showOptionDialog( 
				        null,
				        "Voulez-vous vous déconnecter ?",
				        "Deconnexion",
				        JOptionPane.YES_NO_OPTION,
				        JOptionPane.QUESTION_MESSAGE,
				        null,
				        options,
				        options[1] 
				);
				
				if(choix == 0) {
					Ecran.update(this.parent);
					VueIdentification vueIdentification = new VueIdentification();
					vueIdentification.setVisible(true);
				}
				break;
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		JPanel panel = (JPanel)e.getSource();
		panel.setBackground(Palette.GRAY);
		panel.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}

	@Override
	public void mouseExited(MouseEvent e) {
		JPanel panel = (JPanel)e.getSource();
		panel.setBackground(Palette.DARK_GRAY);
		panel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}
	

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

}
