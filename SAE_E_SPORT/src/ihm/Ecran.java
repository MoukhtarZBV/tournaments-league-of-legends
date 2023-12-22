package ihm;

import java.awt.Dimension;
import java.awt.Toolkit;

public class Ecran {
	
	public static int posX, posY;
	
	public static final int tailleX = 1280;
	public static final int tailleY = 720;
	
	public static void setup() {
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize(); 

		posX = (int)size.getWidth()/2  - tailleX/2;
		posY = (int)size.getHeight()/2 - tailleY/2;
	}

}
