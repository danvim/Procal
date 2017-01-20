package org.bychan.core.dynamic;



import java.util.ArrayList;
import java.util.List;

/**
 * Builds dynamic languages
 *
 */
public class LanguageBuilder<N> implements TokenDefinitionOwner<N> {

    private final List<TokenDefinition<N>> tokens;

    private final String name;
    /**
     * If you don't specify binding powers of your tokens,
     * they will be automatically be set in increasing order. This is
     * tracked by this counter.
     */
    private int currentLeftBindingPower;
    /**
     * Counter which numbers unnamed tokens
     */
    private int unnamedTokenCounter;

    public LanguageBuilder() {
        this("unnamed");
    }

    public LanguageBuilder( final String name) {
        this.currentLeftBindingPower = 1;
        this.tokens = new ArrayList<>();
        this.name = name;
    }


    public Language<N> completeLanguage() {
        return new Language<>(name, tokens);
    }


    public TokenDefinitionBuilder<N> newToken() {
        return new TokenDefinitionBuilder<>(this).leftBindingPower(currentLeftBindingPower);
    }

    @Override
    public int increaseUnnamedTokenCounter() {
        return ++unnamedTokenCounter;
    }

    @Override
    public void tokenBuilt( TokenDefinition<N> token) {
        tokens.add(token);
    }

    public void powerUp() {
        ++currentLeftBindingPower;
    }
}
