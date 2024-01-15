package controleur;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;

import dao.ArbitreJDBC;
import ihm.Ecran;
import ihm.VueAjouterArbitre;
import ihm.VueListeArbitre;
import modele.Arbitre;

public class ControleurAjouterArbitre implements ActionListener, FocusListener, MouseListener, WindowListener {

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
				VueListeArbitre vueArbitres = new VueListeArbitre(arbitreBDD.getTousLesArbitres(), false, null);
				vueArbitres.setVisible(true);
			}
			if (bouton.getName().equals("Valider")) {
					String prenom = this.vue.getPrenom().replace(" ", "");
					String nom = this.vue.getNom().replace(" ", "");
					if (!nom.equals("") && !prenom.equals("") && new Arbitre().getByNomPrenom(vue.getNom(),vue.getPrenom()) == null) {
						Arbitre arbitre = new Arbitre(ArbitreJDBC.getNextValueSequence(),this.vue.getNom(),this.vue.getPrenom());
						modele.ajouterArbitre(arbitre);
						vue.getPopup().setEnabled(false);

						Ecran.update(this.vue);	
						VueListeArbitre vue = new VueListeArbitre(new Arbitre().getTousLesArbitres(), false, null);
						vue.setVisible(true);
					}
					else if (nom.equals("")) {
						this.vue.getPopup().setErreur("Le nom de l'arbitre ne peut pas être vide");
					} else if(prenom.equals("")) {
						this.vue.getPopup().setErreur("Le prénom de l'arbitre ne peut pas être vide");
					} else {
						this.vue.getPopup().setErreur("Un arbitre portant ce nom et prenom existe déjà");
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

	@Override
	public void windowOpened(WindowEvent e) {
		Ecran.closeLast();
	}
	
	
	// NOT IMPLEMENTED \\

	@Override
	public void windowClosing(WindowEvent e) {}

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

	@Override
	public void focusLost(FocusEvent e) {}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

}
