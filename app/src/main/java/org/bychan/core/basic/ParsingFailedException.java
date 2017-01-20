package org.bychan.core.basic;

public class ParsingFailedException extends RuntimeException {

    private final FailureInformation failureInformation;

    public ParsingFailedException( FailureInformation failureInformation) {
        super(failureInformation.toString());
        this.failureInformation = failureInformation;
    }

    public static ParsingFailedException forFailedAfterLexing(String failureMessage, TokenParserCallback<?> parser) {
        return new ParsingFailedException(new ParsingFailedInformation(failureMessage, parser.getParsingPosition()));
    }


    public FailureInformation getFailureInformation() {
        return failureInformation;
    }
}
