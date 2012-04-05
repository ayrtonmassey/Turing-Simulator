package turing.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
	
	private int ROWS_TO_DISPLAY;

	private int COLUMNS_TO_DISPLAY;
	
	GUI gui;
	
	JTable table;
	/**
	 * Creates a new TapePanel.
	 * @param gui The GUI for the Turing machine simulator.
	 */
	
	public TapePanel(GUI gui,int rows,int columns)
	{
		this(gui,rows,columns,0,0); //Initialise new tape panel starting at (0,0)
	}
	
	public TapePanel(GUI gui,int rows,int columns, int beginRowIndex, int beginColumnIndex)
	{
		this.gui = gui;
		
		this.ROWS_TO_DISPLAY=rows;
		this.COLUMNS_TO_DISPLAY=columns;
		
		this.tapeBeginRowIndex=beginRowIndex;
		this.tapeEndColumnIndex=tapeBeginColumnIndex+ROWS_TO_DISPLAY-1;
		
		this.tapeBeginColumnIndex=beginColumnIndex;
		this.tapeEndColumnIndex=tapeBeginColumnIndex+COLUMNS_TO_DISPLAY-1;
		
		init();
	}
	
	/**
	 * Initialises the components and layout of this panel.
	 * @see #initComponents()
	 */
	private void init()
	{	
		this.setLayout(new GridBagLayout());
		initComponents();
		
		if(gui.debugMode())
		{
			this.setBackground(Color.RED);
		}
	}
	
	private int tapeBeginColumnIndex = 4;
	private int tapeEndColumnIndex = tapeBeginColumnIndex+GUI.TAPE_COLUMNS_TO_DISPLAY-1;

	private int tapeBeginRowIndex=0;
	private int tapeEndRowIndex=tapeBeginRowIndex+GUI.TAPE_ROWS_TO_DISPLAY-1;
	
	public int getTapeBeginColumnIndex()
	{
		return tapeBeginColumnIndex;
	}

	public int getTapeEndColumnIndex()
	{
		return tapeEndColumnIndex;
	}
	
	public int getTapeBeginRowIndex()
	{
		return tapeBeginRowIndex;
	}
	
	public int getTapeEndRowIndex()
	{
		return tapeEndRowIndex;
	}
	
	/**
	 * Initialises the components of this panel.
	 * @see TapeCellEditor
	 * @see TapeModel
	 */
	private void initComponents()
	{	
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
			
			table.setMaximumSize(new Dimension(GUI.TAPE_CELL_WIDTH*GUI.TAPE_COLUMNS_TO_DISPLAY,GUI.TAPE_CELL_HEIGHT*GUI.TAPE_ROWS_TO_DISPLAY));
		//*/
		
		GridBagConstraints gc = new GridBagConstraints();
			gc.fill=GridBagConstraints.NONE;
			gc.gridx=1;
			gc.gridy=1;
			gc.gridwidth=1;
			gc.gridheight=1;
			gc.weightx=0;
			gc.weighty=0;
			gc.anchor=GridBagConstraints.CENTER;
		//this.add(table.getTableHeader(),gc);
		this.add(table,gc);
		//this.add(tapeHead);
	}
	
	/**
	 * Updates the tape display.
	 */
	public void update()
	{
		updateTape();
	}
	
	/**
	 * Updates the contents of the tape.
	 */
	private void updateTape()
	{
		int ox = gui.getSimulator().getTapeOriginX();
		int oy = gui.getSimulator().getTapeOriginY();
		int beginRowIndex = tapeBeginRowIndex+oy;
		int endRowIndex = tapeEndRowIndex+oy;
		int beginColumnIndex = tapeBeginColumnIndex+ox;
		int endColumnIndex = tapeEndColumnIndex+ox;
		
		List<List<Character>> tape = gui.getSimulator().getTapeContents(beginRowIndex, endRowIndex, beginColumnIndex, endColumnIndex);
		
		for(int y=0;y<tape.size();y++)
		{
			for(int x=0;x<tape.get(y).size();x++)
			{
				table.setValueAt(tape.get(y).get(x), y, x);
			}
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
		
		int tableTapeHeadColIndex = gui.getSimulator().getTapeHeadColumnIndex()-tapeBeginColumnIndex;
		int tableTapeHeadRowIndex = gui.getSimulator().getTapeHeadRowIndex()-tapeBeginRowIndex;
		
		if(!((tableTapeHeadColIndex<0||tableTapeHeadColIndex>=COLUMNS_TO_DISPLAY)||(tableTapeHeadRowIndex<0||tableTapeHeadRowIndex>=ROWS_TO_DISPLAY)))
		{
			TableColumn tapeHeadColumn = table.getColumnModel().getColumn(tableTapeHeadColIndex);
			
			int tableTapeHeadRowHeight = table.getRowHeight(tableTapeHeadRowIndex);
			
			for(int i=0;i<tableTapeHeadColIndex;i++)
			{
				x+=table.getColumnModel().getColumn(i).getWidth();
			}
			for(int i=0;i<tableTapeHeadRowIndex;i++)
			{
				y+=table.getRowHeight(i);
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
				int tapeHeadY = y+tableTapeHeadRowHeight;
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

	public int getRowCount()
	{
		return ROWS_TO_DISPLAY;
	}

	public int getColumnCount()
	{
		return COLUMNS_TO_DISPLAY;
	}
}
