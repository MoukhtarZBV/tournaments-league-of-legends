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

import ihm.Palette;
import ihm.VueAccueilAdmin;
import ihm.VueAjouterArbitre;
import ihm.VueEquipe;
import ihm.VueListeArbitre;
import ihm.VueListeEquipe;
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
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getSource() instanceof JList) {
			@SuppressWarnings("unchecked")
			JList<String> list = (JList<String>) e.getSource();
			this.vue.getBtnSuppr().setEnabled(true);
			String arbitre = ((String) list.getSelectedValue());
				
			String nom;
			String prenom;
			nom = arbitre.substring(0, arbitre.indexOf(' ')); // Extrait le nom (jusqu'à l'espace)
			prenom = arbitre.substring(arbitre.indexOf(' ') + 1, arbitre.length());
			prenom = prenom.replace(" ", "");
			this.arbitreSelected = new Arbitre().getByNomPrenom(nom, prenom);
			System.out.println(arbitreSelected);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
	    JButton bouton = (JButton) e.getSource();
	    List<Arbitre> arbitres;
	    if(bouton.getName().equals("Retour")) {
	    	this.vue.dispose();
	    	VueAccueilAdmin vue = new VueAccueilAdmin();
			vue.setVisible(true);
	    } 
	    if (bouton.getName().equals("Rechercher")){
			try {
				arbitres = this.modele.getTousLesArbitres();
				List<String> nomArbitres = arbitres.stream()
			            .map(a -> a.getNom() + " "+ a.getPrenom())
			            .sorted((x,y)-> x.compareTo(y))
			            .collect(Collectors.toList());
	
			    List<String> nomArbitresTri = nomArbitres.stream()
			            .filter(eq -> eq.toUpperCase().contains(this.vue.getSearch().toUpperCase()))
			            .sorted((x,y)-> x.compareTo(y))
			            .collect(Collectors.toList());
	
			    // Mise à jour du modèle de la JList dans la vue
			    this.vue.updateListeArbitres(nomArbitresTri);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
	    }
	    if(bouton.getName().equals("Ajouter")) {
	    	VueAjouterArbitre vue = new VueAjouterArbitre();
	    	vue.setVisible(true);
	    	this.vue.dispose();
	    }
	    if(bouton.getName().equals("Supprimer")) {
	    	if(this.vue.getBtnSuppr().isEnabled()) {
	    		if(this.arbitreSelected.getCompte() != null) {
	    			System.out.println("Impossible de supprimer cette arbitre");
	    			// METTRE UN PANEL ERREUR 
	    		}else {
	    			this.modele.supprimerArbitre(this.arbitreSelected);
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

	
	
	// NOT IMPLEMENTED \\
	@Override
	public void mouseClicked(MouseEvent e) {}
	
	@Override
	public void mousePressed(MouseEvent e) {}
	
}
