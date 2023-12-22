package controleur;

import java.awt.Color;



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Date;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;

import ihm.VueCreationTournoi;
import ihm.VueListeTournois;
import modele.Tournoi;

public class ControleurCreationTournoi implements ActionListener, FocusListener {
	
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
				vue.dispose();
				Tournoi t = new Tournoi();
				VueListeTournois vue = new VueListeTournois(t.tousLesTournois());
				vue.setVisible(true);
			}
			if (bouton.getName().equals("Valider")) {
				try {
					Tournoi tournoi = new Tournoi(vue.getNom(), vue.getNiveau(), vue.getDateDebut(), vue.getDateFin(), vue.getPays());
					modele.ajouterTournoi(tournoi);
					vue.getPopup().setEnabled(false);
					Tournoi t = new Tournoi();
					VueListeTournois vue = new VueListeTournois(t.tousLesTournois());
					vue.setVisible(true);
					this.vue.dispose();
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

	
	
	// NOT IMPLEMENTED \\
	
	@Override
	public void focusLost(FocusEvent e) {}

}
