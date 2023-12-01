package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;

import javax.swing.JButton;
import javax.swing.JComboBox;

import dao.TournoiJDBC;
import ihm.VueListeDesTournois;
import modele.ModeleListeTournois;
import modele.Niveau;

public class ControleurListeTournois implements ActionListener, ItemListener {

	private VueListeDesTournois vue;
	private ModeleListeTournois modele;
	private TournoiJDBC jdbc;
	
	public ControleurListeTournois(VueListeDesTournois vue) {
		this.modele = new ModeleListeTournois();
		this.vue = vue;
		this.jdbc = new TournoiJDBC();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JButton) {
			JButton bouton = (JButton) e.getSource();
			
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getSource() instanceof JComboBox) {
			JComboBox comboBox = (JComboBox) e.getSource();
			if (!((String) comboBox.getSelectedItem()).equals("-- Tri Niveau --")) {
				vue.afficherTournois(jdbc.getTournoisDeNiveau(Niveau.getNiveau((String) comboBox.getSelectedItem())));
			}
		}
	}
}
