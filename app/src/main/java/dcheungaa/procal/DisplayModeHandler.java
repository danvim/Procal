package dcheungaa.procal;

/**
 * Created by Administrator on 21/1/2017.
 */

public class DisplayModeHandler {

    public static Boolean displayMode = false;

    public static void handle(String id){
        if (displayMode){

            if (!"execute".contains(id)){
                //the following is normal button which will contribute next calculation
                InputHandler.allClearToken();
                displayMode = false;
                if ("x_inverse x_cubed x_squared power memory_plus memory_minus multiply divide add subtract".contains(id)){
                    //this will add ANS
                    InputHandler.inputToken("answer");
                }
                if (!CursorHandler.cusorVisible) {CursorHandler.cusorVisible=true;CursorHandler.blinkCursor();}
            }else{
                //TODO: add some special button id handler which have special effect in display mode, like d/c will convert fraction<->decimal
            }
        }
    }
}
