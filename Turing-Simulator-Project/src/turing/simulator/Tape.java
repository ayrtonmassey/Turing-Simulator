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
	
	int dimension;
	
	/**
	 * Creates a new Tape.
	 * @param dimension The dimension of this tape - <code>Simulator.ONE_DIMENSIONAL</code> for 1D, <code>Simulator.TWO_DIMENSIONAL</code> for 2D.
	 */
	public Tape(int dimension)
	{
		this(dimension,0,0);
	}
	
	/**
	 * Creates a new tape.
	 * @param dimension The dimension of this tape - <code>Simulator.ONE_DIMENSIONAL</code> for 1D, <code>Simulator.TWO_DIMENSIONAL</code> for 2D.
	 * @param tapeHeadX The x-coordinate of the tape head.
	 * @param tapeHeadY The y-coordinate of the tape head.
	 */
	public Tape(int dimension, int tapeHeadX, int tapeHeadY)
	{
		tape.add(new ArrayList<Character>());
		
		this.dimension=dimension;
		
		this.originX = 0;
		this.originY = 0;
		this.setTapeHeadX(tapeHeadX);
		this.setTapeHeadY(tapeHeadY);
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
	 * @return the dimension of this tape - <code>Simulator.ONE_DIMENSIONAL</code> for 1D, <code>Simulator.TWO_DIMENSIONAL</code> for 2D.
	 */
	public int getTapeDimension()
	{
		return dimension;
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
	 * @return The x-coordinate of the tape head.
	 */
	public int getTapeHeadX()
	{
		return tapeHeadColumnIndex;
	}
	

	/**
	 * @return The x-coordinate of the tape head.
	 */
	public int getTapeHeadY()
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
	
	/**
	 * Returns the symbol at (x,y) on the tape.
	 * @param x The x-coordinate of the cell to return.
	 * @param y The y-coordinate of the cell to return.
	 * @return The symbol contained in the cell at (x,y) as a {@link Character}.
	 */
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

	
	private void insertColumns(int position,int count) throws TuringException
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

	private void insertRows(int position, int count) throws TuringException
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

	/**
	 * Sets the symbol on the tape at (<code>rowIndex</code>,<code>columnIndex</code>) to <code>symbol</code>.
	 * <p>
	 * Because the tape is of infinite length, this method may try to edit cells which are not in the
	 * tape data structure. This method should create those cells and then assign the value to them.
	 * @param symbol The symbol to write to the tape.
	 * @param rowIndex The row index of the cell to write to.
	 * @param columnIndex The column index of the cell to write to.
	 * @return <code>true</code> if the cell was written to successfully, <code>false</code> if not.
	 * @throws TuringException if the tape cell could not be set.
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
	
	/**
	 * Sets the dimension of the tape (1D or 2D).
	 * @param dimension <code>Simulator.ONE_DIMENSIONAL</code> for 1D, <code>Simulator.TWO_DIMENSIONAL</code> for 2D.
	 */
	public void setTapeDimension(int dimension)
	{
		this.dimension = dimension;
	}
	
	/**
	 * Sets the x-coordinate of the tape head.
	 * <p>
	 * If the x-coordinate of the tape head is outside the tape's data structure,
	 * new columns will be inserted in to the data structure and the origin adjusted.
	 * @param x The x-coordinate of the tape head.
	 * @see #insertColumns(int, int)
	 */
	public void setTapeHeadX(int x)
	{
		this.tapeHeadColumnIndex = x;
		
		try
		{
			if(tapeHeadColumnIndex<0)
			{
				int count = 0-tapeHeadColumnIndex;
				insertColumns(Simulator.BEFORE,count);
			}
			else if(tapeHeadColumnIndex>=tape.get(tapeHeadRowIndex).size())
			{
				int count = tapeHeadColumnIndex-(tape.get(tapeHeadRowIndex).size()-1);
				insertColumns(Simulator.AFTER,count);
			}
		}
		catch(TuringException ex)
		{
			Main.err.displayError(ex);
		}
	}
	
	/**
	 * Sets the y-coordinate of the tape head.
	 * <p>
	 * If the y-coordinate of the tape head is outside the tape's data structure,
	 * new rows will be inserted in to the data structure and the origin adjusted.
	 * @param y The y-coordinate of the tape head.
	 * @see #insertRows(int, int)
	 */
	public void setTapeHeadY(int y)
	{
		this.tapeHeadRowIndex = y;
		
		try
		{
			if(tapeHeadRowIndex<0)
			{
				int count = 0-tapeHeadRowIndex;
				insertRows(Simulator.BEFORE,count);
			}
			else if(tapeHeadRowIndex>=tape.size())
			{
				int count = tapeHeadRowIndex-(tape.size()-1);
				insertRows(Simulator.AFTER,count);
			}
		}
		catch(TuringException ex)
		{
			Main.err.displayError(ex);
		}
	}
	
	@Override
	public String toString()
	{
		String tapeString = "";
		
		for(int y=0;y<tape.size();y++)
		{
			if(y==getTapeHeadY()+getTapeOriginY())
			{
				tapeString+=getTapeOriginX()+getTapeHeadX();
			}
			
			tapeString+="!";
			
			for(int x=0;x<tape.get(y).size();x++)
			{
				tapeString+=tape.get(y).get(x);
			}
			
			tapeString+="\n";
		}
		
		return tapeString;
	}
	
}
