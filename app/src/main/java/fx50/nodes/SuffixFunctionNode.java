package fx50.nodes;

import org.bychan.core.basic.Lexeme;
import org.bychan.core.dynamic.UserParserCallback;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import fx50.API.InputToken;
import fx50.CalcMath.SuffixFn;

/**
 * Suffix Function Node
 */
public class SuffixFunctionNode implements CalculatorNode {
    private final CalculatorNode left;
    private final String functionName;
    private Method method;
    private ArrayList<BigDecimal> args = new ArrayList<>();

    public SuffixFunctionNode(CalculatorNode left, UserParserCallback parser, Lexeme lexeme) {
        this(left, parser, lexeme.getText());
    }

    public SuffixFunctionNode(CalculatorNode input, UserParserCallback parser, String functionName) {
        this.left = input;
        this.functionName = functionName;
        try {
            method = SuffixFn.class.getMethod(functionName, ArrayList.class);
        } catch (SecurityException e) {parser.abort("Security Exception");}
        catch (NoSuchMethodException e) {parser.abort("No Such Method: " + functionName);}
    }

    public String getFunctionName() {
        return functionName;
    }

    public ArrayList<BigDecimal> getArgs() {
        return args;
    }

    public BigDecimal evaluate() {
        args.clear();

        BigDecimal result;

        BigDecimal leftResult = left.evaluate();

        args.add(leftResult);

        try {
            result = (BigDecimal) method.invoke(this, args);
        } catch (IllegalArgumentException e) {throw new ArithmeticException("Runtime Error: IllegalArgumentException");}
        catch (IllegalAccessException e) {throw new RuntimeException("Runtime Error: IllegalAccessException");}
        catch (InvocationTargetException e) {throw new ArithmeticException("Runtime Error: Math Error");}

        return result;
    }

    public String toString() {
        return "((" + left + ")" + functionName + ")";
    }

    public List<InputToken> toInputTokens() {
        List<InputToken> resultTokens = left.toInputTokens();
        InputToken suffixFunctionToken;
        switch (functionName) {
            case "squared":
                suffixFunctionToken = new InputToken("squared", Character.toString((char) 0x00B2));
                break;
            case "cubed":
                suffixFunctionToken = new InputToken("cubed", Character.toString((char) 0x00B3));
                break;
            case "inverse":
                suffixFunctionToken = new InputToken("inverse", Character.toString((char) 0x00BA));
                break;
            default:
                suffixFunctionToken = new InputToken(functionName, functionName);
        }
        resultTokens.add(suffixFunctionToken);
        return resultTokens;
    }
}
