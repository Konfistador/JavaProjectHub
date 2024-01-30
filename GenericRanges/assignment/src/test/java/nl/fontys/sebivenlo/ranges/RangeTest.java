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
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Tests the Range interface via leaf class IntegerRange.
 *
 * @author Pieter van den Hombergh {@code pieter.van.den.hombergh@gmail.com}
 */
@TestMethodOrder(MethodOrderer.MethodName.class)
class RangeTest {

    // use as              a, b,  c,     d,    e,    f
    Integer[] pointsA = {42, 51, 55, 1023, 1610, 2840};
    RangeTestDataFactory<IntegerRange, Integer, Integer> daf;

    RangeTestDataFactory<IntegerRange, Integer, Integer> helper() {
        if (null == daf) {
            daf = new RangeTestDataFactory<>(pointsA) {

                @Override
                IntegerRange createRange(Integer start, Integer end) {
                    return IntegerRange.of(start, end);
                }

                @Override
                Integer distance(Integer a, Integer b) {
                    return b - a;
                }

            };

        }
        return daf;
    }

    /**
     * Create range using helper.
     *
     * @param rp1 range spec
     * @return a range
     */
    IntegerRange createRange(String rp1) {
        return helper().createRange(rp1);
    }

    /**
     * Create range using helper.
     *
     * @param p1 point
     * @param p2 point
     * @return range
     */
    IntegerRange createRange(Integer p1, Integer p2) {
        return helper().createRange(p1, p2);
    }

    /**
     * Helper to shorten writing the tests.
     *
     * @param key
     * @return
     */
    Integer lookupPoint(String key) {
        return helper().lookupPoint(key);
    }

    /**
     * Helper to compute distance.
     *
     * @param a point
     * @param b point
     * @return integer distance
     */
    Integer distance(Integer a, Integer b) {
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
        Integer a = lookupPoint(as);
        Integer b = lookupPoint(bs);
        Integer expected = lookupPoint(exs); // the map will return the same instance

        assertThat(Range.max(a, b))
                .as("Expecting a proper max() output")
                .isEqualTo(expected);
        // fail("method t01Max reached end. You know what to do.");
    }

    /**
     * Test the default min function in Range.
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
        var a = lookupPoint(as);
        var b = lookupPoint(bs);

        var expected = lookupPoint(exs);

        assertThat(Range.min(a, b))
                .as("Expecting a proper min() output")
                .isEqualTo(expected);
        //  fail("method t02Min reached end. You know what to do.");
    }

    /**
     * Test the default minmax function in Range.
     *
     * @param as        specifies a
     * @param bs        specifies b
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
        Integer a = lookupPoint(as);
        Integer b = lookupPoint(bs);
        Integer exp0 = lookupPoint(expected0);
        Integer exp1 = lookupPoint(expected1);
        Integer[] t = Range.minmax(a, b);
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(t[0])
                    .as("expecting a proper minmax()[0] output")
                    .isEqualTo(exp0);
            softly.assertThat(t[1])
                    .as("expecting a proper minmax()[1] output")
                    .isEqualTo(exp1);
        });

        //  fail("method t03minmaxTest reached end. You know what to do.");
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
    public void t04Meets(String as, String bs, String cs, String ds,
                         boolean expected) {
        Integer a = lookupPoint(as);
        Integer b = lookupPoint(bs);
        Integer c = lookupPoint(cs);
        Integer d = lookupPoint(ds);

        var rangeA = IntegerRange.of(a, b);
        var rangeB = IntegerRange.of(c, d);

        // Make sure to implement IntergerRange.of

        assertThat(rangeA.meets(rangeB))
                .as("Expecting a proper meets() output")
                .isEqualTo(expected);
        // fail("method t04Meets reached end. You know what to do.");
    }

    /**
     * Test the helper method Range#between. Given.
     */
    //@Disabled("Think TDD")
    @Test
    public void t05CreateBetween() {
        Integer a = lookupPoint("a");
        Integer b = lookupPoint("b");
        Integer c = lookupPoint("c");
        // helper is needed to get access to the between method.
        IntegerRange helper = createRange(c, c);
        IntegerRange rt = helper.between(a, b);
        assertThat(rt)
                .extracting("start", "end")
                .containsExactly(a, b);

//        fail( "createBetween completed succesfully; you know what to do" );
    }

