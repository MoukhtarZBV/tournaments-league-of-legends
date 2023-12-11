package controleur;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JTable;

import ihm.VueGestionDeLaPoule;
import modele.ModelePoule;
import modele.Tournoi;

public class ControleurGestionPoule implements MouseListener {

	private VueGestionDeLaPoule vue;
	private ModelePoule modele;
	
	public ControleurGestionPoule (VueGestionDeLaPoule vue) throws Exception {
		this.vue = vue;
		this.modele = new ModelePoule(this.vue.getTournoi());
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() instanceof JTable) {
			if (e.getClickCount()==2) {
				JTable source = (JTable) e.getSource();
				int columnClicked = source.getSelectedColumn();
				int rowClicked = source.getSelectedRow();
				if (columnClicked == 1 || columnClicked == 2) {
					this.modele.updateGagnant(rowClicked, columnClicked);
					try {
						this.vue.setJTableMatches(this.modele.matches());
						Thread.sleep(100);
						this.vue.setJTableClassement(this.modele.classement());
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		} else if (e.getSource() instanceof JButton) {
			JButton button = (JButton) e.getSource();
			switch (button.getText()) {
				case ("Imprimer") :
					break;
				case ("Cloturer") : 
					break;
				case ("Retour") :
					try {
						this.modele.enregistrerResultat();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					this.vue.dispose();
					break;
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	
}
