package dcheungaa.procal;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.view.Display;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * Created by Administrator on 10/1/2017.
 */

public class KeyPad_init {
    private List<String> keypadButtons = new ArrayList<>();

    private Gson gson = new Gson();
    public KeyPad_init(final Context c , final InputStream in_s, Display display, final RelativeLayout contentMain, final LinearLayout llScreen, final LinearLayout rows){
        //get windows' height and width

        Point size = new Point();
        display.getSize(size);
        final int height = size.y;

        contentMain.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {                  //<--set listener to the btn
            @Override
            public void onGlobalLayout() {                     //<--define listener function
                contentMain.getViewTreeObserver().removeGlobalOnLayoutListener(this);


                int rows_height = height - llScreen.getHeight() - llScreen.getHeight(); //the height of space left to place keyboard
                double btn_large_height = rows_height * 4.5 / 8 / 4;  //define the height of two types of button,  fn : large =  3.5 : 4.5
                double btn_fn_height = rows_height * 3.5 / 8 / 4;     //define the height of two types of button,  fn : large =  3.5 : 4.5

                String json = "";

                try {
                    byte[] b = new byte[in_s.available()];
                    in_s.read(b);
                    json = new String(b);
                } catch (IOException e) {

                }

                JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
                KeypadRows keypadRows = gson.fromJson(jsonObject, KeypadRows.class);

                for (Key[] keys : keypadRows.rows) {
                    LinearLayout row = new LinearLayout(c);
                    row.setOrientation(LinearLayout.HORIZONTAL);
                    row.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
                    for (Key key : keys) {
                        CalcBtn CBtn = new CalcBtn (c);
                        CBtn.init(key);

                        if (key.style.contains("Fn"))               //decide which height apply to the button
                            CBtn.set_height((int)btn_fn_height);
                        else
                            CBtn.set_height((int)btn_large_height);
                        keypadButtons.add(key.id);
                        CBtn.setId(keypadButtons.indexOf(key.id));
                        row.addView(CBtn);
                    }
                    rows.addView(row);
                }
            }
        });
    }
}
