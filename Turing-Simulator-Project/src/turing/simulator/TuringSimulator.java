/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package turing.simulator;

import java.awt.Component;
import turing.Main;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import turing.TuringException;
import turing.gui.TuringGUI;
import turing.interfaces.GUI;
import turing.interfaces.Instruction;
import turing.interfaces.Simulator;

/**
 * 
 * @author Vickers Family
 */
public class TuringSimulator implements Simulator {

	GUI gui;
	Tape tape;
	int currentState = 0;
	public Instruction currentInstruction;
	private List<Instruction> history = new ArrayList<Instruction>();
	private boolean paused = true;
	private int sleep = Simulator.SPEED_INIT;
	private List<Instruction> instructionSet = new ArrayList<Instruction>();

	TuringSimulator()
	{
		reset(Simulator.ONE_DIMENSIONAL);
		gui = new TuringGUI(this);
		run();

	}

	public static void main(String[] args)
	{
		new TuringSimulator();
	}

	public void run()
	{
		while(true)
		{
			if (!paused)
			{
				try
				{
					step();
					Thread.sleep(sleep);
				}
				catch(TuringException ex)
				{
					this.pause();
					gui.update();
					Main.err.displayError(ex);
				}
				catch (InterruptedException ex)
				{
					System.err.println("Simulator woke unexpectedly!");
				}
			}
		}
	}

	public Instruction findInstruction(int currentState, char inputSymbol) throws TuringException
	{
		currentInstruction = null;
		
		for(Instruction i : instructionSet)
		{
			if (i.getCurrentState() == currentState && i.getInputSymbol() == tape.getTapeSymbolAt(tape.getTapeHeadX(), tape.getTapeHeadY()))
			{
				if(currentInstruction!=null)
				{
					throw new TuringException("More than one instruction with input symbol "+inputSymbol+" and current state "+currentState);
				}
				else
				{
					currentInstruction = i;
				}
			}
		}
		
		if(currentInstruction == null)
		{
			throw new TuringException("Could not find instruction with input symbol "+inputSymbol+" and current state "+currentState);
		}
		
		return currentInstruction;
	}

	public void executeInstruction(Instruction instruction) throws TuringException
	{
		int x = tape.getTapeHeadX();
		int y = tape.getTapeHeadY();

		if (history.size() > Simulator.HISTORY_SIZE_LIMIT)
		{
			history.remove(0);
		}

		tape.setTapeCellSymbol(instruction.getOutputSymbol(), x, y);
		
		currentState = instruction.getNextState();

		switch (instruction.getDirection())
		{
		case Instruction.MOVE_LEFT:
			tape.setTapeHeadX(tape.getTapeHeadX() - 1);
			break;
		case Instruction.MOVE_RIGHT:
			tape.setTapeHeadX(tape.getTapeHeadX() + 1);
			break;
		case Instruction.MOVE_UP:
			tape.setTapeHeadY(tape.getTapeHeadY() - 1);
			break;
		case Instruction.MOVE_DOWN:
			tape.setTapeHeadY(tape.getTapeHeadY() + 1);
			break;
		case Instruction.HALT:
			this.pause();
			this.setCurrentState(0);
			break;
		default:
			throw new TuringException("Unexpected direction for instruction "+instruction);
		}
		
		history.add(currentInstruction);
		
		gui.update();
	}

	public int getCurrentState()
	{
		return currentState;
	}

	public List<Instruction> getHistory()
	{
		return history;
	}

	public Tape getTape()
	{
		return tape;
	}

	public boolean isPaused()
	{
		return paused;
	}

	public boolean pause()
	{
		paused = true;
		gui.updateStatusMessage("Simulation paused.");
		return true;
	}

	public boolean play()
	{
		paused = false;
		gui.updateStatusMessage("Simulation resumed.");
		return true;
	}

	@Override
	public void reset(int dimension)
	{
		this.paused = true;
		this.currentState = 0;
		this.history.clear();
		
		instructionSet = new ArrayList<Instruction>();
		tape = new Tape(dimension);
	}

	public void saveFile(File f) throws TuringException
	{
		WriteFile.writeFile(f, tape, instructionSet);
		gui.updateStatusMessage("Saved simulation to "+f.getAbsolutePath());
	}

	public void setSpeed(int value)
	{
		this.sleep = value;
	}

	@Override
	public void step() throws TuringException
	{
		int x = tape.getTapeHeadX();
		int y = tape.getTapeHeadY();
		char currentSymbol = tape.getTapeSymbolAt(x, y);
		
		currentInstruction = findInstruction(currentState, currentSymbol);

		executeInstruction(currentInstruction);
	}

	public List<Instruction> getInstructionSet()
	{
		return instructionSet;
	}

	@Override
	public Instruction createInstruction(String quintuplet) throws TuringException
	{
		Instruction i = new TuringInstruction(quintuplet);
		
		if(tape.getTapeDimension() == Simulator.ONE_DIMENSIONAL && (i.getDirection() == Instruction.MOVE_DOWN || i.getDirection() == Instruction.MOVE_UP) )
		{
			throw new TuringException("Couldn't create instruction: Tape head cannot move up/down in a one-dimensional tape.");
		}
		
		return i;
	}

	public void setInstructionSet(List<Instruction> instructionSet)
	{
		synchronized(this.instructionSet)
		{
			this.instructionSet = instructionSet;
		}
	}

	public void setCurrentState(int state) throws TuringException
	{
		if(state >= 0)
		{
			this.currentState = state;
		}
		else
		{
			throw new TuringException("The state " + state + " is not a valid state for this Turing simulator.");
		}
	}

	public Instruction getCurrentInstruction()
	{
		return currentInstruction;
	}

	@Override
	public void openFile(File f) throws TuringException, FileNotFoundException
	{
		Tape tape = ReadFile.getTapeFromFile(f);
		this.reset(tape.getTapeDimension());
		List<Instruction> instructionSet = ReadFile.getInstructionsFromFile(f);
		
		this.tape = tape;
		this.instructionSet = instructionSet;
	}

	@Override
	public void importFile(File f)
	{
		try
		{
			Tape tape = ReadFile.importTapeFromFile(f);
			this.reset(tape.getTapeDimension());
			List<Instruction> instructionSet = ReadFile.importInstructionsFromFile(f);
			
			this.tape = tape;
			this.instructionSet = instructionSet;
		}
		catch (TuringException ex)
		{
			Main.err.displayError(ex);
		}
		catch (FileNotFoundException ex)
		{
			Main.err.displayError(ex);
		}
	}
}
