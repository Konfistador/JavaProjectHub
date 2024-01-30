package nl.fontys.sebivenlo.ranges;

public class IntegerRangeTest extends RangeTestBase<IntegerRange, Integer, Integer> {

    Integer[] pointsA = {42, 51, 55, 1023, 1610, 2840};
    RangeTestDataFactory<IntegerRange, Integer, Integer> daf;

    @Override
    RangeTestDataFactory<IntegerRange, Integer, Integer> helper() {
        if (daf == null) {

            daf = new RangeTestDataFactory<IntegerRange, Integer, Integer>(pointsA) {
                @Override
                IntegerRange createRange(Integer start, Integer end) {
                    return IntegerRange.of(start, end);
                }

                @Override
                Integer distance(Integer a, Integer b) {
                    return Math.abs(a-b);
                }
            };
        }
        return daf;
    }
}
