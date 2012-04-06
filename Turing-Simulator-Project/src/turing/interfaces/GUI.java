package turing.interfaces;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.border.Border;


public interface GUI {

	public static final Border TOP_BORDER = BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(210,210,210)),
																				BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(255,255,255)));
	
	public static final int TAPE_COLUMNS_TO_DISPLAY=11,
							TAPE_ROWS_TO_DISPLAY=8,
							INSTRUCTIONS_TO_DISPLAY=3;
	
	public static final Font TAPE_FONT=new Font("Arial",Font.BOLD,24), //Any size works nicely. Tape will resize based on font size.
								TAPE_HEAD_FONT=new Font("Arial",Font.BOLD,16), //Some sizes may skew font. Tape head will resize based on font size.
								INSTRUCTION_FONT=new Font("Arial",Font.BOLD,18); //Some sizes may skew font. 18, 20, 24 and 32 work quite nicely.
	
	public static final int TAPE_CELL_WIDTH=TAPE_FONT.getSize()*2,TAPE_CELL_HEIGHT=TAPE_FONT.getSize()*2;

	public static final Color TAPE_HEAD_COLOR = Color.BLACK;

	public static final Color TAPE_HEAD_FONT_COLOR = Color.WHITE;

	public static final int INSTRUCTION_HISTORY_LIMIT = 50;
	
	public boolean debugMode();

	/**
	 * Returns the Simulator for the Turing machine simulator.
	 * @return a {@link Simulator}
	 */
	public Simulator getSimulator();

	/**
	 * Updates the GUI to the current state of the Turing machine.
	 * <p>
	 * This method should be called each time the Turing simulator carries out an instruction,
	 * so that the GUI displays the current state, tape contents etc.
	 */
	public void update();

	public void updateTapeDisplayCoordinates(int tapeBeginRowIndex,
			int tapeEndRowIndex, int tapeBeginColumnIndex,
			int tapeEndColumnIndex);
	
	
}
