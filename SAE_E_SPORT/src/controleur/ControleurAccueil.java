package controleur;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

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
				Equipe equipeBDD = new Equipe();
				VueListeEquipe vueEquipes = new VueListeEquipe(equipeBDD.getToutesLesEquipes());
				vueEquipes.setVisible(true);
				this.vue.dispose();	
				break;
				
			case "Tournois":
				Tournoi tournoiBDD = new Tournoi();
				System.out.println(tournoiBDD.getTousLesTournois());
				VueListeTournois vueTournois = new VueListeTournois(tournoiBDD.getTousLesTournois());
				vueTournois.setVisible(true);
				this.vue.dispose();	
				break;
				
			case "Historique" :
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
