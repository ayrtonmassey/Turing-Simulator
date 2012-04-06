package turing.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import turing.Main;
import turing.interfaces.GUI;
import turing.interfaces.Simulator;

public class CenterViewDialog extends JFrame implements ActionListener {

	GUI gui;
	
	private JTextField xField,yField;
	
	private JLabel descriptionLabel,xLabel,yLabel;
	private JButton acceptButton,cancelButton;
	CenterViewDialog(GUI gui)
	{
		this.gui=gui;
		
		init();
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource().equals(acceptButton) ||
			e.getSource().equals(yField) ||
			e.getSource().equals(xField) )
		{
			try
			{
				int x = Integer.parseInt(xField.getText());
				int y = Integer.parseInt(yField.getText());
				
				gui.setCenterTapeViewportOn(x,y);
				
				this.dispose();
			}
			catch(NumberFormatException ex)
			{
				Main.err.displayError(ex);
			}
		}
		else if(e.getSource().equals(cancelButton))
		{
			this.dispose();
		}
	}

	public void init()
	{
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		JPanel panel = new JPanel();
		
		int w = 160;
		int h = 160;
		this.setMinimumSize(	new Dimension(w,h));
		this.setPreferredSize(	new Dimension(w,h));
		this.setMaximumSize(	new Dimension(w,h));
		
		this.setResizable(false);
		
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		
			descriptionLabel = new JLabel("Center view on:");
				gc.fill=GridBagConstraints.NONE;
				gc.gridx=0;
				gc.gridy=0;
				gc.gridwidth=3;
				gc.gridheight=1;
				gc.weightx=1;
				gc.weighty=0.25;
				gc.anchor=GridBagConstraints.WEST;
			panel.add(descriptionLabel,gc);
			
			xLabel = new JLabel("x: ");
				gc.fill=GridBagConstraints.NONE;
				gc.gridx=0;
				gc.gridy=1;
				gc.gridwidth=1;
				gc.gridheight=1;
				gc.weightx=0.33;
				gc.weighty=0.25;
				gc.anchor=GridBagConstraints.CENTER;
			panel.add(xLabel,gc);
			
			yLabel = new JLabel("y: ");
				gc.fill=GridBagConstraints.NONE;
				gc.gridx=0;
				gc.gridy=2;
				gc.gridwidth=1;
				gc.gridheight=1;
				gc.weightx=0.33;
				gc.weighty=0.25;
				gc.anchor=GridBagConstraints.CENTER;
			panel.add(yLabel,gc);
			
			xField = new JTextField();
				xField.addActionListener(this);
				gc.fill=GridBagConstraints.HORIZONTAL;
				gc.gridx=1;
				gc.gridy=1;
				gc.gridwidth=2;
				gc.gridheight=1;
				gc.weightx=0.66;
				gc.weighty=0.25;
				gc.anchor=GridBagConstraints.CENTER;
			panel.add(xField,gc);
			
			yField = new JTextField();
				yField.addActionListener(this);
				gc.fill=GridBagConstraints.HORIZONTAL;
				gc.gridx=1;
				gc.gridy=2;
				gc.gridwidth=2;
				gc.gridheight=1;
				gc.weightx=0.66;
				gc.weighty=0.25;
				gc.anchor=GridBagConstraints.CENTER;
			panel.add(yField,gc);
			
			acceptButton = new JButton("OK");
				acceptButton.addActionListener(this);
				gc.fill=GridBagConstraints.NONE;
				gc.gridx=0;
				gc.gridy=3;
				gc.gridwidth=1;
				gc.gridheight=1;
				gc.weightx=0.5;
				gc.weighty=0.25;
				gc.anchor=GridBagConstraints.CENTER;
			panel.add(acceptButton,gc);
			
			cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(this);
				gc.fill=GridBagConstraints.NONE;
				gc.gridx=2;
				gc.gridy=3;
				gc.gridwidth=1;
				gc.gridheight=1;
				gc.weightx=0.5;
				gc.weighty=0.25;
				gc.anchor=GridBagConstraints.CENTER;
			panel.add(cancelButton,gc);
			
			panel.setBorder(BorderFactory.createEmptyBorder(8,8,8,8));
		
		this.add(panel);
		
		if(gui.getSimulator().getTape().getTapeDimension()==Simulator.ONE_DIMENSIONAL)
		{
			yField.setEnabled(false);
			yField.setText("0");
		}
		
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
}
