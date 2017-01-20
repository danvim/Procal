package dcheungaa.procal;

import java.util.HashMap;
import java.util.Map;

import fx50.API.Color;
import fx50.API.InputToken;
import fx50.API.InputTokenHelper;

public class Tokens {
    public static Map<String, InputToken> inputTokensMap = new HashMap<>();

    public Tokens() {

        /*
        inputTokensMap.put(key.id, new InputToken(lexable, display));
        */

        /*inputTokensMap.put("x_inverse", new InputToken("inverse", "º"));
        inputTokensMap.put("x_factorial", new InputToken("!", "!"));
        inputTokensMap.put("x_cubed", new InputToken("cubed", "³"));
        inputTokensMap.put("cube_root", new InputToken("3root(", "³√("));
        inputTokensMap.put("complex_fraction", new InputToken("over", "∕"));
        inputTokensMap.put("square_root", new InputToken("sqrt(", "√("));
        inputTokensMap.put("x_squared", new InputToken("squared", "²"));
        inputTokensMap.put("power", new InputToken("^(", "^("));
        inputTokensMap.put("x_root", new InputToken("root(", "ˣ√("));
        inputTokensMap.put("log", new InputToken("log(", "log("));
        inputTokensMap.put("10_power_x", new InputToken("10^(", "⒑^("));
        inputTokensMap.put("natural_log", new InputToken("ln(", "ln("));
        inputTokensMap.put("exp_power_x", new InputToken("&exp^(", "ⅇ^("));
        inputTokensMap.put("exp", new InputToken("&exp", "ⅇ", Color.CONSTANT));
        inputTokensMap.put("negation", new InputToken("(-)", "-"));
        inputTokensMap.put("sexagesimal", new InputToken("'", "˚"));
        inputTokensMap.put("sine", new InputToken("sin(", "sin("));
        inputTokensMap.put("arc_sine", new InputToken("asin(", "sinº("));
        inputTokensMap.put("hyp_sine", new InputToken("sinh(", "sinh("));
        inputTokensMap.put("arc_hyp_sine", new InputToken("asinh(", "sinhº("));
        inputTokensMap.put("cosine", new InputToken("cos(", "cos("));
        inputTokensMap.put("arc_cosine", new InputToken("acos(", "cosº("));
        inputTokensMap.put("hyp_cosine", new InputToken("cosh(", "cosh("));
        inputTokensMap.put("arc_hyp_cosine", new InputToken("acosh(", "coshº("));
        inputTokensMap.put("tangent", new InputToken("tan(", "tan("));
        inputTokensMap.put("arc_tangent", new InputToken("atan(", "tanº("));
        inputTokensMap.put("hyp_tangent", new InputToken("tanh(", "tanh("));
        inputTokensMap.put("arc_hyp_tangent", new InputToken("atanh(", "tanhº("));
        inputTokensMap.put("left_parenthesis", new InputToken("(", "("));
        inputTokensMap.put("percent", new InputToken("%", "%"));
        inputTokensMap.put("right_parenthesis", new InputToken(")", ")"));
        inputTokensMap.put("absolute", new InputToken("Abs(", "Abs("));
        inputTokensMap.put("comma", new InputToken(",", ","));
        inputTokensMap.put("semicolon", new InputToken(";", ";"));
        inputTokensMap.put("memory_plus", new InputToken("M+", "M+", Color.MEMORY));
        inputTokensMap.put("memory_minus", new InputToken("M-", "M-", Color.MEMORY));
        inputTokensMap.put("permutation", new InputToken("P", "ℙ"));
        inputTokensMap.put("combination", new InputToken("C", "ℂ"));
        inputTokensMap.put("polar", new InputToken("Pol(", "Pol("));
        inputTokensMap.put("rectangular", new InputToken("Rec(", "Rec("));
        inputTokensMap.put("multiply", new InputToken("*", "×"));
        inputTokensMap.put("divide", new InputToken("/", "÷"));
        inputTokensMap.put("add", new InputToken("+", "+"));
        inputTokensMap.put("subtract", new InputToken("-", "−"));
        inputTokensMap.put("round", new InputToken("Rnd(", "Rnd("));
        inputTokensMap.put("random", new InputToken("Ran#", "Ran#"));
        inputTokensMap.put("dot", new InputToken(".", ".", false));
        inputTokensMap.put("exponential", new InputToken("E", "ᴇ", false));
        inputTokensMap.put("pi", new InputToken("&pi", "π", Color.CONSTANT));
        inputTokensMap.put("answer", new InputToken("Ans", "Ans"));
*/
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
            //MainActivity.vars.add(Arrays.asList("var_"+c));
        }

