package org.bychan.core.basic;

import org.bychan.core.dynamic.TokenMatcher;

import java.util.regex.Matcher;

/**
 * The result of a lexing match.
 * <p>
 * When the lexer finds a piece of the input text which matches a particular {@link Token} it
 * makes a {@link LexingMatch} to describe the match: The matching text fragment, it's location and the
 * the originating {@link Token}.
 * </p>
 */
public class LexingMatch<N> {

    private final int startPosition;
    private final int endPosition;

    private final String text;

    private final Token<N> token;

    private final Object lexerValue;

    public LexingMatch(int startPosition, int endPosition,  final String text,  final Token<N> token) {
        this(startPosition, endPosition, text, token, null);
    }

    public LexingMatch(int startPosition, int endPosition, final String text, final Token<N> token, Object lexerValue) {
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.text = text;
        this.token = token;
        this.lexerValue = lexerValue;
    }


    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "LexingMatch{" +
                "startPosition=" + startPosition +
                ", endPosition=" + endPosition +
                ", text='" + text + '\'' +
                '}';
    }

    public int getEndPosition() {
        return endPosition;
    }

    public int getStartPosition() {
        return startPosition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LexingMatch that = (LexingMatch) o;

        return endPosition == that.endPosition && startPosition == that.startPosition && text.equals(that.text);
    }

    @Override
    public int hashCode() {
        int result = startPosition;
        result = 31 * result + endPosition;
        result = 31 * result + text.hashCode();
        return result;
    }


    public TokenMatcher getMatcher() {
        return token.getMatcher();
    }


    public String group(int i) {
        return ((Matcher) getLexerValue()).group(i);
    }


    public Lexeme<N> toLexeme() {
        return token.toLexeme(this);
    }


    public Object getLexerValue() {
        if (lexerValue == null) {
            throw new IllegalStateException("No lexer value recorded");
        }
        return lexerValue;
    }
}
