package fx50.nodes;

import org.nevec.rjm.BigDecimalMath;

import java.math.BigDecimal;
import java.util.List;

import fx50.API.InputToken;

import static dcheungaa.procal.Tokens.fromUnicode;
import static fx50.CalcMath.CalcMath.inverse;

/**
 * Root Node
 */
public class RootNode implements CalculatorNode {
    private final CalculatorNode left;
    private final CalculatorNode right;

    public RootNode(CalculatorNode left, CalculatorNode right) {
        this.left = left;
        this.right = right;
    }

    public BigDecimal evaluate() {
        return BigDecimalMath.pow(
                right.evaluate().setScale(15, BigDecimal.ROUND_HALF_UP),
                inverse(left.evaluate().setScale(15, BigDecimal.ROUND_HALF_UP))
        );
    }

    public String toString() {
        return "(" + left.toString() + "root" + right.toString() + ")";
    }

    public List<InputToken> toInputTokens() {
        List<InputToken> resultTokens = left.toInputTokens();
        resultTokens.add(new InputToken("root", fromUnicode(0x02E3, 0x221A) + "("));
        resultTokens.addAll(right.toInputTokens());
        resultTokens.add(new InputToken(")", ")"));
        return resultTokens;
    }
}
