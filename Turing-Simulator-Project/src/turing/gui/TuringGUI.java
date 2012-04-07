package turing.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import turing.Main;
import turing.TuringException;
import turing.interfaces.GUI;
import turing.interfaces.Simulator;

public class TuringGUI extends JFrame implements GUI, ActionListener {
	
	private boolean DEBUG = false;
	
	Simulator sim;
	
	int TAPE_ROWS_TO_DISPLAY = GUI.DEFAULT_NUM_ROWS;
	int TAPE_COLUMNS_TO_DISPLAY = GUI.DEFAULT_NUM_COLUMNS;
	
	int currentState;
	
	int tapeHeadColumnIndex;
	
	TapePanel tape;
	ControlPanel control;
	SidePanel side;
	StatusPanel status;
	
	JMenuBar menuBar;
		JMenu fileMenu;
			JMenuItem newFile;
			JMenuItem openFile;
			JMenuItem saveFile;
			JMenu importMenu;
				JMenuItem import1DFile;
				JMenuItem import2DFile;
		JMenu viewMenu;
			JMenuItem centerView;
	
	/**
	 * Creates a new GUI for the Turing machine simulator.
	 * @param sim
	 */
	public TuringGUI(Simulator sim)
	{
		this.sim = sim;
		this.currentState = sim.getCurrentState();
		this.tapeHeadColumnIndex = sim.getTape().getTapeHeadColumnIndex();
		
		init();
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource().equals(newFile))
		{
			createNewFile();
		}
		else if(e.getSource().equals(openFile) ||
				e.getSource().equals(import1DFile) ||
				e.getSource().equals(import2DFile))
		{	
			int type=-1;
			if(e.getSource().equals(openFile))
			{
				type = Simulator.DETECT_TYPE;
			}
			else if(e.getSource().equals(import1DFile))
			{
				type = Simulator.ONE_DIMENSIONAL;
			}
			else if(e.getSource().equals(import2DFile))
			{
				type = Simulator.TWO_DIMENSIONAL;
			}
			
			showOpenDialog(type);
		}
		else if(e.getSource().equals(saveFile))
		{
			showSaveDialog();
		}
		else if(e.getSource().equals(centerView))
		{
			new CenterViewDialog(this);
		}
	}

	public void createNewFile()
	{
		String option = (String)JOptionPane.showInputDialog(this, "Please select a dimension for the new simulation:", 
				"New Simulation", JOptionPane.PLAIN_MESSAGE, null,
				new Object[]{"One Dimensional","Two Dimensional"}, "One Dimensional");

		if(option.equals("One Dimensional"))
		{
			sim.reset(Simulator.ONE_DIMENSIONAL);
		}
		else
		{
			sim.reset(Simulator.TWO_DIMENSIONAL);
		}
		
		this.reset();
	}
	
	@Override
	public boolean debugMode()
	{
		return DEBUG;
	}

	@Override
	public Simulator getSimulator()
	{
		return sim;
	}

	/**
	 * Initialises this GUI.
	 * <p>
	 * A frame is displayed in the center of the screen, containing the various
	 * components which make up the GUI.
	 * @see TapePanel
	 */
	private void init()
	{
		initFrame();
		initComponents();
		
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	/**
	 * Initialises the components for this GUI.
	 */
	private void initComponents()
	{
		this.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		
		menuBar = new JMenuBar();
			fileMenu = new JMenu("File");
			menuBar.add(fileMenu);
				newFile = new JMenuItem("New");
				newFile.addActionListener(this);
				fileMenu.add(newFile);
				
				openFile = new JMenuItem("Open...");
				openFile.addActionListener(this);
				fileMenu.add(openFile);
				
				saveFile = new JMenuItem("Save...");
				saveFile.addActionListener(this);
				fileMenu.add(saveFile);
				
				importMenu = new JMenu("Import");
				fileMenu.add(importMenu);
				
					import1DFile = new JMenuItem("One-Dimensional Turing Machine...");
					import1DFile.addActionListener(this);
					importMenu.add(import1DFile);
					
					import2DFile = new JMenuItem("Two-Dimensional Turing Machine...");
					import2DFile.addActionListener(this);
					importMenu.add(import2DFile);
					
			viewMenu = new JMenu("View");
			menuBar.add(viewMenu);
				centerView = new JMenuItem("Center View On...");
				centerView.addActionListener(this);
				viewMenu.add(centerView);
				
			
			gc.fill=GridBagConstraints.BOTH;
			gc.gridx=0;
			gc.gridy=0;
			gc.gridwidth=4;
			gc.gridheight=1;
			gc.weightx=1;
			gc.weighty=0;
			gc.anchor=GridBagConstraints.NORTH;
		this.add(menuBar,gc);	
		
		if(this.getSimulator().getTape().getTapeDimension()==Simulator.ONE_DIMENSIONAL)
		{
			TAPE_ROWS_TO_DISPLAY = 1;
		}
		else
		{
			TAPE_ROWS_TO_DISPLAY = GUI.DEFAULT_NUM_ROWS;
		}
		
		tape = new TapePanel(this,TAPE_ROWS_TO_DISPLAY,TAPE_COLUMNS_TO_DISPLAY);
			gc.fill=GridBagConstraints.BOTH;
			gc.gridx=0;
			gc.gridy=1;
			gc.gridwidth=3;
			gc.gridheight=3;
			gc.weightx=1;
			gc.weighty=1;
			gc.anchor=GridBagConstraints.CENTER;
		this.add(tape,gc);
		
		control = new ControlPanel(this);
			gc.fill=GridBagConstraints.HORIZONTAL;
			gc.gridx=0;
			gc.gridy=5;
			gc.gridwidth=3;
			gc.gridheight=1;
			gc.weightx=1;
			gc.weighty=0;
			gc.anchor=GridBagConstraints.SOUTH;
		this.add(control,gc);
		
		side = new SidePanel(this,tape);
			gc.fill=GridBagConstraints.VERTICAL;
			gc.gridx=3;
			gc.gridy=1;
			gc.gridwidth=1;
			gc.gridheight=4;
			gc.weightx=0;
			gc.weighty=1;
			gc.anchor=GridBagConstraints.SOUTH;
		this.add(side,gc);
		
		status = new StatusPanel(this);
			gc.fill=GridBagConstraints.HORIZONTAL;
			gc.gridx=0;
			gc.gridy=6;
			gc.gridwidth=4;
			gc.gridheight=1;
			gc.weightx=1;
			gc.weighty=0;
			gc.anchor=GridBagConstraints.SOUTH;
		this.add(status,gc);
		
		this.updateStatusMessage("New File Created!");
		status.updateTapeHeadCoordinates(sim.getTape().getTapeHeadX(), sim.getTape().getTapeHeadY());
		tape.update();
	}

	/**
	 * Initialises the frame which contains the GUI elements. 
	 */
	private void initFrame()
	{
		int w = 800;
		int h = 740;
		this.setMinimumSize(	new Dimension(w,h));
		this.setPreferredSize(	new Dimension(w,h));
		this.setMaximumSize(	new Dimension(w,h));

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setTitle("Baby's First Turing Machine");
	}

	@Override
	public void reset()
	{		
		this.getContentPane().removeAll(); //Turns out this.removeAll() also removes the content pane. Not good.
		
		this.init();
	}
	
	@Override
	public void setCenterTapeViewportOn(int x, int y)
	{
		tape.setFollowTapeHead(false);
		tape.centerViewportOn(x,y);
		tape.update();
	}
	
	public void showOpenDialog(int type)
	{
		JFileChooser fc = new JFileChooser();
		fc.setMultiSelectionEnabled(false);
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		
		if(type==Simulator.DETECT_TYPE)
		{
			fc.setFileFilter(new TuringFileFilter());
		}
		
		if(fc.showOpenDialog(this)==JFileChooser.APPROVE_OPTION)
		{
			try
			{
				sim.openFile(fc.getSelectedFile(),type);
				this.reset();
			}
			catch (TuringException ex)
			{
				Main.err.displayError(ex);
			}
		}
		
	}
	
	public void showSaveDialog()
	{
		JFileChooser fc = new JFileChooser();
		fc.setMultiSelectionEnabled(false);
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fc.setFileFilter(new TuringFileFilter());
		
		if(fc.showSaveDialog(this)==JFileChooser.APPROVE_OPTION)
		{
			try
			{	
				File f = fc.getSelectedFile();
				if(!f.getAbsolutePath().endsWith(Simulator.FILE_EXTENSION))
				{
					String path = f.getAbsolutePath()+Simulator.FILE_EXTENSION;
					f = new File(path);
				}
				
				sim.saveFile(f);
			}
			catch (TuringException ex)
			{
				Main.err.displayError(ex);
			}
		}
	}
	
	private class TuringFileFilter extends FileFilter {

		@Override
		public boolean accept(File f)
		{
			if(f.getName().endsWith(Simulator.FILE_EXTENSION) || f.isDirectory())
			{
				return true;
			}
			else
			{
				return false;
			}
		}

		@Override
		public String getDescription()
		{
			return "Turing Simulator Files (.tsf)";
		}
		
	}

	@Override
	public void update()
	{
		tape.update();
		
		control.update();
		
		status.updateTapeHeadCoordinates(sim.getTape().getTapeHeadX(), sim.getTape().getTapeHeadY());
		
		this.repaint();
	}
	
	@Override
	public void updateStatusMessage(String message)
	{
		status.updateStatusLabel(message);
	}

	@Override
	public void updateTapeDisplayCoordinates(int tapeBeginRowIndex, int tapeEndRowIndex, int tapeBeginColumnIndex, int tapeEndColumnIndex)
	{
		status.updateTapeDisplayCoordinatess(tapeBeginRowIndex, tapeEndRowIndex, tapeBeginColumnIndex, tapeEndColumnIndex);
	}
}
