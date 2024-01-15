package controleur;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;

import ihm.Ecran;
import ihm.Palette;
import ihm.VueAccueilAdmin;
import ihm.VueIdentification;
import ihm.VueTournoi;
import modele.Compte;
import modele.Tournoi;

public class ControleurIdentification implements ActionListener, WindowListener, MouseListener, KeyListener {
	
	private VueIdentification vue;
	private Compte modele;

	public ControleurIdentification(VueIdentification vue) {
		this.vue = vue;
		this.modele = new Compte();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton bouton = (JButton) e.getSource();
		if (bouton.getName().equals("Connexion")) {
			this.connexion();
		}
			
		if(bouton.getName().equals("Quitter")) {
			this.vue.dispose();
		}
				
	}

	@Override
	public void windowClosing(WindowEvent e) {
		this.vue.dispose();
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		if(e.getSource() instanceof JButton) {
			JButton b = (JButton)e.getSource();
			
			if(b.getName().equals("Connexion")) {
				b.setBackground(Palette.LIGHT_PURPLE.brighter());	
				b.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Palette.LIGHT_PURPLE.brighter()));
			} else {
				b.setBackground(b.getBackground().brighter());
			}
			
			b.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
	}
	
	public void connexion() {
		if (this.modele.compteValide(this.vue.getLogin(), this.vue.getPassword())) {
			Ecran.update(this.vue);	
			// Si administrateur
			if (this.modele.compteIsAdmin(this.vue.getLogin(), this.vue.getPassword())) {
				VueAccueilAdmin vue = new VueAccueilAdmin();
				vue.setVisible(true);
			}
			// Si arbitre
			else {
				Tournoi tournoiBDD = new Tournoi();
				List<Tournoi> tournois = tournoiBDD.getTousLesTournois();
				for (Tournoi tournoi : tournois) {
					if (tournoi.getCompte().getLogin().equals(this.vue.getLogin())) {
						Ecran.update(this.vue);	
						VueTournoi vue = new VueTournoi(tournoi);
						vue.setVisible(true);
					}
				}
			}
			//this.vue.dispose();
		} else {
			// Si pas de correspondance
			this.vue.getPopup().setErreur("Login et/ou mot de passe incorrect(s).");
			this.vue.setLogin("");
			this.vue.setPassword("");
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if(e.getSource() instanceof JButton) {
			JButton b = (JButton)e.getSource();
			
			if(b.getName().equals("Connexion")) {
				b.setBackground(Palette.WHITE);	
				b.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Palette.WHITE));
			} else {
				b.setBackground(b.getBackground().darker());
			}
			
			b.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
	}	
	
	@Override
	public void keyPressed(KeyEvent e) {
	    if (e.getKeyCode() == KeyEvent.VK_ENTER){
	        this.connexion();
	    }
	}

		
	// NOT IMPLEMENTED \\
	
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
	
	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {}
	
}
