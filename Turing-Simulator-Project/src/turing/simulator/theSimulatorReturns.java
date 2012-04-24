/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package turing.simulator;

import java.awt.Component;
import turing.Main;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import turing.TuringException;
import turing.gui.TuringGUI;
import turing.interfaces.GUI;
import turing.interfaces.Instruction;
import turing.interfaces.Simulator;

/**
 *
 * @author Vickers Family
 */
public class theSimulatorReturns implements Simulator {

    GUI gui;
    Tape tape;
    int currentState = 0;
    public Instruction currentInstruction;
    private List<Instruction> history = new ArrayList<Instruction>();
    private boolean paused = true;
    private int sleep = Simulator.SPEED_INIT;
    private List<Instruction> instructionSet = new ArrayList<Instruction>();

    theSimulatorReturns() {
        reset(Simulator.ONE_DIMENSIONAL);
        gui = new TuringGUI(this);
	run();



    }

    public static void main(String[] args) {
        new theSimulatorReturns();
    }

    public void run() {
        while (true) {
            if (paused == false) {
                step();
                try {
                    Thread.sleep(sleep);
                } catch (InterruptedException ex) {
                    System.err.println("Simulator woke unexpectedly!");
                }

            }
        }
    }

    public Instruction findInstruction(int currentState, char inputSymbol) {
        currentInstruction = null;
        for (Instruction i : instructionSet) {
            if (i.getCurrentState() == currentState && i.getInputSymbol() == tape.getTapeSymbolAt(tape.getTapeHeadX(), tape.getTapeHeadY())) {
                currentInstruction = i;
            }
        }
        return currentInstruction;
    }

    public void executeInstruction(Instruction instruction) throws TuringException {

        int x = tape.getTapeHeadX();
        int y = tape.getTapeHeadY();




        try {

            history.add(currentInstruction);

            if (history.size() > Simulator.HISTORY_SIZE_LIMIT) {
                history.remove(0);
            }



            tape.setTapeCellSymbol(currentInstruction.getOutputSymbol(), x, y);
            currentState = instruction.getNextState();
            char symbol = instruction.getOutputSymbol();
            tape.setTapeCellSymbol(symbol, x, y);

            switch (currentInstruction.getDirection()) {
                case Instruction.MOVE_LEFT:
                    tape.setTapeHeadX(tape.getTapeHeadX() - 1);
                    break;
                case Instruction.MOVE_RIGHT:
                    tape.setTapeHeadX(tape.getTapeHeadX() + 1);
                    break;
                case Instruction.MOVE_UP:
                    tape.setTapeHeadY(tape.getTapeHeadY() - 1);
                    break;
                case Instruction.MOVE_DOWN:
                    tape.setTapeHeadY(tape.getTapeHeadY() + 1);
                    break;

                default:
                    this.isPaused();
                    break;

            }
            gui.update();

        } catch (TuringException ex) {
            Main.err.displayError(ex);
        }
    }

    public Instruction getCurrentInstruction(Iterable<TuringInstruction> instructions) {

        currentInstruction = null;
        for (TuringInstruction i : instructions) {
            if (i.getCurrentState() == currentState && i.getInputSymbol() == tape.getTapeSymbolAt(tape.getTapeHeadX(), tape.getTapeHeadY())) {
                currentInstruction = i;
            }
        }
        return currentInstruction;


    }

    public int getCurrentState() {

        TuringInstruction turingInstruction = new TuringInstruction();
        int CurrentState = turingInstruction.getCurrentState();

        return CurrentState;


    }

    public List<Instruction> getHistory() {
        return history;
    }

    public Tape getTape() {

        return tape;
    }

    public boolean isPaused() {

        return paused;
    }

    public void openFile(File f, int type) throws TuringException {
        ReadFile r = new ReadFile();
        List<Character> tapes = r.getTapeFromFile(f);
        instructionSet = r.getInstructionsFromFile(f);
    }

    public boolean pause() {
        paused = true;
        gui.updateStatusMessage(" The Simulation has been paused.");
        return true;
    }

    public boolean play() {
        paused = false;
        gui.updateStatusMessage("Simulation is now playing.");
        return true;
    }

    @Override
    public void reset(int dimension) {

        this.paused = true;
        this.currentState = 0;
        this.history.clear();
        List<Instruction> instructionSet = new ArrayList<Instruction>();
        tape = new Tape(dimension);

    }

    public void saveFile(File f) throws TuringException {
        /*
         * ReadFile r = new ReadFile(); r.saveFile();
        //
         */
        gui.updateStatusMessage("Saved.");
    }

    public void setSpeed(int value) {
        this.sleep = value;
    }

    @Override
    public void step() {
     
                int x = tape.getTapeHeadX();
                int y = tape.getTapeHeadY();
                char currentInputSymbol = tape.getTapeSymbolAt(x, y);
               
                
                currentInstruction=findInstruction(currentState, currentInputSymbol);
                 if(currentInstruction==null)
                {
                    JOptionPane.showMessageDialog((Component) gui,"no instruction");
                    this.pause();
                   gui.update();
                }else
                        {

                try {
                    executeInstruction(currentInstruction);
                } catch (TuringException ex) {
                    Logger.getLogger(theSimulatorReturns.class.getName()).log(Level.SEVERE, null, ex);
                }
                    
                
                }
    }
        
    public List<Instruction> getInstructionSet() {

        return instructionSet;
    }

    @Override
    public Instruction createInstruction(int currentState, char inputSymbol, int nextState, char outputSymbol, int direction) throws TuringException {
        if (tape.getTapeDimension() == Simulator.ONE_DIMENSIONAL) {
            if (direction == Instruction.MOVE_DOWN || direction == Instruction.MOVE_UP) {
                throw new TuringException("Can not move up or down "
                        + "as tape is one dimension");
            }



        }
        return new TuringInstruction(currentState, inputSymbol, nextState, outputSymbol, direction);
    }

    public void setInstructionSet(List<Instruction> instructionSet) {
        synchronized (this.instructionSet) {
            this.instructionSet = instructionSet;
        }
    }

    public void setCurrentState(int state) throws TuringException {
        if (state >= 0) {
            this.currentState = state;
        } else {
            throw new TuringException("The state " + state + " is not a valid.");
        }
    }

    public Instruction getCurrentInstruction() {
        return currentInstruction;
    }
}
