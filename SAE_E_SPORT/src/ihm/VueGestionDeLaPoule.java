package ihm;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import Fonctions.BufferedImageResize;
import Fonctions.PanelRenderer;
import Fonctions.RoundedBorder;
import controleur.ControleurGestionPoule;
import modele.Tournoi;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class VueGestionDeLaPoule extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tableClassement;
	private JTable tableMatches;
	
	/**
	 * Create the frame.
	 * @throws Exception 
	 */
	public VueGestionDeLaPoule(Tournoi tournoi) throws Exception {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 601, 425);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
				
		JPanel mainPanel = new JPanel();
		contentPane.add(mainPanel);
		mainPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel panelTop = new JPanel();
		mainPanel.add(panelTop, BorderLayout.CENTER);
		panelTop.setLayout(new BorderLayout(0, 0));
		panelTop.setBorder(new EmptyBorder(10,20,10,20));
		
		JPanel panelListeMatches = new JPanel();
		panelTop.add(panelListeMatches, BorderLayout.CENTER);
		panelListeMatches.setLayout(new BorderLayout(0, 0));
		
		JLabel lblListeMatches = new JLabel("Liste des matches");
		panelListeMatches.add(lblListeMatches, BorderLayout.NORTH);
		
		JScrollPane scrollPaneMatches = new JScrollPane();
		scrollPaneMatches.setPreferredSize(new Dimension(450,125));
		
		// Liste des matches
        String[] columnNames = { "Match", "Equipe 1", "Equipe 2", "Date"};
        // Initializing the JTable      
        DefaultTableModel modeleMatches = new DefaultTableModel(columnNames, 0) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				// Rendre toutes les cellules non éditables
				return false;
			}
			
			@Override
			public Class<?> getColumnClass(int columnIndex) {
			    if (columnIndex == 1 || columnIndex == 2) {
			        return JPanel.class;
			    }
			    return super.getColumnClass(columnIndex);
			}
			
        };
        
        ControleurGestionPoule controleur = new ControleurGestionPoule(this, tournoi);
        
		this.tableMatches = new JTable(modeleMatches);
		this.tableMatches.getColumnModel().getColumn(1).setCellRenderer(new PanelRenderer());
		this.tableMatches.getColumnModel().getColumn(2).setCellRenderer(new PanelRenderer());
		this.tableMatches.setRowHeight(35);
		this.tableMatches.getColumnModel().getColumn(0).setPreferredWidth(1);
		this.tableMatches.getColumnModel().getColumn(3).setPreferredWidth(30);
		this.tableMatches.getTableHeader().setReorderingAllowed(false);
		this.tableMatches.getTableHeader().setResizingAllowed(false);
		this.tableMatches.addMouseListener(controleur);
		
		panelListeMatches.add(scrollPaneMatches, BorderLayout.CENTER);
		
		scrollPaneMatches.setViewportView(tableMatches);
		
		JPanel panelBottom = new JPanel();
		mainPanel.add(panelBottom, BorderLayout.SOUTH);
		panelBottom.setLayout(new BorderLayout(0, 0));
		
		JPanel panelClassement = new JPanel();
		panelBottom.add(panelClassement);
		panelClassement.setBorder(new EmptyBorder(10,20,20,20));
		panelClassement.setLayout(new BorderLayout(0, 0));
		
		JLabel lblClassement = new JLabel("Classement");
		panelClassement.add(lblClassement, BorderLayout.NORTH);
		
		String[] columnsClassement = {"Rang", "Equipe", "Points Gagnés", "Matches Joués"};
		DefaultTableModel modeleClassement = new DefaultTableModel(columnsClassement,0) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				// Rendre toutes les cellules non éditables
				return false;
			}
		};
		this.tableClassement = new JTable(modeleClassement);
		DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setOpaque(false);
        this.tableClassement.getTableHeader().setReorderingAllowed(false);
        this.tableClassement.getTableHeader().setResizingAllowed(false);
        this.tableClassement.setDefaultRenderer(Object.class, cellRenderer);
		this.tableClassement.getColumnModel().getColumn(0).setPreferredWidth(10);
		this.tableClassement.setRowHeight(25);
		
		JScrollPane scrollPaneClassement = new JScrollPane();
		scrollPaneClassement.setPreferredSize(new Dimension(450,110));
		
		panelClassement.add(scrollPaneClassement, BorderLayout.CENTER);
		scrollPaneClassement.setViewportView(tableClassement);
		
		JPanel panelButtons = new JPanel();
		panelBottom.add(panelButtons, BorderLayout.EAST);
		panelButtons.setBorder(new EmptyBorder(20,20,20,20));
		GridLayout gl_panelButtons = new GridLayout(3, 0, 0, 0);
		gl_panelButtons.setVgap(20);
		panelButtons.setLayout(gl_panelButtons);
		
		JButton btnImprimer = new JButton("Imprimer");
		btnImprimer.setBackground(new Color(255, 255, 255));
		btnImprimer.addMouseListener(controleur);
		btnImprimer.setFocusable(false);
		btnImprimer.setBorder(new RoundedBorder(5));
		panelButtons.add(btnImprimer);
		
		JButton btnCloturer = new JButton("Cloturer Poule");
		btnCloturer.setBackground(new Color(255, 255, 255));
		btnCloturer.addMouseListener(controleur);
		btnCloturer.setFocusable(false);
		btnCloturer.setBorder(new RoundedBorder(5));
		panelButtons.add(btnCloturer);
		
		JButton btnRetour = new JButton("Retour");
		btnRetour.setBackground(new Color(255, 255, 255));
		btnRetour.addMouseListener(controleur);
		panelButtons.add(btnRetour);
		btnRetour.setFocusable(false);
		btnRetour.setBorder(new RoundedBorder(5));
		
		JPanel panelTitle = new JPanel();
		contentPane.add(panelTitle, BorderLayout.NORTH);
		panelTitle.setLayout(new BorderLayout(0, 0));
		
		JLabel lblGestionPoule = new JLabel("Gestion de la Poule");
		lblGestionPoule.setBorder(new EmptyBorder(10, 0, 0, 0));
		lblGestionPoule.setHorizontalAlignment(SwingConstants.CENTER);
		panelTitle.add(lblGestionPoule, BorderLayout.NORTH);
		
		StringBuffer sb = new StringBuffer(tournoi.getNomTournoi() + " (" + 
											new SimpleDateFormat("dd/MM/yyyy")
											.format(tournoi.getDateDebut()) + " - " +
											new SimpleDateFormat("dd/MM/yyyy")
											.format(tournoi.getDateFin()) + ")");
		JLabel lblTitreTournoi = new JLabel(sb.toString());
		lblTitreTournoi.setHorizontalAlignment(SwingConstants.CENTER);
		panelTitle.add(lblTitreTournoi, BorderLayout.SOUTH);
	}
	
	public void setJTableMatches (Object[][] datas) throws IOException {
		DefaultTableModel model = (DefaultTableModel) this.tableMatches.getModel();
		model.setRowCount(0);
		Object[][] cloned = new Object[datas.length][4];
		for (int i=0;i<datas.length;i++) {
			cloned[i] = datas[i].clone();
		}
		for (int i=0;i<datas.length;i++) { 
			for(int j=1;j<=2;j++) {
				JPanel panel = new JPanel();
				panel.setLayout(new BorderLayout(0, 0));
				panel.add(new JLabel(cloned[i][j].toString()), BorderLayout.CENTER);
											        
		        Image image = BufferedImageResize.resize((String) cloned[i][model.getColumnCount()-1+j], 25, 25);
		        
		        JLabel label = new JLabel(new ImageIcon(image));
		        panel.setBorder(new EmptyBorder(0,10,0,10));
				panel.add(label, BorderLayout.EAST);
				panel.setOpaque(false);
				cloned[i][j] = panel;
			}
			this.tableMatches.setRowSelectionAllowed(false);
			this.tableMatches.setOpaque(false);
			model.addRow(cloned[i]);
		}
	}
	
	public void setJTableClassement (Object[][] datas) throws InterruptedException {
		DefaultTableModel model = (DefaultTableModel) this.tableClassement.getModel();
		model.setRowCount(0);
		for (Object[] data : datas) {
			model.addRow(data);
		}
	}
}
