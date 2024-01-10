package ihm;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import components.CoolScrollBar;
import components.PanelPopUp;
import components.PanelRound;
import controleur.ControleurDetailsTournoi;
import Images.ImagesIcons;
import modele.Arbitre;
import modele.Equipe;
import modele.Joueur;
import modele.Tournoi;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.border.MatteBorder;

public class VueTournoi extends JFrame {

	private JTable tableEquipes;
	private JButton btnOuvrir;
	private JPanel panelNomsArbitres;
	private JPanel panelBoutons;
	private PanelPopUp panelMessageArbitres;
	
	private Tournoi tournoi;

	private ControleurDetailsTournoi controleur;
	
	/**
	 * Create the frame.
	 */
	public VueTournoi(Tournoi tournoi) {
		this.tournoi = tournoi;
		this.controleur = new ControleurDetailsTournoi(this);
		List<Equipe> equipesTournoi = new Tournoi().getEquipesTournoi(tournoi);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(Ecran.posX, Ecran.posY, Ecran.tailleX, Ecran.tailleY);
		setTitle(tournoi.getNomTournoi());
		
		
		///// PANEL PRINCIPAL \\\\\	
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		
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
		JLabel lblTitre = new JLabel(tournoi.getNomTournoi());
		lblTitre.setBorder(BorderFactory.createMatteBorder(0, 0, 5, 0, Palette.WHITE));
		lblTitre.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitre.setForeground(Palette.WHITE);
		lblTitre.setFont(Police.GROS_TITRE);
		panelTop.add(lblTitre);
		
		
		///// MAN PANEL MILIEU \\\\\
		JPanel panelCenter = new JPanel();
		panelCenter.setLayout(new BorderLayout(0, 15));
		panelCenter.setBackground(Palette.DARK_GRAY);
		panelCenter.setBorder(new EmptyBorder(15, 100, 15, 100));
		panelMain.add(panelCenter, BorderLayout.CENTER);

		
		///// PANEL INFOS DU TOURNOI \\\\\
		JPanel panelInfos = new JPanel();
		panelInfos.setBackground(Palette.DARK_GRAY);
		panelInfos.setLayout(new BorderLayout(0, 0));
		panelCenter.add(panelInfos, BorderLayout.NORTH);

		///// PANEL LIBELLE INFOS \\\\\
		JPanel panelLibelleInfos = new JPanel();
		panelLibelleInfos.setBorder(new EmptyBorder(10, 20, 10, 20));
		panelLibelleInfos.setBackground(Palette.GRAY);
		panelLibelleInfos.setLayout(new GridLayout(0, 1, 0, 0));
		panelInfos.add(panelLibelleInfos, BorderLayout.NORTH);

		JLabel lblInfosTournoi = new JLabel("À propos du tournoi");
		lblInfosTournoi.setBorder(new MatteBorder(0, 0, 2, 0, Palette.WHITE));
		lblInfosTournoi.setForeground(Palette.WHITE);
		lblInfosTournoi.setFont(Police.SOUS_TITRE);
		panelLibelleInfos.add(lblInfosTournoi);

		///// PANEL BULLES INFOS \\\\\
		JPanel panelBullesInfos = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelBullesInfos.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panelBullesInfos.setBorder(new EmptyBorder(10, 15, 10, 15));
		panelBullesInfos.setBackground(Palette.GRAY);
		panelInfos.add(panelBullesInfos, BorderLayout.CENTER);

		/********************************/
		/************ BULLES ************/
		/********************************/
		
		///// BULLE INFO PAYS \\\\\
		PanelRound panelPaysBorder = creerBordureBulleInfo();
		PanelRound panelPays = creerBulleInfo();
		ajouterLibelleBulle(panelPays, "Pays", ImagesIcons.BULLE_PAYS);
		ajouterInfoBulle(panelPays, tournoi.getPays().denomination());
		panelPaysBorder.add(panelPays);
		panelBullesInfos.add(panelPaysBorder);

		///// BULLE INFO NIVEAU \\\\\
		PanelRound panelNiveauBorder = creerBordureBulleInfo();
		PanelRound panelNiveau = creerBulleInfo();
		ajouterLibelleBulle(panelNiveau, "Niveau", ImagesIcons.BULLE_NIVEAU);
		ajouterInfoBulle(panelNiveau, tournoi.getNiveau().denomination());
		panelNiveauBorder.add(panelNiveau);
		panelBullesInfos.add(panelNiveauBorder);
		
		///// BULLE INFO NOMBRE ÉQUIPES \\\\\
		PanelRound panelEquipesBorder = creerBordureBulleInfo();
		PanelRound panelEquipes = creerBulleInfo();
		ajouterLibelleBulle(panelEquipes, "Équipes", ImagesIcons.BULLE_EQUIPES);
		ajouterInfoBulle(panelEquipes, equipesTournoi.size() + " participants");
		panelEquipesBorder.add(panelEquipes);
		panelBullesInfos.add(panelEquipesBorder);
		
		///// BULLE INFO DATES \\\\\
		PanelRound panelDatesBorder = creerBordureBulleInfo();
		PanelRound panelDates = creerBulleInfo();
		ajouterLibelleBulle(panelDates, "Dates", ImagesIcons.BULLE_DATES);
		ajouterInfoBulle(panelDates, Utilitaires.formaterDate(tournoi.getDateDebut()) + " au " + Utilitaires.formaterDate(tournoi.getDateFin()));
		panelDatesBorder.add(panelDates);
		panelBullesInfos.add(panelDatesBorder);

		///// BULLE INFO DATES \\\\\
		PanelRound panelVainqueurBorder = creerBordureBulleInfo();
		PanelRound panelVainqueur = creerBulleInfo();
		ajouterLibelleBulle(panelVainqueur, "Vainqueur", ImagesIcons.BULLE_VAINQUEUR);
		if (tournoi.getVainqueur() == null) {
			ajouterInfoBulle(panelVainqueur, "Aucun");
		} else {
			ajouterInfoBulle(panelVainqueur, tournoi.getVainqueur().getNom());
		}
		panelVainqueurBorder.add(panelVainqueur);
		panelBullesInfos.add(panelVainqueurBorder);
		
		/********************************/
		/********** FIN BULLES **********/
		/********************************/
		
		///// PANEL TABLE EQUIPES \\\\\
		JPanel panelTableEquipes = new JPanel();
		panelTableEquipes.setBorder(new EmptyBorder(15, 15, 15, 15));
		panelTableEquipes.setBackground(Palette.GRAY);
		panelCenter.add(panelTableEquipes, BorderLayout.CENTER);
		panelTableEquipes.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPaneTableEquipes = new JScrollPane();
		scrollPaneTableEquipes.getViewport().setBackground(Palette.GRAY);
		scrollPaneTableEquipes.setVerticalScrollBar(new CoolScrollBar());
		panelTableEquipes.add(scrollPaneTableEquipes, BorderLayout.CENTER);
		
		// Table des équipes
		tableEquipes = new JTable();
		tableEquipes.setFont(Police.TABLEAU);
		tableEquipes.setRowHeight(30);
		tableEquipes.getTableHeader().setBackground(Palette.DARK_GRAY);
		tableEquipes.getTableHeader().setForeground(Palette.WHITE);
		tableEquipes.getTableHeader().setFont(Police.LABEL);
		tableEquipes.getTableHeader().setReorderingAllowed(false);
		tableEquipes.getTableHeader().setResizingAllowed(false);
		tableEquipes.setBackground(Palette.DARK_GRAY);
		tableEquipes.setForeground(Palette.WHITE);
		tableEquipes.addMouseListener(controleur);
		scrollPaneTableEquipes.setViewportView(tableEquipes);
		DefaultTableModel modele = new DefaultTableModel(new Object[][] {},
	            new String[] { "Équipe", "Joueur 1", "Joueur 2", "Joueur 3", "Joueur 4", "Joueur 5" }) {
	                
				@Override
			    public boolean isCellEditable(int row, int column) {
			       return false;
			    }
			};
		tableEquipes.setModel(modele);
		afficherEquipes(equipesTournoi);
		
		// Assigner images au header
		mettreIconeDansHeader("Joueur 1", ImagesIcons.TOP);
		mettreIconeDansHeader("Joueur 2", ImagesIcons.JUNGLE);
		mettreIconeDansHeader("Joueur 3", ImagesIcons.MID);
		mettreIconeDansHeader("Joueur 4", ImagesIcons.SUPPORT);
		mettreIconeDansHeader("Joueur 5", ImagesIcons.BOTTOM);
		
		///// PANEL BOUTONS \\\\\
		FlowLayout fl_panelBoutons = new FlowLayout(FlowLayout.RIGHT, 5, 5);
		
		panelBoutons = new JPanel();
		panelBoutons.setBorder(new EmptyBorder(0, 0, 0, 0));
		panelBoutons.setBackground(Palette.DARK_GRAY);
		panelBoutons.setLayout(fl_panelBoutons);
		panelCenter.add(panelBoutons, BorderLayout.SOUTH);
		
		// Bouton annuler
		JButton btnRetour = new JButton("Retour");
		btnRetour.setName("Retour");
		btnRetour.setBackground(Palette.GRAY);
		btnRetour.setForeground(Palette.WHITE);
		btnRetour.setBorder(Utilitaires.BORDER_BOUTONS);
		btnRetour.setFont(Police.LABEL);
		btnRetour.addActionListener(controleur);
		btnRetour.setFocusable(false);
		panelBoutons.add(btnRetour);
		
		JButton btnSupprimer = new JButton("Supprimer");
		btnSupprimer.setName("Supprimer");
		btnSupprimer.addActionListener(controleur);
		panelBoutons.add(btnSupprimer);

		///// PANEL ARBITRES \\\\\
		JPanel panelArbitres = new JPanel();
		panelArbitres.setBorder(new EmptyBorder(25, 0, 0, 0));
		panelArbitres.setBackground(Palette.GRAY);
		panelTableEquipes.add(panelArbitres, BorderLayout.SOUTH);
		panelArbitres.setLayout(new BorderLayout(0, 0));

		JLabel lblArbitres = new JLabel("Arbitres");
		lblArbitres.setBorder(new MatteBorder(0, 0, 2, 0, Palette.WHITE));
		lblArbitres.setForeground(Palette.WHITE);
		lblArbitres.setFont(Police.SOUS_TITRE);
		panelArbitres.add(lblArbitres, BorderLayout.NORTH);

		// Liste des noms et prénoms des arbitres
		panelNomsArbitres = new JPanel();
		((FlowLayout) panelNomsArbitres.getLayout()).setAlignment(FlowLayout.LEFT);
		panelNomsArbitres.setBackground(Palette.GRAY);
		panelNomsArbitres.setBorder(null);
		panelArbitres.add(panelNomsArbitres);
		
		afficherBoutonsEtArbitres();
	}

