package turing.gui;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;

import turing.Main;
import turing.TuringException;
import turing.interfaces.GUI;

public class TapeCellEditor extends AbstractCellEditor implements TableCellEditor {

	public static final int CLICK_COUNT_TO_START = 2;

	GUI gui;
	TapePanel parent;
	
	JComponent component;

	int row;
	
	int column;
	/**
	 * Creates a new TapeCellEditor.
	 * @param textField The JTextField to use as the editor component.
	 * @param gui The GUI for the Turing machine simulator.
	 */
	public TapeCellEditor(GUI gui,TapePanel parent,JTextField textField)
	{
		this.component = textField;
		this.parent=parent;
		this.gui=gui;
	}

	@Override
	public Object getCellEditorValue()
	{
		return ((JTextField) component).getText();
	}
	
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column)
	{
		this.row=row;
		this.column=column;
		
		String sValue = (String)value;
		
		if(sValue.equals("_"))
		{
			((JTextField) component).setText("");
		}
		else
		{
			((JTextField) component).setText(sValue);
		}

		return component;
	}
	
	@Override
	public boolean isCellEditable(EventObject anEvent)
	{
		if(gui.getSimulator().isTapeEditable())
		{
			if (anEvent instanceof MouseEvent)
			{
				return ((MouseEvent) anEvent).getClickCount() >= CLICK_COUNT_TO_START;
			}
		}
		return false;
	}

	@Override
	public boolean stopCellEditing()
	{
		String sValue = ((JTextField)component).getText();
		if(sValue.length()<=1)
		{
			char cValue;
			if(sValue.length()==1)
			{
				cValue = sValue.charAt(0);
			}
			else
			{
				cValue='_';
			}
			
			try
			{
				if(gui.getSimulator().setTapeCellSymbol(cValue, parent.getTapeBeginRowIndex()+row, parent.getTapeBeginColumnIndex()+column))
				{
					fireEditingStopped();
					return true;
				}
			}
			catch(TuringException ex)
			{
				Main.err.displayError(ex);
				return false;
			}
		}
		return false;
	}

}