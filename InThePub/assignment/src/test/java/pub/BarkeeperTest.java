package pub;

import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.*;

import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;

import java.util.stream.Stream;

import static pub.Pub.PINT;

/**
 * @author urs
 */
public class BarkeeperTest {

    private Barkeeper barkeeper;
    private Pub pub;
    private final double stock = 100.0;

    private Stream<BeerSize> beerSizes;

    @BeforeEach
    void setUp() {
        pub = new Pub(stock);
        barkeeper = new Barkeeper(pub);
        beerSizes = Stream.of(BeerSize.values());
    }

    @Test
    void barkeeperTapsBeer() {
        SoftAssertions softly = new SoftAssertions();
        beerSizes.forEach(size -> {
            try {
                Beer beer = barkeeper.tapBeer(size.getSize());
                softly.assertThat(beer.getSize()).isEqualTo(size.getSize());
                softly.assertThat(pub.getStock()).isEqualTo(stock - size.getSize());
            } catch (EmptyStockException e) {
                fail("Should not throw exception");
            }
        });
        //fail("You know what to do");
    }

    @Test
    void barkeeperServesDrinker() {
        Drinker drinker = new Drinker(5.0, 22); // I am a big eastern european guy, who can drink plenty of beer.
        SoftAssertions softly = new SoftAssertions();
        var preCapacity = drinker.getVolumeLeft();
        beerSizes.forEach(size -> {
            try {
                Beer beer = barkeeper.tapBeer(size.getSize());
            } catch (EmptyStockException e) {
                throw new RuntimeException(e);
            }
            try {
                barkeeper.serve(drinker, size.getSize());
            } catch (DrinkerTooYoungException | EmptyStockException | DrinkerFullException e) {
                throw new RuntimeException(e);
            }
            softly.assertThat(drinker.getVolumeLeft()).isEqualTo(preCapacity - size.getSize());
            softly.assertThat(pub.getStock()).isEqualTo(stock - size.getSize());
        });
        //fail("You know what to do");
    }

    @Test
    public void testTabBeerEmptyStock() {
        pub.decreaseStock(100.0);
        assertThatThrownBy(() -> barkeeper.tapBeer(PINT))
                .isInstanceOf(EmptyStockException.class)
                .hasMessageContaining("beer is sold out");
       // fail("You know what to do");
    }

    @Test
    public void testServeDrinkerTooYoung() {
        Drinker drinker = new Drinker(5.0, 17); // I am an underage eastern european guy, who can drink plenty of beer.
        assertThatThrownBy(() -> barkeeper.serve(drinker, PINT))
                .isInstanceOf(DrinkerTooYoungException.class)
                .hasMessageContaining("You are too young to drink");
       // fail("You know what to do");
    }
}
