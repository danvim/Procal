package fx50.nodes;

import fx50.API.InputToken;
import org.bychan.core.basic.Lexeme;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static fx50.CalcMath.CalcMath.precision;

/**
 * Exponential Node
 */
public class ExponentialNode implements CalculatorNode {
    private final Lexeme lexeme;

    public ExponentialNode(Lexeme lexeme) {
        this.lexeme = lexeme;
    }

    public BigDecimal evaluate() {
        int rightResult = Integer.parseInt(lexeme.getText().substring(1));
        boolean isNeg = rightResult < 0;
        if (!isNeg)
            return new BigDecimal(10).pow(rightResult);
        else
            return BigDecimal.ONE.divide(new BigDecimal(10).pow(-rightResult), precision);
    }

    public String toString() {
        return lexeme.getText();
    }

    public List<InputToken> toInputTokens() {
        List<InputToken> resultTokens = new ArrayList<>(Collections.singletonList(new InputToken("E", "ᴇ")));
        String numberString = lexeme.getText().substring(1);
        //Loop for each digit
        for (int i = 0; i < numberString.length(); i++) {
            resultTokens.add(new InputToken(Character.toString(numberString.charAt(i)), Character.toString(numberString.charAt(i)), false));
        }
        return resultTokens;
    }
}
