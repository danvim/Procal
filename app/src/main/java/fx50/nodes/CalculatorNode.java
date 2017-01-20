package fx50.nodes;

import fx50.API.InputToken;

import java.math.BigDecimal;
import java.util.List;

/**
 * Calculator Node
 */
public interface CalculatorNode {
    BigDecimal evaluate();
    String toString();
    List<InputToken> toInputTokens();
}
