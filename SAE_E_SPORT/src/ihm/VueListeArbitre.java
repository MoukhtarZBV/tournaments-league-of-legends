package ihm;

import java.awt.FlowLayout;

import java.awt.GridLayout;
import java.util.*;
import java.util.stream.Collectors;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import components.JTextFieldArrondi;
import controleur.ControleurListeArbitre;
import modele.Arbitre;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

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
import java.awt.Color;
import java.awt.Font;


public class VueListeArbitre extends JFrame {

	private JTextField searchBar;
	private JList<Object> listeArbitres;
	private List<Arbitre> arbitres;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Arbitre a = new Arbitre();
					VueListeArbitre frame = new VueListeArbitre(a.tousLesArbitres());
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */

	
	public VueListeArbitre(List<Arbitre> arbitres) {
		
		this.arbitres = arbitres;
		
		ControleurListeArbitre controleur = new ControleurListeArbitre(this);
		
		///// FENÊTRE \\\\\
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		addWindowListener(controleur);
		setBounds(Ecran.posX, Ecran.posY, 873, 528);
		setTitle("Arbitres");
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
		JLabel lblTitre = new JLabel("Arbitres");
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
		
		ImageIcon icon = new ImageIcon(VueListeArbitre.class.getResource("/Images/Search_Icon.png"));
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
		searchBar = new JTextFieldArrondi();
		searchBar.setBackground(Palette.DARK_GRAY);
		searchBar.setForeground(Palette.WHITE);
		searchBar.setFont(Police.INPUT);
		searchBar.setPreferredSize(new Dimension(searchBar.getPreferredSize().width, 25));
		searchBar.setColumns(30);
		panelSearch.add(searchBar);
		
		
		// Panel liste
		JPanel panelListe = new JPanel();
		panelListe.setLayout(new BorderLayout(5, 0));
		panelListe.setBackground(Palette.GRAY);
		panelCenter.add(panelListe, BorderLayout.CENTER);

		JLabel lblHeader = new JLabel(String.format("%-20s %-30s", "Nom", "Prenom"));
		lblHeader.setFont(Police.TABLEAU);
		lblHeader.setBorder(new EmptyBorder(5, 10, 5, 10));
		lblHeader.setForeground(Palette.WHITE);
		panelListe.add(lblHeader, BorderLayout.NORTH);
		
		// Liste des arbitres
		List<String> nomArbitres = this.arbitres.stream()
				.map(a -> String.format("%-11s %-30s", a.getNom(), a.getPrenom()))
				.sorted((x,y)-> x.compareTo(y))
				.collect(Collectors.toList());
		
		this.listeArbitres = new JList<Object>(nomArbitres.toArray());
		listeArbitres.setFont(Police.TABLEAU_MONO);
		listeArbitres.setBackground(Palette.GRAY);
		listeArbitres.setForeground(Palette.WHITE);
		listeArbitres.setBorder(new EmptyBorder(10, 10, 10, 10));
		listeArbitres.addMouseListener(controleur);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(listeArbitres);
		scrollPane.setBorder(null);
		panelListe.add(scrollPane, BorderLayout.CENTER);
		
		
		// PANEL DU BOUTON
		FlowLayout fl_panelBoutons = new FlowLayout(FlowLayout.RIGHT, 5, 5);
		
		JPanel panelBoutons = new JPanel();
		panelBoutons.setBorder(new EmptyBorder(0, 0, 0, 0));
		panelBoutons.setBackground(Palette.DARK_GRAY);
		panelBoutons.setLayout(fl_panelBoutons);
		panelCenter.add(panelBoutons, BorderLayout.SOUTH);
		
		// Bouton retour
		JButton btnRetour = new JButton("<html><body style='padding: 5px 25px;'>Retour</body></html>");
		btnRetour.setName("Retour");
		btnRetour.setBackground(Palette.GRAY);
		btnRetour.setForeground(Palette.WHITE);
		btnRetour.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Palette.WHITE));
		btnRetour.setFont(Police.LABEL);
		btnRetour.addActionListener(controleur);
		btnRetour.setFocusable(false);
		panelBoutons.add(btnRetour);
		
		JButton btnAjouter = new JButton("<html><body style='padding: 5px 25px;'>Ajouter</body></html>");
		btnAjouter.setName("Ajouter");
		btnAjouter.setForeground(Color.WHITE);
		btnAjouter.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnAjouter.setFocusable(false);
		btnAjouter.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Palette.WHITE));
		btnAjouter.setBackground(new Color(32, 28, 44));
		btnAjouter.addActionListener(controleur);
		panelBoutons.add(btnAjouter);
	}
	
	
	public String getSearch() {
		return searchBar.getText();
	}
	
	public void updateListeArbitres(List<String> elementsFiltres) {
	    this.listeArbitres.setListData(elementsFiltres.toArray(new String[0]));
	    this.listeArbitres.repaint();
	}
	
	public JList<Object> getListeArbitres() {
        return this.listeArbitres;
    }

}
