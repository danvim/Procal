package fx50.nodes;

import fx50.API.InputToken;
import fx50.CalculatorHelper;
import org.bychan.core.dynamic.UserParserCallback;

import java.math.BigDecimal;
import java.util.List;

import static fx50.ParsingHelper.nextIsStatementEnd;

/**
 * Statement Node
 */
public class StatementNode implements CalculatorNode {
    protected final CalculatorNode left;
    protected final CalculatorNode right;
    protected final boolean isLast;

    public StatementNode(CalculatorNode left, UserParserCallback parser) {
        this.left = left;
        isLast = nextIsStatementEnd(parser);
        if (!isLast)
            this.right = (CalculatorNode) parser.expression(left);
        else this.right = null;
    }

    public BigDecimal evaluate() {
        BigDecimal leftResult = left.evaluate();
        CalculatorHelper.VariableMap.setValue("~Ans", leftResult);
        if (!isLast) {
            return right.evaluate();
        } else
            return leftResult;
    }

    public String toString() {
        if (!isLast)
            return left.toString() + ":\n" + right.toString();
        else
            return left.toString() + ":";
    }

    public List<InputToken> toInputTokens() {
        List<InputToken> resultTokens = left.toInputTokens();
        resultTokens.add(new InputToken(":", ":"));
        if (!isLast) {
            resultTokens.addAll(right.toInputTokens());
        }
        return resultTokens;
    }
}
