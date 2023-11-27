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
import modele.ModeleCreationTournoi;
import modele.Tournoi;

public class ControleurTournoi implements ActionListener, FocusListener {
	
	private VueCreationTournoi vue;
	private ModeleCreationTournoi modele;
	private TournoiJDBC jdbc;
	
	public ControleurTournoi(VueCreationTournoi vue, Connection cn) {
		this.modele = new ModeleCreationTournoi();
		this.vue = vue;
		this.jdbc = new TournoiJDBC();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JButton) {
			JButton bouton = (JButton) e.getSource();
			if (bouton.getText() == "Annuler") {
				System.exit(0);
			}
			if (bouton.getText() == "Valider") {
				if (vue.champVide()) {
					vue.afficherMessageErreur("Veuillez remplir tous les champs");
				} else if (vue.nomTropLong()) {
					vue.afficherMessageErreur("Le nom ne doit pas dépasser les 100 caractères");
				} else if (!modele.moinsDeDeuxSemainesEntreDates(vue.getDateDebut(), vue.getDateFin())){
					vue.afficherMessageErreur("Le tournoi ne peut durer plus de deux semaines");
				} else if (!modele.anneePourSaisonEnCours(vue.getDateDebut()) &&
						   !modele.anneePourSaisonEnCours(vue.getDateFin())){
					vue.afficherMessageErreur("Le tournoi doit avoir lieu cette année");
				} else {
					try {
						if (jdbc.existeTournoiEntreDates(vue.getDateDebut(), vue.getDateFin())) {
							vue.afficherMessageErreur("Un tournoi existe déjà sur ce créneau");
						} else {
							try {
								Tournoi tournoi = new Tournoi(0, 
										vue.getNom(), 
										vue.getNiveau(), 
										vue.getDateDebut(), 
										vue.getDateFin(), 
										vue.getPays());
								System.out.println(vue.getNiveau().denomination() + " - " + vue.getPays());
								jdbc.add(tournoi);
								for (Tournoi t : jdbc.getAll()) {
									System.out.println("Tournoi : " + t.getNomTournoi() + " | Du " + t.getDateDebut() + " au " + t.getDateFin());
								}
								vue.effacerMessageErreur();
							} catch (IllegalArgumentException iae) {
								vue.afficherMessageErreur(iae.getMessage());
							}
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		}
	}

	@Override
	public void focusGained(FocusEvent e) {
		JFormattedTextField txt = (JFormattedTextField)e.getSource();
		if (txt.getForeground() == Color.LIGHT_GRAY) {
			txt.setForeground(Color.BLACK);
			txt.setText("");
		}
	}

	@Override
	public void focusLost(FocusEvent e) {}
}
