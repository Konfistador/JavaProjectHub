package fraction;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

/**
 * @author Pieter van den Hombergh {@code p.vandehombergh@gmail.com}
 */
@TestMethodOrder(OrderAnnotation.class)
public class FractionTest {

    /**
     * Test getters for numerator and denominator.
     * Use SoftAssertions.assertSoftly to always test both getters even if the
     * first assert fails.
     *
     * @param a           num in frac
     * @param b           denom in frac
     * @param numerator   expected
     * @param denominator expected
     */
    @Order(1)
    //@Disabled("Think TDD")
    @ParameterizedTest
    @CsvSource({
            // message, a,b, num, denom
            "half,1,2,1,2",
            "one third,2,6,1,3",
            "minus half,9,-18,-1,2",})
    void t1Getters(String message, int a, int b, int numerator, int denominator) {
        var sut = new Fraction(a, b);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(sut.getNumerator())
                    .as(message)
                    .isEqualTo(numerator);
            softly.assertThat(sut.getDenominator())
                    .as(message)
                    .isEqualTo(denominator);
        });
        //  fail("method testGetters reached end. You know what to do.");
    }

    /**
     * @param message  per test
     * @param expected value
     * @param num      input
     * @param denom    input
     */
    //@Disabled( "Think TDD" )
    @Order(2)
    @ParameterizedTest
    @CsvSource({
            "whole number, 2, 2, 1 ",
            "two thirds, (2/3), 2, 3",
            "one third, (1/3), -3, -9 ",
            "minus two fifths, -(2/5), 12, -30",
            "one + two fifths, -(1+(2/5)), 35, -25 "
    })
    void t2fractionToString(String message, String expected, int num, int denom) {
        var sut = new Fraction(num, denom);

        assertThat(sut.toString())
                .as(message)
                .containsIgnoringCase(expected);


        // fail("fractionToString completed succesfully; you know what to do");
    }

    /**
     *
     */
    final Map<String, BiFunction<Fraction, Fraction, Fraction>> operations = Map.of(
            "times", Fraction::times, // on value given
            "plus", Fraction::plus,
            "minus", Fraction::minus,
            "divideBy", Fraction::divideBy,
            "negate", (f1,f2) -> f1.negate(),
            "inverse", (f1, f2) -> f1.inverse(),
            "plusInt", ( f1, f2 ) -> f1.plus(f2.getNumerator()),
            "divideByInt", (f1, f2) -> f1.divideBy(f2.getNumerator()),
            "timesInt", (f1,f2) -> f1.times(f2.getNumerator()),
            "minusInt", (f1,f2) -> f1.minus(f2.getNumerator())
            );


    /**
     * Test the operations defined on fraction. Expected result is expressed as
     * a String.
     */
    @Order(3)
