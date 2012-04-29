/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package turing.simulator;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import turing.TuringException;
import turing.interfaces.Instruction;
import turing.interfaces.Simulator;

/**
 *
 * @author Jack & Andrew !(though Ayrton's the best)
 */
public class ReadFile
{

    public static List<Instruction> getInstructionsFromFile(File f) throws TuringException, FileNotFoundException
    {
        List<Instruction> instructions = new ArrayList<Instruction>();

        Scanner scan = new Scanner(f);
        
    	int instructionCount=0;
    	try
    	{
			instructionCount = Integer.parseInt(scan.nextLine());
		}
		catch(NumberFormatException ex)
		{
			throw new TuringException("Could not import file: Number of instructions not specified.");
		}

        for(int i = 0; i<instructionCount; i++)
        {
            String quintuplet = scan.nextLine();
            TuringInstruction instruction = new TuringInstruction(quintuplet);
            instructions.add(instruction);
        }

        return instructions;
    }

    public static Tape getTapeFromFile(File f) throws TuringException, FileNotFoundException
    {
        Tape tape = null;

        Scanner scan = new Scanner(f);
        
        int instructionCount=0;
    	try
    	{
			instructionCount = Integer.parseInt(scan.nextLine());
		}
		catch(NumberFormatException ex)
		{
			throw new TuringException("Could not import file: Number of instructions not specified.");
		}
        
        for (int i=0; i<instructionCount; i++)
        {
            scan.nextLine();
        }

        try
        {
            int dimensions = Integer.parseInt(scan.nextLine());
            if (dimensions == 1)
            {
                tape = new Tape(Simulator.ONE_DIMENSIONAL);
            }
            else if (dimensions == 2)
            {
                tape = new Tape(Simulator.TWO_DIMENSIONAL);
            }
        }
        catch (NumberFormatException ex)
        {
            throw new TuringException("Failed to open file: Tape dimension not specified.");
        }

        for (int y = 0; scan.hasNextLine(); y++)
        {
            String line = scan.nextLine();
            
            if(!line.equals(""))
            {
	            String splitter = "!";
	            String tapeHeadX = line.split(splitter, 2)[0];
	            String tapeContents = line.split(splitter, 2)[1];
	            
	            if (!tapeHeadX.equals(""))
	            {
	                int parsedInt = Integer.parseInt(tapeHeadX);
	                tape.setTapeHeadX(parsedInt);
	                tape.setTapeHeadY(y);
	            }
	
	            for (int x=0; x<tapeContents.length(); x++)
	            {
	                tape.setTapeCellSymbol(tapeContents.charAt(x), x, y);
	            }
            }
        }

        return tape;
    }

	public static Tape importTapeFromFile(File f) throws FileNotFoundException, TuringException
	{
		Scanner s = new Scanner(f);
		
		int instructionCount=0;
		try
		{
			instructionCount = Integer.parseInt(s.nextLine());
		}
		catch(NumberFormatException ex)
		{
			throw new TuringException("Could not import file: Number of instructions not specified.");
		}
		
		for(int i=0;i<instructionCount;i++)
		{
			s.nextLine();
		}
		
		Tape tape = new Tape(Simulator.ONE_DIMENSIONAL);
		
		String tapeString = "";
		while(s.hasNextLine())
		{
			tapeString+=s.nextLine();
		}
		
		boolean tapeHeadPosition=false;
		
		for(int x=0;x<tapeString.length()-2;x++)
		{
			if(tapeString.charAt(x+1)=='<'&&tapeString.charAt(x+3)=='>'&&!tapeHeadPosition)
			{
				tapeHeadPosition=true;
				tape.setTapeCellSymbol(tapeString.charAt(x+2), x, 0);
				tape.setTapeHeadX(x);
				tapeString = tapeString.substring(0,x)+tapeString.substring(x+2,tapeString.length());
			}
			else
			{
				tape.setTapeCellSymbol(tapeString.charAt(x+1), x, 0);
			}
		}
		
		return tape;
	}
	
	public static List<Instruction> importInstructionsFromFile(File f) throws FileNotFoundException, TuringException
	{
		Scanner s = new Scanner(f);
		
		int instructionCount=0;
		try
		{
			instructionCount = Integer.parseInt(s.nextLine());
		}
		catch(NumberFormatException ex)
		{
			throw new TuringException("Could not import file: Number of instructions not specified.");
		}
		
		List<Instruction> instructionSet = new ArrayList<Instruction>();
		
		for(int i=0;i<instructionCount;i++)
		{
			instructionSet.add(new TuringInstruction(s.nextLine().trim()));
		}
		
		return instructionSet;
	}
}
