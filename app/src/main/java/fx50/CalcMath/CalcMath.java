package fx50.CalcMath;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * CalcMath
 */
public class CalcMath {
    public static final MathContext precision = new MathContext(16, RoundingMode.HALF_UP);

    public static BigDecimal factorial(BigDecimal n) {
        if (n.compareTo(new BigDecimal(0)) == 0) return new BigDecimal(1);
        return n.multiply(factorial(n.subtract(new BigDecimal(1))));
    }

    public static BigDecimal inverse(BigDecimal n) {
        return BigDecimal.ONE.divide(n, n.precision(), BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal permutation(BigDecimal n, BigDecimal r) {
        n = n.round(new MathContext(0));
        r = r.round(new MathContext(0));
        return factorial(n).divide(factorial(n.subtract(r)), precision);
    }

    public static BigDecimal combination(BigDecimal n, BigDecimal r) {
        BigDecimal y = r.round(new MathContext(0));
        return permutation(n, r).divide(factorial(y), precision);
    }

    public static BigDecimal sigfig(BigDecimal bigDecimal, int significantDigits) {
        return bigDecimal.round(new MathContext(significantDigits, RoundingMode.HALF_UP)).stripTrailingZeros();
    }

    public static boolean isInt (BigDecimal n) {
        return n.setScale(0, RoundingMode.HALF_UP).compareTo(n) == 0;
        //return (n.setScale(0, BigDecimal.ROUND_DOWN)).equals(n);
    }

    public static BigDecimal simpleHCF (BigDecimal a, BigDecimal b) {
        BigDecimal c = BigDecimal.ONE;
        if (a.compareTo(b) == -1) {
            c = a;
            a = b;
            b = c;
        }
        while (c.compareTo(BigDecimal.ZERO) != 0) {
            c = a.remainder(b);
            a = b;
            b = c;
        }
        return a;
    }

    public static long simpleHCF (long a, long b) {
        long c = 1;
        if (a < b) {
            c = a;
            a = b;
            b = c;
        }
        while (c != 0) {
            c = a % b;
            a = b;
            b = c;
        }
        return a;
    }

    public static BigDecimal simpleLCM (BigDecimal a, BigDecimal b) {
        return a.multiply(b).divide(CalcMath.simpleHCF(a, b), new MathContext(200, RoundingMode.HALF_UP));
    }

    public static long simpleLCM (long a, long b) {
        return a*b/CalcMath.simpleHCF(a, b);
    }

    public static void listAllConstants() {
        for (Constants c: Constants.values()) {
            int indentation = 12;
            System.out.print(c.name());
            for (int i = c.name().length() + 1; i < 12; i++) {
                System.out.print(" ");
            }
            System.out.println(c.name);
            for (int i = 1; i < 12; i++) {
                System.out.print(" ");
            }
            System.out.println(c.value + " " + c.unit);
        }
    }

}