package dcheungaa.procal;

import android.graphics.Rect;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Button;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.graphics.Typeface;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import java.util.ArrayList;
import java.util.List;


public class CalcBtn extends LinearLayout {

    private PopupWindow Pw;
    private View Pv;
    private List <Button> Pbs = new ArrayList <Button>();

    private CalcBtn CBtn = new CalcBtn(getContext());

    public CalcBtn(Context context) {
        super(context);
        init(context);
    }

    public CalcBtn(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CalcBtn(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        inflate(getContext(), R.layout.calc_btn, this);
        //Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/fontfilename.ttf");
    }

    public CalcBtn ConfigBtn(final Context context, String StyleAttr, String BtnText, String key) {
        final Button Btn = new Button(context, null, getResources().getIdentifier(
                StyleAttr,
                "attr",
                context.getPackageName()
                )
        );
        Btn.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        Btn.setText(BtnText);
        Btn.setTag("Btn");
        Btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Call Main_Add_Stack(key);
            }
        });
        return this;
    }


    public CalcBtn ConfigPopup(final Context context) {
        final View Pv = LayoutInflater.from(context).inflate(R.layout.popupview, null);
        final PopupWindow Pw = new PopupWindow(Pv, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        this.Pw = Pw;
        this.Pv = Pv;
        return this;
    }


    public CalcBtn AddPopupBtn(final Context context, String PbText, String Pbkey){
        final LinearLayout Popup = (LinearLayout) Pv.findViewById(R.id.popup);
        Button Pb = new Button(context);
        Pb.setText(PbText);
        Pb.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        Popup.addView(Pb);
        Pb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Call Main_Add_Stack(Pbkey);
            }
        });
        this.Pbs.add(Pb);
        return this;
    }

    public CalcBtn ListenPopup(final Context context){
        final LinearLayout Popup = (LinearLayout) Pv.findViewById(R.id.popup);
        final boolean[] IfClick = {false};
        final Button Btn = (Button) this.findViewWithTag("button");
        Btn.setOnTouchListener(new View.OnTouchListener() {
            public final boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        IfClick[0] = true;
                        break;
                    case MotionEvent.ACTION_UP:
                        if (IfClick[0]){
                            // ONCLICK
                            Btn.performClick();
                        }else {
                            Pw.dismiss();
                            Rect Pr = new Rect();
                            for (Button Pb: Pbs) {
                                Pb.getHitRect(Pr);
                                if (Pr.contains((int) event.getX(), (int) event.getY() + Pb.getHeight() * Pbs.size())) {
                                    Pb.performClick();
                                }

                            }
                        }
                        break;
                    case MotionEvent.ACTION_POINTER_DOWN:
                        break;
                    case MotionEvent.ACTION_POINTER_UP:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        // ONHOLD
                        Pv.setVisibility(View.VISIBLE);
                        Pv.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
                        Pw.showAsDropDown(Btn,
                                (Btn.getWidth() - (int) Btn.getX())/2 - (Pv.getMeasuredWidth() - (int) Pv.getX())/2,
                                - Btn.getHeight() - Pv.getMeasuredHeight());
                        //Pw.showAtLocation(Btn, Gravity.TOP, (int) Pb.getWidth(), (int) Pb.getHeight());

                        Pw.setFocusable(true);
                        Pw.update();
                        Rect Pr = new Rect();
                        for (Button Pb: Pbs) {
                            Pb.getHitRect(Pr);
                            if (Pr.contains((int) event.getX(), (int) event.getY() + Pb.getHeight() * Pbs.size())) {
                                Pb.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                            } else {
                                // Restore to Normal Color
                                Pb.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                            }
                        }
                        IfClick[0] = false;
                        break;

                }
                return false;
            }
        });
        return this;
    }
}