package ihm;

import java.awt.EventQueue;


import java.util.*;
import java.util.stream.Collectors;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import controleur.ControleurListeEquipe;
import dao.ConnectionJDBC;
import dao.EquipeJDBC;
import modele.Equipe;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JList;
import javax.swing.JScrollBar;
import javax.swing.JLabel;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JScrollPane;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;

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
					// TODO Auto-generated catch block
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
		setBounds(100, 100, 900, 600);
		setTitle("Liste des équipes");
		
		

		///// PANEL PRINCIPAL  \\\\\
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		
		
		///// PANEL TITRE \\\\\
		JPanel panelTop = new JPanel();
		panelTop.setBorder(new EmptyBorder(0, 0, 0, 0));
		panelTop.setLayout(new BorderLayout(0, 0));
		contentPane.add(panelTop, BorderLayout.NORTH);
		
		// Label titre
		JLabel lblTitre = new JLabel("Nouveau tournoi");
		lblTitre.setBorder(new EmptyBorder(20, 0, 20, 0));
		lblTitre.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitre.setFont(new Font("DejaVu Sans", Font.PLAIN, 40));
		panelTop.add(lblTitre, BorderLayout.CENTER);
		
		// Ligne colorée séparatrice
		JTextField ligneColoree = new JTextField();
		ligneColoree.setBackground(new Color(25, 25, 112));
		ligneColoree.setEnabled(false);
		ligneColoree.setEditable(false);
		ligneColoree.setFont(new Font("Tahoma", Font.PLAIN, 5));
		panelTop.add(ligneColoree, BorderLayout.SOUTH);
		
		
		
		/// PANEL MAIN \\\
		
		
		// Liste des équipes
		List<String> nomEquipes = equipes.stream()
				.map(e -> e.getNom())
				.collect(Collectors.toList());
		
		JList listeEquipes = new JList<Object>(nomEquipes.toArray());
		this.listeEquipes = listeEquipes;
		listeEquipes.addMouseListener(controleur);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		scrollPane.setViewportView(listeEquipes);
		
		
		JLabel libFenetre = new JLabel("Liste des équipes");
		libFenetre.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panelTop.add(libFenetre);
		
		JPanel panelSearch = new JPanel();
		panelTop.add(panelSearch);
		
		searchBar = new JTextField();
		Dimension preferredSize = new Dimension(searchBar.getPreferredSize().width, 25);
        searchBar.setPreferredSize(preferredSize);
		panelSearch.add(searchBar);
		searchBar.setColumns(30);
		
		JButton validateBtn = new JButton("Valider");

        validateBtn.setBackground(Color.WHITE);
        validateBtn.setForeground(Color.BLACK);
        validateBtn.addActionListener(controleur);
        
        // Définition de la bordure
        
        // padding interieur
        Border paddingBorder = BorderFactory.createEmptyBorder(0, 0, 0, 0);
        // bordure exterieur
        Border originalBorder = validateBtn.getBorder();
        // Combinaison des bordures
        Border compoundBorder = BorderFactory.createCompoundBorder(originalBorder, paddingBorder);
        validateBtn.setBorder(compoundBorder);
        
        Font buttonFont = new Font("Arial", Font.PLAIN, 14);
        validateBtn.setFont(buttonFont);
		panelSearch.add(validateBtn);
	}
	
	public String getSearch() {
		return searchBar.getText();
	}
	
	public void updateListeEquipes(List<String> elementsFiltres) {
	    this.listeEquipes.setListData(elementsFiltres.toArray(new String[0]));
	    this.listeEquipes.repaint();
	}

}
