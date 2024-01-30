package validator;

import java.util.EnumSet;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

/**
 * Test Flaw enum and its static convenience methods.
 * 
 * @author "Pieter van den Hombergh {@code p.vandenhombergh@fontys.nl}"
 */
public class FlawTest {

    /**
     * Test the function for one encoding.
     */
    @Test
    public void encodingChar() {
        assertThat( Flaw.encodingToFlaw( '8' ) )
                .isSameAs( Flaw.NODIGIT );
    }

    /**
     * Test the function for one encoding.
     */
    @Test
    public void encodingCharNull() {
        assertThat( Flaw.encodingToFlaw( '?' ) )
                .isNull();
    }

    /**
     * Test with 2 valid and one invalid encoding and check for the matching set.
     */
    @Test
    public void encodingString() {
        EnumSet<Flaw> stringToFlawSet = Flaw.stringToFlawSet( "#8X" );
        assertThat( stringToFlawSet )
                .containsExactlyInAnyOrder( Flaw.NOSPECIAL, Flaw.NODIGIT );
    }

    /**
     * We want an empty set, not null.
     */
    @Test
    public void encodingStringEmpty() {
        EnumSet<Flaw> stringToFlawSet = Flaw.stringToFlawSet( "" );
        assertThat( stringToFlawSet )
                .isNotNull()
                .isEmpty();
    }

}
