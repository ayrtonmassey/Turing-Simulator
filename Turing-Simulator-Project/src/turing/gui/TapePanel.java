package turing.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Polygon;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import turing.interfaces.GUI;

public class TapePanel extends JPanel {

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
		initComponents();
		
		if(gui.debugMode())
		{
			this.setBackground(Color.RED);
		}
	}
	
	private int tapeBeginIndex = 4;
	private int tapeEndIndex = tapeBeginIndex+GUI.TAPE_COLUMNS_TO_DISPLAY-1;
	
	public int getTapeBeginIndex()
	{
		return tapeBeginIndex;
	}

	public int getTapeEndIndex()
	{
		return tapeEndIndex;
	}

	
	
	/**
	 * Initialises the components of this panel.
	 * @see TapeCellEditor
	 * @see TapeModel
	 */
	private void initComponents()
	{
		int w = TAPE_CELLS_TO_DISPLAY*GUI.TAPE_FONT.getSize()*2;
		int h = GUI.TAPE_FONT.getSize()*2+GUI.TAPE_HEAD_FONT.getSize()*3;
		
		this.setMinimumSize(new Dimension(	w,h));
		this.setPreferredSize(new Dimension(w,h));
		this.setMaximumSize(new Dimension(	w,h));
		
		///*
		table = new JTable(new TapeModel(gui,this));
		
			//Cell Editor
		
			JTextField tapeCellEditorField = new JTextField();
				tapeCellEditorField.setFont(GUI.TAPE_FONT);
				tapeCellEditorField.setHorizontalAlignment(SwingConstants.CENTER);
			table.setDefaultEditor(Object.class, new TapeCellEditor(gui,this,tapeCellEditorField));
			
			//Cell Renderer
			
			tapeCellEditorField.setBorder(BorderFactory.createEmptyBorder());
				DefaultTableCellRenderer tapeCellRenderer = new DefaultTableCellRenderer();
				tapeCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
			table.setDefaultRenderer(Object.class, tapeCellRenderer);
			
			//Table Style
			
			table.setFont(GUI.TAPE_FONT);
			
			table.setRowHeight(GUI.TAPE_CELL_HEIGHT);
			for(int i=0;i<table.getColumnCount();i++)
			{
				table.getColumnModel().getColumn(i).setMaxWidth(GUI.TAPE_CELL_WIDTH);
			}
			
			table.setBorder(BorderFactory.createLineBorder(new Color(0,0,0), 1));
			
				//Selection Style
			
				table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				table.setSelectionBackground(Color.WHITE);
			
			//Miscellaneous
				
			table.setSurrendersFocusOnKeystroke(true);
		//*/
			
		this.add(table.getTableHeader());
		this.add(table);
		//this.add(tapeHead);
	}
	
	/**
	 * Updates the tape display.
	 */
	public void update()
	{
		updateTape();
		//updateTapeHead();
	}
	
	/**
	 * Updates the contents of the tape.
	 */
	private void updateTape()
	{
		int tapeHeadColumnIndex = gui.getSimulator().getTapeHeadColumnIndex();
		int beginIndex = tapeBeginIndex;
		int endIndex = tapeEndIndex;
		List<Character> tape = gui.getSimulator().getTapeContents(0,0,beginIndex, endIndex);
		
		for(int i=0;i<tape.size();i++)
		{
			table.setValueAt(tape.get(i), 0, i);
		}
	}
	
	@Override
	public void paint(Graphics g)
	{
		super.paintComponent(g);
		super.paintChildren(g);
		super.paintBorder(g);
		
		drawTapeHead(g);
	}
	
	int[] xPoints;
	int[] yPoints;
	int nPoints;
	Polygon headArrow;
	
	boolean tapeHeadStatic=false;
	
	public void drawTapeHead(Graphics g)
	{ 
		int x = table.getX();
		int y = table.getY();
		
		boolean drawState = true;
		
		int tableTapeHeadColIndex = gui.getSimulator().getTapeHeadColumnIndex()-tapeBeginIndex;
		TableColumn tapeHeadColumn = table.getColumnModel().getColumn(tableTapeHeadColIndex);
		
		for(int i=0;i<tableTapeHeadColIndex;i++)
		{
			x+=table.getColumnModel().getColumn(i).getWidth();
		}
		
		g.setColor(GUI.TAPE_HEAD_COLOR);
		
		//Draw the tape head cell highlight.
		for(int i=0;i<GUI.TAPE_FONT.getSize()/8;i++)
		{
			g.drawRect(x+i, y+i, tapeHeadColumn.getWidth()-(i*2+2), GUI.TAPE_FONT.getSize()*2-(i*2+1));
		}
		
		if(drawState)
		{
			String stateString = ""+gui.getSimulator().getCurrentState();
			Font TAPE_HEAD_SUBSCRIPT_FONT = new Font(GUI.TAPE_HEAD_FONT.getFamily(),GUI.TAPE_HEAD_FONT.getStyle(),(GUI.TAPE_HEAD_FONT.getSize()*3)/4);
			
			int tapeHeadHorizontalInset = GUI.TAPE_HEAD_FONT.getSize()*2/5;
			int tapeHeadVerticalInset = GUI.TAPE_HEAD_FONT.getSize()*3/5;
				int tapeHeadVerticalSubscriptInset = tapeHeadVerticalInset*3/4;
			int tapeHeadSWidth = GUI.TAPE_HEAD_FONT.getSize()*3/5;
			int tapeHeadTextSpacing = GUI.TAPE_HEAD_FONT.getSize()*1/5; 
			int tapeHeadStateWidth = TAPE_HEAD_SUBSCRIPT_FONT.getSize()*3/5*stateString.length();
			int tapeHeadWidth = tapeHeadHorizontalInset+tapeHeadSWidth+tapeHeadTextSpacing+tapeHeadStateWidth+tapeHeadHorizontalInset;
				if(tapeHeadWidth%2!=0)
				{
					tapeHeadWidth+=1;
				}
			int tapeHeadHeight = TAPE_HEAD_SUBSCRIPT_FONT.getSize()*3;
			int tapeHeadX = (x+tapeHeadColumn.getWidth()/2)-tapeHeadWidth/2;
			int tapeHeadY = y+table.getHeight();
			int tapeHeadPointHeight = TAPE_HEAD_SUBSCRIPT_FONT.getSize();
			//Fill the rectangle containing the text:
			g.fillRect(tapeHeadX, tapeHeadY+tapeHeadPointHeight, tapeHeadWidth, tapeHeadHeight-tapeHeadPointHeight);
			
			//Set the dimensions of the tape head point
			xPoints = new int[] {tapeHeadX,tapeHeadX+tapeHeadWidth/2,tapeHeadX+tapeHeadWidth};
			yPoints = new int[] {tapeHeadY+tapeHeadPointHeight,tapeHeadY,tapeHeadY+tapeHeadPointHeight};
			nPoints = 3;
			headArrow = new Polygon(xPoints,yPoints,nPoints);
		
			//Draw the tape head point:
			g.fillPolygon(headArrow);
			
			//Draw the text:
			g.setColor(GUI.TAPE_HEAD_FONT_COLOR);
			g.setFont(GUI.TAPE_HEAD_FONT);
			g.drawString("S", tapeHeadX+tapeHeadHorizontalInset, tapeHeadY+tapeHeadHeight-tapeHeadVerticalInset);
			g.setFont(TAPE_HEAD_SUBSCRIPT_FONT);
			g.drawString(stateString, tapeHeadX+tapeHeadHorizontalInset+tapeHeadSWidth+tapeHeadTextSpacing,tapeHeadY+tapeHeadHeight-tapeHeadVerticalSubscriptInset);
			//*/
		}
	}
}
