package ihm;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import components.CoolScrollBar;
import components.CoolTextField;
import components.PanelPopUp;
import controleur.ControleurHistoriquePoints;
import modele.Equipe;
import modele.Tournoi;

import java.awt.GridLayout;
import java.awt.Image;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;

public class VueHistoriquePoints extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private CoolTextField searchBar;
	private JTable equipesTable;
	private JTable tournoiTable;
	private JScrollPane tournoiScroll, equipesScroll;
	
	private PanelPopUp popup;
	
	private ControleurHistoriquePoints controleur;
	
	
	public VueHistoriquePoints() {
		
		///// FENÊTRE \\\\\
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(Ecran.posX, Ecran.posY, Ecran.tailleX, Ecran.tailleY);
		setResizable(false);
		setUndecorated(true);
				
		
		///// MAIN PANEL \\\\\
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.setBackground(Palette.GRAY);
		setContentPane(contentPane);

		///// HEADER \\\\\
		Header header = new Header(this);
		header.setTitre("Historique des points");
		contentPane.add(header, BorderLayout.NORTH);
		
		
		///// MENU BAR \\\\\
		MenuBar panelSide = new MenuBar(this);
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
		JLabel lblTitre = new JLabel("Historique des points");
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
		
		/// PANEL RECHERCHE \\\
		JPanel panelSearch = new JPanel();
		panelSearch.setBorder(new EmptyBorder(14, 15, 15, 15));
		panelSearch.setBackground(Palette.GRAY);
		panelSearch.setLayout(new BorderLayout(5, 10));
		panelCenter.add(panelSearch, BorderLayout.NORTH);
		
		// Bouton valider
		JPanel panelValider = new JPanel();
		panelValider.setOpaque(false);
		panelValider.setLayout(new BoxLayout(panelValider, BoxLayout.X_AXIS));
		panelSearch.add(panelValider, BorderLayout.EAST);
		
		ImageIcon icon = new ImageIcon(VueListeEquipe.class.getResource("/Images/Search_Icon.png"));
		Image img = icon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
		icon = new ImageIcon(img);
		
		JButton validateBtn = new JButton("");
		validateBtn.setIcon(icon);
		validateBtn.setName("Rechercher");
		validateBtn.setBorder(new EmptyBorder(5, 5, 5, 5));
		validateBtn.setBackground(Palette.WHITE);
		panelValider.add(validateBtn);
		
		this.popup = new PanelPopUp();
		this.popup.setVisible(false);
		panelSearch.add(popup, BorderLayout.SOUTH);

		// Barre recherche
		searchBar = new CoolTextField();
		searchBar.setBackground(Palette.DARK_GRAY);
		searchBar.setForeground(Palette.WHITE);
		searchBar.setFont(Police.INPUT);
		searchBar.setPreferredSize(new Dimension(searchBar.getPreferredSize().width, 25));
		searchBar.setColumns(30);
		panelSearch.add(searchBar);
		
		
		///// DOUBLE PANEL CENTRAL \\\\\
		JPanel deuxScroll = new JPanel();
		deuxScroll.setBackground(Palette.GRAY);
		deuxScroll.setLayout(new GridLayout(0, 2, 0, 0));
		panelCenter.add(deuxScroll, BorderLayout.CENTER);
		
		// Scroll équipes
		String[] columnNamesEquipes = {"","Equipe"};
		@SuppressWarnings("serial")
		DefaultTableModel modeleEquipes = new DefaultTableModel(columnNamesEquipes, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// Rendre toutes les cellules non éditables
				return false;
			}
        };
        
		this.equipesTable = new JTable();
		this.equipesTable.setModel(modeleEquipes);
		this.equipesTable.setRowHeight(30);
		this.equipesTable.setName("equipesTable");
		this.equipesTable.setShowGrid(true);
		this.equipesTable.setGridColor(Palette.GRAY);
		this.equipesTable.setBorder(null);
		this.equipesTable.setOpaque(false);
		this.equipesTable.setForeground(Palette.WHITE);
		this.equipesTable.setFont(Police.TABLEAU_PETIT);
		this.equipesTable.setBackground(Palette.DARK_GRAY);		
		this.equipesTable.setIntercellSpacing(new Dimension(0, 6));
		this.equipesTable.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
		this.equipesTable.getColumnModel().getColumn(0).setMaxWidth(50);
		this.equipesTable.setSelectionBackground(Palette.LIGHT_PURPLE);

		this.equipesTable.getTableHeader().setReorderingAllowed(false);
		this.equipesTable.getTableHeader().setResizingAllowed(false);
		this.equipesTable.getTableHeader().setToolTipText("Liste des équipes");
		this.equipesTable.getTableHeader().setBackground(Palette.WHITE);
		this.equipesTable.getTableHeader().setFont(Police.TABLEAU);
		
		equipesScroll = new JScrollPane();
		equipesScroll.setBorder(new EmptyBorder(20, 20, 20, 20));
		equipesScroll.setViewportView(this.equipesTable);
		equipesScroll.getViewport().setBackground(Palette.GRAY);
		equipesScroll.setVerticalScrollBar(new CoolScrollBar());
		equipesScroll.setBackground(Palette.GRAY);
		deuxScroll.add(equipesScroll);
		

		// Scroll des résultats de l'équipe aux tournois		
		String[] columnNamesTournoi = {"Date", "Tournoi", "Points"};
		@SuppressWarnings("serial")
		DefaultTableModel modeleTournoi = new DefaultTableModel(columnNamesTournoi, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// Rendre toutes les cellules non éditables
				return false;
			}
        };
        
		this.tournoiTable = new JTable();
		this.tournoiTable.setModel(modeleTournoi);
		this.tournoiTable.setName("tournoiTable");
		this.tournoiTable.setShowGrid(true);
		this.tournoiTable.setGridColor(Palette.GRAY);
		this.tournoiTable.setBorder(null);
		this.tournoiTable.setEnabled(false);
		this.tournoiTable.setOpaque(false);
		this.tournoiTable.setRowHeight(40);
		this.tournoiTable.setForeground(Palette.WHITE);
		this.tournoiTable.setFont(Police.TABLEAU_PETIT);
		this.tournoiTable.setBackground(Palette.DARK_GRAY);		
		this.tournoiTable.setIntercellSpacing(new Dimension(0, 6));
		this.tournoiTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		
		this.tournoiTable.getColumnModel().getColumn(0).setMaxWidth(100);
		this.tournoiTable.getColumnModel().getColumn(0).setMinWidth(100);
		this.tournoiTable.getColumnModel().getColumn(0).setPreferredWidth(100);
		this.tournoiTable.getColumnModel().getColumn(2).setMaxWidth(75);
		this.tournoiTable.getColumnModel().getColumn(2).setMinWidth(75);
		this.tournoiTable.getColumnModel().getColumn(2).setPreferredWidth(75);
		
		this.tournoiTable.getTableHeader().setReorderingAllowed(false);
		this.tournoiTable.getTableHeader().setResizingAllowed(false);
		this.tournoiTable.getTableHeader().setBackground(Palette.WHITE);
		this.tournoiTable.getTableHeader().setFont(Police.TABLEAU);
		
		tournoiScroll = new JScrollPane();
		tournoiScroll.setBorder(new EmptyBorder(20, 20, 20, 20));
		tournoiScroll.setViewportView(tournoiTable);
		tournoiScroll.getViewport().setBackground(Palette.GRAY);
		tournoiScroll.setBackground(Palette.GRAY);
		tournoiScroll.setVerticalScrollBar(new CoolScrollBar());
		deuxScroll.add(tournoiScroll);
		
		tournoiScroll.setVisible(false);
		
		
		///// PANEL BOUTONS \\\\\
		JPanel panelBottom = new JPanel();
		panelBottom.setOpaque(false);
		panelBottom.setLayout(new FlowLayout(FlowLayout.RIGHT));
		panelCenter.add(panelBottom, BorderLayout.SOUTH);
		
		JButton btnRetour = new JButton("Retour");
		btnRetour.setName("Retour");
		btnRetour.setBackground(Palette.GRAY);
		btnRetour.setForeground(Palette.WHITE);
		btnRetour.setBorder(Utilitaires.BORDER_BOUTONS);
		btnRetour.setFont(Police.LABEL);
		btnRetour.setFocusable(false);
		panelBottom.add(btnRetour);
		
		
		// Controleur
		this.controleur = new ControleurHistoriquePoints(this);
		addWindowListener(controleur);
		
		validateBtn.addActionListener(controleur);
		validateBtn.addMouseListener(controleur);
		
		this.searchBar.addActionListener(controleur);
		this.searchBar.addFocusListener(controleur);
		
		this.equipesTable.addMouseListener(controleur);
		this.tournoiTable.addMouseListener(controleur);
		
		btnRetour.addActionListener(controleur);
		btnRetour.addMouseListener(controleur);
	}
	
	
	public void setResultat(boolean res) {
		if(res) {
			this.popup.setVisible(true);
			this.popup.setErreur("Aucune équipe");	
		} else {
			this.popup.setVisible(false);
			showEquipeHeader(true);
		}
	}
	
	public void viderColonneTableTournoi() {
		DefaultTableModel modeleTournoi = (DefaultTableModel) this.tournoiTable.getModel();
		modeleTournoi.setRowCount(0);
		
		showTournoiHeader(false);
	}
	
	public void viderColonneTableEquipe() {
		DefaultTableModel modeleEquipe = (DefaultTableModel) this.equipesTable.getModel();
		modeleEquipe.setRowCount(0);
		
		showEquipeHeader(false);
	}	
	
	public void setTableTournoi (Map<Tournoi, Integer> pointsTournoi) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		DefaultTableModel modeleTournoi = (DefaultTableModel) this.tournoiTable.getModel();
		modeleTournoi.setRowCount(0);
		for (Entry<Tournoi, Integer> entry : pointsTournoi.entrySet()) {
			modeleTournoi.addRow(new Object[]{dateFormat.format(entry.getKey().getDateDebut()), entry.getKey().getNomTournoi(), entry.getValue()});
		}
		if (pointsTournoi.size()>0) {
			modeleTournoi.addRow(new Object[] {"Total", "", pointsTournoi.values().stream().reduce((x,y)->x+y).orElse(0)});
		}
	}
	
	public void setTableEquipes (List<Equipe> equipes) {
		DefaultTableModel modeleEquipe = (DefaultTableModel) this.equipesTable.getModel();
		modeleEquipe.setRowCount(0);
		for (int i = 0; i < equipes.size(); i++) {
			modeleEquipe.addRow(new Object[] {i+1, equipes.get(i).getNom()});
		}
	}
	
	public JTextField getSearchBar() {
		return this.searchBar;
	}
	
	public void showEquipeHeader(boolean visible) {
		this.equipesScroll.setVisible(visible);
	}
	
	public void showTournoiHeader(boolean visible) {
		this.tournoiScroll.setVisible(visible);
	}

}
