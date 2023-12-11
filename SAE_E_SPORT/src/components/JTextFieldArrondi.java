package components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JTextField;

public class JTextFieldArrondi extends JTextField {

	    private Shape shape;
	    
	    public JTextFieldArrondi() {
	        super();
	        setOpaque(false); // As suggested by @AVD in comment.
	    }
	    
	    protected void paintComponent(Graphics g) {
	         g.setColor(getBackground());
	         g.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 10, 10);
	         super.paintComponent(g);
	    }
	    
	    protected void paintBorder(Graphics g) {
	         g.setColor(Color.WHITE);
	         g.drawLine(3, getHeight()-1, getWidth()-3, getHeight()-1);
	    }
	    
	    public boolean contains(int x, int y) {
	         if (shape == null || !shape.getBounds().equals(getBounds())) {
	             shape = new RoundRectangle2D.Float(0, 0, getWidth()-1, getHeight()-1, 15, 15);
	         }
	         return shape.contains(x, y);
	    }
	
}
