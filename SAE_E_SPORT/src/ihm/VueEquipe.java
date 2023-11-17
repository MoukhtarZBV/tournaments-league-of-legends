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
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Dimension;
import javax.swing.JButton;
import java.awt.FlowLayout;

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
	private JTextField fieldPays;
	private JButton btnSave;
	private JButton btnBack;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VueEquipe frame = new VueEquipe();
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
	public VueEquipe() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		panelRank.add(fieldWR);
		
		panelPays = new JPanel();
		panelPays.setBackground(new Color(255, 255, 255));
		left.add(panelPays);
		panelPays.setLayout(new GridLayout(0, 1, 0, 0));
		
		libPays = new JLabel("Pays Equipe");
		panelPays.add(libPays);
		panelPays.setBorder(new EmptyBorder(30, 20, 0, 80));
		
		fieldPays = new JTextField();
		fieldPays.setHorizontalAlignment(SwingConstants.LEFT);
		fieldPays.setColumns(10);
		panelPays.add(fieldPays);
		
		panelButton = new JPanel();
		panelButton.setBackground(new Color(255, 255, 255));
		left.add(panelButton);
		panelButton.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		btnSave = new JButton("Sauvegarder");
		btnSave.setMinimumSize(new Dimension(63, 21));
		btnSave.setMaximumSize(new Dimension(63, 21));
		panelButton.add(btnSave);
		
		btnBack = new JButton("Retour");
		panelButton.add(btnBack);
		panelButton.setBorder(new EmptyBorder(20, 20, 0, 20));
		
		right = new JPanel();
		right.setBackground(new Color(255, 255, 255));
		body.add(right);
		right.setLayout(new BorderLayout(0, 0));
	}

}
