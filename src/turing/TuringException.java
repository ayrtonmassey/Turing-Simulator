package turing;

public class TuringException extends Exception {

	String mistake;

    /**
     * Creates a new {@link TuringException}.
     */
    public TuringException()
    {
        super();
        mistake = "unreported";
    }

    /**
     * Creates a new {@link TuringException} with the error message <code>err</code>.
     * @param err The error message to display.
     */
    public TuringException(String err)
    {
        super(err);
        mistake = err;
    }
	
}