	private void afficherBoutonsEtArbitres() {
		switch (tournoi.getStatut()) {
		case ATTENTE_EQUIPES:
			afficherBoutonImporter();
			afficherMessageArbitres(true);
			break;
		case A_VENIR:
			afficherBoutonOuvrir();
			/*
			if (tournoi.getDateDebut().after(new Date(System.currentTimeMillis()))) {
				btnOuvrir.setEnabled(false);
			}
			*/
			afficherMessageArbitres(true);
			break;
		case EN_COURS:
			afficherBoutonGererPoule("Gérer la poule");
			afficherArbitresTournoi();
			break;
		case FINALE:
		case ATTENTE_RESULTATS:
			afficherBoutonGererPoule("Consulter la poule");
			afficherBoutonFinale("Gérer la finale");
			afficherArbitresTournoi();
			break;
		case TERMINE:
			afficherBoutonGererPoule("Consulter la poule");
			afficherBoutonFinale("Consulter la finale");
			afficherArbitresTournoi();
			break;
		case ANNULE:
			afficherBoutonGererPoule("Consulter la poule");
			afficherArbitresTournoi();
			break;
		}
	}
	
	public void afficherArbitresTournoi() {
		List<Arbitre> arbitresTournoi = new Tournoi().getArbitresTournoi(tournoi);
		for (Arbitre arbitre : arbitresTournoi) {
			JLabel labelArbitre = new JLabel(arbitre.getNom() + " " + arbitre.getPrenom());
			labelArbitre.setForeground(Palette.WHITE);
			labelArbitre.setFont(Police.LABEL);
			labelArbitre.setBackground(Palette.DARK_GRAY);
			labelArbitre.setOpaque(true);
			labelArbitre.setBorder(new CompoundBorder(new MatteBorder(0, 0, 1, 0, Palette.WHITE), Utilitaires.EMPTY_BORDER_BOUTONS));
			panelNomsArbitres.add(labelArbitre);
		}
	}
	
