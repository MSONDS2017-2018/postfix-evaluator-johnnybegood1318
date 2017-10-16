package evaluator.arith;

import static org.junit.Assert.assertEquals;

import evaluator.IllegalPostFixExpressionException;
import evaluator.PostFixEvaluator;

import org.junit.Before;
import org.junit.Test;

public class ArithInFixEvaluatorTest
{

    private PostFixEvaluator<Integer> evaluator;

    @Before
    public void setup()
    {
	evaluator = new ArithInFixEvaluator();
    }

    @Test(timeout = 5000)
    public void testEvaluateSimple()
    {
	Integer result = evaluator.evaluate("1");
	assertEquals(new Integer(1), result);
    }

    @Test(timeout = 5000)
    public void testEvaluatePlus()
    {
	Integer result = evaluator.evaluate("1 + 2");
	assertEquals(new Integer(3), result);

	result = evaluator.evaluate("1 + 2 + 3");
	assertEquals(new Integer(6), result);

	result = evaluator.evaluate("10000 + 1000 + 100 + 10 + 1");
	assertEquals(new Integer(11111), result);
    }

    @Test(timeout = 5000)
    public void testEvaluateSub()
    {
	Integer result = evaluator.evaluate("1 - 2");
	assertEquals(new Integer(-1), result);

	result = evaluator.evaluate("1 - 2 - 3");
	assertEquals(new Integer(-4), result);

	result = evaluator.evaluate("1000 - 100 - 10 - 1");
	assertEquals(new Integer(889), result);
    }

    @Test(timeout = 5000)
    public void testEvaluateMult()
    {
	Integer result = evaluator.evaluate("1 * 2");
	assertEquals(new Integer(2), result);

	result = evaluator.evaluate("1 * 2 * 3");
	assertEquals(new Integer(6), result);

	result = evaluator.evaluate("1 * 2 * 3 * 4");
	assertEquals(new Integer(24), result);
    }

    @Test(timeout = 5000)
    public void testEvaluateNegate()
    {
	Integer result = evaluator.evaluate("! 1");
	assertEquals(new Integer(-1), result);

	result = evaluator.evaluate("! 2");
	assertEquals(new Integer(-2), result);

	result = evaluator.evaluate("! -15");
	assertEquals(new Integer(15), result);
    }

    @Test(timeout = 5000)
    public void testEvaluateDiv()
    {
	Integer result = evaluator.evaluate("2 / 1");
	assertEquals(new Integer(2), result);

	result = evaluator.evaluate("15 / 3");
	assertEquals(new Integer(5), result);

	result = evaluator.evaluate("-140 / 10");
	assertEquals(new Integer(-14), result);
    }

    @Test(timeout = 5000)
    public void testEvaluateComplex()
    {
	Integer result = evaluator.evaluate("2 / 1 * 2");
	assertEquals(new Integer(4), result);

	result = evaluator.evaluate("2 * 15 / 3 + 6");
	assertEquals(new Integer(16), result);

	result = evaluator.evaluate("! 140 / 10");
	assertEquals(new Integer(-14), result);

	result = evaluator.evaluate("15 + ! 12 / 4");
	assertEquals(new Integer(12), result);
    }

    @Test(timeout = 5000, expected = IllegalPostFixExpressionException.class)
    public void testInvalidExpression()
    {
	evaluator.evaluate("1 2");
    }

}
