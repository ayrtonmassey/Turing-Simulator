package turing.simulator;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


import turing.TuringException;
import turing.gui.TuringGUI;
import turing.interfaces.GUI;
import turing.interfaces.Instruction;
import turing.interfaces.Simulator;


public class TuringSimulator implements Simulator {

	GUI gui;
	
	private List<List<Character>> tape = new ArrayList<List<Character>>();
	
	private List<Instruction> instructionSet = new ArrayList<Instruction>();
	
	private Instruction currentInstruction;
	
	private int tapeHeadColumnIndex;
	
	private int currentState = 0;
	
	private int sleep = 500;
	
	public static void main(String[] args)
	{
		new TuringSimulator();
	}
	
	boolean paused=false;

	private int tapeOriginX;

	private int tapeOriginY;

	private int tapeHeadRowIndex;

       List<Instruction> instructions = new ArrayList<Instruction>();


        /**
         * simulator
         *  every time you carry out an instruction(after instruction), call gui.update();
         * use carryOutInstruction as a guide.
         */
	public void run()
	{
                     init();
		 gui = new TuringGUI(this);

        boolean running = true;
        while (running) {


            char currentSymbol =getTapeSymbolAt(tapeHeadRowIndex,tapeHeadColumnIndex);

            Instruction toExecute = null;
            for (Instruction i : instructions)  {
                if (i.getCurrentState() == currentState && i.getInputSymbol() == currentSymbol) {
                    toExecute = i;
                }
            }

            if (toExecute != null) {

                currentState = toExecute.getNextState();
                tape[tapeHeadColumnIndex] = toExecute.getOutputSymbol();
                switch (toExecute.getDirection()) {
                    case Instruction.MOVE_LEFT:
                        tapeHeadColumnIndex--;
                        gui.update();
                        break;
                    case Instruction.MOVE_RIGHT:
                        tapeHeadColumnIndex++;
                        gui.update();
                        break;
                    case Instruction.HALT:
                        gui.update();

                        running = false;
                        break;
                    default:
                        break;
                }
            } else {
                //throw error
            }
        }
	}
	
	
	/**
         * initialise
         */
	public void init()
	{
   instructionSet = openfile();
   tape=openFile();
        


	}
	
	public void printTape()
	{
		for(int i = 0;i<tape.size();i++)
		{
			System.out.println(tape.get(i));
		}
	}
	
	public TuringSimulator()
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
		return tapeHeadRowIndex;
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

	private List<Character> newBlankRow()
	{
		List<Character> blankRow = new ArrayList<Character>();
		for(int i=0;i<tape.get(0).size();i++)
		{
			blankRow.add('_');
		}
		return blankRow;
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

	@Override
	public Instruction getCurrentInstruction()
	{
		return currentInstruction;
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

    @Override
    public void openFile(File f) throws TuringException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
