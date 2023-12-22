package ihm;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import components.CoolTextField;
import components.PanelPopUp;
import modele.Niveau;
import modele.Pays;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.JTextField;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.Date;
import java.time.LocalDate;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.JFormattedTextField;
import javax.swing.BoxLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JComboBox;
import javax.swing.JButton;

import controleur.ControleurAjouterArbitre;
import controleur.ControleurCreationTournoi;
import java.awt.FlowLayout;
import java.awt.Font;

public class VueAjouterArbitre extends JFrame {
	
	private 		 JTextField inputNom;
	
	private PanelPopUp panelPopup;
	private JTextField inputPrenom;

	
	// Création la fenêtre
	public VueAjouterArbitre() {
		
		ControleurAjouterArbitre controleur = new ControleurAjouterArbitre(this);
		Ecran.setup();
		
		///// FENÊTRE \\\\\
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(Ecran.posX, Ecran.posY, 822, 528);
		setTitle("Ajouter un arbitre");
		setResizable(false);
		
		
		
		///// PANEL PRINCIPAL \\\\\
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		
		
		///// MENU BAR \\\\\
		JPanel panelSide = new JPanel();
		panelSide.setBackground(Palette.DARK_GRAY);
		panelSide.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Palette.GRAY));
		panelSide.setPreferredSize(new Dimension(125, 600));
		contentPane.add(panelSide, BorderLayout.WEST);
		
		
		
		///// MAIN \\\\\
		JPanel panelMain = new JPanel();
		panelMain.setBorder(new EmptyBorder(25, 0, 25, 0));
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
		JLabel lblTitre = new JLabel("Ajouter un arbitre");
		lblTitre.setBorder(BorderFactory.createMatteBorder(0, 0, 5, 0, Palette.WHITE));
		lblTitre.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitre.setForeground(Palette.WHITE);
		lblTitre.setFont(Police.GROS_TITRE);
		panelTop.add(lblTitre);

		JPanel panelCenter = new JPanel();
		panelCenter.setBackground(Palette.DARK_GRAY);
		panelCenter.setBorder(new EmptyBorder(15, 100, 15, 100));
		panelMain.add(panelCenter, BorderLayout.CENTER);
		panelCenter.setLayout(new BorderLayout(0, 0));
		
		panelPopup = new PanelPopUp();
		panelCenter.add(panelPopup, BorderLayout.NORTH);

		JPanel panelInfosTournoi = new JPanel();
		panelInfosTournoi.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Palette.GRAY), BorderFactory.createEmptyBorder(15, 15, 15, 15)));
		panelInfosTournoi.setLayout(new BorderLayout(0, 0));
		panelInfosTournoi.setBackground(Palette.DARK_GRAY);
		panelCenter.add(panelInfosTournoi, BorderLayout.CENTER);
		
		// Titre infos
		JLabel lblTitreInfos = new JLabel("Infos Arbitre");
		lblTitreInfos.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Palette.WHITE));
		lblTitreInfos.setFont(Police.SOUS_TITRE);
		lblTitreInfos.setForeground(Palette.WHITE);
		panelInfosTournoi.add(lblTitreInfos, BorderLayout.NORTH);
		
		
		// Panel Inputs informations
		JPanel panelInfos = new JPanel();
		panelInfos.setLayout(new GridLayout(2, 0, 0, 0));
		panelInfosTournoi.add(panelInfos, BorderLayout.CENTER);
		
		
		// Panel Nom
		JPanel panelNom = new JPanel();
		panelNom.setBorder(new EmptyBorder(25, 35, 25, 35));
		panelNom.setLayout(new BoxLayout(panelNom, BoxLayout.X_AXIS));
		panelNom.setBackground(Palette.DARK_GRAY);
		panelInfos.add(panelNom);
		
		// Label Nom
		JLabel lblNom = new JLabel("Nom :");
		lblNom.setHorizontalAlignment(SwingConstants.CENTER);
		lblNom.setForeground(Palette.WHITE);
		lblNom.setFont(Police.LABEL);
		panelNom.add(lblNom);
		
		Component horizontalStrut = Box.createHorizontalStrut(53);
		panelNom.add(horizontalStrut);
		
		// Text field Nom
		inputNom = new JTextField();
		inputNom.setFont(Police.INPUT);
		inputNom.setColumns(35);
		inputNom.setBackground(Palette.GRAY);
		inputNom.setForeground(Palette.WHITE);
		inputNom.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Palette.WHITE));
		panelNom.add(inputNom);
		
		
		// Panel Niveau et Pays
		JPanel panelPrenom = new JPanel();
		panelPrenom.setBorder(new EmptyBorder(25, 35, 25, 35));
		panelPrenom.setLayout(new BoxLayout(panelPrenom, BoxLayout.X_AXIS));
		panelPrenom.setBackground(Palette.DARK_GRAY);
		panelInfos.add(panelPrenom);
		
		JLabel lblPrenom = new JLabel("Prenom :");
		lblPrenom.setHorizontalAlignment(SwingConstants.CENTER);
		lblPrenom.setForeground(Color.WHITE);
		lblPrenom.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panelPrenom.add(lblPrenom);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(25);
		panelPrenom.add(horizontalStrut_1);
		
		inputPrenom = new JTextField();
		inputPrenom.setForeground(Color.WHITE);
		inputPrenom.setFont(new Font("Tahoma", Font.PLAIN, 20));
		inputPrenom.setColumns(35);
		inputPrenom.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Palette.WHITE));
		inputPrenom.setBackground(new Color(32, 28, 44));
		panelPrenom.add(inputPrenom);
		
		FlowLayout fl_panelBoutons = new FlowLayout(FlowLayout.RIGHT, 5, 5);
		
		JPanel panelBoutons = new JPanel();
		panelBoutons.setBorder(new EmptyBorder(0, 0, 0, 0));
		panelBoutons.setBackground(Palette.DARK_GRAY);
		panelBoutons.setLayout(fl_panelBoutons);
		panelCenter.add(panelBoutons, BorderLayout.SOUTH);
		
		// Bouton annuler
		JButton btnAnnuler = new JButton("<html><body style='padding: 5px 25px;'>Annuler</body></html>");
		btnAnnuler.setName("Annuler");
		btnAnnuler.setBackground(Palette.GRAY);
		btnAnnuler.setForeground(Palette.WHITE);
		btnAnnuler.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Palette.WHITE));
		btnAnnuler.setFont(Police.LABEL);
		btnAnnuler.addActionListener(controleur);
		btnAnnuler.setFocusable(false);
		panelBoutons.add(btnAnnuler);
		
		// Bouton valider
		JButton btnValider = new JButton("<html><body style='padding: 5px 25px;'>Valider</body></html>");
		btnValider.setName("Valider");
		btnValider.setBackground(Palette.GRAY);
		btnValider.setForeground(Palette.WHITE);
		btnValider.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Palette.WHITE));
		btnValider.setFont(Police.LABEL);
		btnValider.setFocusable(false);
		btnValider.addActionListener(controleur);
		
		Component horizontalStrut_7 = Box.createHorizontalStrut(20);
		panelBoutons.add(horizontalStrut_7);
		btnValider.setFocusable(false);
		panelBoutons.add(btnValider);
	}
	
	
	public String getNom() {
		return this.inputNom.getText();
	}
	public String getPrenom() {
		return this.inputPrenom.getText();
	}
	
	public PanelPopUp getPopup() {
		return this.panelPopup;
	}
	
	private static LocalDate getDate(String date) {
		int indexSeparateur = date.indexOf('/');
		int jour = Integer.valueOf(date.substring(0, indexSeparateur));
		
		date = date.substring(indexSeparateur + 1);
		indexSeparateur = date.indexOf('/');
		int mois = Integer.valueOf(date.substring(0, indexSeparateur));
		
		date = date.substring(indexSeparateur);
		int annee = Integer.valueOf(date.substring(1));
		
		LocalDate localDate = LocalDate.of(annee, mois, jour);
		return localDate;
	}
}
