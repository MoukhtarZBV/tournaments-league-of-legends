package controleur;

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

import ihm.VueAccueilAdmin;
import ihm.VueCreationTournoi;
import ihm.VueListeTournois;
import ihm.VueTournoi;
import modele.Niveau;
import modele.Status;
import modele.Tournoi;

public class ControleurListeTournois implements ActionListener, ItemListener, MouseListener, WindowListener {

	private VueListeTournois vue;
	private Tournoi modele;
	
	private String nom;
	private Niveau niveau;
	private Status status;
	
	public ControleurListeTournois(VueListeTournois vue) {
		this.modele = new Tournoi();
		this.vue = vue;
		this.nom = "";
		this.niveau = null;
		this.status = null;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JButton) {
			JButton bouton = (JButton) e.getSource();
			
			if (bouton.getName().equals("Nouveau")) {
				VueCreationTournoi vue = new VueCreationTournoi();
				vue.setVisible(true);
				
				this.vue.dispose(); // pour actualiser la page en fond
			} else if (bouton.getName().equals("Retour")) {
				vue.dispose();
				
				VueAccueilAdmin vue = new VueAccueilAdmin();
				vue.setVisible(true);
			} else if (bouton.getFont().getFamily().equals("Gigi")) {
				this.nom = vue.saisieChamp();
				vue.afficherTournois(modele.getTournoisNiveauStatusNom(nom, niveau, status));
			}
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
				this.status = Status.getStatus(option);
			}
			
			vue.afficherTournois(modele.getTournoisNiveauStatusNom(nom, niveau, status));
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2) {
            JTable table = (JTable) e.getSource();
            int row = table.getSelectedRow();
            Tournoi tournoi = null;
            
            try {
            	System.out.println("Avant");
				tournoi = modele.getTournoiDeNom((String) table.getValueAt(row, 0));
				System.out.println("Apres : " + tournoi);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
            
            VueTournoi vueTournoi = new VueTournoi(tournoi);
			vueTournoi.setVisible(true);
         }
	}
	
	@Override
	public void windowClosing(WindowEvent e) {
    	this.vue.dispose();
    	VueAccueilAdmin vue = new VueAccueilAdmin();
		vue.setVisible(true);
	}
	

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void windowOpened(WindowEvent e) {}

	@Override
	public void windowClosed(WindowEvent e) {}

	@Override
	public void windowIconified(WindowEvent e) {}

	@Override
	public void windowDeiconified(WindowEvent e) {}

	@Override
	public void windowActivated(WindowEvent e) {}

	@Override
	public void windowDeactivated(WindowEvent e) {}
}
