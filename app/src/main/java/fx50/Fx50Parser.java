package fx50;

import android.os.Build;

import org.bychan.core.basic.ParseResult;
import org.bychan.core.dynamic.Language;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;

import fx50.nodes.CalculatorNode;

import static fx50.CalcMath.CalcMath.sigfig;
import static fx50.ParsingHelper.sanitizeInput;

public class Fx50Parser {
    private Language<CalculatorNode> l = CalculatorHelper.getFx50Language();
    private Fx50ParseResult parseResult;

    public Fx50Parser(IO io) throws Exception {
        CalculatorHelper.setIO(io);
    }

    public Fx50ParseResult parse(String line) throws UnsupportedEncodingException {
        line = line.replaceAll("\\s+", " ").trim();
        System.out.println("Fx50 parsing input: " + line);
        if (line.equals("")) {
            return new Fx50ParseResult(BigDecimal.ZERO, "0", null, null);
        }
        try {
            ParseResult<CalculatorNode> pr = l.newLexParser().tryParse(sanitizeInput(line));
            BigDecimal bigDecimalResult = pr.getRootNode().evaluate();
            System.out.println(sigfig(bigDecimalResult, 10).stripTrailingZeros().toPlainString());
            String stringResult = "";
            if (Build.VERSION.SDK_INT > 25)
                stringResult = sigfig(bigDecimalResult, 10).toString();
            else {
                double doubleVal = sigfig(bigDecimalResult, 10).doubleValue();
                if (doubleVal == (long) doubleVal)
                    stringResult = Long.toString((long) doubleVal);
                else
                    stringResult = Double.toString(sigfig(bigDecimalResult, 10).doubleValue());
            }
            parseResult = new Fx50ParseResult(bigDecimalResult, stringResult, null, pr.getRootNode().toInputTokens());
        } catch (Exception e) {
            parseResult = new Fx50ParseResult(null, null, e.getMessage(), null);
        }
        return parseResult;
    }

    public Fx50ParseResult getParseResult() {
        return parseResult;
    }
}

