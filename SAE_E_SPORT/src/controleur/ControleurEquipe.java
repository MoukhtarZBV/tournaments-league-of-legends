package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;

import dao.ConnectionJDBC;
import dao.EquipeJDBC;
import ihm.VueEquipe;
import ihm.VueListeEquipe;
import modele.Equipe;

public class ControleurEquipe implements ActionListener{
	public enum Etat {
		ATTENTE_CLIC_SAVE, FIN_SAVE
	}
	private VueEquipe vue;
	private Etat etat;

	public ControleurEquipe(VueEquipe vue) {
		this.vue = vue;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton bouton = (JButton) e.getSource();
		EquipeJDBC ejdbc = new EquipeJDBC();
		if (bouton.getText().equals("Sauvegarder")) {
			try {
				ejdbc.update(new Equipe(this.vue.getIdEquipe(),this.vue.getNomEquipe(),this.vue.getRangEquipe(), this.vue.getPaysEquipe()));
				this.vue.dispose();
				VueListeEquipe vue = new VueListeEquipe(ejdbc.getAll());
				vue.setVisible(true);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		if(bouton.getText().equals("Retour")) {
			this.vue.dispose();
			VueListeEquipe vue;
			try {
				vue = new VueListeEquipe(ejdbc.getAll());
				vue.setVisible(true);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
				
		}
		
	}
