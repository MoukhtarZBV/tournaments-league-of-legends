package controleur;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;

import ihm.Ecran;
import ihm.Palette;
import ihm.VueAccueilAdmin;
import ihm.VueHistoriquePoints;
import ihm.VueListeEquipe;
import ihm.VueListeTournois;
import modele.Equipe;
import modele.Tournoi;

public class ControleurAccueil implements MouseListener {
	
	private VueAccueilAdmin vue;
	
	public ControleurAccueil(VueAccueilAdmin vue) {
		this.vue = vue;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		JPanel panel = (JPanel)e.getSource();
		switch(panel.getName()) {
			case "Equipes":
				Ecran.update(this.vue);
				Equipe equipeBDD = new Equipe();
				VueListeEquipe vueEquipes = new VueListeEquipe(equipeBDD.getToutesLesEquipes());
				vueEquipes.setVisible(true);
				this.vue.dispose();	
				break;
				
			case "Tournois":
				Ecran.update(this.vue);
				Tournoi tournoiBDD = new Tournoi();
				VueListeTournois vueTournois = new VueListeTournois(tournoiBDD.getTousLesTournois());
				vueTournois.setVisible(true);
				this.vue.dispose();	
				break;
				
			case "Historique" :
				Ecran.update(this.vue);
				VueHistoriquePoints vueHistorique = new VueHistoriquePoints();
				vueHistorique.setVisible(true);
				this.vue.dispose();	
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
