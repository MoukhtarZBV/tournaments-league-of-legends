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
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import ihm.Ecran;
import components.PopUpSuppression;
import ihm.Palette;
import ihm.VueAccueilAdmin;
import ihm.VueAjouterArbitre;
import ihm.VueListeArbitre;
import ihm.VueTournoi;
import modele.Arbitre;
import modele.Statut;
import modele.Tournoi;

public class ControleurListeArbitre implements MouseListener, ActionListener, FocusListener, WindowListener {

	private VueListeArbitre vue;
	private Arbitre modele;
	private Arbitre arbitreSelected;

	public ControleurListeArbitre(VueListeArbitre vue) {
		this.vue = vue;
		this.modele = new Arbitre();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getSource() instanceof JList) {
			@SuppressWarnings("unchecked")
			JList<String> list = (JList<String>) e.getSource();
			String arbitre = ((String) list.getSelectedValue());	
			setArbitreSelectionne(arbitre);
			if (this.vue.enModeAjout() && this.vue.getArbitresAttribues().size() < 3) {
				this.vue.setActifBtnAttribuer(true);
			} else if (!this.vue.enModeAjout()) {
				this.vue.setActifBtnSupprimer(true);
			} 
		}
	}
	
	@Override
    public void focusGained(FocusEvent e) {
		if (e.getSource() instanceof JTextField) {
			JTextField searchText = (JTextField) e.getSource();
			if (searchText.getText().equals("Filtrer arbitres par nom ou prénom")) {
	            searchText.setText("");
	            searchText.setForeground(Palette.WHITE);
	        }
		}
    }
	
    @Override
    public void focusLost(FocusEvent e) {
		if (e.getSource() instanceof JTextField) {
	    	JTextField searchText = (JTextField) e.getSource();
	        if (searchText.getText().isEmpty()) {
	            searchText.setForeground(Color.LIGHT_GRAY);
	            searchText.setText("Filtrer arbitres par nom ou prénom");
	        }
		}
    }
	
	@Override
	public void actionPerformed(ActionEvent e) {
	    
		if (e.getSource() instanceof JButton) {
			JButton bouton = (JButton) e.getSource();
		    
		    if (bouton.getName().equals("Retour")) {
				Ecran.update(this.vue);	
				
				if (this.vue.getTournoi() == null) {
					VueAccueilAdmin vue = new VueAccueilAdmin();
					vue.setVisible(true);
				} else {
					VueTournoi vue = new VueTournoi(this.vue.getTournoi());
					vue.setVisible(true);
				}
				
		    } else if (bouton.getName().equals("Rechercher")){
				this.vue.updateListeArbitres(this.modele.arbitresContenant(this.vue.getArbitres(), this.vue.getSearch()));
				
		    } else if (bouton.getName().equals("Ajouter")) {
				Ecran.update(this.vue);	
		    	VueAjouterArbitre vue = new VueAjouterArbitre();
		    	vue.setVisible(true);
	
		    } else if (bouton.getName().equals("Vider")) {
		    	viderListeArbitresAttribues();
		    	
		    } else if (bouton.getName().equals("Attribuer")) {
		    	ajouterArbitreAuxArbitresAttribues();
		    	
		    } else if (bouton.getName().equals("Confirmer")) {
		    	confirmerAttributionArbitres();
		    	
		    } else if (bouton.getName().equals("Supprimer")) {
		    	supprimerArbitreSelectionne();
		    }
		} else if (e.getSource() instanceof JTextField) {
			this.vue.updateListeArbitres(this.modele.arbitresContenant(this.vue.getArbitres(), this.vue.getSearch()));
		}
	}

	private void supprimerArbitreSelectionne() {
		if (this.arbitreSelected != null) {
			if (this.arbitreSelected.getCompte() != null) {
				JOptionPane.showMessageDialog(null, "Impossible de supprimer cet arbitre car il arbitre actuellement un tournoi", "Suppression d'un arbitre", JOptionPane.ERROR_MESSAGE);
			} else {
				int choix = afficherPopUpConfirmation(arbitreSelected.getNom() + " " + arbitreSelected.getPrenom()); 
				if (choix == JOptionPane.YES_OPTION) {
					this.modele.supprimerArbitre(this.arbitreSelected);
					this.vue.getArbitres().remove(this.vue.getArbitres().indexOf(this.arbitreSelected));
					this.vue.updateListeArbitres(this.modele.arbitresContenant(this.vue.getArbitres(), this.vue.getSearch()));
					this.vue.setActifBtnSupprimer(false);
				} 
			}
		}
	}

	private void confirmerAttributionArbitres() {
		Tournoi tournoiBDD = new Tournoi();
		tournoiBDD.associerArbitresTournoi(this.vue.getTournoi(), this.vue.getArbitresAttribues());
		tournoiBDD.changerStatutTournoi(this.vue.getTournoi(), Statut.A_VENIR);
		this.vue.getTournoi().setStatut(Statut.A_VENIR);
		
		Ecran.update(this.vue);
		VueTournoi vueTournoi = new VueTournoi(this.vue.getTournoi());
		vueTournoi.setVisible(true);
	}

	private void viderListeArbitresAttribues() {
		this.vue.viderArbitresAttribues();
		this.vue.setActifBtnVider(false);
		this.vue.setActifBtnConfirmer(false);
		this.vue.updateListeArbitres(this.modele.arbitresContenant(this.modele.getTousLesArbitres(), this.vue.getSearch()));
		this.vue.afficherMessageArbitres();
	}

	private void ajouterArbitreAuxArbitresAttribues() {
		this.vue.afficherArbitresAttribue(arbitreSelected);
		this.vue.updateListeArbitres(this.modele.arbitresContenant(this.vue.getArbitres(), this.vue.getSearch()));
		this.vue.setActifBtnVider(true);
		this.vue.setActifBtnAttribuer(false);
		this.vue.setActifBtnConfirmer(true);
	}

	
	@Override
	public void mouseEntered(MouseEvent e) {
		if(e.getSource() instanceof JButton) {
			JButton b = (JButton)e.getSource();
			
			if(b.isEnabled()) {
				if(b.getName().equals("Rechercher")) {
					b.setBackground(Palette.LIGHT_PURPLE);					
				} else if(b.getName().equals("Supprimer")) {
					b.setBackground(Palette.FOND_ERREUR);		
				} else {
					b.setBackground(b.getBackground().brighter());
				}
				
				b.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
		}
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
		if(e.getSource() instanceof JButton) {
			JButton b = (JButton)e.getSource();
			
			if(b.getName().equals("Rechercher")) {
				b.setBackground(Palette.WHITE);	
			} else if(b.getName().equals("Supprimer")) {
				b.setBackground(Palette.GRAY);	
			} else {
				b.setBackground(b.getBackground().darker());
			}
			
			b.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
	}

	private int afficherPopUpConfirmation(String nomArbitre) {
		String[] options = { "Oui", "Non"}; 
		int choix = PopUpSuppression.afficherPopUpConfirmation(
		        "Voulez vous supprimer l'arbitre " + nomArbitre + " ?",
		        "Suppression d'un arbitre",
		        JOptionPane.YES_NO_OPTION,
		        JOptionPane.QUESTION_MESSAGE,
		        null,
		        options,
		        options[1]
		);
		return choix;
	}
	
	private void setArbitreSelectionne(String arbitre) {
		String nom;
		String prenom;
		nom = arbitre.substring(0, arbitre.indexOf(' ')); // Extrait le nom (jusqu'à l'espace)
		prenom = arbitre.substring(arbitre.indexOf(' ') + 1, arbitre.length());
		prenom = prenom.replace(" ", "");
		this.arbitreSelected = new Arbitre().getByNomPrenom(nom, prenom);
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
