package controleur;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;

import javax.swing.JButton;
import javax.swing.JComboBox;

import dao.TournoiJDBC;
import ihm.VueCreationTournoi;
import ihm.VueListeTournois;
import modele.Niveau;
import modele.Tournoi;

public class ControleurListeTournois implements ActionListener, ItemListener {

	private VueListeTournois vue;
	private Tournoi modele;
	private TournoiJDBC jdbc;
	
	public ControleurListeTournois(VueListeTournois vue) {
		this.modele = new Tournoi();
		this.vue = vue;
		this.jdbc = new TournoiJDBC();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JButton) {
			JButton bouton = (JButton) e.getSource();
			if (bouton.getText().equals("Créer Tournoi")) {
				VueCreationTournoi vue = new VueCreationTournoi();
				vue.setVisible(true);
			}
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getSource() instanceof JComboBox) {
			JComboBox comboBox = (JComboBox) e.getSource();
			if (!((String) comboBox.getSelectedItem()).equals("-- Tri Niveau --")) {
				vue.afficherTournois(jdbc.getTournoisDeNiveau(Niveau.getNiveau((String) comboBox.getSelectedItem())));
			} else {
				try {
					vue.afficherTournois(jdbc.getAll());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
	}
}
