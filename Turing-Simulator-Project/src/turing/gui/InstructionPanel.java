package turing.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JComponent;

import turing.interfaces.Instruction;
import turing.interfaces.GUI;

public class InstructionPanel extends JComponent {

	GUI gui;
	
	int yOffset=GUI.INSTRUCTION_FONT.getSize()/2;
	
	int lineOffset=GUI.INSTRUCTION_FONT.getSize()/8;
	
	/**
	 * Creates a new InstructionPanel.
	 * @param gui The GUI for the Turing machine simulator.
	 */
	InstructionPanel(GUI gui)
	{
		this.gui = gui;
		
		init();
		
		if(gui.debugMode())
		{
			this.setOpaque(true);
			this.setBackground(Color.PINK);
		}
	}
	
	/**
	 * Draws an quintuplet to the screen in the form (Current State,Input Symbol,Next State,Output Symbol,Direction).
	 * @param g The {@link Graphics} context to draw to.
	 * @param i The {@link Instruction} to draw.
	 * @param count The number of quintuplets which have been drawn below this one.
	 */
	private void drawInstruction(Graphics g, Instruction i, int count)
	{
		if(i!=null)
		{
			int xOffset=0;
			g.setColor(this.getForeground());
			Font INSTRUCTION_SUBSCRIPT_FONT = new Font(GUI.INSTRUCTION_FONT.getFamily(),GUI.INSTRUCTION_FONT.getStyle(),GUI.INSTRUCTION_FONT.getSize()*3/4);
			int y = this.getHeight()-yOffset-(GUI.INSTRUCTION_FONT.getSize()*count)-lineOffset*count+1;
			
			String currentState = i.getCurrentState()+"";
			
			String inputSymbol = i.getInputSymbol()+"";
			
			String nextState = i.getNextState()+"";
			
			String outputSymbol = i.getOutputSymbol()+"";
			
			String direction;
			
			switch(i.getDirection())
			{
			case Instruction.MOVE_LEFT:
				direction="←";
				break;
			case Instruction.MOVE_RIGHT:
				direction="→";
				break;
			case Instruction.HALT:
				direction="H";
				break;
			default:
				direction="ERROR";
				break;
			}
			
			int instructionWidth = GUI.INSTRUCTION_FONT.getSize()+
									(INSTRUCTION_SUBSCRIPT_FONT.getSize()*9/16)*currentState.length()+
									GUI.INSTRUCTION_FONT.getSize()*15/8+
									(INSTRUCTION_SUBSCRIPT_FONT.getSize()*9/16)*nextState.length();
			
			if(direction.equals("H"))
			{
				instructionWidth+=GUI.INSTRUCTION_FONT.getSize()*18/8;
			}
			else
			{
				instructionWidth+=GUI.INSTRUCTION_FONT.getSize()*20/8;
			}
			
			//Draw  (S
			xOffset=(this.getWidth()/2)-(instructionWidth/2);		
			g.setFont(GUI.INSTRUCTION_FONT);
			g.drawString("(S", xOffset, y);
			
			//Draw current state
			xOffset+=GUI.INSTRUCTION_FONT.getSize();
			g.setFont(INSTRUCTION_SUBSCRIPT_FONT);
			g.drawString(currentState, xOffset, y+(INSTRUCTION_SUBSCRIPT_FONT.getSize()/4));
			
			//Draw input symbol
			xOffset+=(INSTRUCTION_SUBSCRIPT_FONT.getSize()*9/16)*currentState.length();
			g.setFont(GUI.INSTRUCTION_FONT);
			g.drawString(","+inputSymbol+",S", xOffset, y);
			
			//Draw next state
			xOffset+=GUI.INSTRUCTION_FONT.getSize()*15/8;
			g.setFont(INSTRUCTION_SUBSCRIPT_FONT);
			g.drawString(nextState, xOffset, y+(INSTRUCTION_SUBSCRIPT_FONT.getSize()/4));
			
			//Draw
			xOffset+=(INSTRUCTION_SUBSCRIPT_FONT.getSize()*9/16)*nextState.length();
			
			g.setFont(GUI.INSTRUCTION_FONT);
			
			g.drawString(","+outputSymbol+","+direction+")", xOffset, y);
		}
		else
		{
			System.out.println("null instruction");
		}
	}
	
	/**
	 * Initialises the style of this component.
	 */
	private void init()
	{
		int w = GUI.INSTRUCTION_FONT.getSize()*10;
		int h = GUI.INSTRUCTION_FONT.getSize()*4;
		
		this.setMinimumSize(new Dimension(	w,h));
		this.setPreferredSize(new Dimension(w,h));
		this.setMaximumSize(new Dimension(	w,h));
		
		this.setOpaque(true);
		this.setBackground(Color.white);
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	}
	/**
	 * Draws the instruction history to this component.
	 * @see #drawInstruction(Graphics, Instruction, int)
	 */
	@Override
	public void paintComponent(Graphics g)
	{
		if(this.isOpaque())
		{
			g.setColor(this.getBackground());
			g.fillRect(0,0,this.getWidth(),this.getHeight());
		}
		
		List<Instruction> history = gui.getSimulator().getHistory();
		
		for(int i=history.size()-1, count=0; i>=0 && count<GUI.INSTRUCTIONS_TO_DISPLAY; i--, count++)
		{
			drawInstruction(g, history.get(i),count);
		}
	}
	
	/**
	 * Updates the instruction display.
	 */
	public void update()
	{
		repaint();
	}
	
}
