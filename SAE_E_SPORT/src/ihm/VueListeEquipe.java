package ihm;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.*;
import java.util.stream.Collectors;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import components.CoolScrollBar;
import components.CoolTextField;
import controleur.ControleurListeEquipe;
import modele.Equipe;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import java.awt.Image;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;

public class VueListeEquipe extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JTextField searchBar;
	private JList<Object> listeEquipes;
	private JButton btnSort;
	
	private List<Equipe> equipes;
	
	private boolean triParNom;
	
	
	public VueListeEquipe(List<Equipe> equipes) { 
		
		this.equipes = equipes;
		ControleurListeEquipe controleur = new ControleurListeEquipe(this);
		
		///// FENÊTRE \\\\\
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(Ecran.posX, Ecran.posY, Ecran.tailleX, Ecran.tailleY);
		setResizable(false);
		setUndecorated(true);
		addWindowListener(controleur);
				
		
		///// MAIN PANEL \\\\\
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.setBackground(Palette.GRAY);
		setContentPane(contentPane);

		///// HEADER \\\\\
		Header header = new Header(this);
		header.setTitre("Équipes");
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
		JLabel lblTitre = new JLabel("Équipes");
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
		panelSearch.setLayout(new BorderLayout(5, 0));
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
		validateBtn.addActionListener(controleur);
		validateBtn.addMouseListener(controleur);
		panelValider.add(validateBtn);

		// Barre recherche
		searchBar = new CoolTextField();
		searchBar.setBackground(Palette.DARK_GRAY);
		searchBar.setForeground(Palette.WHITE);
		searchBar.setFont(Police.INPUT);
		searchBar.setPreferredSize(new Dimension(searchBar.getPreferredSize().width, 25));
		searchBar.setColumns(30);
		searchBar.addActionListener(controleur);
		panelSearch.add(searchBar);
		
		
		// Panel liste
		JPanel panelListe = new JPanel();
		panelListe.setLayout(new BorderLayout(5, 0));
		panelListe.setBackground(Palette.GRAY);
		panelCenter.add(panelListe, BorderLayout.CENTER);

		JLabel lblHeader = new JLabel(String.format("%-5s %-50s", "Rang", "Nom de l'équipe"));
		lblHeader.setFont(Police.TABLEAU);
		lblHeader.setBorder(new EmptyBorder(5, 10, 5, 10));
		lblHeader.setForeground(Palette.WHITE);
		panelListe.add(lblHeader, BorderLayout.NORTH);
		
		// Liste des équipes
		List<String> nomEquipes = this.equipes.stream()
				.sorted((x,y)-> {
					if (x.getRang()>y.getRang()){
						return 1;
					}else if(x.getRang()<y.getRang()) {
						return -1;
					}else {
						return 0;
					}
				})
				.map(eq -> String.format("%-5d %-50s", eq.getRang(), eq.getNom()))
				.collect(Collectors.toList());
		
		JList<Object> listeEquipes = new JList<Object>(nomEquipes.toArray());
		listeEquipes.setFont(Police.TABLEAU_MONO);
		listeEquipes.setBackground(Palette.GRAY);
		listeEquipes.setSelectionBackground(Palette.LIGHT_PURPLE);
		listeEquipes.setForeground(Palette.WHITE);
		listeEquipes.setBorder(new EmptyBorder(10, 10, 10, 10));
		listeEquipes.addMouseListener(controleur);
		this.listeEquipes = listeEquipes;
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(listeEquipes);
		scrollPane.setBorder(null);
		scrollPane.setVerticalScrollBar(new CoolScrollBar());
		panelListe.add(scrollPane, BorderLayout.CENTER);
		
		
		// PANEL DU BOUTON
		FlowLayout fl_panelBoutons = new FlowLayout(FlowLayout.RIGHT, 5, 5);
		
		JPanel panelBoutons = new JPanel();
		panelBoutons.setBorder(new EmptyBorder(0, 0, 0, 0));
		panelBoutons.setBackground(Palette.DARK_GRAY);
		panelBoutons.setLayout(fl_panelBoutons);
		panelCenter.add(panelBoutons, BorderLayout.SOUTH);
		
		// Bouton retour
		JButton btnRetour = new JButton("Retour");
		btnRetour.setName("Retour");
		btnRetour.setBackground(Palette.GRAY);
		btnRetour.setForeground(Palette.WHITE);
		btnRetour.setBorder(Utilitaires.BORDER_BOUTONS);
		btnRetour.setFont(Police.LABEL);
		btnRetour.addActionListener(controleur);
		btnRetour.addMouseListener(controleur);
		btnRetour.setFocusable(false);
		panelBoutons.add(btnRetour);
		
		this.btnSort = new JButton("Trier par nom");
		btnSort.setName("Trier");
		btnSort.setBackground(Palette.GRAY);
		btnSort.setForeground(Palette.WHITE);
		btnSort.setBorder(Utilitaires.BORDER_BOUTONS);
		btnSort.setFont(Police.LABEL);
		btnSort.addActionListener(controleur);
		btnSort.addMouseListener(controleur);
		btnSort.setFocusable(false);
		panelBoutons.add(btnSort);
		this.triParNom = false;
	}
	
	public void setBtnSort(String tri) {
		this.btnSort.setText(tri);
	}
	
	public boolean getTriParNom() {
		return this.triParNom;
	}
	
	public void setTriParNom(boolean b) {
		this.triParNom = b;
	}
	
	public String getSearch() {
		return searchBar.getText();
	}
	
	public void updateListeEquipes(List<String> elementsFiltres) {
	    this.listeEquipes.setListData(elementsFiltres.toArray(new String[0]));
	    this.listeEquipes.repaint();
	}
	
	public JList<Object> getListeEquipes() {
        return this.listeEquipes;
    }

}
