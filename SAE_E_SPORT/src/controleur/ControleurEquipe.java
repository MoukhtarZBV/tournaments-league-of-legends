package controleur;

import java.awt.Color;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import ihm.VueEquipe;
import ihm.VueListeEquipe;
import ihm.VueTournoi;
import modele.Equipe;

public class ControleurEquipe implements ActionListener{
	private VueEquipe vue;
	private Equipe modele;

	public ControleurEquipe(VueEquipe vue) {
		this.vue = vue;
		this.modele = new Equipe();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton bouton = (JButton) e.getSource();
		if (bouton.getText().equals("Sauvegarder")) {
			try {
				String name = this.vue.getNomEquipe().replace(" ", "");
				if (!name.equals("")) {
					this.modele.miseAJourEquipe((new Equipe(this.vue.getIdEquipe(),this.vue.getNomEquipe(),this.vue.getRangEquipe(), this.vue.getPaysEquipe())));
					this.vue.dispose();
					if(this.vue.getPapa() == null) {
						VueListeEquipe vue = new VueListeEquipe(this.modele.toutesLesEquipes());
						vue.setVisible(true);
					} else {
						VueTournoi vue = new VueTournoi(this.vue.getPapa());
						vue.setVisible(true);
					}
				}else {
					this.vue.setColorMessage(new Color(255, 204, 204));
					this.vue.setMsgErreur("Le nom d'équipe ne peut pas être vide");
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		if(bouton.getText().equals("Retour")) {
			this.vue.dispose();
			try {
				if(this.vue.getPapa() == null) {
					VueListeEquipe vue = new VueListeEquipe(this.modele.toutesLesEquipes());
					vue.setVisible(true);
				} else {
					VueTournoi vue = new VueTournoi(this.vue.getPapa());
					vue.setVisible(true);
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
				
		}
		
	}
