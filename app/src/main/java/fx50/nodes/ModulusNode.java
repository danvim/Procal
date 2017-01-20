package fx50.nodes;

import fx50.API.InputToken;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Modulus Node
 */
public class ModulusNode implements CalculatorNode {
    private final CalculatorNode left;
    private final CalculatorNode right;

    public ModulusNode(CalculatorNode left, CalculatorNode right) {
        this.left = left;
        this.right = right;
    }

    public BigDecimal evaluate() {
        return left.evaluate().remainder(right.evaluate());
    }

    public String toString() {
        return "(" + left.toString() + "mod" + right.toString() + ")";
    }

    public List<InputToken> toInputTokens() {
        List<InputToken> resultTokens = left.toInputTokens();
        resultTokens.add(new InputToken("mod", "mod"));
        resultTokens.addAll(right.toInputTokens());
        return resultTokens;
    }
}
