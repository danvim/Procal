package fx50;

import fx50.API.InputToken;

import java.math.BigDecimal;
import java.util.List;

/**
 * Parsed Result
 */
public class Fx50ParseResult {
    private BigDecimal bigDecimalResult;
    private String stringResult;
    private String errorString;
    private List<InputToken> inputExpression;

    Fx50ParseResult(BigDecimal bigDecimalResult, String stringResult, String errorString, List<InputToken> inputExpression) {
        this.bigDecimalResult = bigDecimalResult;
        this.stringResult = stringResult;
        this.errorString = errorString;
        this.inputExpression = inputExpression;
    }

    public String getStringResult() {
        return stringResult;
    }

    public BigDecimal getBigDecimalResult() {
        return bigDecimalResult;
    }

    public String getErrorString() {
        return errorString;
    }

    public List<InputToken> getInputExpression() {
        return  inputExpression;
    }
}
