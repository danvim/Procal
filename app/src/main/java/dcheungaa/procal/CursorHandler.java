package dcheungaa.procal;

import android.os.Handler;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

/**
 * Created by Administrator on 13/1/2017.
 */

public class CursorHandler {

    private static TextView cursor;
    public static Boolean cursorVisible = true;

    public static void blinkCursor(){
        final Handler handler = new Handler();
        cursorVisible = true;
        new Thread(new Runnable() {
            @Override
            public void run() {

                int blinkInterval = 500;    //in milliseconds
                try{Thread.sleep(blinkInterval);} catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (!cursorVisible) return;
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
        MainActivity.cursor.setVisibility(View.INVISIBLE);
        cursorVisible = false;
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
            } catch (Exception e){
                System.out.print("this token no display \n");
            }

        }
        locate(InputHandler.cursorPos);

    }

    public static int locate(int cursorPos){
        int x=MainActivity.matrixDisplay.getPaddingLeft();


        for (int i=0; i<Math.min(cursorPos,InputHandler.inputExpression.size()); i++){
            try{
                x+=InputHandler.inputExpression.get(i).display.length() * MainActivity.fontWidth;
            }
            catch (Exception e){
                System.out.print("no expression\n");
            }
        }
        MainActivity.cursor.setPadding(x,MainActivity.matrixDisplay.getPaddingTop(),0,0);


        final int delta = x - MainActivity.horizontalScrollView.getScrollX() - MainActivity.horizontalScrollView.getWidth() + MainActivity.fontWidth;
        if (delta>0){
            final HorizontalScrollView fhsv=MainActivity.horizontalScrollView;
            MainActivity.horizontalScrollView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {                     //<--set listener to the btn
                @Override
                public void onGlobalLayout() {                     //<--define listener function
                    fhsv.scrollTo(fhsv.getScrollX() + delta, MainActivity.horizontalScrollView.getScrollY());
                    fhsv.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
            });
        }
        else {
            final int delta_ = x - MainActivity.horizontalScrollView.getScrollX() - MainActivity.fontWidth;
            if (delta_<0){
                final HorizontalScrollView fhsv=MainActivity.horizontalScrollView;
                MainActivity.horizontalScrollView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {                     //<--set listener to the btn
                    @Override
                    public void onGlobalLayout() {                     //<--define listener function
                        fhsv.scrollTo(fhsv.getScrollX() + delta_, MainActivity.horizontalScrollView.getScrollY());
                        fhsv.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }
                });
            }

        }

        return x;
    }
}
