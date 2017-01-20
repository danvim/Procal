package org.bychan.core.dynamic;




/**
 * The recorded definition of a dynamic token. Utilized to form both {@link DynamicToken}s and
 * {@link DynamicLexeme}s
 */
public class TokenDefinition<N> {

    private final TokenMatcher matcher;

    private final DynamicNudParseAction<N> nud;

    private final DynamicLedParseAction<N> led;

    private final String tokenName;

    private final String documentation;
    private final boolean keepAfterLexing;
    private final int leftBindingPower;
    private final TokenKey tokenKey;

    public TokenDefinition(final TokenMatcher matcher, final DynamicNudParseAction<N> nud, final DynamicLedParseAction<N> led, final String tokenName, final String documentation, boolean keepAfterLexing, int leftBindingPower) {
        this.matcher = matcher;
        this.nud = nud;
        this.led = led;
        this.tokenName = tokenName;
        this.documentation = documentation;
        this.keepAfterLexing = keepAfterLexing;
        this.leftBindingPower = leftBindingPower;
        this.tokenKey = TokenKey.byName(tokenName());
    }


    public TokenMatcher getMatcher() {
        return matcher;
    }

    public boolean keepAfterLexing() {
        return keepAfterLexing;
    }

    @Override
    public String toString() {
        return tokenName;
    }


    public DynamicNudParseAction<N> getNud() {
        return nud;
    }


    public DynamicLedParseAction<N> getLed() {
        return led;
    }


    public String tokenName() {
        return tokenName;
    }

    public int getLeftBindingPower() {
        return leftBindingPower;
    }

    public TokenKey getKey() {
        return tokenKey;
    }


    public String getDocumentation() {
        return documentation;
    }
}
