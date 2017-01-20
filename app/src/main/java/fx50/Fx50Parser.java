package fx50;

import fx50.nodes.CalculatorNode;
import org.bychan.core.basic.ParseResult;
import org.bychan.core.dynamic.Language;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;

import static fx50.CalcMath.CalcMath.sigfig;
import static fx50.ParsingHelper.sanitizeInput;

public class Fx50Parser {
    private Language<CalculatorNode> l = CalculatorHelper.getFx50Language();
    private Fx50ParseResult parseResult;
    private IO io;

    public Fx50Parser(IO io) throws Exception {
        this.io = io;
        CalculatorHelper.setIO(io);
    }

    public Fx50ParseResult parse(String line) throws UnsupportedEncodingException {
        line = line.replaceAll("\\s+", " ").trim();
        if (line.equals("")) {
            parseResult = new Fx50ParseResult(BigDecimal.ZERO, "0", null, null);
        }
        try {
            ParseResult<CalculatorNode> pr = l.newLexParser().tryParse(sanitizeInput(line));
            BigDecimal bigDecimalResult = pr.getRootNode().evaluate();
            parseResult = new Fx50ParseResult(pr.getRootNode().evaluate(), sigfig(bigDecimalResult, 10).toString(), null, pr.getRootNode().toInputTokens());
        } catch (Exception e) {
            parseResult = new Fx50ParseResult(null, null, e.getMessage(), null);
        }
        return parseResult;
    }

    public Fx50ParseResult getParseResult() {
        return parseResult;
    }
}

