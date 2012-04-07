package turing.gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

import turing.interfaces.GUI;
import turing.interfaces.Instruction;

public class InstructionSetModel extends AbstractListModel {

	List<Instruction> instructionSet = new ArrayList<Instruction>();
	
	GUI gui;
	
	public InstructionSetModel(GUI gui)
	{
		this.gui = gui;

		init();
	}
	
	public void init()
	{
		instructionSet = gui.getSimulator().getInstructionSet();
	}

	@Override
	public int getSize()
	{
		return instructionSet.size();
	}

	@Override
	public Instruction getElementAt(int index)
	{
		return instructionSet.get(index);
	}

	public void setElementAt(int index, Instruction i)
	{
		synchronized(instructionSet)
		{
			instructionSet.set(index, i);
			this.fireContentsChanged(this, index, index);
			updateSimulatorInstructionSet();
		}
	}
	
	public void addElement(Instruction i)
	{
		synchronized(instructionSet)
		{
			instructionSet.add(i);
			this.fireIntervalAdded(this, instructionSet.size()-1,instructionSet.size()-1);
			updateSimulatorInstructionSet();	
		}
	}
	
	public void removeElement(int index)
	{
		synchronized(instructionSet)
		{
			instructionSet.remove(index);
			this.fireIntervalRemoved(this, index, index);
			updateSimulatorInstructionSet();
		}
	}
	
	public void updateSimulatorInstructionSet()
	{
		gui.getSimulator().setInstructionSet(instructionSet);
	}

}
