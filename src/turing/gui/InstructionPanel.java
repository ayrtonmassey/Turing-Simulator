package turing.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import turing.Main;
import turing.TuringException;
import turing.interfaces.GUI;
import turing.interfaces.Instruction;
import turing.simulator.TuringInstruction;

public class InstructionPanel extends JPanel implements ActionListener {

	GUI gui;
	
	public InstructionPanel(GUI gui)
	{
		this.gui = gui;
		
		init();
	}
	
	JScrollPane scrollPane;
		JList instructionList;
			InstructionSetModel listModel;
	JButton addInstruction;
		Icon addIcon;
	JButton removeInstruction;
		Icon removeIcon;
	public void init()
	{			
		this.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		
		JLabel panelTitle = new JLabel("Instruction Set:");
			panelTitle.setBorder(BorderFactory.createEmptyBorder(8,0,0,0));
			panelTitle.setFont(GUI.INSTRUCTION_FONT);
			panelTitle.setHorizontalAlignment(SwingConstants.CENTER);
			panelTitle.setHorizontalTextPosition(SwingConstants.CENTER);
			gc.fill=GridBagConstraints.BOTH;
			gc.gridx=0;
			gc.gridy=0;
			gc.gridwidth=2;
			gc.gridheight=1;
			gc.weightx=1;
			gc.weighty=0;
			gc.anchor=GridBagConstraints.CENTER;
		this.add(panelTitle,gc);

		listModel = new InstructionSetModel(gui);
		instructionList = new JList(listModel);
			instructionList.addMouseListener(new MouseAdapter(){
				@Override
				public void mouseClicked(MouseEvent e)
				{
					if(e.getClickCount()>=2)
					{
						try
						{
							editInstruction(instructionList.getSelectedIndex());
						}
						catch (TuringException ex)
						{
							Main.err.displayError(ex);
						}
					}
				}
			});
			instructionList.setCellRenderer(new InstructionRenderer());
			instructionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			instructionList.setBackground(GUI.SIDE_PANEL_BACKGROUND);
		scrollPane = new JScrollPane(instructionList);
			scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			scrollPane.setBorder(BorderFactory.createEmptyBorder());
			scrollPane.setBackground(GUI.SIDE_PANEL_BACKGROUND);
			
			gc.fill=GridBagConstraints.BOTH;
			gc.gridx=0;
			gc.gridy=1;
			gc.gridwidth=2;
			gc.gridheight=1;
			gc.weightx=1;
			gc.weighty=1;
			gc.anchor=GridBagConstraints.NORTH;
		this.add(scrollPane,gc);
		
		addIcon = new ImageIcon(this.getClass().getResource("img/add.png"));
		
		addInstruction = new JButton(addIcon);
			addInstruction.addActionListener(this);
			
			gc.fill=GridBagConstraints.BOTH;
			gc.gridx=0;
			gc.gridy=2;
			gc.gridwidth=1;
			gc.gridheight=1;
			gc.weightx=0.5;
			gc.weighty=0;
			gc.anchor=GridBagConstraints.EAST;
		this.add(addInstruction,gc);
		
		removeIcon = new ImageIcon(this.getClass().getResource("img/remove.png"));
		
		removeInstruction = new JButton(removeIcon);
			removeInstruction.addActionListener(this);
			
			gc.fill=GridBagConstraints.BOTH;
			gc.gridx=1;
			gc.gridy=2;
			gc.gridwidth=1;
			gc.gridheight=1;
			gc.weightx=0.5;
			gc.weighty=0;
			gc.anchor=GridBagConstraints.EAST;
		this.add(removeInstruction,gc);
		
		this.setBorder(GUI.TOP_BORDER);
		this.setOpaque(true);
		this.setBackground(GUI.SIDE_PANEL_BACKGROUND);
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource().equals(addInstruction))
		{
			try
			{
				addInstruction();
			}
			catch (TuringException ex)
			{
				Main.err.displayError(ex);
			}
		}
		else if(e.getSource().equals(removeInstruction))
		{
			removeInstruction(instructionList.getSelectedIndex());
		}
	}
	
	private void addInstruction() throws TuringException
	{
		String quintuplet = JOptionPane.showInputDialog(this, "Enter the new quintuplet for this instruction:");
		
		Instruction i = gui.getSimulator().createInstruction(quintuplet);
		
		listModel.addElement(i);
		
		gui.updateStatusMessage("Instruction added successfully!");
	}
	
	private void removeInstruction(int index)
	{
		if(index>=0)
		{
			if(JOptionPane.showConfirmDialog(this, "Are you sure you want to remove this instruction? You cannot undo this change.",
										"Remove?", JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION)
			{
				listModel.removeElement(index);
				gui.updateStatusMessage("Instruction removed successfully!");
			}
		}
		else
		{
			JOptionPane.showMessageDialog(this, "You have not selected an instruction to remove.");
		}
	}
	
	private void editInstruction(int index) throws TuringException
	{
		Instruction i = (Instruction) listModel.getElementAt(index);
		
		String direction="";
		switch(i.getDirection())
		{
		case Instruction.MOVE_UP:
			direction="U";
			break;
		case Instruction.MOVE_DOWN:
			direction="D";
			break;
		case Instruction.MOVE_LEFT:
			direction="L";
			break;
		case Instruction.MOVE_RIGHT:
			direction="R";
			break;
		case Instruction.HALT:
			direction="H";
			break;
		default:
			direction="ERROR";
			break;
		}
		
		String instruction = "("+i.getCurrentState()+","+i.getInputSymbol()+","+i.getNextState()+","+i.getOutputSymbol()+","+direction+")";
		String quintuplet = JOptionPane.showInputDialog(this, "Enter the new quintuplet for this instruction:", instruction);
		
		i = gui.getSimulator().createInstruction(quintuplet);
		
		listModel.setElementAt(index,i);
		
		gui.updateStatusMessage("Instruction edited successfully!");
	}
	
}
