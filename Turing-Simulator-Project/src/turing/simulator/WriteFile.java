/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package turing.simulator;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


/**
 *
 * @author Jack & Andrew
 */
public class WriteFile
{
    WriteFile()
    {
        PrintWriter pout = null;
        try
        {
            pout = new PrintWriter(new FileWriter(f)); //once ReadFile is fixed this should all be fine-diddly-ine
            //pout.println(tapeList.get(1));
            //To Jack - example printing for an ArrayList
                     
            pout.println(tapeList); //prints full ArrayList to file!
            
        } catch (IOException ex)
        {
            System.out.println(ex.toString());
        } finally
        {
            pout.close();
        }
    }
    
}
