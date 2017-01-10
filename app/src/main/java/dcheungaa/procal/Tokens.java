package dcheungaa.procal;

import java.util.HashMap;
import java.util.Map;

public class Tokens {
    public static Map<String, InputToken> inputTokensMap = new HashMap<>();

    public Tokens() {
        inputTokensMap.put("x_inverse", new InputToken("inverse", "º"));
        inputTokensMap.put("factorial", new InputToken("factorial", "!"));
        inputTokensMap.put("x_cubed", new InputToken("cubed", "³"));
        inputTokensMap.put("complex_fraction", new InputToken("over", "∕"));
        inputTokensMap.put("square_root", new InputToken("sqrt(", "√("));
        inputTokensMap.put("x_squared", new InputToken("squared", "²"));
        inputTokensMap.put("power", new InputToken("^(", "^("));
        inputTokensMap.put("log", new InputToken("log(", "^("));
        inputTokensMap.put("10_power_x", new InputToken("10^(", "⒑^("));
        inputTokensMap.put("natural_log", new InputToken("ln(", "ln("));
        inputTokensMap.put("exp_power_x", new InputToken("&exp^(", "ⅇ^("));
        inputTokensMap.put("exp", new InputToken("&exp", "ⅇ"));
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
        inputTokensMap.put("memory_plus", new InputToken("M+", "M+"));
        inputTokensMap.put("memory_minus", new InputToken("M-", "M-"));
        inputTokensMap.put("multiply", new InputToken("*", "×"));
        inputTokensMap.put("divide", new InputToken("/", "÷"));
        inputTokensMap.put("add", new InputToken("+", "+"));
        inputTokensMap.put("subtract", new InputToken("-", "−"));
        inputTokensMap.put("round", new InputToken("Rnd(", "Rnd("));
        inputTokensMap.put("random", new InputToken("Ran#", "Ran#"));
        inputTokensMap.put("dot", new InputToken(".", ".", false));

        //Digits
        for (int i = 0; i < 10; i++) {
            inputTokensMap.put("" + i, new InputToken("" + i, "" + i, false));
        }

        //Uppercase Latin
        for (int i = 0x0041; i < 0x005B; i++) {
            String c = Character.toString((char) i);
            inputTokensMap.put(c, new InputToken(c, c));
        }

        //Lowercase Latin
        for (int i = 0x0061; i < 0x007B; i++) {
            String c = Character.toString((char) i);
            inputTokensMap.put(c, new InputToken(c, c));
        }

        //Uppercase Greek
        for (int i = 0x0391; i < 0x03AA; i++) {
            if (i == 0x03A2) continue;
            String c = Character.toString((char) i);
            inputTokensMap.put(c, new InputToken(c, c));
        }

        //Lowercase Greek
        for (int i = 0x03B1; i < 0x03CA; i++) {
            if (i == 0x03C2) continue;
            String c = Character.toString((char) i);
            inputTokensMap.put(c, new InputToken(c, c));
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
                {"mu_N", "μ"},
        };
    }
}
