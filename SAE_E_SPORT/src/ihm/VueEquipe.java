package ihm;

import java.awt.EventQueue;



import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.util.List;
import java.util.Optional;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Dimension;
import javax.swing.JButton;
import java.awt.FlowLayout;
import javax.swing.JComboBox;
import javax.swing.table.TableModel;

import controleur.ControleurEquipe;
import controleur.ControleurListeEquipe;
import dao.ConnectionJDBC;
import dao.EquipeJDBC;
import modele.Equipe;
import modele.Pays;

public class VueEquipe extends JFrame {

	private JPanel contentPane;
	private JPanel body;
	private JPanel left;
	private JPanel right;
	private JPanel panelName;
	private JPanel panelRank;
	private JPanel panelPays;
	private JPanel panelButton;
	private JLabel libName;
	private JTextField fieldName;
	private JLabel libWR;
	private JTextField fieldWR;
	private JLabel libPays;
	private JButton btnSave;
	private JButton btnBack;
	private JComboBox comboPays;
	private JTable table;
	private Optional<Equipe> equipe;

	/**
	 * Launch the application.
	 */
	public VueEquipe(List<Equipe> equipes, Optional<Equipe> equipe) {
		this.equipe = equipe;
		ControleurEquipe controleur = new ControleurEquipe(this);
		setMaximumSize(new Dimension(800, 800));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 563, 400);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JLabel titreFenModif = new JLabel("Modification Equipe");
		titreFenModif.setHorizontalAlignment(SwingConstants.CENTER);
		titreFenModif.setFont(new Font("Tahoma", Font.PLAIN, 16));
		titreFenModif.setBorder(new EmptyBorder(5, 0, 20, 20));
		
		contentPane.add(titreFenModif, BorderLayout.NORTH);
		
		body = new JPanel();
		body.setBackground(new Color(255, 255, 255));
		body.setBorder(new LineBorder(new Color(0, 0, 0)));
		contentPane.add(body, BorderLayout.CENTER);
		body.setLayout(new GridLayout(0, 2, 0, 0));
		
		left = new JPanel();
		body.add(left);
		left.setLayout(new GridLayout(4, 1, 0, 0));
		
		panelName = new JPanel();
		panelName.setBackground(new Color(255, 255, 255));
		left.add(panelName);
		panelName.setLayout(new GridLayout(0, 1, 0, 0));
		
		libName = new JLabel("Nom Equipe");
		panelName.add(libName);
		panelName.setBorder(new EmptyBorder(30, 20, 0, 80));
		
		fieldName = new JTextField();
		fieldName.setHorizontalAlignment(SwingConstants.LEFT);
		panelName.add(fieldName);
		fieldName.setColumns(10);
		fieldName.setText(equipe.get().getNom());
		
		panelRank = new JPanel();
		panelRank.setBackground(new Color(255, 255, 255));
		left.add(panelRank);
		panelRank.setLayout(new GridLayout(0, 1, 0, 0));
		
		libWR = new JLabel("World Ranking");
		panelRank.add(libWR);
		panelRank.setBorder(new EmptyBorder(30, 20, 0, 80));
		
		fieldWR = new JTextField();
		fieldWR.setHorizontalAlignment(SwingConstants.LEFT);
		fieldWR.setColumns(10);
		fieldWR.setText(String.valueOf(equipe.get().getRang()));
		panelRank.add(fieldWR);
		
		panelPays = new JPanel();
		panelPays.setBackground(new Color(255, 255, 255));
		left.add(panelPays);
		panelPays.setLayout(new GridLayout(0, 1, 0, 0));
		
		libPays = new JLabel("Pays Equipe");
		panelPays.add(libPays);
		panelPays.setBorder(new EmptyBorder(30, 20, 0, 80));
		
		comboPays = new JComboBox();
		comboPays.setBackground(new Color(255, 255, 255));
		comboPays.addItem(equipe.get().getNationalite().getNom());
		for (Pays p  : Pays.values()) {
			if (p.getNom() != equipe.get().getNationalite().getNom()) {
				comboPays.addItem(p.getNom());
			}
		}
		panelPays.add(comboPays);
		
		panelButton = new JPanel();
		panelButton.setBackground(new Color(255, 255, 255));
		left.add(panelButton);
		panelButton.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		btnSave = new JButton("Sauvegarder");
		btnSave.setMinimumSize(new Dimension(63, 21));
		btnSave.setMaximumSize(new Dimension(63, 21));
		btnSave.addActionListener(controleur);
		panelButton.add(btnSave);
		
		btnBack = new JButton("Retour");
		btnBack.addActionListener(controleur);
		panelButton.add(btnBack);
		panelButton.setBorder(new EmptyBorder(20, 20, 0, 20));
		
		right = new JPanel();
		right.setBackground(new Color(255, 255, 255));
		body.add(right);
		right.setLayout(new BorderLayout(0, 0));
		
		// Données du tableau (A RETIRER PLUS TARD)
        String[] columnsName = new String [] {"", "Membres"};
        
        // Création du DefaultTableModel avec les données et les en-têtes
        DefaultTableModel model = new DefaultTableModel(columnsName, 6);
		
		table = new JTable(model);
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		table.setRowHeight(25);
		right.add(table, BorderLayout.NORTH);
		right.setBorder(new EmptyBorder(50, 20, 0, 20));
		TableColumnModel columnModel = table.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(1);
		columnModel.getColumn(1).setPreferredWidth(130);
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
}
