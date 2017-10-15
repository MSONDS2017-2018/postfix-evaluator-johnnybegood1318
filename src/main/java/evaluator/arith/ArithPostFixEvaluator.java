package evaluator.arith;

import evaluator.IllegalPostFixExpressionException;
import evaluator.PostFixEvaluator;
import language.Operand;
import language.Operator;
import parser.arith.ArithPostFixParser;
import stack.StackInterface;
import stack.LinkedStack;
import stack.LinearNodes;
import java.util.regex.*;

/**
 * An {@link ArithPostFixEvaluator} is a post fix evaluator over simple
 * arithmetic expressions.
 *
 */
public class ArithPostFixEvaluator implements PostFixEvaluator<Integer>
{

    private final StackInterface<LinearNodes<Operand<Integer>>> stack;

    /**
     * Constructs an {@link ArithPostFixEvaluator}.
     */
    public ArithPostFixEvaluator()
    {
	this.stack = new LinkedStack<>(); // TODO Initialize to your LinkedStack
    }

    /**
     * Evaluates a postfix expression.
     * 
     * @return the result
     */
    @Override
    public Integer evaluate(String expr)
    {
	Pattern p1 = Pattern.compile("\\d");
	Pattern p2 = Pattern.compile("([!+/*-])");
	Matcher m1 = p1.matcher(expr);
	Matcher m2 = p2.matcher(expr);

	if (!m1.find() || (!m2.find() && !expr.matches("\\d")))
	    throw new IllegalPostFixExpressionException();

	ArithPostFixParser parser = new ArithPostFixParser(expr);
	while (parser.hasNext())
	{
	    switch (parser.nextType())
	    {
		case OPERAND:
		    if (stack.size() == 0)
		    {
			stack.push(new LinearNodes<Operand<Integer>>(parser
				.nextOperand(), null));
		    } else
		    {
			stack.push(new LinearNodes<Operand<Integer>>(parser
				.nextOperand(), stack.top()));
		    }
		    break;
		case OPERATOR:
		    operatorFound(parser);
		    break;
		default:
		    // TODO If we get here, something went terribly wrong
	    }
	}
	System.out.println(stack.toString());
	Integer result = stack.top().getValue().getValue();
	stack.clear();
	return result;
    }

    private void operatorFound(ArithPostFixParser parser)
    {
	Operator<Integer> next = parser.nextOperator();
	LinearNodes<Operand<Integer>> nextNode = stack.top();
	switch (next.getNumberOfArguments())
	{
	    case 2:
		twoArgs(next, nextNode);
		break;
	    case 1:
		unary(next, nextNode);
	}

    }

    private LinearNodes<Operand<Integer>> twoArgs(Operator<Integer> operator,
	    LinearNodes<Operand<Integer>> next)
    {
	if (next != null)
	{
	    if (next.getNext().getNext() == null)
	    {
		operator.setOperand(0, next.getNext().getValue());
		operator.setOperand(1, next.getValue());
		next.setValue(operator.performOperation());
		next.setNext(null);
		return next;
	    }
	}
	next = twoArgs(operator, next.getNext());
	return next;
    }

    private LinearNodes<Operand<Integer>> unary(Operator<Integer> operator,
	    LinearNodes<Operand<Integer>> next)
    {
	if (next != null)
	{
	    if (next.getNext() == null)
	    {
		operator.setOperand(0, next.getValue());
		next.setValue(operator.performOperation());
		return next;
	    }
	}
	next = unary(operator, next.getNext());
	return next;
    }

    public static void main(String[] args)
    {
	ArithPostFixEvaluator test = new ArithPostFixEvaluator();
	Integer x = test.evaluate("1 2 3 - -");
	System.out.println(x);
    }
}
