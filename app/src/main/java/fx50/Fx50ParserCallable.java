package fx50;

import android.os.Build;
import android.widget.TextView;

import org.bychan.core.basic.ParseResult;
import org.bychan.core.dynamic.Language;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import dcheungaa.procal.InputHandler;
import dcheungaa.procal.MainActivity;
import dcheungaa.procal.R;
import fx50.nodes.CalculatorNode;

import static dcheungaa.procal.InputHandler.isRequestingDisplay;
import static dcheungaa.procal.InputHandler.isRequestingInput;
import static fx50.CalcMath.CalcMath.sigfig;
import static fx50.ParsingHelper.sanitizeInput;

public class Fx50ParserCallable implements Callable<Fx50ParseResult> {
    private Language<CalculatorNode> l = CalculatorHelper.getFx50Language();
    private Fx50ParseResult parseResult;
    private IO io;
    private String input = "";
    public final List<String> inputHolder = new ArrayList<>();
    public final List<IOMessage> outputHolder = new ArrayList<>();

    public Fx50ParserCallable() throws Exception {
        this.io = io;
        CalculatorHelper.setIO(io);
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getInput(IOMessage ioMessage) {
        System.out.println("Requesting input...");
        TextView inquiryDisplay = (TextView) MainActivity.views.get("inquiryDisplay");
        TextView resultDisplay = (TextView) MainActivity.views.get("resultDisplay");
        synchronized (inputHolder) {
            isRequestingInput = true;
            while (inputHolder.isEmpty()) {
                try {
                    MainActivity.mainActivities.get(0).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            inquiryDisplay.setText(MainActivity.context.getString(R.string.inquiry, ioMessage.msg));
                            resultDisplay.setText(formatResult(CalculatorHelper.VariableMap.getValue(ioMessage.msg)));
                        }
                    });
                    inputHolder.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace(System.out);
                }
            }
            System.out.println("The input holder queue currently has length: " + inputHolder.size() + " in thread: " + Thread.currentThread().getName());
            MainActivity.mainActivities.get(0).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    inquiryDisplay.setText("");
                }
            });
            isRequestingInput = false;
            String input = inputHolder.remove(0);
            if (input.trim().equals(""))
                input = "$" + ioMessage.msg;
            return input;
        }
    }

    public void printOutput(IOMessage ioMessage) {
        //display
        System.out.println("Requesting output...");
        synchronized (outputHolder) {
            isRequestingDisplay = true;
            outputHolder.add(ioMessage);
            while (!outputHolder.isEmpty()) {
                try {
                    MainActivity.mainActivities.get(0).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            InputHandler.displayOutput();
                        }
                    });
                    outputHolder.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        isRequestingDisplay = false;
        System.out.println("Continuing evaluation...");
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

            parseResult = new Fx50ParseResult(bigDecimalResult, formatResult(bigDecimalResult), null, pr.getRootNode().toInputTokens());
        } catch (Exception e) {
            parseResult = new Fx50ParseResult(null, null, e.getMessage(), null);
            System.out.println("&&&&&&&&");
            e.printStackTrace(System.out);
        }
        InputHandler.doneEvaluating(parseResult);
        return parseResult;
    }

    public static String formatResult(BigDecimal bigDecimal) {
        String stringResult;
        if (Build.VERSION.SDK_INT > 25)
            stringResult = sigfig(bigDecimal, 10).toString();
        else {
            double doubleVal = sigfig(bigDecimal, 10).doubleValue();
            if (doubleVal == (long) doubleVal)
                stringResult = Long.toString((long) doubleVal);
            else
                stringResult = Double.toString(sigfig(bigDecimal, 10).doubleValue());
        }
        return stringResult;
    }

    public Fx50ParseResult getParseResult() {
        return parseResult;
    }
}

