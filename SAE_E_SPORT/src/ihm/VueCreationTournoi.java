package ihm;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import modele.Niveau;
import modele.Pays;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextField;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.time.LocalDate;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.JFormattedTextField;
import javax.swing.BoxLayout;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.border.LineBorder;

import controleur.ControleurTournoi;

public class VueCreationTournoi extends JFrame {
	
	private 		 JTextField inputNom;
	private 		  JComboBox inputNiveau;
	private 		  JComboBox inputPays;
	private JFormattedTextField inputDateDebut;
	private JFormattedTextField inputDateFin;
	
	private JPanel     panelErreur;
	private JTextField txtBorder, txtErreur;
	
	
	// Main, lançant la fenêtre
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					String dirProjetJava = System.getProperty("user.dir");
					System.setProperty("derby.system.home", dirProjetJava + "/BDD");		
					String urlConnexion = "jdbc:derby:BDD;create=true";
					
					try {
						DriverManager.registerDriver(new org.apache.derby.jdbc.EmbeddedDriver());
						Connection cn = DriverManager.getConnection(urlConnexion);
						VueCreationTournoi frame = new VueCreationTournoi(cn);
						frame.setVisible(true);
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	// Création la fenêtre
	public VueCreationTournoi(Connection cn) {
		
		ControleurTournoi controleur = new ControleurTournoi(this, cn);
		
		///// FENÊTRE \\\\\
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 600);
		setTitle("Nouveau tournoi");
		
		
		
		///// PANEL PRINCIPAL \\\\\
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
		
		
		
		///// MAN PANEL MILIEU \\\\\
		GridBagLayout gb_panelMain = new GridBagLayout();
		gb_panelMain.columnWidths  = new int[]   {774, 0};
		gb_panelMain.rowHeights    = new int[]   {0, 100, 100, 0, 0, 0};
		gb_panelMain.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gb_panelMain.rowWeights    = new double[]{1.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};

		JPanel panelMain = new JPanel();
		panelMain.setLayout(gb_panelMain);
		panelMain.setBackground(new Color(255, 255, 255));
		panelMain.setBorder(new EmptyBorder(15, 50, 15, 50));
		contentPane.add(panelMain, BorderLayout.CENTER);
		
		
		///// PANEL ERREUR \\\\\
		GridBagConstraints gbc_panelErreur = new GridBagConstraints();
		gbc_panelErreur.insets = new Insets(0, 0, 5, 0);
		gbc_panelErreur.fill   = GridBagConstraints.BOTH;
		gbc_panelErreur.gridx  = 0;
		gbc_panelErreur.gridy  = 0;
		
		panelErreur = new JPanel();
		panelMain.add(panelErreur, gbc_panelErreur);
		panelErreur.setLayout(new BorderLayout(0, 0));
		
		// Petite bordure rouge
		txtBorder = new JTextField();
		txtBorder.setText(" ");
		txtBorder.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtBorder.setEnabled(false);
		txtBorder.setEditable(false);
		txtBorder.setMargin(new Insets(5, 5, 5, 5));
		txtBorder.setFont(new Font("Tahoma", Font.PLAIN, 5));
		txtBorder.setBackground(new Color(255, 0, 0));
		panelErreur.add(txtBorder, BorderLayout.WEST);
		
		// Label erreur
		txtErreur = new JTextField();
		txtErreur.setText("LES CHAMPS NE SONT PAS REMPLIS CORRECTEMENT");
		txtErreur.setBorder(new EmptyBorder(2, 10, 2, 2));
		txtErreur.setEditable(false);
		txtErreur.setHorizontalAlignment(SwingConstants.LEFT);
		txtErreur.setForeground(Color.RED);
		txtErreur.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtErreur.setColumns(10);
		txtErreur.setBackground(new Color(255, 204, 204));
		panelErreur.add(txtErreur);
		
		
		///// PANEL INFOS TOURNOIS \\\\\
		GridBagConstraints gbc_panelInfosTournoi = new GridBagConstraints();
		gbc_panelInfosTournoi.fill   = GridBagConstraints.BOTH;
		gbc_panelInfosTournoi.insets = new Insets(0, 0, 5, 0);
		gbc_panelInfosTournoi.gridx  = 0;
		gbc_panelInfosTournoi.gridy  = 1;		

		JPanel panelInfosTournoi = new JPanel();
		panelInfosTournoi.setBorder(new EmptyBorder(5, 5, 5, 5));
		panelInfosTournoi.setLayout(new BorderLayout(0, 0));
		panelMain.add(panelInfosTournoi, gbc_panelInfosTournoi);
		
		// Titre infos
		JLabel lblTitreInfos = new JLabel("  -- Infos Tournoi -------------");
		lblTitreInfos.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		panelInfosTournoi.add(lblTitreInfos, BorderLayout.NORTH);
		
		
		// Panel Inputs informations
		JPanel panelInfos = new JPanel();
		panelInfos.setLayout(new GridLayout(2, 0, 0, 0));
		panelInfosTournoi.add(panelInfos, BorderLayout.CENTER);
		
		
		// Panel Nom
		JPanel panelNomTournoi = new JPanel();
		panelNomTournoi.setBorder(new EmptyBorder(25, 35, 25, 35));
		panelNomTournoi.setLayout(new BoxLayout(panelNomTournoi, BoxLayout.X_AXIS));
		panelInfos.add(panelNomTournoi);
		
		// Label Nom
		JLabel lblNom = new JLabel("Nom :");
		lblNom.setHorizontalAlignment(SwingConstants.CENTER);
		lblNom.setForeground(new Color(0, 0, 0));
		lblNom.setFont(new Font("DejaVu Sans", Font.PLAIN, 20));
		panelNomTournoi.add(lblNom);
		
		Component horizontalStrut = Box.createHorizontalStrut(53);
		panelNomTournoi.add(horizontalStrut);
		
		// Text field Nom
		inputNom = new JTextField();
		inputNom.setFont(new Font("Tahoma", Font.PLAIN, 20));
		inputNom.setColumns(35);
		panelNomTournoi.add(inputNom);
		
		
		// Panel Niveau et Pays
		JPanel panelNiveauPays = new JPanel();
		panelNiveauPays.setLayout(new GridLayout(0, 2, 0, 0));
		panelInfos.add(panelNiveauPays);
		
		
		// Panel Niveau
		JPanel panelNiveau = new JPanel();
		panelNiveau.setBorder(new EmptyBorder(25, 35, 25, 5));
		panelNiveau.setLayout(new BoxLayout(panelNiveau, BoxLayout.X_AXIS));
		panelNiveauPays.add(panelNiveau);
		
		// Label Niveau
		JLabel lblNiveau = new JLabel("Niveau :");
		lblNiveau.setHorizontalAlignment(SwingConstants.LEFT);
		lblNiveau.setFont(new Font("DejaVu Sans", Font.PLAIN, 20));
		panelNiveau.add(lblNiveau);
		
		Component horizontalStrut_3 = Box.createHorizontalStrut(30);
		panelNiveau.add(horizontalStrut_3);
		
		// Combo box Niveau
		inputNiveau = new JComboBox();
		inputNiveau.setFont(new Font("Tahoma", Font.PLAIN, 18));
		inputNiveau.setBackground(new Color(255, 255, 255));
		panelNiveau.add(inputNiveau);
		inputNiveau.addItem("-- Niveau --");
		for (Niveau niveau : Niveau.values()) {
			inputNiveau.addItem(niveau.denomination());
		}
		
		
		// Panel Pays
		JPanel panelPays = new JPanel();
		panelPays.setBorder(new EmptyBorder(25, 5, 25, 35));
		panelNiveauPays.add(panelPays);
		panelPays.setLayout(new BoxLayout(panelPays, BoxLayout.X_AXIS));
		
		Component horizontalStrut_5 = Box.createHorizontalStrut(20);
		panelPays.add(horizontalStrut_5);
		
		// Label Pays
		JLabel lblPays = new JLabel("Pays :");
		lblPays.setHorizontalAlignment(SwingConstants.LEFT);
		lblPays.setFont(new Font("DejaVu Sans", Font.PLAIN, 20));
		panelPays.add(lblPays);
		
		Component horizontalStrut_4 = Box.createHorizontalStrut(30);
		panelPays.add(horizontalStrut_4);
		
		// Combo box pays
		inputPays = new JComboBox();
		inputPays.setFont(new Font("Tahoma", Font.PLAIN, 18));
		inputPays.setBackground(new Color(255, 255, 255));
		panelPays.add(inputPays);
		inputPays.addItem("-- Pays --");
		for (Pays pays : Pays.values()) {
			inputPays.addItem(pays.denomination());
		}
		
		
		///// PANEL DATES \\\\\
		GridBagConstraints gbc_panelDatesTournoi = new GridBagConstraints();
		gbc_panelDatesTournoi.insets = new Insets(0, 0, 5, 0);
		gbc_panelDatesTournoi.fill  = GridBagConstraints.BOTH;
		gbc_panelDatesTournoi.gridx = 0;
		gbc_panelDatesTournoi.gridy = 2;
		
		JPanel panelDatesTournoi = new JPanel();
		panelDatesTournoi.setBorder(new EmptyBorder(5, 5, 5, 5));
		panelDatesTournoi.setLayout(new BorderLayout(0, 0));
		panelMain.add(panelDatesTournoi, gbc_panelDatesTournoi);
		
		// Titre dates
		JLabel lblTitresDates = new JLabel("  -- Dates --------------------");
		lblTitresDates.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		panelDatesTournoi.add(lblTitresDates, BorderLayout.NORTH);
	
		
		// Panel inputs dates
		JPanel panelDatesInputs = new JPanel();
		panelDatesTournoi.add(panelDatesInputs, BorderLayout.CENTER);
		panelDatesInputs.setLayout(new GridLayout(1, 0, 0, 0));
		
		
		// Panel dates début
		JPanel panelDateDebut = new JPanel();
		panelDateDebut.setBorder(new EmptyBorder(25, 35, 25, 5));
		panelDateDebut.setLayout(new BoxLayout(panelDateDebut, BoxLayout.X_AXIS));
		panelDatesInputs.add(panelDateDebut);
		
		// Label date début
		JLabel lblDateDeDebut = new JLabel("Début :");
		lblDateDeDebut.setHorizontalAlignment(SwingConstants.LEFT);
		lblDateDeDebut.setFont(new Font("DejaVu Sans", Font.PLAIN, 20));
		panelDateDebut.add(lblDateDeDebut);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(38);
		panelDateDebut.add(horizontalStrut_1);

		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		// Input date début
		inputDateDebut = new JFormattedTextField(dateFormat);
		inputDateDebut.setForeground(Color.LIGHT_GRAY);
		inputDateDebut.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
		inputDateDebut.setFont(new Font("Tahoma", Font.PLAIN, 20));
		inputDateDebut.setColumns(10);
		inputDateDebut.addActionListener(controleur);
		inputDateDebut.addFocusListener(controleur);
		panelDateDebut.add(inputDateDebut);
		
		
		// Panel date fin
		JPanel panelDateFin = new JPanel();
		panelDateFin.setBorder(new EmptyBorder(25, 5, 25, 35));
		panelDateFin.setLayout(new BoxLayout(panelDateFin, BoxLayout.X_AXIS));
		panelDatesInputs.add(panelDateFin);
		
		Component horizontalStrut_6 = Box.createHorizontalStrut(20);
		panelDateFin.add(horizontalStrut_6);
		
		// Label date fin
		JLabel lblDateDeFin = new JLabel("Fin :");
		lblDateDeFin.setHorizontalAlignment(SwingConstants.LEFT);
		lblDateDeFin.setFont(new Font("DejaVu Sans", Font.PLAIN, 20));
		panelDateFin.add(lblDateDeFin);
		
		Component horizontalStrut_2 = Box.createHorizontalStrut(45);
		panelDateFin.add(horizontalStrut_2);
		
		// Input date fin
		inputDateFin = new JFormattedTextField(dateFormat);
		inputDateFin.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
		inputDateFin.setForeground(Color.LIGHT_GRAY);
		inputDateFin.setFont(new Font("Tahoma", Font.PLAIN, 20));
		inputDateFin.setColumns(10);
		inputDateFin.addActionListener(controleur);
		inputDateFin.addFocusListener(controleur);
		panelDateFin.add(inputDateFin);
		
		
		Component verticalStrut = Box.createVerticalStrut(10);
		GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
		gbc_verticalStrut.insets = new Insets(0, 0, 5, 0);
		gbc_verticalStrut.gridx = 0;
		gbc_verticalStrut.gridy = 3;
		panelMain.add(verticalStrut, gbc_verticalStrut);
		
		
		///// PANEL BOUTONS \\\\\
		GridBagConstraints gbc_panelBoutons = new GridBagConstraints();
		gbc_panelBoutons.fill  = GridBagConstraints.BOTH;
		gbc_panelBoutons.gridx = 0;
		gbc_panelBoutons.gridy = 4;
		
		JPanel panelBoutons = new JPanel();
		panelBoutons.setLayout(new GridLayout(1, 2, 50, 0));
		panelBoutons.setBorder(new EmptyBorder(10, 100, 10, 100));
		panelMain.add(panelBoutons, gbc_panelBoutons);
		
		// Bouton annuler
		JButton btnAnnuler = new JButton("Annuler");
		btnAnnuler.setBackground(new Color(255, 255, 255));
		btnAnnuler.setBorder(new LineBorder(new Color(0, 0, 102, 100), 2, true));
		btnAnnuler.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnAnnuler.addActionListener(controleur);
		panelBoutons.add(btnAnnuler);
		
		// Bouton valider
		JButton btnValider = new JButton("Valider");
		btnValider.setBackground(new Color(255, 255, 255));
		btnValider.setBorder(new LineBorder(new Color(0, 0, 102, 100), 2, true));
		btnValider.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnValider.addActionListener(controleur);
		panelBoutons.add(btnValider);
		
		
		this.effacerMessageErreur();
	}
	
	
	// Afficher le message d'erreur
	public void afficherMessageErreur(String text) {
		this.panelErreur.setVisible(true);
		this.txtErreur.setText(text);
	}
	
