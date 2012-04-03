/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package turing.simulator;

/**
 *
 * @author Vickers Family
 */
public class Instruction {
      public static final int DIRECTION_LEFT=0,DIRECTION_RIGHT=1,DIRECTION_HALT=2;

    int currentState;
    char inputSymbol;
    int nextState;
    char outputSymbol;
    int direction;

    Instruction(int currentState,char inputSymbol,int nextState,char outputSymbol,int direction)
    {
        this.currentState=currentState;
        this.inputSymbol=inputSymbol;
        this.nextState=nextState;
        this.outputSymbol=outputSymbol;
        this.direction=direction;
    }

}
