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
import ihm.VueEquipe;
import ihm.VueListeEquipe;
import modele.Equipe;

public class ControleurListeEquipe implements MouseListener, ActionListener, WindowListener {
	
	private VueListeEquipe vue;
	private Equipe modele;

	public ControleurListeEquipe(VueListeEquipe vue) {
		this.vue = vue;
		this.modele = new Equipe();
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getSource() instanceof JList) {
			JList list = (JList) e.getSource();
			if (e.getClickCount() == 2) {
				try {
					List<Equipe> equipes = (this.modele.toutesLesEquipes());
					String nomEq = ((String) list.getSelectedValue()).substring(6, 55);
					
					VueEquipe vue = new VueEquipe(equipes, this.modele.equipeParNom(nomEq), null);
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
	    JButton bouton = (JButton) e.getSource();
	    List<Equipe> equipes;
	    if(bouton.getName().equals("Retour")) {
	    	this.vue.dispose();
	    	VueAccueilAdmin vue = new VueAccueilAdmin();
			vue.setVisible(true);
	    } 
	    
	    if(bouton.getName().equals("Rechercher")) {
			try {
				// Lister par Nom d'équipe
		    	if(this.vue.getTriParNom()) {
		    		equipes = this.modele.toutesLesEquipes();
					List<String> nomEquipes = equipes.stream()
							.sorted((x,y)-> x.getNom().compareTo(y.getNom()))
				            .map(eq -> String.format("%-5d %-50s", eq.getRang(), eq.getNom()))
				            .collect(Collectors.toList());
					
				    List<String> nomEquipesTri = nomEquipes.stream()
				            .filter(eq -> eq.toUpperCase().contains(this.vue.getSearch().toUpperCase()))
				            .collect(Collectors.toList());
				    
				    this.vue.updateListeEquipes(nomEquipesTri);
				    
				// Lister par Rang
		    	}else {
		    		equipes = this.modele.toutesLesEquipes();
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
		    	}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
	    }
	    
	    if(bouton.getName().equals("Trier")) {
	    	// Lister par Rang
	    	if(this.vue.getTriParNom()) {
	    		this.vue.setTriParNom(false);
	    		equipes = this.modele.toutesLesEquipes();
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
			    
			// Lister par Nom d'équipe
	    	}else {
	    		this.vue.setTriParNom(true);
	    		equipes = this.modele.toutesLesEquipes();
				List<String> nomEquipes = equipes.stream()
						.sorted((x,y)-> x.getNom().compareTo(y.getNom()))
			            .map(eq -> String.format("%-5d %-50s", eq.getRang(), eq.getNom()))
			            .collect(Collectors.toList());
				
			    List<String> nomEquipesTri = nomEquipes.stream()
			            .filter(eq -> eq.toUpperCase().contains(this.vue.getSearch().toUpperCase()))
			            .collect(Collectors.toList());
			    
			    this.vue.updateListeEquipes(nomEquipesTri);
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
	

	@Override
	public void windowClosing(WindowEvent e) {
    	this.vue.dispose();
    	VueAccueilAdmin vue = new VueAccueilAdmin();
		vue.setVisible(true);
	}

	
	
	// NOT IMPLEMENTED \\
	
	@Override
	public void mouseClicked(MouseEvent e) {}
	
	@Override
	public void mousePressed(MouseEvent e) {}
	
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
