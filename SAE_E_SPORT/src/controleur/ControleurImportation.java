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

import dao.ConnectionJDBC;
import dao.EquipeJDBC;
import dao.JoueurJDBC;
import ihm.VueImportation;
import modele.Equipe;
import modele.Joueur;
import modele.LireCSV;
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
		            for (int i = 1; i < data.size(); i = i + 5) {
		                if (i%5 == 1) {
		                    model.addColumn(data.get(i)[4]);
		                }
		            }
		            
		            // Récupération des noms des équipes
		            Object[] titreColonne = new Object[10];
		            int indice = 0;
		            for (int i = 1; i < data.size();i++) {
		            	if (i%5 == 1) {
		            		titreColonne[indice] = (data.get(i)[4]);
		            		indice++;
		            	}
		            } 
		            // Ajout du nom des équipes
		            model.addRow(titreColonne);
		            // changer la couleur de fond de la première ligne
		            table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
		                @Override
		                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		                    Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		                    if (row == 0) {
		                        cell.setBackground(new Color (56,111,215));
		                        cell.setForeground(new Color (255,255,255));
		                    }else {
		                    	cell.setBackground(Color.WHITE);
		                    	cell.setForeground(new Color (0,0,0));
		                    }
		                    return cell;
		                }
		            });
		            
		            // Récupération des joueurs dans une liste
		            List<String> joueurs = new ArrayList<>();
		            for (int i = 1; i < data.size();i++) {
		            	joueurs.add(data.get(i)[7]);
		            } 
		            
		            // Récupération et affichage des joueurs par lignes 
		            Object [][] joueursRow = new Object[5][8];
		            for (int i = 0; i<5;i++) {
		            	int k = i;
		            	for (int j = 0; j<(this.data.size()-1)/5; j++) {
		            		joueursRow[i][j] = joueurs.get(k);
		            		k += 5;
		            	}
		            	model.addRow(joueursRow[i]);
		            }
		            // Mise à jour de l'affichage de la vue
		            vue.setVisible(true);
	             }
	        	
	        } catch (Exception e1) {
	            e1.printStackTrace();
	        }
	    }
	    if(bouton.getText().equals("Valider") && this.data != null) {
	    	boolean roll = false;
	    	try {
				ConnectionJDBC.getConnection().setAutoCommit(false);
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
	    	EquipeJDBC equipeDB = new EquipeJDBC();
	    	JoueurJDBC joueurDB = new JoueurJDBC();
	    	for(int i = 1; i<this.data.size();i=i+5) {
	    		try {
	    			Equipe equipe = new Equipe(equipeDB.getNextValueSequence(), this.data.get(i)[4], Integer.parseInt(this.data.get(i)[5]), Pays.getPays(data.get(i)[6]));
	    			for (int j = i; j<i+5; j++) {
	    				Joueur joueur = new Joueur(joueurDB.getNextValueSequence(), this.data.get(j)[7], equipe);
	    				equipe.ajouterJoueur(joueur);
	    				// Si un joueur est dans une mauvaise équipe
	    				if (!joueur.verifierJoueur(equipe)) {
	    					roll = true;
	    					this.vue.setMsgErreur("La composition d'une ou plusieurs équipes ne correspond pas");
	    				}
	    			}
	    			// si la composition a changé
	    			if(equipeDB.getByNom(equipe.getNom()).orElse(null) != null) {
	    				if (equipe.getJoueurs().equals(equipeDB.getByNom(equipe.getNom()).orElse(null).getJoueurs())) {
	    					roll = true;
	    					this.vue.setMsgErreur("La composition d'une ou plusieurs équipes ne correspond pas");
	    				}
	    			}
	    			// On ajoute l'équipe à la base si elle n'est pas déjà présente
	    			if (equipe.verifierEquipe() && !roll) {
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
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
	    	}
	    }
	    if(bouton.getText().equals("Retour")) {
	    	this.vue.dispose();
	    }
	}
}
