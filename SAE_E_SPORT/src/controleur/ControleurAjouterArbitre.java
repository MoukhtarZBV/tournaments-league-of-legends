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

import dao.ArbitreJDBC;
import dao.JoueurJDBC;
import ihm.Ecran;
import ihm.VueAjouterArbitre;
import ihm.VueCreationTournoi;
import ihm.VueListeArbitre;
import ihm.VueListeTournois;
import modele.Arbitre;
import modele.Tournoi;

public class ControleurAjouterArbitre implements ActionListener, FocusListener {
	
	private VueAjouterArbitre vue;
	private Arbitre modele;
	
	public ControleurAjouterArbitre(VueAjouterArbitre vue) {
		this.modele = new Arbitre();
		this.vue = vue;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JButton) {
			JButton bouton = (JButton) e.getSource();
			if (bouton.getName().equals("Annuler")) {
				Ecran.update(this.vue);				
				Arbitre arbitreBDD = new Arbitre();
				VueListeArbitre vueArbitres = new VueListeArbitre(arbitreBDD.getTousLesArbitres());
				vueArbitres.setVisible(true);
				this.vue.dispose();	
			}
			if (bouton.getName().equals("Valider")) {
				try {
					Ecran.update(this.vue);					
					Arbitre arbitre;
					try {
						arbitre = new Arbitre(ArbitreJDBC.getNextValueSequence(),this.vue.getNom(),this.vue.getPrenom());
						modele.ajouterArbitre(arbitre);
						vue.getPopup().setEnabled(false);
						
						Arbitre arbitreBDD = new Arbitre();
						VueListeArbitre vue = new VueListeArbitre(arbitreBDD.getTousLesArbitres());
						vue.setVisible(true);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
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
