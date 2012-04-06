package turing.gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import turing.interfaces.GUI;

public class StatusPanel extends JPanel {

	GUI gui;
	
	JLabel statusLabel = new JLabel();
	
	JLabel tapeHeadCoordinates = new JLabel();
	JLabel tapeDisplayCoordinates = new JLabel();
	StatusPanel(GUI gui)
	{
		this.gui = gui;
		
		init();
		
		if(gui.debugMode())
		{
			this.setOpaque(true);
			this.setBackground(Color.MAGENTA);
		}
	}
	public void init()
	{
		this.setLayout(new GridBagLayout());
		
		GridBagConstraints gc = new GridBagConstraints();
		
		gc.fill=GridBagConstraints.HORIZONTAL;
		gc.gridx=0;
		gc.gridy=0;
		gc.gridwidth=1;
		gc.gridheight=3;
		gc.weightx=0.2;
		gc.weighty=1;
		gc.anchor=GridBagConstraints.WEST;
		this.add(tapeHeadCoordinates,gc);
		
		gc.fill=GridBagConstraints.HORIZONTAL;
		gc.gridx=1;
		gc.gridy=0;
		gc.gridwidth=1;
		gc.gridheight=3;
		gc.weightx=0.2;
		gc.weighty=1;
		gc.anchor=GridBagConstraints.WEST;
		this.add(tapeDisplayCoordinates,gc);
		
		gc.fill=GridBagConstraints.HORIZONTAL;
		gc.gridx=2;
		gc.gridy=0;
		gc.gridwidth=1;
		gc.gridheight=3;
		gc.weightx=0.6;
		gc.weighty=1;
		gc.anchor=GridBagConstraints.WEST;
		this.add(statusLabel,gc);
		
		this.setBorder(GUI.TOP_BORDER);
	}
	
	public void updateStatusLabel(String status)
	{
		statusLabel.setText(status);
	}
	
	public void updateTapeDisplayCoordinatess(int beginRowIndex,int endRowIndex,int beginColumnIndex,int endColumnIndex)
	{
		tapeDisplayCoordinates.setText("Tape Viewport: ("+beginColumnIndex+","+beginRowIndex+") to ("+endColumnIndex+","+endRowIndex+")");
	}
	
	public void updateTapeHeadCoordinates(int row,int col)
	{
		tapeHeadCoordinates.setText("Tape Head: ("+col+","+row+")");
	}
	
}
