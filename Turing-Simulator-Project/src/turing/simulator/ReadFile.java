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

/**
 *
 * @author Jack & Andrew !(though Ayrton's the best)
 */
public class ReadFile
{
    public static List<Instruction> getInstructionsFromFile(File f) throws TuringException
    {
        List<Instruction> instructions = new ArrayList<Instruction>(); //Fixed by Andrew, your ArrayList only had scope of those brackets
        //Hence, wouldn't be understood when asked to be returned.
        // Had to change <Instruction> instead of <Character> in order for my code to work, Alastair.
        try
        {
            Scanner scan = new Scanner(f); //This is fine.
            String firstLine = scan.nextLine();
            int numberOfInstructions = Integer.parseInt(firstLine);
           
             for (int i = 0; i < numberOfInstructions; i++)
            {
                String instruct = scan.nextLine();
                TuringInstruction instruction = new TuringInstruction(instruct); // not sure if this is correct 
                instructions.add(instruction);
            }
           
        } catch (FileNotFoundException ex)
        {
            Logger.getLogger(ReadFile.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
        
        return instructions; //This will return the list of instructions!
    }

    public static Tape getTapeFromFile(File f)
    {
    	Tape tape = null; //This is fine.
    	
        try
        {
            Scanner scan = new Scanner(f); //This is fine.

            //Not sure what you were trying to do here, but I've left it in case you need it.
            
//	           String tape = scan.nextLine();
//	            for (int i = 0; i < tape.length(); i++)
//	            {
//	                char currentChar = tape.charAt(i); //for convenience
//	
//	                if (currentChar == ')') //when one quintuplet ends
//	                {
//	                    tapeList.add('*'); //splits into quintuplets by asterisks on the tape
//	                }
//	
//	                if (currentChar != ',' //commas are used to split up the instructions
//	                        && currentChar != ')')
//	                {
//	                    tapeList.add(tape.charAt(i));
//	                }
//	            }
            
            /*
             * This method has to read in the tape from a .tsf file. You'll need to:
             * 
             * - Find the beginning of the tape.
             * - Read in the dimension of the tape.
             * - Read in the tape contents, line by line.
             * - Return the tape.
             * 
             * I've written a class called Tape which handles storing the symbols on the tape.
             * What you need to do is get the symbols on the tape from the file, and pass them
             * to a new Tape object, then return that tape object.
             * 
             * The simulator will call this method and set its tape to the Tape object you return
             * in this method.
             */
            
            /*
             * Step by step breakdown:
             * 
             * -----
             * 
             * A .tsf file is composed of the instruction set followed by the tape. In this method, we're reading
             * in the contents of the tape, so we can ignore the instruction set.
             * 
             * To begin, we just need to skip past the lines in the file containing the instruction set.
             * 
             * --
             * 
             * The nextLine() method of the Scanner class moves to the next line of the file and returns the contents
             * as a String object.
             * 
             * When you call a method which returns a variable, you don't HAVE to store whatever was returned - 
             * this means we can use nextLine() both to:
             * 
             * - get the contents of the next line of a file
             * - move to the that line of the file
             * 
             * If we ignore the String returned by nextLine(), we will just move to the next line.
             *
             * --
             * 
             * The first line of the .tsf file is the number of instructions in the file.
             * We need to find out what the number of instructions is, so read the next line of the file using scan.nextLine() and
             * store it as a String object.
             * 
             * You now need to parse the contents of this line as an Integer. (parsing = reading input and converting it to a form
             * which can be interpreted by the computer).
             * 
             * To parse a String as an Integer, you can use Integer.parseInt(String). parseInt(String) returns an int.
             * 
             * --
             * 
             * We now need to skip past x (the number of instructions) lines in the file. You can use a for loop for this:
             * 
             * FOR (0 to x)
             * 	GO TO NEXT LINE
             * 
             * Remember you can use scan.nextLine() to go to the next line in a file.
             * 
             * -----
             * 
             * If you did all that right, you should now be at the end of the instruction set.
             * 
             * The first line after the instruction set in a .tsf file is the dimension of the tape.
             * 
             * Read the next line of the file and store it as a String. The dimension will either be stored as
             * 1 or 2. All you need to do is check if the line equals "1" or "2" (one or two dimensional).
             * 
             * If the tape is one-dimensional, make a new Tape like this:
             * 
             * tape = new Tape(Simulator.ONE_DIMENSIONAL);
             * 
             * Else if it's two-dimensional, make a new Tape like this:
             * 
             * tape = new Tape(Simulator.TWO_DIMENSIONAL);
             * 
             * -----
             * 
             * Now you need to read in the tape contents.
             * 
             * In a .tsf file, each row of the tape is on a new line.
             * Each line begins with an exclamation mark (!) followed by the symbols in that row.
             * This exclamation mark just signifies the beginning of the symbols on that row.
             * One line of the tape will have a number before the !. This is the position of the tape head.
             * 
             * For example:
             * 
             * !111111
             * !111111
             * !111111
             * 4!111101
             * !111111
             * 
             * The first row on the tape is row 0. The first symbol in each row is at column 0. This means that the
             * top left symbol on the tape (the first symbol on the first row) is at (0,0).
             * 
             * rows 0-2 (lines 1-3) are just a bunch of 1s. 
             * 
             * On row 3, however, there is a 4 before the !. This means that the x-coordinate of the tape head on
             * that row is 4.  You'll notice that there's a 0 at (4,3). The tape head will be on this 0 symbol.
             * 
             * When you find the row with the position of the tape head, you need to do:
             * 
             * tape.setTapeHeadX(int);
             * tape.setTapeHeadY(int);
             * 
             * where Y is the current row and X is the x-coordinate at the start of the line.
             * 
             * --
             * 
             * For each symbol on a row, use the Tape class' setTapeSymbolAt(char, int, int) method to set
             * the symbol on the tape.
             * 
             * For example, you are on line 3 (row 2 or y=2) and read in an A as the first symbol (column 0 or x=0).
             * You would do:
             * 
             * tape.setTapeSymbolAt('A', 0, 2);
             * 
             * --
             * 
             * You should use nested for-loops (a for loop inside a for loop) to read in the tape, as in the following
             * pseudo-code:
             * 
             * INT y <- 0;
             * 
             * WHILE(SCANNER HAS NEXT LINE)
             * 
             * 	   STRING line <- GET NEXT LINE;
             * 
             *     STRING tapeHeadX <- SPLIT LINE [0];
             *     STRING tapeContents <- SPLIT LINE [1];
             *     
             *     IF tapeHeadX IS NOT EMPTY
             *     	PARSE tapeHeadX AS INTEGER
             *     	SET TAPE HEAD X TO RESULT
             *     
             *     INT x <- 0;
             *     
             * 	   WHILE(x < LENGTH OF tapeContents)
             * 		   
             *         SET TAPE SYMBOL AT (x,y) TO CHAR AT x IN tapeContents;
             *         
             *         x <- x+1;
             *         
             *     ENDWHILE
             *     
             *     y <- y+1;
             *     
             * ENDWHILE
             * 
             * --
             * 
             * I've written the loops above as WHILE loops in pseudocode because I didn't want to give you incorrect
             * pseudocode. If you implement that code with while loops it should work. Alternatively you can 
             * (and should) use for-loops. A for-loop is constructed like:
             * 
             * for(initialise some variables ; while this condition is true ; do this stuff to these variables)
             * {
             *     //DO STUFF
             * }
             * 
             * so in this case you might have:
             * 
             * for(int y=0; scan.hasNext(); y++)
             * {
             *     //BLAH BLAH DO STUFF
             *     
             *     for(int x=0; x < tapeContents.length(); x++)
             *     {
             *     
             *         //BLAH BLAH DO MORE STUFF
             *     
             *     }
             *     
             * }
             *         
             *  You don't need to increment x and y within the loop. This is because anything after the second ; in the for-loop
             *  statement will be done each time the for loop executes. x++ and y++ are shorthand for x = x+1 (or x+=1) and y = y+1 (or y+=1).
             *     
             *  You might be thinking "why do I need for-loops if I can just use a while loop?". The main reason is because
             *  it makes your code more readable - with a for-loop it's clear that you're iterating over something, whereas
             *  a while loop can be used for lots of purposes (e.g. requesting user input repeatedly).
             *  
             *  (iterating = looping over a set of things and doing something to each of them)
             *  
             *  The condition for your for-loop can be ANYTHING which returns a true or false value!
             *  It doesn't have to relate to the variables you initialised.
             *  
             *  ===============
             *  
             *  Finally, you need to return the tape outside this try/catch block.
             */

        }
        catch (FileNotFoundException ex)
        {
            System.out.println(ex.toString());
        }

        return tape; //This will return the tape!
    }
}
