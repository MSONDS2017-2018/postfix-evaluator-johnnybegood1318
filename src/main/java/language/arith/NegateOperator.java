package language.arith;

import language.Operand;
import language.Operator;

/**
 * The {@code NegateOperator} is an operator that performs negation on a single
 * integer
 * 
 * @author jcollard, jddevaug
 *
 */
public class NegateOperator implements Operator<Integer>
{

    private Operand<Integer> op0;

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNumberOfArguments()
    {
	return 1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Operand<Integer> performOperation()
    {
	if (op0 == null)
	{
	    throw new IllegalStateException("All arguments must be set");
	}
	return new Operand<Integer>(-1 * op0.getValue());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setOperand(int i, Operand<Integer> operand)
    {
	if (operand == null)
	{
	    throw new NullPointerException("Operand can't be null");
	}
	if (i > 0)
	{
	    throw new IllegalArgumentException("Only one operand is allowed");
	} else
	{
	    if (op0 != null)
	    {
		throw new IllegalStateException(
			"Operand value can not be overwritten");
	    }
	    op0 = operand;
	}
    }

    public int priority()
    {
	return 0;
    }
}
