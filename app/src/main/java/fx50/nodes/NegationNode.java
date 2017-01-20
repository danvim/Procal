package fx50.nodes;

import fx50.API.InputToken;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Negation Node
 */
public class NegationNode implements CalculatorNode {
    private final CalculatorNode left;

    public NegationNode(CalculatorNode left) {
        this.left = left;
    }

    public BigDecimal evaluate() {
        return left.evaluate().negate();
    }

    public String toString() {
        return "(-" + left.toString() + ")";
    }

    public List<InputToken> toInputTokens() {
        List<InputToken> resultTokens = new ArrayList<>(Collections.singletonList(new InputToken("(-)", "-")));
        resultTokens.addAll(left.toInputTokens());
        return resultTokens;
    }
}
