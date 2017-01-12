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
    private final int height;
    private float density;
    private List <List<CalcBtn>> btn_rows = new ArrayList<>();
    private int keyPadHeight;   //in px
    private int display_height=160; //the height of llScreen in dp, need to be constant or it is impossible for dynamic layout

    public KeyPad_init(final Context c ,final Resources resource, final InputStream in_s, Display display, final RelativeLayout contentMain, final LinearLayout llScreen, final LinearLayout rows){
        //get windows' height and width

        Point size = new Point();
        display.getSize(size);
        height = size.y;

        density = resource.getDisplayMetrics().density;



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
            List<CalcBtn> btn_row = new ArrayList<>() ;
            for (Key key : keys) {
                CalcBtn CBtn = new CalcBtn (c);
                CBtn.init(key);

                btn_row.add(CBtn);
                keypadButtons.add(key.id);
                CBtn.setId(keypadButtons.indexOf(key.id));
                row.addView(CBtn);
            }
            rows.addView(row);
            btn_rows.add(btn_row);
        }
    }


    public void KeyPad_resize(){

        for(int i=0;i<btn_rows.size();i++){
            List<CalcBtn> btn_row = btn_rows.get(i);
            int max_height=0;
            for (int j=0;j<btn_row.size();j++){
                CalcBtn btn=btn_row.get(j);
                max_height = Math.max(btn.get_height(),max_height);
            }
            keyPadHeight+=max_height;
        }

        int rows_height = (int)(height - density*display_height); //the height of space left to place keyboard
        System.out.print(Integer.toString(height)+" "+Integer.toString(rows_height)+" "+Integer.toString(keyPadHeight)+"\n");

        for(int i=0;i<btn_rows.size();i++){
            List<CalcBtn> btn_row = btn_rows.get(i);
            for (int j=0;j<btn_row.size();j++){
                CalcBtn btn=btn_row.get(j);
                btn.shrink((double)(rows_height)/(double)(keyPadHeight));
            }
        }
    }
}