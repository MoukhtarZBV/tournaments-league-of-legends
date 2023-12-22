package ihm;

import javax.swing.JFrame;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.SwingConstants;
import java.awt.Font;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JComboBox;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.table.DefaultTableModel;

import components.CoolScrollBar;
import components.CoolTextField;
import controleur.ControleurListeTournois;
import dao.ConnectionJDBC;
import dao.TournoiJDBC;
import dao.UpdateDB;
import modele.Niveau;
import modele.Statut;
import modele.Tournoi;

import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import java.awt.FlowLayout;

public class VueListeTournois extends JFrame {
	
	private JTextField 	champRecherche;
	private JTable     	table = new JTable();
	private JComboBox<String> triNiveau = new JComboBox<String>();
	private JComboBox<String> triStatus = new JComboBox<String>();
	
	
	public VueListeTournois(List<Tournoi> tournois) {
		
		ControleurListeTournois controleur = new ControleurListeTournois(this);
		
		///// FENÊTRE \\\\\
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(Ecran.posX, Ecran.posY, Ecran.tailleX, Ecran.tailleY);
		setTitle("Tournois");
		setResizable(false);
		

		///// PANEL PRINCIPAL \\\\\	
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		
		///// MENU BAR \\\\\
		JPanel panelSide = new JPanel();
		panelSide.setBackground(Palette.DARK_GRAY);
		panelSide.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 5, Palette.GRAY));
		panelSide.setPreferredSize(new Dimension(125, 600));
		contentPane.add(panelSide, BorderLayout.WEST);
		
		
		///// MAIN \\\\\
		JPanel panelMain = new JPanel();
		panelMain.setBorder(new EmptyBorder(25, 0, 25, 0));
		panelMain.setLayout(new BorderLayout(0, 0));
		panelMain.setBackground(Palette.DARK_GRAY);
		contentPane.add(panelMain, BorderLayout.CENTER);
		
		
		///// PANEL TITRE \\\\\
		JPanel panelTop = new JPanel();
		panelTop.setPreferredSize(new Dimension(800, 120));
		panelTop.setBackground(Palette.DARK_GRAY);
		panelTop.setBorder(new EmptyBorder(15, 100, 0, 100));
		panelTop.setLayout(new GridLayout());
		panelMain.add(panelTop, BorderLayout.NORTH);
		
		// Label titre
		JLabel lblTitre = new JLabel("Tournois");
		lblTitre.setBorder(BorderFactory.createMatteBorder(0, 0, 5, 0, Palette.WHITE));
		lblTitre.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitre.setForeground(Palette.WHITE);
		lblTitre.setFont(Police.GROS_TITRE);
		panelTop.add(lblTitre);
		
		
		///// MAN PANEL MILIEU \\\\\
		JPanel panelCenter = new JPanel();
		panelCenter.setLayout(new BorderLayout(0, 25));
		panelCenter.setBackground(Palette.DARK_GRAY);
		panelCenter.setBorder(new EmptyBorder(15, 100, 15, 100));
		panelMain.add(panelCenter, BorderLayout.CENTER);
		
		
		///// PANEL RECHERCHE ET TRIS \\\\\
		JPanel panelSearch = new JPanel();
		panelSearch.setBorder(new EmptyBorder(10, 10, 10, 10));
		panelSearch.setBackground(Palette.GRAY);
		panelSearch.setLayout(new GridLayout(0, 2, 10, 0));
		panelCenter.add(panelSearch, BorderLayout.NORTH);
		
		
		///// BARRE RECHERCHE \\\\\
		JPanel panelBarreRecherche = new JPanel();
		panelBarreRecherche.setLayout(new BorderLayout(0, 0));
		panelBarreRecherche.setOpaque(false);
		panelSearch.add(panelBarreRecherche);
		
		// Champ de recherche
		champRecherche = new CoolTextField();
		champRecherche.setFont(Police.INPUT);
		champRecherche.setBackground(Palette.DARK_GRAY);
		champRecherche.setForeground(Palette.WHITE);
		champRecherche.setText("");
		champRecherche.setColumns(10);
		panelBarreRecherche.add(champRecherche, BorderLayout.CENTER);
		
		// Bouton rechercher
		ImageIcon icon = new ImageIcon(VueListeEquipe.class.getResource("/Images/Search_Icon.png"));
		Image img = icon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
		icon = new ImageIcon(img);
		
		JButton btnRecherche = new JButton();
		btnRecherche.setFont(new Font("Gigi", Font.PLAIN, 12));
		btnRecherche.setName("Rechercher");
		btnRecherche.setBackground(Palette.WHITE);
		btnRecherche.addActionListener(controleur);
		btnRecherche.setIcon(icon);
		panelBarreRecherche.add(btnRecherche, BorderLayout.EAST); 
		
		// Panel des tris
		JPanel panelTris = new JPanel();
		panelTris.setOpaque(false);
		panelTris.setLayout(new GridLayout(1, 0, 5, 0));
		panelSearch.add(panelTris);
		
		// Combo box niveau
		triNiveau.setMaximumRowCount(10);
		triNiveau.setFont(Police.COMBO);
		triNiveau.setBackground(Palette.GRAY);
		triNiveau.setForeground(Palette.WHITE);
		triNiveau.setFocusable(false);
		triNiveau.addItemListener(controleur);
		triNiveau.addItem("-- Niveau --");
		for (Niveau niveau : Niveau.values()) {
        	triNiveau.addItem(niveau.denomination());
        }
		panelTris.add(triNiveau);
		
		// Combo box etats
		triStatus.setMaximumRowCount(10);
		triStatus.setFont(Police.COMBO);
		triStatus.setBackground(Palette.GRAY);
		triStatus.setForeground(Palette.WHITE);
		triStatus.setFocusable(false);
		triStatus.addItemListener(controleur);
		triStatus.addItem("-- Status --");
		for (Statut status : Statut.values()) {
        	triStatus.addItem(status.denomination());
        }
		panelTris.add(triStatus);
		
		
		///// PANEL LISTE TOURNOIS \\\\\
		JPanel panelListe = new JPanel();
		panelListe.setBorder(new EmptyBorder(15, 15, 15, 15));
		panelListe.setLayout(new BoxLayout(panelListe, BoxLayout.X_AXIS));
		panelListe.setBackground(Palette.GRAY);
		panelCenter.add(panelListe, BorderLayout.CENTER);
		
		// Tableau
		table.setBorder(null);
		table.setOpaque(false);
		table.setSelectionBackground(Palette.LIGHT_PURPLE);
		table.setRowMargin(5);
		table.setRowHeight(35);
		table.setFont(Police.LABEL);
		table.setShowGrid(false);
		table.setBackground(Palette.DARK_GRAY);
		table.setForeground(Palette.WHITE);
		
		// Modele de la table
		DefaultTableModel modele = new DefaultTableModel(new Object[][] {},
	            new String[] { "Nom", "Niveau", "Date début", "Nombre d'équipes", "État" }) {
	                
				@Override
			    public boolean isCellEditable(int row, int column) {
			       return false;
			    }
			};
		table.setModel(modele);
		afficherTournois(tournois);
		
		// Table Header
		table.getTableHeader().setBackground(Palette.WHITE);
		table.getTableHeader().setForeground(Palette.DARK_GRAY);
		table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 15));
		table.addMouseListener(controleur);
		
		// Scroll pane de la table
		JScrollPane tableScroll = new JScrollPane(table);
		tableScroll.setBorder(new MatteBorder(1, 1, 1, 1, Palette.WHITE));
		tableScroll.setVerticalScrollBar(new CoolScrollBar());
		tableScroll.getViewport().setBackground(Palette.GRAY);
		tableScroll.setViewportBorder(null);
		panelListe.add(tableScroll);
		
		
		///// PANEL BOUTONS \\\\\
		JPanel panelBoutons = new JPanel();
		panelBoutons.setBorder(new EmptyBorder(10, 10, 10, 10));
		panelBoutons.setBackground(Palette.GRAY);
		panelBoutons.setPreferredSize(new Dimension(panelBoutons.getWidth(), 60));
		panelBoutons.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 0));
		panelCenter.add(panelBoutons, BorderLayout.SOUTH);
		
		// Bouton annuler
		JButton btnRetour = new JButton("<html><body style='padding: 5px 25px;'>Retour</body></html>");
		btnRetour.setName("Retour");
		btnRetour.setBackground(Palette.GRAY);
		btnRetour.setForeground(Palette.WHITE);
		btnRetour.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Palette.WHITE));
		btnRetour.setFont(Police.LABEL);
		btnRetour.addActionListener(controleur);
		btnRetour.setFocusable(false);
		panelBoutons.add(btnRetour);
		
		// Bouton valider
		JButton btnNouveau = new JButton("<html><body style='padding: 5px 25px;'>Nouveau tournoi</body></html>");
		btnNouveau.setName("Nouveau");
		btnNouveau.setBackground(Palette.GRAY);
		btnNouveau.setForeground(Palette.WHITE);
		btnNouveau.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Palette.WHITE));
		btnNouveau.setFont(Police.LABEL);
		btnNouveau.setFocusable(false);
		btnNouveau.addActionListener(controleur);
		panelBoutons.add(btnNouveau);
	}
	
	
	public String saisieChamp() {
		return this.champRecherche.getText();
	}
	
	public void afficherTournois(List<Tournoi> tournois) {
		DefaultTableModel modele = ((DefaultTableModel) table.getModel());
		modele.setRowCount(0);
		for (Tournoi tournoi : tournois) {
			modele.addRow(new Object[] {tournoi.getNomTournoi(), tournoi.getNiveau().denomination(), tournoi.getDateDebut(), TournoiJDBC.nombreEquipesTournoi(tournoi), tournoi.getStatut().denomination()});
		}
	}
	
	public boolean estOptionComboboxNiveau(String option){
		ComboBoxModel<String> model = triNiveau.getModel();
		for (int i = 0; i < model.getSize(); i++) {
			if (((String) model.getElementAt(i)).equals(option)){
				return true;
			}
		}
		return false;
	}
	
	public boolean estOptionComboboxStatus(String option){
		ComboBoxModel<String> model = triStatus.getModel();
		for (int i = 0; i < model.getSize(); i++) {
			if (((String) model.getElementAt(i)).equals(option)){
				return true;
			}
		}
		return false;
	}
}
