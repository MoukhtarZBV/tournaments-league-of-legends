package controleur;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextField;

import ihm.VueAccueilAdmin;
import ihm.VueHistoriquePoints;
import modele.Equipe;
import modele.ModeleHistoriquePoints;
import modele.Tournoi;

public class ControleurHistoriquePoints extends MouseAdapter implements FocusListener, ActionListener {

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
	            searchText.setForeground(Color.BLACK);
	        }
		}
    }
	
    @Override
    public void focusLost(FocusEvent e) {
		if (e.getSource() instanceof JTextField) {
	    	JTextField searchText = (JTextField) e.getSource();
	        if (searchText.getText().isEmpty()) {
	            searchText.setForeground(Color.GRAY);
	            searchText.setText("Filtrer équipes par nom");
	        }
		}
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JTextField) {
			JTextField tf = (JTextField) e.getSource();
			String text = tf.getText();
			this.vue.viderColonneTableEquipe();
			if (!text.equals("")) {
				this.modele.filterEquipes(text);
				List<Equipe> filEquipes = this.modele.getFilteredEquipes();
				if (filEquipes.size()==0) {
					this.vue.setResultat(true);
					this.vue.viderColonneTableTournoi();
				} else {
					this.vue.addColumnTableEquipe();
					this.vue.setResultat(false);
					this.vue.setTableEquipes(filEquipes);
					this.vue.viderColonneTableTournoi();
				}
			} else {
				this.modele.resetEquipes();
				this.vue.addColumnTableEquipe();
				this.vue.setResultat(false);
				this.vue.viderColonneTableTournoi();
				this.vue.setTableEquipes(this.modele.getEquipes());
			}
		} else if (e.getSource() instanceof JButton) {
			VueAccueilAdmin frame = new VueAccueilAdmin();
			frame.setVisible(true);
			this.vue.dispose();
		}
	}
	
	@Override 
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount()==2 && e.getSource() instanceof JTable && ((JTable)e.getSource()).getName().equals("equipesTable")){
			JTable table = (JTable) e.getSource();
			int row = table.getSelectedRow();
			Equipe eq = this.modele.getFilteredEquipes().get(row);	
			this.vue.viderColonneTableTournoi();
			this.vue.addColumnTableTournoi();
			this.vue.setTableTournoi(this.modele.pointsTournoiParEquipe(eq));
		}
	}
	
}
