package ihm;

import java.awt.EventQueue;


import java.util.*;
import java.util.stream.Collectors;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

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
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.BoxLayout;
import javax.swing.Icon;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.FlowLayout;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.ImageIcon;

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
		
		

		///// PANEL PRINCIPAL  \\\\\
		JPanel contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
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
		lblTitre.setFont(new Font("DejaVu Sans", Font.BOLD | Font.ITALIC, 40));
		panelTop.add(lblTitre, BorderLayout.CENTER);
		
		// Ligne colorée séparatrice
		JTextField ligneColoree = new JTextField();
		ligneColoree.setBackground(Palette.WARDEN);
		ligneColoree.setEnabled(false);
		ligneColoree.setEditable(false);
		ligneColoree.setFont(new Font("Tahoma", Font.PLAIN, 5));
		panelTop.add(ligneColoree, BorderLayout.SOUTH);
		
		
		
		/// PANEL MAIN \\\
		JPanel panelMain = new JPanel();
		panelMain.setBackground(Color.WHITE);
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
				.map(e -> String.format("%-70s %6d", e.getNom(), e.getRang()))
				.collect(Collectors.toList());
		
		JList listeEquipes = new JList<Object>(nomEquipes.toArray());
		listeEquipes.setFont(new Font("Consolas", Font.PLAIN, 20));
		listeEquipes.setBackground(Palette.COOL);
		this.listeEquipes = listeEquipes;
		listeEquipes.addMouseListener(controleur);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(listeEquipes);
		panelMain.add(scrollPane, BorderLayout.CENTER);
	}
	
	public String getSearch() {
		return searchBar.getText();
	}
	
	public void updateListeEquipes(List<String> elementsFiltres) {
	    this.listeEquipes.setListData(elementsFiltres.toArray(new String[0]));
	    this.listeEquipes.repaint();
	}

}
