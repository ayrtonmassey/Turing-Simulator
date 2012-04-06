package turing.interfaces;

import java.io.File;
import java.util.List;

import turing.TuringException;
import turing.simulator.Tape;

public interface Simulator {

	public static final int BEFORE = 0,AFTER = 1;
	public static final int SPEED_MIN = 0, SPEED_MAX = 1000, SPEED_INIT=500;
	public static final int DETECT_TYPE = 0, ONE_DIMENSIONAL = 1, TWO_DIMENSIONAL = 2;

	/**
	 * @return the {@link Instruction} currently being executed by the Turing simulator.
	 */
	public Instruction getCurrentInstruction();

	/**
	 * @return the current state the Turing machine is in.
	 */
	public int getCurrentState();

	/**
	 * Returns the tape for the Turing machine simulator.
	 * @return
	 */
	public Tape getTape();
	
	/**
	 * Opens the given file in the simulator.
	 * <p>
	 * This method should determine the type of the file from its extension.
	 * @param f The file to open.
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
	
	/*
	 * Executes a single instruction while paused.
	 */
	public void step();

	/**
	 * @return <code>true</code> if the simulator is paused, <code>false</code> if the simulator is running.
	 */
	public boolean isPaused();

	/**
	 * Sets the delay, in milliseconds, between instructions being executed when in continuous simulation.
	 * @param value the amount of milliseconds to wait between executing instructions.
	 */
	public void setSpeed(int value);

	/**
	 * Resets the simulator to its original state.
	 * <p>
	 * This method works like a "new file" operation - it should blank the tape, reset the tape head position,
	 * clear the current instruction etc.
	 */
	public void reset();

	/**
	 * Saves the current state of the simulator to the given file.
	 * @param f The file to save to.
	 * @throws TuringException if the save operation failed.
	 */
	public void saveFile(File f) throws TuringException;
}
