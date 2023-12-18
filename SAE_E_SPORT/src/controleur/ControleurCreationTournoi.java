package controleur;

import java.awt.Color;



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;

import dao.TournoiJDBC;
import ihm.VueCreationTournoi;
import ihm.VueListeEquipe;
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
					vue.effacerMessageErreur();
					Tournoi t = new Tournoi();
					VueListeTournois vue = new VueListeTournois(t.tousLesTournois());
					vue.setVisible(true);
					this.vue.dispose();
				} catch (IllegalArgumentException iae) {
					vue.afficherMessageErreur(iae.getMessage());
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
	public void focusLost(FocusEvent e) {}
}
