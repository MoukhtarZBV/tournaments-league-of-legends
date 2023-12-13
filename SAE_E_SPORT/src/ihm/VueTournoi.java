package ihm;

import java.awt.EventQueue;

import java.awt.Font;
import java.awt.GridBagConstraints;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import components.JTextFieldArrondi;
import components.PanelRound;
import controleur.ControleurDetailsTournoi;
import dao.ParticiperJDBC;
import Images.Images;
import modele.Equipe;
import modele.Joueur;
import modele.Participer;
import modele.Tournoi;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JSeparator;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.border.LineBorder;
import javax.swing.ImageIcon;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.border.MatteBorder;

public class VueTournoi extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tableEquipes;
	
	private Tournoi tournoi;

	/**
	 * Create the frame.
	 */
	public VueTournoi(Tournoi tournoi) {
		this.tournoi = tournoi;
		ControleurDetailsTournoi controleur = new ControleurDetailsTournoi(this);
		Ecran.setup();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setBounds(Ecran.posX, Ecran.posY, Ecran.tailleX, Ecran.tailleY);
		setBounds(510, 240, 900, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		///// MENU BAR \\\\\
		JPanel panelSide = new JPanel();
		panelSide.setBackground(Palette.DARK_GRAY);
		panelSide.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Palette.COOL));
		panelSide.setPreferredSize(new Dimension(100, 600));
		contentPane.add(panelSide, BorderLayout.WEST);

		///// MAIN \\\\\
		JPanel panelMain = new JPanel();
		panelMain.setLayout(new BorderLayout(0, 0));
		panelMain.setBackground(Palette.GRAY);
		contentPane.add(panelMain, BorderLayout.CENTER);

		///// TOP DU MAIN \\\\\
		JPanel panelTop = new JPanel();
		panelTop.setBorder(new EmptyBorder(10, 50, 10, 50));
		panelTop.setBackground(Palette.GRAY);
		panelMain.add(panelTop, BorderLayout.NORTH);
		panelTop.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel panelCenter = new JPanel();
		panelCenter.setBorder(new EmptyBorder(15, 100, 15, 100));
		panelCenter.setBackground(Palette.DARK_GRAY);
		panelMain.add(panelCenter, BorderLayout.CENTER);
		panelCenter.setLayout(new BorderLayout(0, 0));
		
		///// PANEL INFOS DU TOURNOI \\\\\
		JPanel panelInfos = new JPanel();
		panelInfos.setBackground(Palette.DARK_GRAY);
		panelCenter.add(panelInfos, BorderLayout.NORTH);
		panelInfos.setLayout(new BorderLayout(0, 0));

		///// PANEL LIBELLE INFOS \\\\\
		JPanel panelLibelleInfos = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panelLibelleInfos.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		panelLibelleInfos.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(255, 255, 255)));
		panelLibelleInfos.setBackground(Palette.GRAY);
		panelInfos.add(panelLibelleInfos, BorderLayout.NORTH);

		JLabel lblInfosTournoi = new JLabel("A propos du tournoi");
		lblInfosTournoi.setForeground(new Color(255, 255, 255));
		lblInfosTournoi.setFont(Police.LABEL);
		panelLibelleInfos.add(lblInfosTournoi);

		///// PANEL BULLES INFOS \\\\\
		JPanel panelBullesInfos = new JPanel();
		panelBullesInfos.setBorder(new EmptyBorder(10, 0, 0, 0));
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
		ajouterInfoBulle(panelEquipes, "" + new ParticiperJDBC().getAll().stream().peek(p -> p.getTournoi().getNomTournoi()).filter(participer -> participer.getTournoi().getNomTournoi().equals(tournoi.getNomTournoi())).map(participer -> participer.getEquipe()).count());
		panelEquipesBorder.add(panelEquipes);
		panelBullesInfos.add(panelEquipesBorder);
		
		/********************************/
		/********** FIN BULLES **********/
		/********************************/
		
		///// PANEL TABLE EQUIPES \\\\\
		JPanel panelTableEquipes = new JPanel();
		panelTableEquipes.setBorder(new EmptyBorder(50, 20, 50, 20));
		panelTableEquipes.setBackground(Palette.GRAY);
		panelCenter.add(panelTableEquipes, BorderLayout.CENTER);
		panelTableEquipes.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPaneTableEquipes = new JScrollPane();
		scrollPaneTableEquipes.getViewport().setBackground(Palette.GRAY);
		panelTableEquipes.add(scrollPaneTableEquipes, BorderLayout.CENTER);
		
		// Table des équipes
		tableEquipes = new JTable();
		tableEquipes.setFont(new Font("Arial", Font.PLAIN, 14));
		tableEquipes.getTableHeader().setBackground(Palette.DARK_GRAY);
		tableEquipes.getTableHeader().setForeground(Palette.WHITE);
		tableEquipes.getTableHeader().setFont(Police.LABEL);
		tableEquipes.setBackground(Palette.GRAY);
		tableEquipes.setForeground(Palette.WHITE);
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
		JButton btnImporter = new JButton("<html><body style='padding: 5px 20px;'>Importer des équipes</body></html>");
		btnImporter.setName("Importer des équipes");
		btnImporter.setBackground(Palette.GRAY);
		btnImporter.setForeground(Palette.WHITE);
		btnImporter.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Palette.WHITE));
		btnImporter.setFont(Police.LABEL);
		btnImporter.setFocusable(false);
		btnImporter.addActionListener(controleur);
		panelBoutons.add(btnImporter);
		
		Component horizontalStrut_7 = Box.createHorizontalStrut(20);
		panelBoutons.add(horizontalStrut_7);
		btnRetour.setFocusable(false);
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
		lblIconePays.setFont(new Font("Arial", Font.BOLD, 16));
		lblIconePays.setIcon(icone);
		panelPays.add(lblIconePays, BorderLayout.NORTH);
	}
	
	private void ajouterInfoBulle(PanelRound bulle, String info) {
		JLabel lblPays = new JLabel(info);
		lblPays.setForeground(new Color(255, 255, 255));
		lblPays.setFont(new Font("Arial", Font.BOLD, 12));
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
