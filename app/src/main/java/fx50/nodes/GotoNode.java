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
 * Goto Node
 */
public class GotoNode implements CalculatorNode {
    private final String label;
    private UserParserCallback<CalculatorNode> parser;

    //TODO Erroneous
    public GotoNode(CalculatorNode left, UserParserCallback<CalculatorNode> parser, Lexeme<CalculatorNode> lexeme) {
        label = lexeme.getText().replaceAll("Goto +", "");
        if (label.equals(""))
            parser.abort("Label string missing");
        this.parser = parser;
        nextMustBeSeparator(parser, "label");
    }

    public BigDecimal evaluate() {
        /*if (labels.containsKey(label))
            return parser.expression(labels.get(label)).evaluate();*/
        throw new RuntimeException("Label '" + label + "' not found.");
    }

    public String toString() {
        return "Goto " + label + ":";
    }

    public List<InputToken> toInputTokens() {
        List<InputToken> resultTokens = new ArrayList<>(Collections.singletonList(new InputToken("Goto ", "Goto")));
        resultTokens.add(new InputToken(label, label));
        resultTokens.add(new InputToken(":", ":"));
        return resultTokens;
    }
}
