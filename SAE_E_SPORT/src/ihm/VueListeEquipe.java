package ihm;

import java.awt.EventQueue;

import java.util.*;
import java.util.stream.Collectors;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dao.EquipeJDBC;
import modele.Equipe;

import java.awt.BorderLayout;
import javax.swing.JList;
import javax.swing.JScrollBar;
import javax.swing.JLabel;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JScrollPane;

public class VueListeEquipe extends JFrame {

	private JPanel contentPane;
	private List<Equipe> equipes;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					String dirProjetJava = System.getProperty("user.dir");		
					System.setProperty("derby.system.home", dirProjetJava+"/BDD");
					DriverManager.registerDriver(new org.apache.derby.jdbc.EmbeddedDriver());
					String urlConnexion = "jdbc:derby:BDD;create=true";			
																
					// Création d'une connexion
					Connection dbConnection = DriverManager.getConnection(urlConnexion);
					
					List<Equipe> equipes = (new EquipeJDBC(dbConnection).getAll());
					VueListeEquipe frame = new VueListeEquipe(equipes);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VueListeEquipe(List<Equipe> equipes) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		List<String> nomEquipes = equipes.stream()
				.map(e -> e.getNom())
				.collect(Collectors.toList());
		
		JList listeEquipes = new JList(nomEquipes.toArray());
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		scrollPane.setViewportView(listeEquipes);
		
		JLabel libFenetre_1 = new JLabel("Liste des équipes");
		libFenetre_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		contentPane.add(libFenetre_1, BorderLayout.NORTH);
	}

}
