package ihm;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.border.MatteBorder;
import Images.ImagesIcons;
import components.TableEquipes;
import controleur.ControleurFinale;
import modele.Compte;
import modele.Equipe;
import modele.Partie;
import modele.Tournoi;
import modele.TypeCompte;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.border.LineBorder;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Dimension;

public class VueFinale extends JFrame {

	private static final long serialVersionUID = 1L;
	
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
		
		///// FENÊTRE \\\\\
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(Ecran.posX, Ecran.posY, Ecran.tailleX, Ecran.tailleY);
		addWindowListener(controleur);
		setResizable(false);
		setUndecorated(true);
				
		
		///// MAIN PANEL \\\\\
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.setBackground(Palette.GRAY);
		setContentPane(contentPane);

		///// HEADER \\\\\
		Header header = new Header(this);
		header.setTitre("Finale " + this.tournoi.getNomTournoi());
		contentPane.add(header, BorderLayout.NORTH);
		
		
		///// MENU BAR \\\\\
		afficherMenuBar(contentPane);
		
		///// MAIN \\\\\
		JPanel panelMain = new JPanel();
		panelMain.setBorder(new EmptyBorder(25, 50, 25, 50));
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
		JLabel lblTitre = new JLabel("Finale " + this.tournoi.getNomTournoi());
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
		
		
		JPanel panelScoresEquipes = new JPanel();
		panelScoresEquipes.setLayout(new BorderLayout(0, 0));
		panelMain.add(panelScoresEquipes, BorderLayout.CENTER);
		
		JPanel panelScores = new JPanel();
		panelScores.setBackground(Palette.DARK_GRAY);
		panelScores.setBorder(new EmptyBorder(10, 20, 10, 20));
		panelScores.setLayout(new GridLayout(0, 3, 0, 0));
		panelScoresEquipes.add(panelScores, BorderLayout.NORTH);
		
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
		btnRetour.addMouseListener(controleur);
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
		btnConfirmer.addMouseListener(controleur);
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

	private void afficherMenuBar(JPanel contentPane) {
		if(Compte.getCompteConnecte().getType() == TypeCompte.ADMINISTRATEUR) {
			MenuBar panelSide = new MenuBar(this);
			contentPane.add(panelSide, BorderLayout.WEST);
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
