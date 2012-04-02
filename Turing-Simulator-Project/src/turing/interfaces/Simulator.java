package turing.interfaces;

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
}
