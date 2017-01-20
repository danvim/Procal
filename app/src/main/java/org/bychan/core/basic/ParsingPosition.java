package org.bychan.core.basic;

import org.bychan.core.utils.TextPosition;

public class ParsingPosition {

    private final TextPosition textPosition;

    private final LexemeStack<?> lexemeStack;

    public ParsingPosition(TextPosition textPosition, LexemeStack<?> lexemeStack) {
        this.textPosition = textPosition;
        this.lexemeStack = lexemeStack;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParsingPosition that = (ParsingPosition) o;

        return textPosition.equals(that.textPosition) && lexemeStack.equals(that.lexemeStack);

    }

    @Override
    public int hashCode() {
        int result = textPosition.hashCode();
        result = 31 * result + lexemeStack.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return " position " + textPosition +
                ", current lexeme is " + lexemeStack.history(0) + ", previous was " + lexemeStack.history(1) + ", and remaining are " + lexemeStack.remaining();
    }


    public TextPosition getTextPosition() {
        return textPosition;
    }
}
