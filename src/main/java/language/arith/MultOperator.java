package language.arith;

import language.Operand;
import language.BinaryOperator;

/**
 * The {@code MultOperator} is an operator that performs multiplication on two
 * integers.
 * 
 * @author jcollard, jddevaug
 *
 */
public class MultOperator extends BinaryOperator<Integer> {

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
	Integer result = op0.getValue() * op1.getValue();
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
	if (op1.getValue() == 0) {
	    throw new IllegalStateException("Denominator is zero");
	}
	Integer result = op1.getValue() / op0.getValue();
	return new Operand<Integer>(result);
    }

    public int priority() {
	return 2;
    }
}
