package language.arith;

import language.Operand;
import language.Operator;
import language.VariableException;

public class VariableOperator implements Operator<Integer> {

    @Override
    public Operand<Integer> performOperation() {
	throw new VariableException("No operation");
    }

    @Override
    public int priority() {
	return -1;
    }

    @Override
    public Operand<Integer> performInverse() {
	throw new VariableException("No inverse");
    }

    @Override
    public void setOperand(final int i, final Operand<Integer> operand) {
	throw new VariableException("No operands");
    }

    @Override
    public final int getNumberOfArguments() {
	return 0;
    }

}
