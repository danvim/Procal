package fx50.nodes;

import fx50.API.InputToken;
import fx50.CalcMath.BigFraction;
import org.nevec.rjm.BigDecimalMath;

import java.math.BigDecimal;
import java.util.List;

/**
 * Power Node
 */
public class PowerNode implements CalculatorNode {
    private final CalculatorNode left;
    private final CalculatorNode right;

    public PowerNode(CalculatorNode left, CalculatorNode right) {
        this.left = left;
        this.right = right;
    }

    public BigDecimal evaluate() {
        BigDecimal lft = left.evaluate().setScale(15, BigDecimal.ROUND_HALF_UP);
        BigDecimal rgt = right.evaluate().setScale(15, BigDecimal.ROUND_HALF_UP);
        if (lft.compareTo(BigDecimal.ZERO) >= 0) {
            if (rgt.compareTo(BigDecimal.ZERO) == 0){
                throw new ArithmeticException("0^0 is undefined.");
            }
            return BigDecimalMath.pow(lft, rgt);
        } else {
            if (rgt.scale() == 0 && rgt.remainder(new BigDecimal(2)).compareTo(BigDecimal.ZERO) == 0) {
                return BigDecimalMath.pow(lft.negate(), rgt);
            } else if (rgt.scale() == 0 &&
                    Math.abs(rgt.remainder(new BigDecimal(2)).compareTo(BigDecimal.ZERO)) == 1){
                return BigDecimalMath.pow(lft.negate(), rgt).negate();
            } else {
                BigFraction rgtFrac = new BigFraction(rgt);
                if ((rgtFrac.Denominator()).remainder(new BigDecimal(2)).compareTo(BigDecimal.ZERO) == 0){
                    throw new ArithmeticException("Cannot take negative even root.");
                } else {
                    if (rgtFrac.Numerator().remainder(new BigDecimal(2)).compareTo(BigDecimal.ZERO) == 0) {
                        return BigDecimalMath.pow(lft.negate(),rgt);
                    } else {
                        return BigDecimalMath.pow(lft.negate(),rgt).negate();
                    }
                }
            }
        }
    }

    public String toString() {
        return "(" + left.toString() + "^" + right.toString() + ")";
    }

    public List<InputToken> toInputTokens() {
        List<InputToken> resultTokens = left.toInputTokens();
        resultTokens.add(new InputToken("^(", "^("));
        resultTokens.addAll(right.toInputTokens());
        resultTokens.add(new InputToken(")", ")"));
        return resultTokens;
    }
}
