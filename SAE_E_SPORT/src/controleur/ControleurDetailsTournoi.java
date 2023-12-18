package controleur;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JTable;

import dao.EquipeJDBC;
import ihm.VueEquipe;
import ihm.VueGestionDeLaPoule;
import ihm.VueImportation;
import ihm.VueListeTournois;
import ihm.VueTournoi;
import modele.Equipe;
import modele.ModelePoule;
import modele.Tournoi;

public class ControleurDetailsTournoi implements ActionListener, MouseListener, WindowListener {
	
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
			vue.dispose();
		} else if (bouton.getName().equals("Retour")) {
			this.vue.dispose();
			Tournoi t = new Tournoi();
			VueListeTournois vue = new VueListeTournois(t.tousLesTournois());
			vue.setVisible(true);
		} else if (bouton.getName().equals("Gérer la poule")) {
			VueGestionDeLaPoule frame = new VueGestionDeLaPoule(vue.getTournoi());
            frame.setVisible(true);
		} else if (bouton.getName().equals("Ouvrir le tournoi")) {
			vue.getTournoi().generationPoule();
			ModelePoule modelePoule;
			try {
				modelePoule = new ModelePoule(vue.getTournoi());
	            VueGestionDeLaPoule frame = new VueGestionDeLaPoule(vue.getTournoi());
	            frame.setVisible(true);
			} catch (Exception e1) {
				e1.printStackTrace();
			} 
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() instanceof JTable) {
			if (e.getClickCount() == 2) {
				try {
		            JTable table = (JTable) e.getSource();
		            int row = table.getSelectedRow();
		            List<Equipe> equipes = (new EquipeJDBC().getAll());
					
					VueEquipe vue = new VueEquipe(equipes, new EquipeJDBC().getByNom(table.getValueAt(row, 0).toString()), this.vue.getTournoi());
					vue.setVisible(true);
					this.vue.dispose();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
	         }
		}
	}

	@Override
	public void windowClosing(WindowEvent e) {
		this.vue.dispose();
		Tournoi t = new Tournoi();
		VueListeTournois vue = new VueListeTournois(t.tousLesTournois());
		vue.setVisible(true);
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

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}
}
