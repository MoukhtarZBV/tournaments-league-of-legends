package controleur;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JComboBox;

import dao.TournoiJDBC;
import ihm.VueCreationTournoi;
import ihm.VueListeTournois;
import modele.Niveau;
import modele.Status;
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
			Niveau niveau = Niveau.getNiveau((String) comboBox.getSelectedItem());
			Status status = Status.getStatus((String) comboBox.getSelectedItem()); 
			if (niveau == null && status == null) {
				vue.afficherTournois(jdbc.getAll());
			} else if (niveau != null && status == null) {
				vue.afficherTournois(jdbc.getAll().stream().filter(tournoi -> tournoi.getNiveau() == niveau).collect(Collectors.toList()));
			} else if (niveau == null && status != null) {
				vue.afficherTournois(jdbc.getAll().stream().filter(tournoi -> Tournoi.etatTournoi(tournoi) == status).collect(Collectors.toList()));
			} else {
				vue.afficherTournois(jdbc.getAll().stream().filter(tournoi -> Tournoi.etatTournoi(tournoi) == status && tournoi.getNiveau() == niveau).collect(Collectors.toList()));
			}
		}
	}
}
