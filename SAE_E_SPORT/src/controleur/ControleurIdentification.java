package controleur;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.List;

import javax.swing.JButton;

import ihm.VueAccueilAdmin;
import ihm.VueEquipe;
import ihm.VueIdentification;
import ihm.VueListeEquipe;
import ihm.VueTournoi;
import modele.Associer;
import modele.Compte;
import modele.Equipe;
import modele.Tournoi;

public class ControleurIdentification implements ActionListener, WindowListener {
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
			// Verifie que le correspondance compte
			for (Compte compte : this.modele.getTousLesComptes()) {
				if (compte.getLogin().equals(this.vue.getLogin())) {
					if (compte.getMotDePasse().equals(this.vue.getPassword())) {
						this.vue.dispose();
						// Si l'utilisateur est un administrateur
						if (compte.getType().denomination() == "Administrateur") {
							VueAccueilAdmin vue = new VueAccueilAdmin();
							vue.setVisible(true);
							// CHANGER LE BONJOUR ADMIN !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! (le passer en paramètre de la vueAccueil problement)
						}
						// Si l'utilisateur est un arbitre
						else {
							Associer associerBDD = new Associer();
							List<Associer> associations = associerBDD.getToutesLesAssociations();
							for (Associer ass : associations) {
								if (ass.getArbitre().getCompte().getLogin().equals(compte.getLogin())) {
									VueTournoi vue = new VueTournoi(ass.getTournoi());
									vue.setVisible(true);
									this.vue.dispose();
								}
							}
						}
					}
				}
			}
			// Si pas de correspondance
			this.vue.setMessageErreur("Le login et/ou le mot de passe sont incorrect");
			this.vue.setColorErreur (Color.RED);
			this.vue.setLogin("");
			this.vue.setPassword("");
		}
		if(bouton.getText().equals("Retour")) {
			this.vue.dispose();
		}
				
	}

	@Override
	public void windowClosing(WindowEvent e) {
		this.vue.dispose();
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
	
}
