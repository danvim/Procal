package fx50.nodes;

import fx50.API.InputToken;
import org.bychan.core.basic.Lexeme;
import org.bychan.core.dynamic.UserParserCallback;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static fx50.CalculatorHelper.*;

/**
 * Label Node
 */
public class LabelNode implements CalculatorNode {
    private final String label = "";

    //TODO Erroneous
    public LabelNode(CalculatorNode left, UserParserCallback<CalculatorNode> parser, Lexeme<CalculatorNode> lexeme) {
        /*label = lexeme.getText().replaceAll("Lbl +", "");
        if (label.equals(""))
            parser.abort("Label string missing");
        nextMustBeSeparator(parser, "label");
        labels.put(label, this);*/
    }

    public BigDecimal evaluate() {
        return VariableMap.getValue("~Ans");
    }

    public String toString() {
        return "Lbl " + label;
    }

    public List<InputToken> toInputTokens() {
        List<InputToken> resultTokens = new ArrayList<>(Collections.singletonList(new InputToken("Lbl ", "Lbl")));
        resultTokens.add(new InputToken(label, label));
        resultTokens.add(new InputToken(":", ":"));
        return resultTokens;
    }
}
