package fx50.nodes;

import fx50.API.InputToken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Answer Node
 */
public class AnswerNode extends VariableNode{

    public AnswerNode() {
        super("~Ans");
    }

    public String toString() {
        return "Ans";
    }

    public List<InputToken> toInputTokens() {
        return new ArrayList<>(Collections.singletonList(new InputToken("Ans", "Ans")));
    }
}
