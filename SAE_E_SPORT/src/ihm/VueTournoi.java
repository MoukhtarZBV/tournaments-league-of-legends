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
		
		System.out.println(tournoi.getNomTournoi());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		
		/***********************************************/
		
		JPanel panelTop = new JPanel();
		panelTop.setBorder(new EmptyBorder(10, 50, 10, 50));
		panelTop.setBackground(Palette.GRAY);
		panelMain.add(panelTop, BorderLayout.NORTH);
		panelTop.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		///// BULLE INFO PAYS \\\\\
		PanelRound panelPaysBorder = new PanelRound();
		FlowLayout flowLayout = (FlowLayout) panelPaysBorder.getLayout();
		flowLayout.setVgap(1);
		flowLayout.setHgap(1);
		panelPaysBorder.setBorder(new EmptyBorder(1, 1, 1, 1));
		panelPaysBorder.setRoundTopLeft(20);
		panelPaysBorder.setRoundTopRight(20);
		panelPaysBorder.setRoundBottomRight(20);
		panelPaysBorder.setBackground(Palette.WHITE);
		
		PanelRound panelPays = new PanelRound();
		panelPays.setBorder(new EmptyBorder(10, 20, 10, 20));
		panelPays.setRoundTopLeft(20);
		panelPays.setRoundTopRight(20);
		panelPays.setRoundBottomRight(20);
		panelPays.setBackground(Palette.DARK_GRAY);
		panelPays.setLayout(new BorderLayout(0, 0));
		
		panelPaysBorder.add(panelPays);
		
		JLabel lblIconePays = new JLabel(" Pays");
		lblIconePays.setForeground(new Color(255, 255, 255));
		lblIconePays.setFont(new Font("Arial", Font.BOLD, 16));
		lblIconePays.setIcon(Images.EARTH);
		panelPays.add(lblIconePays, BorderLayout.NORTH);
		
		JLabel lblPays = new JLabel("France");
		lblPays.setForeground(new Color(255, 255, 255));
		lblPays.setFont(new Font("Arial", Font.BOLD, 12));
		panelPays.add(lblPays, BorderLayout.SOUTH);
		panelTop.add(panelPaysBorder);

		///// BULLE INFO NIVEAU \\\\\
		PanelRound panelNiveauBorder = new PanelRound();
		flowLayout = (FlowLayout) panelNiveauBorder.getLayout();
		flowLayout.setVgap(1);
		flowLayout.setHgap(1);
		panelNiveauBorder.setBorder(new EmptyBorder(1, 1, 1, 1));
		panelNiveauBorder.setRoundTopLeft(20);
		panelNiveauBorder.setRoundTopRight(20);
		panelNiveauBorder.setRoundBottomRight(20);
		panelNiveauBorder.setBackground(Palette.WHITE);

		PanelRound panelNiveau = new PanelRound();
		panelNiveau.setBorder(new EmptyBorder(10, 20, 10, 20));
		panelNiveau.setRoundTopLeft(20);
		panelNiveau.setRoundTopRight(20);
		panelNiveau.setRoundBottomRight(20);
		panelNiveau.setBackground(Palette.DARK_GRAY);
		panelNiveau.setLayout(new BorderLayout(0, 0));

		panelNiveauBorder.add(panelNiveau);

		JLabel lblIconeNiveau = new JLabel(" Niveau");
		lblIconeNiveau.setForeground(new Color(255, 255, 255));
		lblIconeNiveau.setFont(new Font("Arial", Font.BOLD, 16));
		lblIconeNiveau.setIcon(Images.LEVEL);
		panelNiveau.add(lblIconeNiveau, BorderLayout.NORTH);

		JLabel lblNiveau = new JLabel("National");
		lblNiveau.setForeground(new Color(255, 255, 255));
		lblNiveau.setFont(new Font("Arial", Font.BOLD, 12));
		panelNiveau.add(lblNiveau, BorderLayout.SOUTH);
		panelTop.add(panelNiveauBorder);
		
		/***********************************************/
		
		///// BULLE INFO NIVEAU \\\\\
		JPanel panelCenter = new JPanel();
		panelCenter.setBorder(new EmptyBorder(15, 100, 15, 100));
		panelCenter.setBackground(Palette.DARK_GRAY);
		panelMain.add(panelCenter, BorderLayout.CENTER);
		
		GridBagLayout gb_panelCenter = new GridBagLayout();
		gb_panelCenter.columnWidths  = new int[]   {774, 0};
		gb_panelCenter.rowHeights    = new int[] {0, 50};
		gb_panelCenter.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gb_panelCenter.rowWeights    = new double[]{1.0, 0.0};
		panelCenter.setLayout(gb_panelCenter);
		
		JPanel panelTableEquipes = new JPanel();
		panelTableEquipes.setBorder(new EmptyBorder(50, 20, 50, 20));
		panelTableEquipes.setBackground(Palette.GRAY);
		GridBagConstraints gbc_panelTableEquipes = new GridBagConstraints();
		gbc_panelTableEquipes.fill = GridBagConstraints.BOTH;
		gbc_panelTableEquipes.insets = new Insets(0, 0, 5, 0);
		gbc_panelTableEquipes.gridx = 0;
		gbc_panelTableEquipes.gridy = 0;
		panelCenter.add(panelTableEquipes, gbc_panelTableEquipes);
		panelTableEquipes.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPaneTableEquipes = new JScrollPane();
		panelTableEquipes.add(scrollPaneTableEquipes, BorderLayout.CENTER);
		
		tableEquipes = new JTable();
		tableEquipes.setFont(new Font("Arial", Font.PLAIN, 14));
		tableEquipes.setBackground(new Color(255, 0, 0));
		tableEquipes.getTableHeader().setBackground(Palette.DARK_GRAY);
		tableEquipes.getTableHeader().setForeground(Palette.WHITE);
		tableEquipes.setBackground(Palette.GRAY);
		tableEquipes.setForeground(Palette.WHITE);
		scrollPaneTableEquipes.setViewportView(tableEquipes);
		DefaultTableModel modele = new DefaultTableModel(new Object[][] {},
	            new String[] { "Nom", "Joueur 1", "Joueur 2", "Joueur 3", "Joueur 4", "Joueur 5" }) {
	                
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
		
		GridBagConstraints gbc_panelBoutons_1 = new GridBagConstraints();
		gbc_panelBoutons_1.anchor = GridBagConstraints.NORTH;
		gbc_panelBoutons_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelBoutons_1.gridx = 0;
		gbc_panelBoutons_1.gridy = 1;
		panelCenter.add(panelBoutons, gbc_panelBoutons_1);
		
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
