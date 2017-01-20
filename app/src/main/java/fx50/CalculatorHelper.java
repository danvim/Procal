package fx50;

import fx50.nodes.*;
import org.bychan.core.dynamic.Language;
import org.bychan.core.dynamic.LanguageBuilder;
import org.bychan.core.dynamic.TokenDefinitionBuilder;

import java.io.InputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * CalculatorHelper
 */
public class CalculatorHelper {

    public static IO io = new IO();
    private static Language<CalculatorNode> l;

    static void setIO(IO io) {
        CalculatorHelper.io = io;
    }

    public static class VariableMap {
        private static Map<String, BigDecimal> storage = new HashMap<>();
        public static BigDecimal getValue(String name) {
            if (storage.containsKey(name))
                return storage.get(name);
            else
                return setValue(name, new BigDecimal(0));
        }

        public static BigDecimal setValue(String name, BigDecimal value) {
            storage.put(name, value);
            return value;
        }

        public static void clearMemory() {
            storage.clear();
        }
    }

    //public static Map<String, CalculatorNode> labels = new HashMap<>();

    static LanguageBuilder<CalculatorNode> b = new LanguageBuilder<>("Fx-50F ULTRA");

    public static class Tokens {
        /*
        No way of implementing for now

        public static TokenDefinitionBuilder<CalculatorNode> jumpGoto = b.newToken()
                .named("goto").matchesPattern("Goto *[\\dA-Za-z]*")
                .nud(GotoNode::new);

        public static TokenDefinitionBuilder<CalculatorNode> jumpLabel = b.newToken()
                .named("lbl").matchesPattern("Lbl *[\\dA-Za-z]*")
                .nud(LabelNode::new);*/

        public static TokenDefinitionBuilder<CalculatorNode> loopFor = b.newToken()
                .named("for").matchesString("For")
                .nud(ForLoopNode::new);

        public static TokenDefinitionBuilder<CalculatorNode> loopTo = b.newToken()
                .named("to").matchesString("To");

        public static TokenDefinitionBuilder<CalculatorNode> loopStep = b.newToken()
                .named("step").matchesString("Step");

        public static TokenDefinitionBuilder<CalculatorNode> loopNext = b.newToken()
                .named("next").matchesString("Next");

        public static TokenDefinitionBuilder<CalculatorNode> loopBreak = b.newToken()
                .named("break").matchesString("Break")
                .nud(BreakNode::new);

        public static TokenDefinitionBuilder<CalculatorNode> MPlus = b.newToken()
                .named("M+").matchesString("M+")
                .led(MPlusNode::new);

        public static TokenDefinitionBuilder<CalculatorNode> MMinus = b.newToken()
                .named("M-").matchesString("M-")
                .led(MMinusNode::new);

        public static TokenDefinitionBuilder<CalculatorNode> loopWhile = b.newToken()
                .named("while").matchesString("While")
                .nud(WhileLoopNode::new);

        public static TokenDefinitionBuilder<CalculatorNode> loopWhileEnd = b.newToken()
                .named("whileEnd").matchesString("WhileEnd");

        public static TokenDefinitionBuilder<CalculatorNode> answer = b.newToken()
                .named("answer").matchesString("Ans")
                .nud((left, parser, lexeme) -> new AnswerNode());

        public static TokenDefinitionBuilder<CalculatorNode> shorthandIf = b.newToken()
                .named("shorthandIf").matchesString("=>")
                .led((left, parser, lexeme) -> new ShorthandIfNode(left, parser));

        public static TokenDefinitionBuilder<CalculatorNode> power = b.newToken()
                .named("power").matchesString("^")
                .led((left, parser, lexeme) -> new PowerNode(left, parser.expression(left)));

        public static TokenDefinitionBuilder<CalculatorNode> root = b.newToken()
                .named("root").matchesString("root")
                .led((left, parser, lexeme) -> new RootNode(left, parser.expression(left)));

        public static TokenDefinitionBuilder<CalculatorNode> colon = b.newToken()
                .named("colon").matchesString(":")
                .led((left, parser, lexeme) -> new StatementNode(left, parser));

        public static TokenDefinitionBuilder<CalculatorNode> negate = b.newToken()
                .named("negate").matchesString("(-)")
                .nud((left, parser, lexeme) -> new NegationNode(parser.expression(left)));

