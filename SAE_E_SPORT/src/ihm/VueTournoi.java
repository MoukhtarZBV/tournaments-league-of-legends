package ihm;

import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import components.PanelRound;
import controleur.ControleurDetailsTournoi;
import dao.AssocierJDBC;
import dao.ParticiperJDBC;
import dao.TournoiJDBC;
import Images.Images;
import modele.Arbitre;
import modele.Equipe;
import modele.Joueur;
import modele.Statut;
import modele.Tournoi;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
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
import javax.swing.border.LineBorder;

public class VueTournoi extends JFrame {

	private JTable tableEquipes;
	private Tournoi tournoi;

	/**
	 * Create the frame.
	 */
	public VueTournoi(Tournoi tournoi) {
		this.tournoi = tournoi;
		ControleurDetailsTournoi controleur = new ControleurDetailsTournoi(this);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(Ecran.posX, Ecran.posY, Ecran.tailleX, Ecran.tailleY);
		setTitle(tournoi.getNomTournoi());
		addWindowListener(controleur);
		
		
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

		JLabel lblInfosTournoi = new JLabel("A propos du tournoi");
		lblInfosTournoi.setBorder(new MatteBorder(0, 0, 2, 0, Palette.WHITE));
		lblInfosTournoi.setForeground(Palette.WHITE);
		lblInfosTournoi.setFont(Police.SOUS_TITRE);
		panelLibelleInfos.add(lblInfosTournoi);

		///// PANEL BULLES INFOS \\\\\
		JPanel panelBullesInfos = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelBullesInfos.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panelBullesInfos.setBorder(new EmptyBorder(10, 20, 10, 20));
		panelBullesInfos.setBackground(Palette.GRAY);
		panelInfos.add(panelBullesInfos, BorderLayout.CENTER);

		/********************************/
		/************ BULLES ************/
		/********************************/
		
		///// BULLE INFO PAYS \\\\\
		PanelRound panelPaysBorder = creerBordureBulleInfo();
		PanelRound panelPays = creerBulleInfo();
		ajouterLibelleBulle(panelPays, "Pays", Images.EARTH);
		ajouterInfoBulle(panelPays, tournoi.getPays().denomination());
		panelPaysBorder.add(panelPays);
		panelBullesInfos.add(panelPaysBorder);

		///// BULLE INFO NIVEAU \\\\\
		PanelRound panelNiveauBorder = creerBordureBulleInfo();
		PanelRound panelNiveau = creerBulleInfo();
		ajouterLibelleBulle(panelNiveau, "Niveau", Images.LEVEL);
		ajouterInfoBulle(panelNiveau, tournoi.getNiveau().denomination());
		panelNiveauBorder.add(panelNiveau);
		panelBullesInfos.add(panelNiveauBorder);
		
		///// BULLE INFO NOMBRE ÉQUIPES \\\\\
		PanelRound panelEquipesBorder = creerBordureBulleInfo();
		PanelRound panelEquipes = creerBulleInfo();
		ajouterLibelleBulle(panelEquipes, "Équipes", Images.TEAM);
		ajouterInfoBulle(panelEquipes, "" + new ParticiperJDBC().getAll().stream().filter(participer -> participer.getTournoi().getNomTournoi().equals(tournoi.getNomTournoi())).map(participer -> participer.getEquipe()).count());
		panelEquipesBorder.add(panelEquipes);
		panelBullesInfos.add(panelEquipesBorder);
		
		///// BULLE INFO DATES \\\\\
		PanelRound panelDatesBorder = creerBordureBulleInfo();
		PanelRound panelDates = creerBulleInfo();
		ajouterLibelleBulle(panelDates, "Dates", Images.TEAM);
		ajouterInfoBulle(panelDates, tournoi.getDateDebut() + " au " + tournoi.getDateFin());
		panelDatesBorder.add(panelDates);
		panelBullesInfos.add(panelDatesBorder);
		
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
		panelTableEquipes.add(scrollPaneTableEquipes, BorderLayout.CENTER);
		
		// Table des équipes
		tableEquipes = new JTable();
		tableEquipes.setFont(Police.TABLEAU);
		tableEquipes.setRowHeight(30);
		tableEquipes.getTableHeader().setBackground(Palette.DARK_GRAY);
		tableEquipes.getTableHeader().setForeground(Palette.WHITE);
		tableEquipes.getTableHeader().setFont(Police.LABEL);
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
		try {
			afficherEquipes(tournoi);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Assigner images au header
		mettreIconeDansHeader("Joueur 1", Images.TOP);
		mettreIconeDansHeader("Joueur 2", Images.JUNGLE);
		mettreIconeDansHeader("Joueur 3", Images.MID);
		mettreIconeDansHeader("Joueur 4", Images.SUPPORT);
		mettreIconeDansHeader("Joueur 5", Images.BOTTOM);
		
		///// PANEL BOUTONS \\\\\
		FlowLayout fl_panelBoutons = new FlowLayout(FlowLayout.RIGHT, 5, 5);
		
		JPanel panelBoutons = new JPanel();
		panelBoutons.setBorder(new EmptyBorder(0, 0, 0, 0));
		panelBoutons.setBackground(Palette.DARK_GRAY);
		panelBoutons.setLayout(fl_panelBoutons);
		panelCenter.add(panelBoutons, BorderLayout.SOUTH);
		
		// Bouton annuler
		JButton btnAnnuler = new JButton("<html><body style='padding: 5px 20px;'>Retour</body></html>");
		btnAnnuler.setName("Retour");
		btnAnnuler.setBackground(Palette.GRAY);
		btnAnnuler.setForeground(Palette.WHITE);
		btnAnnuler.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Palette.WHITE));
		btnAnnuler.setFont(Police.LABEL);
		btnAnnuler.addActionListener(controleur);
		btnAnnuler.setFocusable(false);
		panelBoutons.add(btnAnnuler);
		
