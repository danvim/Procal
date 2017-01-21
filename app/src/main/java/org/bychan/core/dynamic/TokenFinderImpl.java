package org.bychan.core.dynamic;

import org.bychan.core.basic.EndToken;
import org.bychan.core.basic.Token;

import java.util.Collection;
import java.util.Map;

import java8.util.stream.Collectors;
import java8.util.stream.StreamSupport;

/**
 * Created by alext on 2015-01-05.
 */
class TokenFinderImpl<N> implements TokenFinder<N> {
    private final Map<TokenKey, Token<N>> tokensByKey;

    public TokenFinderImpl(Collection<DynamicToken<N>> dynamicTokens) {
        tokensByKey = StreamSupport.stream(dynamicTokens).collect(Collectors.toMap(DynamicToken::getKey, t->t));
        tokensByKey.put(EndToken.get().getKey(), EndToken.get());
    }


    @Override
    public Token<N> getToken(TokenKey soughtKey) {
        Token<N> candidate = tokensByKey.get(soughtKey);
        if (candidate == null) {
            throw new IllegalStateException("No registered token definition keyed '" + soughtKey + "' was found. Did you register your token before referring to it?");
        } else if (candidate.getKey().equals(soughtKey)) {
            return candidate;
        } else {
            throw new IllegalStateException("Found a candidate token definition with the same key ('" + soughtKey + "'), but it had a different specification. Do you have multiple copies of this token definition?");
        }
    }
}
