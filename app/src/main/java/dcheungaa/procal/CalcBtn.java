package dcheungaa.procal;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import java.util.ArrayList;
import java.util.List;

import dcheungaa.procal.Func.FuncActivity;

import static dcheungaa.procal.InputHandler.inputExpression;
import static dcheungaa.procal.InputHandler.isAlpha;
import static dcheungaa.procal.InputHandler.isHyp;
import static dcheungaa.procal.InputHandler.isRCL;
import static dcheungaa.procal.InputHandler.isSTO;
import static dcheungaa.procal.InputHandler.isShift;


public class CalcBtn extends LinearLayout {

    private PopupWindow popupWindow;
    private LinearLayout popupView;
    private List <Button> popupButtons = new ArrayList <>();
    private Context context;
    public Button mainButton;
    private Handler handler;
    private Runnable runnable;
    public Key key;
    private int defaultColor;
    private float defaultTextSize;
    private boolean isLarge;
    private boolean isText;
    private Typeface defaultTypeface;
    private int mHeight = 0;

    public CalcBtn(Context context) {
        super(context);
        this.context = context;
    }

    public CalcBtn(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public CalcBtn(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    public void init(Key key) {
        this.key = key;
        final Key mainButtonKey = key;

        System.out.println("Adding " + key.id);

        setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT, 1f));

        mainButton = new Button(context, null, getResources().getIdentifier(
                "Button_" + key.style + (key.shift != null || key.alpha != null ? "_More" : ""),
                "attr",
                context.getPackageName()
        ));
        defaultColor = mainButton.getCurrentTextColor();
        defaultTextSize = mainButton.getTextSize() / getResources().getDisplayMetrics().scaledDensity;
        isLarge = key.style.contains("Large");
        isText = key.style.contains("Text");
        defaultTypeface = mainButton.getTypeface();
        mainButton.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
        if (Build.VERSION.SDK_INT >= 24) {
            mainButton.setText(Html.fromHtml(key.text != null ? key.text : key.id, Html.FROM_HTML_MODE_COMPACT)); // for 24 api and more
        } else {
            mainButton.setText(Html.fromHtml(key.text != null ? key.text : key.id)); // or for older api
        }
        mainButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                silentClick(mainButtonKey);
            }
        });

        resize_horizontal(mainButton);

        if(key.shift != null || key.alpha != null) {

            popupView = new LinearLayout(context);
            popupWindow = new PopupWindow(popupView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            popupView.setBackgroundResource(R.drawable.popup_container);
            popupWindow.setElevation(16f);

            if(key.shift != null) addPopupButton(key.shift);
            if(key.alpha != null) addPopupButton(key.alpha);
            if(key.hyp != null) addPopupButton(key.hyp);
            if(key.hyp!= null && key.hyp.shift != null) addPopupButton(key.hyp.shift);  // Ensure first layer (key.hyp) is passed
            listenPopup();
        }
        addView(mainButton);
    }

    public CalcBtn addPopupButton(Key key){
        final Key popupButtonKey = key;
        Button popupButton = new Button(context, null, getResources().getIdentifier(
                "Button_Popup",
                "attr",
                context.getPackageName()
        ));
        popupButton.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        popupButton.setBackgroundResource(R.drawable.ripple_rounded);
        if (Build.VERSION.SDK_INT >= 24) {
            popupButton.setText(Html.fromHtml(key.text != null ? key.text : key.id, Html.FROM_HTML_MODE_COMPACT)); // for 24 api and more
        } else {
            popupButton.setText(Html.fromHtml(key.text != null ? key.text : key.id)); // or for older api
        }
        popupView.addView(popupButton);
        popupButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Call Main_Add_Stack(key.id);
                silentClick(popupButtonKey);
            }
        });
        popupButtons.add(popupButton);
        return this;
    }

    private void silentClick(Key key) {
        // TODO Call Main_Add_Stack(key.id);
        String id = "";
        //MainActivity.svVar.setVisibility(INVISIBLE);
        if (isShift && isHyp && key.hyp != null && key.hyp.shift != null)
            id = key.hyp.shift.id;
        else if (isShift && key.shift != null)
            id = key.shift.id;
        else if (isAlpha && key.alpha != null)
            id = key.alpha.id;
        else if (isHyp && key.hyp != null)
            id = key.hyp.id;
        else
            id = key.id;
        System.out.println("Pressed: " + id);

        DisplayModeHandler.handle(id);

        switch (id) {

            case "variable":
                if(MainActivity.svVar.getVisibility() == View.INVISIBLE){
                    InputHandler.openDrawer(MainActivity.svVar);
                } else {
                    InputHandler.hideDrawer(MainActivity.svVar);
                }
                break;

            case "recall":
                if(MainActivity.svVar.getVisibility() == View.INVISIBLE){
                    InputHandler.openDrawer(MainActivity.svVar);
                    isRCL = true;
                    System.out.print("set is RCL true\n");
                } else {
                    InputHandler.hideDrawer(MainActivity.svVar);
                    isRCL = false;
                    System.out.print("set is RCL false\n");
                }

                break;

            case "store":
                if(MainActivity.svVar.getVisibility() == View.INVISIBLE){
                    InputHandler.openDrawer(MainActivity.svVar);
                    isSTO = true;
                    System.out.print("set is STO true\n");
                    //TODO : if in program mode, it will just give a -> then open drawer

                } else {
                    InputHandler.hideDrawer(MainActivity.svVar);
                    isSTO = false;
                    System.out.print("set is STO false\n");
                }

                break;

            case "function":
                Intent FuncIntent = new Intent(MainActivity.context, FuncActivity.class);
                //Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(MainActivity.context, R.anim.pull_out_left, R.anim.pull_out_right).toBundle();
                MainActivity.context.startActivity(FuncIntent);
                //Toast.makeText(context, (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ? "true" : "false"), Toast.LENGTH_LONG).show();

                //TODO add change FUNC key to CMD key in PROG EDIT
                /*if(MainActivity.svCmd.getVisibility() == View.INVISIBLE){
                    InputHandler.openDrawer(MainActivity.svCmd);
                } else {
                    InputHandler.hideDrawer(MainActivity.svCmd);
                }*/
                break;

            case "constant":
                if(MainActivity.svConst.getVisibility() == View.INVISIBLE){
                    InputHandler.openDrawer(MainActivity.svConst);
                } else {
                    InputHandler.hideDrawer(MainActivity.svConst);
                }
                break;

            case "delete":
                InputHandler.deleteToken();
                break;

            case "all_clear":
                InputHandler.allClearToken();
                break;

            case "shift":
                InputHandler.altButtons("shift");
                break;

            case "alpha":
                InputHandler.altButtons("alpha");
                break;

            case "hyperbolic":
                InputHandler.altButtons("hyperbolic");
                break;

            case "execute":
                InputHandler.execute();
                break;

            default:
                // if the key before pressing this key is {RCL || STO}
                System.out.print(Boolean.toString(isRCL)+" "+Boolean.toString(isSTO)+"\n");
                if (( isRCL || isSTO) ){
                    if (isRCL&& id.contains("var_")){
                        InputHandler.inputToken(id);
                        if (inputExpression.size()==1){
                            System.out.print("RCL call EXE\n");
                            InputHandler.execute();

                        }
                    }

                    if(isSTO&& id.contains("var_")){
                        if (inputExpression.size()==0) InputHandler.inputToken("answer");
                        InputHandler.cursorPos = inputExpression.size();

                        InputHandler.inputToken("arrow");
                        InputHandler.inputToken(id);
                        InputHandler.execute();
                        System.out.print("STO call EXE\n");
                    }

                    isRCL = false;
                    isSTO = false;
                    System.out.print("S1 set false\n");
                }else{
                    InputHandler.inputToken(id);
                }
                break;
        }
        if (!id.equals("variable") && !id.equals("recall") && !id.equals("store")) InputHandler.hideDrawer(MainActivity.svVar);
        if (!id.equals("function")) InputHandler.hideDrawer(MainActivity.svCmd);
        if (!id.equals("constant")) InputHandler.hideDrawer(MainActivity.svConst);
    }

    private String getKeyId() {
        return key.id;
    }

    private void displayPopup() {
        popupView.setVisibility(View.VISIBLE);
        popupView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        popupWindow.showAsDropDown(mainButton,
                (mainButton.getWidth() - (int) mainButton.getX())/2 - (popupView.getMeasuredWidth() - (int) popupView.getX())/2,
                - mainButton.getHeight() - popupView.getMeasuredHeight() - 24);
        popupWindow.setFocusable(true);
        popupWindow.update();
    }

    private void highlightPopup(MotionEvent event) {
        for (Button popupButton : popupButtons) {
            if (ifValidOnTouch(event, popupButton)) {
                System.out.println("Highlighting~");
                popupButton.setBackgroundResource(R.drawable.popup_button_active);
            } else {
                popupButton.setBackgroundResource(R.drawable.ripple_rounded);
            }
        }
    }

    public boolean ifValidOnTouch(MotionEvent event, Button popupButton) {

        int[] location = new int[2];
        popupButton.getLocationOnScreen(location);
        int x = location[0];
        int w = popupButton.getWidth();
        System.out.println(event.getRawX() + " vs " + x + " ; " + event.getRawY() + " vs " + (popupButton.getHeight() * 2 - this.getHeight()));

        if (popupButtons.indexOf(popupButton) == 0) {
            if (popupButtons.size() == 1) {
                // 1. Case Only popupbtn
                return (event.getX() >= popupButton.getX() - popupButton.getWidth() && event.getX() <= popupButton.getX() + popupButton.getWidth() * 3) && (event.getY() >= -popupButton.getHeight() - this.getHeight() && event.getY() <= popupButton.getHeight() * 2 - this.getHeight());
            } else {
                // 2. Case Leftmost popupbtn
                return (event.getRawX() >= x - 2*w  && event.getRawX() <= x + w) && (event.getY() >= -popupButton.getHeight() - this.getHeight() && event.getY() <= popupButton.getHeight() * 2 - this.getHeight());
            }
        } else {
            if (popupButtons.indexOf(popupButton) == popupButtons.size() - 1) {
                // 3. Case Rightmost popupbtn
                return (event.getRawX() >= x  && event.getRawX() <= x + 3*w) && (event.getY() >= -popupButton.getHeight() - this.getHeight() && event.getY() <= popupButton.getHeight() * 2 - this.getHeight());
            } else {
                // 4. Case Middle popupbtn
                return (event.getRawX() >= x  && event.getRawX() <= x + w) && (event.getY() >= -popupButton.getHeight() - this.getHeight() && event.getY() <= popupButton.getHeight() * 2 - this.getHeight());
            }
        }
    }

    public CalcBtn listenPopup(){

        mainButton.setOnTouchListener(new OnTouchListener() {

            // Determinant
            boolean popingup;
            long waitDuration = 500;
            final Handler handler = new Handler();
            Runnable doPopup = new Runnable() {@Override public void run() {}};

            final int MAX_CLICK_DISTANCE = 50;
            float pressedX;
            float pressedY;
            boolean withinClick;

            private Rect rect = new Rect();

            public boolean onTouch(View v, final MotionEvent event) {

                switch (event.getAction() & MotionEvent.ACTION_MASK) {

                    case MotionEvent.ACTION_DOWN:
                        rect = new Rect(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
                        System.out.println("########" + rect.flattenToString());
                        popingup = false;
                        doPopup = new Runnable() {
                            // Perform these if user has pressed long enough to summon Popup
                            public void run(){
                                popingup = true;
                                displayPopup();
                                for (Button popupButton : popupButtons) {
                                    if (ifValidOnTouch(event, popupButton)) {
                                        System.out.println("Highlighting~");
                                        popupButton.setBackgroundResource(R.drawable.popup_button_active);
                                    } else {
                                        popupButton.setBackgroundResource(R.drawable.ripple_rounded);
                                    }
                                }
                            }
                        };
                        // After waitDuration, execute doPopup
                        handler.postDelayed(doPopup, waitDuration);
                        pressedX = event.getX();
                        pressedY = event.getY();
                        withinClick = true;
                        break;

                    case MotionEvent.ACTION_UP:
                        // Cancel handler (doPopup) even if it is only half-way, determining popingup
                        handler.removeCallbacks(doPopup);
                        if (popingup) {
                            for (Button popupButton : popupButtons){
                                if (ifValidOnTouch(event, popupButton)){
                                    popupButton.performClick();
                                }
                            }
                            popupWindow.dismiss();
                        }
                        break;

                    case MotionEvent.ACTION_MOVE:
                        System.out.println("%%%%%%%%" + (int)event.getX() + "," + (int)event.getY());
                        if (popingup)
                            handler.removeCallbacks(doPopup);
                        else if(!rect.contains((int)event.getX(), (int)event.getY())){
                            // User moved outside mainButton
                            System.out.println("$$$$$$$$$$$$$$$");
                            handler.removeCallbacks(doPopup);
                        }
                        //if (withinClick && distance(pressedX, pressedY, event.getX(), event.getY()) > MAX_CLICK_DISTANCE) {
                        if (true) {
                            withinClick = false;
                            if(popingup) {
                                for (Button popupButton : popupButtons) {
                                    if (ifValidOnTouch(event, popupButton)) {
                                        System.out.println("Highlighting~");
                                        popupButton.setBackgroundResource(R.drawable.popup_button_active);
                                    } else {
                                        popupButton.setBackgroundResource(R.drawable.ripple_rounded);
                                    }
                                }
                            }
                        } else {
                            // withinClick
                            /*doPopup = new Runnable() {
                                // Perform these if user has pressed long enough to summon Popup
                                public void run(){
                                    popingup = true;
                                    displayPopup();
                                    highlightPopup(event);
                                }
                            };*/
                            // After waitDuration, execute doPopup
                            handler.postDelayed(doPopup, waitDuration);
                            withinClick = true;
                            break;
                        }
                        break;
                }
                return false;
            }
        });
        return this;
    }

    private float distance(float x1, float y1, float x2, float y2) {
        float dx = x1 - x2;
        float dy = y1 - y2;
        float distanceInPx = (float) Math.sqrt(dx * dx + dy * dy);
        return pxToDp(distanceInPx);
    }

    private float pxToDp(float px) {
        return px / context.getResources().getDisplayMetrics().density;
    }

    public int get_height(){
        return mainButton.getHeight();
    }

    public int get_mheight(){ return mHeight; }

    /**
     * Adjust the height of CBtn by giving shrinking ratio of padding and text size
     * @param ratio row height / keypad height
     */
    public void shrink(double ratio){
        //px = density * sp
        double density = getResources().getDisplayMetrics().scaledDensity;
        //set padding by multiplying ratio
        int newPadding = (int)(mainButton.getPaddingTop()*ratio);
        //set font size by multiplying ratio adjusted with sp density
        float newTextSize = (float)(mainButton.getTextSize()*ratio/density);

        mainButton.setPadding(newPadding, newPadding, newPadding, newPadding);
        mainButton.setTextSize(newTextSize);
        mHeight = (int)(mainButton.getHeight()*ratio);
        mainButton.setHeight((int)(mainButton.getHeight()*ratio));

        defaultTextSize = newTextSize;

        //after changing font size and padding, some text may go to second line, need to adjust horizontal padding or font size
        resize_horizontal(mainButton);
    }

    //to ensure the text in main button is within one line
    private void resize_horizontal(final Button fbtn){
        mainButton.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {                     //<--set listener to the btn
            @Override
            public void onGlobalLayout() {                     //<--define listener function
                //if take more than 1 line
                if (fbtn.getLineCount()>1){                     //<--check if text in btn more than 1  line
                    //shrink padding
                    fbtn.setPadding(fbtn.getPaddingLeft()-1,fbtn.getPaddingTop(),fbtn.getPaddingRight()-1,fbtn.getPaddingBottom());                     //<--shrink padding
                }
                // if already take 1 line only, remove the listener
                else fbtn.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
    }

    public void refreshState() {
        SpannableString sb;
        int color = defaultColor;
        boolean isAlt = true;
        if (isShift && isHyp && key.hyp != null && key.hyp.shift != null) {
            sb = new SpannableString(Html.fromHtml(key.hyp.shift.text));
            color = context.getResources().getColor(R.color.colorAccent);
        } else if (isShift && key.shift != null) {
            sb = new SpannableString(Html.fromHtml(key.shift.text));
            color = context.getResources().getColor(R.color.colorAccent);
        } else if (isAlpha && key.alpha != null) {
            sb = new SpannableString(Html.fromHtml(key.alpha.text));
            color = context.getResources().getColor(R.color.colorPurple);
        } else if (isHyp && key.hyp != null) {
            sb = new SpannableString(Html.fromHtml(key.hyp.text));
        } else if (key.text != null) {
            sb = new SpannableString(Html.fromHtml(key.text));
            isAlt = false;
        } else {
            sb = new SpannableString(Html.fromHtml(key.id));
            isAlt = false;
        }
        if (isLarge && !isText && sb.length() >= 3 && isAlt)
            mainButton.setTextSize(defaultTextSize*24/34);
        else if (isLarge && isText && sb.length() < 3 && isAlt) {
            mainButton.setTextSize(defaultTextSize * 34 / 24);
            mainButton.setTypeface(Typeface.create("sans-serif-light", Typeface.NORMAL));
        } else if (!isAlt) {
            mainButton.setTextSize(defaultTextSize);
            mainButton.setTypeface(defaultTypeface);
        }
        sb.setSpan(new ForegroundColorSpan(color), 0, sb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mainButton.setText(sb);
    }

    public void setColor(int color){
        mainButton.setTextColor(color);
        //mainButton.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDarker));
        }
}