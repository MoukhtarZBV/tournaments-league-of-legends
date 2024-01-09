package ihm;

import java.awt.EventQueue;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controleur.ControleurHistoriquePoints;
import modele.Equipe;
import modele.ModeleHistoriquePoints;
import modele.Tournoi;

import java.awt.GridLayout;
import java.awt.event.FocusEvent;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JList;
import java.awt.Color;
import javax.swing.JButton;

public class VueHistoriquePoints extends JFrame {

	private JTextField searchBar;
	
	private JTable equipesTable;
	private JTable tournoiTable;
	
	private JLabel pasEquipe;
	
	public VueHistoriquePoints() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1280, 720);
		
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel titrePage = new JLabel("Historique Points");
		titrePage.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(titrePage, BorderLayout.NORTH);
		
		JPanel panelCentre = new JPanel();
		contentPane.add(panelCentre, BorderLayout.CENTER);
		
		searchBar = new JTextField();
		panelCentre.setLayout(new BorderLayout(0, 0));
		JPanel panelTop = new JPanel();
		panelTop.setLayout(new BorderLayout());
		pasEquipe = new JLabel();
		pasEquipe.setHorizontalAlignment(SwingConstants.CENTER);
		pasEquipe.setForeground(new Color(255, 0, 0));
		panelTop.add(searchBar, BorderLayout.CENTER);
		panelTop.add(pasEquipe, BorderLayout.SOUTH);
		pasEquipe.setText("");
		panelCentre.add(panelTop, BorderLayout.NORTH);
		searchBar.setColumns(10);
		
		DefaultListModel<String> modele = new DefaultListModel<>();
		modele.addElement("List des équipes");
		
		JPanel deuxScroll = new JPanel();
		panelCentre.add(deuxScroll);
		deuxScroll.setLayout(new GridLayout(0, 2, 0, 0));
		
		JScrollPane equipesScroll = new JScrollPane();
		equipesScroll.setBorder(new EmptyBorder(20,20,20,20));
		deuxScroll.add(equipesScroll);
		
		String[] columnNamesEquipes = {"","Equipe"};
		DefaultTableModel modeleEquipes = new DefaultTableModel(columnNamesEquipes, 0) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				// Rendre toutes les cellules non éditables
				return false;
			}
			
        };
        modeleEquipes.addRow(new Object[]{"1", "AsiaCup"});
        modeleEquipes.addRow(new Object[]{"2", "EuropeCup"});
        modeleEquipes.addRow(new Object[]{"3", "PhilipinesCup"});
        modeleEquipes.addRow(new Object[]{"4", "UKCup"});
		
		this.equipesTable = new JTable();
		this.equipesTable.setModel(modeleEquipes);
		this.equipesTable.setRowHeight(20);
		this.equipesTable.getTableHeader().setReorderingAllowed(false);
		this.equipesTable.getTableHeader().setResizingAllowed(false);
		this.equipesTable.getTableHeader().setToolTipText("List des équipes");
		this.equipesTable.setName("equipesTable");
		equipesScroll.setViewportView(this.equipesTable);

		JScrollPane tournoiScroll = new JScrollPane();
		deuxScroll.add(tournoiScroll);
		
		tournoiScroll.setBorder(new EmptyBorder(20,20,20,20));
		
		String[] columnNamesTournoi = {"Tournoi","Nombre de points"};
		DefaultTableModel modeleTournoi = new DefaultTableModel(columnNamesTournoi, 0) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				// Rendre toutes les cellules non éditables
				return false;
			}
        };
		tournoiTable = new JTable();
		tournoiTable.setModel(modeleTournoi);
		tournoiTable.setName("tournoiTable");
		tournoiTable.setRowHeight(20);
		tournoiTable.getTableHeader().setReorderingAllowed(false);
		tournoiTable.getTableHeader().setResizingAllowed(false);
		
		ControleurHistoriquePoints controleur = new ControleurHistoriquePoints(this);
		this.equipesTable.addMouseListener(controleur);
		searchBar.addFocusListener(controleur);
		searchBar.addActionListener(controleur);
		tournoiTable.addMouseListener(controleur);
        modeleTournoi.setColumnCount(0);
		tournoiScroll.setViewportView(tournoiTable);
		
		JPanel panelBottom = new JPanel();
		panelCentre.add(panelBottom, BorderLayout.SOUTH);
		
		JButton btnRetour = new JButton("Retour");
		btnRetour.addActionListener(controleur);
		panelBottom.add(btnRetour);
	}
	
	public void setResultat(boolean res) {
		if (res) {
			this.pasEquipe.setText("Pas d'equipe existe");
		} else {
			this.pasEquipe.setText("");
		}
	}
	
	public void viderColonneTableTournoi() {
		DefaultTableModel modeleTournoi = (DefaultTableModel) this.tournoiTable.getModel();
		modeleTournoi.setColumnCount(0);
	}
	
	public void viderColonneTableEquipe() {
		DefaultTableModel modeleEquipe = (DefaultTableModel) this.equipesTable.getModel();
		modeleEquipe.setColumnCount(0);
	}
	
	public void addColumnTableEquipe () {
		DefaultTableModel modeleEquipe = (DefaultTableModel) this.equipesTable.getModel();
		modeleEquipe.addColumn("Tournoi");
		modeleEquipe.addColumn("Nombre de points");
	}
	
	public void addColumnTableTournoi () {
		DefaultTableModel modeleTournoi = (DefaultTableModel) this.tournoiTable.getModel();
		modeleTournoi.addColumn("Tournoi");
		modeleTournoi.addColumn("Nombre de points");
		tournoiTable.getColumnModel().getColumn(0).setPreferredWidth(1);
		tournoiTable.getColumnModel().getColumn(1).setPreferredWidth(30);
		tournoiTable.getColumnModel().getColumn(0).setPreferredWidth(2);
	}
	
	
	public void setTableTournoi (Map<Tournoi, Integer> pointsTournoi) {
		DefaultTableModel modeleTournoi = (DefaultTableModel) this.tournoiTable.getModel();
		modeleTournoi.setRowCount(0);
		for (Entry<Tournoi, Integer> entry : pointsTournoi.entrySet()) {
			modeleTournoi.addRow(new Object[]{entry.getKey().getNomTournoi(), entry.getValue()});
		}
	}
	
	public void setTableEquipes (List<Equipe> equipes) {
		DefaultTableModel modeleEquipe = (DefaultTableModel) this.equipesTable.getModel();
		modeleEquipe.setRowCount(0);
		for (int i=0;i<equipes.size();i++) {
			modeleEquipe.addRow(new Object[] {i+1, equipes.get(i).getNom()});
		}
	}

}
