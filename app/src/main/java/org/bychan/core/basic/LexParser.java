package org.bychan.core.basic;

import java.util.List;

/**
 * Facade for a {@link org.bychan.core.basic.PrattParser} and a {@link Lexer} which lexes and parses
 * an input text completely, making sure all input text has been properly processed before returning a result.
 *
 */
public class LexParser<N> {

    private final Lexer<N> lexer;

    interface ParseFunction<N> {
        ParseResult<N> parse(PrattParser<N> p);
    }

    public LexParser( final Lexer<N> lexer) {
        this.lexer = lexer;
    }


    public N parse( final String text) {
        return getParser(text).parseExpression();
    }


    public PrattParser<N> getParser(String text) {
        List<Lexeme<N>> lexemes = lexer.lex(text);
        return new PrattParser<>(lexemes, text);
    }

    public ParseResult<N> tryParse( final String text) {
        return tryParse(text, p -> p.tryParseFully(null, 0));
    }


    public ParseResult<N> tryParse( N left,  final String text) {
        return tryParse(text, p -> p.tryParseFully(left, 0));
    }


    public ParseResult<N> tryParse( final String text, ParseFunction<N> parseFunction) {
        LexingResult<N> lexingResult = lexer.tryLex(text);
        if (lexingResult.isFailure()) {
            LexingFailedInformation lexParsingFailedInformation = lexingResult.getFailureValue();
            return ParseResult.failure(lexParsingFailedInformation);
        }
        final PrattParser<N> p = new PrattParser<>(lexingResult.getSuccessValue(), text);
        return parseFunction.parse(p);
    }


    public Lexer<N> getLexer() {
        return lexer;
    }
}
