package com.automatr.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractCellEditor;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import net.miginfocom.swing.MigLayout;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.event.ListDataListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeSelectionEvent;

import com.automatr.commons.ExtendedComboBox;
import com.automatr.commons.TableUtils;

public class Main {

	private JFrame frmAutomatr;
	private JTable testDataTable;
	private JTable objRepoTable;
	private JTable testScriptTable;
	private String[] LOCATING_METHODS = new String[]{"CSS", "ID", "LINK_TEXT", "NAME", "PARTIAL_LINK_TEXT", "TAG_NAME", "XPATH"};
	private String[] GENERAL_ACTION_LIST = new String[] {
		"WAIT",
		"JAVASCRIPT",
		"RUN",
		"SWITCH_TO",
		"SWITCH_TO_PARENT",
		"EVALUATE"
	};
	private String[] WEB_ELEMENT_ACTION_LIST = new String[] {
		"INPUT",
		"CLICK",
		"RIGHT_CLICK",
		"CLEAR",
		"SEND",
		"HOVER",
		"COUNT",
		"IS_DISPLAYED",
		"IS_EMPTY",
		"IS_NOT_DISPLAYED",
		"IS_CHECKED",
		"IS_NOT_CHECKED",
		"SELECT_BY_TEXT",
		"SELECT_BY_VALUE",
		"SELEC_BY_INDEX",
		"GET_ATTRIBUTE"
	};
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frmAutomatr.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmAutomatr = new JFrame();
		frmAutomatr.setTitle("Automatr");
		CustomRenderer customCR = new CustomRenderer();
		customCR.setHorizontalAlignment(JLabel.RIGHT);
		
		frmAutomatr.setBounds(100, 100, 780, 600);
		frmAutomatr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frmAutomatr.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmNew = new JMenuItem("New");
		mnFile.add(mntmNew);
		
		JMenuItem mntmOpen = new JMenuItem("Open");
		mnFile.add(mntmOpen);
		
		JMenuItem mntmSave = new JMenuItem("Save");
		mnFile.add(mntmSave);
		
