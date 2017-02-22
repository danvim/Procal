package dcheungaa.procal.Func;

import android.graphics.Color;
import android.os.Environment;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import dcheungaa.procal.CalcBtn;
import dcheungaa.procal.InputHandler;
import dcheungaa.procal.KeyPad_init;
import dcheungaa.procal.MainActivity;
import dcheungaa.procal.R;

/**
 * Created by Bryan on 23/1/2017.
 */


public class FuncEdit {

    public static String funcTitle = "";
    public static String funcDesc = "";
    public static String funcContent = "";
    public static File funcFile;

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
                            null, MainActivity.context.getResources().getDrawable(R.drawable.ic_more_dark), null, null);
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
                            null, MainActivity.context.getResources().getDrawable(R.drawable.ic_more), null, null);
            } else if (calcButton.getId() == KeyPad_init.keypadButtons.indexOf("function")){
                calcButton.mainButton.setText("FUNC");

            }
        }
        InputHandler.refreshState();
    }

    public static void openEditArea(){
        HorizontalScrollView hsv = MainActivity.horizontalScrollView;
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, 0f);
        hsv.setLayoutParams(param);
    }

    public static void closeEditArea(){
        HorizontalScrollView hsv = MainActivity.horizontalScrollView;
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
        hsv.setLayoutParams(param);
    }

    public static void newlyAddedFunc(Boolean importing, boolean isDraft){
        String filename = funcTitle + ".procal";
        funcFile = new File(Environment.getExternalStorageDirectory() + "/Procal/User/", filename);
        String descriptionPart = "";
        String[] descriptionLines = funcDesc.split("\n");
        for (String descriptionLine: descriptionLines) {
            descriptionPart += " * " + descriptionLine + "\n";
        }
        String detailedContent = importing ? funcContent :
                        ("/**\n" +
                        " * "+funcTitle+"\n" +
                        descriptionPart +
                        (isDraft ? " * @draft\n" : "") +
                        " * \n" +
                        " */\n" +
                        funcContent);

        try {
            //FileOutputStream outputStream = MainActivity.context.openFileOutput(filename, MainActivity.context.MODE_PRIVATE);
            FileOutputStream outputStream = new FileOutputStream(funcFile);
            outputStream.write(detailedContent.getBytes());
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //FuncItem funcItem = new FuncItem(funcTitle, funcDesc, funcContent, funcFile);
        //FuncActivity.funcItemList.add(funcItem);
        discardAddFunc();
    }

    public static void discardAddFunc(){
        FuncEdit.funcTitle = "";
        FuncEdit.funcDesc = "";
        FuncEdit.funcContent = "";
        FuncEdit.funcFile = null;
    }
}
