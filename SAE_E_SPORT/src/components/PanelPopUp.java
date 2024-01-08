package components;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import ihm.Palette;
import ihm.Police;

public class PanelPopUp extends JPanel {
	
	private JLabel texte;
	
	// Lors de la création de la popup, il est invisible.
	public PanelPopUp() {
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		this.setFocusable(false);
		
		this.texte = new JLabel(""); 
		this.texte.setFont(Police.POPUPS);
		this.texte.setHorizontalAlignment(SwingConstants.LEFT);
		this.texte.setBorder(new EmptyBorder(5, 10, 5, 0));
		this.add(texte);

			
		this.setEnabled(false);
	}
	
	
	public void setErreur(String texte) {
		this.setOpaque(false);
		this.setBackground(Palette.FOND_ERREUR);
		this.setBorder(BorderFactory.createMatteBorder(0, 5, 0, 0, Palette.ERREUR));
		
		this.texte.setForeground(Palette.ERREUR);
		this.texte.setText(texte);
		
		this.setOpaque(true);
		this.setEnabled(true);
		this.setVisible(true);
	}
	
	public void setSucces(String texte) {
		this.setOpaque(false);
		this.setBackground(Palette.FOND_SUCCES);
		this.setBorder(BorderFactory.createMatteBorder(0, 5, 0, 0, Palette.SUCCES));
		
		this.texte.setForeground(Palette.SUCCES);
		this.texte.setText(texte);
		

		this.setOpaque(true);
		this.setEnabled(true);
		this.setVisible(true);
	}
	
	public void setNormal(String texte) {
		this.setOpaque(true);
		this.setBackground(Palette.DARK_GRAY);
		this.setBorder(BorderFactory.createMatteBorder(0, 5, 0, 0, Palette.WHITE));
		
		this.texte.setForeground(Palette.WHITE);
		this.texte.setText(texte);
		
		this.setVisible(true);
	}

}
