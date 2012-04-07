package turing.interfaces;

import java.io.File;
import java.util.List;

import turing.TuringException;
import turing.simulator.Tape;

public interface Simulator {

	public static final int BEFORE = 0,AFTER = 1;
	public static final int SPEED_MIN = 0, SPEED_MAX = 1000, SPEED_INIT=500;
	public static final int DETECT_TYPE = 0, ONE_DIMENSIONAL = 1, TWO_DIMENSIONAL = 2;
	public static final int HISTORY_SIZE_LIMIT = 50;
	public static final String FILE_EXTENSION = ".tsf";

	/**
	 * @return the {@link Instruction} currently being executed by the Turing simulator.
	 */
	public Instruction getCurrentInstruction();

	/**
	 * @return the current state the Turing machine is in.
	 */
	public int getCurrentState();

	/**
	 * @return the history of previously executed instructions.
	 */
	public List<Instruction> getHistory();
	
	/**
	 * Returns the tape for the Turing machine simulator.
	 * @return
	 */
	public Tape getTape();

	/**
	 * @return <code>true</code> if the simulator is paused, <code>false</code> if the simulator is running.
	 */
	public boolean isPaused();
	
	/**
	 * Opens the given file in the simulator.
	 * <p>
	 * This method should determine the type of the file from its extension.
	 * @param f The file to open.
	 * @param type The dimension of the simulation - <code>Simulator.ONE_DIMENSIONAL</code> for 1D, <code>Simulator.TWO_DIMENSIONAL</code> for 2D.
	 * @throws TuringException if the open operation failed.
	 */
	public void openFile(File f,int type) throws TuringException;
	
	/**
	 * Pauses the current simulation.
	 * @return <code>true</code> if the simulation could be paused, <code>false</code> if not.
	 */
	public boolean pause();

	/**
	 * Resumes the current simulation.
	 * @return <code>true</code> if the simulation could be played, <code>false</code> if not.
	 */
	public boolean play();

	/**
	 * Resets the simulator to its original state.
	 * <p>
	 * This method works like a "new file" operation - it should blank the tape, reset the tape head position,
	 * clear the current instruction etc.
	 * @param dimension The dimension of the new simulation - <code>Simulator.ONE_DIMENSIONAL</code> for 1D, <code>Simulator.TWO_DIMENSIONAL</code> for 2D.
	 */
	public void reset(int dimension);

	/**
	 * Saves the current state of the simulator to the given file.
	 * @param f The file to save to.
	 * @throws TuringException if the save operation failed.
	 */
	public void saveFile(File f) throws TuringException;

	/**
	 * Sets the delay, in milliseconds, between instructions being executed when in continuous simulation.
	 * @param value the amount of milliseconds to wait between executing instructions.
	 */
	public void setSpeed(int value);

	/*
	 * Executes a single instruction while paused.
	 */
	public void step();

	/**
	 * @return the current instruction set, or an empty list if there are no instructions.
	 */
	public List<Instruction> getInstructionSet();

	/**
	 * Returns a new instruction, using this simulator's implementation.
	 * @param currentState The current state for the instruction.
	 * @param inputSymbol The input symbol for the instruction.
	 * @param nextState The next state for the instruction.
	 * @param outputSymbol The output symbol for the instruction.
	 * @param direction The direction for the instruction.
	 * @return A new Instruction.
	 * @throws TuringException if the instruction is not valid for this Turing machine.
	 */
	public Instruction createInstruction(int currentState, char inputSymbol, int nextState, char outputSymbol, int direction) throws TuringException;

	/**
	 * Sets the simulator's instruction set to a new instruction set.
	 * <p>
	 * This method's body will be called by the GUI thread, so it should be synchronized on the simulator's
	 * instruction set.
	 * @param instructionSet The new instruction set.
	 */
	public void setInstructionSet(List<Instruction> instructionSet);

	/**
	 * Sets the current state of the Turing machine.
	 * @param state The new state.
	 * @throws TuringException If the new state is not a valid state.
	 */
	public void setCurrentState(int state) throws TuringException;
}
