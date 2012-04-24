package turing.gui;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
	
	private List<Instruction> history = new ArrayList<Instruction>();
	
	private int currentState = 0;
	
	private int sleep=Simulator.SPEED_INIT;
	
	private boolean paused = true;

	
	Tape tape;
	
	public TestSimulator()
	{
		run();
	}
	
	private void executeCurrentInstruction()
	{
		try
		{
			//Update history
			history.add(currentInstruction);
			
			if(history.size()>Simulator.HISTORY_SIZE_LIMIT)
			{
				history.remove(0);
			}
			
			//Update symbol and state
			tape.setTapeCellSymbol(currentInstruction.getOutputSymbol(), tape.getTapeHeadX(), tape.getTapeHeadY());
			currentState = currentInstruction.getNextState();
			
			//Move the tape head
			switch(currentInstruction.getDirection())
			{
			case Instruction.MOVE_UP:
				tape.setTapeHeadY(tape.getTapeHeadY()-1);
				break;
			case Instruction.MOVE_DOWN:
				tape.setTapeHeadY(tape.getTapeHeadY()+1);
				break;
			case Instruction.MOVE_LEFT:
				tape.setTapeHeadX(tape.getTapeHeadX()-1);
				break;
			case Instruction.MOVE_RIGHT:
				tape.setTapeHeadX(tape.getTapeHeadX()+1);
				break;
			default:
				this.pause();
				break;
			}
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
	public Instruction getCurrentInstruction()
	{
		return currentInstruction;
	}

	

    @Override
	public int getCurrentState()
	{
		return currentState;
	}

	@Override
	public List<Instruction> getHistory()
	{
		return history;
	}

	@Override
	public Tape getTape()
	{
		return tape;
	}

	public void init()
	{
		initTestTape();
		
		initTestInstructions();
		
		System.out.println("INITIAL TAPE:");
		System.out.println(tape);
	}

	public void initTape(int dimension)
	{
		tape = new Tape(dimension,0,0);
		
		System.out.println("\nNEW TAPE:");
		System.out.println(tape);
	}

	public void initTestInstructions()
	{
		instructionSet.add(new TuringInstruction(0,'_',0,'_',Instruction.MOVE_RIGHT));
		instructionSet.add(new TuringInstruction(0,'#',1,'#',Instruction.MOVE_LEFT));
		
		instructionSet.add(new TuringInstruction(1,'_',1,'_',Instruction.MOVE_LEFT));
		instructionSet.add(new TuringInstruction(1,'#',0,'#',Instruction.MOVE_RIGHT));
		
		for(Instruction i : instructionSet)
		{
			System.out.println(i);
		}
		System.out.println();
	}

	public void initTestTape()
	{
		tape = new Tape(Simulator.TWO_DIMENSIONAL,0,0);
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
	}

	@Override
	public boolean isPaused()
	{
		return paused;
	}

	@Override
	public void openFile(File f, int dimension) throws TuringException
	{
		this.reset(dimension);
		
		if(dimension == Simulator.ONE_DIMENSIONAL)
		{
			try
			{
				Scanner s = new Scanner(f);
				
				int instructionCount = Integer.parseInt(s.nextLine());
				
				List<Instruction> instructionSet = new ArrayList<Instruction>();
				
				for(int i=0;i<instructionCount;i++)
				{
					instructionSet.add(new TuringInstruction(s.nextLine()));
				}
				
				Tape tape = new Tape(Simulator.ONE_DIMENSIONAL);
				
				String tapeString = s.nextLine();
				boolean tapeHeadPosition=false;
				for(int i=0;i<tapeString.length()-2;i++)
				{
					if(tapeString.charAt(i+1)=='<'&&tapeString.charAt(i+3)=='>'&&!tapeHeadPosition)
					{
						tapeHeadPosition=true;
						tape.setTapeCellSymbol(tapeString.charAt(i+2), i, 0);
						tape.setTapeHeadX(i);
						tapeString = tapeString.substring(0,i)+tapeString.substring(i+2,tapeString.length());
					}
					else
					{
						tape.setTapeCellSymbol(tapeString.charAt(i+1), i, 0);
					}
				}
				
				this.tape=tape;
				this.instructionSet=instructionSet;
			}
			catch (FileNotFoundException ex)
			{
				throw new TuringException("Could not find file: "+f.getAbsolutePath());
			}
		}
		else
		{
			this.initTestTape();
			this.initTestInstructions();
		}
	}
	
	@Override
	public boolean pause()
	{
		paused=true;
		gui.updateStatusMessage("Simulation paused.");
		return true;
	}

	@Override
	public boolean play()
	{
		paused=false;
		gui.updateStatusMessage("Simulation resumed.");
		return true;
	}
	
	@Override
	public void reset(int dimension)
	{
		this.paused=true;
		this.currentState=0;
		this.history.clear();
		this.instructionSet=new ArrayList<Instruction>();
		this.initTape(dimension);
	}
	
	public void run()
	{
		init();
		
		gui = new TuringGUI(this);
		
		testTapeSetValueAt();
		
		gui.update();
		
		//*
		while(true)
		{
			while(!paused)
			{			
				step();
				
				try
				{
					Thread.sleep(sleep);
				}
				catch(InterruptedException ex)
				{
					System.out.println("Simulation was woken up!");
				}
			}
		}//*/
	}

	@Override
	public void saveFile(File f) throws TuringException
	{
		// TODO Auto-generated method stub
		System.out.println("Save File Option");
	}
	
	@Override
	public void setSpeed(int value)
	{
		this.sleep=value;
	}

	@Override
	public void step()
	{
		findCurrentInstruction();
		if(currentInstruction!=null)
		{
			executeCurrentInstruction();
		}
		else
		{
			//Pause the simulator and display error message
			paused=true;
			JOptionPane.showMessageDialog(null, "No instruction specified for current symbol and state.");
		}
		gui.update();
	}

	public void testTapeSetValueAt()
	{
    	System.out.println("\nTape Origin: ("+tape.getTapeOriginX()+","+tape.getTapeOriginY()+") Value: "+tape.getTapeSymbolAt(0, 0));
    	
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
		System.out.println(tape);
		
		System.out.println("\nTape Origin: ("+tape.getTapeOriginX()+","+tape.getTapeOriginY()+") Value: "+tape.getTapeSymbolAt(0, 0));
	}

	@Override
	public List<Instruction> getInstructionSet()
	{
		return instructionSet;
	}

	@Override
	public Instruction createInstruction(int currentState, char inputSymbol, int nextState, char outputSymbol, int direction) throws TuringException
	{
		return new TuringInstruction(currentState,inputSymbol,nextState,outputSymbol,direction);
	}

	@Override
	public void setInstructionSet(List<Instruction> instructionSet)
	{
		synchronized(this.instructionSet)
		{
			this.instructionSet = instructionSet;
		}
	}

	@Override
	public void setCurrentState(int state) throws TuringException
	{
		if(state>=0)
		{
			this.currentState=state;
		}
		else
		{
			throw new TuringException("The state "+state+" is not a valid state for this Turing simulator.");
		}
	}
}
