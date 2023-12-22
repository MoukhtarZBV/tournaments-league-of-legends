package controleur;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JTable;

import ihm.VueAccueilAdmin;
import ihm.VueGestionDeLaPoule;
import ihm.VueTournoi;
import modele.ModelePoule;
import modele.Statut;

public class ControleurGestionPoule implements MouseListener {

	private VueGestionDeLaPoule vue;
	private ModelePoule modele;
	private EtatPoule etat;
	
	public enum EtatPoule {
		OUVERT, CLOTURE;
	}
	
	public ControleurGestionPoule (VueGestionDeLaPoule vue) {
		this.vue = vue;
		this.etat = EtatPoule.OUVERT;
		try {
			this.modele = new ModelePoule(this.vue.getTournoi());
			this.vue.setJTableClassement(modele.classement());
			this.vue.setJTableMatches(modele.matches()); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		switch (this.etat) {
			case OUVERT : 	
				if (e.getSource() instanceof JTable) {
						if (e.getClickCount()==2) {
							JTable source = (JTable) e.getSource();
							int columnClicked = source.getSelectedColumn();
							int rowClicked = source.getSelectedRow();
							if (columnClicked == 1 || columnClicked == 2) {
								this.modele.updateGagnant(rowClicked, columnClicked);
								if(this.modele.tousLesMatchsJouees()) {
									this.vue.setBtnCloturer(true);
								}else {
									this.vue.setBtnCloturer(false);
								}
								try {
									this.vue.setJTableMatches(this.modele.matches());
									Thread.sleep(100);
									this.vue.setJTableClassement(this.modele.classement());
								} catch (Exception e1) {
									e1.printStackTrace();
								}
							}
						}
				}
				break;
		default:
			break;
		}
		if (e.getSource() instanceof JButton) {
			JButton button = (JButton) e.getSource();
			switch (button.getText()) {
				case ("Imprimer") :
					// A FAIRE !!!
					
					break;
				case ("Cloturer Poule") :
					this.etat = EtatPoule.CLOTURE;
					try {
						this.modele.enregistrerResultat();
					} catch (Exception e2) {
						e2.printStackTrace();
					}
					// (A FAIRE) créer la finale !!!!!!!!!!!!!!!!!!!!!!!!!!!
					
					// Retour au détail du tournoi
					this.modele.changerStatusEnFinale(this.vue.getTournoi());
					this.vue.getTournoi().setStatut(Statut.FINALE);
					VueTournoi vue = new VueTournoi(this.vue.getTournoi());
					vue.setVisible(true);
					this.vue.dispose();
					break;
				case ("Retour") :
					try {
						this.modele.enregistrerResultat();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					this.vue.dispose();
					VueTournoi vueT2 = new VueTournoi(this.vue.getTournoi());
					vueT2.setVisible(true);
					break;
			}
		}
	}

	
	
	// NOT IMPLEMENTED \\
	
	@Override
	public void mousePressed(MouseEvent e) {}
	
	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}
	
}
