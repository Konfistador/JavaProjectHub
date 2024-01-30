package nl.fontys.sebivenlo.ranges;

import java.time.Duration;
import java.time.Instant;
import java.util.function.BiFunction;

public class InstantRange implements Range<InstantRange, Instant, Duration> {

    private final Instant start;
    private final Instant end;

    private InstantRange(Instant[] inputPoints) {
        this.start = inputPoints[0];
        this.end = inputPoints[1];
    }

    /**
     * ConvenienceFactory.
     *
     * @param start of range
     * @param end   of range
     * @return the range
     */
    public static InstantRange of(Instant start, Instant end) {
        return new InstantRange(Range.minmax(start, end));
    }

    @Override
    public Instant start() {
        return this.start;
    }

    @Override
    public Instant end() {
        return this.end;
    }

    @Override
    public BiFunction<Instant, Instant, Duration> meter() {
        return Duration::between;
    }

    @Override
    public InstantRange between(Instant startInclusive, Instant endExclusive) {
        return InstantRange.of(startInclusive, endExclusive);
    }

    @Override
    public Duration zero() {
        return Duration.ZERO;
    }

    @Override
    public int hashCode() {
        return rangeHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return rangeEquals(obj);
    }

    @Override
    public String toString() {
        return rangeToString();
    }
}
