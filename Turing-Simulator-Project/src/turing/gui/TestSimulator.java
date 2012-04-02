package turing.gui;
import turing.interfaces.GUI;
import turing.interfaces.Simulator;


public class TestSimulator implements Simulator {

	static GUI gui;
	
	private int currentState = 0;
	
	public static void main(String[] args)
	{
		new TestSimulator();
	}
	
	public void run()
	{
		gui = new TuringGUI(this);
		
		while(true)
		{
			//find instruction
			//execute instruction
			currentState=1000;
			gui.update();
		}
	}
	
	public TestSimulator()
	{
		run();
	}
	
	@Override
	public int getCurrentState()
	{
		//TODO: FILL THIS IN
		return currentState;
	}

	@Override
	public int getTapeHeadColumnIndex()
	{
		//TODO: FILL THIS IN
		return 0;
	}

	@Override
	public boolean isTapeEditable()
	{
		return true;
	}

	@Override
	public int getTapeHeadRowIndex()
	{
		return 0;
	}

	@Override
	public boolean setTapeCellSymbol(char symbol, int rowIndex, int columnIndex)
	{
		// TODO Auto-generated method stub
		return true;
	}

	
	
}
