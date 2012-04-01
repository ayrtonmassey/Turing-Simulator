package turing.simulator.project.gui;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.DefaultCellEditor;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;

import turing.simulator.project.interfaces.GUI;

public class TapeCellEditor extends AbstractCellEditor implements TableCellEditor {

	public static final int CLICK_COUNT_TO_START = 2;

	GUI gui;
	
	public TapeCellEditor(JTextField textField,GUI gui)
	{
		this.component = textField;
		this.gui=gui;
	}

	JComponent component;

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column)
	{
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
	public boolean stopCellEditing()
	{
		if(((JTextField)component).getText().length()>1)
		{
			return false;
		}
		else
		{
			fireEditingStopped();
			return true;
		}
	}
	
	@Override
	public Object getCellEditorValue()
	{
		return ((JTextField) component).getText();
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

}