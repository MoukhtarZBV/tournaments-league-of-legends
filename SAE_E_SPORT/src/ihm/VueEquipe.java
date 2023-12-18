package ihm;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import java.awt.GridLayout;
import java.util.List;
import java.util.Optional;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JComboBox;

import controleur.ControleurEquipe;
import modele.Equipe;
import modele.Joueur;
import modele.Pays;

public class VueEquipe extends JFrame {

	private JTextField fieldName;
	private JTextField fieldWR;
	private JComboBox<String> comboPays;
	private Optional<Equipe> equipe;
	private JLabel msgErreur;
	private JPanel panelErreur;

	/**
	 * Launch the application.
	 */
	public VueEquipe(List<Equipe> equipes, Optional<Equipe> equipe) {
		
		this.equipe = equipe;
		ControleurEquipe controleur = new ControleurEquipe(this);
		
		///// FENÊTRE \\\\\
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(510, 240, 900, 600);
		setTitle(equipe.get().getNom());
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
		JLabel lblTitre = new JLabel(equipe.get().getNom());
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
		
		JPanel panelCenter = new JPanel();
		contentPane.add(panelCenter, BorderLayout.CENTER);
		panelCenter.setLayout(new BorderLayout(0, 0));
		
		
		/// PANEL MAIN \\\
		JPanel panelMain = new JPanel();
		panelMain.setBackground(Palette.WHITE);
		panelMain.setBorder(new EmptyBorder(15, 15, 15, 15));
		panelMain.setLayout(new GridLayout(0, 2, 0, 0));
		panelCenter.add(panelMain);
		
		
		/// LEFT SIDE \\\
		JPanel leftPanel = new JPanel();
		leftPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
		leftPanel.setLayout(new GridLayout(4, 1, 0, 0));
		leftPanel.setBackground(Palette.WHITE);
		panelMain.add(leftPanel);
		
		
		// Nom de l'équipe
		JPanel panelName = new JPanel();
		panelName.setBackground(Palette.COOL);
		panelName.setLayout(new GridLayout(0, 1, 0, 0));
		panelName.setBorder(new EmptyBorder(15, 15, 15, 15));
		leftPanel.add(panelName);
		
		JLabel libName = new JLabel("Nom Équipe");
		libName.setFont(Police.LABEL);
		panelName.add(libName);
		
		fieldName = new JTextField();
		fieldName.setHorizontalAlignment(SwingConstants.LEFT);
		fieldName.setColumns(10);
		fieldName.setText(equipe.get().getNom());
		panelName.add(fieldName);
		
		
		// Rank de l'équipe 
		JPanel panelRank = new JPanel();
		panelRank.setBackground(Palette.COOL);
		panelRank.setLayout(new GridLayout(0, 1, 0, 0));
		panelRank.setBorder(new EmptyBorder(15, 15, 15, 15));
		leftPanel.add(panelRank);
		
		JLabel libWR = new JLabel("World Ranking");
		libWR.setFont(Police.LABEL);
		panelRank.add(libWR);
		
		fieldWR = new JTextField();
		fieldWR.setHorizontalAlignment(SwingConstants.LEFT);
		fieldWR.setColumns(10);
		fieldWR.setText(String.valueOf(equipe.get().getRang()));
		panelRank.add(fieldWR);
		
		
		// Pays de l'équipe
		JPanel panelPays = new JPanel();
		panelPays.setBackground(Palette.COOL);
		panelPays.setLayout(new GridLayout(0, 1, 0, 0));
		panelPays.setBorder(new EmptyBorder(15, 15, 15, 15));
		leftPanel.add(panelPays);
		
		JLabel libPays = new JLabel("Pays Équipe");
		libPays.setFont(Police.LABEL);
		panelPays.add(libPays);
		
		comboPays = new JComboBox<String>();
		comboPays.setBackground(Palette.WHITE);
		comboPays.addItem(equipe.get().getNationalite().denomination());
		for (Pays p  : Pays.values()) {
			if (p.denomination() != equipe.get().getNationalite().denomination()) {
				comboPays.addItem(p.denomination());
			}
		}
		panelPays.add(comboPays);
		
		
		// Boutons
		JPanel panelButton = new JPanel();
		panelButton.setBackground(Palette.COOL);
		panelButton.setBorder(new EmptyBorder(35, 25, 35, 25));
		panelButton.setLayout(new GridLayout(0, 2, 25, 0));
		leftPanel.add(panelButton);
		
		JButton btnSave = new JButton("Sauvegarder");
		btnSave.addActionListener(controleur);
		panelButton.add(btnSave);
		btnSave.setBackground(Color.WHITE);
		btnSave.setFocusable(false);
		
		JButton btnBack = new JButton("Retour");
		btnBack.addActionListener(controleur);
		panelButton.add(btnBack);
		panelButton.setBorder(new EmptyBorder(20, 20, 0, 20));
		btnBack.setBackground(Color.WHITE);
		btnBack.setFocusable(false);
		
		
		/// RIGHT SIDE \\\
		JPanel rightPanel = new JPanel();
		rightPanel.setBackground(Palette.WHITE);
		rightPanel.setLayout(new BorderLayout(0, 10));
		rightPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
		panelMain.add(rightPanel);
		
		// Titre compo
		JPanel panelTitreCompo = new JPanel();
		panelTitreCompo.setBackground(Palette.COOL);
		panelTitreCompo.setBorder(new EmptyBorder(15, 1, 15, 15));
		panelTitreCompo.setLayout(new GridLayout(0, 1, 0, 0));
		rightPanel.add(panelTitreCompo, BorderLayout.NORTH);
		
		JLabel titreCompo = new JLabel("Composition");
		titreCompo.setFont(Police.SOUS_TITRE);
		titreCompo.setHorizontalAlignment(SwingConstants.CENTER);
		panelTitreCompo.add(titreCompo);
		
        DefaultTableModel model = new DefaultTableModel(new Object[][] {},
				new String[] {"Num", "Joueur"});
        
        int objetCourant = 0;
        for (Joueur j : equipe.get().getJoueurs()) {
        	model.addRow(new Object[] {objetCourant+1,j.getPseudo()});
        	objetCourant ++;
        }
        
		JTable table = new JTable(model);
		table.setEnabled(false);
		table.setRowHeight(25);
		
		TableColumnModel columnModel = table.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(1);
		columnModel.getColumn(1).setPreferredWidth(130);
		
		rightPanel.add(table, BorderLayout.CENTER);
		
		this.panelErreur = new JPanel();
		panelErreur.setBackground(new Color(255, 255, 255));
		panelCenter.add(panelErreur, BorderLayout.NORTH);
		
		this.msgErreur = new JLabel("");
		panelErreur.add(msgErreur);
		
		
	}
	
	
	public String getNomEquipe() {
		return fieldName.getText();
	}
	
	public int getIdEquipe() {
		return this.equipe.get().getIdEquipe();
	}

	public int getRangEquipe() {
		return Integer.parseInt(fieldWR.getText());
	}
	
	public Pays getPaysEquipe() {
		return Pays.getPays((String)comboPays.getSelectedItem());
	}
	
	public void setMsgErreur(String erreur){
		msgErreur.setText(erreur);
	}
	
	public void setColorMessage(Color color) {
		panelErreur.setBackground(color);
	}
}
