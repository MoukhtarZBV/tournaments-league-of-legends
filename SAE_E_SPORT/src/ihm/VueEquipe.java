package ihm;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import components.CoolTextField;
import components.PanelPopUp;
import components.TableEquipes;

import java.awt.GridLayout;
import java.util.List;
import java.util.Optional;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;

import controleur.ControleurEquipe;
import modele.Equipe;
import modele.Joueur;
import modele.Pays;
import modele.Tournoi;

public class VueEquipe extends JFrame {

	private JTextField fieldName;
	private JTextField fieldWR;
	private JComboBox<String> comboPays;
	private Equipe equipe;
	private PanelPopUp panelPopup;
	
	private Tournoi fenPere;

	/**
	 * Launch the application.
	 */
	public VueEquipe(List<Equipe> equipes, Equipe equipe, Tournoi pere) {
		
		this.equipe = equipe;
		this.fenPere = pere;
		ControleurEquipe controleur = new ControleurEquipe(this);
		
		///// FENÊTRE \\\\\
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(Ecran.posX, Ecran.posY, Ecran.tailleX, Ecran.tailleY);
		setTitle(equipe.getNom());
		setResizable(false);
		
		
		///// PANEL PRINCIPAL \\\\\	
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		
		///// MENU BAR \\\\\
		MenuBar panelSide = new MenuBar(this);
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
		JLabel lblTitre = new JLabel(equipe.getNom());
		lblTitre.setBorder(BorderFactory.createMatteBorder(0, 0, 5, 0, Palette.WHITE));
		lblTitre.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitre.setForeground(Palette.WHITE);
		lblTitre.setFont(Police.GROS_TITRE);
		panelTop.add(lblTitre);
		
		
		///// MAN PANEL MILIEU \\\\\
		JPanel panelPrincipal = new JPanel();
		panelPrincipal.setBackground(Palette.DARK_GRAY);
		panelPrincipal.setBorder(new EmptyBorder(15, 100, 15, 100));
		panelPrincipal.setLayout(new BorderLayout(0, 10));
		panelMain.add(panelPrincipal, BorderLayout.CENTER);
		
		// Pop up
		this.panelPopup = new PanelPopUp();
		panelPopup.setBackground(Palette.DARK_GRAY);
		panelPrincipal.add(panelPopup, BorderLayout.NORTH);
		
		
		///// PANEL CENTRE \\\\\
		JPanel panelCenter = new JPanel();
		panelCenter.setBackground(Palette.GRAY);
		panelCenter.setBorder(null);
		panelCenter.setLayout(new GridLayout(1, 2, 0, 0));
		panelPrincipal.add(panelCenter, BorderLayout.CENTER);
	
		
		/// LEFT SIDE \\\
		JPanel leftPanel = new JPanel();
		leftPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
		leftPanel.setLayout(new GridLayout(4, 1, 0, 0));
		leftPanel.setOpaque(false);
		panelCenter.add(leftPanel);
		
		// Nom de l'équipe
		JPanel panelName = new JPanel();
		panelName.setBackground(Palette.GRAY);
		panelName.setLayout(new GridLayout(0, 1, 0, 0));
		panelName.setBorder(new EmptyBorder(15, 15, 15, 15));
		leftPanel.add(panelName);
		
		JLabel libName = new JLabel("Nom Équipe");
		libName.setFont(Police.LABEL);
		libName.setForeground(Palette.WHITE);
		panelName.add(libName);
		
		fieldName = new CoolTextField();
		fieldName.setBackground(Palette.DARK_GRAY);
		fieldName.setForeground(Palette.WHITE);
		fieldName.setFont(Police.INPUT);
		fieldName.setHorizontalAlignment(SwingConstants.LEFT);
		fieldName.setColumns(10);
		fieldName.setText(equipe.getNom());
		panelName.add(fieldName);
		
		// Rank de l'équipe 
		JPanel panelRank = new JPanel();
		panelRank.setBackground(Palette.GRAY);
		panelRank.setLayout(new GridLayout(0, 1, 0, 0));
		panelRank.setBorder(new EmptyBorder(15, 15, 15, 15));
		leftPanel.add(panelRank);
		
		JLabel libWR = new JLabel("World Ranking");
		libWR.setFont(Police.LABEL);
		libWR.setForeground(Palette.WHITE);
		panelRank.add(libWR);
		
		fieldWR = new CoolTextField();
		fieldWR.setBackground(Palette.DARK_GRAY);
		fieldWR.setForeground(Palette.WHITE);
		fieldWR.setFont(Police.INPUT);
		fieldWR.setEnabled(false);
		fieldWR.setColumns(10);
		fieldWR.setText(String.valueOf(equipe.getRang()));
		panelRank.add(fieldWR);
		
		// Pays de l'équipe
		JPanel panelPays = new JPanel();
		panelPays.setBackground(Palette.GRAY);
		panelPays.setLayout(new GridLayout(0, 1, 0, 0));
		panelPays.setBorder(new EmptyBorder(15, 15, 15, 15));
		leftPanel.add(panelPays);
		
		JLabel libPays = new JLabel("Pays Équipe");
		libPays.setFont(Police.LABEL);
		libPays.setForeground(Palette.WHITE);
		panelPays.add(libPays);
		
		comboPays = new JComboBox<String>();
		comboPays.setBackground(Palette.DARK_GRAY);
		comboPays.setForeground(Palette.WHITE);
		comboPays.setFont(Police.COMBO);
		comboPays.addItem(equipe.getNationalite().denomination());
		for (Pays p  : Pays.values()) {
			if (p.denomination() != equipe.getNationalite().denomination()) {
				comboPays.addItem(p.denomination());
			}
		}
		panelPays.add(comboPays);
		
		
		/// RIGHT SIDE \\\
		JPanel rightPanel = new JPanel();
		rightPanel.setOpaque(false);
		rightPanel.setLayout(new BorderLayout(0, 10));
		rightPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
		panelCenter.add(rightPanel);
		
		// Titre compo
		JPanel panelTitreCompo = new JPanel();
		panelTitreCompo.setBackground(Palette.GRAY);
		panelTitreCompo.setBorder(new CompoundBorder(BorderFactory.createMatteBorder(0, 0, 2, 0,  Palette.WHITE), new EmptyBorder(15, 1, 15, 15)));
		panelTitreCompo.setLayout(new GridLayout(0, 1, 0, 0));
		rightPanel.add(panelTitreCompo, BorderLayout.NORTH);
		
		JLabel titreCompo = new JLabel("Composition");
		titreCompo.setFont(Police.LABEL);
		titreCompo.setForeground(Palette.WHITE);
		titreCompo.setHorizontalAlignment(SwingConstants.CENTER);
		panelTitreCompo.add(titreCompo);
		
		TableEquipes tableEquipeUne = new TableEquipes(false);
		tableEquipeUne.ajouterJoueurs(equipe.getJoueurs());
		rightPanel.add(tableEquipeUne, BorderLayout.CENTER);
		
		
		// Boutons
		JPanel panelButton = new JPanel();
		panelButton.setPreferredSize(new Dimension(panelButton.getWidth(), 75));
		panelButton.setBackground(Palette.GRAY);
		panelButton.setBorder(new EmptyBorder(15, 0, 15, 0));
		panelButton.setLayout(new GridLayout(0, 2, 25, 0));
		rightPanel.add(panelButton, BorderLayout.SOUTH);
		
		JButton btnBack = new JButton("Retour");
		btnBack.addActionListener(controleur);
		btnBack.setForeground(Palette.WHITE);
		btnBack.setBackground(Palette.GRAY);
		btnBack.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Palette.WHITE));
		btnBack.setFont(Police.LABEL);
		btnBack.setFocusable(false);
		panelButton.add(btnBack);
		
		JButton btnSave = new JButton("Sauvegarder");
		btnSave.addActionListener(controleur);
		btnSave.setForeground(Palette.WHITE);
		btnSave.setBackground(Palette.GRAY);
		btnSave.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Palette.WHITE));
		btnSave.setFont(Police.LABEL);
		btnSave.setFocusable(false);
		panelButton.add(btnSave);
	}
	
	
	public String getNomEquipe() {
		return fieldName.getText();
	}
	
	public int getIdEquipe() {
		return this.equipe.getIdEquipe();
	}

	public int getRangEquipe() {
		return Integer.parseInt(fieldWR.getText());
	}
	
	public Pays getPaysEquipe() {
		return Pays.getPays((String)comboPays.getSelectedItem());
	}
	
	public Tournoi getPere() {
		return this.fenPere;
	}
	
	public PanelPopUp getPopup() {
		return this.panelPopup;
	}
}
