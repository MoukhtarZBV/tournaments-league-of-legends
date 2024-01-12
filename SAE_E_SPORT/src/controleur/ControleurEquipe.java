package controleur;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;

import ihm.Ecran;
import ihm.VueAccueilAdmin;
import ihm.VueEquipe;
import ihm.VueListeEquipe;
import ihm.VueTournoi;
import modele.Equipe;

public class ControleurEquipe implements ActionListener, MouseListener {
	private VueEquipe vue;
	private Equipe modele;

	public ControleurEquipe(VueEquipe vue) {
		this.vue = vue;
		this.modele = new Equipe();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton bouton = (JButton) e.getSource();
		if (bouton.getText().equals("Sauvegarder")) {
			try {
				String name = this.vue.getNomEquipe().replace(" ", "");
				if (!name.equals("") && new Equipe().getEquipeParNom(vue.getNomEquipe()) == null) {
					this.modele.mettreAJourEquipe((new Equipe(this.vue.getIdEquipe(),this.vue.getNomEquipe(),this.vue.getRangEquipe(), this.vue.getPaysEquipe())));
					Ecran.update(this.vue);
					this.vue.dispose();
					if (this.vue.getPere() == null) {
						VueListeEquipe vue = new VueListeEquipe(this.modele.getToutesLesEquipes());
						vue.setVisible(true);
					} else {
						VueTournoi vue = new VueTournoi(this.vue.getPere());
						vue.setVisible(true);
					}
				} else if (name.equals("")) {
					this.vue.getPopup().setErreur("Le nom de l'équipe ne peut pas être vide");
				} else {
					this.vue.getPopup().setErreur("Une équipe portant ce nom existe déjà");
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		if (bouton.getText().equals("Retour")) {
			Ecran.update(this.vue);
			this.vue.dispose();
			try {
				if(this.vue.getPere() == null) {
					VueListeEquipe vue = new VueListeEquipe(this.modele.getToutesLesEquipes());
					vue.setVisible(true);
				} else {
					VueTournoi vue = new VueTournoi(this.vue.getPere());
					vue.setVisible(true);
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
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
