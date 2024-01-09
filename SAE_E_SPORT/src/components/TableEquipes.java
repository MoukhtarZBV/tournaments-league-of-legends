package components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import Images.ImagesIcons;
import ihm.Palette;
import ihm.Police;
import modele.Joueur;

public class TableEquipes extends JTable {

	private boolean reversed;
	
	public TableEquipes(boolean reversed) {
		super(5, 3);
		this.reversed = reversed;
		this.setShowGrid(false);
		this.setBorder(null);
		this.setEnabled(false);
		this.setOpaque(false);
		this.setRowHeight(50);
		this.setForeground(Palette.WHITE);
		this.setFont(Police.TABLEAU);	
		this.setIntercellSpacing(new Dimension(0, 6));
		this.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		
		int iconColumn = reversed ? 0 : 2; 
		int emptyColumn = reversed ? 2 : 0;
		String[] headers = reversed ? new String[] {"Role", "Joueur", " "} : new String[] {" ", "Joueur", "Role"};
		DefaultTableModel modeleTableEquipes = new DefaultTableModel(new Object[][] {}, headers) {
	                
				@Override
			    public boolean isCellEditable(int row, int column) {
			       return false;
			    }
				
				public Class getColumnClass(int column) {
	                if (column == iconColumn) {
	                	return ImageIcon.class;
	                } else {
	                	return super.getColumnClass(column);
	                }
	            }
		};
		this.setModel(modeleTableEquipes);
		
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
		
		this.getColumnModel().getColumn(iconColumn).setMaxWidth(100);
		if (reversed) {
			this.getColumnModel().getColumn(1).setCellRenderer(rightRenderer);
		}
		this.getColumnModel().getColumn(emptyColumn).setMaxWidth(15);
	}
	
	public Component prepareRenderer(TableCellRenderer renderer,int Index_row, int Index_col) {
		Component comp = super.prepareRenderer(renderer, Index_row, Index_col);
		if (Index_row % 2 == 0 && !isCellSelected(Index_row, Index_col)) {
			comp.setBackground(Palette.GRAY);
		} else {
			comp.setBackground(Palette.DARK_GRAY);
		}
		return comp;
	}
	
	public void ajouterJoueurs(List<Joueur> joueurs) {
		ImageIcon[] iconesRoles = {ImagesIcons.TOP, ImagesIcons.JUNGLE, ImagesIcons.MID, ImagesIcons.SUPPORT, ImagesIcons.BOTTOM};
		DefaultTableModel modele = (DefaultTableModel) this.getModel();
		for (int indice = 0; indice < joueurs.size(); indice++) {
			if (reversed) {
				modele.addRow(new Object[] { iconesRoles[indice], joueurs.get(indice).getPseudo(), " "});
			} else {
				modele.addRow(new Object[] { " ", joueurs.get(indice).getPseudo(), iconesRoles[indice]});
			}
			
		}
	}
}
