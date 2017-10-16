package language.arith;

import language.BinaryOperator;
import language.Operand;

public class ExpOperator extends BinaryOperator<Integer>
{

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
	Integer result = (int) Math.pow(op0.getValue(), op1.getValue());
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
	Integer result = (int) (Math.log(op1.getValue()) / Math.log(op0
		.getValue()));

	return new Operand<Integer>(result);
    }

    public Operand<Integer> optionalInverse()
    {
	Operand<Integer> op0 = this.getOp0();
	Operand<Integer> op1 = this.getOp1();
	if (op0 == null || op1 == null)
	{
	    throw new IllegalStateException(
		    "Could not perform operation prior to operands being set.");
	}
	Integer result = (int) Math.pow(op1.getValue(), 1.0 / op0.getValue());

	return new Operand<Integer>(result);
    }

    @Override
    public int priority()
    {
	return 1;
    }

}
