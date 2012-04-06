package turing.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

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
	ControlPanel control;
	StatusPanel status;
	
	/**
	 * Creates a new GUI for the Turing machine simulator.
	 * @param sim
	 */
	public TuringGUI(Simulator sim)
	{
		this.sim = sim;
		this.currentState = sim.getCurrentState();
		this.tapeHeadColumnIndex = sim.getTape().getTapeHeadColumnIndex();
		
		init();
	}
	
	@Override
	public boolean debugMode()
	{
		return DEBUG;
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
	}
	
	/**
	 * Initialises the components for this GUI.
	 */
	private void initComponents()
	{
		this.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		
		instruction = new InstructionPanel(this);
			gc.fill=GridBagConstraints.NONE;
			gc.gridx=3;
			gc.gridy=0;
			gc.gridwidth=1;
			gc.gridheight=4;
			gc.weightx=0;
			gc.weighty=1;
			gc.anchor=GridBagConstraints.NORTH;
		this.add(instruction,gc);
		
		tape = new TapePanel(this,GUI.TAPE_ROWS_TO_DISPLAY,GUI.TAPE_COLUMNS_TO_DISPLAY);
			gc.fill=GridBagConstraints.BOTH;
			gc.gridx=0;
			gc.gridy=0;
			gc.gridwidth=3;
			gc.gridheight=3;
			gc.weightx=1;
			gc.weighty=1;
			gc.anchor=GridBagConstraints.CENTER;
		this.add(tape,gc);
		
		control = new ControlPanel(this);
			gc.fill=GridBagConstraints.HORIZONTAL;
			gc.gridx=0;
			gc.gridy=4;
			gc.gridwidth=3;
			gc.gridheight=1;
			gc.weightx=1;
			gc.weighty=0;
			gc.anchor=GridBagConstraints.SOUTH;
		this.add(control,gc);
		
		status = new StatusPanel(this);
			gc.fill=GridBagConstraints.HORIZONTAL;
			gc.gridx=0;
			gc.gridy=5;
			gc.gridwidth=4;
			gc.gridheight=1;
			gc.weightx=1;
			gc.weighty=0;
			gc.anchor=GridBagConstraints.SOUTH;
		this.add(status,gc);
	}

	/**
	 * Initialises the frame which contains the GUI elements. 
	 */
	private void initFrame()
	{
		this.setMinimumSize(	new Dimension(800,600));
		this.setPreferredSize(	new Dimension(800,600));
		this.setMaximumSize(	new Dimension(800,600));

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setTitle("Baby's First Turing Machine");
	}

	@Override
	public void update()
	{
		tape.update();
		instruction.update();
		
		status.updateTapeHeadCoordinates(sim.getTape().getTapeHeadRowIndex(), sim.getTape().getTapeHeadColumnIndex());
	}

	@Override
	public void updateTapeDisplayCoordinates(int tapeBeginRowIndex, int tapeEndRowIndex, int tapeBeginColumnIndex, int tapeEndColumnIndex)
	{
		status.updateTapeDisplayCoordinatess(tapeBeginRowIndex, tapeEndRowIndex, tapeBeginColumnIndex, tapeEndColumnIndex);
	}
}
