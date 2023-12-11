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
import controleur.ControleurDetailsTournoi;
import dao.ParticiperJDBC;
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
import javax.swing.Box;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;

public class VueTournoi extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTable table;
	
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

		///// PANEL TITRE \\\\\
		JPanel panelTop = new JPanel();
		panelTop.setBackground(Palette.COOL);
		panelTop.setBorder(new EmptyBorder(0, 0, 0, 0));
		panelTop.setLayout(new BorderLayout(0, 0));
		contentPane.add(panelTop, BorderLayout.NORTH);

		// Label titre
		JLabel lblTitre = new JLabel("Tournoi");
		lblTitre.setForeground(Palette.WARDEN);
		lblTitre.setBorder(new EmptyBorder(20, 0, 20, 0));
		lblTitre.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitre.setFont(new Font("DejaVu Sans", Font.BOLD | Font.ITALIC, 40));
		panelTop.add(lblTitre, BorderLayout.CENTER);

		// Ligne colorée séparatrice
		JTextField ligneColoree = new JTextField();
		ligneColoree.setBackground(Palette.WARDEN);
		ligneColoree.setEnabled(false);
		ligneColoree.setEditable(false);
		ligneColoree.setFont(new Font("Tahoma", Font.PLAIN, 5));
		panelTop.add(ligneColoree, BorderLayout.SOUTH);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panelInfosTournoi = new JPanel();
		panelInfosTournoi.setBackground(new Color(255, 255, 255));
		panel.add(panelInfosTournoi, BorderLayout.NORTH);
		panelInfosTournoi.setLayout(new GridLayout(2, 2, 0, 0));
		
		JPanel panelDates = new JPanel();
		panelDates.setBackground(new Color(3, 54, 49));
		panelInfosTournoi.add(panelDates);
		panelDates.setLayout(new GridLayout(2, 1, 0, -15));
		
		JLabel lblDates = new JLabel("Dates");
		lblDates.setBorder(null);
		lblDates.setForeground(new Color(227, 171, 40));
		lblDates.setFont(new Font("Dubai", Font.BOLD | Font.ITALIC, 22));
		panelDates.add(lblDates);
		
		JLabel lblDatesDebutFin = new JLabel("Du 01/01/2023 au 10/01/2023");
		lblDatesDebutFin.setFont(new Font("Arial", Font.BOLD, 16));
		lblDatesDebutFin.setForeground(new Color(255, 255, 255));
		panelDates.add(lblDatesDebutFin);
		
		JPanel panelDates_2 = new JPanel();
		panelDates_2.setBackground(new Color(3, 54, 49));
		panelInfosTournoi.add(panelDates_2);
		panelDates_2.setLayout(new GridLayout(2, 1, 0, -15));
		
		JLabel lblDates_2 = new JLabel("Dates");
		lblDates_2.setForeground(new Color(227, 171, 40));
		lblDates_2.setFont(new Font("Dubai", Font.BOLD | Font.ITALIC, 22));
		lblDates_2.setBorder(null);
		panelDates_2.add(lblDates_2);
		
		JLabel lblDatesDebutFin_2 = new JLabel("Du 01/01/2023 au 10/01/2023");
		lblDatesDebutFin_2.setForeground(Color.WHITE);
		lblDatesDebutFin_2.setFont(new Font("Arial", Font.BOLD, 16));
		panelDates_2.add(lblDatesDebutFin_2);
		
		JPanel panelNiveau = new JPanel();
		panelNiveau.setBackground(new Color(3, 54, 49));
		panelInfosTournoi.add(panelNiveau);
		panelNiveau.setLayout(new GridLayout(2, 1, 0, -15));
		
		JLabel lblNiveau = new JLabel("Niveau");
		lblNiveau.setForeground(new Color(227, 171, 40));
		lblNiveau.setFont(new Font("Dubai", Font.BOLD | Font.ITALIC, 22));
		lblNiveau.setBorder(null);
		panelNiveau.add(lblNiveau);
		
		JLabel lblRgional = new JLabel("Régional");
		lblRgional.setForeground(Color.WHITE);
		lblRgional.setFont(new Font("Arial", Font.BOLD, 16));
		panelNiveau.add(lblRgional);
		
		JPanel panelVainqueur = new JPanel();
		panelVainqueur.setBackground(new Color(3, 54, 49));
		panelInfosTournoi.add(panelVainqueur);
		panelVainqueur.setLayout(new GridLayout(2, 1, 0, -15));
		
		JLabel lblVainqueur = new JLabel("Vainqueur");
		lblVainqueur.setForeground(new Color(227, 171, 40));
		lblVainqueur.setFont(new Font("Dubai", Font.BOLD | Font.ITALIC, 22));
		lblVainqueur.setBorder(null);
		panelVainqueur.add(lblVainqueur);
		
		JLabel lblAucun = new JLabel("Aucun");
		lblAucun.setForeground(Color.WHITE);
		lblAucun.setFont(new Font("Arial", Font.BOLD, 16));
		panelVainqueur.add(lblAucun);
		
		JPanel panelEquipes = new JPanel();
		panel.add(panelEquipes, BorderLayout.CENTER);
		panelEquipes.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(3, 54, 49));
		panelEquipes.add(panel_1, BorderLayout.NORTH);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_2 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_2.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panel_2.setBackground(new Color(3, 54, 49));
		panel_1.add(panel_2, BorderLayout.WEST);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(new Color(3, 54, 49));
		panel_1.add(panel_4, BorderLayout.EAST);
		
		textField = new JTextFieldArrondi(5);
		textField.setCaretColor(new Color(3, 54, 49));
		textField.setBackground(new Color(227, 166, 64));
		textField.setFont(new Font("Dubai", Font.PLAIN, 0));
		textField.setBorder(null);
		panel_1.add(textField, BorderLayout.CENTER);
		textField.setColumns(10);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(new Color(3, 54, 49));
		panelEquipes.add(panel_3, BorderLayout.CENTER);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panel_3.add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		DefaultTableModel modele = new DefaultTableModel(new Object[][] {},
	            new String[] { "Nom", "Joueur 1", "Joueur 2", "Joueur 3", "Joueur 4", "Joueur 5" }) {
	                
				@Override
			    public boolean isCellEditable(int row, int column) {
			       return false;
			    }
			};
		table.setModel(modele);
		
		JPanel panel_5 = new JPanel();
		panel_3.add(panel_5, BorderLayout.SOUTH);
		
		JButton btnImporter = new JButton("Importer des équipes");
		btnImporter.addActionListener(controleur);
		panel_5.add(btnImporter);
		try {
			afficherEquipes(tournoi);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void afficherEquipes(Tournoi tournoi) throws Exception {
		DefaultTableModel modele = (DefaultTableModel) table.getModel();
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
