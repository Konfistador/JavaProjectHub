package pub;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

/**
 *
 * @author Pieter van den Hombergh {@code Pieter.van.den.Hombergh@gmail.com}
 */
public class HablasEspañolTest {
    
    @Test
    public void testSomeMethod() {
        
        assertThatCode( () -> {
            HablasEspañol.main( new String[]{} );
        } ).doesNotThrowAnyException();
    }
    
    @Test
    public void testInstance() {
        var es = new HablasEspañol();
        assertThat(es).isNotNull();
    }
    
}
