package com.automatr.commons;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

public class TableUtils {
	public static String[] getColumnData(TableModel model, int colIndex) {
		ArrayList<String> dataList = new ArrayList<String>();
		int maxRows = model.getRowCount();
		for (int i = 0; i < maxRows; i++) {
			String cellValue = (String)model.getValueAt(i, colIndex);
			if( cellValue != null && !dataList.contains(cellValue) && !cellValue.equals("")) {
				dataList.add(cellValue);
			}
		}
		Collections.sort(dataList);
		return dataList.toArray(new String[dataList.size()]);
	}
	public static class CustomRenderer extends DefaultTableCellRenderer {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public Component getTableCellRendererComponent(JTable table, Object value, boolean   isSelected, boolean hasFocus, int row, int column) 
		{ 
		    Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); 

		    c.setBackground(Color.lightGray);

		    return c; 
		} 
	}
}
