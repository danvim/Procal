package org.bychan.core.basic;

import org.bychan.core.utils.TextPosition;

public class ParsingFailedInformation implements FailureInformation {

    private final String failureMessage;

    private final ParsingPosition parsingPosition;

    public ParsingFailedInformation( String failureMessage,  ParsingPosition parsingPosition) {
        this.failureMessage = failureMessage;
        this.parsingPosition = parsingPosition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParsingFailedInformation that = (ParsingFailedInformation) o;

        return failureMessage.equals(that.failureMessage) && parsingPosition.equals(that.parsingPosition);

    }

    @Override
    public int hashCode() {
        int result = failureMessage.hashCode();
        result = 31 * result + parsingPosition.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Parsing failed: '" + failureMessage + '\'' + " @ " + parsingPosition ;
    }


    public String getFailureMessage() {
        return failureMessage;
    }


    public ParsingPosition getParsingPosition() {
        return parsingPosition;
    }


    @Override
    public TextPosition getTextPosition() {
        return parsingPosition.getTextPosition();
    }


    @Override
    public ParsingFailedInformation toParsingFailedInformation() {
        return this;
    }
}
