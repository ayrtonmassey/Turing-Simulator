package turing.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

import turing.interfaces.GUI;

public class SidePanel extends JPanel {

	GUI gui;
	TapePanel tape;
	
	public SidePanel(GUI gui,TapePanel tape)
	{
		this.gui = gui;
		this.tape = tape;
		
		init();
	}
	
	HistoryPanel history;
	InstructionPanel instruction;
	NavigationPanel navigation;
	
	public void init()
	{
		int w = GUI.INSTRUCTION_FONT.getSize()*11;
		int h = GUI.INSTRUCTION_FONT.getSize()*4;
		
		this.setMinimumSize(new Dimension(	w,h));
		this.setPreferredSize(new Dimension(w,h));
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		
		history = new HistoryPanel(gui);
			gc.fill=GridBagConstraints.BOTH;
			gc.gridx=0;
			gc.gridy=0;
			gc.gridwidth=1;
			gc.gridheight=1;
			gc.weightx=1;
			gc.weighty=0.3;
			gc.anchor=GridBagConstraints.NORTH;
		this.add(history,gc);
		
		instruction = new InstructionPanel(gui);
			gc.fill=GridBagConstraints.BOTH;
			gc.gridx=0;
			gc.gridy=1;
			gc.gridwidth=1;
			gc.gridheight=1;
			gc.weightx=1;
			gc.weighty=0.5;
			gc.anchor=GridBagConstraints.NORTH;
		this.add(instruction,gc);
		
		navigation = new NavigationPanel(gui,tape);
			gc.fill=GridBagConstraints.NONE;
			gc.gridx=0;
			gc.gridy=2;
			gc.gridwidth=1;
			gc.gridheight=1;
			gc.weightx=1;
			gc.weighty=0;
			gc.anchor=GridBagConstraints.SOUTH;
		this.add(navigation,gc);
		
		this.setBorder(GUI.LEFT_BORDER);
	}
	
}
