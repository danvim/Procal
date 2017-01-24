package dcheungaa.procal.Func;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;
import android.widget.TextView;

import dcheungaa.procal.CalcBtn;
import dcheungaa.procal.InputHandler;
import dcheungaa.procal.KeyPad_init;
import dcheungaa.procal.MainActivity;
import dcheungaa.procal.R;

/**
 * Created by Bryan on 23/1/2017.
 */


public class FuncEdit {

    public static void darkTheme(){
        MainActivity.FuncEditing = true;
        MainActivity.llKeyPad.setBackgroundColor(Color.parseColor("#374046"));
        for (CalcBtn calcButton : MainActivity.calcBtns){
            if (calcButton.getId() != KeyPad_init.keypadButtons.indexOf("function")
                    && calcButton.getId() != KeyPad_init.keypadButtons.indexOf("delete")
                    && calcButton.getId() != KeyPad_init.keypadButtons.indexOf("all_clear")){
                calcButton.mainButton.setTextColor(Color.WHITE);
                if (calcButton.getId() == KeyPad_init.keypadButtons.indexOf("execute")) calcButton.mainButton.setText("OK");
                if (calcButton.isMore) //calcButton.mainButton.setCompoundDrawables(null, drawableSetBound(R.drawable.ic_more_dark), null, null);
                    calcButton.mainButton.setCompoundDrawablesWithIntrinsicBounds(
                            null, null, null, MainActivity.context.getResources().getDrawable(R.drawable.ic_more_dark));
            } else if (calcButton.getId() == KeyPad_init.keypadButtons.indexOf("function")){
                calcButton.mainButton.setText("CMD");

            }
        }
        InputHandler.refreshState();
    }

    public static void lightTheme(){
        MainActivity.FuncEditing = false;
        MainActivity.llKeyPad.setBackgroundColor(Color.parseColor("#0106000f"));
        for (CalcBtn calcButton : MainActivity.calcBtns){
            if (calcButton.getId() != KeyPad_init.keypadButtons.indexOf("function")
                    && calcButton.getId() != KeyPad_init.keypadButtons.indexOf("delete")
                    && calcButton.getId() != KeyPad_init.keypadButtons.indexOf("all_clear")){
                calcButton.mainButton.setTextColor(Color.BLACK);
                if (calcButton.getId() == KeyPad_init.keypadButtons.indexOf("execute")) calcButton.mainButton.setText("EXE");
                if (calcButton.isMore) //calcButton.mainButton.setCompoundDrawables(null, drawableSetBound(R.drawable.ic_more), null, null);
                    calcButton.mainButton.setCompoundDrawablesWithIntrinsicBounds(
                            null, null, null, MainActivity.context.getResources().getDrawable(R.drawable.ic_more));
            } else if (calcButton.getId() == KeyPad_init.keypadButtons.indexOf("function")){
                calcButton.mainButton.setText("FUNC");

            }
        }
        InputHandler.refreshState();
    }

    public static void openEditArea(){
        HorizontalScrollView hsv = MainActivity.horizontalScrollView;
        ScrollView vsv = MainActivity.verticalScrollView;
        TextView rd = ((TextView) MainActivity.views.get("resultDisplay"));
        vsv.setEnabled(true);
        hsv.setEnabled(false);
        //rd.getHeight()
    }

    private static Drawable drawableSetBound(int drawableId){
        Drawable drawable = ResourcesCompat.getDrawable(MainActivity.context.getResources(), drawableId, null);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        return drawable;
    }

}
