package pub;

/**
 * Where it all happens.
 *
 * @author Linda Urselmans {@code l.urselmans@fontys.nl}
 */
class Pub {

    public static final double PINT = 0.57;
    public static final double SMALL = 0.2;
    private double stock;

    Pub( double beerStock ) {
        stock = beerStock;
    }

    double getStock() {
        return stock;
    }

    void decreaseStock( double amount ) {
        stock = stock - amount;
    }

}
