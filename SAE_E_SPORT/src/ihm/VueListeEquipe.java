package ihm;

import java.awt.EventQueue;


import java.util.*;
import java.util.stream.Collectors;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import controleur.ControleurListeEquipe;
import dao.ConnectionJDBC;
import dao.EquipeJDBC;
import modele.Equipe;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JList;
import javax.swing.JLabel;
import java.sql.Connection;
import javax.swing.JScrollPane;
import java.awt.Image;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import java.awt.Color;

public class VueListeEquipe extends JFrame {

	private List<Equipe> equipes;
	private JTextField searchBar;
	private JList<Object> listeEquipes;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Connection c = ConnectionJDBC.getConnection();
					List<Equipe> equipes = (new EquipeJDBC().getAll());
					VueListeEquipe frame = new VueListeEquipe(equipes);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VueListeEquipe(List<Equipe> equipes) {
		
		ControleurListeEquipe controleur = new ControleurListeEquipe(this);
		
		///// FENÊTRE \\\\\
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(510, 240, 900, 600);
		setTitle("Équipes");
		setResizable(false);
		

		///// PANEL PRINCIPAL  \\\\\
		JPanel contentPane = new JPanel();
		contentPane.setBackground(Palette.WHITE);
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		
		
		///// PANEL TITRE \\\\\
		JPanel panelTop = new JPanel();
		panelTop.setBackground(Palette.COOL);
		panelTop.setBorder(new EmptyBorder(0, 0, 0, 0));
		panelTop.setLayout(new BorderLayout(0, 0));
		contentPane.add(panelTop, BorderLayout.NORTH);
		
		// Label titre
		JLabel lblTitre = new JLabel("Équipes");
		lblTitre.setForeground(Palette.WARDEN);
		lblTitre.setBorder(new EmptyBorder(20, 0, 20, 0));
		lblTitre.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitre.setFont(Police.GROS_TITRE);
		panelTop.add(lblTitre, BorderLayout.CENTER);
		
		// Ligne colorée séparatrice
		JTextField ligneColoree = new JTextField();
		ligneColoree.setBackground(Palette.WARDEN);
		ligneColoree.setEnabled(false);
		ligneColoree.setEditable(false);
		ligneColoree.setFont(Police.LIGNE);
		panelTop.add(ligneColoree, BorderLayout.SOUTH);
		
		
		
		/// PANEL MAIN \\\
		JPanel panelMain = new JPanel();
		panelMain.setBackground(Palette.WHITE);
		panelMain.setBorder(new EmptyBorder(15, 15, 15, 15));
		panelMain.setLayout(new BorderLayout(10, 10));
		contentPane.add(panelMain, BorderLayout.CENTER);
		
		
		/// PANEL RECHERCHE \\\
		JPanel panelSearch = new JPanel();
		panelSearch.setBorder(new EmptyBorder(5, 5, 5, 5));
		panelSearch.setBackground(Palette.COOL);
		panelSearch.setLayout(new BorderLayout(5, 0));
		panelMain.add(panelSearch, BorderLayout.NORTH);
		
		// Bouton valider
		JPanel panelValider = new JPanel();
		panelValider.setBackground(Palette.COOL);
		panelValider.setLayout(new BoxLayout(panelValider, BoxLayout.X_AXIS));
		panelSearch.add(panelValider, BorderLayout.EAST);
		
		ImageIcon icon = new ImageIcon(VueListeEquipe.class.getResource("/Images/Search_Icon.png"));
		Image img = icon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
		icon = new ImageIcon(img);
		
		JButton validateBtn = new JButton("");
		validateBtn.setIcon(icon);
		validateBtn.setBorder(new EmptyBorder(5, 5, 5, 5));
		validateBtn.setBackground(Palette.WHITE);
		validateBtn.addActionListener(controleur);
		validateBtn.addMouseListener(controleur);
		panelValider.add(validateBtn);

		// Barre recherche
		searchBar = new JTextField();
		searchBar.setPreferredSize(new Dimension(searchBar.getPreferredSize().width, 25));
		searchBar.setColumns(30);
		panelSearch.add(searchBar);
		
		// Liste des équipes
		List<String> nomEquipes = equipes.stream()
				.map(eq -> String.format("%-5d %-50s", eq.getRang(), eq.getNom()))
				.collect(Collectors.toList());
		
		JList<Object> listeEquipes = new JList<Object>(nomEquipes.toArray());
		listeEquipes.setFont(Police.TABLEAU);
		listeEquipes.setBackground(Palette.COOL);
		listeEquipes.addMouseListener(controleur);
		this.listeEquipes = listeEquipes;
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(listeEquipes);
		panelMain.add(scrollPane, BorderLayout.CENTER);
		
		JPanel panelBottom = new JPanel();
		panelBottom.setBackground(new Color(255, 255, 255));
		contentPane.add(panelBottom, BorderLayout.SOUTH);
		
		JButton btnRetour = new JButton("Retour");
		panelBottom.add(btnRetour);
		btnRetour.addActionListener(controleur);
	}
	
	public String getSearch() {
		return searchBar.getText();
	}
	
	public void updateListeEquipes(List<String> elementsFiltres) {
	    this.listeEquipes.setListData(elementsFiltres.toArray(new String[0]));
	    this.listeEquipes.repaint();
	}

}
