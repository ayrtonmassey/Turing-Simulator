package turing.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import turing.interfaces.GUI;

public class TapePanel extends JPanel {

	public static final Font TAPE_FONT = new Font("Arial", Font.BOLD, 24);
	public static final int TAPE_CELLS_TO_DISPLAY=10;
	
	
	GUI gui;
	
	JTable table;
	
	/**
	 * Creates a new TapePanel.
	 * @param gui The GUI for the Turing machine simulator.
	 */
	public TapePanel(GUI gui)
	{
		this.gui = gui;
		init();
	}
	
	/**
	 * Initialises the components and layout of this panel.
	 * @see #initComponents()
	 */
	private void init()
	{	
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBackground(Color.blue);
		initComponents();
	}
	
	/**
	 * Initialises the components of this panel.
	 * @see TapeCellEditor
	 * @see TapeModel
	 */
	public void initComponents()
	{
		this.setMinimumSize(new Dimension(TAPE_CELLS_TO_DISPLAY*TAPE_FONT.getSize()*2,TAPE_FONT.getSize()*2));
		this.setPreferredSize(new Dimension(TAPE_CELLS_TO_DISPLAY*TAPE_FONT.getSize()*2,TAPE_FONT.getSize()*2));
		this.setMaximumSize(new Dimension(TAPE_CELLS_TO_DISPLAY*TAPE_FONT.getSize()*2,TAPE_FONT.getSize()*2));
		
		///*
		table = new JTable(new TapeModel(gui));
		
			//Cell Editor
		
			JTextField tapeCellEditorField = new JTextField();
				tapeCellEditorField.setFont(TAPE_FONT);
				tapeCellEditorField.setHorizontalAlignment(SwingConstants.CENTER);
			table.setDefaultEditor(Object.class, new TapeCellEditor(tapeCellEditorField,gui));
			
			//Cell Renderer
			
			tapeCellEditorField.setBorder(BorderFactory.createEmptyBorder());
				DefaultTableCellRenderer tapeCellRenderer = new DefaultTableCellRenderer();
				tapeCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
			table.setDefaultRenderer(Object.class, tapeCellRenderer);
			
			//Table Style
			
			table.setFont(TAPE_FONT);
			
			table.setRowHeight(TAPE_FONT.getSize()*2);
			for(int i=0;i<table.getColumnCount();i++)
			{
				table.getColumnModel().getColumn(i).setMaxWidth(TAPE_FONT.getSize()*2);
			}
			
			table.setBorder(BorderFactory.createLineBorder(new Color(128,169,255), 1));
			
				//Selection Style
			
				table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				table.setSelectionBackground(Color.WHITE);
			
			//Miscellaneous
				
			table.setSurrendersFocusOnKeystroke(true);
		//*/
			
		this.add(table.getTableHeader());
		this.add(table);
	}
}
