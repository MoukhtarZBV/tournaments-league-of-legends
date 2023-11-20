package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import dao.TournoiJDBC;
import modele.Tournoi;

public class ControleurTournoi implements ActionListener {

	public enum Etat{
		SAISIE, FIN_SAISIE
	}
	
	private Etat etat;
	
	private VueCreationTournoi vue;
	private Tournoi modele;
	private TournoiJDBC jdbc;
	
	public ControleurTournoi(VueCreationTournoi vue) {
		this.etat = Etat.SAISIE;
		this.vue = vue;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton bouton = (JButton) e.getSource();
		switch (this.etat) {
			case SAISIE:
				if (bouton.getText() == "Annuler") {
					System.exit(0);
				}
				break;
			case FIN_SAISIE:
				if (bouton.getText() == "Annuler") {
					System.exit(0);
				}
				if (bouton.getText() == "Valider") {
					if (vue.champVide()) {
						vue.afficherMessageErreur("Veuillez remplir tous les champs");
					} else if (vue.nomTropLong()) {
						vue.afficherMessageErreur("Le nom ne doit pas dépasser les 100 caractères");
					} else if (jdbc.existeTournoiEntreDates(vue.getDateDebut(), vue.getDateFin())) {
						vue.afficherMessageErreur("Un tournoi existe déjà sur ce créneau");
					} else {
						Tournoi tournoi = new Tournoi(0, 
								vue.getNom(), 
								vue.getNiveau(), 
								vue.getDateDebut(), 
								vue.getDateFin(), 
								vue.getPays(),
								null,
								null);
						
						jdbc.add(tournoi);
					}
				}
				break;
		}
	}
}
