package org.bychan.core.basic;

import org.bychan.core.utils.TextPosition;

/**
 * Created by alext on 2015-04-05.
 */
public interface FailureInformation {

    TextPosition getTextPosition();


    ParsingFailedInformation toParsingFailedInformation();
}
