package pub;

/**
 * Thirsty Tom.
 * 
 * @author Linda Urselmans {@code l.urselmans@fontys.nl}
 */
class Drinker {

    private double startCapacity;
    private final int age;

    Drinker( double maxDrinkVolume, int anAge ) {
        startCapacity = maxDrinkVolume;
        age = anAge;
    }

    void drinkBeer( Beer beer )
            throws DrinkerFullException {
        if ( startCapacity - beer.getSize() < 0 ) {
            throw new DrinkerFullException();
        }
        startCapacity -= beer.getSize();
    }

    double getVolumeLeft() {
        return startCapacity;
    }

    int getAge() {
        return age;
    }
}
