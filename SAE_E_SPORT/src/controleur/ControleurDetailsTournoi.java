package controleur;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import javax.swing.JButton;

import ihm.VueGestionDeLaPoule;
import ihm.VueImportation;
import ihm.VueTournoi;
import modele.ModelePoule;
import modele.Tournoi;

public class ControleurDetailsTournoi implements ActionListener {
	
	private VueTournoi vue;
	private Tournoi modele;

	public ControleurDetailsTournoi(VueTournoi vue) {
		this.modele = new Tournoi();
		this.vue = vue;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton bouton = (JButton) e.getSource();
		if (bouton.getName().equals("Importer des équipes")) {
			VueImportation vueImportation = new VueImportation(vue.getTournoi());
			vueImportation.setVisible(true);
		} else if (bouton.getName().equals("Retour")) {
			vue.dispose();
		} else if (bouton.getName().equals("Gérer la poule")) {
			vue.getTournoi().generationPoule();
			ModelePoule modelePoule;
			try {
				modelePoule = new ModelePoule(vue.getTournoi());
				Object[][] classement = modelePoule.classement();
	            Object[][] parties = modelePoule.matches();
	            
	            VueGestionDeLaPoule frame = new VueGestionDeLaPoule(vue.getTournoi());
	            
	            frame.setJTableMatches(parties);
	            frame.setJTableClassement(classement);
	            
	            frame.setVisible(true);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            
		}
	}

}