		// Bouton valider
		JButton btnRetour = new JButton("<html><body style='padding: 5px 20px;'>Gérer la poule</body></html>");
		btnRetour.setName("Gérer la poule");
		btnRetour.setBackground(Palette.GRAY);
		btnRetour.setForeground(Palette.WHITE);
		btnRetour.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Palette.WHITE));
		btnRetour.setFont(Police.LABEL);
		btnRetour.setFocusable(false);
		btnRetour.addActionListener(controleur);
		panelBoutons.add(btnRetour);
		
		// Bouton Importer
		if (new TournoiJDBC().nombreEquipesTournoi(tournoi) < 4) {
			JButton btnImporter = new JButton("<html><body style='padding: 5px 20px;'>Importer des équipes</body></html>");
			btnImporter.setName("Importer des équipes");
			btnImporter.setBackground(Palette.GRAY);
			btnImporter.setForeground(Palette.WHITE);
			btnImporter.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Palette.WHITE));
			btnImporter.setFont(Police.LABEL);
			btnImporter.setFocusable(false);
			btnImporter.addActionListener(controleur);
			panelBoutons.add(btnImporter);
		}
		
		// Bouton ouvrir le tournoi
		if (tournoi.getStatut() == Statut.A_VENIR) {
			JButton btnOuvrir = new JButton("<html><body style='padding: 5px 20px;'>Ouvrir le tournoi</body></html>");
			btnOuvrir.setName("Ouvrir le tournoi");
			btnOuvrir.setBackground(Palette.GRAY);
			btnOuvrir.setForeground(Palette.WHITE);
			btnOuvrir.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Palette.WHITE));
			btnOuvrir.setFont(Police.LABEL);
			btnOuvrir.setFocusable(false);
			btnOuvrir.addActionListener(controleur);
			panelBoutons.add(btnOuvrir);
		}
		
		JPanel panelArbitres = new JPanel();
		panelArbitres.setBorder(new EmptyBorder(25, 0, 0, 0));
		panelArbitres.setBackground(Palette.GRAY);
		panelTableEquipes.add(panelArbitres, BorderLayout.SOUTH);
		panelArbitres.setLayout(new BorderLayout(0, 0));
		

		JLabel lblNewLabel = new JLabel("Arbitres");
		lblNewLabel.setBorder(new MatteBorder(0, 0, 2, 0, Palette.WHITE));
		lblNewLabel.setForeground(Palette.WHITE);
		lblNewLabel.setFont(Police.SOUS_TITRE);
		panelArbitres.add(lblNewLabel, BorderLayout.NORTH);
		
		JPanel panelNomsArbitres = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panelNomsArbitres.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		panelNomsArbitres.setBackground(Palette.GRAY);
		panelArbitres.add(panelNomsArbitres);
		
		List<Arbitre> arbitresTournoi = new AssocierJDBC().getAll().stream().filter(associer -> associer.getTournoi().getNomTournoi().equals(tournoi.getNomTournoi())).map(associer -> associer.getArbitre()).collect(Collectors.toList());
		for (Arbitre arbitre : arbitresTournoi) {
			JLabel labelArbitre = new JLabel("<html><body style='padding: 5px 20px;'>" + arbitre.getNom() + " " + arbitre.getPrenom() + "</body></html>");
			labelArbitre.setForeground(Palette.WHITE);
			labelArbitre.setFont(Police.LABEL);
			labelArbitre.setBackground(Palette.DARK_GRAY);
			labelArbitre.setOpaque(true);
			labelArbitre.setBorder(new MatteBorder(0, 0, 1, 0, Palette.WHITE));
			panelNomsArbitres.add(labelArbitre);
		}
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
		bulle.setBorder(new EmptyBorder(10, 20, 10, 20));
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
		lblPays.setForeground(Palette.WHITE);
		lblPays.setFont(Police.INFO);
		bulle.add(lblPays, BorderLayout.SOUTH);
	}

	private void mettreIconeDansHeader(String colonne, ImageIcon image) {
		tableEquipes.getColumn(colonne).setHeaderRenderer(new TableCellRenderer() {
		        @Override
		        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		               return new JLabel(image);
		        }
		    });
	}

	public void afficherEquipes(Tournoi tournoi) throws Exception {
		DefaultTableModel modele = (DefaultTableModel) tableEquipes.getModel();
		List<Equipe> equipes = new ParticiperJDBC().getAll().stream().peek(p -> p.getTournoi().getNomTournoi()).filter(participer -> participer.getTournoi().getNomTournoi().equals(tournoi.getNomTournoi())).map(participer -> participer.getEquipe()).collect(Collectors.toList());
		System.out.println(equipes);
		for (Equipe equipe : equipes) {
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
