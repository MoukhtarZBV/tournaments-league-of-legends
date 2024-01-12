package controleur;

import java.awt.Cursor;
import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;

import ihm.Ecran;
import ihm.VueAccueilAdmin;
import ihm.VueCreationTournoi;
import ihm.VueListeTournois;
import ihm.VueTournoi;
import modele.Niveau;
import modele.Statut;
import modele.Tournoi;

public class ControleurListeTournois implements ActionListener, ItemListener, MouseListener {

	private VueListeTournois vue;
	private Tournoi modele;
	
	private String nom;
	private Niveau niveau;
	private Statut statut;
	
	public ControleurListeTournois(VueListeTournois vue) {
		this.modele = new Tournoi();
		this.vue = vue;
		this.nom = "";
		this.niveau = null;
		this.statut = null;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JButton) {
			JButton bouton = (JButton) e.getSource();
			
			if (bouton.getName().equals("Nouveau")) {
				Ecran.update(this.vue);
				VueCreationTournoi vue = new VueCreationTournoi();
				vue.setVisible(true);
				this.vue.dispose(); 
			} else if (bouton.getName().equals("Retour")) {
				Ecran.update(this.vue);
				VueAccueilAdmin vue = new VueAccueilAdmin();
				vue.setVisible(true);
				this.vue.dispose(); 
			} else if (bouton.getFont().getFamily().equals("Gigi")) {
				this.nom = vue.saisieChamp();
				vue.afficherTournois(modele.getTournoisNiveauStatutNom(nom, niveau, statut));
			}
		} else if (e.getSource() instanceof JTextField) {
			this.nom = vue.saisieChamp();
			vue.afficherTournois(modele.getTournoisNiveauStatutNom(nom, niveau, statut));
		}
			
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getSource() instanceof JComboBox) {
			JComboBox<String> comboBox = (JComboBox<String>) e.getSource();
			String option = (String) comboBox.getSelectedItem();
			
			if (vue.estOptionComboboxNiveau(option)) {
				this.niveau = Niveau.getNiveau(option);
			}
			
			if (vue.estOptionComboboxStatus(option)) {
				this.statut = Statut.getStatut(option);
			}
			
			vue.afficherTournois(modele.getTournoisNiveauStatutNom(nom, niveau, statut));
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2) {
            JTable table = (JTable) e.getSource();
            int row = table.getSelectedRow();
            Tournoi tournoi = null;
            
            try {
				tournoi = modele.getTournoiParNom((String) table.getValueAt(row, 0));
			} catch (Exception e1) {
				e1.printStackTrace();
			}

            Ecran.update(this.vue);
            VueTournoi vueTournoi = new VueTournoi(tournoi);
			vueTournoi.setVisible(true);
			this.vue.dispose();
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
