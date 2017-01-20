package fx50.nodes;

import java.math.BigDecimal;

/**
 * Boolean Node
 */
interface BooleanNode extends CalculatorNode {
    BigDecimal compare(CalculatorNode left, CalculatorNode right);
}
