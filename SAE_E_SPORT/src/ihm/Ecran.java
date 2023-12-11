package ihm;

import java.awt.Dimension;
import java.awt.Toolkit;

public class Ecran {
	
	public static int posX, posY, tailleX, tailleY;
	
	public static void setup() {
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize(); 
		
		tailleX = 1280;
		tailleY = 720;

		posX = (int)size.getWidth()/2  - tailleX/2;
		posY = (int)size.getHeight()/2 - tailleY/2;
	}

}
