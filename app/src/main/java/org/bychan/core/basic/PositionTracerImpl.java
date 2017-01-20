package org.bychan.core.basic;

import org.bychan.core.utils.StringUtils;
import org.bychan.core.utils.TextPosition;

public class PositionTracerImpl<N> implements PositionTracer<N> {
    private final String originalInputString;

    public PositionTracerImpl( String originalInputString) {
        this.originalInputString = originalInputString;
    }


    @Override
    public ParsingPosition getParsingPosition(LexemeStack<N> lexemeStack) {
        Lexeme<N> current = lexemeStack.history(0);
        final TextPosition textPosition = getTextPosition(current);
        return new ParsingPosition(textPosition, lexemeStack);
    }


    private TextPosition getTextPosition(Lexeme<N> current) {
        if (originalInputString.isEmpty()) {
            return new TextPosition(0,1,1);
        }
        final int positionToUse = getPositionToUse(current);
        return StringUtils.getTextPosition(originalInputString, positionToUse);
    }

    private int getPositionToUse( Lexeme<N> current) {
        //Null means that we haven't started parsing yet. In this case we use the first string position.
        if (current == null) {
            return 0;
        }
        final int currentPosition = current.getMatch().getStartPosition();
        if (currentPosition == originalInputString.length()) {
            assert current.getToken().equals(EndToken.get());
            //If we are at the END token (past the input length), use the last position.
            return originalInputString.length() -1;
        }
        return currentPosition;
    }

}
