package fx50.nodes;

import fx50.API.InputToken;
import org.bychan.core.dynamic.UserParserCallback;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static fx50.CalculatorHelper.Tokens.*;
import static fx50.ParsingHelper.indent;
import static fx50.ParsingHelper.nextMustBeSeparator;
import static fx50.ParsingHelper.optionalColon;

/**
 * Condition Node
 */

//TODO must start in a new statement
public class ConditionNode implements CalculatorNode {
    private final CalculatorNode ifNode;
    private CalculatorNode thenNode;
    private final CalculatorNode elseNode;

    public ConditionNode(CalculatorNode left, UserParserCallback parser) {
        //Only accepts 1 expression
        ifNode = (CalculatorNode) parser.expression(left, 3);

        optionalColon(parser);

        parser.expectSingleLexeme(conditionThen.getKey());
        thenNode = (CalculatorNode) parser.expression(left);

        if (parser.nextIs(conditionElse.getKey())) {
            parser.expectSingleLexeme(conditionElse.getKey());
            elseNode = (CalculatorNode) parser.expression(left);
        } else
            elseNode = null;

        parser.expectSingleLexeme(conditionIfEnd.getKey());
        nextMustBeSeparator(parser, "IfEnd");
    }

    public BigDecimal evaluate() {
        if (ifNode.evaluate().compareTo(BigDecimal.ZERO) != 0) {
            return thenNode.evaluate();
        } else if (elseNode != null)
            return elseNode.evaluate();
        return new BigDecimal(0);
    }

    public String toString() {
        return "If " +
                ifNode.toString() +
                " Then\n" + indent(thenNode.toString()) +
                (elseNode != null ? "\nElse\n" + indent(elseNode.toString()) : "") +
                "\nIfEnd";
    }

    public List<InputToken> toInputTokens() {
        List<InputToken> resultTokens = new ArrayList<>(Collections.singletonList(new InputToken("If", "If ")));
        resultTokens.addAll(ifNode.toInputTokens());
        resultTokens.add(new InputToken("Then", "Then "));
        if (elseNode != null) {
            resultTokens.add(new InputToken("Else", "Else "));
            resultTokens.addAll(elseNode.toInputTokens());
        }
        return resultTokens;
    }
}
