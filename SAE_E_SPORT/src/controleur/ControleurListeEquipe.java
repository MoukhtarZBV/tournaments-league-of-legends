package controleur;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JList;

import dao.ConnectionJDBC;
import dao.EquipeJDBC;
import ihm.Palette;
import ihm.VueEquipe;
import ihm.VueListeEquipe;
import modele.Equipe;

public class ControleurListeEquipe implements MouseListener, ActionListener{
	private VueListeEquipe vue;

	public ControleurListeEquipe(VueListeEquipe vue) {
		this.vue = vue;
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getSource() instanceof JList) {
			JList list = (JList) e.getSource();
			if (e.getClickCount() == 2) {
				try {
					List<Equipe> equipes = (new EquipeJDBC().getAll());
					VueEquipe vue = new VueEquipe(equipes, new EquipeJDBC().getByNom(((String) list.getSelectedValue()).substring(0, 50)));
					vue.setVisible(true);
					this.vue.dispose();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
	    JButton bouton = (JButton) e.getSource();
	    List<Equipe> equipes;
		try {
			EquipeJDBC ejdbc = new EquipeJDBC();
			equipes = ejdbc.getAll();
			List<String> nomEquipes = equipes.stream()
		            .map(eq -> String.format("%-70s %6d", eq.getNom(), eq.getRang()))
		            .collect(Collectors.toList());

		    List<String> nomEquipesTri = nomEquipes.stream()
		            .filter(eq -> eq.toUpperCase().contains(this.vue.getSearch().toUpperCase()))
		            .collect(Collectors.toList());

		    // Mise à jour du modèle de la JList dans la vue
		    this.vue.updateListeEquipes(nomEquipesTri);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		if(e.getSource() instanceof JButton) {
			JButton b = (JButton)e.getSource();
			b.setBackground(Palette.COOL);
		}
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		if(e.getSource() instanceof JButton) {
			JButton b = (JButton)e.getSource();
			b.setBackground(Palette.WHITE);
		}	
	}


}
