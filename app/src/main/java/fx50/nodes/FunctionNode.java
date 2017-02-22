package fx50.nodes;

import org.bychan.core.basic.Lexeme;
import org.bychan.core.dynamic.UserParserCallback;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fx50.API.InputToken;
import fx50.CalcMath.Fn;
import java8.util.stream.Collectors;
import java8.util.stream.StreamSupport;

import static dcheungaa.procal.Tokens.fromUnicode;
import static fx50.CalculatorHelper.Tokens.comma;
import static fx50.CalculatorHelper.Tokens.lparen;
import static fx50.CalculatorHelper.Tokens.rparen;
import static fx50.ParsingHelper.nextIsStatementEnd;

/**
 * Function Node
 */
public class FunctionNode implements CalculatorNode {
    private ArrayList<CalculatorNode> argNodes = new ArrayList<>();
    private final String functionName;
    private Method method;
    private ArrayList<BigDecimal> args = new ArrayList<>();

    public FunctionNode(CalculatorNode left, UserParserCallback<CalculatorNode> parser, Lexeme lexeme) {
        argNodes.clear();
        parser.expectSingleLexeme(lparen.getKey());
        argNodes.add(parser.expression(left, 5));
        while(parser.nextIs(comma.getKey())) {
            //Has next argument
            parser.expectSingleLexeme(comma.getKey());
            argNodes.add(parser.expression(left, 5));
        }
        if (!nextIsStatementEnd(parser))
            parser.expectSingleLexeme(rparen.getKey());
        this.functionName = lexeme.getText();
        getFunction(parser);
    }

    public FunctionNode(CalculatorNode input, UserParserCallback parser, String functionName) {
        argNodes.add(input);
        this.functionName = functionName;
        getFunction(parser);
    }

    private void getFunction(UserParserCallback parser) {
        try {
            method = Fn.class.getMethod(functionName, ArrayList.class);
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
        BigDecimal result;

        args.clear();

        for(CalculatorNode argNode: argNodes) {
            args.add(argNode.evaluate());
        }

        try {
            result = (BigDecimal) method.invoke(this, args);
        } catch (IllegalArgumentException e) {throw new RuntimeException("Runtime Error: IllegalArgumentException");}
        catch (IllegalAccessException e) {throw new RuntimeException("Runtime Error: IllegalAccessException");}
        catch (InvocationTargetException e) {throw new ArithmeticException("Math Error: '" + e.getCause().getMessage() + "' @ function '" + functionName + "'.");}

        return result;
    }

    public String toString() {
        return  functionName + "(" + StreamSupport.stream(argNodes).map(Object::toString).collect(Collectors.joining(",")) + ")";
    }

    public List<InputToken> toInputTokens() {
        String functionDisplay = "";
        switch(functionName) {
            case "sqrt":
                functionDisplay = fromUnicode(0x00B2, 0x221A);
                break;
            case "cbrt":
                functionDisplay = fromUnicode(0x00B3, 0x221A);
                break;
            case "asin":
                functionDisplay = "sin" + fromUnicode(0x00BA);
                break;
            case "acos":
                functionDisplay = "cos" + fromUnicode(0x00BA);
                break;
            case "atan":
                functionDisplay = "tan" + fromUnicode(0x00BA);
                break;
            case "asinh":
                functionDisplay = "sinh" + fromUnicode(0x00BA);
                break;
            case "acosh":
                functionDisplay = "cosh" + fromUnicode(0x00BA);
                break;
            case "atanh":
                functionDisplay = "tanh" + fromUnicode(0x00BA);
                break;
            default:
                functionDisplay = functionName;
        }
        List<InputToken> resultTokens = new ArrayList<>(Collections.singletonList(new InputToken(functionName + "(", functionDisplay + "(")));
        for(int i = 0; i < argNodes.size(); i++) {
            resultTokens.addAll(argNodes.get(i).toInputTokens());
            if (!(i == argNodes.size() - 1))
                resultTokens.add(new InputToken(",", ","));
        }
        resultTokens.add(new InputToken(")", ")"));
        return resultTokens;
    }
}
