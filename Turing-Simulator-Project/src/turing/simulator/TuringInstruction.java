package turing.simulator;

import turing.TuringException;
import turing.interfaces.Instruction;

public class TuringInstruction implements Instruction {

	private int currentState;
	private char inputSymbol;
	private int nextState;
	private char outputSymbol;
	private int direction;

	public TuringInstruction(int currentState,char inputSymbol,int nextState,char outputSymbol,int direction)
	{
		this.currentState=currentState;
		this.inputSymbol=inputSymbol;
		this.nextState=nextState;
		this.outputSymbol=outputSymbol;
		this.direction=direction;
	}
	
	public TuringInstruction(String quintuplet) throws TuringException
	{
		if(quintuplet.matches(Instruction.QUINTUPLET_REGEX))
		{
			quintuplet = quintuplet.substring(1,quintuplet.length()-1);
			
			String[] instructionArray = quintuplet.split(",");
			
			int currentState = Integer.parseInt(instructionArray[0]);
			char inputSymbol = instructionArray[1].charAt(0);
			int nextState = Integer.parseInt(instructionArray[2]);
			char outputSymbol = instructionArray[3].charAt(0);
			char directionChar = instructionArray[4].charAt(0);
			int direction;
			switch(directionChar)
			{
			case 'U':
				direction = Instruction.MOVE_UP;
				break;
			case 'D':
				direction = Instruction.MOVE_DOWN;
				break;
			case 'L':
				direction = Instruction.MOVE_LEFT;
				break;
			case 'R':
				direction = Instruction.MOVE_RIGHT;
				break;
			case 'H':
				direction = Instruction.HALT;
				break;
			default:
				direction = -1;
			}
			
			this.currentState=currentState;
			this.inputSymbol=inputSymbol;
			this.nextState=nextState;
			this.outputSymbol=outputSymbol;
			this.direction=direction;
		}
		else
		{
			throw new TuringException("Quintuplet "+quintuplet+" is not a valid instruction for this Turing machine simulator.");
		}
	}

	@Override
	public int getCurrentState()
	{
		return currentState;
	}

	public void setCurrentState(int currentState)
	{
		this.currentState = currentState;
	}

	@Override
	public char getInputSymbol()
	{
		return inputSymbol;
	}

	public void setInputSymbol(char inputSymbol)
	{
		this.inputSymbol = inputSymbol;
	}

	@Override
	public int getNextState()
	{
		return nextState;
	}

	public void setNextState(int nextState)
	{
		this.nextState = nextState;
	}

	@Override
	public char getOutputSymbol()
	{
		return outputSymbol;
	}

	public void setOutputSymbol(char outputSymbol)
	{
		this.outputSymbol = outputSymbol;
	}

	@Override
	public int getDirection()
	{
		return direction;
	}
	
	@Override
	public String toString()
	{
		String directionString="";
		switch(direction)
		{
		case Instruction.MOVE_UP:
			directionString = "U";
			break;
		case Instruction.MOVE_DOWN:
			directionString = "D";
			break;
		case Instruction.MOVE_LEFT:
			directionString = "L";
			break;
		case Instruction.MOVE_RIGHT:
			directionString = "R";
			break;
		case Instruction.HALT:
			directionString = "H";
			break;
		default:
			break;
		}
		return "("+currentState+","+inputSymbol+","+nextState+","+outputSymbol+","+directionString+")";
	}

}
