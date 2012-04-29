package turing;

import javax.swing.JOptionPane;

public class ErrorHandler {

	/**
	 * Displays a user-readable error message.
	 * <p>
	 * This method is a catch-all for exceptions thrown by the Simulator.
	 * During development, this method will print a stack trace to the console,
	 * but providing it is called instead of <code>System.err.println</code>
	 * or <code>printStackTrace</code> we can modify this method at a
	 * later time to change how exceptions are handled globally.
	 * @param ex The {@link Exception} which has been caught.
	 */
	public void displayError(Exception ex)
	{
		ex.printStackTrace();
		
		JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	}
	
}
