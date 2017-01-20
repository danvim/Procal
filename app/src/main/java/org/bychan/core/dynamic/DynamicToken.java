package org.bychan.core.dynamic;

import org.bychan.core.basic.Lexeme;
import org.bychan.core.basic.LexingMatch;
import org.bychan.core.basic.Token;


public class DynamicToken<N> implements Token<N> {

    private final TokenDefinition<N> def;

    private final TokenFinder<N> tokenFinder;

    public DynamicToken( final TokenDefinition<N> def,  final TokenFinder<N> tokenFinder) {
        this.def = def;
        this.tokenFinder = tokenFinder;
    }


    @Override
    public Lexeme<N> toLexeme( final LexingMatch match) {
        return new DynamicLexeme<>(this, match, def, tokenFinder);
    }


    @Override
    public TokenMatcher getMatcher() {
        return def.getMatcher();
    }

    @Override
    public boolean keepAfterLexing() {
        return def.keepAfterLexing();
    }


    @Override
    public String getName() {
        return def.tokenName();
    }

    public String toString() {
        return def.getLeftBindingPower() + ":" + getName();
    }


    public TokenDefinition<N> getTokenDefinition() {
        return def;
    }


    public TokenKey getKey() {
        return def.getKey();
    }
}
