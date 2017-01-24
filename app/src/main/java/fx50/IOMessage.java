package fx50;

import java.util.List;

import fx50.API.InputToken;

public class IOMessage {
    public String type;
    public String msg;
    public List<InputToken> inputExpression;
    public IOMessage(String type, String msg, List<InputToken> inputExpression) {
        this.type = type;
        this.msg = msg;
        this.inputExpression = inputExpression;
    }
}