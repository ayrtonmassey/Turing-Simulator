/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package turing.simulator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import turing.TuringException;
import turing.interfaces.Simulator;

/**
 *
 * @author Vickers Family
 */
public class carryOutInstructions implements Simulator {
       int currentTapePosition = 0;
   
           public void executeInstructions() {
 char[] tape = {'0', '0', '0', '0', '0', '#'};
        int currentState = 0;


        List<TuringInstruction> instructions = new ArrayList<TuringInstruction>();
        TuringInstruction i1 = new TuringInstruction(0, '0', 1, '1', TuringInstruction.DIRECTION_RIGHT);

        instructions.add(i1);
        TuringInstruction i2 = new TuringInstruction(1, '0', 0, '1', TuringInstruction.DIRECTION_RIGHT);
        instructions.add(i2);
        TuringInstruction i3 = new TuringInstruction(0, '#', 2, '1', TuringInstruction.DIRECTION_HALT);
        instructions.add(i3);
        TuringInstruction i4 = new TuringInstruction(1, '#', 2, '1', TuringInstruction.DIRECTION_HALT);
        instructions.add(i4);

        for (int i = 0; i < tape.length; i++) {
            System.out.println("Original Tape:" + tape[i]);
        }
        boolean running = true;
        while (running) {


            char currentSymbol = tape[currentTapePosition];

            TuringInstruction toExecute = null;
            for (TuringInstruction i : instructions) {
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

      

    @Override
    public int getCurrentState() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isTapeEditable() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getTapeHeadColumnIndex() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getTapeHeadRowIndex() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean setTapeCellSymbol(char symbol, int rowIndex, int columnIndex) throws TuringException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<List<Character>> getTapeContents(int rowBeginIndex, int rowEndIndex, int colBeginIndex, int colEndIndex) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public turing.interfaces.Instruction getCurrentInstruction() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getTapeOriginX() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getTapeOriginY() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void openFile(File f) throws TuringException {


        throw new UnsupportedOperationException("Not supported yet.");
    }
}

