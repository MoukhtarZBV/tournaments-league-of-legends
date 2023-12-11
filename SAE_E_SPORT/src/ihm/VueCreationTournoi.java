package ihm;

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
import javax.swing.border.LineBorder;

import components.JTextFieldArrondi;
import javax.swing.border.MatteBorder;
import controleur.ControleurCreationTournoi;

public class VueCreationTournoi extends JFrame {
	
	private 		 JTextField inputNom;
	private   JComboBox<String> inputNiveau;
	private   JComboBox<String> inputPays;
	private JFormattedTextField inputDateDebut;
	private JFormattedTextField inputDateFin;
	
	private JPanel     panelErreur;
	private JTextField txtBorderErreur, txtErreur;
	private JPanel     panelSucces;
	private JTextField txtBorderSucces, txtSucces;

	
	// Création la fenêtre
	public VueCreationTournoi() {
		
		ControleurCreationTournoi controleur = new ControleurCreationTournoi(this);
		
		///// FENÊTRE \\\\\
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(510, 240, 900, 600);
		setTitle("Nouveau tournoi");
		setResizable(false);
		
		
		
		///// PANEL PRINCIPAL \\\\\
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		
		
		///// MENU BAR \\\\\
		JPanel panelSide = new JPanel();
		panelSide.setBackground(Palette.DARK_GRAY);
		panelSide.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Palette.GRAY));
		panelSide.setPreferredSize(new Dimension(100, 600));
		contentPane.add(panelSide, BorderLayout.WEST);
		
		
		
		///// MAIN \\\\\
		JPanel panelMain = new JPanel();
		panelMain.setLayout(new BorderLayout(0, 0));
		panelMain.setBackground(Palette.DARK_GRAY);
		contentPane.add(panelMain, BorderLayout.CENTER);
		
		///// PANEL TITRE \\\\\
		JPanel panelTop = new JPanel();
		panelTop.setPreferredSize(new Dimension(800, 120));
		panelTop.setBackground(Palette.DARK_GRAY);
		panelTop.setBorder(new EmptyBorder(0, 30, 0, 30));
		panelTop.setLayout(new GridLayout());
		panelMain.add(panelTop, BorderLayout.NORTH);
		
		// Label titre
		JLabel lblTitre = new JLabel("Nouveau tournoi");
		lblTitre.setBorder(BorderFactory.createMatteBorder(0, 0, 5, 0, Palette.GRAY));
		lblTitre.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitre.setForeground(Palette.WHITE);
		lblTitre.setFont(Police.GROS_TITRE);
		panelTop.add(lblTitre);
		
		
		
		///// MAN PANEL MILIEU \\\\\
		GridBagLayout gb_panelCenter = new GridBagLayout();
		gb_panelCenter.columnWidths  = new int[]   {774, 0};
		gb_panelCenter.rowHeights    = new int[]   {0, 100, 100, 0, 0, 0};
		gb_panelCenter.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gb_panelCenter.rowWeights    = new double[]{1.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};

		JPanel panelCenter = new JPanel();
		panelCenter.setLayout(gb_panelCenter);
		panelCenter.setBackground(Palette.DARK_GRAY);
		panelCenter.setBorder(new EmptyBorder(15, 15, 15, 15));
		panelMain.add(panelCenter, BorderLayout.CENTER);
		
		
		///// PANEL SUCCES \\\\\
		GridBagConstraints gbc_panelSucces = new GridBagConstraints();
		gbc_panelSucces.insets = new Insets(0, 0, 5, 0);
		gbc_panelSucces.fill   = GridBagConstraints.BOTH;
		gbc_panelSucces.gridx  = 0;
		gbc_panelSucces.gridy  = 0;
		
		panelSucces = new JPanel();
		panelSucces.setLayout(new BorderLayout(0, 0));
		panelCenter.add(panelSucces, gbc_panelSucces);
		
		// Petite bordure verte
		txtBorderSucces = new JTextField();
		txtBorderSucces.setText(" ");
		txtBorderSucces.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtBorderSucces.setEnabled(false);
		txtBorderSucces.setEditable(false);
		txtBorderSucces.setMargin(new Insets(5, 5, 5, 5));
		txtBorderSucces.setFont(Police.LIGNE);
		txtBorderSucces.setBackground(Palette.VERT);
		panelSucces.add(txtBorderSucces, BorderLayout.WEST);
		
		// Label succes
		txtSucces = new JTextField();
		txtSucces.setText("TOURNOI CRÉÉ AVEC SUCCÈS");
		txtSucces.setBorder(new EmptyBorder(2, 10, 2, 2));
		txtSucces.setEditable(false);
		txtSucces.setHorizontalAlignment(SwingConstants.LEFT);
		txtSucces.setForeground(Palette.VERT);
		txtSucces.setFont(Police.POPUPS);
		txtSucces.setColumns(10);
		txtSucces.setBackground(Palette.FOND_VERT);
		panelSucces.add(txtSucces);
		
		
		///// PANEL ERREUR \\\\\
		GridBagConstraints gbc_panelErreur = new GridBagConstraints();
		gbc_panelErreur.insets = new Insets(0, 0, 5, 0);
		gbc_panelErreur.fill   = GridBagConstraints.BOTH;
		gbc_panelErreur.gridx  = 0;
		gbc_panelErreur.gridy  = 0;
		
		panelErreur = new JPanel();
		panelErreur.setLayout(new BorderLayout(0, 0));
		panelCenter.add(panelErreur, gbc_panelErreur);
		
		// Petite bordure rouge
		txtBorderErreur = new JTextField();
		txtBorderErreur.setText(" ");
		txtBorderErreur.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtBorderErreur.setEnabled(false);
		txtBorderErreur.setEditable(false);
		txtBorderErreur.setMargin(new Insets(5, 5, 5, 5));
		txtBorderErreur.setFont(Police.LIGNE);
		txtBorderErreur.setBackground(Palette.ROUGE);
		panelErreur.add(txtBorderErreur, BorderLayout.WEST);
		
		// Label erreur
		txtErreur = new JTextField();
		txtErreur.setText("LES CHAMPS NE SONT PAS REMPLIS CORRECTEMENT");
		txtErreur.setBorder(new EmptyBorder(2, 10, 2, 2));
		txtErreur.setEditable(false);
		txtErreur.setHorizontalAlignment(SwingConstants.LEFT);
		txtErreur.setForeground(Palette.ROUGE);
		txtErreur.setFont(Police.POPUPS);
		txtErreur.setColumns(10);
		txtErreur.setBackground(Palette.FOND_ROUGE);
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
		panelInfosTournoi.setBackground(Palette.BLUE);
		panelCenter.add(panelInfosTournoi, gbc_panelInfosTournoi);
		
		// Titre infos
		JLabel lblTitreInfos = new JLabel("Infos Tournoi");
		lblTitreInfos.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Palette.WHITE));
		lblTitreInfos.setFont(Police.SOUS_TITRE);
		lblTitreInfos.setForeground(Palette.WHITE);
		panelInfosTournoi.add(lblTitreInfos, BorderLayout.NORTH);
		
		
		// Panel Inputs informations
		JPanel panelInfos = new JPanel();
		panelInfos.setLayout(new GridLayout(2, 0, 0, 0));
		panelInfos.setBackground(Palette.LIGHT_BLUE);
		panelInfos.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Palette.LIGHT_BLUE));
		panelInfosTournoi.add(panelInfos, BorderLayout.CENTER);
		
		
		// Panel Nom
		JPanel panelNomTournoi = new JPanel();
		panelNomTournoi.setBorder(new EmptyBorder(25, 35, 25, 35));
		panelNomTournoi.setLayout(new BoxLayout(panelNomTournoi, BoxLayout.X_AXIS));
		panelNomTournoi.setBackground(Palette.BLUE);
		panelInfos.add(panelNomTournoi);
		
		// Label Nom
		JLabel lblNom = new JLabel("Nom :");
		lblNom.setHorizontalAlignment(SwingConstants.CENTER);
		lblNom.setForeground(Palette.WHITE);
		lblNom.setFont(Police.LABEL);
		panelNomTournoi.add(lblNom);
		
		Component horizontalStrut = Box.createHorizontalStrut(53);
		panelNomTournoi.add(horizontalStrut);
		
		// Text field Nom
		inputNom = new JTextField();
		inputNom.setFont(Police.INPUT);
		inputNom.setColumns(35);
		inputNom.setBackground(Palette.DARK_BLUE);
		inputNom.setForeground(Palette.WHITE);
		inputNom.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Palette.LIGHT_BLUE));
		panelNomTournoi.add(inputNom);
		
		
		// Panel Niveau et Pays
		JPanel panelNiveauPays = new JPanel();
		panelNiveauPays.setLayout(new GridLayout(0, 2, 0, 0));
		panelNiveauPays.setBackground(Palette.BLUE);
		panelInfos.add(panelNiveauPays);
		
		
		// Panel Niveau
		JPanel panelNiveau = new JPanel();
		panelNiveau.setBorder(new EmptyBorder(25, 35, 25, 5));
		panelNiveau.setLayout(new BoxLayout(panelNiveau, BoxLayout.X_AXIS));
		panelNiveau.setBackground(Palette.BLUE);
		panelNiveauPays.add(panelNiveau);
		
		// Label Niveau
		JLabel lblNiveau = new JLabel("Niveau :");
		lblNiveau.setHorizontalAlignment(SwingConstants.LEFT);
		lblNiveau.setForeground(Palette.WHITE);
		lblNiveau.setFont(Police.LABEL);
		panelNiveau.add(lblNiveau);
		
		Component horizontalStrut_3 = Box.createHorizontalStrut(30);
		panelNiveau.add(horizontalStrut_3);
		
		// Combo box Niveau
		inputNiveau = new JComboBox<String>();
		inputNiveau.setFont(Police.COMBO);
		inputNiveau.setBackground(Palette.DARK_BLUE);
		inputNiveau.setForeground(Palette.WHITE);
		inputNiveau.setFocusable(false);
		inputNiveau.addItem("-- Niveau --");
		for (Niveau niveau : Niveau.values()) {
			inputNiveau.addItem(niveau.denomination());
		}
		panelNiveau.add(inputNiveau);
		
		
		// Panel Pays
		JPanel panelPays = new JPanel();
		panelPays.setBorder(new EmptyBorder(25, 5, 25, 35));
		panelPays.setLayout(new BoxLayout(panelPays, BoxLayout.X_AXIS));
		panelPays.setBackground(Palette.BLUE);
		panelNiveauPays.add(panelPays);
		
		Component horizontalStrut_5 = Box.createHorizontalStrut(20);
		panelPays.add(horizontalStrut_5);
		
		// Label Pays
		JLabel lblPays = new JLabel("Pays :");
		lblPays.setHorizontalAlignment(SwingConstants.LEFT);
		lblPays.setForeground(Palette.WHITE);
		lblPays.setFont(Police.LABEL);
		panelPays.add(lblPays);
		
		Component horizontalStrut_4 = Box.createHorizontalStrut(30);
		panelPays.add(horizontalStrut_4);
		
		// Combo box pays
		inputPays = new JComboBox<String>();
		inputPays.setFont(Police.COMBO);
		inputPays.setBackground(Palette.DARK_BLUE);
		inputPays.setForeground(Palette.WHITE);
		inputPays.setFocusable(false);
		inputPays.addItem("-- Pays --");
		for (Pays pays : Pays.values()) {
			inputPays.addItem(pays.denomination());
		}
		panelPays.add(inputPays);
		
		
		///// PANEL DATES \\\\\
		GridBagConstraints gbc_panelDatesTournoi = new GridBagConstraints();
		gbc_panelDatesTournoi.insets = new Insets(0, 0, 5, 0);
		gbc_panelDatesTournoi.fill  = GridBagConstraints.BOTH;
		gbc_panelDatesTournoi.gridx = 0;
		gbc_panelDatesTournoi.gridy = 2;
		
		JPanel panelDatesTournoi = new JPanel();
		panelDatesTournoi.setBorder(new EmptyBorder(5, 5, 5, 5));
		panelDatesTournoi.setLayout(new BorderLayout(0, 0));
		panelDatesTournoi.setBackground(Palette.BLUE);
		panelCenter.add(panelDatesTournoi, gbc_panelDatesTournoi);
		
		// Titre dates
		JLabel lblTitreDates = new JLabel(String.format("%-25s", "Dates"));
		lblTitreDates.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Palette.WHITE));
		lblTitreDates.setForeground(Palette.WHITE);
		lblTitreDates.setFont(Police.SOUS_TITRE);
		panelDatesTournoi.add(lblTitreDates, BorderLayout.NORTH);
	
		
		// Panel inputs dates
		JPanel panelDatesInputs = new JPanel();
		panelDatesInputs.setLayout(new GridLayout(1, 0, 0, 0));
		panelDatesInputs.setBackground(Palette.BLUE);
		panelDatesInputs.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Palette.LIGHT_BLUE));
		panelDatesTournoi.add(panelDatesInputs, BorderLayout.CENTER);
		
		
		// Panel dates début
		JPanel panelDateDebut = new JPanel();
		panelDateDebut.setBorder(new EmptyBorder(25, 35, 25, 5));
		panelDateDebut.setLayout(new BoxLayout(panelDateDebut, BoxLayout.X_AXIS));
		panelDateDebut.setBackground(Palette.BLUE);
		panelDatesInputs.add(panelDateDebut);
		
		// Label date début
		JLabel lblDateDeDebut = new JLabel("Début :");
		lblDateDeDebut.setHorizontalAlignment(SwingConstants.LEFT);
		lblDateDeDebut.setForeground(Palette.WHITE);
		lblDateDeDebut.setFont(Police.LABEL);
		panelDateDebut.add(lblDateDeDebut);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(38);
		panelDateDebut.add(horizontalStrut_1);

		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		// Input date début
		inputDateDebut = new JFormattedTextField(dateFormat);
		inputDateDebut.setForeground(Palette.LIGHT_BLUE);
		inputDateDebut.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
		inputDateDebut.setFont(Police.INPUT);
		inputDateDebut.setColumns(10);
		inputDateDebut.addActionListener(controleur);
		inputDateDebut.addFocusListener(controleur);
		inputDateDebut.setBackground(Palette.DARK_BLUE);
		inputDateDebut.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Palette.LIGHT_BLUE));
		panelDateDebut.add(inputDateDebut);
		
		
		// Panel date fin
		JPanel panelDateFin = new JPanel();
		panelDateFin.setBorder(new EmptyBorder(25, 5, 25, 35));
		panelDateFin.setLayout(new BoxLayout(panelDateFin, BoxLayout.X_AXIS));
		panelDateFin.setBackground(Palette.BLUE);
		panelDatesInputs.add(panelDateFin);
		
		Component horizontalStrut_6 = Box.createHorizontalStrut(20);
		panelDateFin.add(horizontalStrut_6);
		
		// Label date fin
		JLabel lblDateDeFin = new JLabel("Fin :");
		lblDateDeFin.setHorizontalAlignment(SwingConstants.LEFT);
		lblDateDeFin.setForeground(Palette.WHITE);
		lblDateDeFin.setFont(Police.LABEL);
		panelDateFin.add(lblDateDeFin);
		
		Component horizontalStrut_2 = Box.createHorizontalStrut(45);
		panelDateFin.add(horizontalStrut_2);
		
		// Input date fin
		inputDateFin = new JFormattedTextField(dateFormat);
		inputDateFin.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
		inputDateFin.setForeground(Palette.LIGHT_BLUE);
		inputDateFin.setFont(Police.INPUT);
		inputDateFin.setColumns(10);
		inputDateFin.addActionListener(controleur);
		inputDateFin.addFocusListener(controleur);
		inputDateFin.setBackground(Palette.DARK_BLUE);
		inputDateFin.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Palette.LIGHT_BLUE));
		panelDateFin.add(inputDateFin);
		
		
		Component verticalStrut = Box.createVerticalStrut(10);
		GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
		gbc_verticalStrut.insets = new Insets(0, 0, 5, 0);
		gbc_verticalStrut.gridx = 0;
		gbc_verticalStrut.gridy = 3;
		panelCenter.add(verticalStrut, gbc_verticalStrut);
		
		
		///// PANEL BOUTONS \\\\\
		GridBagConstraints gbc_panelBoutons = new GridBagConstraints();
		gbc_panelBoutons.fill  = GridBagConstraints.BOTH;
		gbc_panelBoutons.gridx = 0;
		gbc_panelBoutons.gridy = 4;
		
		JPanel panelBoutons = new JPanel();
		panelBoutons.setLayout(new GridLayout(1, 2, 50, 0));
		panelBoutons.setBorder(new EmptyBorder(10, 100, 10, 100));
		panelBoutons.setBackground(Palette.DARK_BLUE);
		panelCenter.add(panelBoutons, gbc_panelBoutons);
		
		// Bouton annuler
		JButton btnAnnuler = new JButton("Annuler");
		btnAnnuler.setBackground(Palette.DARK_BLUE);
		btnAnnuler.setForeground(Palette.WHITE);
		btnAnnuler.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Palette.WHITE));
		btnAnnuler.setFont(Police.LABEL);
		btnAnnuler.addActionListener(controleur);
		btnAnnuler.setFocusable(false);
		panelBoutons.add(btnAnnuler);
		
		// Bouton valider
		JButton btnValider = new JButton("Valider");
		btnValider.setBackground(Palette.DARK_BLUE);
		btnValider.setForeground(Palette.WHITE);
		btnValider.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Palette.WHITE));
		btnValider.setFont(Police.LABEL);
		btnValider.addActionListener(controleur);
		btnValider.setFocusable(false);
		panelBoutons.add(btnValider);
		
		
		this.effacerMessageErreur();
		this.effacerMessageSucces();
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
	
	// Afficher le message de succes
	public void afficherMessageSucces(String text) {
		this.panelSucces.setVisible(true);
		this.txtSucces.setText(text);
	}
	
	// Effacer le message de succes
	public void effacerMessageSucces() {
		this.panelSucces.setVisible(false);
	}

	public boolean champVide() {
		boolean unChampVide = false;
		if (this.inputNom.getText().equals("")) {
			unChampVide = true;
		} else if (((String) this.inputPays.getSelectedItem()).equals("-- Pays --")) {
			unChampVide = true;
		} else if (((String) this.inputNiveau.getSelectedItem()).equals("-- Niveau --")) {
			unChampVide = true;
		} else if (this.inputDateDebut.getForeground() == Color.LIGHT_GRAY ||
				this.inputDateDebut.getForeground().equals("") ||
				this.inputDateFin.getForeground() == Color.LIGHT_GRAY ||
				this.inputDateFin.getForeground().equals("")) {
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
		
		date = date.substring(indexSeparateur);
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
