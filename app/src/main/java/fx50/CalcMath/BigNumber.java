package fx50.CalcMath;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * This class stores a Big Decimal for value evaluation and contains a fraction state with fraction methods
 */
public class BigNumber{

    private BigDecimal decimalVal;
    private BigFraction fractionVal;
    private boolean isFractionState = true;

    BigNumber (String in) {
        decimalVal = new BigDecimal(in);
    }

    BigNumber (int in) {
        decimalVal = new BigDecimal(in);
    }

    private BigDecimal convertFromFractionString(String string) {
        if (string.matches("(\\d+ *over *){1,2}\\d+")) {
            string = string.replaceAll(" *", "");
            String[] sections = string.split("over");
            BigDecimal result = new BigDecimal(0);
            if (sections.length == 3) {
                //Mixed number
                result = new BigDecimal(sections[0]).add(
                        new BigDecimal(sections[1]).divide(new BigDecimal(sections[2]), new MathContext(20, RoundingMode.HALF_UP))
                );
            } else if (sections.length == 2) {
                //Improper fraction
                result = new BigDecimal(sections[1]).divide(new BigDecimal(sections[2]), new MathContext(20, RoundingMode.HALF_UP));
            }
            return result;
        } else return new BigDecimal(string);
    }
}
