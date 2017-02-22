package fx50.nodes;

import java.math.BigDecimal;
import java.util.List;

import fx50.API.InputToken;

/**
 * Multiplication Node
 */
public class HiddenMultiplicationNode implements CalculatorNode {
    private final CalculatorNode left;
    private final CalculatorNode right;

    public HiddenMultiplicationNode(CalculatorNode left, CalculatorNode right) {
        this.left = left;
        this.right = right;
    }

    public BigDecimal evaluate() {
        return left.evaluate().multiply(right.evaluate());
    }

    public String toString() {
        return "(" + left.toString() + "`" + right.toString() + ")";
    }

    public List<InputToken> toInputTokens() {
        List<InputToken> resultTokens = left.toInputTokens();
        resultTokens.addAll(right.toInputTokens());
        return resultTokens;
    }
}
