package fx50;

import android.os.Build;

import org.bychan.core.basic.ParseResult;
import org.bychan.core.dynamic.Language;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import dcheungaa.procal.InputHandler;
import fx50.nodes.CalculatorNode;

import static dcheungaa.procal.InputHandler.isRequestingInput;
import static fx50.CalcMath.CalcMath.sigfig;
import static fx50.ParsingHelper.sanitizeInput;

public class Fx50ParserCallable implements Callable<Fx50ParseResult> {
    private Language<CalculatorNode> l = CalculatorHelper.getFx50Language();
    private Fx50ParseResult parseResult;
    private IO io;
    private String input = "";
    public final List<String> inputHolder = new ArrayList<>();

    public Fx50ParserCallable() throws Exception {
        this.io = io;
        CalculatorHelper.setIO(io);
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getInput(IOMessage ioMessage) {
        System.out.println("Requesting input...");
        synchronized (inputHolder) {
            isRequestingInput = true;
                while (inputHolder.isEmpty()) {
                    try {
                        inputHolder.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace(System.out);
                    }
                }
            isRequestingInput = false;
            return inputHolder.remove(0);
        }
    }

    public void printOutput(String output) {
        //?, display
        System.out.println(output);
    }

    @Override
    public Fx50ParseResult call() throws UnsupportedEncodingException {
        input = input.replaceAll("\\s+", " ").trim();
        System.out.println("Fx50 parsing input: " + input);
        if (input.equals("")) {
            Fx50ParseResult result = new Fx50ParseResult(BigDecimal.ZERO, "0", null, null);
            InputHandler.doneEvaluating(result);
            return result;
        }
        try {
            ParseResult<CalculatorNode> pr = l.newLexParser().tryParse(sanitizeInput(input));
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
            System.out.println("&&&&&&&&");
            e.printStackTrace(System.out);
        }
        InputHandler.doneEvaluating(parseResult);
        return parseResult;
    }

    public Fx50ParseResult getParseResult() {
        return parseResult;
    }
}

