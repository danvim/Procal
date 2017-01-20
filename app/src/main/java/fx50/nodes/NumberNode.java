package fx50.nodes;

import fx50.API.InputToken;
import org.bychan.core.basic.Lexeme;
import org.bychan.core.dynamic.UserParserCallback;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Number Node
 */
public class NumberNode implements CalculatorNode {
    protected BigDecimal value;
    private String numberString;

    public NumberNode() {
        this.value = new BigDecimal(0);
        this.numberString = "0";
    }

    public NumberNode(BigDecimal value) {
        this.value = value;
        this.numberString = value.toPlainString();
    }

    public NumberNode(CalculatorNode left, UserParserCallback<CalculatorNode> parser, Lexeme lexeme) {
        this.value = new BigDecimal(lexeme.getText().equals(".") ? "0": lexeme.getText());
        this.numberString = lexeme.getText();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NumberNode numberNode = (NumberNode) o;

        return value.equals(numberNode.value);
    }

    public BigDecimal evaluate() {
        return value;
    }

    public String toString() {
        return value.toPlainString();
    }

    public List<InputToken> toInputTokens() {
        List<InputToken> resultTokens = new ArrayList<>();
        for (int i = 0; i < numberString.length(); i++) {
            resultTokens.add(new InputToken(Character.toString(numberString.charAt(i)), Character.toString(numberString.charAt(i)), false));
        }
        return resultTokens;
    }
}