		JMenuItem mntmQuit = new JMenuItem("Quit");
		mntmQuit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				frmAutomatr.dispose();
			}
		});
		mnFile.add(mntmQuit);
		
		JMenu mnOptions = new JMenu("Options");
		menuBar.add(mnOptions);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		frmAutomatr.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		frmAutomatr.getContentPane().add(tabbedPane);
		
		JScrollPane testDataPane = new JScrollPane();
		testDataPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		tabbedPane.addTab("Test Data", null, testDataPane, null);
		
		// ================================================================================
		// TEST DATA TABLE
		// ================================================================================
			testDataTable = new JTable();
			DefaultTableModel testDataDM = new DefaultTableModel(0, 0) {
				boolean[] columnEditables = new boolean[] {
						false, true, true, true
				};
				@Override 
				public boolean isCellEditable(int row, int column)
			    {
					return columnEditables[column];
			    }
			};
			
			// Table header
			String testDataHeaders[] = new String[] {"#", "Name", "Value", "Additional Information"};
			testDataDM.setColumnIdentifiers(testDataHeaders);
			testDataTable.setModel(testDataDM);
			for (int i = 1; i < 101; i++) {
				testDataDM.addRow(new Object[] { new Integer(i), null, null, null });
			}
			testDataTable.setColumnSelectionAllowed(true);
			testDataTable.setRowHeight(20);
			testDataTable.getColumnModel().getColumn(0).setPreferredWidth(35);
			testDataTable.getColumnModel().getColumn(0).setMinWidth(35);
			testDataTable.getColumnModel().getColumn(0).setMaxWidth(65);
			testDataTable.getColumnModel().getColumn(0).setCellRenderer(new TableUtils.CustomRenderer());
			testDataTable.getColumnModel().getColumn(1).setPreferredWidth(150);
			testDataTable.getColumnModel().getColumn(1).setMinWidth(150);
			testDataTable.getColumnModel().getColumn(2).setPreferredWidth(150);
			testDataTable.getColumnModel().getColumn(2).setMinWidth(150);
			testDataTable.getColumnModel().getColumn(3).setPreferredWidth(200);
			testDataTable.getColumnModel().getColumn(3).setMinWidth(150);
			testDataPane.setViewportView(testDataTable);
			
			// ================================================================================
			// MODEL LISTENERS
			// ================================================================================
			testDataDM.addTableModelListener(new TableModelListener() {
				
				@Override
				public void tableChanged(TableModelEvent evt) {
					if( evt.getColumn() == 1 ) {
						testScriptTable.getColumnModel().getColumn(5).setCellEditor(new CustomCellEditor(
								TableUtils.getColumnData(testDataTable.getModel(), 1)
							));
					}
					
				}
			});
			
		// ================================================================================
		// END OF TEST DATA TABLE
		// ================================================================================
		
		JScrollPane objRepoPane = new JScrollPane();
		objRepoPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		tabbedPane.addTab("Object Repository", null, objRepoPane, null);
		
		objRepoTable = new JTable();
		
		
		
		final DefaultTableModel objRepoDM = new DefaultTableModel(0, 0) {
			boolean[] columnEditables = new boolean[] {
					false, true, true, true, true, true
			};
			@Override 
			public boolean isCellEditable(int row, int column)
		    {
				return columnEditables[column];
		    }
		};
		
		// Table header
		String objRepoHeaders[] = new String[] {"#", "Repository Name", "Element Name", "Locator Type", "Locator Value", "Additional Information"};
		objRepoDM.setColumnIdentifiers(objRepoHeaders);
		
		objRepoTable.setModel(objRepoDM);
		for (int i = 1; i < 101; i++) {
			objRepoDM.addRow(new Object[] { new Integer(i), null, null, null });
		}
		
		objRepoDM.addTableModelListener(new TableModelListener() {
			public void tableChanged(TableModelEvent evt) {
				switch(evt.getColumn()) {
					case 1:
						testScriptTable.getColumnModel().getColumn(3).setCellEditor(new CustomCellEditor(
								TableUtils.getColumnData(objRepoTable.getModel(), 1)
								));
						break;
					case 2:
						testScriptTable.getColumnModel().getColumn(4).setCellEditor(new CustomCellEditor(
								TableUtils.getColumnData(objRepoTable.getModel(), 2)
								));
						break;
				}
			}
		});
		
		objRepoTable.setColumnSelectionAllowed(true);
		objRepoTable.setRowHeight(20);
		objRepoTable.getColumnModel().getColumn(0).setPreferredWidth(35);
		objRepoTable.getColumnModel().getColumn(0).setMinWidth(35);
		objRepoTable.getColumnModel().getColumn(0).setMaxWidth(65);
		objRepoTable.getColumnModel().getColumn(0).setCellRenderer(customCR);
		objRepoTable.getColumnModel().getColumn(1).setPreferredWidth(200);
		objRepoTable.getColumnModel().getColumn(2).setPreferredWidth(200);
		objRepoTable.getColumnModel().getColumn(3).setPreferredWidth(150);
		objRepoTable.getColumnModel().getColumn(3).setCellEditor(new CustomCellEditor(LOCATING_METHODS));
		objRepoTable.getColumnModel().getColumn(4).setPreferredWidth(250);
		objRepoTable.getColumnModel().getColumn(5).setPreferredWidth(200);
		
		
		objRepoPane.setViewportView(objRepoTable);
		
		
		
	// ====================================================================
	// TEST SCRIPT PANE
	// ====================================================================
		// DECLARATIONS
		JScrollPane testScriptPane = new JScrollPane();
		JPanel panel = new JPanel();
		JToolBar toolBar = new JToolBar();
		final JButton btnNewModule = new JButton("New Module");
		final JButton btnNewTestCase = new JButton("New Test Case");
		final JButton btnDelete = new JButton("Delete");
		final JTree tree = new JTree();
		final DefaultMutableTreeNode root = new DefaultMutableTreeNode("Modules");
		final DefaultTreeModel treeModel = new DefaultTreeModel(root);
		testScriptTable = new JTable();
		DefaultTableModel testScriptModel;
		final JScrollPane testScriptScroll;
		
		// ====================================================================
		// PANE
		// ====================================================================
		
		tabbedPane.addTab("Test Scripts", null, testScriptPane, null);
		
		// ====================================================================
		// END OF PANE
		// ====================================================================
		
		// ====================================================================
		// PANEL
		// ====================================================================
		
		testScriptPane.setViewportView(panel);
		panel.setLayout(new MigLayout("", "[][][][grow][][][][][][][][][][][][][][][][][][][][][]", "[][grow]"));
		panel.add(toolBar, "cell 0 0 24 1");
		
		// ====================================================================
		// END OF PANEL
		// ====================================================================
		
		// ====================================================================
		// TOOLBAR
		// ====================================================================
		
		toolBar.setBorder(null);
		btnNewTestCase.setEnabled(false);
		btnDelete.setEnabled(false);
		
		toolBar.add(btnNewModule);
		toolBar.add(btnNewTestCase);
		toolBar.add(btnDelete);
		
			// ====================================================================
			// TOOL BAR BUTTON EVENTS
			// ====================================================================
			btnNewModule.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					if( btnNewModule.isEnabled() ) {
						String newModule = JOptionPane.showInputDialog(frmAutomatr, "Please provide the name of the module:");
						if( newModule != null ) {
							DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(newModule);
						    treeModel.insertNodeInto(childNode, root, root.getChildCount());
					        tree.scrollPathToVisible(new TreePath(childNode.getPath()));
						}
					}
				}
			});
			btnNewTestCase.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent evt) {
					if( btnNewTestCase.isEnabled() ) {
						String newTestCase = JOptionPane.showInputDialog(frmAutomatr, "Please provide the name of the test case:");
						if( newTestCase != null ) {
							DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(newTestCase);
							DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)tree.getSelectionPath().getLastPathComponent();
						    treeModel.insertNodeInto(childNode, selectedNode, selectedNode.getChildCount());
					        tree.scrollPathToVisible(new TreePath(childNode.getPath()));
						}
					}
				}
			});
			btnDelete.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent evt) {
					if( btnDelete.isEnabled() ) {
						DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)tree.getSelectionPath().getLastPathComponent();
						int confirmation = JOptionPane.showConfirmDialog(frmAutomatr, "Are you sure you want to delete '" + selectedNode + "'?");
						if( confirmation == 0 ) {
							selectedNode.removeFromParent();
							DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
							model.reload(root);
							btnDelete.setEnabled(false);
							btnNewTestCase.setEnabled(false);
						}
					}
				}
			});
			
			// ================================================================================
			// END OF BUTTON EVENTS
			// ================================================================================
			
		// ================================================================================
		// END OF TOOLBAR
		// ================================================================================
		
		// ================================================================================
		// TREE
		// ================================================================================
		
			tree.setModel(treeModel);
			JScrollPane treePane = new JScrollPane(tree);
			panel.add(treePane, "cell 1 1 2 1, grow, width 250:250:250");
			
			
		
		// ================================================================================
		// END OF TREE
		// ================================================================================
		
		
		// ================================================================================
		// TEST SCRIPTS TABLE
		// ================================================================================
		testScriptModel = new DefaultTableModel(0, 0) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] {
					false, true, true, true, true, true, true, true
			};
			public boolean isCellEditable(int row, int column)
		    {
				return columnEditables[column];
		    }
		};
		
		String testScriptHeaders[] = new String[] {"#", "Step Name", "Action", "Repository Name", "Element Name", "Test Data", "Misc Params 1", "Misc Params 2"};
		testScriptModel.setColumnIdentifiers(testScriptHeaders);
		testScriptTable.setModel(testScriptModel);
		
		for (int i = 1; i < 101; i++) {
			testScriptModel.addRow(new Object[] { new Integer(i), null, null, null, null, null, null, null });
		}
		
		// COLUMN SETTINGS
		testScriptTable.setColumnSelectionAllowed(true);
		testScriptTable.setRowHeight(20);
		testScriptTable.getColumnModel().getColumn(0).setPreferredWidth(35);
		testScriptTable.getColumnModel().getColumn(0).setMinWidth(35);
		testScriptTable.getColumnModel().getColumn(0).setMaxWidth(65);
		testScriptTable.getColumnModel().getColumn(0).setCellRenderer(customCR);
		testScriptTable.getColumnModel().getColumn(1).setPreferredWidth(200);
		
		testScriptTable.getColumnModel().getColumn(2).setPreferredWidth(200);
		
		CustomCellEditor actionOptions = new CustomCellEditor();
		actionOptions.appendOptions(GENERAL_ACTION_LIST, "General Functions");
		actionOptions.appendOptions(WEB_ELEMENT_ACTION_LIST, "Web Related Functions");
		testScriptTable.getColumnModel().getColumn(2).setCellEditor(actionOptions);
		
		testScriptTable.getColumnModel().getColumn(3).setPreferredWidth(150);
		testScriptTable.getColumnModel().getColumn(3).setCellEditor(new CustomCellEditor());
		testScriptTable.getColumnModel().getColumn(4).setPreferredWidth(250);
		testScriptTable.getColumnModel().getColumn(4).setCellEditor(new CustomCellEditor());
		testScriptTable.getColumnModel().getColumn(5).setPreferredWidth(200);
		testScriptTable.getColumnModel().getColumn(5).setCellEditor(new CustomCellEditor());
		
		testScriptScroll = new JScrollPane(testScriptTable);
		testScriptScroll.setVisible(false);
		panel.add(testScriptScroll, "cell 3 1 22 1,grow");
		
		testScriptModel.addTableModelListener(new TableModelListener()  {
			@Override
			public void tableChanged(TableModelEvent evt) {
				
			}
		});
		// ================================================================================
		// END OF TEST SCRIPTS TABLE
		// ================================================================================
		
		// ================================================================================
		// TREE EVENTS
		// ================================================================================
			tree.addTreeSelectionListener(new TreeSelectionListener() {
				public void valueChanged(TreeSelectionEvent evt) {
					// Enable 'Add Test Case Button'
					if( evt.getPath().getPathCount() == 2 ) {
						btnNewTestCase.setEnabled(true);
						btnDelete.setEnabled(true);
					}
					else {
						btnNewTestCase.setEnabled(false);
					}
					if( evt.getPath().getPathCount() > 1 ) {
						btnDelete.setEnabled(true);
					}
					else {
						btnDelete.setEnabled(false);
					}
					// Show test script screen
					if( evt.getPath().getPathCount() == 3 ) {
						testScriptScroll.setVisible(true);
					} else {
						testScriptScroll.setVisible(false);
					}
				}
			});
			tree.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent evt) {
					
					// Update Node Name
					if (evt.getClickCount() == 2) {
						DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)tree.getSelectionPath().getLastPathComponent();
						if( selectedNode.getPath().length > 1 ) {
							String editTestCase = JOptionPane.showInputDialog(frmAutomatr, "Update '" + selectedNode + "' to: ");
							if( editTestCase != null ) 
								selectedNode.setUserObject(editTestCase);
						}
					}
			    }
			});
		// ================================================================================
		// END OF TREE EVENTS
		// ================================================================================
	}
	
	
}

class CustomRenderer extends DefaultTableCellRenderer {
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


class CustomCellEditor extends AbstractCellEditor implements TableCellEditor  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6696922801539405570L;
	private ExtendedComboBox editor;
	private String DEFAULT_OPTION = " - Select an option - ";
	public CustomCellEditor() {
		editor = new ExtendedComboBox();
		editor.addDelimiter(DEFAULT_OPTION);
	}
	public CustomCellEditor(String[] values)
    {
	    editor = new ExtendedComboBox();
	    editor.addDelimiter(DEFAULT_OPTION);
	    
	    for (String item : values) {
			editor.addItem(item);
		}
    }
	
	public void appendOptions(String[] values, String delimiter) {
		editor.addDelimiter(delimiter);
		for (String item : values) {
			editor.addItem(item);
		}
	}

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int rowIndex, int colIndex) 
    {
    	// Set the model data of the table
	    if(!isSelected)
	    {
		    editor.setSelectedItem(value);
		    TableModel model = table.getModel();
		    model.setValueAt(value, rowIndex, colIndex);
	    }
	
	    return editor;
    }

    public Object getCellEditorValue() 
    {
    	return editor.getSelectedItem();
    }
}
