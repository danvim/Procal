package dcheungaa.procal;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import fx50.API.InputToken;

import static dcheungaa.procal.Tokens.inputTokensMap;
import fx50.API.InputToken;
import fx50.Fx50ParseResult;

/**
 * Created by Daniel on 11/1/2017.
 */

public class InputHandler {
    /**
     * This is where the input expression is stored.
     */
    public static List<InputToken> inputExpression = new ArrayList();
    public static List<String> lexableExpression = new ArrayList();

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
    public static boolean isRCL = false;
    public static boolean isSTO = false;

    /**
     * Removes the token at index
     *
     * @param i index
     */
    public static void removeInputTokenAt(int i) {
        inputExpression.remove(i);
        updateMatrixDisplay();
    }

    /**
     * Add(insert/overwrite, depends on {@link #isInsert}) the token at index
     *
     * @param i     index
     * @param keyId {@link InputToken} key id
     */

    public static void addInputTokenAt(int i, String keyId) throws NullPointerException {
        InputToken token = inputTokensMap.get(keyId);
        if (token == null)
            throw new NullPointerException();
        inputExpression.add(i, token);
        System.out.println(inputTokensMap.get(keyId).display);
        updateMatrixDisplay();
    }

    /**
     * This is called by methods above to update the matrix display
     */
    public static void updateMatrixDisplay() {

        final SpannableStringBuilder sb = new SpannableStringBuilder();
        for (final InputToken token : inputExpression) {
            int i = sb.length();
            try {
                sb.append(token.display);
            } catch (Exception e) {
                System.out.println("Cannot use token!");
            }
            sb.setSpan(new ForegroundColorSpan(token.color.getColor()), i, sb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        MainActivity.matrixDisplay.setText(sb);
        MainActivity.matrixDisplay.append(" ");
        System.out.println("Text: ");
        System.out.println(MainActivity.matrixDisplay.getText());

        makeLinksFocusable(MainActivity.matrixDisplay);

    }

    /**
     * Input a token at cursor position
     *
     * @param keyId {@link InputToken} key id
     */
    public static void inputToken(String keyId) {
        //From key inputs, will be routed to methods above
        try {
            addInputTokenAt(cursorPos, keyId);
            cursorPos++;
            CursorHandler.locate(cursorPos);
        } catch (NullPointerException e) {
            Toast toast = Toast.makeText(context, keyId + " action not found!", Toast.LENGTH_SHORT);
            toast.show();
        }
        resetAltStates();
    }

    /**
     * Delete a token at cursor position
     */
    public static void deleteToken() {
        cursorPos = Math.max(Math.min(--cursorPos, inputExpression.size() - 1), 0);
        System.out.println(cursorPos);
        CursorHandler.locate(cursorPos);
        if (cursorPos >= 0 && inputExpression.size() > 0) {
            removeInputTokenAt(cursorPos);
        }
    }

    public static void allClearToken() {
        inputExpression.clear();
        cursorPos = 0;
        CursorHandler.locate(cursorPos);
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

    public static void openDrawer(ScrollView sv) {
        sv.setVisibility(View.VISIBLE);
        TranslateAnimation animation = new TranslateAnimation(
                Animation.ABSOLUTE,MainActivity.screenWidth,
                Animation.ABSOLUTE,0,
                Animation.ABSOLUTE,0,
                Animation.ABSOLUTE,0
        );
        //animation.setRepeatCount(Animation.INFINITE);
        animation.setDuration(500);
        animation.setInterpolator(MainActivity.context,android.R.interpolator.fast_out_slow_in);
        sv.startAnimation(animation);
    }

    public static void hideDrawer(final ScrollView sv) {
        if (sv.getVisibility()==View.INVISIBLE) return;
        TranslateAnimation animation = new TranslateAnimation(
                Animation.ABSOLUTE,0,
                Animation.ABSOLUTE,MainActivity.screenWidth,
                Animation.ABSOLUTE,0,
                Animation.ABSOLUTE,0
        );
        //animation.setRepeatCount(Animation.INFINITE);
        animation.setDuration(500);
        animation.setInterpolator(MainActivity.context,android.R.interpolator.fast_out_slow_in);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                sv.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        sv.startAnimation(animation);

    }

    public static String getLexableString() {
        String lexableString = "";
        for (InputToken token:inputExpression) {
            lexableString += token.spaced ? " " + token.lexable + " " : token.lexable;
        }
        return lexableString;
    }

    public static void execute(){
        DisplayModeHandler.displayMode = true;
        for (InputToken token : InputHandler.inputExpression) {
            InputHandler.lexableExpression.add(token.lexable);
        }
        // Throw to API
        try {
            Fx50ParseResult parseResult = MainActivity.fx50Parser.parse(InputHandler.getLexableString());
            if (parseResult.getErrorString() != null)
                throw new Exception(parseResult.getErrorString());
            MainActivity.resultDisplay.setText(parseResult.getStringResult());
            System.out.println(parseResult.getStringResult());
            System.out.println(parseResult.getBigDecimalResult());
        } catch (Exception e) {
            MainActivity.matrixDisplay.setText(e.getMessage());
            e.printStackTrace(System.out);
        }
        CursorHandler.hide();
    }

    public static void runProgram(String lexableString){
        DisplayModeHandler.displayMode = true;
        // Throw to API
        try {
            Fx50ParseResult parseResult = MainActivity.fx50Parser.parse(lexableString);
            if (parseResult.getErrorString() != null)
                throw new Exception(parseResult.getErrorString());
            MainActivity.resultDisplay.setText(parseResult.getStringResult());
            System.out.println(parseResult.getStringResult());
            System.out.println(parseResult.getBigDecimalResult());
        } catch (Exception e) {
            MainActivity.matrixDisplay.setText(e.getMessage());
            e.printStackTrace(System.out);
        }
        CursorHandler.hide();
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

    /**
     * Methods used above for changing cursor position
     * @param tv TextView to make selectable
     */
    private static void makeLinksFocusable(TextView tv) {
        MovementMethod m = tv.getMovementMethod();
        if ((m == null) || !(m instanceof LinkMovementMethod)) {
            if (tv.getLinksClickable()) {
                tv.setMovementMethod(LinkMovementMethod.getInstance());
            }
        }
    }
}