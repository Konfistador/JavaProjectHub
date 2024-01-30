package minibar;

import static org.assertj.core.api.Assertions.*;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Pieter van den Hombergh {@code p.vandehombergh@gmail.com}
 */
public class GuestTest {

    private Guest sut;

    @BeforeEach
    public void setUp() {
        sut = new Guest(3);
    }

    @Test
    public void guestCanDrinkBeer() {
        sut.drink(new Beer(2.5));

        assertThat(sut.getRemainingCapacity())
                .as("Expecting guest's remaining capacity to change, after drinking a beer")
                .isEqualTo(.5);
        //fail("guestCanDrinkBeer test method completed. You know what to do");
    }

    @Test
    public void exceptionIsThrownWhenGuestIsDrunk() {
        assertThatThrownBy(() -> sut.drink(new Beer(5)))
                .as("Expecting Guest to throw(up) a proper exception when drinking more than their capacity")
                .isExactlyInstanceOf(DrunkenException.class)
                        .hasMessageContaining("Guest got drunk");
        //fail("exceptionIsThrownWhenGuestIsDrunk test method completed. You know what to do");
    }
}
