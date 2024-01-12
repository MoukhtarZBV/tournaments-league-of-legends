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
import components.TableEquipes;
import controleur.ControleurFinale;
import modele.Equipe;
import modele.Joueur;
import modele.Partie;
import modele.Statut;
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
import java.awt.Dimension;

public class VueFinale extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	private JLabel lblTropheeEquipeUne;
	private JLabel lblTropheeEquipeDeux;
	private JButton btnConfirmer;
	private JLabel lblNewLabel;
	
	private Tournoi tournoi;
	
	private Equipe equipeUne;
	private Equipe equipeDeux;
	private Equipe equipeVainqueur;


	public VueFinale(Tournoi tournoi) {
		
		this.tournoi = tournoi;
		setEquipes();
		ControleurFinale controleur = new ControleurFinale(this);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(Ecran.posX, Ecran.posY, Ecran.tailleX, Ecran.tailleY);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panelTitreFinale = new JPanel();
		panelTitreFinale.setPreferredSize(new Dimension(10, 120));
		panelTitreFinale.setBorder(new EmptyBorder(40, 0, 15, 0));
		panelTitreFinale.setBackground(Palette.DARK_GRAY);
		contentPane.add(panelTitreFinale, BorderLayout.NORTH);
		
		JLabel lblFinale = new JLabel("Finale - ");
		lblFinale.setFont(Police.GROS_TITRE);
		lblFinale.setForeground(Palette.WHITE);
		panelTitreFinale.add(lblFinale);
		
		JLabel lblNomTournoi = new JLabel(tournoi.getNomTournoi());
		lblNomTournoi.setFont(Police.GROS_TITRE);
		lblNomTournoi.setForeground(Palette.WHITE);
		panelTitreFinale.add(lblNomTournoi);
		
		JPanel panelScoresEquipes = new JPanel();
		contentPane.add(panelScoresEquipes, BorderLayout.CENTER);
		panelScoresEquipes.setLayout(new BorderLayout(0, 0));
		
		JPanel panelScores = new JPanel();
		panelScores.setBackground(Palette.DARK_GRAY);
		panelScores.setBorder(new EmptyBorder(10, 20, 10, 20));
		panelScoresEquipes.add(panelScores, BorderLayout.NORTH);
		panelScores.setLayout(new GridLayout(0, 3, 0, 0));
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Palette.GRAY);
		panel_2.setBorder(new CompoundBorder(new MatteBorder(0, 2, 0, 0, Palette.EQUIPE_ROUGE), new EmptyBorder(10, 30, 10, 20)));
		panelScores.add(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNomEquipeUne = new JLabel(equipeUne.getNom());
		lblNomEquipeUne.setBorder(new EmptyBorder(0, 10, 0, 0));
		lblNomEquipeUne.setForeground(new Color(255, 255, 255));
		lblNomEquipeUne.setFont(new Font("DejaVu Sans", Font.BOLD, 20));
		panel_2.add(lblNomEquipeUne, BorderLayout.EAST);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new EmptyBorder(15, 20, 15, 20));
		panel_3.setBackground(Palette.GRAY);
		panelScores.add(panel_3);
		panel_3.setLayout(new BorderLayout(15, 0));
		
		lblNewLabel = new JLabel();
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setOpaque(true);
		lblNewLabel.setBackground(Palette.DARK_GRAY);
		lblNewLabel.setBorder(new LineBorder(new Color(238, 238, 238)));
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("DejaVu Sans", Font.BOLD, 16));
		setLabelStatus();
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
		panel_4.setBackground(Palette.GRAY);
		panel_4.setBorder(new CompoundBorder(new MatteBorder(0, 0, 0, 2, Palette.EQUIPE_BLEU), new EmptyBorder(10, 20, 10, 30)));
		panelScores.add(panel_4);
		panel_4.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel_1 = new JLabel(equipeDeux.getNom());
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_1.setFont(new Font("DejaVu Sans", Font.BOLD, 20));
		panel_4.add(lblNewLabel_1, BorderLayout.WEST);
		
		JPanel panelEquipes = new JPanel();
		panelEquipes.setBorder(new EmptyBorder(10, 20, 20, 20));
		panelEquipes.setBackground(Palette.DARK_GRAY);
		panelScoresEquipes.add(panelEquipes, BorderLayout.CENTER);
			
		TableEquipes tableEquipeUne = new TableEquipes(true);
		tableEquipeUne.setBorder(new CompoundBorder(new MatteBorder(2, 0, 0, 0, Palette.EQUIPE_ROUGE), new EmptyBorder(10, 20, 20, 10)));
		tableEquipeUne.ajouterJoueurs(equipeUne.getJoueurs());
		
		TableEquipes tableEquipeDeux = new TableEquipes(false);
		tableEquipeDeux.setBorder(new CompoundBorder(new MatteBorder(2, 0, 0, 0, Palette.EQUIPE_BLEU), new EmptyBorder(10, 20, 20, 10)));
		tableEquipeDeux.ajouterJoueurs(equipeDeux.getJoueurs());
		
		
		panelEquipes.setLayout(new GridLayout(0, 2, 20, 0));
		panelEquipes.add(tableEquipeUne);
		panelEquipes.add(tableEquipeDeux);
		
		JPanel panelBoutons = new JPanel();
		panelBoutons.setBorder(new EmptyBorder(20, 20, 20, 20));
		FlowLayout flowLayout = (FlowLayout) panelBoutons.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		panelBoutons.setBackground(Palette.DARK_GRAY);
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
		
		if (tournoi.getVainqueur() == null) {
			System.out.println("Null");
			btnConfirmer.setVisible(true);
			lblTropheeEquipeUne.addMouseListener(controleur);
			lblTropheeEquipeDeux.addMouseListener(controleur);
		} else {
			afficherTropheeVainqueur();
		}
	}

	public void afficherTropheeVainqueur() {
		if (tournoi.getVainqueur().equals(equipeUne)) {
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
	
	public void setVisibleConfirmer(boolean visible) {
		this.btnConfirmer.setVisible(visible);
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
	
	public void setLabelStatus() {
		String status = tournoi.getVainqueur() == null ? "En cours" : "Terminée";
		lblNewLabel.setText("<html><body style='padding: 5px 5px;'>" + status + "</body></html>");
	}
	
	public void setEquipes() {
		Partie partieBDD = new Partie();
		List<Equipe> equipes = partieBDD.getEquipesPartie(partieBDD.getFinaleTournoi(tournoi));
		equipeUne = equipes.get(0);
		equipeDeux = equipes.get(1);
	}
}
