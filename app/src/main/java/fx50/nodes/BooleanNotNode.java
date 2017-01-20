package fx50.nodes;

import fx50.API.InputToken;
import org.bychan.core.basic.Lexeme;
import org.bychan.core.dynamic.UserParserCallback;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Boolean Not Node
 */
public class BooleanNotNode implements BooleanNode {
    private final CalculatorNode right;

    public BooleanNotNode(CalculatorNode left, UserParserCallback<CalculatorNode> parser, Lexeme<CalculatorNode> lexeme) {
        this.right = parser.expression(left);
    }

    public BigDecimal evaluate() {
        return new BigDecimal(right.evaluate().compareTo(BigDecimal.ZERO) != 1 ? 1 : 0);
    }

    public String toString() {
        return "not(" + right.toString() + ")";
    }

    public List<InputToken> toInputTokens() {
        List<InputToken> resultTokens = new ArrayList<>(Collections.singletonList(new InputToken("not", "NOT")));
        resultTokens.addAll(right.toInputTokens());
        return resultTokens;
    }

    public BigDecimal compare(CalculatorNode left, CalculatorNode right) {
        return new BigDecimal(0);
    }
}
