package minibar;

/**
 * This class represent a typical guest of a pub. He / she want drink beer and
 * can be drunken.
 *
 * @author Pia Erbrath
 */
public class Guest {
    private final double drinkingCapacity;
    private double fill;

    public Guest(double drinkingCapacity) {
        this.drinkingCapacity = drinkingCapacity;
        fill = 0.0;
    }
    /**
     * *
     * Drinks a beer, optionally get drunk, Hickup.
     *
     * @param beer to consume
     * @return self, I am still on my feet.
     * @throws DrunkenException when overfilled.
     */
    public Guest drink( Beer beer ) throws DrunkenException {
        if(getRemainingCapacity() < beer.getVolume()) throw new DrunkenException("Guest got drunk");
        this.fill += beer.getVolume();
        return this;
    }

    public double getRemainingCapacity() {
        return drinkingCapacity - fill;
    }
}
