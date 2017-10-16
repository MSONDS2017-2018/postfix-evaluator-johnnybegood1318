package evaluator.arith;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import evaluator.IllegalPostFixExpressionException;
import evaluator.PostFixEvaluator;
import language.Operand;
import language.Operator;
import language.arith.ExpOperator;
import language.arith.SubOperator;
import language.arith.VariableOperator;
import parser.arith.ArithEquationParser;
import stack.LinearNodes;
import stack.LinkedStack;
import stack.StackInterface;

public class ArithEquationEvaluator implements PostFixEvaluator<Integer>
{
    private Operand<Integer> solution;
    private String expr;
    private Operator<Integer> nextOperator;

    private final StackInterface<LinearNodes<Operand<Integer>>> operand;
    private final StackInterface<LinearNodes<Operator<Integer>>> operator;
    private Operator<Integer> operatorTop;

    public ArithEquationEvaluator()
    {
	this.operand = new LinkedStack<>();
	this.operator = new LinkedStack<>();
    }

    @Override
    public Integer evaluate(String expression)
    {
	checkString(expression);
	setupEquation(expression);

	fillStacks(expr);
	clearStacks();
	return solution.getValue();
    }

    public void checkString(String expression)
    {
	Pattern p1 = Pattern.compile("\\d");
	Pattern p2 = Pattern.compile("([!+/*-^])");
	Matcher m1 = p1.matcher(expression);
	Matcher m2 = p2.matcher(expression);

	if (!m1.find() || (!m2.find() && !expression.matches("\\d"))
		|| !expression.contains("="))
	    throw new IllegalPostFixExpressionException();
    }

    public void setupEquation(String eq)
    {
	String[] parts = eq.split("=");
	for (int i = 0; i < parts.length; i++)
	{
	    parts[i] = parts[i].trim();
	    try
	    {
		solution = new Operand<Integer>(new Integer(parts[i]));
	    } catch (Exception e)
	    {
		if (!parts[i].equals("="))
		{
		    expr = parts[i];
		}
	    }
	}
    }

    private void fillStacks(String expr)
    {
	ArithEquationParser parser = new ArithEquationParser(expr);
	while (parser.hasNext())
	{
	    switch (parser.nextType())
	    {
		case OPERAND:
		    if (operand.size() == 0)
		    {
			operand.push(new LinearNodes<Operand<Integer>>(parser
				.nextOperand(), null));
		    } else
		    {
			operand.push(new LinearNodes<Operand<Integer>>(parser
				.nextOperand(), operand.top()));
		    }
		    break;
		case OPERATOR:
		    nextOperator = parser.nextOperator();
		    handleOperator(nextOperator);
		    break;
		default:
		    // TODO If we get here, something went terribly wrong
	    }
	}
    }

    private void handleOperator(Operator<Integer> temp)
    {
	if (operatorTop == null)
	{
	    operator.push(new LinearNodes<Operator<Integer>>(temp, null));
	    operatorTop = operator.top().getValue();
	} else if (operatorTop.priority() > temp.priority())
	{
	    lowerOperation();
	    if (operator.isEmpty())
	    {
		operator.push(new LinearNodes<Operator<Integer>>(temp, null));
	    } else
	    {
		operator.push(new LinearNodes<Operator<Integer>>(temp, operator
			.top()));
	    }
	    operatorTop = temp;
	} else if (operatorTop.priority() < temp.priority() || operatorTop
		.priority() == temp.priority())
	{
	    operator.push(new LinearNodes<Operator<Integer>>(temp, operator
		    .top()));
	    operatorTop = temp;
	}
    }

    private void lowerOperation()
    {
	Operator<Integer> operation = operator.pop().getValue();
	if (operator.isEmpty())
	{
	    operatorTop = null;
	} else
	{
	    operatorTop = operator.top().getValue();
	}
	switch (operation.getNumberOfArguments())
	{
	    case 1:
		unary(operation);
		break;
	    case 2:
		twoArgs(operation);
		break;
	}
    }

    private void unary(Operator<Integer> operation)
    {
	operation.setOperand(0, solution);
	solution = operation.performOperation();
    }

    private void twoArgs(Operator<Integer> operation)
    {
	Operand<Integer> value1 = operand.pop().getValue();
	operation.setOperand(0, value1);
	operation.setOperand(1, solution);

	if (operation.getClass().equals(SubOperator.class) && nextOperator
		.getClass().equals(VariableOperator.class))
	{
	    System.out.println("enter");
	    solution = new Operand<Integer>(solution.getValue() * -1);
	    operator.pop();
	} else if (operation.getClass().toString().contains("Exp")
		&& operatorTop != null && operatorTop.getClass().toString()
			.contains("Var"))
	{
	    ExpOperator temp = (ExpOperator) operation;
	    solution = temp.optionalInverse();
	} else if (operation.getClass().toString().contains("Div")
		&& nextOperator.getClass().equals(VariableOperator.class))
	{
	    solution = operation.performOperation();
	} else
	{
	    solution = operation.performInverse();
	}
	System.out.println(solution);
    }

    private void clearStacks()
    {
	while (!operator.isEmpty())
	{
	    lowerOperation();
	}
    }

    public static void main(String[] args)
    {
	ArithEquationEvaluator test = new ArithEquationEvaluator();
	System.out.println(test.evaluate("1000 - 100 - x - 1 = 889"));
    }
}
