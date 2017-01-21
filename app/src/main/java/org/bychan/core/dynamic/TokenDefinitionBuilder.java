package org.bychan.core.dynamic;



import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import java8.util.stream.StreamSupport;

public class TokenDefinitionBuilder<N> {
    private final TokenDefinitionOwner<N> tokenDefinitionOwner;
    private TokenMatcher matcher;
    private DynamicNudParseAction<N> nud;
    private DynamicLedParseAction<N> led;
    private boolean keepAfterLexing;
    private String tokenName;
    private int leftBindingPower = 1;
    private TokenKey tokenKey;
    private String documentation;

    public TokenDefinitionBuilder( TokenDefinitionOwner<N> tokenDefinitionOwner) {
        this.tokenDefinitionOwner = tokenDefinitionOwner;
        keepAfterLexing = true;
    }


    public TokenDefinitionBuilder<N> matchesString(final String text) {
        return matches(new StringMatcher(text));
    }


    public TokenDefinitionBuilder<N> matches(final TokenMatcher matcher) {
        this.matcher = matcher;
        return this;
    }


    public TokenDefinitionBuilder<N> matchesPattern(final String pattern) {
        return matchesPattern(Pattern.compile(pattern));
    }


    public TokenDefinitionBuilder<N> matchesPattern(final Pattern pattern) {
        return matches(new RegexMatcher(pattern));
    }


    public TokenDefinitionBuilder<N> nud(DynamicNudParseAction<N> nud) {
        this.nud = nud;
        return this;
    }


    public TokenDefinition<N> build() {
        if (matcher == null) {
            throw new IllegalStateException("No matching pattern has been set");
        }
        if (tokenName == null) {
            tokenName = "token" + tokenDefinitionOwner.increaseUnnamedTokenCounter();
        }
        TokenDefinition<N> token = new TokenDefinition<>(matcher, nud, led, tokenName, documentation, keepAfterLexing, leftBindingPower);
        tokenDefinitionOwner.tokenBuilt(token);
        return token;
    }


    public TokenDefinitionBuilder<N> led(final DynamicLedParseAction<N> led) {
        this.led = led;
        return this;
    }


    public TokenDefinitionBuilder<N> discardAfterLexing() {
        this.keepAfterLexing = false;
        return this;
    }


    public TokenDefinitionBuilder<N> named(final String name) {
        this.tokenName = name;
        tokenKey = TokenKey.byName(tokenName);
        return this;
    }


    public TokenDefinitionBuilder<N> matchesWhitespace() {
        return matchesPattern("\\s+");
    }


    public TokenDefinitionBuilder<N> leftBindingPower(int leftBindingPower) {
        this.leftBindingPower = leftBindingPower;
        return this;
    }


    public TokenKey getKey() {
        if (tokenKey == null) {
            throw new IllegalStateException("You must set a name first");
        }
        return tokenKey;
    }


    public TokenDefinitionBuilder<N> doc(final String documentation) {
        this.documentation = documentation;
        return this;
    }


    public TokenDefinitionBuilder<N> matchesAnyPermutationOf(List<String> strings, Collection<String> ignoredSeparators) {
        return matches(new AnyPermutationMatcher(strings, ignoredSeparators));
    }


    public TokenDefinitionBuilder<N> matchesAny(List<String> strings) {
        return matches((input, searchStart) ->
                StreamSupport.stream(strings).map(StringMatcher::new).map(
                        m -> m.tryMatch(input, searchStart)).filter(Objects::nonNull).findFirst().orElse(null));
    }
}
