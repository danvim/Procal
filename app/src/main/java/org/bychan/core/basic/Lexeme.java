package org.bychan.core.basic;

import org.bychan.core.dynamic.TokenMatcher;

/**
 * A lexeme in the lexing stream.
 */
public interface Lexeme<N> {

    NudParseAction<N> getNud();


    LedParseAction<N> getLed();
    /**
     * @return How strongly this lexeme, when interpreted as an infix operator (led), binds to the left argument.
     */
    int leftBindingPower();

    /**
     * Get the token of this lexeme.
     */

    Token<N> getToken();

    /**
     * @return the lexing match which this lexeme originated from
     */

    LexingMatch getMatch();

    String getText();

    TokenMatcher getMatcher();

    Object getLexerValue();

    String group(int i);
}
