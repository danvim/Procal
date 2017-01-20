package fx50.nodes;

import fx50.API.InputToken;
import org.bychan.core.basic.Lexeme;
import org.bychan.core.dynamic.UserParserCallback;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static fx50.ParsingHelper.nextMustBeSeparator;

/**
 * Break Node
 */

public class BreakNode implements CalculatorNode {

    public BreakNode(CalculatorNode left, UserParserCallback<CalculatorNode> parser, Lexeme<CalculatorNode> lexeme) {
        nextMustBeSeparator(parser, "break");
    }

    public BigDecimal evaluate() {
        throw new RuntimeException("Breaking");
    }

    public List<InputToken> toInputTokens() {
        return new ArrayList<>(Collections.singletonList(new InputToken("Break", "Break")));
    }

    public String toString() {
        return "Break";
    }
}
