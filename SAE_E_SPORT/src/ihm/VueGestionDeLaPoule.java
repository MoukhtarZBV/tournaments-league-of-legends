package ihm;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controleur.ControleurGestionPoule;
import dao.ConnectionJDBC;
import dao.EquipeJDBC;
import dao.TournoiJDBC;
import modele.Equipe;
import modele.Niveau;
import modele.Pays;
import modele.Tournoi;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

import javax.swing.JButton;
import javax.swing.ScrollPaneConstants;

public class VueGestionDeLaPoule extends JFrame {

	private JPanel contentPane;
	private JTable tableClassement;
	private JTable tableMatches;

	public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Tournoi t = new Tournoi(new TournoiJDBC().getNextSequenceValue(), "My Tournament", Niveau.INTERNATIONAL, 
                    		Date.valueOf(LocalDate.of(2023, 11, 28)), Date.valueOf(LocalDate.of(2023, 12, 23)),
                    		Pays.FR);
                    VueGestionDeLaPoule frame = new VueGestionDeLaPoule(t);
                    frame.setVisible(true);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
	}
	
	/**
	 * Create the frame.
	 */
	public VueGestionDeLaPoule(Tournoi tournoi) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 601, 425);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		ControleurGestionPoule controleur = new ControleurGestionPoule(this);
		
		JPanel mainPanel = new JPanel();
		contentPane.add(mainPanel);
		mainPanel.setLayout(new GridLayout(2, 1, 0, 0));
		
		JPanel panelTop = new JPanel();
		mainPanel.add(panelTop);
		panelTop.setLayout(new BorderLayout(0, 0));
		panelTop.setBorder(new EmptyBorder(10,20,10,20));
		
		JPanel panelListeMatches = new JPanel();
		panelTop.add(panelListeMatches, BorderLayout.NORTH);
		panelListeMatches.setLayout(new BorderLayout(0, 0));
		
		JLabel lblListeMatches = new JLabel("Liste des matches");
		panelListeMatches.add(lblListeMatches, BorderLayout.NORTH);
		
		JScrollPane scrollPaneMatches = new JScrollPane();
		scrollPaneMatches.setPreferredSize(new Dimension(450,125));
		scrollPaneMatches.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		tableMatches = new JTable();
		
		panelListeMatches.add(scrollPaneMatches, BorderLayout.CENTER);
		
		scrollPaneMatches.setViewportView(tableMatches);
		
		JPanel panelBottom = new JPanel();
		mainPanel.add(panelBottom);
		panelBottom.setLayout(new BorderLayout(0, 0));
		
		JPanel panelClassement = new JPanel();
		panelBottom.add(panelClassement);
		panelClassement.setBorder(new EmptyBorder(10,20,20,20));
		panelClassement.setLayout(new BorderLayout(0, 0));
		
		JLabel lblClassement = new JLabel("Classement");
		panelClassement.add(lblClassement, BorderLayout.NORTH);
		
		tableClassement = new JTable();
		
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
		btnImprimer.addActionListener(controleur);
		panelButtons.add(btnImprimer);
		
		JButton btnCloturer = new JButton("Cloturer Poule");
		btnCloturer.addActionListener(controleur);
		panelButtons.add(btnCloturer);
		
		JButton btnRetour = new JButton("Retour");
		btnRetour.addActionListener(controleur);
		panelButtons.add(btnRetour);
		
		JPanel panelTitle = new JPanel();
		contentPane.add(panelTitle, BorderLayout.NORTH);
		panelTitle.setLayout(new BorderLayout(0, 0));
		
		JLabel lblGestionPoule = new JLabel("Gestion de la Poule");
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

}
