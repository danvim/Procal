package fx50.nodes;

import fx50.API.InputToken;

import java.math.BigDecimal;
import java.util.List;

/**
 * Subtraction Node
 */
public class SubtractionNode implements CalculatorNode {
    private final CalculatorNode left;
    private final CalculatorNode right;

    public SubtractionNode(CalculatorNode left, CalculatorNode right) {
        this.left = left;
        this.right = right;
    }

    public BigDecimal evaluate() {
        return left.evaluate().subtract(right.evaluate());
    }

    public String toString() {
        return "(" + left.toString() + "-" + right.toString() + ")";
    }

    public List<InputToken> toInputTokens() {
        List<InputToken> resultTokens = left.toInputTokens();
        resultTokens.add(new InputToken("-", "−"));
        resultTokens.addAll(right.toInputTokens());
        return resultTokens;
    }
}