	public void afficherMessageArbitres(boolean afficher) {
		if (afficher) {
			panelMessageArbitres = new PanelPopUp();
			panelMessageArbitres.setNormal("Ouvrez le tournoi pour assigner des arbitres");
			panelNomsArbitres.setLayout(new BorderLayout());
			panelNomsArbitres.add(panelMessageArbitres, BorderLayout.CENTER);
		} else {
			for (Component c : panelNomsArbitres.getComponents()) {
				panelNomsArbitres.remove(c); }
			panelNomsArbitres.setLayout(new FlowLayout());
			((FlowLayout) panelNomsArbitres.getLayout()).setAlignment(FlowLayout.LEFT);
		}
	}
	
	public void afficherBoutonImporter() {
		JButton btnImporter = new JButton("Importer des équipes");
		btnImporter.setName("Importer");
		btnImporter.setBackground(Palette.GRAY);
		btnImporter.setForeground(Palette.WHITE);
		btnImporter.setBorder(Utilitaires.BORDER_BOUTONS);
		btnImporter.setFont(Police.LABEL);
		btnImporter.setFocusable(false);
		btnImporter.addActionListener(controleur);
		panelBoutons.add(btnImporter);
	}

	public void afficherBoutonOuvrir() {
		btnOuvrir = new JButton("Ouvrir le tournoi");
		btnOuvrir.setName("Ouvrir");
		btnOuvrir.setBackground(Palette.GRAY);
		btnOuvrir.setForeground(Palette.WHITE);
		btnOuvrir.setBorder(Utilitaires.BORDER_BOUTONS);
		btnOuvrir.setFont(Police.LABEL);
		btnOuvrir.setFocusable(false);
		btnOuvrir.addActionListener(controleur);
		panelBoutons.add(btnOuvrir);
	}

