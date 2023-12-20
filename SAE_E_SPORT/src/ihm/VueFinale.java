package ihm;

import java.awt.EventQueue;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.List;

import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;

import Images.ImagesIcons;
import components.EquipesTable;
import controleur.ControleurFinale;
import dao.TournoiJDBC;
import modele.Equipe;
import modele.Joueur;
import modele.Tournoi;

import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.border.LineBorder;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class VueFinale extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	private JLabel lblTropheeEquipeUne;
	private JLabel lblTropheeEquipeDeux;
	private JButton btnConfirmer;
	
	private Tournoi tournoi;
	
	private Equipe equipeUne;
	private Equipe equipeDeux;
	private Equipe equipeVainqueur;

	/**
	 * Create the frame.
	 */
	public VueFinale(Tournoi tournoi) {
		
		this.tournoi = tournoi;
		System.out.println(tournoi);
		setEquipes();
		ControleurFinale controleur = new ControleurFinale(this);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 560);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panelTitreFinale = new JPanel();
		panelTitreFinale.setBackground(Color.decode("#11022B"));
		contentPane.add(panelTitreFinale, BorderLayout.NORTH);
		
		JLabel lblFinale = new JLabel("FINALE");
		lblFinale.setFont(Police.GROS_TITRE);
		lblFinale.setForeground(Palette.WHITE);
		panelTitreFinale.add(lblFinale);
		
		JLabel lblNomTournoi = new JLabel("TAD 2023");
		lblNomTournoi.setFont(Police.GROS_TITRE);
		lblNomTournoi.setForeground(Palette.WHITE);
		panelTitreFinale.add(lblNomTournoi);
		
		JPanel panelScoresEquipes = new JPanel();
		contentPane.add(panelScoresEquipes, BorderLayout.CENTER);
		panelScoresEquipes.setLayout(new BorderLayout(0, 0));
		
		JPanel panelScores = new JPanel();
		panelScores.setBackground(Color.decode("#11022B"));
		panelScores.setBorder(new EmptyBorder(10, 20, 10, 20));
		panelScoresEquipes.add(panelScores, BorderLayout.NORTH);
		panelScores.setLayout(new GridLayout(0, 3, 0, 0));
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.decode("#261C3F"));
		panel_2.setBorder(new CompoundBorder(new MatteBorder(0, 2, 0, 0, (Color) new Color(160, 0, 0)), new EmptyBorder(10, 30, 10, 20)));
		panelScores.add(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNomEquipeUne = new JLabel(equipeUne.getNom());
		lblNomEquipeUne.setBorder(new EmptyBorder(0, 10, 0, 0));
		lblNomEquipeUne.setForeground(new Color(255, 255, 255));
		lblNomEquipeUne.setFont(new Font("DejaVu Sans", Font.BOLD, 20));
		panel_2.add(lblNomEquipeUne, BorderLayout.EAST);
		
		JLabel lblLogoEquipeUne = new JLabel();
		lblLogoEquipeUne.setIcon(ImagesIcons.FRANCE);
		panel_2.add(lblLogoEquipeUne, BorderLayout.WEST);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new EmptyBorder(15, 20, 15, 20));
		panel_3.setBackground(Color.decode("#261C3F"));
		panelScores.add(panel_3);
		panel_3.setLayout(new BorderLayout(15, 0));
		
		
		String status = tournoi.getVainqueur() == null ? "En cours" : "Terminée";
		JLabel lblNewLabel = new JLabel("<html><body style='padding: 5px 5px;'>" + status + "</body></html>");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setOpaque(true);
		lblNewLabel.setBackground(new Color(30, 22, 50));
		lblNewLabel.setBorder(new LineBorder(new Color(238, 238, 238)));
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("DejaVu Sans", Font.BOLD, 16));
		panel_3.add(lblNewLabel);
		
		lblTropheeEquipeUne = new JLabel();
		lblTropheeEquipeUne.setIcon(new ImageIcon(ImagesIcons.class.getResource(ImagesIcons.TROPHY)));
		lblTropheeEquipeUne.setName("Trophee equipe une");
		panel_3.add(lblTropheeEquipeUne, BorderLayout.WEST);
		
		lblTropheeEquipeDeux = new JLabel();
		lblTropheeEquipeDeux.setIcon(new ImageIcon(ImagesIcons.class.getResource(ImagesIcons.TROPHY)));
		lblTropheeEquipeDeux.setName("Trophee equipe deux");
		panel_3.add(lblTropheeEquipeDeux, BorderLayout.EAST);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(Color.decode("#261C3F"));
		panel_4.setBorder(new CompoundBorder(new MatteBorder(0, 0, 0, 2, (Color) new Color(0, 0, 160)), new EmptyBorder(10, 20, 10, 30)));
		panelScores.add(panel_4);
		panel_4.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel_1 = new JLabel(equipeDeux.getNom());
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_1.setFont(new Font("DejaVu Sans", Font.BOLD, 20));
		panel_4.add(lblNewLabel_1, BorderLayout.WEST);
		
		JLabel lblLogoEquipeDeux = new JLabel();
		lblLogoEquipeDeux.setIcon(ImagesIcons.UKRAINE);
		panel_4.add(lblLogoEquipeDeux, BorderLayout.EAST);
		
		JPanel panelEquipes = new JPanel();
		panelEquipes.setBorder(new EmptyBorder(10, 20, 20, 20));
		panelEquipes.setBackground(Color.decode("#11022B"));
		panelScoresEquipes.add(panelEquipes, BorderLayout.CENTER);
		

		DefaultTableModel modeleTableEquipeUne = new DefaultTableModel(new Object[][] {},
	            new String[] { "Équipe", "Joueur 1"}) {
	                
				@Override
			    public boolean isCellEditable(int row, int column) {
			       return false;
			    }
		};
		
		EquipesTable tableEquipeUne = new EquipesTable(5, 3);
		tableEquipeUne.setBorder(new CompoundBorder(new MatteBorder(2, 0, 0, 0, new Color(160, 0, 0)), new EmptyBorder(10, 20, 20, 10)));
		tableEquipeUne.setFont(Police.TABLEAU);
		tableEquipeUne.getTableHeader().setBackground(Palette.DARK_GRAY);
		tableEquipeUne.getTableHeader().setForeground(Palette.WHITE);
		tableEquipeUne.getTableHeader().setFont(Police.LABEL);
		tableEquipeUne.setBackground(Palette.DARK_GRAY);
		tableEquipeUne.setForeground(Palette.WHITE);
		tableEquipeUne.setModel(modeleTableEquipeUne);
		afficherEquipes(tableEquipeUne, equipeUne);
		
		DefaultTableModel modeleTableEquipeDeux = new DefaultTableModel(new Object[][] {},
	            new String[] { "Équipe", "Joueur 1"}) {
	                
				@Override
			    public boolean isCellEditable(int row, int column) {
			       return false;
			    }
		};

		EquipesTable tableEquipeDeux = new EquipesTable(5, 3);
		tableEquipeDeux.setBorder(new CompoundBorder(new MatteBorder(2, 0, 0, 0, new Color(0, 0, 160)), new EmptyBorder(10, 20, 20, 10)));
		tableEquipeDeux.setFont(Police.TABLEAU);
		tableEquipeDeux.getTableHeader().setBackground(Palette.DARK_GRAY);
		tableEquipeDeux.getTableHeader().setForeground(Palette.WHITE);
		tableEquipeDeux.getTableHeader().setFont(Police.LABEL);
		tableEquipeDeux.setBackground(Palette.DARK_GRAY);
		tableEquipeDeux.setForeground(Palette.WHITE);
		tableEquipeDeux.setModel(modeleTableEquipeDeux);
		afficherEquipes(tableEquipeDeux, equipeDeux);
		
		
		panelEquipes.setLayout(new GridLayout(0, 2, 20, 0));
		panelEquipes.add(tableEquipeUne);
		panelEquipes.add(tableEquipeDeux);
		
		JPanel panelBoutons = new JPanel();
		panelBoutons.setBorder(new EmptyBorder(20, 20, 20, 20));
		FlowLayout flowLayout = (FlowLayout) panelBoutons.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		panelBoutons.setBackground(Color.decode("#11022B"));
		panelScoresEquipes.add(panelBoutons, BorderLayout.SOUTH);
		
		JButton btnRetour = new JButton("<html><body style='padding: 5px 20px;'>Retour</body></html>");
		btnRetour.setName("Retour");
		btnRetour.setBackground(Palette.GRAY);
		btnRetour.setForeground(Palette.WHITE);
		btnRetour.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Palette.WHITE));
		btnRetour.setFont(Police.LABEL);
		btnRetour.setFocusable(false);
		btnRetour.addActionListener(controleur);
		panelBoutons.add(btnRetour);

		btnConfirmer = new JButton("<html><body style='padding: 5px 20px;'>Confirmer</body></html>");
		btnConfirmer.setName("Confirmer");
		btnConfirmer.setBackground(Palette.GRAY);
		btnConfirmer.setForeground(Palette.WHITE);
		btnConfirmer.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Palette.WHITE));
		btnConfirmer.setFont(Police.LABEL);
		btnConfirmer.setFocusable(false);
		btnConfirmer.setEnabled(false);
		btnConfirmer.setVisible(false);
		btnConfirmer.addActionListener(controleur);
		panelBoutons.add(btnConfirmer);
		
		afficherBoutonsTrophees(tournoi, controleur);
		
	}
	
	private void afficherEquipes(JTable table, Equipe equipe) {
		DefaultTableModel modele = (DefaultTableModel) table.getModel();
		List<Joueur> joueursEquipe = equipe.getJoueurs();
		for (int i = 0; i < joueursEquipe.size(); i++) {
			modele.addRow(new Object[] {
				joueursEquipe.get(i).getPseudo(), "Role"
			});
		}
	}

	private void afficherBoutonsTrophees(Tournoi tournoi, ControleurFinale controleur) {
		if (tournoi.getVainqueur() == null) {
			btnConfirmer.setVisible(true);
			lblTropheeEquipeUne.addMouseListener(controleur);
			lblTropheeEquipeDeux.addMouseListener(controleur);
		} else if (tournoi.getVainqueur().equals(equipeUne)) {
			setIconesTrophees(new ImageIcon(ImagesIcons.class.getResource(ImagesIcons.TROPHY_WIN)), null);
		} else {
			setIconesTrophees(null, new ImageIcon(ImagesIcons.class.getResource(ImagesIcons.TROPHY_WIN)));
		}
	}
	
	public Tournoi getTournoi() {
		return this.tournoi;
	}
	
	public void setActifConfirmer(boolean actif) {
		this.btnConfirmer.setEnabled(actif);
	}

	public void setVainqueur(String equipeGagnante) {
		if (equipeGagnante.equals(lblTropheeEquipeUne.getName())) {
			setIconesTrophees(new ImageIcon(ImagesIcons.class.getResource(ImagesIcons.TROPHY_WIN)), new ImageIcon(ImagesIcons.class.getResource(ImagesIcons.TROPHY)));
			equipeVainqueur = equipeUne;
		} else {
			setIconesTrophees(new ImageIcon(ImagesIcons.class.getResource(ImagesIcons.TROPHY)), new ImageIcon(ImagesIcons.class.getResource(ImagesIcons.TROPHY_WIN)));
			equipeVainqueur = equipeDeux;
		}
	}

	private void setIconesTrophees(ImageIcon iconeTropheeEquipeUn, ImageIcon iconeTropheeEquipeDeux) {
		lblTropheeEquipeUne.setIcon(iconeTropheeEquipeUn);
		lblTropheeEquipeDeux.setIcon(iconeTropheeEquipeDeux);
	}
	
	public Equipe getVainqueur() {
		return equipeVainqueur;
	}
	
	public void setEquipes() {
		List<Equipe> equipes = new Tournoi().getEquipesFinale(tournoi);
		equipeUne = equipes.get(0);
		equipeDeux = equipes.get(1);
	}
}
