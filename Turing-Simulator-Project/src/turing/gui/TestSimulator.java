package turing.gui;
import java.util.ArrayList;
import java.util.List;

import turing.Main;
import turing.interfaces.GUI;
import turing.interfaces.Instruction;
import turing.interfaces.Simulator;


public class TestSimulator implements Simulator {

	GUI gui;
	
	private List<Character> tape = new ArrayList<Character>();
	
	private List<Instruction> instructionSet = new ArrayList<Instruction>();
	
	private Instruction currentInstruction;
	
	private int tapeHeadColumnIndex;
	
	private int currentState = 0;
	
	private int sleep = 500;
	
	public static void main(String[] args)
	{
		new TestSimulator();
	}
	
	boolean paused=false;
	public void run()
	{
		gui = new TuringGUI(this);
		
		init();
		
		//gui.update();
		
		//*
		while(true)
		{
			while(!paused)
			{
				currentInstruction=null;
				for(Instruction i : instructionSet)
				{
					if(i.getCurrentState()==currentState && i.getInputSymbol() == tape.get(tapeHeadColumnIndex))
					{
						currentInstruction = i;
					}
				}
				
				if(currentInstruction!=null)
				{
					this.setTapeCellSymbol(currentInstruction.getOutputSymbol(), 0, tapeHeadColumnIndex);
					currentState = currentInstruction.getNextState();
					
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
					
					gui.update();
				
					try
					{
						Thread.sleep(sleep);
					}
					catch (InterruptedException ex)
					{
						Main.err.displayError(ex);
					}
				}
			}
		}//*/
	}
	
	public void init()
	{
		tape.add('0');
		tape.add('1');//
		tape.add('2');
		tape.add('3');//
		tape.add('#');
		tape.add('_');//
		tape.add('_');
		tape.add('_');//
		tape.add('_');
		tape.add('_');//
		tape.add('_');
		tape.add('_');//
		tape.add('_');
		tape.add('_');//
		tape.add('#');
		tape.add('5');//
		
		System.out.println(tape);
		
		tapeHeadColumnIndex = 7;
		
		instructionSet.add(new TuringInstruction(0,'_',0,'_',Instruction.MOVE_RIGHT));
		instructionSet.add(new TuringInstruction(0,'#',2147483647,'#',Instruction.MOVE_LEFT));
		
		instructionSet.add(new TuringInstruction(2147483647,'_',2147483647,'_',Instruction.MOVE_LEFT));
		instructionSet.add(new TuringInstruction(2147483647,'#',0,'#',Instruction.MOVE_RIGHT));
	}
	
	public TestSimulator()
	{
		run();
	}
	
	@Override
	public int getCurrentState()
	{
		//TODO: FILL THIS IN
		return currentState;
	}

	@Override
	public int getTapeHeadColumnIndex()
	{
		//TODO: FILL THIS IN
		return tapeHeadColumnIndex;
	}

	@Override
	public boolean isTapeEditable()
	{
		return true;
	}

	@Override
	public int getTapeHeadRowIndex()
	{
		return 0;
	}

	@Override
	public boolean setTapeCellSymbol(char symbol, int rowIndex, int columnIndex)
	{
		if(isTapeEditable())
		{
			if(columnIndex<0)
			{
				for(;columnIndex<0;columnIndex++)
				{
					tape.add(0,'_');
				}
			}
			else if(columnIndex>=tape.size())
			{
				for(int i=tape.size();i<=columnIndex;i++)
				{
					tape.add('_');
				}
			}
			
			tape.set(columnIndex, symbol);
			return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	public List<Character> getTapeContents(int rowBeginIndex,int rowEndIndex,int beginIndex, int endIndex)
	{
		endIndex++;
		if(beginIndex<0)
		{
			for(;beginIndex<0;beginIndex++,endIndex++)
			{
				tape.add(0,'_');
			}
		}
		
		if(endIndex>tape.size()-1)
		{
			for(int i=tape.size();i<endIndex;i++)
			{
				tape.add('_');
			}
		}
		
		return tape.subList(beginIndex,endIndex);
	}

	@Override
	public Instruction getCurrentInstruction()
	{
		return currentInstruction;
	}
}
