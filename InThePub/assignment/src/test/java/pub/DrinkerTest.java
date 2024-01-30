package pub;

//@author urs
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.within;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DrinkerTest {

    private Drinker drinker;
    private final double maxDrinkVolume = 3.0;

    @BeforeEach
    public void setUp() {
        Pub pub = new Pub( 100.0 );
        drinker = new Drinker( maxDrinkVolume, 18 );

    }

    @Test
    public void drinkerDrinksBeer() throws DrinkerFullException {

        drinker.drinkBeer( new Beer( 0.57 ) );
        double actual = drinker.getVolumeLeft();
        assertThat( actual ).isCloseTo( 2.43, within( 0.1d ) );
    }
    
    @Test
    public void testDrinkerFull() {
        ThrowingCallable code = () -> {
            drinker.drinkBeer(new Beer(maxDrinkVolume + 1.0));
        };
        
        assertThatCode(code)
                .as("Drinker cannot drink more than their limit")
                .isExactlyInstanceOf(DrinkerFullException.class);
    }

}
