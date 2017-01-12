package dcheungaa.procal;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import java.util.ArrayList;
import java.util.List;

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

    public static Context context;
    public static boolean isShift = false;
    public static boolean isAlpha = false;
    public static boolean isHyp = false;


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
        System.out.println(inputTokensMap.get(keyId).display);
        updateMatrixDisplay();
    }

    /**
     * This is called by methods above to update the matrix display
     */
    public static void updateMatrixDisplay () {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        for (InputToken token : inputExpression) {
            int i = sb.length();
            try {
                sb.append(token.display);
            } catch (Exception e) {
                System.out.println("Cannot use token!");
            }
            sb.setSpan(new ForegroundColorSpan(token.color.getColor()), i, sb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        MainActivity.matrixDisplay.setText(sb);
        System.out.println("Text: ");
        System.out.println(MainActivity.matrixDisplay.getText());
    }

    /**
     * Input a token at cursor position
     * @param keyId {@link InputToken} key id
     */
    public static void inputToken (String keyId) {
        //From key inputs, will be routed to methods above
        addInputTokenAt(cursorPos, keyId);
        resetAltStates();
        cursorPos++;
    }

    /**
     * Delete a token at cursor position
     */
    public static void deleteToken () {
        cursorPos = Math.max(Math.min(cursorPos--, inputExpression.size() - 1), 0);
        System.out.println(cursorPos);
        if (cursorPos >= 0 && inputExpression.size() > 0)
            removeInputTokenAt(cursorPos);
    }

    public static void allClearToken() {
        inputExpression.clear();
        cursorPos = 0;
        updateMatrixDisplay();
    }

    public static void altButtons(String alt) {
        if (alt.equals("shift")) {
            isShift = !isShift;
            isAlpha = false;
        } else if (alt.equals("alpha")) {
            isAlpha = !isAlpha;
            isShift = false;
        } else if (alt.equals("hyperbolic"))
            isHyp = !isHyp;
        refreshState();
    }

    private static void resetAltStates() {
        isShift = false;
        isAlpha = false;
        isHyp = false;
        refreshState();
    }

    private static void refreshState() {
        for (CalcBtn calcBtn : MainActivity.calcBtns) {
            calcBtn.refreshState();
        }
    }

    public static void setContext(Context context) {
        InputHandler.context = context;
    }
}