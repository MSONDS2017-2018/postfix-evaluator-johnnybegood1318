package stack;

/**
 * A {@link StackUnderflowException} is thrown at runtime when an operation that
 * would return or manipulate the top most element of a stack that is empty is
 * called.
 *
 * @author jcollard, jddevaug
 *
 */
public class StackUnderflowException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Default Constructor.
     */
    public StackUnderflowException() {
	super();
    }

    /**
     * Constructor with message.
     * @param message - the message to be passed
     */
    public StackUnderflowException(final String message) {
	super(message);
    }
}
