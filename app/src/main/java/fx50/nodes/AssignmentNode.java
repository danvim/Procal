package fx50.nodes;

import fx50.API.InputToken;
import fx50.CalculatorHelper.VariableMap;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Assignment Node
 */
public class AssignmentNode extends NumberNode {
    private final CalculatorNode left;
    private final String variableName;

    public AssignmentNode(CalculatorNode left, VariableNode right) {
        this.left = left;
        this.variableName = right.getName();
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
        return new ArrayList<>(Collections.singletonList(new InputToken("->", "→")));
    }
}
