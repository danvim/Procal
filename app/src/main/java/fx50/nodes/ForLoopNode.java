package fx50.nodes;

import fx50.API.InputToken;
import org.bychan.core.basic.Lexeme;
import org.bychan.core.dynamic.UserParserCallback;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static fx50.CalculatorHelper.VariableMap;
import static fx50.CalculatorHelper.Tokens.*;
import static fx50.ParsingHelper.indent;
import static fx50.ParsingHelper.nextMustBeSeparator;

/**
 * While Loop Node
 */

//TODO must start in a new statement
public class ForLoopNode implements CalculatorNode {
    private final CalculatorNode initNode;
    private final CalculatorNode toNode;
    private VariableNode controlVariable;
    private CalculatorNode doNode;
    private CalculatorNode stepNode;

    public ForLoopNode(CalculatorNode left, UserParserCallback<CalculatorNode> parser, Lexeme<CalculatorNode> lexeme) {
        //Expression before set
        initNode = parser.expression(left, 4);
        parser.expectSingleLexeme(set.getKey());
        controlVariable = (VariableNode) parser.parseSingleToken(left, variable.getKey());
        parser.expectSingleLexeme(loopTo.getKey());

        toNode = parser.expression(left, 3);

        if (parser.nextIs(loopStep.getKey())) {
            parser.expectSingleLexeme(loopStep.getKey());
            stepNode = parser.expression(left, 3);
        }

        parser.expectSingleLexeme(colon.getKey());

        doNode = parser.expression(left);

        parser.expectSingleLexeme(loopNext.getKey());
        nextMustBeSeparator(parser, "Next");
    }

    public BigDecimal evaluate() {
        BigDecimal toResult = toNode.evaluate();
        String controlVariableName = controlVariable.getName();
        VariableMap.setValue(controlVariableName, initNode.evaluate());
        BigDecimal doResult = new BigDecimal(0);
        BigDecimal controlVariableValue;
        try {
            while ((controlVariableValue = VariableMap.getValue(controlVariableName)).compareTo(toResult) < 1) {
                doResult = doNode.evaluate();
                VariableMap.setValue(controlVariableName, controlVariableValue.add(BigDecimal.ONE));
            }
        } catch (RuntimeException e) {
            if (!e.getMessage().equals("Breaking"))
                throw e;
        }
        return doResult;
    }

    public String toString() {
        return "For " + initNode.toString() + " -> " + controlVariable.toString() +
                " To "+ toNode.toString() +
                (stepNode == null ? "" : " Step " + stepNode.toString()) + ":\n" +
                indent(doNode.toString()) +
                "\nNext";
    }

    public List<InputToken> toInputTokens() {
        List<InputToken> resultTokens = new ArrayList<>(Collections.singletonList(new InputToken("For", "For ")));
        resultTokens.addAll(initNode.toInputTokens());
        resultTokens.add(new InputToken("->", "→"));
        resultTokens.addAll(controlVariable.toInputTokens());
        resultTokens.add(new InputToken("To", "To "));
        resultTokens.addAll(toNode.toInputTokens());
        if (stepNode != null) {
            resultTokens.add(new InputToken("Step", "Step "));
            resultTokens.addAll(stepNode.toInputTokens());
        }
        resultTokens.add(new InputToken(":", ":"));
        resultTokens.addAll(doNode.toInputTokens());
        resultTokens.add(new InputToken("Next", "Next"));
        return resultTokens;
    }
}
