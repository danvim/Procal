package fx50.nodes;


import fx50.API.InputToken;
import org.bychan.core.dynamic.UserParserCallback;

import java.math.BigDecimal;
import java.util.List;

/**
 * Shorthand If Node
 */
public class ShorthandIfNode implements CalculatorNode {
    private final CalculatorNode ifNode;
    private CalculatorNode thenNode;

    public ShorthandIfNode(CalculatorNode left, UserParserCallback<CalculatorNode> parser) {
        ifNode = left;

        thenNode = parser.expression(left);
    }

    public BigDecimal evaluate() {
        if (ifNode.evaluate().compareTo(BigDecimal.ZERO) != 0)
            return thenNode.evaluate();
        return new BigDecimal(0);
    }

    public String toString() {
        return ifNode.toString() + "=>(" + thenNode.toString() + ")";
    }

    public List<InputToken> toInputTokens() {
        List<InputToken> resultTokens = ifNode.toInputTokens();
        resultTokens.add(new InputToken("=>", "⇒"));
        resultTokens.addAll(thenNode.toInputTokens());
        return resultTokens;
    }
}
