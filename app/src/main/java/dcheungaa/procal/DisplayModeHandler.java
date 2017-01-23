package dcheungaa.procal;

/**
 * Created by Administrator on 21/1/2017.
 */

public class DisplayModeHandler {

    public static Boolean displayMode = false;

    public static void handle(String id){
        if (displayMode){
            if (InputHandler.error) InputHandler.error = false;
            if (!"execute shift alpha recall store hyperbolic".contains(id)){
                //the following is normal button which will contribute next calculation
                InputHandler.allClearToken();
                displayMode = false;
                if ("x_inverse x_factorial x_cubed x_squared power memory_plus memory_minus multiply divide add subtract percent permutation combination".contains(id)){
                    //this will add ANS
                    InputHandler.inputToken("answer");
                }
                if (!CursorHandler.cursorVisible) {
                    CursorHandler.cursorVisible =true;
                    CursorHandler.blinkCursor();
                }
            }else{
                //TODO: add some special button id handler which have special effect in display mode, like d/c will convert fraction<->decimal
            }
        }
    }
}
