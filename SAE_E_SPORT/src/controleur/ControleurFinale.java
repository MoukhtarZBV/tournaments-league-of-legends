package controleur;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;

import ihm.Ecran;
import ihm.VueFinale;
import ihm.VueListeTournois;
import ihm.VueTournoi;
import modele.Participer;
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
			Ecran.update(this.vue);
			VueTournoi vueTournoi = new VueTournoi(this.vue.getTournoi());
			vueTournoi.setVisible(true);
			this.vue.dispose();
		} else if (bouton.getName().equals("Confirmer")) {
			this.modele.cloturerTournoi(this.vue.getTournoi(), this.vue.getVainqueur());
			this.vue.setVisibleConfirmer(false);
			this.vue.getTournoi().setStatut(Statut.TERMINE);
			this.vue.getTournoi().setVainqueur(this.vue.getVainqueur());
			this.vue.afficherTropheeVainqueur();
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

}