    /**
     * Test Range#rangeHashCode and Range#rangeEquals implicitly through
     * concrete IntegerRange. Given.
     */
    //@Disabled("Think TDD")
    @Test
    public void t06HashCodeEquals() {
        Integer a = lookupPoint("a");
        Integer b = lookupPoint("b");
        Integer c = lookupPoint("c");

        IntegerRange ref = createRange(a, b);
        IntegerRange equ = createRange(a, b);
        IntegerRange diffB = createRange(a, c);
        IntegerRange diffC = createRange(c, b);

        TestUtils.verifyEqualsAndHashCode(ref, equ, diffB, diffC);

//        fail( "hashCodeEquals completed succesfully; you know what to do" );
    }

    /**
     * Test length function. Bit dubious, does it really test anything in range?
     */
    //@Disabled("Think TDD")
    @Test
    public void t07Length() {
        var pointA = lookupPoint("a");
        var pointB = lookupPoint("b");

        var range = IntegerRange.of(pointA, pointB);

        assertThat(range.length())
                .as("Expecting a proper length() output")
                .isEqualTo(Math.abs(pointA - pointB));
        // fail("method t07Length reached end. You know what to do.");
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
            "ab,cd,false", // disjunct
            "ac,cd,false", // meet
            "ac,bd,true", //  B < C < D
            "ab,ad,true" // B > C

    }
    )
    void t08Overlaps(String rp1, String rp2, boolean overlaps) {
        IntegerRange r1 = createRange(rp1);
        IntegerRange r2 = createRange(rp2);

        assertThat(r1.overlaps(r2))
                .as("Expecting a proper overlaps with " + r1 + "\t" + r2)
                .isEqualTo(overlaps);
//        fail("method t08Overlaps reached end. You know what to do.");
    }

    /**
     * Compute the overlap between two ranges.
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
    void t09OverLap(String rp1, String rp2, String rp3) {
        var rangeA = createRange(rp1);
        var rangeB = createRange(rp2);
        var expected = createRange(rp3);

        assertThat(rangeA.overlap(rangeB))
                .as("expecting a proper overlap with " + rp1 + "\t" + rp2)
                .isEqualTo(expected.length());
        //  fail("test t09overLap completed, you know what to do.");
    }

    /**
     * Assert that the range constructor puts start and end in the proper order.
     * E.g. IntergerRange(5,4) -> start: 4 and end: 5
     * Assert that end of range is less or equal to start, using compareTo.
     */
    //@Disabled("Think TDD")
    @Test
    void t10Normalizes() {
        var sut = createRange("da");
        var start = sut.start();
        var end = sut.end();

        assertThat(sut.start() <= sut.end())
                .as("Expecting a normalised Range")
                .isTrue();

//        fail("test t10normalizes completed, you know what to do.");

    }

    /**
     * Check the contain(p) method works correctly. Method is given. Add test
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
        var rangeUT = createRange(pp);
        var lookupPoint = lookupPoint(point);

        assertThat(rangeUT.contains(lookupPoint))
                .as("Expecting a proper contains()")
                .isEqualTo(contains);
        // fail("t11ContainsPoint completed succesfully; you know what to do");
    }

    //@Disabled("Think TDD")
    @Test
    void t12ToStringTest() {
        var rangeUT = createRange("ae");

        assertThat(rangeUT.toString())
                .as("Expecting a proper String representation of a Range")
                .isEqualTo("[" + Objects.toString(rangeUT.start()) + "," + Objects.toString(rangeUT.end()) + ")");
        //   fail(" t12ToString reached end. You know what to do.");
    }

    /**
     * Test that method checkMeetsOrOverlaps throws exception at the proper
     * situation.
     * <p>
     * Test that it DOESN'T throw the exception
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
        IntegerRange r1 = createRange(pp1);
        IntegerRange r2 = createRange(pp2);

        assertThatCode(() -> r1.checkMeetsOrOverlaps(r2))
                .as("Expecting exception to be thrown, only when necessary")
                .doesNotThrowAnyException();
        //   fail("method t13aCheckMeetsOrOverlaps reached end. You know what to do.");
    }

    /**
     * Test that method checkMeetsOrOverlaps throws exception at the proper
     * situation.
     * <p>
     * Test that it DOES throw the exception
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
        IntegerRange r1 = createRange(pp1);
        IntegerRange r2 = createRange(pp2);


        assertThatThrownBy(() -> r1.checkMeetsOrOverlaps(r2))
                .as("Expecting a proper exception")
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("this range " + r1.toString() + " and other " + r2.toString() + " do not meet nor overlap");
        //  fail("method t13bCheckMeetsOrOverlaps reached end. You know what to do.");
    }

    /**
     * Check joinWith. The test values should all produce a join, the exception
     * throwing is tested elsewhere.
     *
     * @param pp1           first range spec
     * @param pp2           second range spec.
     * @param expectedRange in the test
     */
    //@Disabled("Think TDD")
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
        var expected = createRange(expectedRange);

        assertThat(rangeA.joinWith(rangeB))
                .as("Expecting a proper join")
                .isEqualTo(expected);
        //  fail("method t14JoinWith reached end. You know what to do.");
    }

    @Test
    public void properJoinShouldNotRaiseException(){
        var falseRangeA = createRange(51,1023);
        var falseRangeB = createRange(55,42);

        var rangeA = createRange(55,42);
        var rangeB = createRange(51,1023);

        System.out.println(falseRangeA.compareTo(falseRangeB) + "\t false Ranges");
        System.out.println(rangeA.compareTo(rangeB) + "\t proper Ranges");
        assertThatCode(() -> falseRangeA.joinWith(falseRangeB)).as("Proper join should not throw exceptions")
                        .doesNotThrowAnyException();
        //fail("properJoinShouldNotRaiseException test method completed. You know what to do");
    }

    /**
     * Check the intersect method part 1.
     * <p>
     * In this test all values should produce a non-empty intersection.
     *
     * @param rp1          range specification
     * @param rp2          cutter range spec
     * @param intersection expected result of cut.
     */
    //@Disabled("Think TDD")
    @ParameterizedTest
    @CsvSource(value = {
            // this, cutter, intersection
            "ac,bd,bc",
            "ac,ae,ac",
            "bd,cf,cd",
            "be,cd,cd",
            "bf,cf,cf"

    }
    )
    void t15aCommonRangeSuccess(String rp1, String rp2, String intersection) {
        IntegerRange range = createRange(rp1);
        IntegerRange cutter = createRange(rp2);
        var expectedIntersec = createRange(intersection);
        Optional<IntegerRange> result = range.intersectWith(cutter);

        assertThat(result.get())
                .as("Expecting a proper cut")
                .isEqualTo(expectedIntersec);
        // fail("t15aCommonRangeSuccess completed succesfully; you know what to do");
    }

    /**
     * Check the intersect method part 2.
     * <p>
     * In this test all values should produce an empty intersection.
     *
     * @param rp1 range specification
     * @param rp2 cutter range spec
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
        IntegerRange range = createRange(rp1);
        IntegerRange cutter = createRange(rp2);
        Optional<IntegerRange> expected = Optional.empty();

        assertThat(range.intersectWith(cutter))
                .as("expecting a proper intersec result")
                .isEqualTo(expected);
//        fail("t15bCommonRangeEmpty completed succesfully; you know what to do");
    }

    /**
     * Test if range is fully contained in other. (contains method)
     * <p>
     * Method is given. Add test values
     *
     * @param rp1      this range
     * @param rp2      other range
     * @param expected outcome
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
        IntegerRange range = createRange(rp1);
        IntegerRange other = createRange(rp2);

        assertThat(range.contains(other))
                .as("Expecting a proper output from contains()")
                .isEqualTo(expected);
        //  fail("t16ContainsRange completed succesfully; you know what to do");

    }

    /**
     * Test the punchThrough method. Test is given. Add test values.
     * <p>
     * In expected value ab|bc means a stream with exactly the elements [ab) and
     * [bc).
     * Use the method restRanges of the RangeTestDataFactory to help convert
     * expected value to Stream of ranges
     *
     * @param rangeP     range value
     * @param punchP     punch value
     * @param restPairs, | separated list of expected ranges in stream
     */
    //@Disabled("Think TDD")
    @ParameterizedTest
    @CsvSource(value = {
            "ab,ab,ab", // replace
            "ac,ab,ab|bc", // left punch
            "bf,df,bd|df", // right punch
            "be,cd,bc|cd|de", // middle punch
            "ab,cd,ab" //doesn't contain the punch

    }
    )
    void t17PunchThrough(String rangeP, String punchP, String restPairs) {
        IntegerRange range = createRange(rangeP);
        IntegerRange punch = createRange(punchP);
        var expectedParts = helper().restRanges("\\|", restPairs);
        Stream<IntegerRange> result = range.punchThrough(punch);

        assertThat(result.collect(Collectors.toUnmodifiableList()))
                .as("Expecting results list to contain the proper elements")
                .containsExactlyElementsOf(expectedParts);
        // fail("t17PunchThrough completed succesfully; you know what to do");

    }

    /**
     * Test compareTo. The outcome is negative, zero or positive, which is
     * expressed in the table as -1, 0. or 1.
     * <p>
     * Have a look at Integer.signum to help with the assertion
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
        IntegerRange r1 = createRange(pp1);
        IntegerRange r2 = createRange(pp2);

        assertThat(r1.compareTo(r2))
                .as("Expecting a proper comparison feature")
                .isEqualTo(expected);
//        fail("t18CompareTo completed succesfully; you know what to do");
    }
}
