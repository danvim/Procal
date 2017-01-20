package org.bychan.core.dynamic;

import android.text.TextUtils;

import org.bychan.core.utils.CollectionUtils;
import org.bychan.core.utils.PatternUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnyPermutationMatcher implements TokenMatcher {

    private final Pattern pattern;

    public AnyPermutationMatcher( final List<String> targets,  final Collection<String> ignoredSeparators) {
        List<String> quotedSeparators = PatternUtils.quote(ignoredSeparators);
        List<String> quotedTargets = PatternUtils.quote(targets);
        String anySeparatorPatternString = TextUtils.join("|", quotedSeparators);
        String anyTargetPatternString = TextUtils.join("|", quotedTargets);
        pattern = Pattern.compile("(" + anyTargetPatternString + ")(?:" + anySeparatorPatternString + ")?");
    }


    @Override
    public TokenMatchResult tryMatch(String input, final int searchStart) {
        ArrayList<String> hits = new ArrayList<>();
        Matcher m = pattern.matcher(input);
        m.region(searchStart, input.length());
        while (m.lookingAt()) {
            String hit = m.group(1);
            hits.add(hit);
            m.region(m.end(), input.length());
        }
        if (hits.isEmpty()) {
            return null;
        }
        Set<String> dupes = CollectionUtils.getDuplicates(hits);
        if (!dupes.isEmpty()) {
            throw new IllegalStateException("Duplicates: " + dupes);
        }
        return TokenMatchResult.create(m.regionStart());
    }

}
