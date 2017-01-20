package fx50.CalcMath;

import fx50.CalculatorHelper;
import org.nevec.rjm.BigDecimalMath;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static fx50.CalculatorHelper.*;

/**
 * Fn
 */
public class Fn {
    //TODO change setScale to sigfig

    public static BigDecimal isInt(ArrayList<BigDecimal> bigDecimals) {
        BigDecimal n = bigDecimals.get(0);
        return new BigDecimal(n.compareTo(n.setScale(0, BigDecimal.ROUND_HALF_UP)) == 0 ? 1 : 0);
    }

    public static BigDecimal sqrt(ArrayList<BigDecimal> bigDecimals) {
        if (bigDecimals.get(0).compareTo(BigDecimal.ZERO) == 0)
            return new BigDecimal(0);
        return BigDecimalMath.sqrt(bigDecimals.get(0).setScale(200, BigDecimal.ROUND_HALF_UP)).setScale(15, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal Rnd(ArrayList<BigDecimal> bigDecimals) {
        return bigDecimals.get(0).setScale(0, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal Rnd_Down(ArrayList<BigDecimal> bigDecimals) {
        return bigDecimals.get(0).setScale(0, BigDecimal.ROUND_DOWN);
    }

    public static BigDecimal Rnd_Up(ArrayList<BigDecimal> bigDecimals) {
        return bigDecimals.get(0).setScale(0, BigDecimal.ROUND_UP);
    }

    public static BigDecimal Floor(ArrayList<BigDecimal> bigDecimals) {
        return bigDecimals.get(0).setScale(0, BigDecimal.ROUND_FLOOR);
    }

    public static BigDecimal Ceiling(ArrayList<BigDecimal> bigDecimals) {
        return bigDecimals.get(0).setScale(0, BigDecimal.ROUND_CEILING);
    }

    public static BigDecimal Abs(ArrayList<BigDecimal> bigDecimals) {
        return bigDecimals.get(0).abs();
    }

    public static BigDecimal Ran(ArrayList<BigDecimal> bigDecimals) {
        return BigDecimal.valueOf(Math.random()).multiply(bigDecimals.get(0)).setScale(3, BigDecimal.ROUND_DOWN);
    }

    public static BigDecimal sin(ArrayList<BigDecimal> bigDecimals) {
        return BigDecimalMath.sin(bigDecimals.get(0).setScale(200, BigDecimal.ROUND_HALF_UP)).setScale(15, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal cos(ArrayList<BigDecimal> bigDecimals) {
        return BigDecimalMath.cos(bigDecimals.get(0).setScale(200, BigDecimal.ROUND_HALF_UP)).setScale(15, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal tan(ArrayList<BigDecimal> bigDecimals) {
        return BigDecimalMath.tan(bigDecimals.get(0).setScale(15, BigDecimal.ROUND_HALF_UP));
    }

    public static BigDecimal asin(ArrayList<BigDecimal> bigDecimals) {
        //return BigDecimalMath.asin(bigDecimals.get(0).setScale(300, BigDecimal.ROUND_HALF_UP)).setScale(15, BigDecimal.ROUND_HALF_UP);
        //asin(x) = 2 * atan(x/(1+sqrt(1-x^2)))
        BigDecimal x = bigDecimals.get(0).setScale(200, BigDecimal.ROUND_HALF_UP);
        BigDecimal y;
        BigDecimal a = BigDecimal.ONE.subtract(x.multiply(x)).setScale(200, BigDecimal.ROUND_HALF_UP);
        if (a.compareTo(BigDecimal.ZERO) == 0){
            y = BigDecimal.ONE;
        } else {
            y = BigDecimal.ONE.add(BigDecimalMath.sqrt(a));
        }
        return (BigDecimal.ONE.add(BigDecimal.ONE)).multiply(BigDecimalMath.atan(x.divide(y, CalcMath.precision)));
    }

    public static BigDecimal acos(ArrayList<BigDecimal> bigDecimals) {
        //return BigDecimalMath.acos(bigDecimals.get(0).setScale(300, BigDecimal.ROUND_HALF_UP)).setScale(15, BigDecimal.ROUND_HALF_UP);
        //acos(x) =  2 * atan(sqrt(1-x^2) / (1+x))
        BigDecimal x = bigDecimals.get(0).setScale(200, BigDecimal.ROUND_HALF_UP);
        BigDecimal a = BigDecimal.ONE.subtract(x.multiply(x)).setScale(200, BigDecimal.ROUND_HALF_UP);
        if (x.compareTo(BigDecimal.ZERO.subtract(BigDecimal.ONE)) == 0){
            return  BigDecimalMath.pi(new MathContext(20));
        }
        if (a.compareTo(BigDecimal.ZERO) == 0){
            return BigDecimal.ZERO;
        } else {
            return (BigDecimal.ONE.add(BigDecimal.ONE)).multiply(BigDecimalMath.atan(BigDecimalMath.sqrt(a).divide(BigDecimal.ONE.add(x), new MathContext(200, RoundingMode.HALF_UP)).setScale(200, BigDecimal.ROUND_HALF_UP)));
        }
     }

    public static BigDecimal atan(ArrayList<BigDecimal> bigDecimals) {
        return BigDecimalMath.atan(bigDecimals.get(0).setScale(15, BigDecimal.ROUND_HALF_UP));
    }

    public static BigDecimal sinh(ArrayList<BigDecimal> bigDecimals) {
        return BigDecimalMath.sinh(bigDecimals.get(0).setScale(15, BigDecimal.ROUND_HALF_UP));
    }

    public static BigDecimal cosh(ArrayList<BigDecimal> bigDecimals) {
        return BigDecimalMath.cosh(bigDecimals.get(0).setScale(15, BigDecimal.ROUND_HALF_UP));
    }

    public static BigDecimal tanh(ArrayList<BigDecimal> bigDecimals) {
        return BigDecimalMath.tanh(bigDecimals.get(0).setScale(15, BigDecimal.ROUND_HALF_UP));
    }

    public static BigDecimal asinh(ArrayList<BigDecimal> bigDecimals) {
        return BigDecimalMath.asinh(bigDecimals.get(0).setScale(15, BigDecimal.ROUND_HALF_UP));
    }

    public static BigDecimal acosh(ArrayList<BigDecimal> bigDecimals) {
        return BigDecimalMath.acosh(bigDecimals.get(0).setScale(15, BigDecimal.ROUND_HALF_UP));
    }

    public static BigDecimal atanh(ArrayList<BigDecimal> bigDecimals) {
        return BigDecimalMath.log(BigDecimal.ONE
                .add(bigDecimals.get(0))
                .setScale(16, BigDecimal.ROUND_HALF_UP)
                .divide(BigDecimal.ONE.subtract(bigDecimals.get(0)), BigDecimal.ROUND_HALF_UP)
        ).divide(new BigDecimal(2), BigDecimal.ROUND_HALF_UP).setScale(15, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal log(ArrayList<BigDecimal> bigDecimals) {
        if (bigDecimals.size() == 1) {
            bigDecimals.add(bigDecimals.get(0));
            bigDecimals.set(0, BigDecimal.TEN);
        }
        return BigDecimalMath.log(bigDecimals.get(1).setScale(16, BigDecimal.ROUND_HALF_UP))
                .divide(BigDecimalMath.log(bigDecimals.get(0).setScale(15, BigDecimal.ROUND_HALF_UP)), BigDecimal.ROUND_HALF_UP)
                .setScale(15, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal ln(ArrayList<BigDecimal> bigDecimals) {
        return BigDecimalMath.log(bigDecimals.get(0).setScale(16, BigDecimal.ROUND_HALF_UP)).setScale(15, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal HCF(ArrayList<BigDecimal> bigDecimals) {
        if (bigDecimals.size() == 1)
            return bigDecimals.get(0);
        if (bigDecimals.size() == 2)
            return CalcMath.simpleHCF(bigDecimals.get(0), bigDecimals.get(1));
        int halfSize = (bigDecimals.size()-1)/2;
        return CalcMath.simpleHCF(
                HCF(new ArrayList<>(bigDecimals.subList(0, halfSize))),
                HCF(new ArrayList<>(bigDecimals.subList(halfSize, bigDecimals.size())))
        );
    }

    public static BigDecimal LCM(ArrayList<BigDecimal> bigDecimals) {
        if (bigDecimals.size() == 1)
            return bigDecimals.get(0);
        if (bigDecimals.size() == 2)
            return CalcMath.simpleLCM(bigDecimals.get(0), bigDecimals.get(1));
        int halfSize = (bigDecimals.size()-1)/2;
        return CalcMath.simpleLCM(
                LCM(new ArrayList<>(bigDecimals.subList(0, halfSize))),
                LCM(new ArrayList<>(bigDecimals.subList(halfSize, bigDecimals.size())))
        );
    }

    public static BigDecimal Pol(ArrayList<BigDecimal> bigDecimals) {
        if (bigDecimals.size() < 2)
            throw new ArithmeticException("Pol() function requires 2 parameters: x, y");
        BigDecimal x = bigDecimals.get(0);
        BigDecimal y = bigDecimals.get(1);
        ArrayList<BigDecimal> sqrtArray = new ArrayList<>();
        sqrtArray.add(x.pow(2).add(y.pow(2)));
        BigDecimal r = sqrt(sqrtArray);
        ArrayList<BigDecimal> atanArray = new ArrayList<>();
        atanArray.add(x.divide(y, new MathContext(200, RoundingMode.HALF_UP)));
        BigDecimal theta = atan(atanArray);
        VariableMap.setValue("X", r);
        VariableMap.setValue("Y", theta);
        return r;
    }

    public static BigDecimal Rec(ArrayList<BigDecimal> bigDecimals) {
        if (bigDecimals.size() < 2)
            throw new ArithmeticException("Rec() function requires 2 parameters: r, theta");
        BigDecimal r = bigDecimals.get(0);
        BigDecimal theta = bigDecimals.get(1);
        ArrayList<BigDecimal> sinArray = new ArrayList<>();
        sinArray.add(theta);
        BigDecimal x = sin(sinArray).multiply(r);
        ArrayList<BigDecimal> cosArray = new ArrayList<>();
        cosArray.add(theta);
        BigDecimal y = cos(cosArray).multiply(r);
        VariableMap.setValue("X", x);
        VariableMap.setValue("Y", y);
        return x;
    }

}
