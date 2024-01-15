package ihm;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Images.ImagesIcons;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Header extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private int xDrag;
	private int yDrag;
	
	private JLabel titre;
	
	public Header(JFrame parent) {
		
		setBounds(0, 0, 1280, 25);
		setBorder(new EmptyBorder(0, 0, 0, 0));
		setLayout(new BorderLayout(0, 0));
		setBackground(Palette.LIGHT_GRAY);
		
		
		// PARTIE GAUCHE (logo + title)
		JPanel leading = new JPanel();
		leading.setLayout(new BorderLayout(10, 0));
		leading.setBorder(new EmptyBorder(2, 2, 2, 2));
		leading.setOpaque(false);
		add(leading, BorderLayout.CENTER);
		
		JLabel logo = new JLabel("");
		logo.setIcon(ImagesIcons.APP_LOGO);
		logo.setOpaque(false);
		leading.add(logo, BorderLayout.WEST);
		
		titre = new JLabel("Titre");
		titre.setFont(Police.HEADER);
		titre.setForeground(Palette.WHITE.darker());
		titre.setOpaque(false);
		leading.add(titre, BorderLayout.CENTER);
		
		
		// PARTIE DROITE (boutons) 
		JPanel buttons = new JPanel();
		buttons.setLayout(new GridLayout(1, 2));
		buttons.setOpaque(false);
		add(buttons, BorderLayout.EAST);
		
		JButton btnReduire = new JButton("─");
		btnReduire.setBackground(Palette.LIGHT_GRAY.brighter());
		btnReduire.setForeground(Palette.WHITE);
		btnReduire.setFont(Police.HEADER);
		btnReduire.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnReduire.setPreferredSize(new Dimension(40, 25));
		btnReduire.setFocusable(false);
		btnReduire.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parent.setState(JFrame.ICONIFIED);
			}
		});
		buttons.add(btnReduire);
		
		JButton btnQuitter = new JButton("✕");
		btnQuitter.setBackground(Palette.ERREUR.darker());
		btnQuitter.setForeground(Palette.WHITE);
		btnQuitter.setFont(Police.HEADER);
		btnQuitter.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnQuitter.setPreferredSize(new Dimension(40, 25));
		btnQuitter.setFocusable(false);
		btnQuitter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parent.dispose();
			}
		});
		buttons.add(btnQuitter);
		
		 
		//////////////////////////////////////////////////////////////////////////////////
		// Drag
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				xDrag = e.getXOnScreen() - parent.getX();
			    yDrag = e.getYOnScreen() - parent.getY();
			}
		});
		
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				parent.setLocation(e.getXOnScreen()-xDrag, e.getYOnScreen()-yDrag);
			}
		});
		//
		//////////////////////////////////////////////////////////////////////////////////
	}
	
	public void setTitre(String titre) {
		this.titre.setText(titre);
	}

}
