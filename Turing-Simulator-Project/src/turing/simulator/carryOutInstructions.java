/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package turing.simulator;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Vickers Family
 */
public class carryOutInstructions {
       int currentTapePosition = 0;
   
           public void executeInstructions() {
 char[] tape = {'0', '0', '0', '0', '0', '#'};
        int currentState = 0;


        List<Instruction> instructions = new ArrayList<Instruction>();
        Instruction i1 = new Instruction(0, '0', 1, '1', Instruction.DIRECTION_RIGHT);

        instructions.add(i1);
        Instruction i2 = new Instruction(1, '0', 0, '1', Instruction.DIRECTION_RIGHT);
        instructions.add(i2);
        Instruction i3 = new Instruction(0, '#', 2, '1', Instruction.DIRECTION_HALT);
        instructions.add(i3);
        Instruction i4 = new Instruction(1, '#', 2, '1', Instruction.DIRECTION_HALT);
        instructions.add(i4);

        for (int i = 0; i < tape.length; i++) {
            System.out.println("Original Tape:" + tape[i]);
        }
        boolean running = true;
        while (running) {


            char currentSymbol = tape[currentTapePosition];

            Instruction toExecute = null;
            for (Instruction i : instructions) {
                if (i.currentState == currentState && i.inputSymbol == currentSymbol) {
                    toExecute = i;
                }
            }

            if (toExecute != null) {

                currentState = toExecute.nextState;
                tape[currentTapePosition] = toExecute.outputSymbol;
                switch (toExecute.direction) {
                    case Instruction.DIRECTION_LEFT:
                        currentTapePosition--;
                        break;
                    case Instruction.DIRECTION_RIGHT:
                        currentTapePosition++;
                        break;
                    case Instruction.DIRECTION_HALT:
                        running = false;
                        break;
                    default:
                        break;
                }
            } else {
                //throw error
            }
        }

        for (int i = 0; i< tape.length; i++) {
            System.out.println("new Tape:" + tape[i]);
        }
    }
}

