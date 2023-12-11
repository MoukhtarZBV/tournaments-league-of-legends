package Fonctions;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import ihm.VueGestionDeLaPoule;

public class BufferedImageResize {

	public static Image resize (String image, int x, int y) throws IOException {
		BufferedImage bufferedImage = ImageIO.read(VueGestionDeLaPoule.class.getResource(image));
        return bufferedImage.getScaledInstance(x, y, Image.SCALE_SMOOTH);   
	}
	
}
