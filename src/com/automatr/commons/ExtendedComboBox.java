package com.automatr.commons;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;

public class ExtendedComboBox extends JComboBox<Object> {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExtendedComboBox() {
        setModel(new ExtendedComboBoxModel());
        setRenderer(new ExtendedListCellRenderer());
    }

    public void addDelimiter(String text) {
        this.addItem(new Delimiter(text));
    }

    private static class ExtendedComboBoxModel extends DefaultComboBoxModel<Object> {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
        public void setSelectedItem(Object anObject) {
            if (!(anObject instanceof Delimiter)) {
                super.setSelectedItem(anObject);
            } else {
                int index = getIndexOf(anObject);
                if (index < getSize()) {
                    setSelectedItem(null);
                }
            }
        }

    }

    private static class ExtendedListCellRenderer extends DefaultListCellRenderer {

        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            if (!(value instanceof Delimiter)) {
                return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            } else {
                JLabel label = new JLabel(value.toString());
                Font f = label.getFont();
                label.setFont(f.deriveFont(f.getStyle() | Font.BOLD | Font.ITALIC));
                label.setForeground(Color.RED);
                return label;
            }
        }
    }

    private static class Delimiter {
        private String text;

        private Delimiter(String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text.toString();
        }
    }
}
