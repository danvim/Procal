package fx50.nodes;

import java.math.BigDecimal;
import java.util.List;

import fx50.API.InputToken;
import fx50.CalculatorHelper.VariableMap;

/**
 * Assignment Node
 */
public class AssignmentNode extends NumberNode {
    private final CalculatorNode left;
    private final CalculatorNode right;
    private final String variableName;

    public AssignmentNode(CalculatorNode left, VariableNode right) {
        this.left = left;
        this.variableName = right.getName();
        this.right = right;
    }

    public BigDecimal evaluate() {
        BigDecimal leftResult = left.evaluate();
        VariableMap.setValue(variableName, leftResult);
        return leftResult;
    }

    public String toString() {
        return left.toString() + "->$" + variableName;
    }

    public List<InputToken> toInputTokens() {
        List<InputToken> resultTokens = left.toInputTokens();
        resultTokens.add(new InputToken("->", Character.toString((char) 0x2192)));
        resultTokens.addAll(right.toInputTokens());
        return resultTokens;
    }
}
