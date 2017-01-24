package fx50.nodes;

import java.math.BigDecimal;
import java.util.List;

import fx50.API.InputToken;

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
        resultTokens.add(new InputToken("-", Character.toString((char) 0x2212)));
        resultTokens.addAll(right.toInputTokens());
        return resultTokens;
    }
}
