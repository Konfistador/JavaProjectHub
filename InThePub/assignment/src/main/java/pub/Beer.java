package pub;

/**
 * Beer.
 *
 * @author Linda Urselmans {@code l.urselmans@fontys.nl}
 */
public class Beer {

    private final double capacity;

    Beer( double size ) {
        capacity = size;
    }

    double getSize() {
        return capacity;
    }
}
