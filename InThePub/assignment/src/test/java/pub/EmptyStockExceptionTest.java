package pub;

//@author bonm
import java.util.Locale;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class EmptyStockExceptionTest {

    @ParameterizedTest
    @CsvSource({
        "nl,NL,bier uitverkocht",
        "en,US,beer is sold out",
        "es,ES,cerveza agotada"
    })
    public void testLocalizedMessage(String language, String country, String message) {
        Locale.setDefault( new Locale( language, country ) );
        var description = String.format("EmptyStockException in %s_%s should contain %s", language, country, message);
        EmptyStockException ex = new EmptyStockException(0.0);
        assertThat(ex.getLocalizedMessage())
                .as(description)
                .contains(message.split(" "));
    }
}
