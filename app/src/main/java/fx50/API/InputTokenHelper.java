package fx50.API;

/**
 * Contains useful resources for {@link InputToken}
 */
public class InputTokenHelper {
    public static String[] greekAlphabet = {
            "alpha",
            "beta",
            "gamma",
            "delta",
            "epsilon",
            "zeta",
            "eta",
            "theta",
            "iota",
            "kappa",
            "lambda",
            "mu",
            "nu",
            "xi",
            "omicron",
            "pi",
            "rho",
            "sigma_final",
            "sigma",
            "tau",
            "upsilon",
            "phi",
            "chi",
            "psi",
            "omega"
    };

    private static int alphaCodePointU = 0x0391;
    private static int alphaCodePointL = 0x03B1;

    public static String getGreekNameFromUnicode(int greekUnicodePoint) {
        int range = 25;
        if (greekUnicodePoint >= alphaCodePointU && greekUnicodePoint < alphaCodePointU + range) {
            //Uppercase Greek
            String letter = greekAlphabet[greekUnicodePoint - alphaCodePointU];
            letter = letter.substring(0, 1).toUpperCase() + letter.substring(1);
            return letter;
        } else if (greekUnicodePoint >= alphaCodePointL && greekUnicodePoint < alphaCodePointL + range) {
            //Lowercase Greek
            return greekAlphabet[greekUnicodePoint - alphaCodePointL];
        } else {
            return null;
        }
    }

    public static String getGreekUnicodeCharacterFromName(String greekName) {
        char firstLetter = greekName.charAt(0);
        boolean isUppercase = (firstLetter >= 0x0041 && firstLetter <= 0x005A);
        int greekIndex = indexOfGreek(greekName.toLowerCase());
        if (greekIndex > -1)
            return Character.toString((char) ((isUppercase ? alphaCodePointU : alphaCodePointL) + greekIndex));
        else
            return greekName;
    }

    private static int indexOfGreek(String greekName) {
        int length = greekAlphabet.length;
        for (int i = 0; i < length; i++) {
            if (greekName.equals(greekAlphabet[i]))
                return i;
        }
        return -1;
    }

    private static String fromUnicode(int...unicodePoints) {
        String returnString = "";
        for (int unicodePoint : unicodePoints) {
            returnString += Character.toString((char) unicodePoint);
        }
        return returnString;
    }
}
