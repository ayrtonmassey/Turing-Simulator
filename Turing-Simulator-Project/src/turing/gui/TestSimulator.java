package turing.gui;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import turing.Main;
import turing.TuringException;
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
	
	private List<List<Character>> tape = new ArrayList<List<Character>>();
	
	private List<Instruction> instructionSet = new ArrayList<Instruction>();
	
	private Instruction currentInstruction;
	
	private int tapeHeadColumnIndex;
	
	private int currentState = 0;
	
	private int sleep=Simulator.SPEED_INIT;
	
	private boolean paused = true;

	private int tapeOriginX;

	private int tapeOriginY;

	private int tapeHeadRowIndex;
	
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
	
	@Override
	public List<List<Character>> getTapeContents(int rowBeginIndex,int rowEndIndex,int colBeginIndex, int colEndIndex)
	{
		List<List<Character>> tapeContents = new ArrayList<List<Character>>();
		for(int y = rowBeginIndex; y<=rowEndIndex;y++)
		{
			List<Character> tapeRow = new ArrayList<Character>();
			for(int x = colBeginIndex; x<=colEndIndex;x++)
			{
				tapeRow.add(getTapeSymbolAt(x,y));
			}
			tapeContents.add(tapeRow);
		}
	
		return tapeContents;
	}
	
	@Override
	public int getTapeHeadColumnIndex()
	{
		//TODO: FILL THIS IN
		return tapeHeadColumnIndex;
	}
	
	@Override
	public int getTapeHeadRowIndex()
	{
		return tapeHeadRowIndex;
	}

	@Override
	public int getTapeOriginX()
	{
		return tapeOriginX;
	}

	@Override
	public int getTapeOriginY()
	{
		return tapeOriginY;
	}

	private Character getTapeSymbolAt(int x, int y)
	{
		if(y<tape.size()&&y>=0)
		{
			if(x<tape.get(y).size()&&x>=0)
			{
				return tape.get(y).get(x);
			}
		}
		return '_';
	}

	public void init()
	{
		tape.add(new ArrayList<Character>());
		try
		{
			this.setTapeCellSymbol('0', 0, 0);
			this.setTapeCellSymbol('1', 0, 1);
			this.setTapeCellSymbol('2', 0, 2);
			this.setTapeCellSymbol('3', 0, 3);
			this.setTapeCellSymbol('4', 0, 4);
			this.setTapeCellSymbol('5', 0, 5);
			this.setTapeCellSymbol('6', 0, 6);
			this.setTapeCellSymbol('7', 0, 7);
			this.setTapeCellSymbol('8', 0, 8);
			this.setTapeCellSymbol('9', 0, 9);
			this.setTapeCellSymbol('0', 0, 10);
			this.setTapeCellSymbol('1', 0, 11);
			this.setTapeCellSymbol('2', 0, 12);
			this.setTapeCellSymbol('3', 0, 13);
			this.setTapeCellSymbol('4', 0, 14);
			this.setTapeCellSymbol('5', 0, 15);
			
			this.setTapeCellSymbol('#', 3, 1);
			this.setTapeCellSymbol('#', 3, 9);
		}
		catch (TuringException ex)
		{
			Main.err.displayError(ex);
		}
		
		System.out.println("INITIAL TAPE:");
		printTape();
		
		tapeHeadColumnIndex = 7;
		tapeHeadRowIndex = 3;
		
		instructionSet.add(new TuringInstruction(0,'_',0,'_',Instruction.MOVE_RIGHT));
		instructionSet.add(new TuringInstruction(0,'#',1,'#',Instruction.MOVE_LEFT));
		
		instructionSet.add(new TuringInstruction(1,'_',1,'_',Instruction.MOVE_LEFT));
		instructionSet.add(new TuringInstruction(1,'#',0,'#',Instruction.MOVE_RIGHT));
	}
	
	public void insertColumns(int position,int count) throws TuringException
	{
		switch(position)
		{
		case Simulator.BEFORE:
			for(int y=0;y<tape.size();y++)
			{
				for(int i=0;i<count;i++)
				{
					tape.get(y).add(0,'_');
				}
			}
			tapeOriginX+=count;
			break;
		case Simulator.AFTER:
			for(int y=0;y<tape.size();y++)
			{
				for(int i=0;i<count;i++)
				{
					tape.get(y).add('_');
				}
			}
			break;
		default:
			throw new TuringException("Error inserting columns: "+position+" is not a valid position for insertion" +
										" - use Simulator.BEFORE or Simulator.AFTER.");
		}
	}

	private void insertRows(int position, int count) throws TuringException
	{
		switch(position)
		{
		case Simulator.BEFORE:
			for(int i=0;i<count;i++)
			{
				tape.add(0,newBlankRow());
			}
			tapeOriginY+=count;
			break;
		case Simulator.AFTER:
			for(int i=0;i<count;i++)
			{
				tape.add(newBlankRow());
			}
			break;
		default:
			throw new TuringException("Error inserting rows: "+position+" is not a valid position for insertion" +
										" - use Simulator.BEFORE or Simulator.AFTER.");
		}
	}

	@Override
	public boolean isTapeEditable()
	{
		return true;
	}

	private List<Character> newBlankRow()
	{
		List<Character> blankRow = new ArrayList<Character>();
		for(int i=0;i<tape.get(0).size();i++)
		{
			blankRow.add('_');
		}
		return blankRow;
	}

	@Override
    public void openFile(File f) throws TuringException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

	public void printTape()
	{
		for(int i = 0;i<tape.size();i++)
		{
			System.out.println(tape.get(i));
		}
	}

	public void run()
	{
		gui = new TuringGUI(this);
		
		init();
		
		//testTapeSetValueAt();
		
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

	@Override
	public boolean setTapeCellSymbol(char symbol, int rowIndex, int columnIndex) throws TuringException
	{
		if(isTapeEditable())
		{
			if(rowIndex<0)
			{
				int count = 0-rowIndex;
				insertRows(Simulator.BEFORE,count);
				rowIndex+=count;
			}
			else if(rowIndex>=tape.size())
			{
				int count = rowIndex-(tape.size()-1);
				insertRows(Simulator.AFTER,count);
			}
			
			if(columnIndex<0)
			{
				int count = 0-columnIndex;
				insertColumns(Simulator.BEFORE,count);
				columnIndex+=count;
			}
			else if(columnIndex>=tape.get(rowIndex).size())
			{
				int count = columnIndex-(tape.get(rowIndex).size()-1);
				insertColumns(Simulator.AFTER,count);
			}
			
			try
			{
				tape.get(rowIndex).set(columnIndex, symbol);
			}
			catch(IndexOutOfBoundsException ex)
			{
				throw new TuringException("Error setting tape symbol at ("+columnIndex+","+rowIndex+"): " +
											"The tape cell does not exist in the data structure.");
			}
			return true;
		}
		else
		{
			return false;
		}
	}

    public void testTapeSetValueAt()
	{
		System.out.println("\nTape Origin: ("+tapeOriginX+","+tapeOriginY+") Value: "+tape.get(tapeOriginY).get(tapeOriginX));
		
		try
		{
			setTapeCellSymbol('a', 0, 0);
			setTapeCellSymbol('b', 0, 4);
			setTapeCellSymbol('c', 0, -1);
			setTapeCellSymbol('d', 0, -10);
			setTapeCellSymbol('e', 0, 15);
			setTapeCellSymbol('f', 0, 21);
			setTapeCellSymbol('g',-1,0);
			setTapeCellSymbol('h',1,0);
			setTapeCellSymbol('i',10,0);
			setTapeCellSymbol('j',-5,0);
			setTapeCellSymbol('k',1,1);
			setTapeCellSymbol('l',-1,-1);
		}
		catch(TuringException ex)
		{
			Main.err.displayError(ex);
		}
		
		System.out.println("\nNEW TAPE:");
		printTape();
		
		System.out.println("\nTape Origin: ("+tapeOriginX+","+tapeOriginY+") Value: "+tape.get(tapeOriginY).get(tapeOriginX));
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
			this.setTapeCellSymbol(currentInstruction.getOutputSymbol(), tapeHeadRowIndex, tapeHeadColumnIndex);
			currentState = currentInstruction.getNextState();
			
			//Move the tape head
			switch(currentInstruction.getDirection())
			{
			case Instruction.MOVE_LEFT:
				tapeHeadColumnIndex--;
				break;
			case Instruction.MOVE_RIGHT:
				tapeHeadColumnIndex++;
				break;
			default:
				System.out.println("ERROR!");
				break;
			}
			
			//Check if tape head is still inside the tape data structure
			if(tapeHeadRowIndex<0)
			{
				insertRows(Simulator.BEFORE,1);
			}
			else if(tapeHeadRowIndex>tape.size())
			{
				insertRows(Simulator.AFTER,1);
			}
			
			if(tapeHeadColumnIndex<0)
			{
				insertColumns(Simulator.BEFORE,1);
			}
			else if(tapeHeadColumnIndex>tape.get(0).size())
			{
				insertColumns(Simulator.AFTER,1);
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
			if(i.getCurrentState()==currentState && i.getInputSymbol() == getTapeSymbolAt(tapeOriginX+tapeHeadColumnIndex,tapeOriginY+tapeHeadRowIndex))
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
}
