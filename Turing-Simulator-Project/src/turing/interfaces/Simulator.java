package turing.interfaces;

import java.io.File;
import java.util.List;

import turing.TuringException;
import turing.simulator.Tape;

public interface Simulator {

	public static final int BEFORE = 0,AFTER = 1;
	public static final int SPEED_MIN = 0, SPEED_MAX = 1000, SPEED_INIT=500;

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
	 * @throws TuringException if the file is invalid or an error occurs while opening the file.
	 */
	public void openFile(File f) throws TuringException;

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
}
