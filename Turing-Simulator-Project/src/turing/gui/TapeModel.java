package turing.gui;

import javax.swing.table.AbstractTableModel;

import turing.Main;
import turing.TuringException;
import turing.interfaces.GUI;

public class TapeModel extends AbstractTableModel {
	
	/* 
	 * Although the tape contains char values, using String allows
	 * us to use the default cell editor for the JTable
	 */
	public String[][] tapeData;
	
	GUI gui;
	TapePanel parent;
	
	/**
	  * @param gui The GUI for the Turing Simulator.
	  */
	TapeModel(GUI gui,TapePanel parent)
	{
		this.gui = gui;
		this.parent = parent;
		
		initTapeData();
	}
	
	/**
	 * Returns <code>String.class</code> regardless of <code>columnIndex</code>.
	 */
	@Override
	public Class<?> getColumnClass(int columnIndex)
	{
		return String.class;
	}
	
	@Override
	public int getColumnCount()
	{
		return tapeData[0].length;
	}

	/**
	 * Returns <code>null</code>. Columns in the tape model have no column name.
	 * @return <code>null</code>
	 */
	@Override
	public String getColumnName(int columnIndex)
	{
		return null;
	}

	@Override
	public int getRowCount()
	{
		return tapeData.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex)
	{
		return tapeData[rowIndex][columnIndex];
	}

	/**
	 * Initialises the data in this TableModel to blank (underscore) symbols.
	 */
	private void initTapeData()
	{
		tapeData = new String[parent.getRowCount()][parent.getColumnCount()];
		for(int y=0;y<tapeData.length;y++)
		{
			for(int x=0;x<tapeData[y].length;x++)
			{
				tapeData[y][x]="_";
			}
		}
	}

	/**
	 * Returns <code>true</code> if the Simulator tape is editable.
	 * @return <code>true</code> if the Simulator tape is editable, <code>false</code> if not.
	 * @see turing.interfaces.Simulator#isTapeEditable()
	 */
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex)
	{
		if(gui.getSimulator().getTape().isTapeEditable())
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * Sets the value on the Simulator tape at <code>rowIndex</code>,<code>columnIndex</code> to <code>aValue</code>.
	 * Note that the value is also changed on the Simulator tape, not only in the GUI.
	 * @see turing.interfaces.Simulator#setValueOnTape(char,int,int)
	 */
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex)
	{
		String sValue="ERROR";
		
		if(aValue instanceof String)
		{
			sValue = (String)aValue;
		}
		else if(aValue instanceof Character)
		{
			sValue = ((Character)aValue).toString();
		}
		
		if(sValue.equals(""))
		{
			sValue="_";
		}
		
		if(sValue.length()==1)
		{
			tapeData[rowIndex][columnIndex] = sValue;
			fireTableCellUpdated(rowIndex, columnIndex);
		}
		else
		{
			Main.err.displayError(new TuringException("Invalid Input - The tape may only contain one symbol per cell."));
		}
		
		parent.repaint();
	}
}
