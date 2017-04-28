package fx50.nodes;

import org.bychan.core.basic.Lexeme;
import org.bychan.core.dynamic.UserParserCallback;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fx50.API.InputToken;

import static fx50.CalculatorHelper.Tokens.colon;
import static fx50.CalculatorHelper.Tokens.loopWhileEnd;
import static fx50.ParsingHelper.indent;
import static fx50.ParsingHelper.nextMustBeSeparator;

/**
 * While Loop Node
 */

//TODO must start in a new statement
public class WhileLoopNode implements CalculatorNode {
    private final CalculatorNode conditionNode;
    private CalculatorNode doNode;

    public WhileLoopNode(CalculatorNode left, UserParserCallback<CalculatorNode> parser, Lexeme<CalculatorNode> lexeme) {
        conditionNode = parser.expression(left, 3);

        parser.expectSingleLexeme(colon.getKey());
        doNode = parser.expression(left);

        parser.expectSingleLexeme(loopWhileEnd.getKey());
        nextMustBeSeparator(parser, "WhileEnd");
    }

    public BigDecimal evaluate() {
        BigDecimal doResult = new BigDecimal(0);
        try {
            BigDecimal conditionBigDecimal;
            while ((conditionBigDecimal = conditionNode.evaluate().setScale(15, BigDecimal.ROUND_DOWN)).compareTo(BigDecimal.ZERO) != 0) {
                System.out.println("Processing while loop. Condition is: " + conditionBigDecimal.toString());
                doResult = doNode.evaluate();
            }
        } catch (RuntimeException e) {
            if (!e.getMessage().equals("Breaking"))
                throw e;
        }
        return doResult;
    }

    public String toString() {
        return "While " +
                conditionNode.toString() +
                ":\n" + indent(doNode.toString()) +
                "\nWhileEnd";
    }

    public List<InputToken> toInputTokens() {
        List<InputToken> resultTokens = new ArrayList<>(Collections.singletonList(new InputToken("While", "While ")));
        resultTokens.addAll(conditionNode.toInputTokens());
        resultTokens.add(new InputToken(":", ":"));
        resultTokens.addAll(doNode.toInputTokens());
        resultTokens.add(new InputToken("WhileEnd", "WhileEnd"));
        return resultTokens;
    }
}
