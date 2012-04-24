/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package turing.simulator;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import turing.interfaces.Simulator;

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
            pout = new PrintWriter(new FileWriter(""));
            
                     
            
            
        } catch (IOException ex)
        {
            System.out.println(ex.getMessage());
        } finally
        {
            pout.close();
        }
    }
    
}
