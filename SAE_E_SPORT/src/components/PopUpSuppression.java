package components;

import java.awt.Component;
import java.awt.HeadlessException;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Images.ImagesIcons;
import ihm.Palette;

public class PopUpSuppression extends JOptionPane{

	public PopUpSuppression(Object message, int messageType, int optionType,
            Icon icon, Object[] options, Object initialValue) {
		super(message, messageType, optionType, icon, options, initialValue);
	}
	
	public static int afficherPopUpConfirmation(Object message, String title, int optionType, int messageType,
	        Icon icon, Object[] options, Object initialValue) {
		return showOptionDialog(getPanel(), message, title, optionType, messageType, icon, options, initialValue);
	}
	
	private static JPanel getPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Palette.DARK_GRAY);
        JLabel label = new JLabel("Java Technology Dive Log");
        ImageIcon image = ImagesIcons.HISTORIQUE;

        label.setIcon(image);
        panel.add(label);

        return panel;
    }
}
