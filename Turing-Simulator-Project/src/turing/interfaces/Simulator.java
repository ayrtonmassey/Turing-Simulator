package turing.interfaces;

import java.io.File;
import java.util.List;

import turing.TuringException;

public interface Simulator {

	public static final int BEFORE = 0,AFTER = 1;

	/**
	 * @return the current state the Turing machine is in.
	 */
	public int getCurrentState();

	/**
	 * @return <code>true</code> if the tape is editable, <code>false</code> if not.
	 */
	public boolean isTapeEditable();

	/**
	 * The column index of the tape head is equivalent to its x-coordinate if you imagine the tape as a grid.
	 * @return the column index of the tape head on the tape.
	 */
	public int getTapeHeadColumnIndex();
	
	/**
	 * The row index of the tape head is equivalent to its y-coordinate if you imagine the tape as a grid.
	 * @return the row index of the tape head on the tape.
	 */
	public int getTapeHeadRowIndex();
	
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
	public boolean setTapeCellSymbol(char symbol,int rowIndex,int columnIndex) throws TuringException;
	
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
	public List<List<Character>> getTapeContents(int rowBeginIndex,int rowEndIndex,int colBeginIndex,int colEndIndex);

	/**
	 * @return the {@link Instruction} currently being executed by the Turing simulator.
	 */
	public Instruction getCurrentInstruction();

	/**
	 * Returns the original x-coordinate of the tape head.
	 * <p>
	 * The origin is the cell at position (0,0) - the initial position of the tape head.
	 * Because the tape data structure can change, the origin cell may move to a different position.
	 * To counter this, this method should return the current position of the origin cell.
	 * @return The x-coordinate of the current position of the origin cell.
	 */
	public int getTapeOriginX();

	/**
	 * Returns the original y-coordinate of the tape head.
	 * <p>
	 * The origin is the cell at position (0,0) - the initial position of the tape head.
	 * Because the tape data structure can change, the origin cell may move to a different position.
	 * To counter this, this method should return the current position of the origin cell.
	 * @return The y-coordinate of the current position of the origin cell.
	 */
	public int getTapeOriginY();
	
	/**
	 * Opens the given file in the simulator.
	 * <p>
	 * @throws TuringException if the file is invalid or an error occurs while opening the file.
	 */
	public void openFile(File f) throws TuringException;
}
