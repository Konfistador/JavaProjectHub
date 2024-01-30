package enumcalculator;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.IntBinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Enum with operations. Example of how to use lambda expressions in an (enum)
 * constructor. This calculator works with an IntBinaryOperator, which means
 * that the result is always an integer; divisions are INTEGER-DIVISIONS. Don't
 * worry about calculation results that are too big to fit the int data type.
 * (overflow and underflow).
 *
 * @""      /    
 */
public enum Operator {
    //TODO Define enum values
    ADD("+", Integer::sum),
    SUBTRACT("-", (a, b) -> a - b),
    MULTIPLY("*", (a, b) -> a * b),
    DIVIDE("/", (a, b) -> a / b),
    POWER("**", (a, b) -> (int) Math.pow(a, b));

    /**
     * Get the operator using its symbol. This method does a linear search
     * through the values of this enum.
     *
     * @param symbol to search
     * @return operation when found, null otherwise.
     */
    public static Operator get(String symbol) {
        switch (symbol) {
            case "+":
                return ADD;
            case "-":
                return SUBTRACT;
            case "*":
                return MULTIPLY;
            case "/":
                return DIVIDE;
            case "**":
                return POWER;
        }
        return null;
    }

    /**
     * The operation is a IntBinaryOperator.
     */
    private final IntBinaryOperator computation;
    /**
     * The symbol that identifies the operation.
     */
    private final String symbol;

    /**
     * The constructor to create the Operation instances.
     *
     * @param symbol      of this operator
     * @param computation the actual method
     */
    Operator(String symbol, IntBinaryOperator computation) {
        this.symbol = symbol;
        this.computation = computation;
    }

    /**
     * Execute the calculation.
     *
     * @param a first param
     * @param b second param
     * @return result of the computation.
     */
    public int compute(int a, int b) {
        if (symbol.equals("/") && b == 0)
            throw new IllegalArgumentException("Only Chuck Norris possesses the power to divide by 0.");
        return computation.applyAsInt(a, b);
    }

    /**
     * Get the symbols of supported operators.
     *
     * @return String array containing all supported operations.
     */
    public static String[] supportedSymbols() {
        return Arrays.stream(Operator.values())
                .map(Operator::getSymbol)
                .toArray(String[]::new);
    }

    public String getSymbol() {
        return symbol;
    }
}