        public static TokenDefinitionBuilder<CalculatorNode> rparen = b.newToken()
                .named("rparen").matchesString(")");

        public static TokenDefinitionBuilder<CalculatorNode> lparen = b.newToken()
                .named("lparen").matchesString("(")
                .nud((left, parser, lexeme) -> new ParenthesisNode(left, parser));

        public static TokenDefinitionBuilder<CalculatorNode> permutation = b.newToken()
                .named("permutation").matchesString("P")
                .led((left, parser, lexeme) -> new PermutationNode(left, parser.expression(left)));

        public static TokenDefinitionBuilder<CalculatorNode> combination = b.newToken()
                .named("combination").matchesString("C")
                .led((left, parser, lexeme) -> new CombinationNode(left, parser.expression(left)));

        public static TokenDefinitionBuilder<CalculatorNode> randomNumber = b.newToken()
                .named("randomNumber").matchesString("Ran#")
                .nud(RandomNumberNode::new);

        public static TokenDefinitionBuilder<CalculatorNode> variable = b.newToken()
                .named("variable").matchesPattern("\\$[A-Za-z_][A-Za-z_0-9]*")
                .nud((left, parser, lexeme) -> new VariableNode(lexeme));

        public static TokenDefinitionBuilder<CalculatorNode> constant = b.newToken()
                .named("constant").matchesPattern("&[A-Za-z_][A-Za-z_0-9]*")
                .nud(ConstantNode::new);

        public static TokenDefinitionBuilder<CalculatorNode> number = b.newToken()
                .named("number").matchesPattern("\\d+\\.?\\d+|\\d+\\.|\\.\\d+|\\.|\\d+")
                .nud(NumberNode::new);

        public static TokenDefinitionBuilder<CalculatorNode> percent = b.newToken()
                .named("percent").matchesString("%")
                .led(PercentNode::new);

        public static TokenDefinitionBuilder<CalculatorNode> set = b.newToken()
                .named("set").matchesString("->")
                .led((left, parser, lexeme) -> {
                    if (!parser.nextIs(variable.getKey()))
                        parser.abort("Invalid assignment RHS. Expected a variable name");
                    return new AssignmentNode(left, (VariableNode) parser.expression(left));
                });

        public static TokenDefinitionBuilder<CalculatorNode> input = b.newToken()
                .named("input").matchesString("?")
                .nud((left, parser, lexeme) -> {
                    parser.expectSingleLexeme(set.getKey());
                    if (!parser.nextIs(variable.getKey()))
                        parser.abort("Invalid assignment RHS. Expected a variable name");
                    VariableNode variableNode = (VariableNode) parser.expression(left);
                    return new AssignmentNode(new InputPromptNode(variableNode.getName()), variableNode);
                });

        public static TokenDefinitionBuilder<CalculatorNode> display = b.newToken()
                .named("display").matchesString("display")
                .led((left, parser, lexeme) -> {
                    return new DisplayNode(left, parser);
                });

        public static TokenDefinitionBuilder<CalculatorNode> factorial = b.newToken()
                .named("factorial").matchesString("!")
                .led((left, parser, lexeme) -> new FactorialNode(left));

        public static TokenDefinitionBuilder<CalculatorNode> modulo = b.newToken()
                .named("modulo").matchesString("mod")
                .nud((left, parser, lexeme) -> parser.expression(left))
                .led((left, parser, lexeme) -> new ModulusNode(left, parser.expression(left)));

        public static TokenDefinitionBuilder<CalculatorNode> multiply = b.newToken()
                .named("multiply").matchesString("*")
                .led((left, parser, lexeme) -> new MultiplicationNode(left, parser.expression(left)));

        public static TokenDefinitionBuilder<CalculatorNode> divide = b.newToken()
                .named("divide").matchesString("/")
                .led((left, parser, lexeme) -> new DivisionNode(left, parser.expression(left)));

        public static TokenDefinitionBuilder<CalculatorNode> minus = b.newToken()
                .named("minus").matchesString("-")
                .nud((left, parser, lexeme) -> new NegationNode(parser.expression(left)))
                .led((left, parser, lexeme) -> new SubtractionNode(left, parser.expression(left)));

