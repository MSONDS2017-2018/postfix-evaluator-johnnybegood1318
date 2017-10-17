package stack;

/**
 * A {@link LinkedStack} is a stack that is implemented using a Linked List
 * structure to allow for unbounded size.
 *
 * @param <T>
 *            the elements stored in the stack
 */
public class LinkedStack<T> implements StackInterface<T> {

    private LinearNodes<T> top;

    private int size = 0;

    public LinkedStack() {
    }

    public LinkedStack(T value) {
	top = new LinearNodes<T>(value, top);
	size++;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public T pop() {
	if (top == null)
	    throw new StackUnderflowException();
	T value = top.getValue();
	top = top.getNext();
	size--;
	return value;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public T top() {
	if (top == null)
	    throw new StackUnderflowException();
	return top.getValue();
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public boolean isEmpty() {
	if (top != null)
	    return false;
	else
	    return true;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public int size() {
	return size;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void push(T elem) {
	top = new LinearNodes<T>(elem, top);
	size++;
    }

    public void clear() {
	top = null;
	size = 0;
    }

    public String toString() {
	return top.toString();
    }

}
