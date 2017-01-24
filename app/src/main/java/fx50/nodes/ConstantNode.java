package fx50.nodes;

import org.bychan.core.basic.Lexeme;
import org.bychan.core.dynamic.UserParserCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fx50.API.Color;
import fx50.API.InputToken;
import fx50.CalcMath.Constants;

/**
 * Constant Node
 */
public class ConstantNode extends NumberNode {
    private final String constantName;

    public ConstantNode(CalculatorNode left, UserParserCallback<CalculatorNode> parser, Lexeme lexeme) {
        this.constantName = lexeme.getText().substring(1);
        try {
            this.value = Constants.valueOf(constantName).getValue();
        } catch (IllegalArgumentException e) {parser.abort("Constant not present");}
    }

    public String toString() {
        return "&"+constantName;
    }

    public List<InputToken> toInputTokens() {
        return new ArrayList<>(Collections.singletonList(new InputToken("&" + constantName, Constants.valueOf(constantName).getDisplay(), Color.CONSTANT)));
    }
}