        public static TokenDefinitionBuilder<CalculatorNode> plus = b.newToken()
                .named("plus").matchesString("+")
                .nud((left, parser, lexeme) -> parser.expression(left))
                .led((left, parser, lexeme) -> new AdditionNode(left, parser.expression(left)));

        public static TokenDefinitionBuilder<CalculatorNode> exponential = b.newToken()
                .named("exponential").matchesPattern("E-*\\d\\d?(?!\\d)")
                .nud((left, parser, lexeme) -> new ExponentialNode(lexeme));

        public static TokenDefinitionBuilder<CalculatorNode> function = b.newToken()
                .named("function").matchesPattern("[A-Za-z_]\\w+(?=\\()")
                .nud(FunctionNode::new);

        public static TokenDefinitionBuilder<CalculatorNode> suffixFunction = b.newToken()
                .named("suffixFunction").matchesPattern("[A-Za-z_]\\w+")
                .led(SuffixFunctionNode::new);

        public static TokenDefinitionBuilder<CalculatorNode> booleanNotEqual = b.newToken()
                .named("booleanNotEqual").matchesString("!=")
                .led((left, parser, lexeme) -> new BooleanNotEqualNode(left, parser.expression(left)));

        public static TokenDefinitionBuilder<CalculatorNode> booleanEqual = b.newToken()
                .named("booleanEqual").matchesString("==")
                .led((left, parser, lexeme) -> new BooleanEqualNode(left, parser.expression(left)));

        public static TokenDefinitionBuilder<CalculatorNode> booleanLesserThanOrEquals = b.newToken()
                .named("booleanLesserThanOrEquals").matchesString("<=")
                .led((left, parser, lexeme) -> new BooleanLesserThanOrEqualNode(left, parser.expression(left)));

        public static TokenDefinitionBuilder<CalculatorNode> booleanLesserThan = b.newToken()
                .named("booleanLesserThan").matchesString("<")
                .led((left, parser, lexeme) -> new BooleanLesserThanNode(left, parser.expression(left)));

        public static TokenDefinitionBuilder<CalculatorNode> booleanGreaterThanOrEquals = b.newToken()
                .named("booleanGreaterThanOrEquals").matchesString(">=")
                .led((left, parser, lexeme) -> new BooleanGreaterThanOrEqualNode(left, parser.expression(left)));

        public static TokenDefinitionBuilder<CalculatorNode> booleanGreaterThan = b.newToken()
                .named("booleanGreaterThan").matchesString(">")
                .led((left, parser, lexeme) -> new BooleanGreaterThanNode(left, parser.expression(left)));

        public static TokenDefinitionBuilder<CalculatorNode> booleanNot = b.newToken()
                .named("not").matchesString("not")
                .nud(BooleanNotNode::new);

        public static TokenDefinitionBuilder<CalculatorNode> booleanAnd = b.newToken()
                .named("and").matchesString("and")
                .led((left, parser, lexeme) -> new BooleanAndNode(left, parser.expression(left)));

        public static TokenDefinitionBuilder<CalculatorNode> booleanOr = b.newToken()
                .named("or").matchesString("or")
                .led((left, parser, lexeme) -> new BooleanOrNode(left, parser.expression(left)));

        public static TokenDefinitionBuilder<CalculatorNode> booleanXor = b.newToken()
                .named("xor").matchesString("xor")
                .led((left, parser, lexeme) -> new BooleanXorNode(left, parser.expression(left)));

        public static TokenDefinitionBuilder<CalculatorNode> booleanXnor = b.newToken()
                .named("xnor").matchesString("xnor")
                .led((left, parser, lexeme) -> new BooleanXnorNode(left, parser.expression(left)));

        public static TokenDefinitionBuilder<CalculatorNode> conditionIf = b.newToken()
                .named("if").matchesString("If")
                .nud((left, parser, lexeme) -> new ConditionNode(left, parser));

        public static TokenDefinitionBuilder<CalculatorNode> conditionThen = b.newToken()
                .named("then").matchesString("Then");

        public static TokenDefinitionBuilder<CalculatorNode> conditionElse = b.newToken()
                .named("else").matchesString("Else");

