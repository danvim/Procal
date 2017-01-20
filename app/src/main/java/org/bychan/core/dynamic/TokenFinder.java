package org.bychan.core.dynamic;

import org.bychan.core.basic.Token;

interface TokenFinder<N> {

    Token<N> getToken(final TokenKey soughtKey);
}
