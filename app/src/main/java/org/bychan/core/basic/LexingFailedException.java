package org.bychan.core.basic;

class LexingFailedException extends RuntimeException {
    private final LexingPosition lexingPosition;

    public LexingFailedException( final LexingPosition lexingPosition,  final String message) {
        super(message);
        this.lexingPosition = lexingPosition;
    }

    public LexingPosition getLexingPosition() {
        return lexingPosition;
    }

    @Override
    public String toString() {
        return super.getMessage() + " @ " + lexingPosition;
    }
}
