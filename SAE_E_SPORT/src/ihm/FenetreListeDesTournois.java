package ihm;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dao.EquipeJDBC;
import modele.Equipe;

import javax.swing.JLabel;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.SwingConstants;
import java.awt.Font;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Dimension;

public class FenetreListeDesTournois extends JFrame {

	private JPanel contentPane;
	private JTextField search_field;
	private JTable table;

	public static void main (String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {											
					// Création d'une connexion
					FenetreListeDesTournois fenetre = new FenetreListeDesTournois();
					fenetre.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the frame.
	 */
	public FenetreListeDesTournois() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JLabel lblPage = new JLabel("Liste des tournois");
		lblPage.setHorizontalAlignment(SwingConstants.CENTER);
		lblPage.setFont(new Font("Tahoma", Font.PLAIN, 20));
		contentPane.add(lblPage, BorderLayout.NORTH);
		
		JPanel panel_body = new JPanel();
		contentPane.add(panel_body, BorderLayout.CENTER);
		panel_body.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_search = new JPanel();
		panel_body.add(panel_search, BorderLayout.NORTH);
		
		search_field = new JTextField();
		panel_search.add(search_field);
		search_field.setColumns(25);
		
		JButton searchButton = new JButton();
		searchButton.setIconTextGap(4);
		searchButton.setIcon(new ImageIcon("src/Images/Search_Icon3.jpg"));
		searchButton.setBackground(Color.WHITE);
		panel_search.add(searchButton);
		
		table = new JTable();
		JScrollPane scrollPane = new JScrollPane(table);
		panel_body.add(scrollPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		panel_body.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		
		JButton btnRetour = new JButton("");
		panel.add(btnRetour);
		
		
	}

}
