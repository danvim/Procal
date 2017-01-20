package fx50.nodes;

import fx50.API.Color;
import fx50.API.InputToken;
import fx50.CalculatorHelper;
import org.bychan.core.basic.Lexeme;
import org.bychan.core.dynamic.UserParserCallback;

import java.math.BigDecimal;
import java.util.List;

import static fx50.ParsingHelper.nextMustBeSeparator;

/**
 * M+ Node
 */
public class MPlusNode implements CalculatorNode {
    private final CalculatorNode left;

    public MPlusNode(CalculatorNode left, UserParserCallback<CalculatorNode> parser, Lexeme<CalculatorNode> lexeme) {
        this.left = left;
        nextMustBeSeparator(parser, "M+");
    }

    public BigDecimal evaluate() {
        BigDecimal m = CalculatorHelper.VariableMap.getValue("M");
        BigDecimal leftResult = left.evaluate();
        CalculatorHelper.VariableMap.setValue("M", m.add(left.evaluate()));
        return leftResult;
    }

    public String toString() {
        return "(" + left.toString() + ") M+";
    }

    public List<InputToken> toInputTokens() {
        List<InputToken> resultTokens = left.toInputTokens();
        resultTokens.add(new InputToken("M+", "M+", Color.MEMORY));
        return resultTokens;
    }
}
