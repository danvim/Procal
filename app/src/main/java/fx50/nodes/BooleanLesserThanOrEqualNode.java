package fx50.nodes;

import java.math.BigDecimal;
import java.util.List;

import fx50.API.InputToken;

/**
 * Boolean Lesser Than Or Equal Node
 */
public class BooleanLesserThanOrEqualNode implements BooleanNode {
    private final CalculatorNode left;
    private final CalculatorNode right;

    public BooleanLesserThanOrEqualNode(CalculatorNode left, CalculatorNode right) {
        this.left = left;
        this.right = right;
    }

    public BigDecimal evaluate() {
        return compare(left, right);
    }

    public String toString() {
        return "(" + left.toString() + ")<=(" + right.toString() + ")";
    }

    public List<InputToken> toInputTokens() {
        List<InputToken> resultTokens = left.toInputTokens();
        resultTokens.add(new InputToken("<=", Character.toString((char) 0x2264)));
        resultTokens.addAll(right.toInputTokens());
        return resultTokens;
    }

    public BigDecimal compare(CalculatorNode left, CalculatorNode right) {
        return new BigDecimal(left.evaluate().compareTo(right.evaluate()) <= 0 ? 1 : 0);
    }
}
