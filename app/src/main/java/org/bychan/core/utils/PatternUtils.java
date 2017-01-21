package org.bychan.core.utils;

import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

import java8.util.stream.StreamSupport;

import static java8.util.stream.Collectors.toList;

/**
 * Created by alext on 2015-03-25.
 */
public class PatternUtils {

    public static List<String> quote( final Collection<String> strings) {
        return StreamSupport.stream(strings).map(Pattern::quote).collect(toList());
    }
}
