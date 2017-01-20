package org.bychan.core.dynamic;




import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Matches input against a regular expression
 */
public class RegexMatcher implements TokenMatcher<Matcher> {

    private final Pattern pattern;

    public RegexMatcher( final Pattern pattern) {
        this.pattern = pattern;
    }

    public RegexMatcher( final String pattern) {
        this(Pattern.compile(pattern));
    }

    public Matcher matcher( final String text) {
        return pattern.matcher(text);
    }


    @Override
    public TokenMatchResult<Matcher> tryMatch(String input, int searchStart) {
        Matcher matcher = pattern.matcher(input);
        matcher.region(searchStart, input.length());
        if (matcher.lookingAt()) {
            return TokenMatchResult.create(matcher, matcher.end());
        }
        return null;
    }
}
