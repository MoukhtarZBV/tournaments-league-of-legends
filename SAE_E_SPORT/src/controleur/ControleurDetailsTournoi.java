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

import dao.CompteJDBC;
import dao.EquipeJDBC;
import ihm.VueEquipe;
import ihm.VueFinale;
import ihm.VueGestionDeLaPoule;
import ihm.VueImportation;
import ihm.VueListeTournois;
import ihm.VueTournoi;
import modele.Compte;
import modele.Equipe;
import modele.ModelePoule;
import modele.Statut;
import modele.Tournoi;
import modele.TypeCompte;

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
			VueListeTournois vue = new VueListeTournois(new Tournoi().tousLesTournois());
			vue.setVisible(true);
		} else if (bouton.getName().equals("Poule")) {
			ModelePoule modelePoule;
			try {
				modelePoule = new ModelePoule(this.vue.getTournoi());
				Object[][] classement = modelePoule.classement();
	            Object[][] parties = modelePoule.matches();
	            
	            this.vue.dispose();
	            
	            VueGestionDeLaPoule frame = new VueGestionDeLaPoule(this.vue.getTournoi());
	            frame.setJTableMatches(parties);
	            frame.setJTableClassement(classement);            
				frame.setVisible(true);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} else if (bouton.getName().equals("Ouvrir le tournoi")) {
			this.modele.selectionArbitre(vue.getTournoi());
			this.modele.changerStatusTournoi(vue.getTournoi(), Statut.EN_COURS);
			this.vue.getTournoi().setStatut(Statut.EN_COURS);
			this.vue.getTournoi().generationPoule();
			this.vue.setVisibleBoutonOuvrir(false);
			this.vue.afficherArbitresTournoi(vue.getTournoi());
			this.vue.afficherBoutonGererPoule("Gérer la poule");
		} else if (bouton.getName().equals("Finale")) {
			VueFinale vueFinale = new VueFinale(this.vue.getTournoi());
			vueFinale.setVisible(true);
			this.vue.dispose();
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
