package evaluator.arith;

import evaluator.IllegalPostFixExpressionException;
import evaluator.PostFixEvaluator;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import language.Operand;
import language.Operator;
import parser.arith.ArithPostFixParser;
import stack.LinkedStack;
import stack.LinearNodes;
import stack.StackInterface;

public class ArithInFixEvaluator implements PostFixEvaluator<Integer> {

    private final StackInterface<LinearNodes<Operand<Integer>>> operand;
    private final StackInterface<LinearNodes<Operator<Integer>>> operator;
    private Operator<Integer> operatorTop;

    /**
     * Constructs an {@link ArithInFixEvaluator}.
     */
    public ArithInFixEvaluator() {
	this.operand = new LinkedStack<>();
	this.operator = new LinkedStack<>();
    }

    /**
     * Evaluates a infix expression.
     * 
     * @return the result
     */
    public Integer evaluate(String expr) {
	checkString(expr);

	fillStacks(expr);

	clearOperators();
	Integer result = operand.top().getValue().getValue();
	operand.clear();
	return result;
    }

    private void checkString(String expr) {
	Pattern p1 = Pattern.compile("\\d");
	Pattern p2 = Pattern.compile("([!+/*-])");
	Matcher m1 = p1.matcher(expr);
	Matcher m2 = p2.matcher(expr);

	if (!m1.find() || (!m2.find() && !expr.matches("\\d")))
	    throw new IllegalPostFixExpressionException();
    }

    private void fillStacks(String expr) {
	ArithPostFixParser parser = new ArithPostFixParser(expr);
	while (parser.hasNext()) {
	    switch (parser.nextType()) {
		case OPERAND:
		    if (operand.size() == 0) {
			operand.push(new LinearNodes<Operand<Integer>>(parser
				.nextOperand(), null));
		    } else {
			operand.push(new LinearNodes<Operand<Integer>>(parser
				.nextOperand(), operand.top()));
		    }
		    break;
		case OPERATOR:
		    Operator<Integer> temp = parser.nextOperator();
		    if (operatorTop == null) {
			operator.push(new LinearNodes<Operator<Integer>>(temp,
				null));
			operatorTop = operator.top().getValue();
		    } else if (operatorTop.priority() < temp.priority()
			    || operatorTop.priority() == temp.priority()) {
			while (operatorTop != null && operatorTop
				.priority() <= temp.priority())
			    higherOperation();
			if (operator.isEmpty()) {
			    operator.push(new LinearNodes<Operator<Integer>>(
				    temp, null));
			} else {
			    operator.push(new LinearNodes<Operator<Integer>>(
				    temp, operator.top()));
			}
			operatorTop = temp;
		    } else if (operatorTop.priority() > temp.priority()) {
			operator.push(new LinearNodes<Operator<Integer>>(temp,
				operator.top()));
			operatorTop = temp;
		    }
		    break;
		default:
		    // TODO If we get here, something went terribly wrong
	    }
	}
    }

    private void higherOperation() {
	Operator<Integer> operation = operator.pop().getValue();
	if (operator.isEmpty()) {
	    operatorTop = null;
	} else {
	    operatorTop = operator.top().getValue();
	}
	switch (operation.getNumberOfArguments()) {
	    case 1:
		unary(operation);
		break;
	    case 2:
		twoArgs(operation);
		break;
	}
    }

    private void unary(Operator<Integer> operation) {
	Operand<Integer> value = operand.pop().getValue();
	operation.setOperand(0, value);
	value = operation.performOperation();
	if (!operand.isEmpty()) {
	    operand.push(new LinearNodes<Operand<Integer>>(value, operand
		    .top()));
	} else {
	    operand.push(new LinearNodes<Operand<Integer>>(value, null));
	}
    }

    private void twoArgs(Operator<Integer> operation) {
	Operand<Integer> value1 = operand.pop().getValue();
	Operand<Integer> value2 = operand.pop().getValue();
	operation.setOperand(0, value2);
	operation.setOperand(1, value1);
	value1 = operation.performOperation();
	if (!operand.isEmpty()) {
	    operand.push(new LinearNodes<Operand<Integer>>(value1, operand
		    .top()));
	} else {
	    operand.push(new LinearNodes<Operand<Integer>>(value1, null));
	}
    }

    private void clearOperators() {
	while (!operator.isEmpty()) {
	    higherOperation();
	}
    }

    public static void main(String[] args) {
	ArithInFixEvaluator test = new ArithInFixEvaluator();
	System.out.println(test.evaluate("1 - 2 - 3"));
    }

}
