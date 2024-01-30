package nl.fontys.sebivenlo.ranges;

import java.time.Duration;
import java.time.Instant;

public class InstantRangeTest extends RangeTestBase<InstantRange, Instant, Duration> {
    Instant[] points = {
            Instant.ofEpochSecond(42),
            Instant.ofEpochSecond(51),
            Instant.ofEpochSecond(55),
            Instant.ofEpochSecond(1023),
            Instant.ofEpochSecond(1610),
            Instant.ofEpochSecond(2840)
    };

    RangeTestDataFactory<InstantRange,Instant,Duration> daf;

    @Override
    RangeTestDataFactory<InstantRange, Instant, Duration> helper() {
        if(daf==null){
            daf = new RangeTestDataFactory<InstantRange, Instant, Duration>(points) {
                @Override
                InstantRange createRange(Instant start, Instant end) {
                    return InstantRange.of(start,end);
                }

                @Override
                Duration distance(Instant a, Instant b) {
                    return Duration.between(a,b);
                }
            };
        }
        return daf;
    }
}
