package ihm;

import java.awt.EventQueue;

import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

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
		
		JPanel panelInfosTournoi = new JPanel();
		panelInfosTournoi.setBorder(new EmptyBorder(10, 50, 10, 50));
		panelInfosTournoi.setBackground(Palette.GRAY);
		panelMain.add(panelInfosTournoi, BorderLayout.NORTH);
		panelInfosTournoi.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
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
		panelInfosTournoi.add(panelPaysBorder);

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
		panelInfosTournoi.add(panelNiveauBorder);
		
		/***********************************************/
		
		///// BULLE INFO NIVEAU \\\\\
		JPanel panelEquipes = new JPanel();
		panelMain.add(panelEquipes, BorderLayout.CENTER);
		panelEquipes.setLayout(new BorderLayout(0, 0));
		
		JPanel panelTableEquipes = new JPanel();
		panelTableEquipes.setBorder(new EmptyBorder(50, 20, 50, 20));
		panelTableEquipes.setBackground(Palette.GRAY);
		panelEquipes.add(panelTableEquipes, BorderLayout.CENTER);
		panelTableEquipes.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPaneTableEquipes = new JScrollPane();
		panelTableEquipes.add(scrollPaneTableEquipes, BorderLayout.CENTER);
		
		tableEquipes = new JTable();
		scrollPaneTableEquipes.setViewportView(tableEquipes);
		DefaultTableModel modele = new DefaultTableModel(new Object[][] {},
	            new String[] { "Nom", "Joueur 1", "Joueur 2", "Joueur 3", "Joueur 4", "Joueur 5" }) {
	                
				@Override
			    public boolean isCellEditable(int row, int column) {
			       return false;
			    }
			};
		tableEquipes.setModel(modele);
		
		JPanel panelBoutons = new JPanel();
		panelTableEquipes.add(panelBoutons, BorderLayout.SOUTH);
		
		JButton btnImporter = new JButton("Importer des équipes");
		btnImporter.addActionListener(controleur);
		panelBoutons.add(btnImporter);
		try {
			afficherEquipes(tournoi);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
