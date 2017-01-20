package fx50.nodes;

import fx50.API.InputToken;
import fx50.CalculatorHelper;
import fx50.Fx50ParseResult;
import fx50.Fx50Parser;

import java.io.InputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import static fx50.CalculatorHelper.io;

/**
 * Input Number Node
 */
public class InputPromptNode implements CalculatorNode {
    private final String variableName;

    public InputPromptNode(String variableName) {
        this.variableName = variableName;
    }

    public BigDecimal evaluate() {
        io.printOutput(variableName + "?:");
        String input = io.getInput();
        try {
            Fx50Parser parser = new Fx50Parser(io);
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
