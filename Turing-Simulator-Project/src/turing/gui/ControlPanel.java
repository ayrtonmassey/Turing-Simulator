package turing.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import turing.Main;
import turing.TuringException;
import turing.interfaces.GUI;
import turing.interfaces.Simulator;

public class ControlPanel extends JPanel implements ActionListener,ChangeListener{

	GUI gui;
	
	JButton playButton;
	
	Icon playIcon;
		Icon pauseIcon;
		JButton stepButton;
	Icon stepIcon;
		JSlider speedSlider;
	ControlPanel(GUI gui)
	{
		this.gui = gui;
		
		init();
		
		if(gui.debugMode())
		{
			this.setOpaque(true);
			this.setBackground(Color.YELLOW);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource().equals(playButton))
		{
			if(gui.getSimulator().isPaused())
			{
				if(gui.getSimulator().play())
				{
					playButton.setIcon(pauseIcon);
					stepButton.setEnabled(false);
				}
			}
			else
			{
				if(gui.getSimulator().pause())
				{
					playButton.setIcon(playIcon);
					stepButton.setEnabled(true);
				}
			}
		}
		else if(e.getSource().equals(stepButton))
		{
			if(gui.getSimulator().isPaused())
			{
				try
				{
					gui.getSimulator().step();
				}
				catch (TuringException ex)
				{
					Main.err.displayError(ex);
				}
			}
		}
	}

	public void init()
	{	
		this.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		
		playButton = new JButton();
			playButton.addActionListener(this);
			
			int pW = 32;
			int pH = 32;
			playButton.setMinimumSize(new Dimension(pW,pH));
			playButton.setPreferredSize(new Dimension(pW,pH));
			playButton.setMaximumSize(new Dimension(pW,pH));
			
			playIcon = new ImageIcon(this.getClass().getResource("img/play.png"));
			pauseIcon = new ImageIcon(this.getClass().getResource("img/pause.png"));
			
			gc.fill=GridBagConstraints.NONE;
			gc.gridx=0;
			gc.gridy=0;
			gc.gridwidth=1;
			gc.gridheight=3;
			gc.weightx=0;
			gc.weighty=1;
			gc.anchor=GridBagConstraints.WEST;
		this.add(playButton,gc);
		
		stepButton = new JButton();
			stepButton.addActionListener(this);
			
			int sW = 32;
			int sH = 32;
			stepButton.setMinimumSize(new Dimension(sW,sH));
			stepButton.setPreferredSize(new Dimension(sW,sH));
			stepButton.setMaximumSize(new Dimension(sW,sH));
			
			stepIcon = new ImageIcon(this.getClass().getResource("img/step.png"));
			stepButton.setIcon(stepIcon);
			
			gc.fill=GridBagConstraints.NONE;
			gc.gridx=1;
			gc.gridy=0;
			gc.gridwidth=1;
			gc.gridheight=3;
			gc.weightx=0;
			gc.weighty=1;
			gc.anchor=GridBagConstraints.WEST;
		this.add(stepButton,gc);
		
		speedSlider = new JSlider(SwingConstants.HORIZONTAL,Simulator.SPEED_MIN,Simulator.SPEED_MAX,(Simulator.SPEED_MAX-Simulator.SPEED_MIN)/2);
			speedSlider.addChangeListener(this);
		
			speedSlider.setMajorTickSpacing(Simulator.SPEED_MAX/4);
			speedSlider.setMinorTickSpacing(Simulator.SPEED_MAX/8);
			speedSlider.setPaintTicks(true);
			
			gc.fill=GridBagConstraints.HORIZONTAL;
			gc.gridx=2;
			gc.gridy=0;
			gc.gridwidth=1;
			gc.gridheight=3;
			gc.weightx=1;
			gc.weighty=1;
			gc.anchor=GridBagConstraints.WEST;
		this.add(speedSlider,gc);
		
		if(gui.getSimulator().isPaused())
		{
			playButton.setIcon(playIcon);
			stepButton.setEnabled(true);
		}
		else
		{
			playButton.setIcon(pauseIcon);
			stepButton.setEnabled(false);
		}
		
		this.setBorder(GUI.TOP_BORDER);
	}

	@Override
	public void stateChanged(ChangeEvent e)
	{
		if(e.getSource().equals(speedSlider))
		{
			gui.getSimulator().setSpeed(speedSlider.getValue());
		}
	}

	public void update()
	{
		if(gui.getSimulator().isPaused())
		{
			playButton.setIcon(playIcon);
			stepButton.setEnabled(true);
		}
		else
		{
			playButton.setIcon(pauseIcon);
			stepButton.setEnabled(false);
		}
	}
	
}
