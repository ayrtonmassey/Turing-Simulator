/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package turing.simulator.sumulator;


import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jack & Andrew (though Andrew's the best)
 */
public class ReadFile
{

    ReadFile()
    {
        getInstructionsFromFile();
        getTapeFromFile();
    }
    
    void getInstructionsFromFile()
    {
       
     try
         
        {
            
           Scanner scan = new Scanner(new File("C:\\Users\\Jack\\My Documents\\New Text Document.TXT"));
           List<Character> instructions = new ArrayList<Character>();
            
           instructions.add('a');
           
            System.out.println(instructions.get(0));
           
             
        } catch (FileNotFoundException ex)
        {
            Logger.getLogger(ReadFile.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }

     
    }
    
    void getTapeFromFile() //will turn into function at later date
    {
        try
        {
            Scanner scan = new Scanner(new File("")); //enter whatever file we are using here
            String tape = scan.nextLine();
            List<Character> tapeList = new ArrayList<Character>();            
            
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
        
        //remember - return the tapeList!    
    }

    public static void main(String[] args)
    {
        ReadFile r = new ReadFile();
    }
        
}
