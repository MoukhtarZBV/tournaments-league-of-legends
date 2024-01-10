package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;

import ihm.Palette;
import ihm.VueAccueilAdmin;
import ihm.VueAjouterArbitre;
import ihm.VueEquipe;
import ihm.VueListeArbitre;
import ihm.VueListeEquipe;
import ihm.VueListeTournois;
import modele.Arbitre;
import modele.Associer;
import modele.Equipe;
import modele.Statut;

public class ControleurListeArbitre implements MouseListener, ActionListener {
	
	private VueListeArbitre vue;
	private Arbitre modele;
	private Arbitre arbitreSelected;

	public ControleurListeArbitre(VueListeArbitre vue) {
		this.vue = vue;
		this.modele = new Arbitre();
		System.out.println(modele.getTousLesArbitres());
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getSource() instanceof JList) {
			@SuppressWarnings("unchecked")
			JList<String> list = (JList<String>) e.getSource();
			this.vue.getBtnSuppr().setEnabled(true);
			String arbitre = ((String) list.getSelectedValue());	
			setArbitreSelectionne(arbitre);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
	    JButton bouton = (JButton) e.getSource();
	    if (bouton.getName().equals("Retour")) {
	    	VueAccueilAdmin vue = new VueAccueilAdmin();
			vue.setVisible(true);
			this.vue.dispose();
	    } else if (bouton.getName().equals("Rechercher")){
			this.vue.updateListeArbitres(this.modele.arbitresContenant(this.vue.getArbitres(), this.vue.getSearch()));
	    } else if (bouton.getName().equals("Ajouter")) {
	    	VueAjouterArbitre vue = new VueAjouterArbitre();
	    	vue.setVisible(true);
	    	this.vue.dispose();
	    } else if (bouton.getName().equals("Supprimer")) {
	    	if(this.arbitreSelected != null) {
	    		if (this.arbitreSelected.getCompte() != null) {
	    			JOptionPane.showMessageDialog(null, "Impossible de supprimer cet arbitre car il arbitre actuellement un tournoi", "Suppression d'un arbitre", JOptionPane.ERROR_MESSAGE);
	    		} else {
	    			int choix = afficherPopUpConfirmation(arbitreSelected.getNom() + " " + arbitreSelected.getPrenom()); 
	    			if (choix == JOptionPane.YES_OPTION) {
	    				this.modele.supprimerArbitre(this.arbitreSelected);
	    				this.vue.getArbitres().remove(this.vue.getArbitres().indexOf(this.arbitreSelected));
	    				this.vue.updateListeArbitres(this.modele.arbitresContenant(this.vue.getArbitres(), this.vue.getSearch()));
	    			} 
	    		}
	    	}
	    }
	}

	
	@Override
	public void mouseEntered(MouseEvent e) {
		if(e.getSource() instanceof JButton) {
			JButton b = (JButton)e.getSource();
			b.setBackground(Palette.LIGHT_PURPLE);
		}
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		if(e.getSource() instanceof JButton) {
			JButton b = (JButton)e.getSource();
			b.setBackground(Palette.WHITE);
		}	
	}

	private int afficherPopUpConfirmation(String nomArbitre) {
		String[] options = { "Oui", "Non"}; 
		int choix = JOptionPane.showOptionDialog( 
		        null,
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
	
	
	// NOT IMPLEMENTED \\
	@Override
	public void mouseClicked(MouseEvent e) {}
	
	@Override
	public void mousePressed(MouseEvent e) {}
	
}
