package ihm;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextField;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JFormattedTextField;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import java.awt.Component;
import javax.swing.Box;

public class VueCreationTournoi extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VueCreationTournoi frame = new VueCreationTournoi();
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
	public VueCreationTournoi() {
		// Fenêtre
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 600);
		setTitle("Nouveau tournoi");
		
		
		// Panel principal
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		
		// Panel du haut
		JPanel panelTop = new JPanel();
		panelTop.setBorder(new EmptyBorder(0, 0, 0, 0));
		panelTop.setLayout(new BorderLayout(0, 0));
		contentPane.add(panelTop, BorderLayout.NORTH);
		
		// Label titre
		JLabel lblTitre = new JLabel("Nouveau tournoi");
		lblTitre.setBorder(new EmptyBorder(30, 0, 30, 0));
		lblTitre.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitre.setFont(new Font("DejaVu Sans", Font.PLAIN, 40));
		panelTop.add(lblTitre, BorderLayout.CENTER);
		
		// Ligne colorée séparatrice
		textField = new JTextField();
		textField.setBackground(new Color(25, 25, 112));
		textField.setEnabled(false);
		textField.setEditable(false);
		textField.setFont(new Font("Tahoma", Font.PLAIN, 5));
		panelTop.add(textField, BorderLayout.SOUTH);
		
		
		// Panel du milieu
		JPanel panelMain = new JPanel();
		panelMain.setBackground(new Color(255, 255, 255));
		panelMain.setBorder(new EmptyBorder(15, 15, 15, 15));
		panelMain.setLayout(new GridLayout(5, 1, 10, 0));
		contentPane.add(panelMain, BorderLayout.CENTER);
		
		
		// Panel Nom
		JPanel panelNomTournoi = new JPanel();
		panelNomTournoi.setBorder(new EmptyBorder(25, 35, 25, 35));
		panelMain.add(panelNomTournoi);
		panelNomTournoi.setLayout(new BoxLayout(panelNomTournoi, BoxLayout.X_AXIS));
		
		// Label Nom
		JLabel lblNomTournoi = new JLabel("Nom du tournoi :");
		lblNomTournoi.setHorizontalAlignment(SwingConstants.CENTER);
		lblNomTournoi.setForeground(new Color(0, 0, 0));
		lblNomTournoi.setFont(new Font("DejaVu Sans", Font.PLAIN, 20));
		panelNomTournoi.add(lblNomTournoi);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		panelNomTournoi.add(horizontalStrut);
		
		// Text field Nom
		JTextField txtNom = new JTextField();
		txtNom.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtNom.setColumns(35);
		panelNomTournoi.add(txtNom);
		
		
		// Panel Nom
		JPanel panelDatesTournoi = new JPanel();
		panelDatesTournoi.setBorder(new EmptyBorder(0, 0, 0, 0));
		panelMain.add(panelDatesTournoi);
		panelDatesTournoi.setLayout(new GridLayout(0, 2, 0, 0));
		
		JPanel panelDateDebut = new JPanel();
		panelDateDebut.setBorder(new EmptyBorder(0, 35, 0, 0));
		FlowLayout flowLayout = (FlowLayout) panelDateDebut.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panelDatesTournoi.add(panelDateDebut);
		
		JLabel lblDateDeDebut = new JLabel("Date de début :");
		lblDateDeDebut.setHorizontalAlignment(SwingConstants.LEFT);
		lblDateDeDebut.setFont(new Font("DejaVu Sans", Font.PLAIN, 20));
		panelDateDebut.add(lblDateDeDebut);
		
		JFormattedTextField formattedTextField = new JFormattedTextField();
		formattedTextField.setFont(new Font("Tahoma", Font.PLAIN, 20));
		formattedTextField.setColumns(10);
		panelDateDebut.add(formattedTextField);
		
		JPanel panelDateDebut_1 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panelDateDebut_1.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		panelDateDebut_1.setBorder(new EmptyBorder(0, 35, 0, 0));
		panelDatesTournoi.add(panelDateDebut_1);
		
		JLabel lblDateDeFin = new JLabel("Date de fin :");
		lblDateDeFin.setHorizontalAlignment(SwingConstants.LEFT);
		lblDateDeFin.setFont(new Font("DejaVu Sans", Font.PLAIN, 20));
		panelDateDebut_1.add(lblDateDeFin);
		
		JFormattedTextField formattedTextField_1 = new JFormattedTextField();
		formattedTextField_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		formattedTextField_1.setColumns(10);
		panelDateDebut_1.add(formattedTextField_1);
	}

}
