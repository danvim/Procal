package fx50.nodes;

import org.bychan.core.basic.Lexeme;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fx50.API.Color;
import fx50.API.InputToken;
import fx50.CalculatorHelper.VariableMap;

import static fx50.API.InputTokenHelper.getGreekUnicodeCharacterFromName;

/**
 * Variable Node
 */
public class VariableNode extends NumberNode {
    private final String variableName;

    public VariableNode(Lexeme lexeme) {
        this.variableName = lexeme.getText().substring(1);
    }

    public VariableNode(String name) {
        this.variableName = name;
    }

    public BigDecimal evaluate() {
        return VariableMap.getValue(variableName);
    }

    public String getName() {
        return variableName;
    }

    public String toString() {
        return "$"+variableName;
    }

    public List<InputToken> toInputTokens() {
        //TODO fix Greek and Color
        String name = getGreekUnicodeCharacterFromName(variableName);
        Color color = Color.LATIN_VARIABLE;
        if (!name.equals(variableName))
            color = Color.GREEK_VARIABLE;
        if (name.matches("A|B|C|D|X|Y|M"))
            color = Color.SPECIAL_VARIABLE;
        return new ArrayList<>(Collections.singletonList(new InputToken("$" + variableName, getGreekUnicodeCharacterFromName(variableName), color)));
    }
}
