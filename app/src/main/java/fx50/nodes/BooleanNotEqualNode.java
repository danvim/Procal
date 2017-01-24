package fx50.nodes;

import java.math.BigDecimal;
import java.util.List;

import fx50.API.InputToken;

/**
 * Boolean Not Equal Node
 */
public class BooleanNotEqualNode implements BooleanNode {
    private final CalculatorNode left;
    private final CalculatorNode right;

    public BooleanNotEqualNode(CalculatorNode left, CalculatorNode right) {
        this.left = left;
        this.right = right;
    }

    public BigDecimal evaluate() {
        return compare(left, right);
    }

    public String toString() {
        return "(" + left.toString() + ")!=(" + right.toString() + ")";
    }

    public List<InputToken> toInputTokens() {
        List<InputToken> resultTokens = left.toInputTokens();
        resultTokens.add(new InputToken("!=", Character.toString((char) 0x2260)));
        resultTokens.addAll(right.toInputTokens());
        return resultTokens;
    }

    public BigDecimal compare(CalculatorNode left, CalculatorNode right) {
        return new BigDecimal(left.evaluate().compareTo(right.evaluate()) == 0 ? 0 : 1);
    }
}
