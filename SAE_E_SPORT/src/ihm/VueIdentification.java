package ihm;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import components.CoolPassword;
import components.CoolTextField;
import components.PanelImage;
import components.PanelPopUp;
import controleur.ControleurIdentification;

import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import java.awt.Dimension;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import Images.ImagesIcons;

import javax.swing.BorderFactory;

public class VueIdentification extends JFrame {

	private JTextField textFieldLogin;
	private JTextField textFieldPassword;
	
	private ControleurIdentification controleur;
	
	private PanelPopUp panelErreur;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		Ecran.setup();
		
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

	
	public VueIdentification() {
		
		controleur = new ControleurIdentification(this);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(Ecran.posX, Ecran.posY, Ecran.tailleX, Ecran.tailleY);
		setResizable(false);
				
		
		///// MAIN PANEL \\\\\
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.setBackground(Palette.GRAY);
		setContentPane(contentPane);
		
		
		///// PANEL CONNEXION \\\\\
		JPanel panelConnexion = new JPanel();
		panelConnexion.setBorder(new CompoundBorder(new MatteBorder(0, 0, 0, 5, Palette.LIGHT_GRAY), new EmptyBorder(20, 0, 0, 0)));
		panelConnexion.setLayout(new BorderLayout(0, 0));
		panelConnexion.setPreferredSize(new Dimension(400, panelConnexion.getPreferredSize().height));
		panelConnexion.setBackground(Palette.GRAY);
		contentPane.add(panelConnexion, BorderLayout.WEST);
				
		
		///// PANEL HEADER \\\\\
		JPanel panelTitre = new JPanel();
		panelTitre.setLayout(new BorderLayout(0, 15));
		panelTitre.setBorder(new EmptyBorder(0, 75, 10, 75));
		panelTitre.setPreferredSize(new Dimension(150, 250));
		panelTitre.setBackground(Palette.GRAY);
		panelConnexion.add(panelTitre, BorderLayout.NORTH);
		
		// Titre
		JLabel lblConnexion = new JLabel("Connexion");
		lblConnexion.setFont(Police.GROS_TITRE);
		lblConnexion.setHorizontalAlignment(SwingConstants.CENTER);
		lblConnexion.setBorder(new MatteBorder(0, 0, 5, 0, Palette.LIGHT_GRAY));
		lblConnexion.setForeground(Palette.WHITE);
		panelTitre.add(lblConnexion, BorderLayout.SOUTH);
		
		
		///// MAIN CONNEXION PANEL \\\\\
		JPanel panelMain = new JPanel();
		panelMain.setBorder(new EmptyBorder(0, 5, 5, 5));
		panelMain.setBackground(Palette.GRAY);
		panelConnexion.add(panelMain, BorderLayout.CENTER);
		panelMain.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 10));
		
		// Panel erreur
		panelErreur = new PanelPopUp();
		panelErreur.setPreferredSize(new Dimension(getPreferredSize().width - 100, 30));
		panelErreur.setBackground(Palette.GRAY);
		panelErreur.setVisible(false);
		panelMain.add(panelErreur);
		
		
		///// PANEL LOGIN \\\\\
		JPanel panelLogin = new JPanel();
		panelLogin.setLayout(new BorderLayout(0, 0));
		panelLogin.setOpaque(false);
		panelLogin.setPreferredSize(new Dimension(getPreferredSize().width - 100, 30));
		panelMain.add(panelLogin);
		
		// Login input
		JLabel iconLogin = new JLabel();
		iconLogin.setPreferredSize(new Dimension(35, iconLogin.getPreferredSize().height));
		iconLogin.setOpaque(true);
		iconLogin.setBackground(Palette.LIGHT_GRAY);
		iconLogin.setIcon(ImagesIcons.LOGIN);
		iconLogin.setHorizontalAlignment(SwingConstants.CENTER);
		iconLogin.setBorder(new MatteBorder(0, 0, 1, 0, Palette.WHITE));
		panelLogin.add(iconLogin, BorderLayout.WEST);
		
		this.textFieldLogin = new CoolTextField();
		textFieldLogin.setColumns(15);
		textFieldLogin.addKeyListener(controleur);
		panelLogin.add(textFieldLogin, BorderLayout.CENTER);
		
		
		///// PANEL PASSWORD \\\\\
		JPanel panelPass = new JPanel();
		panelPass.setLayout(new BorderLayout(0, 0));
		panelPass.setOpaque(false);
		panelPass.setPreferredSize(new Dimension(getPreferredSize().width - 100, 30));
		panelMain.add(panelPass);
		
		// Password input
		JLabel iconPass = new JLabel();
		iconPass.setPreferredSize(new Dimension(35, iconLogin.getPreferredSize().height));
		iconPass.setOpaque(true);
		iconPass.setBackground(Palette.LIGHT_GRAY);
		iconPass.setIcon(ImagesIcons.PASSWORD);
		iconPass.setHorizontalAlignment(SwingConstants.CENTER);
		iconPass.setBorder(new MatteBorder(0, 0, 1, 0, Palette.WHITE));
		panelPass.add(iconPass, BorderLayout.WEST);
		
		// Password input
		CoolPassword passwordField = new CoolPassword(15, this);
		this.textFieldPassword = passwordField.getPasswordField();
		textFieldPassword.addKeyListener(controleur);
		panelPass.add(passwordField);
		
		
		///// PANEL BOUTONS \\\\\
		JPanel panelBtn = new JPanel();
		panelBtn.setBorder(new EmptyBorder(25, 0, 0, 0));
		panelBtn.setOpaque(false);
		panelBtn.setLayout(new GridLayout(0, 1, 0, 10));
		panelMain.add(panelBtn);
		
		// Bouton connexion
		JButton btnConnexion = new JButton("<html><body style='padding: 5px 25px;'>Connexion</body></html>");
		btnConnexion.setName("Connexion");
		btnConnexion.addActionListener(controleur);
		btnConnexion.addMouseListener(controleur);
		btnConnexion.setBackground(Palette.WHITE);
		btnConnexion.setForeground(Palette.GRAY);
		btnConnexion.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Palette.WHITE));
		btnConnexion.setFont(Police.LABEL);
		btnConnexion.setFocusable(false);
		panelBtn.add(btnConnexion);
		
		// Bouton quitter
		JButton btnQuitter = new JButton("<html><body style='padding: 5px 25px;'>Quitter</body></html>");
		btnQuitter.setName("Quitter");
		btnQuitter.addActionListener(controleur);
		btnQuitter.addMouseListener(controleur);
		btnQuitter.setBackground(Palette.GRAY);
		btnQuitter.setForeground(Palette.WHITE);
		btnQuitter.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Palette.WHITE));
		btnQuitter.setFont(Police.LABEL);
		btnQuitter.setFocusable(false);
		panelBtn.add(btnQuitter);
		

		panelBtn.setPreferredSize(new Dimension(300, panelBtn.getPreferredSize().height));
		
		
		///// PANEL IMAGE CONNEXION \\\\\\
		PanelImage panelImg = new PanelImage("src/Images/connexion.png");
		contentPane.add(panelImg, BorderLayout.CENTER);
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
	
	public PanelPopUp getPopup() {
		return this.panelErreur;
	}
	
	public ControleurIdentification getControleur() {
		return this.controleur;
	}
}
