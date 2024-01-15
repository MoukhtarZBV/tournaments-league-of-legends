package controleur;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JButton;
import javax.swing.JTable;

import ihm.Ecran;
import ihm.VueGestionDeLaPoule;
import ihm.VueTournoi;
import modele.Compte;
import modele.ModelePoule;
import modele.Statut;
import modele.TypeCompte;

public class ControleurGestionPoule implements MouseListener, ActionListener, WindowListener {

	private VueGestionDeLaPoule vue;
	private ModelePoule modele;
	private boolean modifie;
	
	public ControleurGestionPoule (VueGestionDeLaPoule vue) {
		this.vue = vue;
		this.modifie = false;
		
		try {
			this.modele = new ModelePoule(this.vue.getTournoi());
			this.vue.setJTableClassement(modele.classement());
			this.vue.setJTableMatches(modele.matches());
			if (!modele.tousLesMatchsJouees() || Compte.getCompteConnecte().getType() == TypeCompte.ADMINISTRATEUR) {
				this.vue.setActifBoutonCloturer(false);
				
			} else if (this.vue.getTournoi().getStatut() != Statut.EN_COURS) {
				this.vue.setVisibleBoutonCloturer(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JButton) {
			JButton button = (JButton) e.getSource();
			
			switch (button.getText()) {
				case ("Imprimer") :
					this.vue.printClassemenToPDF();
					break;
					
				case ("Cloturer Poule") :
					try {
						this.modele.enregistrerResultat();
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				
					this.modele.creerFinale(this.vue.getTournoi());
					this.modele.changerStatusEnFinale(this.vue.getTournoi());
					this.vue.getTournoi().setStatut(Statut.FINALE);
					
					Ecran.update(this.vue);
					VueTournoi vue = new VueTournoi(this.vue.getTournoi());
					vue.setVisible(true);
					break;
					
				case ("Retour") :
					if (this.modifie) {
						this.modele.enregistrerResultat();
					}
				
					Ecran.update(this.vue);
					VueTournoi vueT2 = new VueTournoi(this.vue.getTournoi());
					vueT2.setVisible(true);
					break;
			}
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (this.vue.getTournoi().getStatut() == Statut.EN_COURS) {
			if (e.getSource() instanceof JTable && Compte.getCompteConnecte().getType() == TypeCompte.ARBITRE) {
				JTable source = (JTable) e.getSource();
				
				int columnClicked = source.getSelectedColumn();
				int rowClicked = source.getSelectedRow();
				
				if (columnClicked == 1 || columnClicked == 2) {
					this.modele.updateGagnant(rowClicked, columnClicked);
					
					if (this.modele.tousLesMatchsJouees()) {
						this.vue.setActifBoutonCloturer(true);
					}
					
					try {
						this.vue.setJTableMatches(this.modele.matches());
						Thread.sleep(100);
						this.vue.setJTableClassement(this.modele.classement());
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
				this.modifie = true;
			}
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
	public void mousePressed(MouseEvent e) {}
	
	@Override
	public void mouseReleased(MouseEvent e) {}
	
}
