package org.bychan.core.basic;

import java.util.List;

public class PrattParser<N> implements TokenParserCallback<N> {

    private final LexemeStack<N> lexemeStack;

    private final PositionTracer<N> positionTracer;

    PrattParser(List<Lexeme<N>> lexemes, final PositionTracer<N> positionTracer) {
        this.lexemeStack = new LexemeStack<>(lexemes);
        this.positionTracer = positionTracer;
    }

    public PrattParser(List<Lexeme<N>> lexemes, final String originalInputString) {
        this(lexemes, new PositionTracerImpl<>(originalInputString));
    }

    public N parseExpression() {
        return parseExpression(null, 0);
    }


    public ParseResult<N> tryParseFully( N left, final int rightBindingPower) {
        ParseResult<N> parsed = tryParse(left, rightBindingPower);
        if (parsed.isSuccess()) {
            if (!peek().getToken().equals(EndToken.get())) {
                return ParseResult.failure(new ParsingFailedInformation("The input stream was not completely parsed", getParsingPosition()));
            }
            swallow(EndToken.get());
        }
        return parsed;
    }


    public ParseResult<N> tryParse( N left, final int rightBindingPower) {
        try {
            N rootNode = parseExpression(left, rightBindingPower);
            return ParseResult.success(rootNode);
        } catch (ParsingFailedException e) {
            return ParseResult.failure(e.getFailureInformation());
        }
    }

    /**
     * Parse upcoming tokens from the stream into an expression, and keep going
     * until token binding powers drop down to or below the supplied right binding power. If this
     * feels backwards, remember that weak operands end up higher in the parse tree, consider for instance
     * <code>1*2 + 3 </code> which becomes
     * <pre>
     *       +
     *      / \
     *    1*2  3
     * </pre>.
     * To parse this expression, we start by swallowing "1*2", stopping by "+". This is achieved by calling
     * this method with the lower binding power of "+" as an argument.
     */

    public N parseExpression( N left, final int rightBindingPower) {
        // An expression always starts with a symbol which can qualify as a nud value
        // i.e
        // "+" as in "positive", used in for instance "+3 + 5", parses to +(rest of expression)
        // "-" as in "negative", used in for instance "-3 + 5", parses to -(rest of expression)
        // "(" as in "start sub-expression", used in for instance "(3)", parses rest of expression with 0 strength,
        //         which keeps going until next 0-valued token is encountered (")" or end)
        // any digit, used in for instance "3", parses to 3.
        Lexeme<N> firstLexeme = pop();
        final N first = nud(left, firstLexeme);
        // When we have the nud parsing settled, we cannot be sure that the parsing is done. Digit parsing
        // returns almost immediately for instance. If the nud parse swallowed all the expression, only the end
        // token will remain. But since the end token has 0 binding power, we will never continue in this case.
        //
        // If there is more to parse, however, we should keep parsing as long as we encounter stronger tokens
        // than the caller is currently parsing. When a weaker token is encountered, the tokens so far should namely
        // be wrapped up into a part-expression. This part-expression will then usually form the LHS or RHS of
        // the weaker operand. Remember that weak operands end up higher in the tree, consider for instance
        // 1*2 + 3 which becomes
        //
        //       +
        //      / \
        //    1*2 3
        //
        // The multiplication token will here call parse on the rest of the expression ("2+3"). This parse
        // should abort as soon as the weaker addition token is encountered, so that it returns "2" as the RHS
        // of the multiplication.
        // The addition operators led-parser is then called by the top-level expression parser,
        // passing (1*2) into it as the expression parsed so far. It will then proceed to swallow the 3,
        // completing the expression.
        return recursiveParseLoop(first, rightBindingPower);
    }

    private N iterativeParseLoop( N currentLeftHandSide, final int rightBindingPower) {
        while (peek().leftBindingPower() > rightBindingPower) {
            Lexeme<N> next = pop();
            currentLeftHandSide = led(currentLeftHandSide, next);
        }
        return currentLeftHandSide;
    }

    private N recursiveParseLoop( final N currentLeftHandSide, final int rightBindingPower) {
        Lexeme<N> peekedLexeme = peek();
        if (peekedLexeme.leftBindingPower() > rightBindingPower) {
            //Parsing happens by passing the left LHS to the operator, which will continue parsing.
            Lexeme<N> next = pop();
            N nextExpression = led(currentLeftHandSide, next);
            return recursiveParseLoop(nextExpression, rightBindingPower);
        }
        return currentLeftHandSide;
    }

    private N led( N currentLeftHandSide,  Lexeme<N> takenLexeme) {
        LedParseAction<N> ledParseAction = takenLexeme.getLed();
        if (ledParseAction == null) {
            throw new ParsingFailedException(new ParsingFailedInformation("Current token does not support led parsing", getParsingPosition()));
        }
        return ledParseAction.parse(currentLeftHandSide, this);
    }


    public ParsingPosition getParsingPosition() {
        return positionTracer.getParsingPosition(lexemeStack);
    }


    @Override
    public Lexeme<N> next() {
        return lexemeStack.pop();
    }


    public Lexeme<N> swallow(Token<N> token) {
        Lexeme<N> next = next();
        if (!next.getToken().equals(token)) {
            throw new ParsingFailedException(new ParsingFailedInformation("Expected token '" + token.getName() + "', but got '" + next + "'", getParsingPosition()));
        }
        return next;
    }


    @Override
    public N nud( N left,  Lexeme<N> lexeme) {
        NudParseAction<N> nudParseAction = lexeme.getNud();
        if (nudParseAction == null) {
            throw new ParsingFailedException(new ParsingFailedInformation("Current lexeme does not support nud parsing", getParsingPosition()));
        }
        return nudParseAction.parse(left, this);
    }


    @Override
    public Lexeme<N> peek() {
        return lexemeStack.peek();
    }


    private Lexeme<N> pop() {
        return lexemeStack.pop();
    }
}
