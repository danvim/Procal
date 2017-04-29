package dcheungaa.procal;

import java.util.HashMap;
import java.util.Map;

import fx50.API.Color;
import fx50.API.InputToken;

import static fx50.API.InputTokenHelper.getGreekNameFromUnicode;

public class Tokens {
    public static Map<String, InputToken> inputTokensMap = new HashMap<>();

    public Tokens() {

        //Digits
        for (int i = 0; i < 10; i++) {
            inputTokensMap.put("" + i, new InputToken("" + i, "" + i, false));
        }

        //Uppercase Latin
        for (int i = 0x0041; i < 0x005B; i++) {
            String c = Character.toString((char) i);
            inputTokensMap.put("var_"+c, new InputToken("$"+c, c,
                    (c.matches("A|B|C|D|X|Y|M") ? Color.SPECIAL_VARIABLE : Color.LATIN_VARIABLE)
            ));
        }

        //Lowercase Latin
        for (int i = 0x0061; i < 0x007B; i++) {
            String c = Character.toString((char) i);
            inputTokensMap.put("var_"+c, new InputToken("$"+c, c, Color.LATIN_VARIABLE));
        }

        //Uppercase Greek
        for (int i = 0x0391; i < 0x03AA; i++) {
            if (i == 0x03A2) continue;
            String c = Character.toString((char) i);
            inputTokensMap.put("var_"+ getGreekNameFromUnicode(i), new InputToken("$"+getGreekNameFromUnicode(i), c, Color.GREEK_VARIABLE));
            MainActivity.vars.add("var_"+c);
        }

        //Lowercase Greek
        for (int i = 0x03B1; i < 0x03CA; i++) {
            if (i == 0x03C2) continue;
            String c = Character.toString((char) i);
            inputTokensMap.put("var_"+ getGreekNameFromUnicode(i), new InputToken("$"+getGreekNameFromUnicode(i), c, Color.GREEK_VARIABLE));
        }

        //Special tokens on normal keypad
        inputTokensMap.put("clear", new InputToken("ClrMemory", "ClrMemory", Color.COMMAND));

        //System.out.println("Token map has entries: " + inputTokensMap.size());
    }
    public static String fromUnicode(int... unicodePoints) {
        String returnString = "";
        for (int unicodePoint:unicodePoints) {
            returnString += Character.toString((char) unicodePoint);
        }
        return returnString;
    }

}
