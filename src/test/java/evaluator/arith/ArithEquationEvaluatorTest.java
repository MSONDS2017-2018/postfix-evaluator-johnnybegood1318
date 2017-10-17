package evaluator.arith;

import static org.junit.Assert.assertEquals;

import evaluator.IllegalPostFixExpressionException;
import evaluator.PostFixEvaluator;

import org.junit.Before;
import org.junit.Test;

public class ArithEquationEvaluatorTest {

    private PostFixEvaluator<Integer> evaluator;

    @Before
    public void setup() {
	evaluator = new ArithEquationEvaluator();
    }

    @Test(timeout = 5000)
    public void testEvaluateSimple() {
	Integer result = evaluator.evaluate("1");
	assertEquals(new Integer(1), result);
    }

    @Test(timeout = 5000)
    public void testEvaluatePlus() {
	Integer result = evaluator.evaluate("w + 2 = 3");
	assertEquals(new Integer(1), result);

	result = evaluator.evaluate("1 + e + 3 = 6");
	assertEquals(new Integer(2), result);

	result = evaluator.evaluate("10000 + e + 100 + 10 + 1 = 11111");
	assertEquals(new Integer(1000), result);
    }

    @Test(timeout = 5000)
    public void testEvaluateSub() {
	Integer result = evaluator.evaluate("q - 2 = -1");
	assertEquals(new Integer(1), result);

	result = evaluator.evaluate("x - 2 - 3 = -4");
	assertEquals(new Integer(1), result);

	result = evaluator.evaluate("1000 - 100 - c - 1 = 889");
	assertEquals(new Integer(10), result);
    }

    @Test(timeout = 5000)
    public void testEvaluateMult() {
	Integer result = evaluator.evaluate("1 * x = 2");
	assertEquals(new Integer(2), result);

	result = evaluator.evaluate("1 * x * 3 = 6");
	assertEquals(new Integer(2), result);

	result = evaluator.evaluate("1 * 2 * 3 * r = 24");
	assertEquals(new Integer(4), result);

	result = evaluator.evaluate("3 * 3 * x + 9 = 18");
	assertEquals(new Integer(1), result);
    }

    @Test(timeout = 5000)
    public void testEvaluateNegate() {
	Integer result = evaluator.evaluate("! x = 1");
	assertEquals(new Integer(-1), result);

	result = evaluator.evaluate("! x = 2");
	assertEquals(new Integer(-2), result);

	result = evaluator.evaluate("! x = -15");
	assertEquals(new Integer(15), result);
    }

    @Test(timeout = 5000)
    public void testEvaluateDiv() {
	Integer result = evaluator.evaluate("Z / 1 = 2");
	assertEquals(new Integer(2), result);

	result = evaluator.evaluate("a / 3 = 5");
	assertEquals(new Integer(15), result);

	result = evaluator.evaluate("-140 / x = -14");
	assertEquals(new Integer(10), result);
    }

    @Test(timeout = 5000)
    public void testEvaluateComplex() {
	Integer result = evaluator.evaluate("4 / x  * 2 = 8");
	assertEquals(new Integer(1), result);

	result = evaluator.evaluate("x * 15 / 3 + 6 = 16");
	assertEquals(new Integer(2), result);

	result = evaluator.evaluate("! 140 / x = -14");
	assertEquals(new Integer(10), result);

	result = evaluator.evaluate("15 + ! 12 / x = 12");
	assertEquals(new Integer(4), result);
    }

    @Test(timeout = 5000, expected = IllegalPostFixExpressionException.class)
    public void testInvalidExpression() {
	evaluator.evaluate("1 2");
    }

}
