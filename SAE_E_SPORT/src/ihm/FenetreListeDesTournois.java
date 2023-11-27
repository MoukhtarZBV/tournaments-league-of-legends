package ihm;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.SwingConstants;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;

import java.awt.GridLayout;
import javax.swing.table.DefaultTableModel;

import controleur.ControleurListeTournois;
import dao.TournoiJDBC;
import modele.ModeleListeTournois;
import modele.Niveau;
import modele.Tournoi;

import javax.swing.BoxLayout;
import java.awt.Insets;
import java.awt.FlowLayout;
import java.awt.Component;
import javax.swing.Box;

public class FenetreListeDesTournois extends JFrame {
	
	private JTextField 		champRecherche;
	private JTable     		table;

	public static void main (String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {											
					String dirProjetJava = System.getProperty("user.dir");
					System.setProperty("derby.system.home", dirProjetJava + "/BDD");		
					String urlConnexion = "jdbc:derby:BDD;create=true";
					
					try {
						DriverManager.registerDriver(new org.apache.derby.jdbc.EmbeddedDriver());
						Connection cn = DriverManager.getConnection(urlConnexion);
						FenetreListeDesTournois frame = new FenetreListeDesTournois();
						frame.setVisible(true);
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	public FenetreListeDesTournois() {
		ControleurListeTournois controleur = new ControleurListeTournois(this);
		
		///// FENÊTRE \\\\\
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 600);
		setTitle("Tournois");
		
		
		
		///// PANEL PRINCIPAL \\\\\
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		
		
		///// PANEL TITRE \\\\\
		JPanel panelTop = new JPanel();
		panelTop.setBorder(new EmptyBorder(0, 0, 0, 0));
		panelTop.setLayout(new BorderLayout(0, 0));
		contentPane.add(panelTop, BorderLayout.NORTH);
		
		// Label titre
		JLabel lblTitre = new JLabel("Tournois");
		lblTitre.setBorder(new EmptyBorder(20, 0, 20, 0));
		lblTitre.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitre.setFont(new Font("DejaVu Sans", Font.PLAIN, 40));
		panelTop.add(lblTitre, BorderLayout.CENTER);
		
		// Ligne colorée séparatrice
		JTextField ligneColoree = new JTextField();
		ligneColoree.setBackground(new Color(25, 25, 112));
		ligneColoree.setEnabled(false);
		ligneColoree.setEditable(false);
		ligneColoree.setFont(new Font("Tahoma", Font.PLAIN, 5));
		panelTop.add(ligneColoree, BorderLayout.SOUTH);
		
		
		
		///// PANEL MAIN \\\\\
		JPanel panelMain = new JPanel();
		panelMain.setBackground(new Color(255, 255, 255));
		panelMain.setBorder(new EmptyBorder(10, 10, 10, 10));
		contentPane.add(panelMain, BorderLayout.CENTER);
		panelMain.setLayout(new BorderLayout(10, 10));
		
		
		///// PANEL RECHERCHE ET TRIS \\\\\
		JPanel panelSearch = new JPanel();
		panelSearch.setBorder(new EmptyBorder(5, 5, 5, 5));
		panelMain.add(panelSearch, BorderLayout.NORTH);
		panelSearch.setLayout(new GridLayout(0, 2, 10, 0));
		
		// Barre de recherche
		JTextField champRecherche = new JTextField();
		champRecherche.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panelSearch.add(champRecherche);
		champRecherche.setColumns(20);
		
		
		// Panel des tris
		JPanel panelTris = new JPanel();
		panelSearch.add(panelTris);
		panelTris.setLayout(new GridLayout(1, 0, 5, 0));
		
		// Combo box niveau
		JComboBox triNiveau = new JComboBox();
		triNiveau.addItemListener(controleur);
		triNiveau.setMaximumRowCount(10);
		triNiveau.setFont(new Font("Tahoma", Font.PLAIN, 18));
		triNiveau.addItem("-- Tri Niveau --");
		for (Niveau niveau : Niveau.values()) {
			triNiveau.addItem(niveau.denomination());
		}
		panelTris.add(triNiveau);
		
		// Combo box etats
		JComboBox triEtat = new JComboBox();
		triEtat.setModel(new DefaultComboBoxModel(new String[] {"-- Tri État --", "Pas commencé", "En cours", "Terminé"}));
		triEtat.setMaximumRowCount(10);
		triEtat.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panelTris.add(triEtat);
		
		
		
		///// PANEL LISTE TOURNOIS \\\\\
		JPanel panelListe = new JPanel();
		panelListe.setBorder(new EmptyBorder(5, 5, 5, 5));
		panelListe.setLayout(new BoxLayout(panelListe, BoxLayout.X_AXIS));
		panelMain.add(panelListe, BorderLayout.CENTER);
		
		// Tableau
		table = new JTable();
		table.setSelectionBackground(new Color(0, 0, 128, 100));
		table.setRowMargin(5);
		table.setRowHeight(20);
		table.setFont(new Font("Tahoma", Font.PLAIN, 15));
		table.setShowGrid(false);
		
		// Modele de la table
		TournoiJDBC jdbc = new TournoiJDBC();
		try {
			afficherTournois(jdbc.getAll());
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(2).setResizable(false);
		table.getColumnModel().getColumn(3).setResizable(false);
		*/
		// Table Header
		table.getTableHeader().setBackground(new Color(25, 25, 112));
		table.getTableHeader().setForeground(Color.WHITE);
		table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 15));
		
		// Scroll pane de la table
		JScrollPane tableScroll = new JScrollPane(table);
		panelListe.add(tableScroll);
		
		
		///// PANEL BOUTONS \\\\\
		JPanel panelBoutons = new JPanel();
		panelBoutons.setBorder(new EmptyBorder(10, 100, 10, 100));
		panelMain.add(panelBoutons, BorderLayout.SOUTH);
		panelBoutons.setLayout(new GridLayout(0, 2, 15, 0));
		
		// Bouton annuler
		JButton btnRetour = new JButton("Retour");
		btnRetour.setBackground(new Color(255, 255, 255));
		btnRetour.setBorder(new LineBorder(new Color(0, 0, 102, 100), 2, true));
		btnRetour.setFont(new Font("Tahoma", Font.PLAIN, 20));
		//btnRetour.addActionListener(controleur);
		panelBoutons.add(btnRetour);
		
		// Bouton valider
		JButton btnCreer = new JButton("Créer Tournoi");
		btnCreer.setBackground(new Color(255, 255, 255));
		btnCreer.setBorder(new LineBorder(new Color(0, 0, 102, 100), 2, true));
		btnCreer.setFont(new Font("Tahoma", Font.PLAIN, 20));
		//btnValider.addActionListener(controleur);
		panelBoutons.add(btnCreer);
		
	}
	
	public void afficherTournois(List<Tournoi> tournois) {
		DefaultTableModel modele = new DefaultTableModel(new Object[][] {},
			new String[] { "Nom", "Niveau", "Date début", "Nombre d'équipes", "État" }) {
			
			// Non éditables
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false
			};;
		};
		for (Tournoi tournoi : tournois) {
			modele.addRow(new Object[] {tournoi.getNomTournoi(), tournoi.getNiveau().denomination(), tournoi.getDateDebut(), TournoiJDBC.nombreEquipesTournoi(tournoi), ModeleListeTournois.etatTournoi(tournoi)});
		}
		this.table.setModel(modele);
	}

}
