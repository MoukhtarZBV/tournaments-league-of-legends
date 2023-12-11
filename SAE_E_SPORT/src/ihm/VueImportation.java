package ihm;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import controleur.ControleurImportation;
import modele.Tournoi;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;

public class VueImportation extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private DefaultTableModel modele;
	public static List<String[]> data;
	private JTable table;
	private JButton btnValider;
	private JLabel msgErreur;
	private JPanel panelMessage;
	
	private Tournoi tournoi;

	/**
	 * Create the frame.
	 */
	public VueImportation(Tournoi tournoi) {
		this.tournoi = tournoi;
		ControleurImportation controleur = new ControleurImportation(this);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 850, 564);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panelTitre = new JPanel();
		panelTitre.setBackground(new Color(255, 255, 255));
		contentPane.add(panelTitre, BorderLayout.NORTH);
		panelTitre.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		panelTitre.setBorder(new EmptyBorder(15, 0, 0, 0));
		
		JLabel titreImport = new JLabel("Importation des équipes");
		titreImport.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelTitre.add(titreImport);
		
		JPanel panelCenter = new JPanel();
		panelCenter.setBackground(new Color(255, 255, 255));
		contentPane.add(panelCenter, BorderLayout.CENTER);
		        
        this.modele = new DefaultTableModel();
		panelCenter.setBorder(new EmptyBorder(50, 10, 0, 10));
		panelCenter.setLayout(new BorderLayout(0, 0));
		
		JPanel panelTable = new JPanel();
		panelTable.setBackground(new Color(255, 255, 255));
		panelCenter.add(panelTable, BorderLayout.CENTER);
		panelTable.setLayout(new BorderLayout(0, 0));
		
		JPanel panelJTable = new JPanel();
		panelJTable.setBorder(new EmptyBorder(0, 20, 20, 20));
		panelJTable.setBackground(new Color(255, 255, 255));
		panelJTable.setLayout(new BorderLayout());
		panelTable.add(panelJTable, BorderLayout.CENTER);
		
		table = new JTable(modele);
		table.setRowHeight(25);
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		this.couleurModeleTable();
		panelJTable.add(table, BorderLayout.CENTER);
		
		this.panelMessage = new JPanel();
		panelMessage.setBackground(new Color(255, 255, 255));
		panelTable.add(panelMessage, BorderLayout.NORTH);
		
		this.msgErreur = new JLabel(" ");
		msgErreur.setHorizontalAlignment(SwingConstants.CENTER);
		panelMessage.add(msgErreur);
		
		JPanel panelNorth = new JPanel();
		panelNorth.setBackground(new Color(255, 255, 255));
		panelCenter.add(panelNorth, BorderLayout.NORTH);
		
		JButton btnRetour = new JButton("Retour");
		btnRetour.setBackground(Color.WHITE);
        btnRetour.setForeground(Color.BLACK);
		panelNorth.add(btnRetour);
		btnRetour.addActionListener(controleur);
		btnRetour.setFocusable(false);
		
		JButton btnImportation = new JButton("Importer");
		btnImportation.setBackground(Color.WHITE);
        btnImportation.setForeground(Color.BLACK);
		panelNorth.add(btnImportation);
		btnImportation.addActionListener(controleur);
		btnImportation.setFocusable(false);
		
		btnValider = new JButton("Valider");
		btnValider.setEnabled(false);
		btnValider.setBackground(Color.WHITE);
        btnValider.setForeground(Color.BLACK);
		panelNorth.add(btnValider);
		btnValider.addActionListener(controleur);	
		btnValider.setFocusable(false);
	}
	
	public void setModeleTable () {
		
	}
	
	public void couleurModeleTable () {
		table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (row == 0) {
                    cell.setBackground(new Color (56,111,215));
                    cell.setForeground(new Color (255,255,255));
                } else {
                	cell.setBackground(Color.WHITE);
                	cell.setForeground(new Color (0,0,0));
                }
                return cell;
            }
        });
	}
	
	public void changerBtnValider(boolean etat) {
		this.btnValider.setEnabled(etat);
	}
	
	public DefaultTableModel getModel() {
		return modele;
	}
	
	public void newModel() {
		this.modele = new DefaultTableModel();
	}
	
	public void setMsgErreur(String erreur){
		msgErreur.setText(erreur);
	}
	
	public void setColorMessage(Color color) {
		panelMessage.setBackground(color);
	}
	
	public JTable getTable() {
		return table;
	}

	public Tournoi getTournoi() {
		return this.tournoi;
	}
}
