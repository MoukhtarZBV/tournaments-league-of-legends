package components;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JScrollBar;

import ihm.Palette;

public class CoolScrollBar extends JScrollBar {
	
	public CoolScrollBar() {
		super();
		
		this.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Palette.DARK_GRAY));
		this.setPreferredSize(new Dimension(15, this.getPreferredSize().height));
		this.setFocusable(false);
		this.setBackground(Palette.DARK_GRAY);
		
		this.setUI(new CoolScrollBarUI());
		
	}

}



