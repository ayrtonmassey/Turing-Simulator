package turing.interfaces;

public interface Instruction {
	
	public static final int MOVE_LEFT=0,MOVE_RIGHT=1,HALT=2;
	
	public int getCurrentState();
	public int getNextState();
	public char getInputSymbol();
	public char getOutputSymbol();
	public int getDirection();
	
}
