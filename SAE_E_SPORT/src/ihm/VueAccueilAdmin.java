package ihm;

import java.awt.EventQueue;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.border.LineBorder;
import controleur.ControleurAccueil;
import java.awt.Color;
import javax.swing.JButton;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.FlowLayout;

public class VueAccueilAdmin extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VueAccueilAdmin frame = new VueAccueilAdmin();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	public VueAccueilAdmin() throws IOException {
		
		ControleurAccueil controleur = new ControleurAccueil(this);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 791, 502);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panelNorth = new JPanel();
		panelNorth.setBackground(new Color(255, 255, 255));
		contentPane.add(panelNorth, BorderLayout.NORTH);
		panelNorth.setLayout(new GridLayout(2, 0, 0, 0));
		
		JPanel panelTitre = new JPanel();
		panelTitre.setBackground(new Color(255, 255, 255));
		panelNorth.add(panelTitre);
		
		JLabel labelTitre = new JLabel("Accueil");
		labelTitre.setFont(new Font("Tahoma", Font.PLAIN, 31));
		panelTitre.add(labelTitre);
		
		JPanel panelBjr = new JPanel();
		panelBjr.setBackground(new Color(255, 255, 255));
		panelNorth.add(panelBjr);
		
		JLabel labelBjr = new JLabel("Bonjour admin");
		labelBjr.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelBjr.add(labelBjr);
		
		JPanel panelCenter = new JPanel();
		panelCenter.setBackground(new Color(255, 255, 255));
		panelCenter.setBorder(new EmptyBorder(5, 50, 50, 50));
		contentPane.add(panelCenter, BorderLayout.CENTER);
		panelCenter.setLayout(new BorderLayout(0, 0));
		
		JPanel panelCadre = new JPanel();
		panelCadre.setBackground(new Color(255, 255, 255));
		panelCadre.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelCenter.add(panelCadre);
		panelCadre.setLayout(new GridLayout(0, 3, 0, 0));
		
		JPanel panelEquipe = new JPanel();
		panelEquipe.setBackground(new Color(255, 255, 255));
		panelCadre.add(panelEquipe);
		panelEquipe.setLayout(new BorderLayout(0, 0));
		
		JPanel panelCadreEquipe = new JPanel();
		panelCadreEquipe.setBackground(new Color(255, 255, 255));
		FlowLayout flowLayout = (FlowLayout) panelCadreEquipe.getLayout();
		flowLayout.setHgap(0);
		panelCadreEquipe.setBorder(new EmptyBorder(50, 50, 50, 50));
		panelEquipe.add(panelCadreEquipe, BorderLayout.CENTER);
		
		JLabel iconEquipe = new JLabel("");
		iconEquipe.setFont(new Font("Tahoma", Font.PLAIN, 6));
		BufferedImage bufferedImage = ImageIO.read(VueAccueilAdmin.class.getResource("/Images/imgEquipe.png"));
		Image image = bufferedImage.getScaledInstance(128, 128, Image.SCALE_DEFAULT);
		iconEquipe.setIcon(new ImageIcon(image));
		iconEquipe.setSize(20, 80);
		panelCadreEquipe.add(iconEquipe);
		
		JButton btnEquipe = new JButton("Liste équipes");
		btnEquipe.setBackground(Color.WHITE);
		panelCadreEquipe.add(btnEquipe);
		btnEquipe.setFocusable(false);
		btnEquipe.addActionListener(controleur);
		
		JPanel panelTournoi = new JPanel();
		panelTournoi.setBackground(new Color(255, 255, 255));
		panelCadre.add(panelTournoi);
		panelTournoi.setLayout(new BorderLayout(0, 0));
		
		JPanel panelCadreTournoi = new JPanel();
		panelCadreTournoi.setBackground(new Color(255, 255, 255));
		panelCadreTournoi.setBorder(new EmptyBorder(50, 50, 50, 50));
		panelTournoi.add(panelCadreTournoi);
		
		JLabel iconTournoi = new JLabel("");
		iconTournoi.setFont(new Font("Tahoma", Font.PLAIN, 6));
		BufferedImage bufferedImage2 = ImageIO.read(VueAccueilAdmin.class.getResource("/Images/imgTournoi.png"));
		Image image2 = bufferedImage2.getScaledInstance(128, 128, Image.SCALE_DEFAULT);
		iconTournoi.setIcon(new ImageIcon(image2));
		iconTournoi.setSize(20, 80);
		panelCadreTournoi.add(iconTournoi);
		
		JButton btnTournoi = new JButton("Liste Tournois");
		btnTournoi.setBackground(Color.WHITE);
		panelCadreTournoi.add(btnTournoi);
		btnTournoi.setFocusable(false);
		btnTournoi.addActionListener(controleur);
		
		
		
		JPanel panelHistorique = new JPanel();
		panelHistorique.setBackground(new Color(255, 255, 255));
		panelCadre.add(panelHistorique);
		panelHistorique.setLayout(new BorderLayout(0, 0));
		
		JPanel panelCadreHistorique = new JPanel();
		panelCadreHistorique.setBackground(new Color(255, 255, 255));
		panelCadreHistorique.setBorder(new EmptyBorder(50, 50, 50, 50));
		panelHistorique.add(panelCadreHistorique, BorderLayout.NORTH);
		
		JLabel iconHistorique = new JLabel("");
		iconHistorique.setFont(new Font("Tahoma", Font.PLAIN, 6));
		iconHistorique.setFont(new Font("Tahoma", Font.PLAIN, 6));
		BufferedImage bufferedImage3 = ImageIO.read(VueAccueilAdmin.class.getResource("/Images/imgHistorique.png"));
		Image image3 = bufferedImage3.getScaledInstance(128, 128, Image.SCALE_DEFAULT);
		iconHistorique.setIcon(new ImageIcon(image3));
		iconHistorique.setSize(128, 128);
		panelCadreHistorique.add(iconHistorique);
		
		JButton btnHistorique = new JButton("Historique");
		btnHistorique.setBackground(Color.WHITE);
		panelCadreHistorique.add(btnHistorique);
		btnHistorique.setFocusable(false);
	}

}
