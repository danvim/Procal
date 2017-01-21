package org.bychan.core.dynamic;

import org.bychan.core.basic.LedParseAction;
import org.bychan.core.basic.Lexeme;
import org.bychan.core.basic.LexingMatch;
import org.bychan.core.basic.NudParseAction;
import org.bychan.core.basic.Token;


public class DynamicLexeme<N> implements Lexeme<N> {
    private final DynamicToken<N> token;

    private final LexingMatch match;

    private final TokenDefinition<N> def;

    private final TokenFinder<N> tokenFinder;

    private final DynamicLedParseAction<N> led;

    private final DynamicNudParseAction<N> nud;

    public DynamicLexeme( final DynamicToken<N> token,  final LexingMatch match,  final TokenDefinition<N> def,  final TokenFinder<N> tokenFinder) {
        this.token = token;
        this.match = match;
        this.def = def;
        this.tokenFinder = tokenFinder;
        led = def.getLed();
        nud = def.getNud();
    }


    @Override
    public NudParseAction<N> getNud() {
        return nud == null ? null : (left, parser) -> {
            UserParserCallbackImpl<N> callback = new UserParserCallbackImpl<>(def, tokenFinder, parser, left, this);
            return nud.parse(left, callback, this);
        };
    }


    @Override
    public LedParseAction<N> getLed() {
        return led == null ? null : (left, parser) -> {
            UserParserCallbackImpl<N> callback = new UserParserCallbackImpl<>(def, tokenFinder, parser, left, this);
            return led.parse(left, callback, this);
        };
    }

    @Override
    public int leftBindingPower() {
        return def.getLeftBindingPower();
    }

    @Override

    public Token<N> getToken() {
        return token;
    }

    public String toString() {
        return token.getTokenDefinition().tokenName() + "(" + match.getText() + ")";
    }


    @Override
    public LexingMatch getMatch() {
        return match;
    }

    public String getText() {
        return getMatch().getText();
    }

    public TokenMatcher getMatcher() {
        return getMatch().getMatcher();
    }

    public Object getLexerValue() {
        return getMatch().getLexerValue();
    }

    public String group(int i) {
        return getMatch().group(i);
    }

}
