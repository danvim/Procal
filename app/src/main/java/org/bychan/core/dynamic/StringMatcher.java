package org.bychan.core.dynamic;




/**
 * Tries to match input against an exact string
 */
public class StringMatcher implements TokenMatcher {

    private final String text;

    public StringMatcher( final String text) {
        this.text = text;
    }

    @Override

    public TokenMatchResult<Object> tryMatch(String input, int searchStart) {
        if (input.regionMatches(searchStart, text, 0, text.length())) {
            return TokenMatchResult.create(searchStart + text.length());
        }
        return null;
    }
}
