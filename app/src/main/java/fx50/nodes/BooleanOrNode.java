package fx50.nodes;

import fx50.API.InputToken;

import java.math.BigDecimal;
import java.util.List;

/**
 * Boolean Or Node
 */
public class BooleanOrNode implements BooleanNode {
    private final CalculatorNode left;
    private final CalculatorNode right;

    public BooleanOrNode(CalculatorNode left, CalculatorNode right) {
        this.left = left;
        this.right = right;
    }

    public BigDecimal evaluate() {
        return compare(left, right);
    }

    public String toString() {
        return "(" + left.toString() + ")or(" + right.toString() + ")";
    }

    public List<InputToken> toInputTokens() {
        List<InputToken> resultTokens = left.toInputTokens();
        resultTokens.add(new InputToken("or", "OR"));
        resultTokens.addAll(right.toInputTokens());
        return resultTokens;
    }

    public BigDecimal compare(CalculatorNode left, CalculatorNode right) {
        return new BigDecimal(left.evaluate().compareTo(BigDecimal.ZERO) == 1 ||
                right.evaluate().compareTo(BigDecimal.ZERO) == 1 ? 1 : 0);
    }
}
