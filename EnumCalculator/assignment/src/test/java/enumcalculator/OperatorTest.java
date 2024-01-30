package enumcalculator;

import org.assertj.core.api.Assumptions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Objects;

import static org.assertj.core.api.Assertions.*;

/**
 * Parameterized test for enum operation. Look in [Kaczanowski, ch 3] on how to
 * use parameterized tests.
 *
 * @""      /    
 */
public class OperatorTest {

    /**
     * Most of the test input, including the operator symbol. In following
     * exercises you will have to do that on your own. The test data is
     * collected in a set of comma separator lines or records, in which you can
     * access the columns with an index, zero based.
     *
     * @param message  for test
     * @param symbol   of operation
     * @param expected outcome
     * @param a        first value
     * @param b        second value
     */
    @ParameterizedTest
    @CsvSource({
            // message, symbol, expected result, a,b
            "add      , + ," + (2 + 3) + ", 2, 3 ",
            "subtract      , - ," + (2 - 3) + ", 2, 3 ",
            "multiply      , * ," + (2 * 3) + ", 2, 3 ",
            "divide      , / ," + (12 / 3) + ", 12, 3 ",
            "power      , ** ," + (8) + ", 2, 3 "
            //comma separated strings
    })
    public void testOperator(String message, String symbol, int expected, int a, int b) {
        var operation = Operator.get(symbol);

        assertThat(operation.compute(a, b))
                .as(message)
                .isEqualTo(expected);
        // fail("test not yet implemented");
    }

    @Test
    public void getReturnsNullWhenOperationNotPresent() {
        assertThat(Operator.get("Kur"))
                .as("Expecting null, when operation is not present")
                .isNull();
       // fail("getReturnsNullWhenOperationNotPresent test method completed. You know what to do");
    }

    /**
     * testSupportedOperators.
     * Should be +, -, *, /, **
     */
    @Test
    public void testSupportedOperators() {
        assertThat(Operator.supportedSymbols())
                .as("Expecting Operator to support proper operations")
                .contains("+", "-", "*", "/", "**");
        //fail("test reached end");
    }

    /**
     * Test that a division by zero results in IllegalArgumentException.
     */
    @Test
    public void testDivisionByZeroNotAllowed() {
        assertThatThrownBy(() -> Objects.requireNonNull(Operator.get("/")).compute(4, 0))
                .as("Expecting a proper exception, when trying to divide by 0")
                .hasMessage("Only Chuck Norris possesses the power to divide by 0.")
                .isExactlyInstanceOf(IllegalArgumentException.class);
        //fail("test not yet implemented");
    }
}
