package dcheungaa.procal;

import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 13/1/2017.
 */

public class CursorHandler {

    private static TextView cursor;

    public static void blinkCursor(){
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                int blinkInterval = 500;    //in milissegunds
                try{Thread.sleep(blinkInterval);} catch (Exception e) {}
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(MainActivity.cursor.getVisibility() == View.VISIBLE){
                            MainActivity.cursor.setVisibility(View.INVISIBLE);
                        }else{
                            MainActivity.cursor.setVisibility(View.VISIBLE);
                        }
                        blinkCursor();
                    }
                });
            }
        }).start();
    }

    public static void toggleVisibility(){
        cursor.setVisibility(cursor.getVisibility() == View.VISIBLE ? View.VISIBLE : View.INVISIBLE);
    }

    public static void hide(){
        cursor.setVisibility(View.INVISIBLE);
    }

    public static void show(){
        cursor.setVisibility(View.VISIBLE);
    }

    public static void locateCursorPos(int tap_x) {
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

    public static int locate(int cursorpos){
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


        final int delta = x - MainActivity.scrollView.getScrollX() - MainActivity.scrollView.getWidth() + MainActivity.fontWidth;
        if (delta>0){
            final HorizontalScrollView fhsv=MainActivity.scrollView;
            MainActivity.scrollView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {                     //<--set listener to the btn
                @Override
                public void onGlobalLayout() {                     //<--define listener function
                    fhsv.scrollTo(fhsv.getScrollX() + delta, MainActivity.scrollView.getScrollY());
                    fhsv.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
            });
        }
        else {
            final int delta_ = x - MainActivity.scrollView.getScrollX() - MainActivity.fontWidth;
            if (delta_<0){
                final HorizontalScrollView fhsv=MainActivity.scrollView;
                MainActivity.scrollView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {                     //<--set listener to the btn
                    @Override
                    public void onGlobalLayout() {                     //<--define listener function
                        fhsv.scrollTo(fhsv.getScrollX() + delta_, MainActivity.scrollView.getScrollY());
                        fhsv.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }
                });
            }

        }

        return x;
    }
}
