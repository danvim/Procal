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
            "sigma",
            "sigma",
            "tau",
            "upsilon",
            "phi",
            "chi",
            "psi",
            "omega"
    };

    public static String getGreekNameFromUnicode(int greekUnicodePoint) {
        int alphaCodePointU = 0x0391;
        int alphaCodePointL = 0x03B1;
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
}
