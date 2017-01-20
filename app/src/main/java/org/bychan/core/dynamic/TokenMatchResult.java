package org.bychan.core.dynamic;




/**
 * Created by alext on 2015-02-06.
 *
 * @param <M> type of object which a lexerValue results in
 */
public class TokenMatchResult<M> {

    private final M lexerValue;
    private final int matchEndPosition;

    private TokenMatchResult( final M lexerValue, int matchEndPosition) {
        this.lexerValue = lexerValue;
        this.matchEndPosition = matchEndPosition;
    }


    public static <M> TokenMatchResult<M> create(int matchEndPosition) {
        return new TokenMatchResult<>(null, matchEndPosition);
    }


    public static <M> TokenMatchResult<M> create(final M lexerValue, int matchEndPosition) {
        return new TokenMatchResult<>(lexerValue, matchEndPosition);
    }


    public M getLexerValue() {
        return lexerValue;
    }

    public int getMatchEndPosition() {
        return matchEndPosition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TokenMatchResult that = (TokenMatchResult) o;

        return matchEndPosition == that.matchEndPosition && !(lexerValue != null ? !lexerValue.equals(that.lexerValue) :
                that.lexerValue != null);
    }

    @Override
    public int hashCode() {
        int result = lexerValue != null ? lexerValue.hashCode() : 0;
        result = 31 * result + matchEndPosition;
        return result;
    }

    @Override
    public String toString() {
        return "TokenMatchResult{" +
                "lexerValue=" + lexerValue +
                ", matchEndPosition=" + matchEndPosition +
                '}';
    }
}
