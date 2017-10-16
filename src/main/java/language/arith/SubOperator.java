package language.arith;

import language.Operand;
import language.BinaryOperator;

/**
 * The {@code SubOperator} is an operator that performs subtraction on two
 * integers.
 * 
 * @author jcollard, jddevaug
 *
 */
public class SubOperator extends BinaryOperator<Integer>
{

    // TODO Before you get started, have you looked at the
    // PlusOperator class? You'll notice that it is taking advantage
    // of the abstract BinaryOperator class. Take a moment to
    // also look at that class. Finally, you should implement
    // this class.

    /**
     * {@inheritDoc}
     */
    @Override
    public Operand<Integer> performOperation()
    {
	Operand<Integer> op0 = this.getOp0();
	Operand<Integer> op1 = this.getOp1();
	if (op0 == null || op1 == null)
	{
	    throw new IllegalStateException(
		    "Could not perform operation prior to operands being set.");
	}
	Integer result = op0.getValue() - op1.getValue();
	return new Operand<Integer>(result);
    }

    @Override
    public Operand<Integer> performInverse()
    {
	Operand<Integer> op0 = this.getOp0();
	Operand<Integer> op1 = this.getOp1();
	if (op0 == null || op1 == null)
	{
	    throw new IllegalStateException(
		    "Could not perform operation prior to operands being set.");
	}
	Integer result = op0.getValue() + op1.getValue();
	return new Operand<Integer>(result);
    }
    
    public int priority()
    {
	return 5;
    }
}
