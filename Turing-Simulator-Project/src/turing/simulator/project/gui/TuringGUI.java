package turing.simulator.project.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

import turing.simulator.project.interfaces.GUI;
import turing.simulator.project.interfaces.Simulator;

public class TuringGUI extends JFrame implements GUI {

	public static final int CELLS_TO_DISPLAY=10;
	
	Simulator sim;
	
	int currentState;
	int tapeHeadIndex;
	
	public TuringGUI(Simulator sim)
	{
		this.sim = sim;
		this.currentState = sim.getCurrentState();
		this.tapeHeadIndex = sim.getTapeHeadIndex();
		
		
		init();
	}
	
	TapePanel tape;
	private void init()
	{
		initFrame();
		initComponents();
		
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setBackground(new Color(255,0,0));
	}
	
	private void initComponents()
	{
		tape = new TapePanel(this);
		this.add(tape);
	}

	private void initFrame()
	{
		this.setMinimumSize(new Dimension(800,600));
		this.setPreferredSize(new Dimension(800,600));
		this.setMaximumSize(new Dimension(800,600));
		
		this.setLayout(new BoxLayout(this.getContentPane(),BoxLayout.Y_AXIS));
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	@Override
	public void update()
	{
		
	}

	@Override
	public Simulator getSimulator()
	{
		return sim;
	}
}
