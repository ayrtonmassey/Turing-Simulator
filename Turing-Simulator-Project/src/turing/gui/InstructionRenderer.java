package turing.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import turing.interfaces.GUI;
import turing.interfaces.Instruction;

public class InstructionRenderer extends JComponent implements ListCellRenderer {

	Instruction instruction;
	boolean isSelected;
	
	InstructionRenderer()
	{
		this.setPreferredSize(new Dimension(100,GUI.INSTRUCTION_FONT.getSize()*2));
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		if(instruction!=null)
		{
			if(this.isSelected)
			{
				g.setColor(GUI.SELECTED_COLOR);
				g.fillRect(0, 0, this.getWidth(), this.getHeight());
			}
			int y = this.getHeight()/2+GUI.INSTRUCTION_FONT.getSize()/2-4;
	
			int xOffset=0;
			g.setColor(this.getForeground());
			Font INSTRUCTION_SUBSCRIPT_FONT = new Font(GUI.INSTRUCTION_FONT.getFamily(),GUI.INSTRUCTION_FONT.getStyle(),GUI.INSTRUCTION_FONT.getSize()*3/4);
			
			String currentState = instruction.getCurrentState()+"";
			
			String inputSymbol = instruction.getInputSymbol()+"";
			
			String nextState = instruction.getNextState()+"";
			
			String outputSymbol = instruction.getOutputSymbol()+"";
			
			String direction;
			
			switch(instruction.getDirection())
			{
			case Instruction.MOVE_UP:
				direction="↑";
				break;
			case Instruction.MOVE_DOWN:
				direction="↓";
				break;
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
	}

	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus)
	{
		this.instruction = (Instruction) value;
		this.isSelected = isSelected;
		
		return this;
	}
	
}
