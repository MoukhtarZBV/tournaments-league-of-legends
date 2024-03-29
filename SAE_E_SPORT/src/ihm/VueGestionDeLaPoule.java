package ihm;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import components.BufferedImageResize;
import components.CoolScrollBar;
import components.PanelRenderer;
import controleur.ControleurGestionPoule;
import modele.Compte;
import modele.Tournoi;
import modele.TypeCompte;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class VueGestionDeLaPoule extends JFrame implements Printable{

	private static final long serialVersionUID = 1L;
	
	private JTable   tableClassement = new JTable();
	private JTable   tableMatches    = new JTable();
	private Tournoi  tournoi;
	private JButton  btnCloturer;
	private String[] columnsClassementT;
	
	private ControleurGestionPoule controleur;
	
	
	public VueGestionDeLaPoule(Tournoi tournoi) {
		
		this.tournoi = tournoi;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(Ecran.posX, Ecran.posY, Ecran.tailleX, Ecran.tailleY); 
		setResizable(false);
		setUndecorated(true);
				
		
		///// MAIN PANEL \\\\\
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.setBackground(Palette.GRAY);
		setContentPane(contentPane);

		///// HEADER \\\\\
		Header header = new Header(this);
		header.setTitre("Connexion");
		contentPane.add(header, BorderLayout.NORTH);
		
		
		///// MENU BAR \\\\\
		afficherMenuBar(contentPane);
		
		
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
		panelTop.setLayout(new GridLayout(0, 1));
		panelMain.add(panelTop, BorderLayout.NORTH);
		
		// Label titre
		JLabel lblTitre = new JLabel("Poule - " + tournoi.getNomTournoi());
		lblTitre.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitre.setForeground(Palette.WHITE);
		lblTitre.setFont(Police.GROS_TITRE);
		lblTitre.setBorder(BorderFactory.createMatteBorder(0, 0, 5, 0, Palette.WHITE));
		panelTop.add(lblTitre);
		
		
		
		///// MAN PANEL MILIEU \\\\\
		JPanel panelCenter = new JPanel();
		panelCenter.setBackground(Palette.DARK_GRAY);
		panelCenter.setBorder(new EmptyBorder(15, 100, 15, 100));
		panelMain.add(panelCenter, BorderLayout.CENTER);
		panelCenter.setLayout(new BorderLayout(0, 5));
		
		
		///// PANEL LISTE DES MATCHS \\\\\
		JPanel panelListe = new JPanel();
		panelListe.setBorder(new EmptyBorder(10, 20, 20, 20));
		panelListe.setLayout(new BorderLayout(0, 10));
		panelListe.setBackground(Palette.GRAY);
		panelCenter.add(panelListe, BorderLayout.CENTER);
		
		// Titre liste
		JLabel lblListeMatches = new JLabel("Liste des matches");
		lblListeMatches.setForeground(Palette.WHITE);
		lblListeMatches.setFont(Police.SOUS_TITRE);
		lblListeMatches.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Palette.WHITE));
		panelListe.add(lblListeMatches, BorderLayout.NORTH);
				
		// Table des matchs
        String[] columnNames = { "Match", "Equipe 1", "Equipe 2", "Date"}; 
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
        tableMatches.setBorder(new EmptyBorder(0, 0, 0, 5));
        
        this.tableMatches.setModel(modeleMatches);
        this.tableMatches.setShowGrid(false);
        this.tableMatches.setOpaque(false);
        this.tableMatches.setRowHeight(50);
        this.tableMatches.setForeground(Palette.WHITE);
        this.tableMatches.setFont(Police.TABLEAU);
        this.tableMatches.setBackground(Palette.DARK_GRAY);		
        this.tableMatches.setIntercellSpacing(new Dimension(0, 8));
		this.tableMatches.getColumnModel().getColumn(0).setCellRenderer(new PanelRenderer());
		this.tableMatches.getColumnModel().getColumn(1).setCellRenderer(new PanelRenderer());
		this.tableMatches.getColumnModel().getColumn(2).setCellRenderer(new PanelRenderer());
		this.tableMatches.getColumnModel().getColumn(3).setCellRenderer(new PanelRenderer());
		this.tableMatches.getTableHeader().setReorderingAllowed(false);
		this.tableMatches.getTableHeader().setResizingAllowed(false);
		this.tableMatches.getTableHeader().setUI(null);
		
		JScrollPane scrollPaneMatches = new JScrollPane();
		scrollPaneMatches.setBorder(new EmptyBorder(0, 0, 0, 0));
		scrollPaneMatches.setPreferredSize(new Dimension(450,125));
		scrollPaneMatches.setViewportView(tableMatches);
		scrollPaneMatches.setBackground(Palette.GRAY);
		scrollPaneMatches.setOpaque(false);
		scrollPaneMatches.getViewport().setBackground(Palette.GRAY);
		scrollPaneMatches.setVerticalScrollBar(new CoolScrollBar());
		panelListe.add(scrollPaneMatches, BorderLayout.CENTER);
		
		
		///// PANEL INFERIEUR \\\\\
		JPanel panelBottom = new JPanel();
		panelBottom.setLayout(new BorderLayout(0, 0));
		panelBottom.setBackground(Palette.GRAY);
		panelCenter.add(panelBottom, BorderLayout.SOUTH);
		
		
		///// PANEL CLASSEMENT \\\\\
		JPanel panelClassement = new JPanel();
		panelClassement.setBorder(new EmptyBorder(10, 20, 20, 20));
		panelClassement.setLayout(new BorderLayout(0, 10));
		panelClassement.setBackground(Palette.GRAY);
		panelBottom.add(panelClassement);
		
		// Titre classement
		JLabel lblClassement = new JLabel("Classement");
		lblClassement.setForeground(Palette.WHITE);
		lblClassement.setFont(Police.SOUS_TITRE);
		lblClassement.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Palette.WHITE));
		panelClassement.add(lblClassement, BorderLayout.NORTH);
		
		// Table classement
		String[] columnsClassement = {"Rang", "Équipe", "Points gagnés", "Parties gagnés"};
		columnsClassementT = columnsClassement.clone();
		DefaultTableModel modeleClassement = new DefaultTableModel(columnsClassement, 0) {
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
		DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setOpaque(false);
        
		JScrollPane scrollPaneClassement = new JScrollPane();
		scrollPaneClassement.setPreferredSize(new Dimension(500, 120));
		scrollPaneClassement.setViewportView(tableClassement);
		scrollPaneClassement.setBorder(new EmptyBorder(0, 0, 0, 0));
		scrollPaneClassement.setBackground(Palette.GRAY);
		scrollPaneClassement.getViewport().setBackground(Palette.GRAY);
		scrollPaneClassement.setVerticalScrollBar(new CoolScrollBar());
		panelClassement.add(scrollPaneClassement, BorderLayout.CENTER);
        
		// Table classement
		this.tableClassement.setModel(modeleClassement);
        this.tableClassement.setDefaultRenderer(Object.class, cellRenderer);
		this.tableClassement.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		this.tableClassement.setRowHeight(25);
        this.tableClassement.setShowGrid(false);
        this.tableClassement.setEnabled(false);
        this.tableClassement.setOpaque(false);
        this.tableClassement.setRowHeight(30);
        this.tableClassement.setForeground(Palette.WHITE);
        this.tableClassement.setFont(Police.TABLEAU);
        this.tableClassement.setBackground(Palette.DARK_GRAY);		
        this.tableClassement.setIntercellSpacing(new Dimension(0, 8));
		
        this.tableClassement.getTableHeader().setReorderingAllowed(false);
        this.tableClassement.getTableHeader().setResizingAllowed(false);
        this.tableClassement.getTableHeader().setBackground(Palette.WHITE);
        this.tableClassement.getTableHeader().setForeground(Palette.DARK_GRAY);
        this.tableClassement.getTableHeader().setFont(Police.TABLEAU);
        
		this.tableClassement.getColumnModel().getColumn(0).setMaxWidth(75);		
		this.tableClassement.getColumnModel().getColumn(2).setMinWidth(150);
		this.tableClassement.getColumnModel().getColumn(2).setMaxWidth(150);
		this.tableClassement.getColumnModel().getColumn(3).setMaxWidth(150);
		this.tableClassement.getColumnModel().getColumn(3).setMinWidth(150);
		
		
		///// PANEL DES BOUTONS \\\\\
		GridLayout gl_panelButtons = new GridLayout(3, 0, 0, 0);
		gl_panelButtons.setVgap(7);
		
		JPanel panelButtons = new JPanel();
		panelButtons.setPreferredSize(new Dimension(250, 10));
		panelButtons.setBorder(new EmptyBorder(20, 20, 20, 20));
		panelButtons.setBackground(Palette.GRAY);
		panelButtons.setLayout(gl_panelButtons);
		panelBottom.add(panelButtons, BorderLayout.EAST);
		
		// Bouton cloturer
		this.btnCloturer = new JButton("Cloturer Poule");
		btnCloturer.setBackground(Palette.GRAY);
		btnCloturer.setForeground(Palette.WHITE);
		btnCloturer.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Palette.WHITE));
		btnCloturer.setFont(Police.LABEL);
		btnCloturer.setFocusable(false);
		panelButtons.add(btnCloturer);

		// Bouton imprimer
		JButton btnImprimer = new JButton("Imprimer");
		btnImprimer.setBackground(Palette.GRAY);
		btnImprimer.setForeground(Palette.WHITE);
		btnImprimer.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Palette.WHITE));
		btnImprimer.setFont(Police.LABEL);
		btnImprimer.setFocusable(false);
		panelButtons.add(btnImprimer);
		
		// Bouton retour
		JButton btnRetour = new JButton("Retour");
		btnRetour.setBackground(Palette.GRAY);
		btnRetour.setForeground(Palette.WHITE);
		btnRetour.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Palette.WHITE));
		btnRetour.setFont(Police.LABEL);
		btnRetour.setFocusable(false);
		panelButtons.add(btnRetour);
		
		
		// Controleur
		this.controleur = new ControleurGestionPoule(this);
		addWindowListener(controleur);
		
		this.tableMatches.addMouseListener(controleur);
		
		btnImprimer.addActionListener(controleur);
		btnImprimer.addMouseListener(controleur);
		
		btnCloturer.addActionListener(controleur);
		btnCloturer.addMouseListener(controleur);
		
		btnRetour.addActionListener(controleur);
		btnRetour.addMouseListener(controleur);
		
	}
	
	private void afficherMenuBar(JPanel contentPane) {
		if(Compte.getCompteConnecte().getType() == TypeCompte.ADMINISTRATEUR) {
			MenuBar panelSide = new MenuBar(this);
			contentPane.add(panelSide, BorderLayout.WEST);
		}
	}
	
	public Tournoi getTournoi() {
		return this.tournoi;
	}
	
	public void setJTableMatches (Object[][] datas) throws IOException {
		DefaultTableModel model = (DefaultTableModel) this.tableMatches.getModel();
		model.setRowCount(0);
		
		Object[][] cloned = new Object[datas.length][4];
		for (int i = 0; i < datas.length; i++) {
			cloned[i] = datas[i].clone();
		}
		for (int i = 0; i < datas.length; i++) { 
			for(int j = 0; j < datas[i].length; j++) {
				
				// Panel avec bordure
				JPanel panel = new JPanel();
				panel.setLayout(new BorderLayout(0, 0));
		        panel.setBorder(new CompoundBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Palette.LIGHT_PURPLE), new EmptyBorder(0, 10, 0, 10)));
				panel.setBackground(Palette.DARK_GRAY); 
				switch(j) {
					case 0:
						JLabel nbMatch = new JLabel(String.valueOf(cloned[i][j]));
						nbMatch.setForeground(Palette.WHITE);
						nbMatch.setFont(Police.TABLEAU);
						nbMatch.setHorizontalAlignment(SwingConstants.LEFT);
						panel.add(nbMatch, BorderLayout.CENTER);
						break;
					case 1:
						Image image1 = BufferedImageResize.resize((String) cloned[i][model.getColumnCount()-1+j], 25, 25);
				        JLabel label1 = new JLabel(new ImageIcon(image1));
				        label1.addMouseListener(controleur);
						panel.add(label1, BorderLayout.WEST);
				        
						JLabel name1 = new JLabel(cloned[i][j].toString());
						name1.setForeground(Palette.WHITE);
						name1.setFont(Police.TABLEAU);
						name1.setHorizontalAlignment(SwingConstants.RIGHT);
						panel.add(name1, BorderLayout.CENTER);
						break;
					case 2:
						Image image2 = BufferedImageResize.resize((String) cloned[i][model.getColumnCount()-1+j], 25, 25);
				        JLabel label2 = new JLabel(new ImageIcon(image2));
				        label2.addMouseListener(controleur);
						panel.add(label2, BorderLayout.EAST);
				        
						JLabel name2 = new JLabel(cloned[i][j].toString());
						name2.setForeground(Palette.WHITE);
						name2.setFont(Police.TABLEAU);
						name2.setHorizontalAlignment(SwingConstants.LEFT);
						panel.add(name2, BorderLayout.CENTER);
						break;
					case 3:
						JLabel date = new JLabel(String.valueOf(cloned[i][j]));
						date.setForeground(Palette.WHITE);
						date.setFont(Police.TABLEAU);
						date.setHorizontalAlignment(SwingConstants.RIGHT);
						panel.add(date, BorderLayout.CENTER);
						break;
				}
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
	
	public void setActifBoutonCloturer(boolean actif) {
		this.btnCloturer.setEnabled(actif);
	}
	
	public void setVisibleBoutonCloturer(boolean visible) {
		this.btnCloturer.setVisible(visible);
	}
	
	private JTable cloneTableClassement() {
		DefaultTableModel modele = (DefaultTableModel) this.tableClassement.getModel();
		DefaultTableModel clonedModele = new DefaultTableModel(this.columnsClassementT, 0);
		JTable clonedTable = new JTable();
		clonedTable.setModel(clonedModele);
		
		Object[] datas = new Object[modele.getRowCount()];
		
		clonedModele.addRow(this.columnsClassementT);
		
		for (int i=0; i<modele.getRowCount(); i++) {
			for(int j=0; j<modele.getColumnCount(); j++) {
				datas[j] = modele.getValueAt(i, j);
			}
			clonedModele.addRow(datas);
		}
		return clonedTable;
	}
	
	// Print to pdf
	public void printClassemenToPDF() {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(this);

        if (job.printDialog()) {
            try {
                job.print();
            } catch (PrinterException ex) {
                System.out.println("Annulation d'imprimerie");
            }
        }
    }

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        if (pageIndex > 0) {
            return Printable.NO_SUCH_PAGE;
        }
        
        double xLeft = pageFormat.getImageableX();
		double yTop = pageFormat.getImageableY();
		double width = pageFormat.getImageableWidth();
		double height = pageFormat.getImageableHeight();

		// Convert 2.1 cm to points (1 inch = 72 points)
		double xCoordinate = 2.1 * 72 / 2.54; // 2.1 cm in points
		double yCoordinate = 2.1 * 72 / 2.54; // 2.1 cm in points

		// Create a Graphics2D object for printing
		Graphics2D g2d = (Graphics2D) graphics;

		// Translate to the specified coordinates
		g2d.translate(xLeft + xCoordinate, yTop + yCoordinate);
		
		// Draw the "Hello world" text
		Font font = new Font("SansSerif", Font.PLAIN, 12);
		g2d.setFont(font);
		
		String nomTournoi = this.tournoi.getNomTournoi();
		String str = "Classement du Tournoi "+nomTournoi;
		g2d.drawString(str, 0, 0);

		// Move the pen below the title
		FontMetrics fontMetrics = g2d.getFontMetrics();
		
        // Move the pen below the title
        g2d.translate(0, fontMetrics.getHeight());
        
        JTable tableC = this.cloneTableClassement();
        tableC.getColumnModel().getColumn(0).setWidth(50);
        tableC.getColumnModel().getColumn(1).setWidth(200);
        tableC.getColumnModel().getColumn(2).setWidth(100);
        tableC.getColumnModel().getColumn(3).setWidth(100);
        tableC.setSize((int) width + 100, (int) height - fontMetrics.getHeight());
        tableC.print(g2d);
        
        addBorderToTable(g2d, tableC.getColumnModel().getTotalColumnWidth(), tableC.getRowCount() * tableC.getRowHeight());
        
        return Printable.PAGE_EXISTS;
    }
    
    // ajouter border a la table en pdf 
    private void addBorderToTable(Graphics2D g2d, int tableWidth, int tableHeight) {    	
    	// Draw a border around the table
        g2d.setColor(new Color(122, 138, 153));
        g2d.drawRect(0, 0, tableWidth - 1, tableHeight - 1);
    }

}
