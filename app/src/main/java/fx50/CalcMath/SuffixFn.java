package fx50.CalcMath;

import java.math.BigDecimal;
import java.util.ArrayList;

import static fx50.CalcMath.CalcMath.precision;

/**
 * Fn
 */
public class SuffixFn {
    public static BigDecimal squared(ArrayList<BigDecimal> bigDecimals) {
        return bigDecimals.get(0).pow(2);
    }

    public static BigDecimal cubed(ArrayList<BigDecimal> bigDecimals) {
        return bigDecimals.get(0).pow(3);
    }

    public static BigDecimal inverse(ArrayList<BigDecimal> bigDecimals) {
        return BigDecimal.ONE.divide(bigDecimals.get(0), precision);
    }

    public static BigDecimal factorial(ArrayList<BigDecimal> bigDecimals) {
        if (!CalcMath.isInt(bigDecimals.get(0)) || bigDecimals.get(0).compareTo(new BigDecimal(0)) < 0) {
            throw new ArithmeticException("Number must be non-negative integer");
        }
        return CalcMath.factorial(bigDecimals.get(0));
    }
}
