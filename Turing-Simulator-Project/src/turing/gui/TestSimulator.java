package turing.gui;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import turing.Main;
import turing.TuringException;
import turing.simulator.Tape;
import turing.simulator.TuringInstruction;
import turing.interfaces.GUI;
import turing.interfaces.Instruction;
import turing.interfaces.Simulator;


public class TestSimulator implements Simulator {

	public static void main(String[] args)
	{
		new TestSimulator();
	}
	
	GUI gui;
	
	private List<Instruction> instructionSet = new ArrayList<Instruction>();
	
	private Instruction currentInstruction;
	

	
	private int currentState = 0;
	
	private int sleep=Simulator.SPEED_INIT;
	
	private boolean paused = true;

	
	public TestSimulator()
	{
		run();
	}
	
	@Override
	public Instruction getCurrentInstruction()
	{
		return currentInstruction;
	}
	
	@Override
	public int getCurrentState()
	{
		//TODO: FILL THIS IN
		return currentState;
	}
	
	public void init()
	{
		tape = new Tape(0,0);
		tape.setTapeHeadX(3);
		tape.setTapeHeadY(3);
		
		try
		{
			tape.setTapeCellSymbol('0', 0, 0);
			tape.setTapeCellSymbol('1', 1, 0);
			tape.setTapeCellSymbol('2', 2, 0);
			tape.setTapeCellSymbol('3', 3, 0);
			tape.setTapeCellSymbol('4', 4, 0);
			tape.setTapeCellSymbol('5', 5, 0);
			tape.setTapeCellSymbol('6', 6, 0);
			tape.setTapeCellSymbol('7', 7, 0);
			tape.setTapeCellSymbol('8', 8, 0);
			tape.setTapeCellSymbol('9', 9, 0);
			tape.setTapeCellSymbol('0', 10, 0);
			tape.setTapeCellSymbol('1', 11, 0);
			tape.setTapeCellSymbol('2', 12, 0);
			tape.setTapeCellSymbol('3', 13, 0);
			tape.setTapeCellSymbol('4', 14, 0);
			tape.setTapeCellSymbol('5', 15, 0);
			
			tape.setTapeCellSymbol('#', 1, 3);
			tape.setTapeCellSymbol('#', 5, 3);
		}
		catch (TuringException ex)
		{
			Main.err.displayError(ex);
		}
		
		System.out.println("INITIAL TAPE:");
		tape.print();
		
		instructionSet.add(new TuringInstruction(0,'_',0,'_',Instruction.MOVE_RIGHT));
		instructionSet.add(new TuringInstruction(0,'#',1,'#',Instruction.MOVE_LEFT));
		
		instructionSet.add(new TuringInstruction(1,'_',1,'_',Instruction.MOVE_LEFT));
		instructionSet.add(new TuringInstruction(1,'#',0,'#',Instruction.MOVE_RIGHT));
	}
	
	@Override
    public void openFile(File f) throws TuringException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

	public void run()
	{
		//gui = new TuringGUI(this);
		
		init();
		
		gui = new TuringGUI(this);
		
		testTapeSetValueAt();
		
		gui.update();
		
		//*
		while(true)
		{
			while(!paused)
			{			
				findCurrentInstruction();
				if(currentInstruction!=null)
				{
					executeCurrentInstruction();
					try
					{
						Thread.sleep(sleep);
					}
					catch(InterruptedException ex)
					{
						System.out.println("Simulation was woken up!");
					}
				}
				else
				{
					//Pause the simulator and display error message
					paused=true;
					JOptionPane.showMessageDialog(null, "No instruction specified for current symbol and state.");
				}
			}
		}//*/
	}

	

    public void testTapeSetValueAt()
	{
    	System.out.println("\nTape Origin: ("+tape.getTapeOriginX()+","+tape.getTapeOriginY()+") Value: "+tape.getTapeSymbolAt(tape.getTapeOriginX(), tape.getTapeOriginY()));
    	
		try
		{
			tape.setTapeCellSymbol('a', 0, 0);
			tape.setTapeCellSymbol('b', 4, 0);
			tape.setTapeCellSymbol('c', -1, 0);
			tape.setTapeCellSymbol('d', -10, 0);
			tape.setTapeCellSymbol('e', 15, 0);
			tape.setTapeCellSymbol('f', 21, 0);
			tape.setTapeCellSymbol('g',0,-1);
			tape.setTapeCellSymbol('h',0,1);
			tape.setTapeCellSymbol('i',0,10);
			tape.setTapeCellSymbol('j',0,-5);
			tape.setTapeCellSymbol('k',1,1);
			tape.setTapeCellSymbol('l',-1,-1);
		}
		catch(TuringException ex)
		{
			Main.err.displayError(ex);
		}
		
		System.out.println("\nNEW TAPE:");
		tape.print();
		
		System.out.println("\nTape Origin: ("+tape.getTapeOriginX()+","+tape.getTapeOriginY()+") Value: "+tape.getTapeSymbolAt(tape.getTapeOriginX(), tape.getTapeOriginY()));
	}

	@Override
	public boolean pause()
	{
		paused=true;
		return true;
	}

	@Override
	public boolean play()
	{
		synchronized(this)
		{
			notifyAll();
		}
		paused=false;
		return true;
	}

	@Override
	public void step()
	{
		if(paused)
		{
			findCurrentInstruction();
			if(currentInstruction!=null)
			{
				executeCurrentInstruction();
			}
		}
	}

	private void executeCurrentInstruction()
	{
		try
		{
			//Update symbol and state
			tape.setTapeCellSymbol(currentInstruction.getOutputSymbol(), tape.getTapeHeadX(), tape.getTapeHeadY());
			currentState = currentInstruction.getNextState();
			
			//Move the tape head
			switch(currentInstruction.getDirection())
			{
			case Instruction.MOVE_LEFT:
				tape.setTapeHeadX(tape.getTapeHeadX()-1);
				break;
			case Instruction.MOVE_RIGHT:
				tape.setTapeHeadX(tape.getTapeHeadX()+1);
				break;
			default:
				System.out.println("ERROR!");
				break;
			}
			
			gui.update();
		}
		catch(TuringException ex)
		{
			Main.err.displayError(ex);
		}
	}

	private Instruction findCurrentInstruction()
	{
		currentInstruction=null;
		for(Instruction i : instructionSet)
		{
			if(i.getCurrentState()==currentState && i.getInputSymbol() == tape.getTapeSymbolAt(tape.getTapeHeadX(),tape.getTapeHeadY()))
			{
				currentInstruction = i;
			}
		}
		return currentInstruction;
	}

	@Override
	public boolean isPaused()
	{
		return paused;
	}

	@Override
	public void setSpeed(int value)
	{
		this.sleep=value;
	}

	Tape tape;
	
	@Override
	public Tape getTape()
	{
		return tape;
	}
}
