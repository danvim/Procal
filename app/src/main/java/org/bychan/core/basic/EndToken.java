package org.bychan.core.basic;

import org.bychan.core.dynamic.TokenMatcher;

public class EndToken<N> implements Token<N> {

    private static final EndToken INSTANCE = new EndToken<>();


    public static <N> EndToken<N> get() {
        //noinspection unchecked
        return (EndToken<N>) INSTANCE;
    }


    @Override
    public Lexeme<N> toLexeme( LexingMatch match) {
        throw new UnsupportedOperationException("End token should not be lexed");
    }


    @Override
    public TokenMatcher getMatcher() {
        throw new UnsupportedOperationException("End token should not be lexed");
    }

    @Override
    public boolean keepAfterLexing() {
        return true;
    }

    public String toString() {
        return getClass().getSimpleName();
    }
}
