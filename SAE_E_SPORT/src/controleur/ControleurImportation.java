package controleur;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import Fonctions.LireCSV;
import dao.ConnectionJDBC;
import dao.EquipeJDBC;
import dao.JoueurJDBC;
import dao.ParticiperJDBC;
import ihm.VueImportation;
import modele.Equipe;
import modele.Joueur;
import modele.Pays;

public class ControleurImportation implements ActionListener{
	
	private VueImportation vue;
	private List<String[]> data = null;

	public ControleurImportation(VueImportation vue) {
		this.vue = vue;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
	    JButton bouton = (JButton) e.getSource();
	    if (bouton.getText().equals("Importer")) {
	        try {
	            JFileChooser fc = new JFileChooser();
	            // Affichage de la boite de séléction de fichier
	            if (fc.showOpenDialog(this.vue) == JFileChooser.APPROVE_OPTION) {
	            	// Récupération du chemin absolu vers le fichier
	            	String chemin = fc.getSelectedFile().getAbsolutePath();
	 	            LireCSV read = new LireCSV(chemin);
		            this.data = read.getData();

		            // Récupération modèle de la JTable
		            DefaultTableModel model = (DefaultTableModel) this.vue.getModel();
		            JTable table = (JTable) this.vue.getTable();
		            
		            // Efface les lignes et colonnes
		            model.setRowCount(0);
		            model.setColumnCount(0);

		            // Ajout des colonnes au modèle
		            ajouterColonnes(model);
		            
		            // Récupération des noms des équipes
		            Object[] titreColonne = recupNomEquipes(); 
		            
		            // Ajout du nom des équipes
		            model.addRow(titreColonne);
		            
		            // changer la couleur de fond de la première ligne
		            changerBackgroundColorTableau(table);
		            
		            // Récupération des joueurs dans une liste
		            List<String> joueurs = recupJoueurListe(); 
		            
		            // Récupération et affichage des joueurs par lignes 
		            affichageListeJoueurTableau(model, joueurs);
		            
		            // Mise à jour de l'affichage de la vue
		            vue.setVisible(true);
	             }
	        	
	        } catch (Exception e1) {
	            e1.printStackTrace();
	        }
	    }
	    if (bouton.getText().equals("Valider") && this.data != null) {
	    	boolean roll = false;
	    	try {
				ConnectionJDBC.getConnection().setAutoCommit(false);
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
	    	EquipeJDBC equipeDB = new EquipeJDBC();
	    	JoueurJDBC joueurDB = new JoueurJDBC();
	    	ParticiperJDBC participerDB = new ParticiperJDBC();
	    	for (int ligne = 1; ligne<this.data.size(); ligne += 5) {
	    		try {
	    			Equipe equipe = new Equipe(equipeDB.getNextValueSequence(), this.data.get(i)[4], Integer.parseInt(this.data.get(i)[5]), Pays.getPays(data.get(i)[6]));
	    			for (int j = i; j<i+5; j++) {
	    				Joueur joueur = new Joueur(joueurDB.getNextValueSequence(), this.data.get(j)[7], equipe);
	    				equipe.ajouterJoueur(joueur);
	    				// Si un joueur est déjà dans une autre équipe
	    				if (!joueur.verifierJoueur(equipe)) {
	    					roll = true;
	    					this.vue.setMsgErreur("Un ou plusieurs joueurs appartiennent à plus d'une équipe");
	    				}
	    			}
	    			// si la composition a changé
	    			if(equipeDB.getByNom(equipe.getNom()).orElse(null) != null) {
	    				if (!(equipe.getJoueurs().equals(equipeDB.getByNom(equipe.getNom()).get().getJoueurs()))) {
	    					roll = true;
	    					this.vue.setMsgErreur("La composition d'une ou plusieurs équipes ne correspond pas");
	    				}
	    			}
	    			// On ajoute l'équipe à la base si elle n'est pas déjà présente
	    			if (Equipe.verifierEquipe(equipe) && !roll) {
	    				equipeDB.add(equipe);
	    				for (Joueur j : equipe.getJoueurs()) {
							joueurDB.add(j);
						}
	    			}
					// si composition ne correspond pas rollaback
					if (roll == true) {
						ConnectionJDBC.getConnection().rollback();
						this.vue.setColorMessage(new Color(255, 204, 204));
					// sinon succès de l'importation
					}else {
						ConnectionJDBC.getConnection().commit();
						this.vue.setColorMessage(new Color(204, 255, 204));
						this.vue.setMsgErreur("Les équipes ont bien été importées");
	    			Equipe equipe = new Equipe(equipeDB.getNextValueSequence(), this.data.get(ligne)[4], Integer.parseInt(this.data.get(ligne)[5]), Pays.getPays(data.get(ligne)[6]));
	    			roll = ajouterJoueursAUneEquipe(roll, joueurDB, ligne, equipe);
	    			roll = compositionAChangee(roll, equipeDB, equipe);
	    			ajouterEquipeALaBase(roll, equipeDB, equipe);
					confirmerAjout(roll);
					if (!roll) {
						System.out.println(equipe);
						Participer participer = new Participer(equipe, vue.getTournoi());
						participerDB.add(participer);
					}
					
				} catch (Exception e1) {
					e1.printStackTrace();
				}
	    	}
	    }
	    if (bouton.getText().equals("Retour")) {
	    	this.vue.dispose();
	    }
	}
	private void confirmerAjout(boolean roll) throws SQLException {
		if (roll == true) {
			ConnectionJDBC.getConnection().rollback();
			this.vue.setColorMessage(new Color(255, 204, 204));
		} else {
			ConnectionJDBC.getConnection().commit();
			this.vue.setColorMessage(new Color(204, 255, 204));
			this.vue.setMsgErreur("Les équipes ont bien été importées");
		}
	}
	
	private void ajouterEquipeALaBase(boolean roll, EquipeJDBC equipeDB, Equipe equipe) throws Exception {
		if (equipe.verifierEquipe() && !roll) {
			equipeDB.add(equipe);
		}
	}
	
	private boolean compositionAChangee(boolean roll, EquipeJDBC equipeDB, Equipe equipe) throws Exception {
		// si la composition a changé
		/**
		 * *
		 * *
		 * *
		 * *
		 * * NE PAS OUBLIERRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR
		 * *
		 * *
		 * *
		 * *
		 * *
		 */
		if(equipeDB.getByNom(equipe.getNom()).orElse(null) != null) {
			if (!(equipe.getJoueurs().equals(equipeDB.getByNom(equipe.getNom()).get().getJoueurs()))) {
				roll = true;
				this.vue.setMsgErreur("La composition d'une ou plusieurs équipes ne correspond pas");
			}
		}
		return roll;
	}
	private boolean ajouterJoueursAUneEquipe(boolean roll, JoueurJDBC joueurDB, int i, Equipe equipe) throws Exception {
		for (int j = i; j<i+5; j++) {
			Joueur joueur = new Joueur(joueurDB.getNextValueSequence(), this.data.get(j)[7], equipe);
			equipe.ajouterJoueur(joueur);
			// Si un joueur est déjà dans une autre équipe
			if (!joueur.verifierJoueur(equipe)) {
				roll = true;
				this.vue.setMsgErreur("Un ou plusieurs joueurs appartiennent à plus d'une équipe");
			}
		}
		return roll;
	}
}
