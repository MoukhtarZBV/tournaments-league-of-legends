package ihm;

import java.awt.FlowLayout;

import java.awt.GridLayout;
import java.util.*;
import java.util.stream.Collectors;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import components.CoolTextField;
import components.PanelPopUp;
import controleur.ControleurListeArbitre;
import modele.Arbitre;
import modele.Tournoi;

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
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;


public class VueListeArbitre extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JTextField searchBar;
	private JList<Object> listeArbitres;
	private List<Arbitre> arbitres;
	private List<Arbitre> arbitresAttribues;
	
	private JButton btnSuppr;
	private JButton btnVider;
	private JButton btnAttribuer;
	private JButton btnConfirmer;
	
	private JPanel panelNomsArbitresAttribues;
	
	private boolean modeAjout;
	private Tournoi tournoi;
	
	public VueListeArbitre(List<Arbitre> arbitres, boolean modeAjout, Tournoi tournoi) {
		
		this.arbitres = arbitres;
		this.arbitresAttribues = new ArrayList<>();
		this.modeAjout = modeAjout;
		this.tournoi = tournoi;
		
		ControleurListeArbitre controleur = new ControleurListeArbitre(this);
		
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
		header.setTitre("Arbitres");
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
		searchBar = new CoolTextField();
		searchBar.setBackground(Palette.DARK_GRAY);
		searchBar.setForeground(Palette.WHITE);
		searchBar.setFont(Police.INPUT);
		searchBar.setPreferredSize(new Dimension(searchBar.getPreferredSize().width, 25));
		searchBar.setColumns(30);
		searchBar.addActionListener(controleur);
		searchBar.addFocusListener(controleur);
		panelSearch.add(searchBar);
		
		
		// Panel liste
		JPanel panelListe = new JPanel();
		panelListe.setLayout(new BorderLayout(5, 0));
		panelListe.setBorder(new EmptyBorder(25, 25, 0, 25));
		panelListe.setBackground(Palette.GRAY);
		panelCenter.add(panelListe, BorderLayout.CENTER);

		JLabel lblHeader = new JLabel(String.format("%-20s %-30s", "Nom", "Prenom"));
		lblHeader.setFont(Police.TABLEAU);
		lblHeader.setBorder(new EmptyBorder(5, 10, 5, 10));
		lblHeader.setForeground(Palette.WHITE);
		panelListe.add(lblHeader, BorderLayout.NORTH);
		
		// Liste des arbitres
		this.listeArbitres = new JList<Object>();
		listeArbitres.setFont(Police.TABLEAU_MONO);
		listeArbitres.setBackground(Palette.GRAY);
		listeArbitres.setForeground(Palette.WHITE);
		listeArbitres.setBorder(new EmptyBorder(5, 5, 5, 5));
		listeArbitres.addMouseListener(controleur);
		updateListeArbitres(new Arbitre().arbitresContenant(arbitres, ""));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(listeArbitres);
		scrollPane.setBorder(null);
		panelListe.add(scrollPane, BorderLayout.CENTER);
		
		if (modeAjout) {
			///// PANEL ARBITRES \\\\\
			JPanel panelArbitres = new JPanel();
			panelArbitres.setBorder(new EmptyBorder(25, 0, 15, 0));
			panelArbitres.setBackground(Palette.GRAY);
			panelArbitres.setLayout(new BorderLayout(0, 0));
			panelListe.add(panelArbitres, BorderLayout.SOUTH);

			JLabel lblArbitres = new JLabel("Arbitres attribués");
			lblArbitres.setBorder(new MatteBorder(0, 0, 2, 0, Palette.WHITE));
			lblArbitres.setForeground(Palette.WHITE);
			lblArbitres.setFont(Police.SOUS_TITRE);
			panelArbitres.add(lblArbitres, BorderLayout.NORTH);

			// Liste des noms et prénoms des arbitres
			panelNomsArbitresAttribues = new JPanel();
			panelNomsArbitresAttribues.setBackground(Palette.GRAY);
			panelNomsArbitresAttribues.setBorder(null);
			panelArbitres.add(panelNomsArbitresAttribues);
			afficherMessageArbitres();
		}
		
		
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
		
		if (!modeAjout) {
			// Bouton Supprimer
			this.btnSuppr = new JButton("Supprimer");
			btnSuppr.setName("Supprimer");
			btnSuppr.setEnabled(false);
			btnSuppr.setBackground(Palette.GRAY);
			btnSuppr.setForeground(Palette.ERREUR);
			btnSuppr.setBorder(Utilitaires.BORDER_BOUTONS_DANGEREUX);
			btnSuppr.setFont(Police.LABEL);
			btnSuppr.addActionListener(controleur);
			btnSuppr.addMouseListener(controleur);
			btnSuppr.setFocusable(false);
			panelBoutons.add(btnSuppr);
			
			// Bouton Ajouter
			JButton btnAjouter = new JButton("Ajouter");
			btnAjouter.setName("Ajouter");
			btnAjouter.setForeground(Color.WHITE);
			btnAjouter.setFont(new Font("Tahoma", Font.PLAIN, 20));
			btnAjouter.setFocusable(false);
			btnAjouter.setBorder(Utilitaires.BORDER_BOUTONS);
			btnAjouter.setBackground(new Color(32, 28, 44));
			btnAjouter.addActionListener(controleur);
			btnAjouter.addMouseListener(controleur);
			panelBoutons.add(btnAjouter);
		} else {
			// Bouton attribuer arbitre
			this.btnVider = new JButton("Vider liste");
			btnVider.setName("Vider");
			btnVider.setEnabled(false);
			btnVider.setBackground(Palette.GRAY);
			btnVider.setForeground(Palette.WHITE);
			btnVider.setBorder(Utilitaires.BORDER_BOUTONS);
			btnVider.setFont(Police.LABEL);
			btnVider.addActionListener(controleur);
			btnVider.addMouseListener(controleur);
			btnVider.setFocusable(false);
			btnVider.setEnabled(false);
			panelBoutons.add(btnVider);
						
			// Bouton attribuer arbitre
			this.btnAttribuer = new JButton("Attribuer l'arbitre");
			btnAttribuer.setName("Attribuer");
			btnAttribuer.setEnabled(false);
			btnAttribuer.setBackground(Palette.GRAY);
			btnAttribuer.setForeground(Palette.WHITE);
			btnAttribuer.setBorder(Utilitaires.BORDER_BOUTONS);
			btnAttribuer.setFont(Police.LABEL);
			btnAttribuer.addActionListener(controleur);
			btnAttribuer.addMouseListener(controleur);
			btnAttribuer.setFocusable(false);
			btnAttribuer.setEnabled(false);
			panelBoutons.add(btnAttribuer);

			// Bouton confirmer attribution
			this.btnConfirmer = new JButton("Confirmer");
			btnConfirmer.setName("Confirmer");
			btnConfirmer.setEnabled(false);
			btnConfirmer.setBackground(Palette.GRAY);
			btnConfirmer.setForeground(Palette.WHITE);
			btnConfirmer.setBorder(Utilitaires.BORDER_BOUTONS);
			btnConfirmer.setFont(Police.LABEL);
			btnConfirmer.addActionListener(controleur);
			btnConfirmer.addMouseListener(controleur);
			btnConfirmer.setFocusable(false);
			btnConfirmer.setEnabled(false);
			panelBoutons.add(btnConfirmer);
		}
	}
	
	public void setActifBtnSupprimer(boolean actif) {
		this.btnSuppr.setEnabled(actif);;
	}
	
	public void setActifBtnVider(boolean actif) {
		this.btnVider.setEnabled(actif);
	}
	
	public void setActifBtnAttribuer(boolean actif) {
		this.btnAttribuer.setEnabled(actif);
	}
	
	public void setActifBtnConfirmer(boolean actif) {
		this.btnConfirmer.setEnabled(actif);
	}
	
	public boolean enModeAjout() {
		return this.modeAjout;
	}
	
	public void setJListArbitre () throws InterruptedException {
		List<String> nomArbitres = this.arbitres.stream()
				.map(a -> String.format("%-11s %-30s", a.getNom(), a.getPrenom()))
				.sorted((x,y)-> x.compareTo(y))
				.collect(Collectors.toList());
		
		this.listeArbitres = new JList<Object>(nomArbitres.toArray());
		listeArbitres.setFont(Police.TABLEAU_MONO);
		listeArbitres.setBackground(Palette.GRAY);
		listeArbitres.setForeground(Palette.WHITE);
		listeArbitres.setBorder(new EmptyBorder(10, 10, 10, 10));
	}
	
	public String getSearch() {
		return searchBar.getText();
	}
	
	public JList<Object> getListArbitre() {
		return this.listeArbitres;
	}
	
	public List<Arbitre> getArbitresAttribues() {
		return this.arbitresAttribues;
	}
	
	public Tournoi getTournoi() {
		return this.tournoi;
	}
	
	public void updateListeArbitres(List<String> arbitres) {
	    this.listeArbitres.setListData(arbitres.toArray(new String[0]));
	    this.listeArbitres.repaint();
	}
	
	public void afficherArbitresAttribue(Arbitre arbitre) {
		if (this.arbitresAttribues.size() == 0) {
			viderPanelArbitres();
			panelNomsArbitresAttribues.setLayout(new FlowLayout());
			((FlowLayout) panelNomsArbitresAttribues.getLayout()).setAlignment(FlowLayout.LEFT);
		}
		this.arbitres.remove(this.arbitres.indexOf(arbitre));
		this.arbitresAttribues.add(arbitre);
		JLabel labelArbitre = new JLabel(arbitre.getNom() + " " + arbitre.getPrenom());
		labelArbitre.setForeground(Palette.WHITE);
		labelArbitre.setFont(Police.LABEL);
		labelArbitre.setBackground(Palette.DARK_GRAY);
		labelArbitre.setOpaque(true);
		labelArbitre.setBorder(new CompoundBorder(new MatteBorder(0, 0, 1, 0, Palette.WHITE), Utilitaires.EMPTY_BORDER_BOUTONS));
		this.panelNomsArbitresAttribues.add(labelArbitre);
		this.panelNomsArbitresAttribues.repaint();
		this.panelNomsArbitresAttribues.revalidate();
	}
	
	public void viderArbitresAttribues() {
		viderPanelArbitres();
		int nbArbitresAttribues = this.arbitresAttribues.size();
		for (int i = 0; i < nbArbitresAttribues; i++) {
			this.arbitres.add(this.arbitresAttribues.get(0));
			this.arbitresAttribues.remove(0);
		}
	}

	private void viderPanelArbitres() {
		for (Component c : this.panelNomsArbitresAttribues.getComponents()) {
			this.panelNomsArbitresAttribues.remove(c);
		}
		this.panelNomsArbitresAttribues.repaint();
		this.panelNomsArbitresAttribues.revalidate();
	}
	
	public void afficherMessageArbitres() {
		PanelPopUp panelMessageArbitres = new PanelPopUp();
		panelMessageArbitres.setNormal("Aucun arbitre attribué");
		panelNomsArbitresAttribues.setLayout(new BorderLayout());
		panelNomsArbitresAttribues.add(panelMessageArbitres, BorderLayout.CENTER);
	}
	
	public JList<Object> getListeArbitres() {
        return this.listeArbitres;
    }
	
	public List<Arbitre> getArbitres(){
		return arbitres;
	}

}
