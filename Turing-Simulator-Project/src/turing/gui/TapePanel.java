package turing.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;

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

	public static final int TAPE_CELLS_TO_DISPLAY=10;
	
	GUI gui;
	
	JTable table;
	TapeHead tapeHead;
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
		initComponents();
		
		if(gui.debugMode())
		{
			this.setBackground(Color.RED);
		}
	}
	
	/**
	 * Initialises the components of this panel.
	 * @see TapeCellEditor
	 * @see TapeModel
	 */
	private void initComponents()
	{
		int w = TAPE_CELLS_TO_DISPLAY*GUI.TAPE_FONT.getSize()*2;
		int h = GUI.TAPE_FONT.getSize()*2+GUI.TAPE_HEAD_FONT.getSize()*2;
		
		this.setMinimumSize(new Dimension(	w,h));
		this.setPreferredSize(new Dimension(w,h));
		this.setMaximumSize(new Dimension(	w,h));
		
		///*
		table = new JTable(new TapeModel(gui));
		
			//Cell Editor
		
			JTextField tapeCellEditorField = new JTextField();
				tapeCellEditorField.setFont(GUI.TAPE_FONT);
				tapeCellEditorField.setHorizontalAlignment(SwingConstants.CENTER);
			table.setDefaultEditor(Object.class, new TapeCellEditor(tapeCellEditorField,gui));
			
			//Cell Renderer
			
			tapeCellEditorField.setBorder(BorderFactory.createEmptyBorder());
				DefaultTableCellRenderer tapeCellRenderer = new DefaultTableCellRenderer();
				tapeCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
			table.setDefaultRenderer(Object.class, tapeCellRenderer);
			
			//Table Style
			
			table.setFont(GUI.TAPE_FONT);
			
			table.setRowHeight(GUI.TAPE_FONT.getSize()*2);
			for(int i=0;i<table.getColumnCount();i++)
			{
				table.getColumnModel().getColumn(i).setMaxWidth(GUI.TAPE_FONT.getSize()*2);
			}
			
			table.setBorder(BorderFactory.createLineBorder(new Color(0,0,0), 1));
			
				//Selection Style
			
				table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				table.setSelectionBackground(Color.WHITE);
			
			//Miscellaneous
				
			table.setSurrendersFocusOnKeystroke(true);
		//*/
		
		//Tape Head
		tapeHead = new TapeHead(gui,this);
			tapeHead.setAlignmentX(0.45f); //I don't know why I had to do it like this, but the label is slightly off-centre without it.
			
			
		this.add(table.getTableHeader());
		this.add(table);
		this.add(tapeHead);
	}
	
	/**
	 * Updates the tape display.
	 */
	public void update()
	{
		updateTape();
		updateTapeHead();
	}
	
	/**
	 * Updates the contents of the tape.
	 */
	private void updateTape()
	{
		int tapeHeadColumnIndex = gui.getSimulator().getTapeHeadColumnIndex();
		int beginIndex = tapeHeadColumnIndex-(int)Math.floor((double)GUI.TAPE_COLUMNS_TO_DISPLAY/(double)2);
		int endIndex = tapeHeadColumnIndex+(int)Math.ceil((double)GUI.TAPE_COLUMNS_TO_DISPLAY/(double)2)-1;
		List<Character> tape = gui.getSimulator().getTapeContents(0,0,beginIndex, endIndex);
		
		for(int i=0;i<tape.size();i++)
		{
			table.setValueAt(tape.get(i), 0, i);
		}
	}

	/**
	 * Updates the tape head's display.
	 */
	private void updateTapeHead()
	{
		tapeHead.update();
	}
}
