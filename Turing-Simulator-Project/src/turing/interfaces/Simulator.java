package turing.interfaces;

import java.util.List;

public interface Simulator {

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
	 * As our tape is currently one-dimensional, have this method return the current position of the tape head.
	 * @return the column index of the tape head on the tape.
	 */
	public int getTapeHeadColumnIndex();
	
	/**
	 * The row index of the tape head is equivalent to its y-coordinate if you imagine the tape as a grid.
	 * As our tape is currently one-dimensional, have this method return 0.
	 * @return the row index of the tape head on the tape.
	 */
	public int getTapeHeadRowIndex();
	
	/**
	 * Sets the symbol on the tape at (<code>rowIndex</code>,<code>columnIndex</code>) to <code>symbol</code>.
	 * <p>
	 * Although our tape is currently one-dimensional, including <code>rowIndex</code> and <code>columnIndex</code>
	 * will make it possible to extend this to a two-dimensional tape at a later time.
	 * <p>
	 * For now, take <code>columnIndex</code> as the index of the cell on the tape.
	 * @param symbol The symbol to write to the tape.
	 * @param rowIndex The row index of the cell to write to.
	 * @param columnIndex The column index of the cell to write to.
	 * @return <code>true</code> if the cell was written to successfully, <code>false</code> if not.
	 */
	public boolean setTapeCellSymbol(char symbol,int rowIndex,int columnIndex);
	
	/**
	 * Returns the contents of the tape between beginIndex and endIndex inclusive.
	 * <p>
	 * The size of the returned list should be (beginIndex-endIndex)+1, as 0 is an index in the list.
	 * <p>
	 * As the tape is of infinite length, this method may request cells which do not exist
	 * in the data structure which represents the tape. This method should return blanks
	 * for any cells which do not exist.
	 * <p>
	 * For now, ignore the row parameters - these have been added to make way for a two-dimensional tape later.
	 * @param rowBeginIndex The first row of the section of the tape to return.
	 * @param rowEndIndex The last row of the section of the tape to return.
	 * @param colBeginIndex The first column of the section of the tape to return.
	 * @param colEndIndex The last column of the section of the tape to return.
	 * @return A List&lt;Character&gt; containing the cells on the tape between
	 * <code>beginIndex</code> and <code>endIndex</code>
	 */
	public List<Character> getTapeContents(int rowBeginIndex,int rowEndIndex,int colBeginIndex,int colEndIndex);

	/**
	 * Returns the {@link Instruction} currently being executed by the Turing simulator.
	 * @return the {@link Instruction} currently being executed by the Turing simulator.
	 */
	public Instruction getCurrentInstruction();
}
