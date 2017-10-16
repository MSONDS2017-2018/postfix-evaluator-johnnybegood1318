package language.arith;

import language.EqualsException;
import language.Operand;
import language.Operator;

public class EqualOperator implements Operator<Integer>
{

    public EqualOperator()
    {
	// TODO Auto-generated constructor stub
    }

    @Override
    public int getNumberOfArguments()
    {
	return 0;
    }

    @Override
    public Operand<Integer> performOperation()
    {
	throw new EqualsException("Not a valid operation");
    }

    @Override
    public void setOperand(int position, Operand<Integer> operand)
    {
	throw new EqualsException("Equals has no operands");
    }

    @Override
    public int priority()
    {
	return -1;
    }
    
    public Operand<Integer> performInverse()
    {
	throw new EqualsException("Not a valid operation");
    }

}
