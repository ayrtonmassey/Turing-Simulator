package turing.interfaces;

public interface Instruction {

	public static final int MOVE_UP = 0, MOVE_DOWN = 1, MOVE_LEFT = 2, MOVE_RIGHT = 3, HALT = 4;
	public static final String QUINTUPLET_REGEX = "^\\([0-9]+,.,[0-9]+,.,[UDLRH]\\)$";

	public int getCurrentState();

	public int getDirection();

	public char getInputSymbol();

	public int getNextState();

	public char getOutputSymbol();

}
