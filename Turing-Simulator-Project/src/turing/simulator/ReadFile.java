/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package turing.simulator;


import java.io.File;
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
    Scanner scan = new Scanner(f);

    ReadFile(File f)
    {
       getInstructionsFromFile(File f);
       getTapeFromFile(File f);

    }

    ReadFile() {

    }

    public List<Instruction> getInstructionsFromFile(File f)
    {
     List<Instruction> instructions = new ArrayList<Instruction>(); //Fixed by Andrew, your ArrayList only had scope of those brackets
     try                                                        //Hence, wouldn't be understood when asked to be returned.
                                                                // Had to change <Instruction> instead of <Character> in order for my code to work, Alastair.
         
        {
            
           Scanner scan = new Scanner(new File("C:\\Users\\Jack\\My Documents\\New Text Document.TXT"));

            
           instructions.add('a');
           
            System.out.println(instructions.get(0));
           
             
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
            Scanner scan = new Scanner(new File("")); //enter whatever file we are using here
            String tape = scan.nextLine();
     
            
            for (int i = 0; i < tape.length(); i++)
            {
                char currentChar = tape.charAt(i); //for convenience
                
                if (currentChar == ')') //when one quintuplet ends
                {
                    tapeList.add('*'); //splits into quintuplets on the tape
                }
                
                if(     currentChar != ',' //commas are used to split up the instructions
                      &&currentChar != ')')
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
    

    public static void main(String[] args)
    {
        ReadFile r = new ReadFile(File f);
    }
        
}
