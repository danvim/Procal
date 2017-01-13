package dcheungaa.procal;

import android.text.ParcelableSpan;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UpdateAppearance;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static dcheungaa.procal.Tokens.inputTokensMap;

/**
 * Created by Daniel on 11/1/2017.
 */

public class InputHandler {
    /**
     * This is where the input expression is stored.
     */
    public static List<InputToken> inputExpression = new ArrayList();
    /**
     * This is the flashing text cursor in the matrix display to determine where to insert/overwrite or delete.
     */
    public static int cursorPos = 0;

    /**
     * True for insert mode, false for overtype mode
     */
    public static boolean isInsert = true;


    /**
     * Removes the token at index
     * @param i index
     */
    public static void removeInputTokenAt (int i) {
        inputExpression.remove(i);
        updateMatrixDisplay();
    }

    /**
     * Add(insert/overwrite, depends on {@link #isInsert}) the token at index
     * @param i index
     * @param keyId {@link InputToken} key id
     */
    public static void addInputTokenAt (int i, String keyId) {
        inputExpression.add(i, inputTokensMap.get(keyId));
        //System.out.println(inputTokensMap.get(keyId).display);
        updateMatrixDisplay();
    }

    /**
     * This is called by methods above to update the matrix display
     */
    public static void updateMatrixDisplay () {

        int index=0;
        int lengthSum=0;
        int length = MainActivity.matrixDisplay.getText().length();
        final SpannableStringBuilder sb = new SpannableStringBuilder();
        for (final InputToken token : inputExpression) {
            int i = sb.length();
            try {
                sb.append(token.display);
            } catch (Exception e) {
                System.out.println("Cannot use token!");
            }
            //sb.setSpan(new ForegroundColorSpan(token.color.getColor()), i, sb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            final int final_index=index;
            final int final_lengthSum=lengthSum;

            ClickableString clickableString=new ClickableString(token.color.getColor(), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int x=sb.getSpanStart(this);
                    System.out.print("\npressed! x: "+x+"\n");
                    MainActivity.cursor.setX(x);
                    cursorPos=final_index;
                    updateMatrixDisplay();
                }
            });

            sb.setSpan(clickableString, i, sb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            //if (index==cursorPos) sb.append("|");
            index++;
            lengthSum+=sb.length()-i;
        };
        CursorHandler.hideCursor();
        MainActivity.matrixDisplay.setText(sb);
        System.out.println("Text: ");
        System.out.println(MainActivity.matrixDisplay.getText());

        makeLinksFocusable(MainActivity.matrixDisplay);
        CursorHandler.hideCursor();
        //CursorHandler.locate(cursorPos);

    }

    /**
     * Input a token at cursor position
     * @param keyId {@link InputToken} key id
     */
    public static void inputToken (String keyId) {
        //From key inputs, will be routed to methods above
        addInputTokenAt(cursorPos, keyId);
        cursorPos++;
    }

    /**
     * Delete a token at cursor position
     */
    public static void deleteToken () {
        cursorPos = Math.max(Math.min(cursorPos--, inputExpression.size() - 1), 0);
        System.out.println(cursorPos);
        if (cursorPos >= 0 && inputExpression.size() > 0) {
            removeInputTokenAt(cursorPos);
        }
    }

    public static void allclearToken () {
        inputExpression.clear();
        cursorPos = 0;
        updateMatrixDisplay();
    }



    /*
 * Methods used above for changing cursor position
 */
    private static void makeLinksFocusable(TextView tv) {
        MovementMethod m = tv.getMovementMethod();
        if ((m == null) || !(m instanceof LinkMovementMethod)) {
            if (tv.getLinksClickable()) {
                tv.setMovementMethod(LinkMovementMethod.getInstance());
            }
        }
    }

/*
 * ClickableString class
 */
    private static class ClickableString extends ClickableSpan {
        private View.OnClickListener mListener;
        private final int mColor;
        public ClickableString(int color, View.OnClickListener listener) {
            mListener = listener;
            mColor = color;
        }
        @Override
        public void onClick(View v) {
            mListener.onClick(v);
        }
        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setColor(mColor);
            ds.setUnderlineText(false);
        }
    }
}