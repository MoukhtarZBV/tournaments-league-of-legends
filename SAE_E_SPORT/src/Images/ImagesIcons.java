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

public class ImagesIcons {

	public static final ImageIcon BULLE_PAYS = ImagesIcons.getIcon(25, 25, "infoEarth");
	public static final ImageIcon BULLE_NIVEAU = ImagesIcons.getIcon(25, 25, "infoLevel");
	public static final ImageIcon BULLE_EQUIPES = ImagesIcons.getIcon(25, 25, "infoTeam");
	public static final ImageIcon BULLE_DATES = ImagesIcons.getIcon(25, 25, "infoCalendar");
	public static final ImageIcon BULLE_VAINQUEUR = ImagesIcons.getIcon(25, 25, "infoTrophy");
	public static final ImageIcon TOP = ImagesIcons.getIcon(25, 25, "topRole");
	public static final ImageIcon JUNGLE = ImagesIcons.getIcon(25, 25, "junglerRole");
	public static final ImageIcon MID = ImagesIcons.getIcon(25, 25, "midRole");
	public static final ImageIcon SUPPORT = ImagesIcons.getIcon(25, 25, "supportRole");
	public static final ImageIcon BOTTOM = ImagesIcons.getIcon(25, 25, "bottomRole");
	public static final String TROPHY = "/Images/trophy.png";
	public static final String TROPHY_WIN = "/Images/trophyWin.png";
	public static final ImageIcon UPLOAD = ImagesIcons.getIcon(100, 100, "upload");
	
	public static final ImageIcon EQUIPE = ImagesIcons.getIcon(128, 128, "imgEquipe");
	public static final ImageIcon TOURNOI = ImagesIcons.getIcon(128, 128, "imgTournoi");
	public static final ImageIcon HISTORIQUE = ImagesIcons.getIcon(128, 128, "imgHistorique");
	public static final ImageIcon ARBITRE = ImagesIcons.getIcon(128, 128, "imgArbitres");
	
	public static final ImageIcon LOGIN = ImagesIcons.getIcon(25, 25, "login");
	public static final ImageIcon PASSWORD = ImagesIcons.getIcon(25, 25, "password");
	public static final ImageIcon VIEW_PASS = ImagesIcons.getIcon(25, 25, "viewPass");
	public static final ImageIcon VIEW_PASS_HOVER = ImagesIcons.getIcon(25, 25, "viewPassHover");
	
	// Drapeaux
	public static final ImageIcon FRANCE = ImagesIcons.getIcon(40, 40, "drapeaux/france");
	public static final ImageIcon UKRAINE = ImagesIcons.getIcon(40, 40, "drapeaux/ukraine");
	
	private static ImageIcon getIcon(int xScale, int yScale, String type) {
		ImageIcon originalIcon = new ImageIcon(ImagesIcons.class.getResource("/images/" + type + ".png"));
		return new ImageIcon(originalIcon.getImage().getScaledInstance(xScale, yScale, Image.SCALE_DEFAULT));
	}
	
}
