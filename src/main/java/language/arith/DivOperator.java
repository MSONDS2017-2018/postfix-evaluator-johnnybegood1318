package language.arith;

import java.lang.IllegalStateException;
import language.Operand;
import language.BinaryOperator;

/**
 * The {@link DivOperator} is an operator that performs division on two
 * integers.
 * 
 * @author jcollard, jddevaug
 *
 */
public class DivOperator extends BinaryOperator<Integer> {

    // TODO Before you get started, have you looked at the
    // PlusOperator class? You'll notice that it is taking advantage
    // of the abstract BinaryOperator class. Take a moment to
    // also look at that class. Finally, you should implement
    // this class.

    /**
     * {@inheritDoc}
     */
    @Override
    public Operand<Integer> performOperation() {
	Operand<Integer> op0 = this.getOp0();
	Operand<Integer> op1 = this.getOp1();
	if (op0 == null || op1 == null) {
	    throw new IllegalStateException(
		    "Could not perform operation prior to operands being set.");
	}
	if (op1.getValue() == 0) {
	    throw new IllegalStateException("Denominator is zero");
	}
	Integer result = op0.getValue() / op1.getValue();
	return new Operand<Integer>(result);
    }

    @Override
    public Operand<Integer> performInverse() {
	Operand<Integer> op0 = this.getOp0();
	Operand<Integer> op1 = this.getOp1();
	if (op0 == null || op1 == null) {
	    throw new IllegalStateException(
		    "Could not perform operation prior to operands being set.");
	}
	Integer result = op0.getValue() * op1.getValue();
	return new Operand<Integer>(result);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setOperand(int i, Operand<Integer> operand) {
	if (i == 1 && operand.getValue() == 0) {
	    throw new IllegalStateException();
	}
	if (operand == null) {
	    throw new NullPointerException("Could not set null operand.");
	}
	if (i > 1) {
	    throw new IllegalArgumentException(
		    "Binary operator only accepts operands 0 and 1 "
			    + "but recieved " + i + ".");
	}
	if (i == 0) {
	    super.op0 = operand;
	} else {
	    super.op1 = operand;
	}
    }

    @Override
    public int priority() {
	return 2;
    }
}
