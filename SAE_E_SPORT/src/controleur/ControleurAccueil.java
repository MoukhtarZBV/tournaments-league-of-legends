package controleur;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import ihm.Palette;
import ihm.VueAccueilAdmin;
import ihm.VueListeEquipe;
import ihm.VueListeTournois;
import modele.Equipe;
import modele.Tournoi;

public class ControleurAccueil implements MouseListener {
	
	private VueAccueilAdmin vue;
	
	public ControleurAccueil(VueAccueilAdmin vue) {
		this.vue = vue;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		JPanel panel = (JPanel)e.getSource();
		switch(panel.getName()) {
			case "Equipes":
				Equipe eq = new Equipe();
				VueListeEquipe vueE = new VueListeEquipe(eq.toutesLesEquipes());
				vueE.setVisible(true);
				this.vue.dispose();	
				break;
				
			case "Tournois":
				Tournoi t = new Tournoi();
				VueListeTournois vueT = new VueListeTournois(t.tousLesTournois());
				vueT.setVisible(true);
				this.vue.dispose();	
				break;
		}
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		JPanel panel = (JPanel)e.getSource();
		panel.setBackground(Palette.GRAY);
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		BufferedImage bufferedImage;
		try { 
			bufferedImage = ImageIO.read(ControleurAccueil.class.getResource("/Images/kind_cursor.png"));
			Image image = bufferedImage.getScaledInstance(100, 100, Image.SCALE_DEFAULT);
			Cursor c = toolkit.createCustomCursor(image , new Point(0,0), "img");
			panel.setCursor(c);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		JPanel panel = (JPanel)e.getSource();
		panel.setBackground(Palette.DARK_GRAY);
		panel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

}
