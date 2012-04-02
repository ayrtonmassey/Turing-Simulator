package turing.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Polygon;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import turing.interfaces.GUI;

public class TapeHead extends JComponent {

	int[] xPoints;
	int[] yPoints;
	int nPoints;
	Polygon headArrow;
	
	GUI gui;
	
	public TapeHead(GUI gui)
	{
		this.gui=gui;
		
		init();
		
		if(TuringGUI.DEBUG)
		{
			this.setBackground(Color.GREEN);
			this.setOpaque(true);
		}
	}
	
	/**
	 * Initializes the style for the tape head.
	 */
	private void init()
	{
		this.setMinimumSize(new Dimension(	GUI.TAPE_HEAD_FONT.getSize()*3,(int)(GUI.TAPE_HEAD_FONT.getSize()*2)));
		this.setPreferredSize(new Dimension(GUI.TAPE_HEAD_FONT.getSize()*3,(int)(GUI.TAPE_HEAD_FONT.getSize()*2)));
		this.setMaximumSize(new Dimension(	GUI.TAPE_HEAD_FONT.getSize()*3,(int)(GUI.TAPE_HEAD_FONT.getSize()*2)));
		
		this.setForeground(Color.WHITE);
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		//Javadoc requests that if isOpaque is true, the background be filled.
		//As we are not using super's implementation of paintComponent,
		//This code fills the background if isOpaque is true.
		if(this.isOpaque())
		{
			g.setColor(this.getBackground());
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
		}
		
		g.setColor(Color.BLACK);
		
		//Fill the rectangle containing the text:
		g.fillRect(0, this.getHeight()-(int)(GUI.TAPE_HEAD_FONT.getSize()*1.25), this.getWidth(), (int)(GUI.TAPE_HEAD_FONT.getSize()*1.25));
		
		//Initializing the point of the tape head.
		//If this is called in init, getWidth returns 0
		//As the width has been set when this method is called, I put this code here.
		xPoints = new int[] {0,this.getWidth()/2,this.getWidth()};
		yPoints = new int[] {this.getHeight()-(int)(GUI.TAPE_HEAD_FONT.getSize()*1.25),0,this.getHeight()-(int)(GUI.TAPE_HEAD_FONT.getSize()*1.25)};
		nPoints = 3;
		headArrow = new Polygon(xPoints,yPoints,nPoints);
		
		g.fillPolygon(headArrow);
		
		//Variables for text position:
		String stateString = ""+gui.getSimulator().getCurrentState();
		int tapeHeadFontSize = GUI.TAPE_HEAD_FONT.getSize();
		Font TAPE_HEAD_SUBSCRIPT_FONT = new Font(GUI.TAPE_HEAD_FONT.getFamily(),GUI.TAPE_HEAD_FONT.getStyle(),(GUI.TAPE_HEAD_FONT.getSize()*3)/4);
		int tapeHeadSubscriptFontSize = TAPE_HEAD_SUBSCRIPT_FONT.getSize();
		int horizontalCenter = this.getWidth()/2;
		
		//Text position:
		g.setColor(Color.WHITE);
		g.setFont(GUI.TAPE_HEAD_FONT);
		g.drawString("S", horizontalCenter-(tapeHeadFontSize/2)-(tapeHeadSubscriptFontSize*stateString.length())/8,this.getHeight()-(tapeHeadFontSize/2));
		g.setFont(TAPE_HEAD_SUBSCRIPT_FONT);
		g.drawString(stateString, horizontalCenter+(tapeHeadFontSize/5)-(tapeHeadSubscriptFontSize*stateString.length())/8, this.getHeight()-(tapeHeadSubscriptFontSize/2));
	}
	
	/**
	 * Updates the tape head to the current state of the Turing machine.
	 * <p>
	 * Call this method every time the GUI's <code>update</code> method is called.
	 * @see GUI#update() 
	 */
	public void update()
	{
		repaint();
	}
}