//    //@Disabled("Think TDD")
    @ParameterizedTest
    @CsvSource(
            {
                    // msg, opname, expected, a,b,c,d
                    "'one half times one third is 1 sixth','times',   '(1/6)',   1,2,1,3",
                    "'one thirds plus two thirds is 1'    , 'plus',       '1',   1,3,2,3",
                    "'inverse fraction '                  , 'inverse',    '3',   1,3,1,3",
                    "'one half minus two thirds is'       , 'minus' , '-(1/6)',  1,2,2,3",
                    "'4/2 - 1/2 = 3/2'       , 'minus' , '2',  17,8,1,8",
                    "'one half times 4 is 2'              , 'timesInt','2',      1,2,4,1",
                    "'one half plus  1 is 1(1/2)'         , 'plusInt','(1+(1/2))',  1,2,1,1",
                    "'5 thirds minus 2 is -1/3  '         , 'minusInt','-(1/3)', 5,3,2,1",
                    "'5 thirds divided by 4 is  '         , 'divideByInt','(5/12)', 15,9,4,1",
                    "'1/2 div 1/3     is 1+1/2  '         , 'divideBy','(1+(1/2))', 1,2,3,9",
                    "'negate 1/5                '         , 'negate','-(1/5)', 1,5,1,5",})
    void t3FractionOps(String message, String operation, String expected, int a,
                       int b, int c, int d) {
        BiFunction<Fraction, Fraction, Fraction> op = operations.get(operation);
        var sut = new Fraction(a, b);
        var secondFrac = new Fraction(c, d);
        // ignore the test if opName not found
        var result = op.apply(sut, secondFrac);

        assertThat(result.toString())
                .as(message)
                .isEqualTo(expected);


        // fail("tFractionOps completed succesfully; you know what to do");
    }

    /**
     * Make sure the fraction throws an IllegalArgumentException
     * when denom == 0.
     */
    @Order(4)
    //@Disabled("Think TDD")
    @Test
    void t4DivideByZeroNotAllowed() {
        assertThatThrownBy(() -> new Fraction(2,5).divideBy(0))
                .isExactlyInstanceOf(IllegalArgumentException.class);

        // fail("tDivideByZeroNotAllowed completed succesfully; you know what to do");
    }

    /**
     * Test that the one parameter variant of frac creates a Fraction with a
     * denominator of 1.
     */
    @Order(5)
    //@Disabled("Think TDD")
    @Test
    public void t5IntAsFrac() {
        assertThatCode(() -> new Fraction(2))
                .as("One argument shall be sufficient for creating a Fraction")
                .doesNotThrowAnyException();
        // fail("tIntAsFrac completed succesfully; you know what to do");
    }

    /**
     * Use test helper method {
     */
    @Order(7)
    //@Disabled("Think TDD")
    @Test
    public void t7EqualsHashCode() {
        var reference = new Fraction(4);
        var equal = new Fraction(12, 3);
        var unEqualList = List.of(
                new Fraction(7, 2),
                new Fraction(3),
                new Fraction(13, 3)
        );

        FractionTest.verifyEqualsAndHashCode(reference, equal, unEqualList);
        //fail("tEqualsHasCode completed succesfully; you know what to do");
    }

    /**
     * The fraction should be comparable.Create a fraction and test signum of
     * compareTo result.
     *
     * @param msg    message
     * @param a      num in 1st frac
     * @param b      denum in 1st frac
     * @param c      num in 2nd frac
     * @param d      denom in 2nd frac
     * @param signum expected sign or 0 if equal.
     */
    @Order(8)
    //@Disabled("Think TDD")
    @ParameterizedTest
    @CsvSource({
            "equal,   1,2,2,4,0",
            "less,    1,2,3,4,-1",
            "greater, 1,2,1,3,1",})
    public void t8Comparable(String msg, int a, int b, int c, int d, int signum) {
        assertThat(new Fraction(a, b).compareTo(new Fraction(c, d)))
                .as(msg)
                .isEqualTo(signum);
        //   fail("tComparable completed succesfully; you know what to do");
    }

    /**
     * Helper for equals tests, which are tedious to get completely covered.
     *
     * @param <T>     type of class to test
     * @param ref     reference value
     * @param equal   one that should test equals true
     * @param unEqual list of elements that should test unequal in all cases.
     */
    public static <T> void verifyEqualsAndHashCode(T ref, T equal, T... unEqual) {
        Object object = "Hello";
        T tnull = null;
        String cname = ref.getClass().getCanonicalName();
        // I got bitten here, assertJ equalTo does not invoke equals on the
        // object when ref and 'other' are same.
        // THAT's why the first one differs from the rest.
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(ref.equals(ref))
                    .as(cname + ".equals(this): with self should produce true")
                    .isTrue();
            softly.assertThat(ref.equals(tnull))
                    .as(cname + ".equals(null): ref object "
                            + safeToString(ref) + " and null should produce false"
                    )
                    .isFalse();
            softly.assertThat(ref.equals(object))
                    .as(cname + ".equals(new Object()): ref object"
                            + " compared to other type should produce false"
                    )
                    .isFalse();
            softly.assertThat(ref.equals(equal))
                    .as(cname + " ref object [" + safeToString(ref)
                            + "] and equal object [" + safeToString(equal)
                            + "] should report equal"
                    )
                    .isTrue();
            for (int i = 0; i < unEqual.length; i++) {
                T ueq = unEqual[i];
                softly.assertThat(ref)
                        .as("testing supposed unequal objects")
                        .isNotEqualTo(ueq);
            }
            // ref and equal should have same hashCode
            softly.assertThat(ref.hashCode())
                    .as(cname + " equal objects "
                            + ref.toString() + " and "
                            + equal.toString() + " should have same hashcode"
                    )
                    .isEqualTo(equal.hashCode());
        });
    }


    /**
     * ToString that deals with any exceptions that are thrown during its
     * invocation.
     * <p>
     * When x.toString triggers an exception, the returned string contains a
     * message with this information plus class and system hashCode of the
     * object.
     *
     * @param x to turn into string or a meaningful message.
     * @return "null" when x==null, x.toString() when not.
     */
    public static String safeToString(Object x) {
        if (x == null) {
            return "null";
        }
        try {
            return x.toString();
        } catch (Throwable e) {
            return "invoking toString on instance "
                    + x.getClass().getCanonicalName() + "@"
                    + Integer.toHexString(System.identityHashCode(x))
                    + " causes an exception " + e.toString();
        }
    }
}
