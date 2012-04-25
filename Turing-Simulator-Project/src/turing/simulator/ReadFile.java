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
import turing.interfaces.Instruction;
import turing.interfaces.Simulator;

/**
 *
 * @author Jack & Andrew !(though Ayrton's the best)
 */
public class ReadFile
{

    ReadFile(File f)
    {
        try
        {
            Scanner scan = new Scanner(new File(f));
            
        } catch (FileNotFoundException e)
        {
            System.out.println(e.toString());
        }

        getInstructionsFromFile(f);
        getTapeFromFile(f);
    }

    public List<Instruction> getInstructionsFromFile(File f)
    {
        List<Instruction> instructions = new ArrayList<Instruction>(); //Fixed by Andrew, your ArrayList only had scope of those brackets
        //Hence, wouldn't be understood when asked to be returned.
        // Had to change <Instruction> instead of <Character> in order for my code to work, Alastair.
        try
        {
            Scanner scan = new Scanner(f);
        } catch (FileNotFoundException ex)
        {
            Logger.getLogger(ReadFile.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
        Tape.setTapeDimension(Simulator.ONE_DIMENSIONAL);
        return instructions;

    }

    public List<Character> getTapeFromFile(File f)
    {
        List<Character> tapeList = new ArrayList<Character>();
        try
        {
            Scanner scan = new Scanner(f); //enter whatever file we are using here

            String tape = scan.nextLine();

            for (int i = 0; i < tape.length(); i++)
            {
                char currentChar = tape.charAt(i); //for convenience

                if (currentChar == ')') //when one quintuplet ends
                {
                    tapeList.add('*'); //splits into quintuplets by asterisks on the tape
                }

                if (currentChar != ',' //commas are used to split up the instructions
                        && currentChar != ')')
                {
                    tapeList.add(tape.charAt(i));
                }
            }

        } catch (FileNotFoundException ex)
        {
            System.out.println(ex.toString());
        }

        Tape.setTapeDimension(Simulator.ONE_DIMENSIONAL);
        return tapeList;
    }
}
