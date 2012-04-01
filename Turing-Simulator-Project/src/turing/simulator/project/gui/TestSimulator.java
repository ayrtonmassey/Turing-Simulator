package turing.simulator.project.gui;
import turing.simulator.project.interfaces.GUI;
import turing.simulator.project.interfaces.Simulator;


public class TestSimulator implements Simulator {

	static GUI gui;
	
	public static void main(String[] args)
	{
		new TestSimulator();
	}
	
	public TestSimulator()
	{
		run();
	}
	
	public void run()
	{
		gui = new TuringGUI(this);
		
		while(true)
		{
			//find instruction
			//execute instruction
		
			gui.update();
		}
	}

	@Override
	public int getCurrentState()
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
	public int getTapeHeadIndex()
	{
		//TODO: FILL THIS IN
		return 0;
	}
	
}
