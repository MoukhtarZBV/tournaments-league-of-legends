package controleur;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ihm.Ecran;
import ihm.Palette;
import ihm.VueAccueilAdmin;
import ihm.VueHistoriquePoints;
import ihm.VueIdentification;
import ihm.VueListeArbitre;
import ihm.VueListeEquipe;
import ihm.VueListeTournois;
import modele.Arbitre;
import modele.Equipe;
import modele.Tournoi;

public class ControleurAccueil implements MouseListener, WindowListener, ActionListener {
	
	private VueAccueilAdmin vue;
	
	public ControleurAccueil(VueAccueilAdmin vue) {
		this.vue = vue;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton b = (JButton) e.getSource();
		if(b.getName().equals("Deconnexion")) {
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
				Ecran.update(this.vue);
				VueIdentification vueIdentification = new VueIdentification();
				vueIdentification.setVisible(true);
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		JPanel panel = (JPanel)e.getSource();

		Ecran.update(this.vue);
		switch(panel.getName()) {
			case "Equipes":
				Equipe equipeBDD = new Equipe();
				VueListeEquipe vueEquipes = new VueListeEquipe(equipeBDD.getToutesLesEquipes());
				vueEquipes.setVisible(true);
				break;
				
			case "Tournois":
				Tournoi tournoiBDD = new Tournoi();
				VueListeTournois vueTournois = new VueListeTournois(tournoiBDD.getTousLesTournois());
				vueTournois.setVisible(true);
				break;
				
			case "Historique" :
				VueHistoriquePoints vueHistorique = new VueHistoriquePoints();
				vueHistorique.setVisible(true);
				break;
				
			case "Arbitres":
				Arbitre arbitreBDD = new Arbitre();
				VueListeArbitre vueArbitres = new VueListeArbitre(arbitreBDD.getTousLesArbitres(), false, null);
				vueArbitres.setVisible(true);
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
	public void windowOpened(WindowEvent e) {
		Ecran.closeLast();
	}
	
	
	// NOT IMPLEMENTED \\

	@Override
	public void windowClosing(WindowEvent e) {}

	@Override
	public void windowClosed(WindowEvent e) {}

	@Override
	public void windowIconified(WindowEvent e) {}

	@Override
	public void windowDeiconified(WindowEvent e) {}

	@Override
	public void windowActivated(WindowEvent e) {}

	@Override
	public void windowDeactivated(WindowEvent e) {}
	
	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

}
