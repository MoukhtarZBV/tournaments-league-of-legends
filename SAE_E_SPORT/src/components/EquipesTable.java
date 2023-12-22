package components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import ihm.Palette;
import ihm.Police;

public class EquipesTable extends JTable {

	public EquipesTable(int rows, int columns) {
		super(rows, columns);
		this.setShowGrid(false);
		this.setBorder(null);
		this.setEnabled(false);
		this.setOpaque(false);
		this.setRowHeight(50);
		this.setForeground(Palette.WHITE);
		this.setFont(Police.TABLEAU);	
		this.setIntercellSpacing(new Dimension(0, 6));
		this.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		this.getColumnModel().getColumn(0).setMaxWidth(75);
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
}
