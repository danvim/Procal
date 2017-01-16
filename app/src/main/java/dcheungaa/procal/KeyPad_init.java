package dcheungaa.procal;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.FILL_PARENT;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * Created by Administrator on 10/1/2017.
 */

public class KeyPad_init {
    public List<String> keypadButtons = new ArrayList<>();
    private Gson gson = new Gson();
    private final int height;
    private final int width;
    private float density;
    public List<List<CalcBtn>> btn_rows = new ArrayList<>();
    private int keyPadHeight;   //in px
    private int display_height=160; //the height of llScreen in dp, need to be constant or it is impossible for dynamic layout

    //to generate main key_pad
    public KeyPad_init(final Context c ,final Resources resource, final InputStream in_s, Display display, final RelativeLayout contentMain, final LinearLayout llScreen, final LinearLayout rows){
        //get windows' height and width

        Point size = new Point();
        display.getSize(size);
        height = size.y;
        width = size.x;
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
                CalcBtn calcBtn = new CalcBtn (c);
                calcBtn.init(key);

                btn_row.add(calcBtn);
                keypadButtons.add(key.id);
                calcBtn.setId(keypadButtons.indexOf(key.id));
                row.addView(calcBtn);
                MainActivity.calcBtns.add(calcBtn);
            }
            rows.addView(row);
            btn_rows.add(btn_row);
        }
    }

    //to generate var keypad
    public KeyPad_init(final Context c ,final Resources resource, Display display, final LinearLayout rows){
        //get windows' height and width

        Point size = new Point();
        display.getSize(size);
        height = size.y;
        width = size.x;

        density = resource.getDisplayMetrics().density;

        List <Integer> var_list = new ArrayList<>();

        //Uppercase Latin
        for (int i = 0x0041; i < 0x005B; i++) {
            var_list.add(i);
        }
        //Lowercase Latin
        for (int i = 0x0061; i < 0x007B; i++) {
            var_list.add(i);
        }

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(MATCH_PARENT,MATCH_PARENT);
        LinearLayout rs=new LinearLayout(c);
        rs.setLayoutParams(lp);
        rs.setOrientation(LinearLayout.VERTICAL);
        for (int i=0; i<var_list.size(); i+=8) {
            LinearLayout row = new LinearLayout(c);
            row.setOrientation(LinearLayout.HORIZONTAL);
            row.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
            List<CalcBtn> btn_row = new ArrayList<>() ;
            for (int j =0; j<8; j++) {
                if (i+j>=var_list.size()) break;
                CalcBtn calcBtn = varBtn(c,var_list.get(i+j));
                btn_row.add(calcBtn);
                row.addView(calcBtn);
                //MainActivity.calcBtns.add(calcBtn);

                calcBtn.setVarColor();
            }
            rs.addView(row);
            btn_rows.add(btn_row);
        }
        rows.addView(rs);
        rows.addView(new TextView(c));
        var_list = new ArrayList<>();
        rs=new LinearLayout(c);
        rs.setLayoutParams(lp);
        rs.setOrientation(LinearLayout.VERTICAL);
        //Uppercase Greek
        for (int i = 0x0391; i < 0x03AA; i++) {
            if (i == 0x03A2) continue;
            var_list.add(i);
        }
        //Lowercase Greek
        for (int i = 0x03B1; i < 0x03CA; i++) {
            if (i == 0x03C2) continue;
            var_list.add(i);
        }

        //int index=0;
        for (int i=0; i<var_list.size(); i+=8) {
            LinearLayout row = new LinearLayout(c);
            row.setOrientation(LinearLayout.HORIZONTAL);
            row.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
            List<CalcBtn> btn_row = new ArrayList<>() ;
            for (int j =0; j<8; j++) {
                if (i+j>=var_list.size()) break;
                CalcBtn calcBtn = varBtn(c,var_list.get(i+j));
                btn_row.add(calcBtn);
                row.addView(calcBtn);
                //MainActivity.calcBtns.add(calcBtn);

                calcBtn.setVarColor();
            }
            rs.addView(row);
            btn_rows.add(btn_row);
        }
        rows.addView(rs);
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

    /*drawable keypad resize
    * x : scrollview x offset from left, y :y offset from top,
     */

    public void resize(int y, int h){
        double ratio = h/3/btn_rows.get(0).get(0).get_height();
        for(int i=0;i<btn_rows.size();i++){
            List<CalcBtn> btn_row = btn_rows.get(i);
            for (int j=0;j<btn_row.size();j++){
                CalcBtn btn=btn_row.get(j);
                btn.shrink(ratio);
            }
        }
        MainActivity.svVar.setVisibility(View.INVISIBLE);
        //MainActivity.svVar.setPadding(0,(int)(display_height*density),MainActivity.svVar.getPaddingRight(),MainActivity.svVar.getPaddingBottom());
        RelativeLayout.LayoutParams lp= new RelativeLayout.LayoutParams(MATCH_PARENT,h) ;
        lp.addRule(RelativeLayout.ALIGN_TOP,MainActivity.llkeyPad.getId());
        //lp.addRule(RelativeLayout.RIGHT_OF,MainActivity.llkeyPad.getId());
        lp.setMargins(0,y,0,0);
        //MainActivity.svVar.setTop((int)(display_height*density));
        MainActivity.svVar.setLayoutParams(lp);
        MainActivity.svVar.setElevation(4*density);
    }

    private CalcBtn varBtn(Context c,int ascii){
        CalcBtn btn=new CalcBtn(c);
        String ch = Character.toString((char) ascii);
        Key k= new Key();
        k.id = "var_"+ch;
        k.text = ch;
        k.style = "Fn";
        btn.init(k);
        keypadButtons.add(k.id);
        btn.setId(keypadButtons.indexOf(k.id));
        return btn;
    }
}