        //Lowercase Latin
        for (int i = 0x0061; i < 0x007B; i++) {
            String c = Character.toString((char) i);
            inputTokensMap.put("var_"+c, new InputToken("$"+c, c, Color.LATIN_VARIABLE));
            //MainActivity.vars.add(Arrays.asList("var_"+c));
        }

        //Uppercase Greek
        int greekIndex = 0;
        for (int i = 0x0391; i < 0x03AA; i++) {
            if (i == 0x03A2) continue;
            String c = Character.toString((char) i);
            inputTokensMap.put("var_"+ InputTokenHelper.greekAlphabet[greekIndex++], new InputToken("$"+c, c, Color.GREEK_VARIABLE));
            //MainActivity.vars.add(Arrays.asList("var_"+c));
            MainActivity.vars.add("var_"+c);
        }

        //Lowercase Greek
        greekIndex = 0;
        for (int i = 0x03B1; i < 0x03CA; i++) {
            if (i == 0x03C2) continue;
            String c = Character.toString((char) i);
            inputTokensMap.put("var_"+ InputTokenHelper.greekAlphabet[greekIndex++], new InputToken("$"+c, c, Color.GREEK_VARIABLE));
            //MainActivity.vars.add(Arrays.asList("var_"+c));
        }

        //Constants
        String[][] constants = {
                {"pi", "π"},
                {"exp", "ⅇ"},
                {"m_p", "mₚ"},
                {"m_n", "mₙ"},
                {"m_e", "me"},
                {"m_mu", "mµ"},
                {"a_0", "a₀"},
                {"h", "ℎ"},
                {"mu_N", "μɴ"},
                {"mu_B", "μʙ"},
                {"h_stroke", "ℏ"},
                {"alpha", "α"},
                {"r_e", "re"},
                {"lambda_c", "λc"},
                {"gamma_p", "γₚ"},
                {"lambda_cp", "λcₚ"},
                {"lambda_cn", "λcₙ"},
                {"R_inf", "R∞"},
                {"u", "υ"},
                {"mu_p", "μₚ"},
                {"mu_e", "μe"},
                {"mu_n", "μₙ"},
                {"mu_mu", "μµ"},
                {"F", "ℱ"},
                {"e", "ℯ"},
                {"N_A", "Nᴀ"},
                {"k", "ƙ"},
                {"V_m", "Vm"},
                {"R", "ℛ"},
                {"c_0", "c₀"},
                {"c_1", "c₁"},
                {"c_2", "c₂"},
                {"sigma", "σ"},
                {"epsilon_0", "ℇ₀"},
                {"mu_0", "μ₀"},
                {"phi_0", "φ₀"},
                {"g", "g"},
                {"G_0", "G₀"},
                {"Z_0", "Z₀"},
                {"t", "t"},
                {"G", "G"},
                {"atm", "atm"}
        };
        for (String[] constant : constants) {
            inputTokensMap.put(constant[0], new InputToken("&" + constant[0], constant[1], Color.CONSTANT));
        }

        System.out.println("Token map has entries: " + inputTokensMap.size());
    }
    public static String fromUnicode(int... unicodePoints) {
        String returnString = "";
        for (int unicodePoint:unicodePoints) {
            returnString += Character.toString((char) unicodePoint);
        }
        return returnString;
    }

}
