package org.bychan.core.dynamic;



/**
 * Used to refer to other tokens
 */
public class TokenKey {

    private final String tokenName;

    private TokenKey( String tokenName) {
        this.tokenName = tokenName;
    }

    public static TokenKey byName(final String tokenName) {
        return new TokenKey(tokenName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TokenKey tokenKey = (TokenKey) o;

        return tokenName.equals(tokenKey.tokenName);

    }

    @Override
    public int hashCode() {
        return tokenName.hashCode();
    }

    @Override
    public String toString() {
        return "token(" + tokenName + ")";
    }
}

