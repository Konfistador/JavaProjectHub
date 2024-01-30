package pub;

//@author bonm
import java.util.Locale;
import static org.assertj.core.api.Assertions.*;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class DrinkerFullExceptionTest {

    @ParameterizedTest
    @CsvSource({
        "nl,NL,te veel gedronken",
        "en,US,drank too much",
        "es,ES,Bebiste demasiado"
    })
    public void testLocalizedMessage(String language, String country, String message) {
        Locale.setDefault( new Locale( language, country ) );
        var description = String.format("DrinkerFullException in %s_%s should contain %s", language,country, message);
        DrinkerFullException ex = new DrinkerFullException();
        assertThat(ex.getLocalizedMessage())
                .as(description)
                .contains(message.split(" "));
    }
}
