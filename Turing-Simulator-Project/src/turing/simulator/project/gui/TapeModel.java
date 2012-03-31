package turing.simulator.project.gui;

import javax.swing.table.AbstractTableModel;

import turing.simulator.project.interfaces.GUI;

public class TapeModel extends AbstractTableModel {

	public static final int NUM_COLUMNS = 15,
							NUM_ROWS = 1;
	
	/* 
	 * Although the tape contains char values, using String allows
	 * us to use the default cell editor for the JTable
	 */
	public String[][] tapeData = new String[NUM_ROWS][NUM_COLUMNS];
	
	GUI gui;
	
	/**
	  * @param gui The GUI for the Turing Simulator.
	  */
	TapeModel(GUI gui)
	{
		this.gui = gui;
		
		initTapeData();
	}
	
	/**
	 * Initialises the data in this TableModel to blank (underscore) symbols.
	 */
	private void initTapeData()
	{
		for(int y=0;y<tapeData.length;y++)
		{
			for(int x=0;x<tapeData[y].length;x++)
			{
				tapeData[y][x]="_";
			}
		}
	}
	
	@Override
	public int getRowCount()
	{
		return NUM_ROWS;
	}

	@Override
	public int getColumnCount()
	{
		return NUM_COLUMNS;
	}

	@Override
	public String getColumnName(int columnIndex)
	{
		return null;
	}

	@Override
	public Class<?> getColumnClass(int columnIndex)
	{
		return String.class;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex)
	{
		if(gui.getSimulator().isTapeEditable())
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex)
	{
		return tapeData[rowIndex][columnIndex];
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex)
	{
		String sValue = (String)aValue;
		if(sValue.equals(""))
		{
			sValue="_";
		}
		
		if(sValue.length()==1)
		{
			tapeData[rowIndex][columnIndex] = sValue;
		}
		else
		{
			System.out.println("INVALID DATA TYPE");
			//TODO: DO STUFF
		}
		
		fireTableCellUpdated(rowIndex, columnIndex);
	}
}
