package org.bychan.core.basic;

/**
 * An interface to the parser, which the tokens can use to
 * continue the parsing
 */
public interface TokenParserCallback<N> {

    N parseExpression(N left, int rightBindingPower);

    /**
     * Swallow a token of the specified token.
     */

    Lexeme<N> swallow(Token<N> token);


    Lexeme<N> peek();


    N nud(N left, Lexeme<N> lexeme);


    ParsingPosition getParsingPosition();


    Lexeme<N> next();
}
