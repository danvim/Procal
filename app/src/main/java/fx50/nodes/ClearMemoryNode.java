package fx50.nodes;

import org.bychan.core.basic.Lexeme;
import org.bychan.core.dynamic.UserParserCallback;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dcheungaa.procal.MainActivity;
import fx50.API.Color;
import fx50.API.InputToken;
import fx50.CalculatorHelper;
import fx50.IOMessage;

import static fx50.ParsingHelper.nextMustBeSeparator;

/**
 * Clear Memory Node
 */
public class ClearMemoryNode implements CalculatorNode {

    public ClearMemoryNode(CalculatorNode left, UserParserCallback<CalculatorNode> parser, Lexeme lexeme) {
        nextMustBeSeparator(parser, "clearMemory");
    }

    public BigDecimal evaluate() {
        CalculatorHelper.VariableMap.clearMemory();
        MainActivity.fx50Parser.printOutput(new IOMessage("display", "", Collections.singletonList(new InputToken("Memory Cleared!", "Memory Cleared!"))));
        return new BigDecimal(0);
    }

    public String toString() {
        return "ClrMemory";
    }

    public List<InputToken> toInputTokens() {
        return new ArrayList<>(Collections.singletonList(new InputToken("ClrMemory", "ClrMemory", Color.COMMAND)));
    }
}
