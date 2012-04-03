package turing.interfaces;

import java.awt.Font;


public interface GUI {

	public static final int TAPE_COLUMNS_TO_DISPLAY=11,
							TAPE_ROWS_TO_DISPLAY=1,
							INSTRUCTIONS_TO_DISPLAY=3;
	
	public static final Font TAPE_FONT=new Font("Arial",Font.BOLD,24),
								TAPE_HEAD_FONT=new Font("Arial",Font.BOLD,16),
								INSTRUCTION_FONT=new Font("Arial",Font.BOLD,24);
	
	/**
	 * Updates the GUI to the current state of the Turing machine.
	 * <p>
	 * This method should be called each time the Turing simulator carries out an instruction,
	 * so that the GUI displays the current state, tape contents etc.
	 */
	public void update();

	/**
	 * Returns the Simulator for the Turing machine simulator.
	 * @return a {@link Simulator}
	 */
	public Simulator getSimulator();

	public boolean debugMode();
	
}
