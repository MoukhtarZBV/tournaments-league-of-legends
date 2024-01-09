package components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import Images.ImagesIcons;
import ihm.Palette;
import ihm.Police;
import ihm.VueIdentification;

public class CoolPassword extends JPanel {

	    private Shape shape;
	    
	    private JPasswordField pass;
	    private JButton viewPass;
	    
	    private VueIdentification vue;
	    
	    public CoolPassword(int col, VueIdentification vue) {
	        super();
	        setBackground(Palette.DARK_GRAY);
	        setLayout(new BorderLayout());
	        
	        this.pass = new JPasswordField();
	        this.pass.setOpaque(false);
	        this.pass.setBackground(getBackground());
	        this.pass.setForeground(Palette.WHITE);
	        this.pass.setBorder(BorderFactory.createEmptyBorder(1, 2, 1, 1));
	        this.pass.setFont(Police.INPUT);
	        this.pass.setColumns(col);
	        add(pass, BorderLayout.CENTER);
	        
	        this.viewPass = new JButton();
	        this.viewPass.setOpaque(false);
	        this.viewPass.setBackground(getBackground());
	        this.viewPass.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
	        this.viewPass.setFocusable(false);
	        this.viewPass.setForeground(Palette.LIGHT_GRAY);
	        this.viewPass.setContentAreaFilled(false);
	        this.viewPass.setIcon(ImagesIcons.VIEW_PASS);
	        viewPassAction(this.viewPass, this.pass);
	        add(viewPass, BorderLayout.EAST);
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
	    
	    public JPasswordField getPasswordField() {
	    	return this.pass;
	    }
	    
	    private void viewPassAction(JButton viewPass, JPasswordField pass) {
			this.viewPass.addMouseListener(new MouseListener() {
				boolean isPressed = false;

				@Override
				public void mousePressed(MouseEvent e) {
					isPressed = true;
					viewPass.setIcon(ImagesIcons.VIEW_PASS_HOVER);	
					pass.setEchoChar((char)0);					
				}

				@Override
				public void mouseReleased(MouseEvent e) {
					isPressed = false;
					viewPass.setIcon(ImagesIcons.VIEW_PASS);
					pass.setEchoChar('•');
					
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					viewPass.setIcon(ImagesIcons.VIEW_PASS_HOVER);				
				}

				@Override
				public void mouseExited(MouseEvent e) {
					if(!isPressed) viewPass.setIcon(ImagesIcons.VIEW_PASS);
				}
				
				@Override
				public void mouseClicked(MouseEvent e) {}
	        });
		}
	
}