	public void afficherBoutonGererPoule(String nomBouton) {
		JButton btnRetour = new JButton(nomBouton);
		btnRetour.setName("Poule");
		btnRetour.setBackground(Palette.GRAY);
		btnRetour.setForeground(Palette.WHITE);
		btnRetour.setBorder(Utilitaires.BORDER_BOUTONS);
		btnRetour.setFont(Police.LABEL);
		btnRetour.setFocusable(false);
		btnRetour.addActionListener(controleur);
		panelBoutons.add(btnRetour);
	}

	public void afficherBoutonFinale(String nomBouton) {
		JButton btnFinale = new JButton(nomBouton);
		btnFinale.setName("Finale");
		btnFinale.setBackground(Palette.GRAY);
		btnFinale.setForeground(Palette.WHITE);
		btnFinale.setBorder(Utilitaires.BORDER_BOUTONS);
		btnFinale.setFont(Police.LABEL);
		btnFinale.setFocusable(false);
		btnFinale.addActionListener(controleur);
		panelBoutons.add(btnFinale);
	}
	
	private PanelRound creerBordureBulleInfo() {
		PanelRound bordureBulle = new PanelRound();
		FlowLayout flowLayout = (FlowLayout) bordureBulle.getLayout();
		flowLayout.setVgap(1);
		flowLayout.setHgap(1);
		bordureBulle.setBorder(new EmptyBorder(1, 1, 1, 1));
		bordureBulle.setRoundTopLeft(20);
		bordureBulle.setRoundTopRight(20);
		bordureBulle.setRoundBottomRight(20);
		bordureBulle.setBackground(Palette.WHITE);
		return bordureBulle;
	}
	
	private PanelRound creerBulleInfo() {
		PanelRound bulle = new PanelRound();
		bulle.setBorder(new EmptyBorder(10, 15, 10, 15));
		bulle.setRoundTopLeft(20);
		bulle.setRoundTopRight(20);
		bulle.setRoundBottomRight(20);
		bulle.setBackground(Palette.DARK_GRAY);
		bulle.setLayout(new BorderLayout(0, 0));
		return bulle;
	}
	
	private void ajouterLibelleBulle(PanelRound panelPays, String libelle, ImageIcon icone) {
		JLabel lblIconePays = new JLabel(" " + libelle);
		lblIconePays.setForeground(Palette.LIGHT_PURPLE);
		lblIconePays.setFont(Police.LABEL);
		lblIconePays.setIcon(icone);
		panelPays.add(lblIconePays, BorderLayout.NORTH);
	}
	
	private void ajouterInfoBulle(PanelRound bulle, String info) {
		JLabel lblPays = new JLabel(info);
		lblPays.setBorder(new EmptyBorder(2, 0, 0, 0));
		lblPays.setForeground(Palette.WHITE);
		lblPays.setFont(Police.TABLEAU);
		bulle.add(lblPays, BorderLayout.SOUTH);
	}
	
	public void setVisibleBoutonOuvrir(boolean visible) {
		this.btnOuvrir.setVisible(visible);
	}
	
	public void afficherMessageErreurArbitres() {
		this.panelMessageArbitres.setErreur("Il n'y a pas assez d'arbitres disponibles pour ouvrir ce tournoi");
	}

	private void mettreIconeDansHeader(String colonne, ImageIcon image) {
		tableEquipes.getColumn(colonne).setHeaderRenderer(new TableCellRenderer() {
		        @Override
		        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		               return new JLabel(image);
		        }
		    });
	}

	public void afficherEquipes(List<Equipe> equipesTournoi) {
		DefaultTableModel modele = (DefaultTableModel) tableEquipes.getModel();
		for (Equipe equipe : equipesTournoi) {
			List<Joueur> joueursEquipe = equipe.getJoueurs();
			modele.addRow(new Object[] {
				equipe.getNom(), joueursEquipe.get(0).getPseudo(), joueursEquipe.get(1).getPseudo(), joueursEquipe.get(2).getPseudo(), joueursEquipe.get(3).getPseudo(), joueursEquipe.get(4).getPseudo(),  
			});
		}
	}
	
	public Tournoi getTournoi() {
		return this.tournoi;
	}
}
