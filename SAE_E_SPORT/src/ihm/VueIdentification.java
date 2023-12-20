package ihm;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controleur.ControleurIdentification;

import javax.swing.BoxLayout;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;

public class VueIdentification extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldLogin;
	private JTextField textFieldPassword;
	private JLabel errMsg;
	private JPanel panelErreur;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VueIdentification frame = new VueIdentification();
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
	public VueIdentification() {
		
		ControleurIdentification controleur = new ControleurIdentification(this);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 826, 477);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
		
		JPanel panelId = new JPanel();
		contentPane.add(panelId);
		panelId.setLayout(new BorderLayout(0, 0));
		
		JPanel panelHeader = new JPanel();
		panelId.add(panelHeader, BorderLayout.NORTH);
		panelHeader.setLayout(new BorderLayout(0, 0));
		
		JPanel panelBar = new JPanel();
		panelBar.setBackground(new Color(0, 128, 192));
		panelHeader.add(panelBar, BorderLayout.SOUTH);
		panelBar.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel panelTitre = new JPanel();
		panelTitre.setBorder(new EmptyBorder(70, 0, 0, 0));
		panelHeader.add(panelTitre, BorderLayout.NORTH);
		
		JLabel lblConnexion = new JLabel("Connexion");
		lblConnexion.setFont(new Font("Tahoma", Font.PLAIN, 25));
		panelTitre.add(lblConnexion);
		
		JPanel panelMain = new JPanel();
		panelId.add(panelMain, BorderLayout.CENTER);
		panelMain.setLayout(new BorderLayout(0, 0));
		
		this.panelErreur = new JPanel();
		panelErreur.setBackground(new Color(240, 240, 240));
		panelMain.add(panelErreur, BorderLayout.NORTH);
		
		this.errMsg = new JLabel(" ");
		panelErreur.add(errMsg);
		
		JPanel panelField = new JPanel();
		panelMain.add(panelField, BorderLayout.CENTER);
		
		this.textFieldLogin = new JTextField();
		panelField.add(textFieldLogin);
		textFieldLogin.setColumns(15);
		
		this.textFieldPassword = new JTextField();
		panelField.add(textFieldPassword);
		textFieldPassword.setColumns(15);
		
		JPanel panelBtn = new JPanel();
		panelBtn.setBorder(new EmptyBorder(0, 0, 200, 0));
		panelId.add(panelBtn, BorderLayout.SOUTH);
		
		JButton btnConnexion = new JButton("Se connecter");
		btnConnexion.addActionListener(controleur);
		panelBtn.add(btnConnexion);
		
		JButton btnRetour = new JButton("Retour");
		btnRetour.addActionListener(controleur);
		panelBtn.add(btnRetour);
		
		JPanel panelImg = new JPanel();
		FlowLayout fl_panelImg = (FlowLayout) panelImg.getLayout();
		fl_panelImg.setHgap(275);
		contentPane.add(panelImg);
	}
	
	public void setMessageErreur(String erreur) {
		this.errMsg.setText(erreur);
	}
	
	public void setColorErreur(Color c) {
		this.panelErreur.setBackground(c);
	}
	
	public void setLogin(String login) {
		this.textFieldLogin.setText(login);
	}
	
	public void setPassword(String password) {
		this.textFieldPassword.setText(password);
	}
	
	public String getLogin() {
		return this.textFieldLogin.getText();
	}
	
	public String getPassword() {
		return this.textFieldPassword.getText();
	}

}
