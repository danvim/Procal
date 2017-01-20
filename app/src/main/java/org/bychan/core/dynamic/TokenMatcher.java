package org.bychan.core.dynamic;

/**
 * Created by alext on 2015-02-04
 * @param <M> match result type, if applicable.
 */
public interface TokenMatcher<M> {
    /**
     * Try to match agains the input string at given start location
     *
     * @return <code>null</code> if no match, otherwise a {@link TokenMatchResult} with the
     * index of the match end and any parsed object which you want to be able to pass to your parser callback.
     */

    TokenMatchResult<M> tryMatch(final String input, int searchStart);
}
