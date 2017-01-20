package org.bychan.core.basic;

/**
 * A "Left Denotation" parse action. Typically used by infix and suffix parse actions.
 */
public interface LedParseAction<N> {
    /**
     * Parse this lexeme as the continuation of a block.
     *
     * @param left The result of parsing the lexemes directly preceding this lexeme.
     * @param parser   Useful for continuing the parsing
     * @return the resulting AST node.
     */

    N parse(N left, TokenParserCallback<N> parser);
}
