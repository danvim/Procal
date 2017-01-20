package org.bychan.core.dynamic;

/**
 * Created by alext on 2015-01-05.
 */
interface TokenDefinitionOwner<N> {
    int increaseUnnamedTokenCounter();
    void tokenBuilt(TokenDefinition<N> token);
}
