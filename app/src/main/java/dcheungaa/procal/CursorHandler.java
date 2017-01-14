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

    private static int cursorIndex=0;     //index of cursor in display string (not index of token)*/
    private static long shine_interval = 1000;
    private static TextView cursor;
    private static Timer cursorTimer=new Timer();

    public static void hideCursor(){
        //MainActivity.set_Cursor_Visibility(cursor,View.INVISIBLE);
        cursorTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                showCursor();
            }
        }, shine_interval);
    }

    public static void showCursor(){
        //MainActivity.set_Cursor_Visibility(cursor,View.VISIBLE);
        cursorTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                hideCursor();
            }
        }, shine_interval);
    }

    public static int findCursorIndex(){
        cursorIndex=0;
        for (int i = 0; i<InputHandler.cursorPos; i++){
            cursorIndex+=InputHandler.inputExpression.get(i).display.length();
        }
        return  cursorIndex;
    }

    /*
    public static void locate(int x, int y, int ScrollX) {
        cursor = MainActivity.cursor;
        x = x+ScrollX;
        int X=0;
        int i=0;
        while (true) {
            if (i < InputHandler.inputExpression.size())
                X += InputHandler.inputExpression.get(i).display.length() * MainActivity.fontWidth;
            else {
                //InputHandler.cursorPos = i;
                cursor.setX(X - ScrollX);
                break;
            }
            i++;
            if (X > x) {
                //InputHandler.cursorPos = i;
                cursor.setX(X - InputHandler.inputExpression.get(i).display.length() * MainActivity.fontWidth);
                break;
            }
        }

    }*/

    public static void locate(int cursorpos){
        int x=MainActivity.matrixDisplay.getPaddingLeft();
        for (int i=0; i<=Math.min(cursorpos,InputHandler.inputExpression.size()); i++){
            try{
                x+=InputHandler.inputExpression.get(i).display.length() * MainActivity.fontWidth;
            }
            catch (Exception e){
                System.out.print("no expression\n");
            }
        }

        //int padding = x-MainActivity.scrollView.getScrollX();
        MainActivity.cursor.setPadding(x,MainActivity.matrixDisplay.getPaddingTop(),0,0);
        //System.out.print("cursor.X = "+Integer.toString(x)+" - "+Integer.toString(MainActivity.scrollView.getScrollX())+"\n="+Integer.toString(x-MainActivity.scrollView.getScrollX())+"\n");
        //System.out.print("cursor left = "+ Integer.toString(MainActivity.cursor.getLeft())+"\n");
    }
}
