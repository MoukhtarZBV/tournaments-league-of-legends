package Images;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
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
	public static final String TROPHY = "/Images/trophy.png";
	public static final String TROPHY_WIN = "/Images/trophyWin.png";
	public static final ImageIcon UPLOAD = Images.getIcon(100, 100, "upload");
	public static final ImageIcon EQUIPE = Images.getIcon(128, 128, "imgEquipe");
	public static final ImageIcon TOURNOI = Images.getIcon(128, 128, "imgTournoi");
	public static final ImageIcon HISTORIQUE = Images.getIcon(128, 128, "imgHistorique");
	
	private static ImageIcon getIcon(int xScale, int yScale, String type) {
		ImageIcon originalIcon = new ImageIcon(Images.class.getResource("/images/" + type + ".png"));
		return new ImageIcon(originalIcon.getImage().getScaledInstance(xScale, yScale, Image.SCALE_DEFAULT));
	}
	
}
