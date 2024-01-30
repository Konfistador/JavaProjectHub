package pub;

/**
 * Bart.
 *
 * @author Linda Urselmans {@code l.urselmans@fontys.nl}
 */
class Barkeeper {

    private final Pub pub;

    Barkeeper( Pub pub ) {
        this.pub = pub;
    }

    Beer tapBeer( double amount )
            throws EmptyStockException {
        double stockAvialable = pub.getStock();
        if ( stockAvialable - amount < 0 ) {
            throw new EmptyStockException( stockAvialable );
        }
        pub.decreaseStock( amount );
        return new Beer( amount );
    }

    void serve( Drinker drinker, Double beerAmount )
            throws DrinkerTooYoungException, EmptyStockException,
                   DrinkerFullException {
        if ( drinker.getAge() < 18 ) {
            throw new DrinkerTooYoungException();
        }
        Beer beer = tapBeer( beerAmount );
        drinker.drinkBeer( beer );
    }
}
