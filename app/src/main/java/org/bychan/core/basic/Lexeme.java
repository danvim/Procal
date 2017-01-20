package org.bychan.core.basic;

import android.os.Build;
import android.support.annotation.RequiresApi;

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


    @RequiresApi(api = Build.VERSION_CODES.N)
    default String getText() {
        return getMatch().getText();
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    default TokenMatcher getMatcher() {
        return getMatch().getMatcher();
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    default Object getLexerValue() {
        return getMatch().getLexerValue();
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    default String group(int i) {
        return getMatch().group(i);
    }
}
