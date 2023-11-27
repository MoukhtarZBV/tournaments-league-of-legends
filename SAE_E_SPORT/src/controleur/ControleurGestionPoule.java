package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;

import ihm.VueGestionDeLaPoule;
import modele.ModelePoule;
import modele.Tournoi;

public class ControleurGestionPoule implements ActionListener {

	private VueGestionDeLaPoule vue;
	private ModelePoule modele;

	public ControleurGestionPoule(VueGestionDeLaPoule vue) {
		this.vue = vue;
		this.modele = new ModelePoule();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource();
		List<Tournoi> tournois = null;
		try {
			 tournois = this.modele.listeTournois();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		switch (button.getText()) {
			case ("Imprimer") :
				break;
			case ("Cloturer") : 
				
				break;
			case ("Retour") :
				this.vue.dispose();
			break;
		}

	}

}
