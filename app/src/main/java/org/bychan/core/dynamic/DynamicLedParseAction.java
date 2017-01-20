package org.bychan.core.dynamic;

import org.bychan.core.basic.Lexeme;


/**
 * Similar to {@link org.bychan.core.basic.LedParseAction}, but receives more runtime information
 * which is needed in the dynamic environment.
 */
public interface DynamicLedParseAction<N> {
    /**
     * Parse this lexeme as the continuation of a block.
     *
     * @param left   The result of parsing the lexemes directly preceding this lexeme.
     * @param parser Useful for continuing the parsing
     * @param lexeme The lexeme which triggered this action
     * @return the resulting AST node.
     */
    N parse(N left, UserParserCallback<N> parser, Lexeme<N> lexeme);
}
