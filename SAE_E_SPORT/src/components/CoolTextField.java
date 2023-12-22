package components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

import javax.swing.JTextField;

import ihm.Palette;
import ihm.Police;

public class CoolTextField extends JTextField {

	    private Shape shape;
	    
	    public CoolTextField() {
	        super();
	        setOpaque(false);
	        setFont(Police.INPUT);
	        setForeground(Palette.WHITE);
	        setBackground(Palette.DARK_GRAY);
	    }
	    
	    protected void paintComponent(Graphics g) {
	         g.setColor(getBackground());
	         g.fillRect(0, 0, getWidth()-1, getHeight()-1);
	         super.paintComponent(g);
	    }
	    
	    protected void paintBorder(Graphics g) {
	         g.setColor(Color.WHITE);
	         g.drawLine(0, getHeight()-1, getWidth()-2, getHeight()-1);
	    }
	    
	    public boolean contains(int x, int y) {
	         if (shape == null || !shape.getBounds().equals(getBounds())) {
	             shape = new Rectangle2D.Float(0, 0, getWidth()-1, getHeight()-1);
	         }
	         return shape.contains(x, y);
	    }
	
}
