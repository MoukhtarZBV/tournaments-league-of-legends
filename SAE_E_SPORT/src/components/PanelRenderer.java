package components;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class PanelRenderer extends DefaultTableCellRenderer{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
	
		@Override
		public Component getTableCellRendererComponent(
	            JTable table, Object panel, boolean isSelected, boolean hasFocus,int row, int column) {
			if (panel instanceof Component) {
				return (Component) panel;
			}
			return super.getTableCellRendererComponent(table, panel, isSelected, hasFocus, row, column);
		}
}
