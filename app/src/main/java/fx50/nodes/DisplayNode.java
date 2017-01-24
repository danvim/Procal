package fx50.nodes;

import org.bychan.core.dynamic.UserParserCallback;

import java.math.BigDecimal;
import java.util.List;

import dcheungaa.procal.MainActivity;
import fx50.API.InputToken;
import fx50.Fx50ParserCallable;
import fx50.IOMessage;

/**
 * Statement Node
 */
public class DisplayNode extends StatementNode {

    public DisplayNode(CalculatorNode left, UserParserCallback<CalculatorNode> parser) {
        super(left, parser);
    }

    public BigDecimal evaluate() {
        BigDecimal leftResult;
        leftResult = left.evaluate();
        MainActivity.fx50Parser.printOutput(new IOMessage("display", Fx50ParserCallable.formatResult(leftResult), left.toInputTokens()));
        if (!isLast) {
            return right.evaluate();
        } else
            return leftResult;
    }

    public String toString() {
        if (!isLast)
            return left.toString() + " display\n" + right.toString();
        else
            return left.toString() + " display";
    }

    public List<InputToken> toInputTokens() {
        List<InputToken> resultTokens = left.toInputTokens();
        resultTokens.add(new InputToken("display", Character.toString((char) 0x25E2)));
        if (!isLast)
            resultTokens.addAll(right.toInputTokens());
        return resultTokens;
    }
}
