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
import javax.swing.JTextField;

import ihm.Palette;
import ihm.VueAccueilAdmin;
import ihm.VueEquipe;
import ihm.VueListeEquipe;
import modele.Equipe;

public class ControleurListeEquipe implements MouseListener, ActionListener {
	
	private VueListeEquipe vue;
	private Equipe modele;

	public ControleurListeEquipe(VueListeEquipe vue) {
		this.vue = vue;
		this.modele = new Equipe();
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getSource() instanceof JList) {
			@SuppressWarnings("unchecked")
			JList<String> list = (JList<String>) e.getSource();
			if (e.getClickCount() == 2) {
				try {
					List<Equipe> equipes = (this.modele.getToutesLesEquipes());
					String nomEq = ((String) list.getSelectedValue()).substring(6, 55);
					
					VueEquipe vue = new VueEquipe(equipes, this.modele.getEquipeParNom(nomEq), null);
					vue.setVisible(true);
					this.vue.dispose();
					
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		List<Equipe> equipes;
		if (e.getSource() instanceof JTextField) {


		} else if (e.getSource() instanceof JButton) {
			JButton bouton = (JButton) e.getSource();
			switch (bouton.getName()) {
			case "Retour": 
				this.vue.dispose();
				VueAccueilAdmin vue = new VueAccueilAdmin();
				vue.setVisible(true);
				break;
			case "Rechercher" :
				try {
					// Lister par Nom d'équipe
					if(this.vue.getTriParNom()) {
						equipes = this.modele.getToutesLesEquipes();
						List<String> nomEquipes = equipes.stream()
								.sorted((x,y)-> x.getNom().compareTo(y.getNom()))
								.map(eq -> String.format("%-5d %-50s", eq.getRang(), eq.getNom()))
								.collect(Collectors.toList());

						List<String> nomEquipesTri = nomEquipes.stream()
								.filter(eq -> eq.toUpperCase().contains(this.vue.getSearch().toUpperCase()))
								.collect(Collectors.toList());

						this.vue.updateListeEquipes(nomEquipesTri);
						this.vue.setBtnSort("Trier par rang");

						// Lister par Rang
					}else {
						equipes = this.modele.getToutesLesEquipes();
						List<String> nomEquipes = equipes.stream()
								.sorted((x,y)-> {
									if (x.getRang()>y.getRang()){
										return 1;
									}else if(x.getRang()<y.getRang()) {
										return -1;
									}else {
										return 0;
									}
								})
								.map(eq -> String.format("%-5d %-50s", eq.getRang(), eq.getNom()))
								.collect(Collectors.toList());

						List<String> nomEquipesTri = nomEquipes.stream()
								.filter(eq -> eq.toUpperCase().contains(this.vue.getSearch().toUpperCase()))
								.collect(Collectors.toList());

						this.vue.updateListeEquipes(nomEquipesTri);
						this.vue.setBtnSort("Trier par nom");
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				break;

			case "Trier" : 
				// Lister par Rang
				if(this.vue.getTriParNom()) {
					this.vue.setTriParNom(false);
					equipes = this.modele.getToutesLesEquipes();
					List<String> nomEquipes = equipes.stream()
							.sorted((x,y)-> {
								if (x.getRang()>y.getRang()){
									return 1;
								} else if (x.getRang()<y.getRang()) {
									return -1;
								} else {
									return 0;
								}
							})
							.map(eq -> String.format("%-5d %-50s", eq.getRang(), eq.getNom()))
							.collect(Collectors.toList());

					List<String> nomEquipesTri = nomEquipes.stream()
							.filter(eq -> eq.toUpperCase().contains(this.vue.getSearch().toUpperCase()))
							.collect(Collectors.toList());

					this.vue.updateListeEquipes(nomEquipesTri);
					this.vue.setBtnSort("Trier par nom");

					// Lister par Nom d'équipe
				}else {
					this.vue.setTriParNom(true);
					equipes = this.modele.getToutesLesEquipes();
					List<String> nomEquipes = equipes.stream()
							.sorted((x,y)-> x.getNom().compareTo(y.getNom()))
							.map(eq -> String.format("%-5d %-50s", eq.getRang(), eq.getNom()))
							.collect(Collectors.toList());

					List<String> nomEquipesTri = nomEquipes.stream()
							.filter(eq -> eq.toUpperCase().contains(this.vue.getSearch().toUpperCase()))
							.collect(Collectors.toList());

					this.vue.updateListeEquipes(nomEquipesTri);
					this.vue.setBtnSort("Trier par rang");
				}
				break;
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
