package controleur;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextField;

import ihm.Ecran;
import ihm.Palette;
import ihm.VueAccueilAdmin;
import ihm.VueHistoriquePoints;
import modele.Equipe;
import modele.ModeleHistoriquePoints;
import modele.Tournoi;

public class ControleurHistoriquePoints extends MouseAdapter implements FocusListener, ActionListener, MouseListener, WindowListener {

	private VueHistoriquePoints vue;
	private ModeleHistoriquePoints modele;
	
	public ControleurHistoriquePoints(VueHistoriquePoints vue) {
		this.vue = vue;
		this.modele = new ModeleHistoriquePoints();
		this.vue.setTableEquipes(this.modele.getEquipes());
	}

	@Override
    public void focusGained(FocusEvent e) {
		if (e.getSource() instanceof JTextField) {
			JTextField searchText = (JTextField) e.getSource();
			if (searchText.getText().equals("Filtrer équipes par nom")) {
	            searchText.setText("");
	            searchText.setForeground(Palette.WHITE);
	        }
		}
    }
	
    @Override
    public void focusLost(FocusEvent e) {
		if (e.getSource() instanceof JTextField) {
	    	JTextField searchText = (JTextField) e.getSource();
	        if (searchText.getText().isEmpty()) {
	            searchText.setForeground(Color.LIGHT_GRAY);
	            searchText.setText("Filtrer équipes par nom");
	        }
		}
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JTextField) {
			JTextField tf = (JTextField) e.getSource();
			String text = tf.getText();
			
			recherche(text);
		} else if (e.getSource() instanceof JButton) {
			JButton b = (JButton) e.getSource();
			if(b.getName().equals("Rechercher")) {
				String text = this.vue.getSearchBar().getText();
				if(text.equals("Filtrer équipes par nom")) text = "";
				recherche(text);
			} else {
				Ecran.update(this.vue);
				VueAccueilAdmin frame = new VueAccueilAdmin();
				frame.setVisible(true);
			}
		}
	}
	
	@Override 
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount()==2 && e.getSource() instanceof JTable && ((JTable)e.getSource()).getName().equals("equipesTable")){
			JTable table = (JTable) e.getSource();
			int row = table.getSelectedRow();
			Equipe eq = this.modele.getFilteredEquipes().get(row);	
			this.vue.viderColonneTableTournoi();
			
			Map<Tournoi, Integer> points = this.modele.pointsTournoiParEquipe(eq);
			this.vue.setTableTournoi(points);
			boolean visible = (points.size() == 0) ? false : true;
			this.vue.showTournoiHeader(visible);
		}
	}
	
	
	private void recherche(String text) {
		this.vue.viderColonneTableEquipe();
		if (!text.equals("")) {
			this.modele.filterEquipes(text);
			List<Equipe> filEquipes = this.modele.getFilteredEquipes();
			if (filEquipes.size()==0) {
				this.vue.setResultat(true);
				this.vue.viderColonneTableTournoi();
			} else {
				this.vue.setResultat(false);
				this.vue.setTableEquipes(filEquipes);
				this.vue.viderColonneTableTournoi();
			}
		} else {
			this.modele.resetEquipes();
			this.vue.setResultat(false);
			this.vue.viderColonneTableTournoi();
			this.vue.setTableEquipes(this.modele.getEquipes());
		}
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		if(e.getSource() instanceof JButton) {
			JButton b = (JButton)e.getSource();
			
			if(b.getName().equals("Rechercher")) {
				b.setBackground(Palette.LIGHT_PURPLE);	
			} else {
				b.setBackground(b.getBackground().brighter());
			}
			
			b.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
		if(e.getSource() instanceof JButton) {
			JButton b = (JButton)e.getSource();
			
			if(b.getName().equals("Rechercher")) {
				b.setBackground(Palette.WHITE);	
			} else {
				b.setBackground(b.getBackground().darker());
			}
			
			b.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
	}
	
	@Override
	public void windowOpened(WindowEvent e) {
		Ecran.closeLast();
	}
	
	
	// NOT IMPLEMENTED \\

	@Override
	public void windowClosing(WindowEvent e) {}

	@Override
	public void windowClosed(WindowEvent e) {}

	@Override
	public void windowIconified(WindowEvent e) {}

	@Override
	public void windowDeiconified(WindowEvent e) {}

	@Override
	public void windowActivated(WindowEvent e) {}

	@Override
	public void windowDeactivated(WindowEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}
}
