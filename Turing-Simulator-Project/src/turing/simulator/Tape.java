package turing.simulator;

import java.util.ArrayList;
import java.util.List;

import turing.Main;
import turing.TuringException;
import turing.interfaces.Simulator;

public class Tape {

	private List<List<Character>> tape = new ArrayList<List<Character>>();
	
	private int tapeHeadColumnIndex;
	
	private int originX;

	private int originY;
	
	private int tapeHeadRowIndex;
	
	public Tape(int originX, int originY)
	{
		this(originX,originY,originX,originY);
	}
	
	public Tape(int originX,int originY, int tapeHeadX, int tapeHeadY)
	{
		this.originX = originX;
		this.originY = originY;
		this.tapeHeadColumnIndex = tapeHeadX;
		this.tapeHeadRowIndex = tapeHeadY;
		
		tape.add(new ArrayList<Character>());
	}
	
	/**
	 * Returns the contents of the tape between rowBeginIndex, rowEndIndex, colBeginIndex and colEndIndex.
	 * <p>
	 * As the tape is of infinite length, this method may request cells which do not exist
	 * in the data structure which represents the tape. This method should return blanks
	 * for any cells which do not exist.
	 * @param rowBeginIndex The first row of the section of the tape to return.
	 * @param rowEndIndex The last row of the section of the tape to return.
	 * @param colBeginIndex The first column of the section of the tape to return.
	 * @param colEndIndex The last column of the section of the tape to return.
	 * @return A List&lt;Character&gt; containing the cells on the tape between
	 * <code>beginIndex</code> and <code>endIndex</code>
	 */
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
	
	/**
	 * The column index of the tape head is equivalent to its x-coordinate if you imagine the tape as a grid.
	 * @return the column index of the tape head on the tape.
	 */
	public int getTapeHeadColumnIndex()
	{
		return tapeHeadColumnIndex;
	}
	
	/**
	 * The row index of the tape head is equivalent to its y-coordinate if you imagine the tape as a grid.
	 * @return the row index of the tape head on the tape.
	 */
	public int getTapeHeadRowIndex()
	{
		return tapeHeadRowIndex;
	}

	/**
	 * Returns the original x-coordinate of the tape head.
	 * <p>
	 * The origin is the cell at position (0,0) - the initial position of the tape head.
	 * Because the tape data structure can change, the origin cell may move to a different position.
	 * To counter this, this method should return the current position of the origin cell.
	 * @return The x-coordinate of the current position of the origin cell.
	 */
	public int getTapeOriginX()
	{
		return originX;
	}
	
	/**
	 * Returns the original y-coordinate of the tape head.
	 * <p>
	 * The origin is the cell at position (0,0) - the initial position of the tape head.
	 * Because the tape data structure can change, the origin cell may move to a different position.
	 * To counter this, this method should return the current position of the origin cell.
	 * @return The y-coordinate of the current position of the origin cell.
	 */
	public int getTapeOriginY()
	{
		return originY;
	}
	

	public Character getTapeSymbolAt(int x, int y)
	{
		if(y+originY<tape.size()&&y+originY>=0)
		{
			if(x+originX<tape.get(y+originY).size()&&x+originX>=0)
			{
				return tape.get(y+originY).get(x+originX);
			}
		}
		return '_';
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
			originX+=count;
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
	
	public void insertRows(int position, int count) throws TuringException
	{
		switch(position)
		{
		case Simulator.BEFORE:
			for(int i=0;i<count;i++)
			{
				tape.add(0,newBlankRow());
			}
			originY+=count;
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
	
	/**
	 * @return <code>true</code> if the tape is editable, <code>false</code> if not.
	 */
	public boolean isTapeEditable()
	{
		return true;
	}

	private List<Character> newBlankRow()
	{
		List<Character> blankRow = new ArrayList<Character>();
		
		if(!tape.isEmpty())
		{
			for(int i=0;i<tape.get(0).size();i++)
			{
				blankRow.add('_');
			}
		}
		
		return blankRow;
	}

	public void print()
	{
		for(int i = 0;i<tape.size();i++)
		{
			System.out.println(tape.get(i));
		}
	}
	
	/**
	 * Sets the symbol on the tape at (<code>rowIndex</code>,<code>columnIndex</code>) to <code>symbol</code>.
	 * <p>
	 * Because the tape is of infinite length, this method may try to edit cells which are not in the
	 * tape data structure. This method should create those cells and then assign the value to them.
	 * @param symbol The symbol to write to the tape.
	 * @param rowIndex The row index of the cell to write to.
	 * @param columnIndex The column index of the cell to write to.
	 * @return <code>true</code> if the cell was written to successfully, <code>false</code> if not.
	 * @throws TuringException 
	 */
	public boolean setTapeCellSymbol(char symbol, int x, int y) throws TuringException
	{
		int columnIndex = x+originX;
		int rowIndex = y+originY;
		
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

	public int getTapeHeadX()
	{
		return tapeHeadColumnIndex;
	}
	
	public int getTapeHeadY()
	{
		return tapeHeadRowIndex;
	}
	
	public void setTapeHeadX(int x)
	{
		this.tapeHeadColumnIndex = x;
		
		try
		{
			if(tapeHeadColumnIndex<0)
			{
				insertColumns(Simulator.BEFORE,1);
			}
			else if(tapeHeadColumnIndex>tape.get(0).size())
			{
				insertColumns(Simulator.AFTER,1);
			}
		}
		catch(TuringException ex)
		{
			Main.err.displayError(ex);
		}
	}
	
	public void setTapeHeadY(int y)
	{
		this.tapeHeadRowIndex = y;
		
		try
		{
			if(tapeHeadRowIndex<0)
			{
				insertRows(Simulator.BEFORE,1);
			}
			else if(tapeHeadRowIndex>tape.size())
			{
				insertRows(Simulator.AFTER,1);
			}
		}
		catch(TuringException ex)
		{
			Main.err.displayError(ex);
		}
	}
	
}