package Images;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Images {

	public static final ImageIcon EARTH = Images.getIcon(25, 25, "earth");
	public static final ImageIcon LEVEL = Images.getIcon(25, 25, "level");

	private static ImageIcon getIcon(int xScale, int yScale, String type) {
		ImageIcon originalIcon = new ImageIcon(Images.class.getResource("/images/" + type + ".png"));
		return new ImageIcon(originalIcon.getImage().getScaledInstance(xScale, yScale, Image.SCALE_DEFAULT));
	}

}
