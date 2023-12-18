package ihm;

import java.awt.Font;

public class Police {
	
	// Titres
	public static final Font GROS_TITRE   = new Font("DejaVu Sans", Font.BOLD | Font.ITALIC, 40);	// Gros titre de fou
	public static final Font SOUS_TITRE   = new Font("Bahnschrift", Font.PLAIN, 23);				// Sous titres
	
	// Textes basiques
	public static final Font LABEL        = new Font("Tahoma",      Font.PLAIN, 20);				// Tous les labels et boutons etc
	public static final Font POPUPS       = new Font("Tahoma",      Font.BOLD,  14);				// Message des popup de réussite et echecs

	public static final Font INFO         = new Font("DejaVu Sans", Font.PLAIN, 12);				// Texte des info bulles
	
	// Components swing
	public static final Font INPUT        = new Font("Tahoma",      Font.PLAIN, 20);				// JTextField
	public static final Font TABLEAU_MONO = new Font("Consolas",    Font.PLAIN, 20);				// JList avec une police mono
	public static final Font TABLEAU      = new Font("Tahoma",      Font.PLAIN, 17);				// JTable dans les cellules
	public static final Font COMBO        = new Font("Tahoma",      Font.PLAIN, 18);				// JComboBox
	
	// à suppr plus tard
	public static final Font LIGNE        = new Font("Tahoma",      Font.PLAIN,  5);
}