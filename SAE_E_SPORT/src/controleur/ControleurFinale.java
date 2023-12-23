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
import modele.Statut;
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
			VueTournoi vueTournoi = new VueTournoi(this.vue.getTournoi());
			vueTournoi.setVisible(true);
			this.vue.dispose();
		} else if (bouton.getName().equals("Confirmer")) {
			this.modele.setVainqueurTournoi(this.vue.getTournoi(), this.vue.getVainqueur());
			this.vue.setVisibleConfirmer(false);
			this.modele.changerStatusTournoi(this.vue.getTournoi(), Statut.TERMINE);
			this.vue.getTournoi().setStatut(Statut.TERMINE);
			this.vue.getTournoi().setVainqueur(this.vue.getVainqueur());
			this.vue.afficherBoutonsTrophees();
			this.vue.setLabelStatus();
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (this.vue.getTournoi().getStatut() == Statut.FINALE) {
			JLabel trophee = (JLabel) e.getSource();
			this.vue.setVainqueur(trophee.getName());
			this.vue.setActifConfirmer(true);
		}
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
