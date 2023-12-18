package Images;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Images {

	public static final ImageIcon EARTH = Images.getIcon(25, 25, "earth");
	public static final ImageIcon LEVEL = Images.getIcon(25, 25, "level");
	public static final ImageIcon TEAM = Images.getIcon(25, 25, "team");
	public static final ImageIcon TOP = Images.getIcon(25, 25, "topRole");
	public static final ImageIcon JUNGLE = Images.getIcon(25, 25, "junglerRole");
	public static final ImageIcon MID = Images.getIcon(25, 25, "midRole");
	public static final ImageIcon SUPPORT = Images.getIcon(25, 25, "supportRole");
	public static final ImageIcon BOTTOM = Images.getIcon(25, 25, "bottomRole");
	public static final ImageIcon TROPHY = Images.getIcon(25, 25, "trophy");
	public static final ImageIcon TROPHY_WIN = Images.getIcon(25, 25, "trophyWin");
	public static final ImageIcon UPLOAD = Images.getIcon(100, 100, "upload");
	

	private static ImageIcon getIcon(int xScale, int yScale, String type) {
		ImageIcon originalIcon = new ImageIcon(Images.class.getResource("/images/" + type + ".png"));
		return new ImageIcon(originalIcon.getImage().getScaledInstance(xScale, yScale, Image.SCALE_DEFAULT));
	}

}
