package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import ihm.VueAccueilAdmin;
import ihm.VueCreationTournoi;
import ihm.VueListeEquipe;
import ihm.VueListeTournois;
import modele.Equipe;
import modele.Tournoi;

public class ControleurAccueil implements ActionListener{
	
	private VueAccueilAdmin vue;
	private Equipe modele;
	
	public ControleurAccueil(VueAccueilAdmin vue) {
		this.modele = new Equipe();
		this.vue = vue;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton bouton = (JButton) e.getSource();
		if(bouton.getText() == "Liste équipes") {
			Equipe eq = new Equipe();
			VueListeEquipe vue = new VueListeEquipe(eq.toutesLesEquipes());
			vue.setVisible(true);
			this.vue.dispose();	
		}
		if(bouton.getText() == "Liste Tournois") {
			Tournoi t = new Tournoi();
			VueListeTournois vue = new VueListeTournois(t.tousLesTournois());
			vue.setVisible(true);
			this.vue.dispose();	
		}
	}

}
