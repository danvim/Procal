package fx50.nodes;

import fx50.API.InputToken;
import fx50.CalcMath.CalcMath;

import java.math.BigDecimal;
import java.util.List;

/**
 * Division Node
 */
public class DivisionNode implements CalculatorNode {
    private final CalculatorNode left;
    private final CalculatorNode right;


    public DivisionNode(CalculatorNode left, CalculatorNode right) {
        this.left = left;
        this.right = right;
    }

    public BigDecimal evaluate() {
        return left.evaluate().divide(right.evaluate(), CalcMath.precision);
    }

    public String toString() {
        return "(" + left.toString() + "/" + right.toString() + ")";
    }

    public List<InputToken> toInputTokens() {
        List<InputToken> resultTokens = left.toInputTokens();
        resultTokens.add(new InputToken("/", "÷"));
        resultTokens.addAll(right.toInputTokens());
        return resultTokens;
    }
}
