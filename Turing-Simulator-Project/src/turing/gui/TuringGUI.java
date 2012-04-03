package turing.gui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

import turing.interfaces.GUI;
import turing.interfaces.Simulator;

public class TuringGUI extends JFrame implements GUI {

	public static final int CELLS_TO_DISPLAY=10;

	private boolean DEBUG = false;
	
	Simulator sim;
	
	int currentState;
	int tapeHeadColumnIndex;
	
	TapePanel tape;
	InstructionPanel instruction;
	
	/**
	 * Creates a new GUI for the Turing machine simulator.
	 * @param sim
	 */
	public TuringGUI(Simulator sim)
	{
		this.sim = sim;
		this.currentState = sim.getCurrentState();
		this.tapeHeadColumnIndex = sim.getTapeHeadColumnIndex();
		
		init();
	}
	
	@Override
	public Simulator getSimulator()
	{
		return sim;
	}
	
	/**
	 * Initialises this GUI.
	 * <p>
	 * A frame is displayed in the center of the screen, containing the various
	 * components which make up the GUI.
	 * @see TapePanel
	 */
	private void init()
	{
		initFrame();
		initComponents();
		
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setBackground(new Color(255,0,0));
	}

	/**
	 * Initialises the components for this GUI.
	 */
	private void initComponents()
	{
		instruction = new InstructionPanel(this);
		this.add(instruction);
		tape = new TapePanel(this);
		this.add(tape);
	}
	
	/**
	 * Initialises the frame which contains the GUI elements. 
	 */
	private void initFrame()
	{
		this.setMinimumSize(	new Dimension(800,600));
		this.setPreferredSize(	new Dimension(800,600));
		this.setMaximumSize(	new Dimension(800,600));
		
		this.setLayout(new BoxLayout(this.getContentPane(),BoxLayout.Y_AXIS));
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void update()
	{
		
	}

	@Override
	public boolean debugMode()
	{
		return DEBUG;
	}
}
