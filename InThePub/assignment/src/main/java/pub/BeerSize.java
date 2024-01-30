package pub;

import java.util.stream.Stream;

/**
 * Beer Container sizes.
 *
 * @author Linda Urselmans {@code l.urselmans@fontys.nl}
 */
public enum BeerSize {
    /**
     * Containers in  ascending volume.
     */
    SMALL( 0.2 ), PINT( 0.57 ), LARGE( 1.0 );

    private final double capacity;

    BeerSize( double size ) {
        capacity = size;
    }

    double getSize() {
        return capacity;
    }

    /**
     * Use stream to get the container that fits the volume requested .
     * 
     * Hint to the implementer: use streams to find a fitting container. 
     * 
     * @param volume requested
     * @return a beer container that fits, or null if not available.
     */
    static BeerSize findBySize( double volume ) {
        return Stream.of( values() )
                .filter( b -> b.getSize() >= volume )
                .findFirst()
                .orElse( null );
    }
}
