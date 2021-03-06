package org.bychan.core.basic;

/**
 * A "Null Denotion" parse action. Typically used for standalone values and prefix operators.
 *
 */
public interface NudParseAction<N> {
    /**
     * Parse this lexeme as the start of a new block.
     *
     * @param left the result of parsing the lexemes directly preceding this lexeme. This parameter is passed
     *                 in the NUD case even though it is not specified in the original paper. The reason is that
     *                 it might be handy to know the directly preceding result when implementing nested scopes and
     *                 other constructions. Without this knowledge the parser must keep external extra state for these
     *                 things.
     * @param parser Useful for continuing the parsing
     * @return the resulting AST node.
     */

    N parse(N left, TokenParserCallback<N> parser);
}
