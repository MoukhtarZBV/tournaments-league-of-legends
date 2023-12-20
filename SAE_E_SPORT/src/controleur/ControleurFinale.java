package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;

import ihm.VueFinale;
import ihm.VueListeTournois;
import ihm.VueTournoi;
import modele.Tournoi;

public class ControleurFinale implements MouseListener, ActionListener {

	private VueFinale vue;
	private Tournoi modele;
	
	public ControleurFinale (VueFinale vue) {
		this.vue = vue;
		this.modele = new Tournoi();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton bouton = (JButton) e.getSource();
		if (bouton.getName().equals("Retour")) {
			this.vue.dispose();
			VueTournoi vueTournoi = new VueTournoi(this.vue.getTournoi());
			vueTournoi.setVisible(true);
		} else if (bouton.getName().equals("Confirmer")) {
			this.modele.setVainqueurTournoi(this.vue.getTournoi(), this.vue.getVainqueur());
			this.vue.setActifConfirmer(false);
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		JLabel trophee = (JLabel) e.getSource();
		this.vue.setVainqueur(trophee.getName());
		this.vue.setActifConfirmer(true);
	}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

}
