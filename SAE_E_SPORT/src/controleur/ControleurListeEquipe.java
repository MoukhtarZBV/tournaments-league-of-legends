package controleur;

import java.awt.Cursor;
import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JTextField;

import ihm.Ecran;
import ihm.Palette;
import ihm.VueAccueilAdmin;
import ihm.VueEquipe;
import ihm.VueListeEquipe;
import modele.Equipe;
import modele.ModeleListeEquipes;

public class ControleurListeEquipe implements MouseListener, ActionListener, WindowListener {
	
	private VueListeEquipe vue;
	private ModeleListeEquipes modele;

	public ControleurListeEquipe(VueListeEquipe vue) {
		this.vue = vue;
		this.modele = new ModeleListeEquipes();
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getSource() instanceof JList) {
			@SuppressWarnings("unchecked")
			JList<String> list = (JList<String>) e.getSource();
			if (e.getClickCount() == 2) {
				try {;
					String nomEq = ((String) list.getSelectedValue()).substring(6, 55);
					
					Ecran.update(this.vue);	
					VueEquipe vue = new VueEquipe(this.modele.getEquipes(), new Equipe().getEquipeParNom(nomEq), null);
					vue.setVisible(true);
					
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JTextField) {
			this.rechercherParNomEtRang();;
		} else if (e.getSource() instanceof JButton) {
			JButton bouton = (JButton) e.getSource();
			
			switch (bouton.getName()) {
				case "Retour": 
					Ecran.update(this.vue);
					VueAccueilAdmin vue = new VueAccueilAdmin();
					vue.setVisible(true);
					break;
					
				case "Rechercher" :
					this.rechercherParNomEtRang();
					break;
	
				case "Trier" : 
					// Lister par Rang
					if(this.vue.getTriParNom()) {
						this.vue.setTriParNom(false);
						this.vue.updateListeEquipes(this.modele.trierParRang(this.vue.getSearch().toUpperCase()));
						this.vue.setBtnSort("Trier par nom");
	
					// Lister par Nom d'équipe
					} else {
						this.vue.setTriParNom(true);
						this.vue.updateListeEquipes(this.modele.trierParNom(this.vue.getSearch().toUpperCase()));
						this.vue.setBtnSort("Trier par rang");
					}
					break;
			}
		}
	}

	private void rechercherParNomEtRang() {
		try {
			// Lister par Nom d'équipe
			if(this.vue.getTriParNom()) {
				this.vue.updateListeEquipes(this.modele.trierParNom(this.vue.getSearch().toUpperCase()));
				this.vue.setBtnSort("Trier par rang");

			// Lister par Rang
			} else {
				this.vue.updateListeEquipes(this.modele.trierParRang(this.vue.getSearch().toUpperCase()));
				this.vue.setBtnSort("Trier par nom");
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		if(e.getSource() instanceof JButton) {
			JButton b = (JButton)e.getSource();
			
			if(b.getName().equals("Rechercher")) {
				b.setBackground(Palette.LIGHT_PURPLE);	
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
			
			if(b.getName().equals("Rechercher")) {
				b.setBackground(Palette.WHITE);	
			} else {
				b.setBackground(b.getBackground().darker());
			}
			
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
	public void mouseClicked(MouseEvent e) {}
	
	@Override
	public void mousePressed(MouseEvent e) {}
	
}
