package stack;

public class LinearNodes<T>
{
    private T value;
    private LinearNodes<T> next;

    public LinearNodes(T obj, LinearNodes<T> prev)
    {
	value = obj;
	next = prev;
    }

    public T getValue()
    {
	return value;
    }

    public void setValue(T newValue)
    {
	value = newValue;
    }

    public LinearNodes<T> getNext()
    {
	return next;
    }

    public void setNext(LinearNodes<T> newNext)
    {
	next = newNext;
    }

    public String toString()
    {
	if (next != null)
	    return next.toString() + " " + value.toString();
	return value.toString();
    }
}
