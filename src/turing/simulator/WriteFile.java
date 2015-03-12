/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package turing.simulator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import turing.interfaces.Instruction;


/**
 *
 * @author Jack & Andrew
 */
public class WriteFile
{    
    public static void writeFile(File f, Tape tape, List<Instruction> instructions)
    {
        try
        {
            PrintWriter pout = new PrintWriter(new FileWriter(f));
                
            pout.println(instructions.size());
            for(int i=0;i<instructions.size();i++)
            {
            	pout.println(instructions.get(i));
            }
            pout.println(tape.getTapeDimension());
            pout.println(tape);
            pout.close();
        }
        catch (IOException ex)
        {
            System.out.println(ex.toString());
        }
    }
    
}
