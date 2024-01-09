package controleur;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import ihm.Palette;
import ihm.VueAccueilAdmin;
import ihm.VueIdentification;
import ihm.VueTournoi;
import modele.Associer;
import modele.Compte;

public class ControleurIdentification implements ActionListener, WindowListener, MouseListener {
	private VueIdentification vue;
	private Compte modele;

	public ControleurIdentification(VueIdentification vue) {
		this.vue = vue;
		this.modele = new Compte();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton bouton = (JButton) e.getSource();
		if (bouton.getText().equals("Se connecter")) {
			if (this.modele.compteValide(this.vue.getLogin(), this.vue.getPassword())) {
				this.vue.dispose();
				// Si administrateur
				if (this.modele.compteIsAdmin(this.vue.getLogin(), this.vue.getPassword())) {
					VueAccueilAdmin vue = new VueAccueilAdmin();
					vue.setVisible(true);
					// CHANGER LE BONJOUR ADMIN !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! (le passer en paramètre de la vueAccueil problement)
				}
				// Si arbitre
				else {
					Associer associerBDD = new Associer();
					List<Associer> associations = associerBDD.getToutesLesAssociations();
					for (Associer ass : associations) {
						if (ass.getArbitre().getCompte().getLogin().equals(this.vue.getLogin())) {
							VueTournoi vue = new VueTournoi(ass.getTournoi());
							vue.setVisible(true);
							this.vue.dispose();
						}
					}
				}
			}else {
				// Si pas de correspondance
				this.vue.setMessageErreur("Le login et/ou le mot de passe sont incorrect");
				this.vue.setColorErreur (Color.RED);
				this.vue.setLogin("");
				this.vue.setPassword("");
			}
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
	
}
