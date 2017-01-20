package fx50.CalcMath;

import org.nevec.rjm.BigDecimalMath;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;

import static fx50.CalcMath.CalcMath.simpleHCF;

/**
 * BigFraction
 */
public class BigFraction{
    private BigDecimal numerator;
    private BigDecimal denominator;
    private static int accuracy = 7;
    private static int brutethresold = 13;
    private ArrayList<Integer> digits = new ArrayList<>();
    private int digit = 0;

    private void inversion(BigDecimal decimal){
        if (decimal.compareTo(BigDecimal.ZERO) != 0){
            decimal = BigDecimal.ONE.divide(decimal, new MathContext(250, RoundingMode.HALF_UP));
            this.digits.add(decimal.intValue());
            if (decimal.setScale(accuracy,  BigDecimal.ROUND_HALF_UP)
                    .compareTo(new BigDecimal(decimal.intValue())) != 0){
                decimal = decimal.subtract(new BigDecimal(decimal.intValue()));
                inversion(decimal);
            }

        }
    }

    public BigFraction(int numerator, int denominator){
        if (denominator == 0){
            throw new IllegalArgumentException("Denominator cannot be zero.");
        }
        if (denominator < 0){
            numerator *= -1;
            denominator *= -1;
        }
        this.numerator = new BigDecimal(numerator);
        this.denominator = new BigDecimal(denominator);
    }

    public BigFraction(BigDecimal numerator, BigDecimal denominator){
        if ((!CalcMath.isInt(numerator)) || (!CalcMath.isInt(denominator))){
            throw new ArithmeticException("Numerator/Denominator must be int.");
        }
        if (denominator.compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalArgumentException("Denominator cannot be zero.");
        }
        if (denominator.compareTo(BigDecimal.ZERO) < 0){
            numerator = numerator.negate();
            denominator = denominator.negate();
        }
        this.numerator = numerator;
        this.denominator = denominator;
    }

    public BigFraction (BigDecimal decimal){
        long numerator;
        long denominator;
        if (decimal.scale() >= brutethresold){ //By brute forcing
            this.digit = decimal.intValue();
            if (this.digit != 0){
                this.digits.add(0);
                this.digits.add(digit);
                decimal = decimal.subtract(new BigDecimal(digit));
            }
            inversion(decimal);
            denominator = digits.remove(digits.size()-1);
            numerator = 1;
            while (digits.size() != 0){
                long temp = numerator + denominator * digits.remove(digits.size()-1);
                numerator = denominator;
                denominator = temp;
            }
            this.numerator = new BigDecimal(numerator);
            this.denominator = new BigDecimal(denominator);

        } else { //By natural conversion
            int exp = decimal.scale();
            numerator = decimal.multiply(new BigDecimal(Math.pow(10, exp))).intValue();
            denominator = (int) (Math.pow(10, exp));
            long factor = simpleHCF(numerator, denominator);
            this.numerator = new BigDecimal(numerator / factor);
            this.denominator = new BigDecimal(denominator / factor);
        }

        if (this.numerator.compareTo(BigDecimal.ZERO) < 0){
            this.numerator = this.numerator.negate();
            this.denominator = this.denominator.negate();
        }
    }

    public BigFraction simplify() {
        BigDecimal factor = new BigDecimal(simpleHCF(this.numerator.intValue(), this.denominator.intValue()));
        return new BigFraction(this.numerator.divide(factor), this.denominator.divide(factor));
    }

    public BigFraction negate(){
        return new BigFraction(this.numerator.negate(), this.denominator);
    }

    public BigFraction inverse(){
        if (this.numerator.compareTo(BigDecimal.ZERO) > 0) {
            return new BigFraction(this.denominator, this.numerator);
        } else if (this.numerator.compareTo(BigDecimal.ZERO) < 0){
            return new BigFraction(this.denominator.negate(), this.numerator.negate());
        } else {
            throw new ArithmeticException("Cannot inverse 0 fraction");
        }
    }

    public BigDecimal toDecimal(){
        return this.numerator.divide(this.denominator);
    }

    public BigFraction add(BigFraction frac){
        return (new BigFraction(this.numerator.multiply(frac.denominator).add(frac.numerator.multiply(this.denominator)),
                this.denominator.multiply(frac.denominator))).simplify();
    }

    public BigFraction subtract(BigFraction frac){
        return this.add(frac.negate());
    }

    public BigFraction multiply(BigFraction frac){
        return (new BigFraction(this.numerator.multiply(frac.numerator), this.denominator.multiply(frac.denominator))).simplify();
    }

    public BigFraction divide(BigFraction frac){
        return this.multiply(frac.inverse());
    }

    public BigDecimal pow(BigDecimal exp){
        return BigDecimalMath.pow(this.toDecimal().setScale(15, BigDecimal.ROUND_HALF_UP), exp.setScale(15, BigDecimal.ROUND_HALF_UP));
    }

    public String toImproperString(){
        return (this.Numerator() + " over " + this.Denominator());
    }

    public String toMixedString(){
        int a = this.numerator.divide(this.denominator, CalcMath.precision).intValue();
        if (a == 0){
            return this.toImproperString();
        } else {
            return(a + " and " +
                    this.numerator.remainder(this.denominator).intValue() + " over " +
                    this.denominator);
        }
    }

    public String toString(){
        return (this.numerator.divide(this.denominator, CalcMath.precision)).toPlainString();
    }

    public BigDecimal Numerator(){
        return this.numerator;
    }

    public BigDecimal Denominator(){
        return this.denominator;
    }

}
