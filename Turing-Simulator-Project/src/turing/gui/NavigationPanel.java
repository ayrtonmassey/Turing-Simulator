package turing.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;

import turing.interfaces.GUI;

public class NavigationPanel extends JPanel implements ActionListener {

	GUI gui;
	TapePanel tape;
	
	NavigationPanel(GUI gui,TapePanel tape)
	{
		this.gui = gui;
		this.tape= tape;
		
		init();
		
		if(gui.debugMode())
		{
			this.setOpaque(true);
			this.setBackground(Color.CYAN);
		}
	}

	JButton upButton;
	JButton downButton;
	JButton rightButton;
	JButton leftButton;
	JButton centerButton;
	
	Icon upIcon;
	Icon downIcon;
	Icon leftIcon;
	Icon rightIcon;
	Icon centerIcon;
	
	public void init()
	{
		this.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		
		int bW = 40;
		int bH = 40;
		
		upIcon = new ImageIcon(this.getClass().getResource("img/up.png"));
		downIcon = new ImageIcon(this.getClass().getResource("img/down.png"));
		leftIcon = new ImageIcon(this.getClass().getResource("img/left.png"));
		rightIcon = new ImageIcon(this.getClass().getResource("img/right.png"));
		centerIcon = new ImageIcon(this.getClass().getResource("img/center.png"));
		
		upButton = new JButton(upIcon);
			upButton.addActionListener(this);
		
			upButton.setFont(GUI.NAVIGATION_FONT);
			upButton.setMinimumSize(new Dimension(bW,bH));
			upButton.setPreferredSize(new Dimension(bW,bH));
			upButton.setMaximumSize(new Dimension(bW,bH));
			
			gc.fill=GridBagConstraints.NONE;
			gc.gridx=1;
			gc.gridy=0;
			gc.gridwidth=1;
			gc.gridheight=1;
			gc.weightx=0;
			gc.weighty=0.33;
			gc.anchor=GridBagConstraints.NORTH;
		this.add(upButton,gc);
		
		downButton = new JButton(downIcon);
			downButton.addActionListener(this);
		
			downButton.setFont(GUI.NAVIGATION_FONT);
			downButton.setMinimumSize(new Dimension(bW,bH));
			downButton.setPreferredSize(new Dimension(bW,bH));
			downButton.setMaximumSize(new Dimension(bW,bH));
			
			gc.fill=GridBagConstraints.NONE;
			gc.gridx=1;
			gc.gridy=2;
			gc.gridwidth=1;
			gc.gridheight=1;
			gc.weightx=0;
			gc.weighty=0.33;
			gc.anchor=GridBagConstraints.NORTH;
		this.add(downButton,gc);
		
		leftButton = new JButton(leftIcon);
			leftButton.addActionListener(this);
			
			leftButton.setFont(GUI.NAVIGATION_FONT);
			leftButton.setMinimumSize(new Dimension(bW,bH));
			leftButton.setPreferredSize(new Dimension(bW,bH));
			leftButton.setMaximumSize(new Dimension(bW,bH));
			
			gc.fill=GridBagConstraints.NONE;
			gc.gridx=0;
			gc.gridy=1;
			gc.gridwidth=1;
			gc.gridheight=1;
			gc.weightx=0;
			gc.weighty=0.33;
			gc.anchor=GridBagConstraints.NORTH;
		this.add(leftButton,gc);

		rightButton = new JButton(rightIcon);
			rightButton.addActionListener(this);
		
			rightButton.setFont(GUI.NAVIGATION_FONT);
			rightButton.setMinimumSize(new Dimension(bW,bH));
			rightButton.setPreferredSize(new Dimension(bW,bH));
			rightButton.setMaximumSize(new Dimension(bW,bH));
			
			gc.fill=GridBagConstraints.NONE;
			gc.gridx=2;
			gc.gridy=1;
			gc.gridwidth=1;
			gc.gridheight=1;
			gc.weightx=0;
			gc.weighty=0.33;
			gc.anchor=GridBagConstraints.NORTH;
		this.add(rightButton,gc);
		
		centerButton = new JButton(centerIcon);
			centerButton.addActionListener(this);
		
			centerButton.setFont(GUI.NAVIGATION_FONT);
			centerButton.setMinimumSize(new Dimension(bW,bH));
			centerButton.setPreferredSize(new Dimension(bW,bH));
			centerButton.setMaximumSize(new Dimension(bW,bH));
			
			gc.fill=GridBagConstraints.NONE;
			gc.gridx=1;
			gc.gridy=1;
			gc.gridwidth=1;
			gc.gridheight=1;
			gc.weightx=0;
			gc.weighty=0.33;
			gc.anchor=GridBagConstraints.NORTH;
		this.add(centerButton,gc);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource().equals(upButton))
		{
			tape.moveViewport(TapePanel.DIRECTION_UP);
		}
		else if(e.getSource().equals(downButton))
		{
			tape.moveViewport(TapePanel.DIRECTION_DOWN);
		}
		else if(e.getSource().equals(leftButton))
		{
			tape.moveViewport(TapePanel.DIRECTION_LEFT);
		}
		else if(e.getSource().equals(rightButton))
		{
			tape.moveViewport(TapePanel.DIRECTION_RIGHT);
		}
		else if(e.getSource().equals(centerButton))
		{
			tape.setFollowTapeHead(!tape.isFollowingTapeHead());
		}
	}
}
