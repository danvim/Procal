package fx50.nodes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dcheungaa.procal.MainActivity;
import fx50.API.InputToken;
import fx50.CalculatorHelper;
import fx50.Fx50ParseResult;
import fx50.Fx50Parser;
import fx50.IOMessage;

/**
 * Input Number Node
 */
public class InputPromptNode implements CalculatorNode {
    private final String variableName;

    public InputPromptNode(String variableName) {
        this.variableName = variableName;
    }

    public BigDecimal evaluate() {
        String input = MainActivity.fx50Parser.getInput(new IOMessage("inputPrompt", variableName, null));
        try {
            Fx50Parser parser = new Fx50Parser(CalculatorHelper.io);
            Fx50ParseResult parseResult = parser.parse(input);
            if (parseResult.getErrorString() != null)
                throw new Exception(parseResult.getErrorString());
            return new BigDecimal(parseResult.getStringResult());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public String toString() {
        return "?";
    }

    public List<InputToken> toInputTokens() {
        return new ArrayList<>(Collections.singletonList(new InputToken("?", "?")));
    }
}