        public static TokenDefinitionBuilder<CalculatorNode> conditionIfEnd = b.newToken()
                .named("ifEnd").matchesString("IfEnd");

        public static TokenDefinitionBuilder<CalculatorNode> comma = b.newToken()
                .named("comma").matchesString(",");
    }

    public static Language<CalculatorNode> createFx50Language() throws Exception {
        b.newToken().named("clearMemory").matchesString("ClrMemory")
                .nud(ClearMemoryNode::new).build();

        b.newToken().named("whitespace").matchesPattern("\\s+").discardAfterLexing().build();

        b.newToken().named("comment").matchesPattern("\\/\\*.*?\\*\\/").discardAfterLexing().build();

        //Tokens.jumpLabel.build();
        //Tokens.jumpGoto.build();

        Tokens.loopNext.leftBindingPower(1).build();
        Tokens.loopFor.leftBindingPower(1).build();
        Tokens.loopTo.leftBindingPower(2).build();
        Tokens.loopStep.leftBindingPower(2).build();
        Tokens.loopWhileEnd.leftBindingPower(1).build();
        Tokens.loopWhile.leftBindingPower(1).build();
        Tokens.conditionIfEnd.leftBindingPower(1).build();
        Tokens.conditionIf.leftBindingPower(1).build();
        Tokens.conditionThen.leftBindingPower(1).build();
        Tokens.conditionElse.leftBindingPower(1).build();

        Tokens.shorthandIf.leftBindingPower(3).build();
        Tokens.display.leftBindingPower(3).build();
        Tokens.colon.leftBindingPower(2).build();
        Tokens.set.leftBindingPower(3).build();

        Tokens.loopBreak.leftBindingPower(4).build();
        Tokens.MPlus.leftBindingPower(5).build();
        Tokens.MMinus.leftBindingPower(5).build();

        Tokens.negate.leftBindingPower(13).build();

        Tokens.rparen.leftBindingPower(4).build();
        Tokens.lparen.leftBindingPower(4).build();

        Tokens.comma.leftBindingPower(4).build();

        Tokens.booleanXor.leftBindingPower(5).build();
        Tokens.booleanXnor.leftBindingPower(5).build();
        Tokens.booleanOr.leftBindingPower(5).build();

        Tokens.booleanAnd.leftBindingPower(6).build();

        Tokens.booleanNot.leftBindingPower(7).build();

        Tokens.booleanEqual.leftBindingPower(8).build();
        Tokens.booleanNotEqual.leftBindingPower(8).build();
        Tokens.booleanLesserThanOrEquals.leftBindingPower(8).build();
        Tokens.booleanLesserThan.leftBindingPower(8).build();
        Tokens.booleanGreaterThanOrEquals.leftBindingPower(8).build();
        Tokens.booleanGreaterThan.leftBindingPower(8).build();

        Tokens.plus.leftBindingPower(9).build();
        Tokens.minus.leftBindingPower(9).build();

        Tokens.multiply.leftBindingPower(10).build();
        Tokens.divide.leftBindingPower(10).build();
        Tokens.modulo.leftBindingPower(10).build();

        Tokens.factorial.leftBindingPower(14).build();
        Tokens.power.leftBindingPower(14).build();
        Tokens.root.leftBindingPower(14).build();

        Tokens.exponential.leftBindingPower(16).build();

        Tokens.answer.leftBindingPower(10).build();
        Tokens.randomNumber.leftBindingPower(10).build();

        Tokens.function.leftBindingPower(10).build();
        Tokens.permutation.leftBindingPower(11).build();
        Tokens.combination.leftBindingPower(11).build();
        Tokens.suffixFunction.leftBindingPower(15).build();
        Tokens.percent.leftBindingPower(15).build();

        Tokens.number.leftBindingPower(10).build();
        Tokens.variable.leftBindingPower(10).build();
        Tokens.constant.leftBindingPower(10).build();
        Tokens.input.leftBindingPower(10).build();

        // leftBindingPower applies only to led; this merges the left to group tokens thus write the tree of brackets

        return b.completeLanguage();
    }

    public static Language<CalculatorNode> getFx50Language() throws Exception {
        if (l == null)
            l = createFx50Language();
        return l;
    }
}