	// Effacer le message d'erreur
	public void effacerMessageErreur() {
		this.panelErreur.setVisible(false);
	}

	public boolean champVide() {
		boolean unChampVide = false;
		if (this.inputNom.getText().equals("")) {
			unChampVide = true;
		} else if (((String) this.inputPays.getSelectedItem()).equals("-- Pays --")) {
			unChampVide = true;
		} else if (((String) this.inputNiveau.getSelectedItem()).equals("-- Niveau --")) {
			unChampVide = true;
		}
		return unChampVide;
	}
	
	public boolean nomTropLong() {
		return this.inputNom.getText().length() > 100;
	}
	
	public String getNom() {
		return this.inputNom.getText();
	}
	
	public Pays getPays() {
		return Pays.getPays((String) this.inputPays.getSelectedItem());
	}
	
	public Niveau getNiveau() {
		return Niveau.getNiveau((String) this.inputNiveau.getSelectedItem());
	}
	
	private static LocalDate getDate(String date) {
		int indexSeparateur = date.indexOf('/');
		int jour = Integer.valueOf(date.substring(0, indexSeparateur));
		
		date = date.substring(indexSeparateur + 1);
		indexSeparateur = date.indexOf('/');
		int mois = Integer.valueOf(date.substring(0, indexSeparateur));
		
		date = date.substring(indexSeparateur + 1);
		int annee = Integer.valueOf(date.substring(1));
		
		LocalDate localDate = LocalDate.of(annee, mois, jour);
		return localDate;
	}
	
	public java.sql.Date getDateDebut() {
		java.sql.Date date = java.sql.Date.valueOf(getDate(this.inputDateDebut.getText()));
        return date;
	}
	
	public java.sql.Date getDateFin() {
		java.sql.Date date = java.sql.Date.valueOf(getDate(this.inputDateFin.getText()));
        return date;
	}
}
