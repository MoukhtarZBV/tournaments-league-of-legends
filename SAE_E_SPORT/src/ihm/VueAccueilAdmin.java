package ihm;

import java.awt.EventQueue;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.Connection;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import Images.Images;
import controleur.ControleurAccueil;
import dao.ConnectionJDBC;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import java.awt.FlowLayout;

public class VueAccueilAdmin extends JFrame {

	
	public static void main(String[] args) {
		Ecran.setup();
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Connection c = ConnectionJDBC.getConnection();
					VueAccueilAdmin frame = new VueAccueilAdmin();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace(); 
				}
			}
		});
	}

	
	public VueAccueilAdmin() {
		
		ControleurAccueil controleur = new ControleurAccueil(this);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(Ecran.posX, Ecran.posY, Ecran.tailleX, Ecran.tailleY);
		setTitle("Accueil");
		
		
		///// PANEL PRINCIPAL \\\\\
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		
		///// PANEL TITRE \\\\\
		JPanel panelTop = new JPanel();
		panelTop.setPreferredSize(new Dimension(800, 120));
		panelTop.setBackground(Palette.DARK_GRAY);
		panelTop.setBorder(new EmptyBorder(15, 100, 0, 100));
		panelTop.setLayout(new GridLayout());
		contentPane.add(panelTop, BorderLayout.NORTH);
		
		// Label titre
		JLabel lblTitre = new JLabel("Accueil");
		lblTitre.setBorder(BorderFactory.createMatteBorder(0, 0, 5, 0, Palette.WHITE));
		lblTitre.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitre.setForeground(Palette.WHITE);
		lblTitre.setFont(Police.GROS_TITRE);
		panelTop.add(lblTitre);
		
		
		///// MAIN PANEL \\\\\
		JPanel panelMain = new JPanel();
		panelMain.setLayout(new BorderLayout(0, 0));
		contentPane.add(panelMain, BorderLayout.CENTER);
		
		
		///// PANEL BONJOUUUUR \\\\\
		JPanel panelBjr = new JPanel();
		panelBjr.setBorder(new EmptyBorder(10, 0, 10, 0));
		panelBjr.setBackground(Palette.DARK_GRAY);
		panelMain.add(panelBjr, BorderLayout.NORTH);

		// Label bonjour
		JLabel lblBjr = new JLabel("Bonjour admin");
		lblBjr.setHorizontalAlignment(SwingConstants.CENTER);
		lblBjr.setFont(Police.SOUS_TITRE);
		lblBjr.setForeground(Palette.WHITE);
		panelBjr.add(lblBjr);
		
		
		///// PANEL CENTER \\\\\
		JPanel panelCenter = new JPanel();
		panelCenter.setBackground(Palette.DARK_GRAY);
		panelCenter.setBorder(new EmptyBorder(5, 100, 50, 100));
		panelCenter.setLayout(new BorderLayout(0, 0));
		panelMain.add(panelCenter, BorderLayout.CENTER);
		
		
		///// PANEL CADRE PRINCIPAL \\\\\
		JPanel panelCadre = new JPanel();
		panelCadre.setBackground(Palette.DARK_GRAY);
		panelCadre.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Palette.GRAY));
		panelCadre.setLayout(new GridLayout(0, 3, 0, 0));
		panelCenter.add(panelCadre);
		
		
		///// PANEL EQUIPE \\\\\
		JPanel panelEquipe = new JPanel();
		panelEquipe.setName("Equipes");
		panelEquipe.setBorder(new EmptyBorder(50, 50, 50, 50));
		panelEquipe.addMouseListener(controleur);
		panelEquipe.setBackground(Palette.DARK_GRAY);
		panelEquipe.setLayout(new BorderLayout(0, 0));
		panelCadre.add(panelEquipe);
		
		// Logo Equipe
		JLabel iconEquipe = new JLabel();
		iconEquipe.setHorizontalAlignment(SwingConstants.CENTER);
		iconEquipe.setIcon(Images.EQUIPE);
		iconEquipe.setSize(20, 80);
		panelEquipe.add(iconEquipe, BorderLayout.CENTER);
			
		
		// Bouton équipe
		JLabel lblEquipe = new JLabel("Liste équipes");
		lblEquipe.setHorizontalAlignment(SwingConstants.CENTER);
		lblEquipe.setFont(Police.SOUS_TITRE);
		lblEquipe.setForeground(Palette.WHITE);
		panelEquipe.add(lblEquipe, BorderLayout.SOUTH);
		
		
		///// PANEL TOURNOIS \\\\\
		JPanel panelTournois = new JPanel();
		panelTournois.setName("Tournois");
		panelTournois.setBorder(new EmptyBorder(50, 50, 50, 50));
		panelTournois.addMouseListener(controleur);
		panelTournois.setBackground(Palette.DARK_GRAY);
		panelTournois.setLayout(new BorderLayout(0, 0));
		panelCadre.add(panelTournois);
		
		// Logo Tournois
//		try {
//			BufferedImage bufferedImageT;
//			bufferedImageT = ImageIO.read(VueAccueilAdmin.class.getResource("/Images/imgTournoi.png"));
//			Image imageT = bufferedImageT.getScaledInstance(128, 128, Image.SCALE_DEFAULT);
			
			JLabel iconTournois = new JLabel();
			iconTournois.setHorizontalAlignment(SwingConstants.CENTER);
//			iconTournois.setIcon(new ImageIcon(imageT));
			iconTournois.setIcon(Images.TOURNOI);
			iconTournois.setSize(20, 80);
			panelTournois.add(iconTournois, BorderLayout.CENTER);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
		// Bouton tournois
		JLabel lblTournois = new JLabel("Liste tournois");
		lblTournois.setHorizontalAlignment(SwingConstants.CENTER);
		lblTournois.setFont(Police.SOUS_TITRE);
		lblTournois.setForeground(Palette.WHITE);
		panelTournois.add(lblTournois, BorderLayout.SOUTH);
		
		
		///// PANEL HISTORIQUE \\\\\
		JPanel panelHistorique = new JPanel();
		panelHistorique.setName("Historique");
		panelHistorique.setBorder(new EmptyBorder(50, 50, 50, 50));
		panelHistorique.addMouseListener(controleur);
		panelHistorique.setBackground(Palette.DARK_GRAY);
		panelHistorique.setLayout(new BorderLayout(0, 0));
		panelCadre.add(panelHistorique);
		
		// Logo Tournois
//		try {
//			BufferedImage bufferedImageH;
//			bufferedImageH = ImageIO.read(VueAccueilAdmin.class.getResource("/Images/imgHistorique.png"));
//			Image imageH = bufferedImageH.getScaledInstance(128, 128, Image.SCALE_DEFAULT);
			
			JLabel iconHistorique = new JLabel();
			iconHistorique.setHorizontalAlignment(SwingConstants.CENTER);
//			iconHistorique.setIcon(new ImageIcon(imageH));
			iconHistorique.setIcon(Images.HISTORIQUE);
			iconHistorique.setSize(20, 80);
			panelHistorique.add(iconHistorique, BorderLayout.CENTER);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
		// Bouton tournois
		JLabel lblHistorique = new JLabel("Historique");
		lblHistorique.setHorizontalAlignment(SwingConstants.CENTER);
		lblHistorique.setForeground(Palette.WHITE);
		lblHistorique.setFont(Police.SOUS_TITRE);
		panelHistorique.add(lblHistorique, BorderLayout.SOUTH);
	}

}
