package fx50.nodes;

import java.math.BigDecimal;
import java.util.List;

import fx50.API.InputToken;
import fx50.CalcMath.CalcMath;

import static fx50.CalcMath.CalcMath.isInt;

/**
 * Combination Node
 */
public class CombinationNode implements CalculatorNode {
    private final CalculatorNode left;
    private final CalculatorNode right;

    public CombinationNode(CalculatorNode left, CalculatorNode right) {
        this.left = left;
        this.right = right;
    }

    public BigDecimal evaluate() {
        BigDecimal leftResult = left.evaluate();
        BigDecimal rightResult = right.evaluate();
        if (leftResult.compareTo(rightResult) == -1 ||
                leftResult.compareTo(BigDecimal.ZERO) == -1 ||
                rightResult.compareTo(BigDecimal.ZERO) == -1 ||
                !isInt(leftResult) || !isInt(rightResult))
            throw new ArithmeticException("Math error: n and r must be positive non-zero integers and n must be greater or equals to r");
        return CalcMath.combination(left.evaluate(),right.evaluate());
    }

    public String toString() {
        return "(" + left.toString() + "C" + right.toString() + ")";
    }

    public List<InputToken> toInputTokens() {
        List<InputToken> resultTokens = left.toInputTokens();
        resultTokens.add(new InputToken("C", Character.toString((char) 0x2102)));
        resultTokens.addAll(right.toInputTokens());
        return resultTokens;
    }
}
