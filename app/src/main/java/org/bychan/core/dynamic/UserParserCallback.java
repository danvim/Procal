package org.bychan.core.dynamic;

import org.bychan.core.basic.Lexeme;


/**
 * The parser interface used in the dynamic API
 */
public interface UserParserCallback<N> {
    /**
     * Parse the directly following tokens an an expression. Proceed while tokens
     * with higher priority than given binding power are found.
     *
     * @param left passed to the sub-expression parser as the node found directly before. If you
     *                 have performed any custom parsing in your handler you should pass in the directly leading
     *                 node before the sub-expression which you are trying to parse.
     */

    N expression(final N left, int leftBindingPower);

    /**
     * Parse the directly following tokens an an expression. Proceed while tokens
     * with higher priority than the left token are found.
     *
     * @param left passed to the sub-expression parser as the node found directly before. If you
     *                 have performed any custom parsing in your handler you should pass in the directly leading
     *                 node before the sub-expression which you are trying to parse.
     */

    N expression(final N left);

    /**
     * Parse a single lexeme of the token identified by the given key.
     */

    Lexeme<N> expectSingleLexeme(TokenKey tokenKey);

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    boolean nextIs(TokenKey tokenKey);


    N parseSingleToken(N left, TokenKey tokenKey);

    /**
     * Calls the nud parse action. This is pure convenience
     * for led actions which resemble or partly consist of their
     * nud counterparts.
     */
    N nud();

    <S> S abort(final String message);


    Lexeme<N> next();
}
