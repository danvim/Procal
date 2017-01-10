package dcheungaa.procal;

import android.graphics.Rect;
import android.os.Build;
import android.os.Looper;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.view.MotionEvent;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Button;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import android.os.Handler;
import java.util.logging.LogRecord;


public class CalcBtn extends LinearLayout {

    private PopupWindow popupWindow;
    private LinearLayout popupView;
    private List <Button> popupButtons = new ArrayList <>();
    private Context context;
    private Button mainButton;
    private Timer timer;
    private Handler handler;
    private Runnable runnable;

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
        System.out.println("Adding " + key.id);

        setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f));

        mainButton = new Button(context, null, getResources().getIdentifier(
                "Button_" + key.style + (key.shift != null || key.alpha != null ? "_More" : ""),
                "attr",
                context.getPackageName()
        ));
        mainButton.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        if (Build.VERSION.SDK_INT >= 24) {
            mainButton.setText(Html.fromHtml(key.text != null ? key.text : key.id, Html.FROM_HTML_MODE_COMPACT)); // for 24 api and more
        } else {
            mainButton.setText(Html.fromHtml(key.text != null ? key.text : key.id)); // or for older api
        }
        mainButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                silentClick();
            }
        });

        //to ensure the text in main button is within one line
        final Button fbtn = mainButton;
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

        if(key.shift != null || key.alpha != null) {

            popupView = new LinearLayout(context);
            popupWindow = new PopupWindow(popupView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            popupView.setBackgroundResource(R.drawable.popup_container);
            popupWindow.setElevation(16f);

            if(key.shift != null) addPopupButton(key.shift);
            if(key.alpha != null) addPopupButton(key.alpha);
            listenPopup();
        }
        addView(mainButton);
        timer = new Timer();
    }

    public CalcBtn addPopupButton(Key key){
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
            }
        });
        popupButtons.add(popupButton);
        return this;
    }

    private void silentClick() {
        // TODO Call Main_Add_Stack(key.id);
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

    public boolean ifValidOnTouch(MotionEvent event, Button popupButton){
        return (
                (popupButtons.indexOf(popupButton) == 0) ?
                        (
                        (popupButtons.size() == 1) ?
                                (
                                    // 1. Case Only popupbtn
                                    (event.getX() >= popupButton.getX() - popupButton.getWidth() && event.getX() <= popupButton.getX() + popupButton.getWidth() * 3) && (event.getY() >= - popupButton.getHeight() - this.getHeight() && event.getY() <= popupButton.getHeight() * 2 - this.getHeight())
                                ) : (
                                    // 2. Case Leftmost popupbtn
                                    (event.getX() >= popupButton.getX() - popupButton.getWidth() && event.getX() <= popupButton.getX() + popupButton.getWidth()) && (event.getY() >= - popupButton.getHeight() - this.getHeight() && event.getY() <= popupButton.getHeight() * 2 - this.getHeight())
                                )
                        ) : (
                        (popupButtons.indexOf(popupButton) == popupButtons.size() - 1) ?
                                (
                                    // 3. Case Rightmost popupbtn
                                    (event.getX() >= popupButton.getX() && event.getX() <= popupButton.getX() + popupButton.getWidth() * 2) && (event.getY() >= - popupButton.getHeight() - this.getHeight() && event.getY() <= popupButton.getHeight() * 2 - this.getHeight())
                                ) : (
                                    // 4. Case Middle popupbtn
                                    (event.getX() >= popupButton.getX() && event.getX() <= popupButton.getX() + popupButton.getWidth()) && (event.getY() >= - popupButton.getHeight() - this.getHeight() && event.getY() <= popupButton.getHeight() * 2 - this.getHeight())
                                )
                        )
                );
    }

    public CalcBtn listenPopup(){
        mainButton.setOnTouchListener(new OnTouchListener() {
            boolean isClicked = false;
            // boolean displayed = false;
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        isClicked = true;
                        // Testing Hold for timeout to popup
                        /*final Handler handler = new Handler();
                        handler.postDelayed(runnable, 500);
                        Runnable runnable = new Runnable() {
                            @Override
                            public void run() {
                                isClicked = false;
                                displayPopup();
                                handler.postDelayed(this, 500);
                            }
                        };*/
                        break;
                    case MotionEvent.ACTION_UP:
                        if (isClicked){
                            // ONCLICK
                            silentClick();
                            timer.cancel();
                        } else {
                            popupWindow.dismiss();
                            for (Button popupButton: popupButtons) {
                                if (ifValidOnTouch(event, popupButton)){
                                    popupButton.performClick();
                                }

                            }
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                         //if (!isClicked) {   // ONHOLD
                             displayPopup();
                             for (Button popupButton : popupButtons) {
                                 if (ifValidOnTouch(event, popupButton)) {
                                     popupButton.setBackgroundResource(R.drawable.popup_button_active);
                                 } else {
                                     popupButton.setBackgroundResource(R.drawable.ripple_rounded);
                                 }
                             }
                             isClicked = false;
                         //}
                        break;
                }
                return false;
            }
        });
        return this;
    }

    //set height by shrinking padding
    public void set_height(int height){
        int delta = mainButton.getHeight() - height ;
        if (delta==0) return;
        else {
            //shrink top and bottom padding in ratio of 1:2 or "..." symbol on the top of mainButton will disappear
            mainButton.setPadding(mainButton.getPaddingLeft(),mainButton.getPaddingTop()-delta*1/3,
                    mainButton.getPaddingRight(),mainButton.getPaddingBottom()-delta*2/3);
        }
    }
    //add more comment la, or freerider can't read you code :(
}