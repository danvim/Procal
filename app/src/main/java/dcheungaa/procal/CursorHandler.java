package dcheungaa.procal;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 13/1/2017.
 */

public class CursorHandler {

    private static long shine_interval = 1000;
    private static TextView cursor;
    private static Timer cursorTimer=new Timer();

    public static void hideCursor(){
        //MainActivity.set_Cursor_Visibility(cursor,View.INVISIBLE);
        //MainActivity.cursor.setText(" ");
        //cursorTimer.cancel();
        //System.out.print("hide\n");
        cursorTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                showCursor();
            }
        }, shine_interval);
    }

    public static void showCursor(){
        //MainActivity.set_Cursor_Visibility(cursor,View.VISIBLE);
        //MainActivity.cursor.setText("|");
        //cursorTimer.cancel();
        //System.out.print("show\n");
        cursorTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                hideCursor();
            }
        }, shine_interval);
    }

    public static void changeCursorPos(int tap_x) {
        cursor = MainActivity.cursor;
        System.out.print("x before : "+ Integer.toString(tap_x)+"\n");
        int base_x=MainActivity.matrixDisplay.getPaddingLeft();
        for (int i=0; i<InputHandler.inputExpression.size();i++){
            try {
                int length = InputHandler.inputExpression.get(i).display.length()*MainActivity.fontWidth;
                if (tap_x<base_x+length/2){
                    InputHandler.cursorPos=i;
                    break;
                }else{
                    InputHandler.cursorPos=i+1;
                    base_x+=length;
                }
            }catch (Exception e){
                System.out.print("this token no display \n");
                continue;
            }

        }
        locate(InputHandler.cursorPos);

    }

    public static void locate(int cursorpos){
        int x=MainActivity.matrixDisplay.getPaddingLeft();



        for (int i=0; i<Math.min(cursorpos,InputHandler.inputExpression.size()); i++){
            try{
                x+=InputHandler.inputExpression.get(i).display.length() * MainActivity.fontWidth;
            }
            catch (Exception e){
                System.out.print("no expression\n");
            }
        }
        MainActivity.cursor.setPadding(x,MainActivity.matrixDisplay.getPaddingTop(),0,0);
        hideCursor();
    }
}
