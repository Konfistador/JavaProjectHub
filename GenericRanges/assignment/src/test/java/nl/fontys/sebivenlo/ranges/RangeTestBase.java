package nl.fontys.sebivenlo.ranges;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assumptions.*;

import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests the Range interface via leaf class IntegerRange.
 *
 * @author Pieter van den Hombergh {@code pieter.van.den.hombergh@gmail.com}
 */
@TestMethodOrder(MethodOrderer.MethodName.class)
public abstract class RangeTestBase<
        R extends Range<R,P,D>,
        P extends Comparable<? super P>,
        D extends Comparable<?super D>
        > {

    abstract RangeTestDataFactory<R,P,D> helper();

    /**
     * Create range using helper.
     *
     * @param rp1 range spec
     * @return a range
     */
    R createRange(String rp1) {
        return helper().createRange(rp1);
    }

    /**
     * Create range using helper.
     *
     * @param p1 point
     * @param p2 point
     * @return range
     */
    R createRange(P p1, P p2) {
        return helper().createRange(p1, p2);
    }

    /**
     * Helper to shorten writing the tests.
     *
     * @param key
     * @return
     */
    P lookupPoint(String key) {
        return helper().lookupPoint(key);
    }

    /**
     * Helper to compute distance.
     *
     * @param a point
     * @param b point
     * @return integer distance
     */
    D distance(P a, P b) {
        return helper().distance(a, b);
    }

    /**
     * Test the default max function in Range.
     *
     * @param as  specifies a
     * @param bs  specifies a
     * @param exs specifies expected point
     */
    //@Disabled("Think TDD")
    @ParameterizedTest
    @CsvSource({
            "a,b,b",
            "c,b,c",
            "a,a,a"
    })
    public void t01Max(String as, String bs, String exs) {
        P a = lookupPoint(as);
        P b = lookupPoint(bs);
        P expected = lookupPoint(exs); // the map will return the same instance

        assertThat(Range.max(a, b))
                .as("Expecting to receive the proper maxValue")
                .isEqualTo(expected);
        //fail( "method t01Max reached end. You know what to do." );
    }

    /**
     * Test the default max function in Range.
     *
     * @param as  specifies a
     * @param bs  specifies a
     * @param exs specifies expected point
     */
    //@Disabled("Think TDD")
    @ParameterizedTest
    @CsvSource({
            "a,b,a",
            "c,b,b",
            "a,a,a"
    })
    public void t02Min(String as, String bs, String exs) {
        P a = lookupPoint(as);
        P b = lookupPoint(bs);
        P expected = lookupPoint(exs);


        assertThat(Range.min(a, b))
                .as("Expecting the proper Minimum value")
                .isEqualTo(expected);
        //fail( "method t02Min reached end. You know what to do." );
    }

    /**
     * Test the default minmax function in Range.
     *
     * @param as        specifies a
     * @param bs        specifies a
     * @param expected0 specifies expected0 point
     * @param expected1 specifies expected1 point
     */
    //@Disabled("Think TDD")
    @ParameterizedTest
    @CsvSource({
            "a,a,a,a",
            "a,b,a,b",
            "d,c,c,d",})
    public void t03MinmaxTest(String as, String bs, String expected0,
                              String expected1) {
        P a = lookupPoint(as);
        P b = lookupPoint(bs);
        P exp0 = lookupPoint(expected0);
        P exp1 = lookupPoint(expected1);
        P[] t = Range.minmax(a, b);
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(t)
                    .as("Expecting the size of the output Array to be exactly 2")
                    .hasSize(2);
            softly.assertThat(t[0])
                    .as("Expecting the output array to contain the proper value at index 0")
                    .isEqualTo(exp0);
            softly.assertThat(t[1])
                    .as("Expecting the output array to contain the proper value at index 1")
                    .isEqualTo(exp1);

        });

        //fail( "method t03minmaxTest reached end. You know what to do." );
    }

    /**
     * Test Range#meets.
     *
     * @param as       specifies a
     * @param bs       specifies b
     * @param cs       specifies c
     * @param ds       specifies d
     * @param expected outcome
     */
    //@Disabled("Think TDD")
    @ParameterizedTest
    @CsvSource({
            "a,b,c,d,false",
            "c,d,a,b,false",
            "a,b,b,d,true",
            "c,d,a,c,true",})
    public void to4Meets(String as, String bs, String cs, String ds,
                         boolean expected) {
        P a = lookupPoint(as);
        P b = lookupPoint(bs);
        P c = lookupPoint(cs);
        P d = lookupPoint(ds);

        R rangeA = createRange(a, b);
        R rangeB = createRange(c, d);

        assertThat(rangeA.meets(rangeB))
                .as("Expecting meets() to return a proper value")
                .isEqualTo(expected);
        //fail("method t04Meets reached end. You know what to do.");
    }

    /**
     * Test the helper method Range#between. Given.
     */
    //@Disabled("Think TDD")
    @Test
    public void t05CreateBetween() {
        P a = lookupPoint("a");
        P b = lookupPoint("b");
        P c = lookupPoint("c");
        // helper is needed to get access to the between method.
        R helper = createRange(c, c);
        R rt = helper.between(a, b);
        assertThat(rt)
                .extracting("start", "end")
                .containsExactly(a, b);

        //fail( "createBetween completed succesfully; you know what to do" );
    }

    /**
     * Test Range#rangeHashCode and Range#rangeEquals implicitly through
     * concrete IntegerRange. Given.
     */
    //@Disabled("Think TDD")
    @Test
    public void t06HashCodeEquals() {
        P a = lookupPoint("a");
        P b = lookupPoint("b");
        P c = lookupPoint("c");

        R ref = createRange(a, b);
        R equ = createRange(a, b);
        R diffB = createRange(a, c);
        R diffC = createRange(c, b);

        TestUtils.verifyEqualsAndHashCode(ref, equ, diffB, diffC);

//        fail( "hashCodeEquals completed succesfully; you know what to do" );
    }

    /**
     * Test length function. Bit dubious, does it really test anything in range?
     */
    //@Disabled("Think TDD")
    @Test
    public void t07Length() {
        P a = lookupPoint("a");
        P b = lookupPoint("b");
        R rangeUnderTest = createRange(a,b);
        D expectedDistance = distance(a,b);
        assertThat(rangeUnderTest.length())
                .as("Expecting the distance() to return endPoint - startPoint")
                .isEqualTo(expectedDistance);
        //fail( "method t07Length reached end. You know what to do." );
    }

    /**
     * Test the overlaps function. The method is given. Add more test values.
     *
     * @param rp1      point pair 1
     * @param rp2      point pair 2
     * @param overlaps expected outcome
     */
    //@Disabled("Think TDD")
    @ParameterizedTest
    @CsvSource(value = {
            "ab,cd,false", // disjunction
            "ac,cd,false", // meet
            "ac,bd,true",  //  B < C < D
            "ae,ac,true",  //  A = A < D
            "bd,cd,true",  //  B < C < D
            "bd,cf,true"   //  B < C < F

    }
    )
    void t08Overlaps(String rp1, String rp2, boolean overlaps) {
        R r1 = createRange(rp1);
        R r2 = createRange(rp2);

        assertThat(r1.overlaps(r2))
                .as("Expecting the overlap() to properly determine if two ranges are overlapping")
                .isEqualTo(overlaps);
        //fail( "method t08Overlaps reached end. You know what to do." );
    }

    /**
     * Compute the overlap function as long.
     *
     * @param rp1 point pair one defining first range
     * @param rp2 point pair two defining second range
     * @param rp3 point pair with expected length
     */
    //@Disabled("Think TDD")
    @ParameterizedTest
    @CsvSource(value = {
            // first, second, distance  points
            "ab,cd,aa", // disjunction
            "ab,bc,bb", // disjunction
            "ac,bd,bc", //  B < C < Integer
            "ae,ac,ac",  // A = A < D
            "bd,cd,cd",  //  B < C < D
            "bd,cf,cd"   //  B < C < F

    }
    )
    void t09overLap(String rp1, String rp2, String rp3) {
        var range1 = createRange(rp1);
        var range2 = createRange(rp2);
        var expected = createRange(rp3);

        assertThat(range1.overlap(range2))
                .as("Expecting the overlap to return the proper overlap length")
                .isEqualTo(expected.length());
        //fail("test t09overLap completed, you know what to do.");
    }

    /**
     * Assert that the range constructor puts start and end in the proper order.
     * Assert that end of range is less or equal to start, using compareTo.
     */
    //@Disabled("Think TDD")
    @Test
    void t10normalizes() {
        R r1 = createRange("bf");
        R r2 = createRange("fb");

        assertThat(r1.compareTo(r2)).as("Expecting a Range to be normalized{\nstart() is before the end()\n}")
                .isEqualTo(0);
        //fail("test t10normalizes completed, you know what to do.");

    }

    /**
     * Check the contain(p) method word correctly. Method is given. Add test
     * values.
     *
     * @param pp       first range lookup
     * @param point    to check
     * @param contains expected value
     */
    //@Disabled("Think TDD")
    @ParameterizedTest
    @CsvSource({
            "bc,a,false",
            "bc,d,false",
            "ad,c,true",
            "ab,a,true",
            "aa,a,true",
            "bf,f,false",
            "be,c,true"
    })
    void t11ContainsPoint(String pp, String point, boolean contains) {
        var rangeUnderTest = createRange(pp);
        var pointContained = lookupPoint(point);
        assertThat(rangeUnderTest.contains(pointContained))
                .as("containsPoint shall properly determine if a point is contained into a range.")
                .isEqualTo(contains);
        //fail("t11ContainsPoint completed succesfully; you know what to do");
    }

    //@Disabled("Think TDD")
    @Test
    void t12ToStringTest() {
        R range = createRange("ab");

        assertThat(range.toString())
                .as("Expecting a proper String representation of a Range")
                .isEqualTo("[" + Objects.toString(range.start()) + "," + Objects.toString(range.end()) + ")");
        //fail(" t12ToString reached end. You know what to do.");
    }

    /**
     * Test that method checkMeetsOrOverlaps throws exception at the proper
     * situation. In this the exception should NOT occur.
     *
     * @param pp1
     * @param pp2
     */
    //@Disabled("Think TDD")
    @ParameterizedTest
    @CsvSource({
            "ab,bc",
            "ac,bd"
    })
    void t13aCheckMeetsOrOverlaps(String pp1, String pp2) {
        R r1 = createRange(pp1);
        R r2 = createRange(pp2);
        // code that should throw the exception.
        assertThatCode(() -> r1.checkMeetsOrOverlaps(r2))
                .as("checkMeetsOrOverlaps shall not throw an exception")
                .doesNotThrowAnyException();
        //fail("method t13aCheckMeetsOrOverlaps reached end. You know what to do.");
    }

    /**
     * Test that method checkMeetsOrOverlaps throws exception at the proper
     * situation. In this test the Exception IS expected.
     *
     * @param pp1
     * @param pp2
     */
    //@Disabled("Think TDD")
    @ParameterizedTest
    @CsvSource({
            "ab,cd"
    })
    void t13bCheckMeetsOrOverlaps(String pp1, String pp2) {
        R r1 = createRange(pp1);
        R r2 = createRange(pp2);
        // code that should throw or not throw exception.
        assertThatThrownBy(() -> r1.checkMeetsOrOverlaps(r2))
                .as("Expecting checkMeetsOrOverlaps() to throw a proper exception")
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("this range " + r1.toString() + " and other " + r2.toString() + " do not meet nor overlap");
        //fail("method t13bCheckMeetsOrOverlaps reached end. You know what to do.");
    }

    /**
     * Check joinWith. The test values should all produce a join, the exception
     * throwing is tested elsewhere.
     *
     * @param pp1           first range spec
     * @param pp2           second range spec.
     * @param expectedRange in the test
     */
    // @Disabled("Think TDD")
    @ParameterizedTest
    @CsvSource({
            "ab,bc,ac",
            "ac,bd,ad",
            "bd,df,bf",
            "cd,de,ce"
    })
    void t14JoinWith(String pp1, String pp2, String expectedRange) {
        var rangeA = createRange(pp1);
        var rangeB = createRange(pp2);
        var expectedOutput = createRange(expectedRange);

        assertThat(rangeA.joinWith(rangeB))
                .as("Expecting a proper join of the two input ranges.")
                .isEqualTo(expectedOutput);
        //fail("method t14JoinWith reached end. You know what to do.");
    }

    /**
     * Check the intersect method part 1. In this test all values should produce
     * a non-empty intersection.
     *
     * @param rp1          range specification
     * @param rp2          cutter range spec
     * @param interSection expected value 1
     * @param interSection expected result of cut.
     */
    //@Disabled("Think TDD")
    @ParameterizedTest
    @CsvSource(value = {
            // this, cutter, cuts, expected result
            "ac,bd,bc",
            "ac,ae,ac",
            "bd,cf,cd",
            "be,cd,cd",
            "bf,cf,cf"

    }
    )
    void t15aCommonRangeSuccess(String rp1, String rp2, String interSection) {
        R range = createRange(rp1);
        R cutter = createRange(rp2);
        R expectedIntersection = createRange(interSection);
        Optional<R> result = range.intersectWith(cutter);

        assertThat(result.get())
                .as("Expecting a proper result of the intersection")
                .isEqualTo(expectedIntersection);
        //fail("t15aCommonRangeSuccess completed succesfully; you know what to do");
    }

    /**
     * Check the intersect method part 2. In this test all values should produce
     * an empty intersection.
     *
     * @param rp1          range specification
     * @param rp2          cutter range spec.
     */
    //@Disabled("Think TDD")
    @ParameterizedTest
    @CsvSource(value = {
            // this, cutter, cuts, expected result
            "ab,cd",
            "bd,ef",
            "ab,bd",
            "cd,ef",
            "bc,ef"
    })
    void t15bCommonRangeEmpty(String rp1, String rp2) {
        R range = createRange(rp1);
        R cutter = createRange(rp2);
        Optional<IntegerRange> expectedResult = Optional.empty();

        assertThat(range.intersectWith(cutter))
                .as("Expecting an empty result, as the two ranges do not overlap")
                .isEqualTo(expectedResult);
        //fail("t15bCommonRangeEmpty completed succesfully; you know what to do");
    }

    /**
     * Test if range is fully contained in other. (contains method)
     * <p>
     * Method is given. Add test values
     *
     * @param rp1      this range
     * @param rp2      other range
     * @param expected outcome./home/hom/teambin/builder-mkpending
     */
    //@Disabled("Think TDD")
    @ParameterizedTest
    @CsvSource(value = {
            // this, cutter, cuts, expected result
            "ab,cd,false", // disjunct
            "be,cd,true",
            "ad,bd,true",
            "ae,ad,true",
            "ab,af,false",
            "bd, ad,false"
    }
    )
    void t16ContainsRange(String rp1, String rp2, boolean expected) {
        R range = createRange(rp1);
        R other = createRange(rp2);

        assertThat(range.contains(other))
                .as("Expecting the contains() to properly determine if a range is fully contained within other one")
                .isEqualTo(expected);
        //fail("t16ContainsRange completed succesfully; you know what to do");

    }

    /**
     * Test the punchThrough method. Test is given. Add test values.
     * <p>
     * In expected value ab|bc means a stream with exactly the elements [ab) and
     * [bc).
     *
     * @param rangeP     range value
     * @param punchP     punch value
     * @param restPairs, | separated list of expected ranges in stream
     */
    // @Disabled("Think TDD")
    @ParameterizedTest
    @CsvSource(value = {
            // range, punch, results, | separated
            "ab,ab,ab", // replace
            "ac,ab,ab|bc", // left punch
            "bf,df,bd|df", // right punch
            "be,cd,bc|cd|de" // middle punch
    }
    )
    void t17PunchThrough(String rangeP, String punchP, String restPairs) {
        R range = createRange(rangeP);
        R punch = createRange(punchP);
        var expectedParts = helper().restRanges("\\|", restPairs);
        Stream<R> result = range.punchThrough(punch);
        assertThat(result.collect(Collectors.toList())).as("Expecting punchThrough() to return the proper ranges")
                .containsExactlyElementsOf(expectedParts);
        // fail("t17PunchThrough completed succesfully; you know what to do");

    }

    /**
     * Test compareTo. The outcome is negative, zero or positive, which is
     * expressed in the table as -1, 0. or 1.
     * <p>
     * To test for zero is easy, but a special case. To test for the negative
     * value, multiply expected with the actual value and test it to be greater
     * than 0.
     * <p>
     * we need to detect that result and expected have the same // sign or are
     * equal. // we can achieve
     *
     * @param pp1      range 1
     * @param pp2      range 2
     * @param expected value
     */
    //@Disabled("Think TDD")
    @ParameterizedTest
    @CsvSource({
            "ab,ac,0", // same start
            "ac,bd,-1", // start left of
            "bc,ad,1", // start right of
    })
    void t18CompareTo(String pp1, String pp2, int expected) {
        R r1 = createRange(pp1);
        R r2 = createRange(pp2);
        var actual = r1.compareTo(r2);
        if (Math.signum(Double.valueOf(expected)) < 0) {
            actual *= expected;
        }
        assertThat(r1.compareTo(r2)).as("Expecting a proper comparison")
                .isEqualTo(expected);

        //fail("t18CompareTo completed succesfully; you know what to do");
    }
}
