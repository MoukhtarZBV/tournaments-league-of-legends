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
import modele.Niveau;
import modele.Status;
import modele.Tournoi;

import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;

import java.awt.Insets;
import java.awt.FlowLayout;
import java.awt.Component;
import javax.swing.Box;

public class VueListeTournois extends JFrame {
	
	private JTextField 	champRecherche;
	private JTable     	table;
	private JComboBox   triNiveau;
	private JComboBox   triStatus;
	private JTextField textField;

	public static void main (String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {											
				VueListeTournois frame = new VueListeTournois();
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	public VueListeTournois() {
		
		ControleurListeTournois controleur = new ControleurListeTournois(this);
		
		///// FENÊTRE \\\\\
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(510, 240, 900, 600);
		setTitle("Tournois");
		
		
		
		///// PANEL PRINCIPAL \\\\\
		JPanel contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		
		
		///// PANEL TITRE \\\\\
		JPanel panelTop = new JPanel();
		panelTop.setBackground(Palette.COOL);
		panelTop.setBorder(new EmptyBorder(0, 0, 0, 0));
		panelTop.setLayout(new BorderLayout(0, 0));
		contentPane.add(panelTop, BorderLayout.NORTH);
		
		// Label titre
		JLabel lblTitre = new JLabel("Tournois");
		lblTitre.setForeground(Palette.WARDEN);
		lblTitre.setBorder(new EmptyBorder(20, 0, 20, 0));
		lblTitre.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitre.setFont(new Font("DejaVu Sans", Font.BOLD | Font.ITALIC, 40));
		panelTop.add(lblTitre, BorderLayout.CENTER);
		
		// Ligne colorée séparatrice
		JTextField ligneColoree = new JTextField();
		ligneColoree.setBackground(Palette.WARDEN);
		ligneColoree.setEnabled(false);
		ligneColoree.setEditable(false);
		ligneColoree.setFont(new Font("Tahoma", Font.PLAIN, 5));
		panelTop.add(ligneColoree, BorderLayout.SOUTH);
		
		
		
		///// PANEL MAIN \\\\\
		JPanel panelMain = new JPanel();
		panelMain.setBackground(Color.WHITE);
		panelMain.setBorder(new EmptyBorder(15, 15, 15, 15));
		panelMain.setLayout(new BorderLayout(10, 10));
		contentPane.add(panelMain, BorderLayout.CENTER);
		
		
		///// PANEL RECHERCHE ET TRIS \\\\\
		JPanel panelSearch = new JPanel();
		panelSearch.setBorder(new EmptyBorder(5, 5, 5, 5));
		panelSearch.setBackground(Palette.COOL);
		panelSearch.setLayout(new GridLayout(0, 2, 10, 0));
		panelMain.add(panelSearch, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		panelSearch.add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		// Champ de recherche
		champRecherche = new JTextField();
		champRecherche.setText("");
		panel.add(champRecherche, BorderLayout.CENTER);
		champRecherche.setColumns(10);
		
		JButton btnRecherche = new JButton("Rechercher");
		panel.add(btnRecherche, BorderLayout.EAST); 
		btnRecherche.addActionListener(controleur);
		
		
		// Panel des tris
		JPanel panelTris = new JPanel();
		panelTris.setBackground(Palette.COOL);
		panelTris.setLayout(new GridLayout(1, 0, 5, 0));
		panelSearch.add(panelTris);
		
		// Combo box niveau
		triNiveau = new JComboBox<String>();
		triNiveau.setMaximumRowCount(10);
		triNiveau.setFont(new Font("Tahoma", Font.PLAIN, 18));
		triNiveau.setBackground(Color.WHITE);
		triNiveau.setForeground(Palette.WARDEN);
		triNiveau.addItem("-- Niveau --");
		for (Niveau niveau : Niveau.values()) {
        	triNiveau.addItem(niveau.denomination());
        }
		triNiveau.addItemListener(controleur);
		panelTris.add(triNiveau);
		
		
		
		
		// Combo box etats
		triStatus = new JComboBox<String>();
		triStatus.setMaximumRowCount(10);
		triStatus.setFont(new Font("Tahoma", Font.PLAIN, 18));
		triStatus.setBackground(Color.WHITE);
		triStatus.setForeground(Palette.WARDEN);
		triStatus.addItem("-- Status --");
		for (Status status : Status.values()) {
        	triStatus.addItem(status.denomination());
        }
		triStatus.addItemListener(controleur);
		panelTris.add(triStatus);
		
		
		
		///// PANEL LISTE TOURNOIS \\\\\
		JPanel panelListe = new JPanel();
		panelListe.setBorder(new EmptyBorder(5, 5, 5, 5));
		panelListe.setLayout(new BoxLayout(panelListe, BoxLayout.X_AXIS));
		panelListe.setBackground(Palette.COOL);
		panelMain.add(panelListe, BorderLayout.CENTER);
		
		// Tableau
		table = new JTable();
		table.setSelectionBackground(Palette.COOL);
		table.setRowMargin(5);
		table.setRowHeight(20);
		table.setFont(new Font("Tahoma", Font.PLAIN, 15));
		table.setShowGrid(false);
		table.setBackground(Color.WHITE);
		table.setForeground(Palette.WARDEN);
		
		// Modele de la table
		DefaultTableModel modele = new DefaultTableModel(new Object[][] {},
	            new String[] { "Nom", "Niveau", "Date début", "Nombre d'équipes", "État" }) {
	                
				@Override
			    public boolean isCellEditable(int row, int column) {
			       return false;
			    }
			};
		table.setModel(modele);
		TournoiJDBC jdbc = new TournoiJDBC();
		afficherTournois(jdbc.getAll());
		
		// Table Header
		table.getTableHeader().setBackground(Palette.WARDEN);
		table.getTableHeader().setForeground(Color.WHITE);
		table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 15));
		table.addMouseListener(controleur);
		
		// Scroll pane de la table
		JScrollPane tableScroll = new JScrollPane(table);
		tableScroll.getViewport().setBackground(Palette.COOL);
		panelListe.add(tableScroll);
		
		
		///// PANEL BOUTONS \\\\\
		JPanel panelBoutons = new JPanel();
		panelBoutons.setBorder(new EmptyBorder(10, 100, 10, 100));
		panelBoutons.setBackground(Palette.COOL);
		panelBoutons.setLayout(new GridLayout(0, 2, 15, 0));
		panelMain.add(panelBoutons, BorderLayout.SOUTH);
		
		// Bouton annuler
		JButton btnRetour = new JButton("Retour");
		btnRetour.setBackground(new Color(255, 255, 255));
		btnRetour.setBorder(new LineBorder(new Color(0, 0, 102, 100), 2, true));
		btnRetour.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnRetour.addActionListener(controleur);
		panelBoutons.add(btnRetour);
		
		// Bouton valider
		JButton btnCreer = new JButton("Créer Tournoi");
		btnCreer.setBackground(new Color(255, 255, 255));
		btnCreer.setBorder(new LineBorder(new Color(0, 0, 102, 100), 2, true));
		btnCreer.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnCreer.addActionListener(controleur);
		panelBoutons.add(btnCreer);
	}
	
	public String saisieChamp() {
		return this.champRecherche.getText();
	}
	
	public void afficherTournois(List<Tournoi> tournois) {
		DefaultTableModel modele = ((DefaultTableModel) table.getModel());
		modele.setRowCount(0);
		for (Tournoi tournoi : tournois) {
			modele.addRow(new Object[] {tournoi.getNomTournoi(), tournoi.getNiveau().denomination(), tournoi.getDateDebut(), TournoiJDBC.nombreEquipesTournoi(tournoi), Tournoi.etatTournoi(tournoi).denomination()});
		}
	}
	
	public boolean estOptionComboboxNiveau(String option){
		ComboBoxModel model = triNiveau.getModel();
		for (int i = 0; i < model.getSize(); i++) {
			if (((String) model.getElementAt(i)).equals(option)){
				return true;
			}
		}
		return false;
	}
	
	public boolean estOptionComboboxStatus(String option){
		ComboBoxModel model = triStatus.getModel();
		for (int i = 0; i < model.getSize(); i++) {
			if (((String) model.getElementAt(i)).equals(option)){
				return true;
			}
		}
		return false;
	}
}
