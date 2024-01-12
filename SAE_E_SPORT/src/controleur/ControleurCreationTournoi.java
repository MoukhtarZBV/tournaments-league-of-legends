package controleur;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Date;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;

import ihm.Ecran;
import ihm.Palette;
import ihm.VueCreationTournoi;
import ihm.VueListeTournois;
import modele.Tournoi;

public class ControleurCreationTournoi implements ActionListener, FocusListener, MouseListener {
	
	private VueCreationTournoi vue;
	private Tournoi modele;
	
	public ControleurCreationTournoi(VueCreationTournoi vue) {
		this.modele = new Tournoi();
		this.vue = vue;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JButton) {
			JButton bouton = (JButton) e.getSource();
			if (bouton.getName().equals("Annuler")) {
				Ecran.update(this.vue);
				Tournoi t = new Tournoi();
				VueListeTournois vue = new VueListeTournois(t.getTousLesTournois());
				vue.setVisible(true);
				this.vue.dispose();	
			}
			if (bouton.getName().equals("Valider")) {
				try {
					Tournoi tournoi = new Tournoi(vue.getNom(), vue.getNiveau(), vue.getDateDebut(), vue.getDateFin(), vue.getPays());
					modele.ajouterTournoi(tournoi);
					vue.getPopup().setEnabled(false);
					
					Ecran.update(this.vue);
					Tournoi t = new Tournoi();
					VueListeTournois vue = new VueListeTournois(t.getTousLesTournois());
					this.vue.dispose();	
					vue.setVisible(true);
				} catch (IllegalArgumentException iae) {
					vue.getPopup().setErreur(iae.getMessage());
				}
			}
		}
	}

	@Override
	public void focusGained(FocusEvent e) {
		JFormattedTextField txt = (JFormattedTextField) e.getSource();
		if (txt.getForeground() == Color.LIGHT_GRAY) {
			txt.setForeground(Color.BLACK);
			txt.setText("");
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
	public void focusLost(FocusEvent e) {}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

}
