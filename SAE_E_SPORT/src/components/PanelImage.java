package components;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class PanelImage extends JPanel {

    private BufferedImage image;

    public PanelImage(String imagePath) {
       try {                
          image = ImageIO.read(new File(imagePath));
       } catch (IOException ex) {
    	   System.out.println("Erreur lors de la lecture de l'image: " + imagePath);
       }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);         
    }

}
