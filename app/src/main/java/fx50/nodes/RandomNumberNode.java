package fx50.nodes;

import fx50.API.InputToken;
import fx50.CalcMath.Fn;
import org.bychan.core.basic.Lexeme;
import org.bychan.core.dynamic.UserParserCallback;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Random Number Node
 */
public class RandomNumberNode implements CalculatorNode {

    public RandomNumberNode(CalculatorNode left, UserParserCallback<CalculatorNode> parser, Lexeme lexeme) {}

    public BigDecimal evaluate() {
        ArrayList<BigDecimal> args = new ArrayList<>();
        args.add(new BigDecimal(1));
        return Fn.Ran(args);
    }

    public String toString() {
        return "Ran#";
    }

    public List<InputToken> toInputTokens() {
        return new ArrayList<>(Collections.singletonList(new InputToken("Ran#", "Ran#")));
    }
}
