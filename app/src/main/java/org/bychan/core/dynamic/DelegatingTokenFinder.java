package org.bychan.core.dynamic;

import org.bychan.core.basic.Token;


/**
 * Used to look up tokens in the list of {@link TokenDefinition}s.
 *
 */
public class DelegatingTokenFinder<N> implements TokenFinder<N> {

    private TokenFinder<N> delegate;


    @Override
    public Token<N> getToken(TokenKey soughtKey) {
        assert delegate != null;
        return delegate.getToken(soughtKey);
    }

    public void setDelegate(@SuppressWarnings("NullableProblems")  final TokenFinder<N> delegate) {
        this.delegate = delegate;
    }
}
