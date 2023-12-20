package ihm;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import Images.ImagesIcons;
import components.DropListener;
import components.PanelDropFile;
import controleur.ControleurImportation;
import modele.Tournoi;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.CardLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.border.TitledBorder;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.border.BevelBorder;

public class VueImportation extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private DefaultTableModel modele;
	public static List<String[]> data;
	private JTable tableEquipes;
	private JButton btnValider;
	private JLabel msgErreur;
	private JPanel panelMessage;
	
	private PanelDropFile panelDrop;
	private JPanel panelTableOuDrop;
	
	private Tournoi tournoi;

	/**
	 * Create the frame.
	 */
	public VueImportation(Tournoi tournoi) {
		this.tournoi = tournoi;
		ControleurImportation controleur = new ControleurImportation(this);
		this.setDropListener(controleur); 
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 850, 564);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane); 
		contentPane.setLayout(new BorderLayout(0, 0));
		
		///// PANEL TITRE \\\\\
		JPanel panelTitre = new JPanel();
		panelTitre.setBackground(Palette.DARK_GRAY);
		contentPane.add(panelTitre, BorderLayout.NORTH);
		panelTitre.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		panelTitre.setBorder(new EmptyBorder(15, 0, 0, 0));
		
		// Titre page
		JLabel titreImport = new JLabel("Importation des équipes");
		titreImport.setFont(Police.GROS_TITRE);
		titreImport.setForeground(Palette.WHITE);
		panelTitre.add(titreImport);
		
		
		///// PANEL CENTRE \\\\\
		JPanel panelCenter = new JPanel();
		panelCenter.setBackground(Palette.DARK_GRAY);
		panelCenter.setBorder(new EmptyBorder(10, 10, 20, 10));
		panelCenter.setLayout(new BorderLayout(0, 0));	
		contentPane.add(panelCenter, BorderLayout.CENTER);
		        
		///// PANEL DROP ET TABLE \\\\\
		panelTableOuDrop = new JPanel();
		panelTableOuDrop.setBorder(new EmptyBorder(10, 10, 10, 10));
		panelTableOuDrop.setBackground(Palette.DARK_GRAY);
		panelTableOuDrop.setLayout(new BorderLayout(0, 0));
		panelCenter.add(panelTableOuDrop, BorderLayout.CENTER);

		// Panel message
		this.panelMessage = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panelMessage.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		panelMessage.setBorder(new LineBorder(new Color(255, 255, 255)));
		panelMessage.setBackground(Palette.GRAY);
		panelTableOuDrop.add(panelMessage, BorderLayout.NORTH);

		this.msgErreur = new JLabel("Sélectionnez le fichier CSV correspondant pour importer les équipes");
		msgErreur.setBorder(new EmptyBorder(5, 2, 5, 2));
		msgErreur.setFont(Police.POPUPS);
		msgErreur.setForeground(Palette.WHITE);
		panelMessage.add(msgErreur);
		
		// Zone de drop
		panelDrop = new PanelDropFile();
		panelDrop.setBorder(BorderFactory.createDashedBorder(Palette.WHITE, 1, 10, 5, true));
		panelDrop.setBackground(Palette.GRAY);
		panelDrop.setDropListener(new DropListenerImpl());
		panelDrop.setMessageComponent(panelMessage, msgErreur);
		panelDrop.setLayout(new GridBagLayout());
		panelTableOuDrop.add(panelDrop, BorderLayout.CENTER);

		// Panel contenant informations sur la zone de drop
		GridBagConstraints gbc_panelDropInfos = new GridBagConstraints();
		gbc_panelDropInfos.gridx = 0;
		gbc_panelDropInfos.gridy = 0;
		gbc_panelDropInfos.weightx = 1.0;
		gbc_panelDropInfos.weighty = 1.0;
		gbc_panelDropInfos.fill = GridBagConstraints.BOTH;
		JPanel panelDropInfos = new JPanel();
		panelDropInfos.setLayout(new GridBagLayout());
		panelDropInfos.setOpaque(false);
		panelDrop.add(panelDropInfos, gbc_panelDropInfos);
		
		// Icone importer
		GridBagConstraints gbc_lblIconeImporter = new GridBagConstraints();
		gbc_lblIconeImporter.insets = new Insets(0, 0, 5, 5);
		gbc_lblIconeImporter.gridx = 1;
		gbc_lblIconeImporter.gridy = 0;
		JLabel lblIconeImporter = new JLabel();
		lblIconeImporter.setIcon(ImagesIcons.UPLOAD);
		panelDropInfos.add(lblIconeImporter, gbc_lblIconeImporter);
		
		// Libellé 'Glissez votre fichier ici'
		GridBagConstraints gbc_lblDeposerFichier = new GridBagConstraints();
		gbc_lblDeposerFichier.insets = new Insets(0, 0, 5, 5);
		gbc_lblDeposerFichier.gridx = 1;
		gbc_lblDeposerFichier.gridy = 1;
		JLabel lblDeposerFichier = new JLabel("Glissez votre fichier ici");
		lblDeposerFichier.setFont(Police.LABEL);
		lblDeposerFichier.setForeground(Palette.WHITE);
		panelDropInfos.add(lblDeposerFichier, gbc_lblDeposerFichier);

		// Libellé 'OU'
		GridBagConstraints gbc_lblAutreOption = new GridBagConstraints();
		gbc_lblAutreOption.insets = new Insets(0, 0, 5, 5);
		gbc_lblAutreOption.gridx = 1;
		gbc_lblAutreOption.gridy = 2;
		JLabel lblAutreOption = new JLabel("OU");
		lblAutreOption.setFont(Police.LABEL);
		lblAutreOption.setForeground(Palette.WHITE);
		panelDropInfos.add(lblAutreOption, gbc_lblAutreOption);

		// Bouton importer
		GridBagConstraints gbc_btnImporter = new GridBagConstraints();
		gbc_btnImporter.insets = new Insets(0, 0, 0, 5);
		gbc_btnImporter.gridx = 1;
		gbc_btnImporter.gridy = 3;
		JButton btnImporter = new JButton("Importez depuis l'explorateur");
		btnImporter.setBackground(new Color(255, 255, 255));
		btnImporter.setFocusable(false);
		btnImporter.addActionListener(controleur);
		panelDropInfos.add(btnImporter, gbc_btnImporter);


		///// PANEL BOUTONS \\\\\
		JPanel panelBoutons = new JPanel();
		panelBoutons.setBorder(new EmptyBorder(0, 10, 0, 10));
		FlowLayout flowLayout = (FlowLayout) panelBoutons.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		panelBoutons.setBackground(Palette.DARK_GRAY);
		panelCenter.add(panelBoutons, BorderLayout.SOUTH);

		JButton btnRetour = new JButton("Retour");
		btnRetour.setBackground(Color.WHITE);
		btnRetour.setForeground(Color.BLACK);
		panelBoutons.add(btnRetour);
		btnRetour.addActionListener(controleur);
		btnRetour.setFocusable(false);

		btnValider = new JButton("Valider");
		btnValider.setEnabled(false);
		btnValider.setBackground(Color.WHITE);
		btnValider.setForeground(Color.BLACK);
		btnValider.addActionListener(controleur);	
		btnValider.setFocusable(false);
		panelBoutons.add(btnValider);

	}
	
	private void mettreIconeDansHeader(String colonne, ImageIcon image) {
		tableEquipes.getColumn(colonne).setHeaderRenderer(new TableCellRenderer() {
		        @Override
		        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		               return new JLabel(image);
		        }
		    });
	}

	public void ajouterEquipesTable(Object[][] fichierCSV) {
		for (int colonne = 0; colonne < fichierCSV[0].length; colonne++) {
    		modele.addRow(new Object[] {
    			fichierCSV[0][colonne], fichierCSV[1][colonne], fichierCSV[2][colonne], fichierCSV[3][colonne], fichierCSV[4][colonne], fichierCSV[5][colonne]
    		});
		}
	}
	
	public void changerBtnValider(boolean etat) {
		this.btnValider.setEnabled(etat);
	}

	public void setMsgErreur(String erreur){
		msgErreur.setText(erreur);
		msgErreur.setForeground(Palette.ERREUR);
		panelMessage.setBackground(Palette.FOND_ERREUR);
	}
	
	public void setMsgSucces(String succes) {
		msgErreur.setText(succes);
		msgErreur.setForeground(Palette.SUCCES);
		panelMessage.setBackground(Palette.FOND_SUCCES);
	}
	
	public void setMsgNormal(String message) {
		msgErreur.setText(message);
		msgErreur.setForeground(Palette.WHITE);
		panelMessage.setBackground(Palette.GRAY);
	}
	
	public JTable getTable() {
		return tableEquipes;
	}

	public Tournoi getTournoi() {
		return this.tournoi;
	}
	
	public void afficherTableEquipes() {
		panelDrop.setVisible(false);
		JScrollPane tableScroll = new JScrollPane();
		tableScroll.getViewport().setBackground(Palette.DARK_GRAY);
		tableScroll.setBackground(Palette.DARK_GRAY);
		panelTableOuDrop.add(tableScroll, BorderLayout.CENTER);
		tableEquipes = new JTable();
		tableEquipes.setFont(new Font("Arial", Font.PLAIN, 14));
		tableEquipes.setBorder(new EmptyBorder(10, 10, 10, 0));
		tableEquipes.setRowHeight(25);
		tableEquipes.getTableHeader().setBackground(Palette.DARK_GRAY);
		tableEquipes.getTableHeader().setForeground(Palette.WHITE);
		tableEquipes.getTableHeader().setFont(Police.LABEL);
		tableEquipes.setBackground(Palette.GRAY);
		tableEquipes.setForeground(Palette.WHITE);
		this.modele = new DefaultTableModel(new Object[][] {},
	            new String[] { "Équipe", "Joueur 1", "Joueur 2", "Joueur 3", "Joueur 4", "Joueur 5" }) {
	                
				@Override
			    public boolean isCellEditable(int row, int column) {
			       return false;
			    }
			};
		tableEquipes.setModel(modele);
		mettreIconeDansHeader("Joueur 1", ImagesIcons.TOP);
		mettreIconeDansHeader("Joueur 2", ImagesIcons.JUNGLE);
		mettreIconeDansHeader("Joueur 3", ImagesIcons.MID);
		mettreIconeDansHeader("Joueur 4", ImagesIcons.SUPPORT);
		mettreIconeDansHeader("Joueur 5", ImagesIcons.BOTTOM);
		tableScroll.setViewportView(tableEquipes);
	}
	
	private class DropListenerImpl implements DropListener {
        @Override
        public void fileDropped(String filePath) {
        	notifyDropListener(filePath);
        }
    }
	
	// Listener pour que le controleur reçoit l'information lorsqu'un fichier est déposé
	private DropListener dropListener;

    public void setDropListener(DropListener listener) {
        dropListener = listener;
    }

    private void notifyDropListener(String filePath) {
        dropListener.fileDropped(filePath);
    }
}
